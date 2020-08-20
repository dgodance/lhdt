package lhdt.svc.landscape.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import lhdt.svc.common.SvcController;
import lhdt.svc.common.SvcUtils;
import lhdt.svc.landscape.domain.LandScapeAnals;
import lhdt.svc.landscape.model.LandScapeAnalsParam;
import lhdt.svc.landscape.service.LandScapeAnalsService;
import lhdt.svc.landscape.types.LandScapeAnalsType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/landscape_anals")
public class LandScapeAnalsController extends SvcController {
	@Autowired
	private LandScapeAnalsService landScapeAnalsService;


	@Value("${app.file.upload.path}")
	private String fileUploadPath;
	
	@Value("${app.predict.server.url}")
	private String predictServerUrl;

	/**
	 * 모든 LowInfo 정보를 가지고 옵니다
	 * @return
	 */
	@GetMapping("/select_all")
	public List<LandScapeAnals> getLandScapeAll() {
		return this.landScapeAnalsService.findAll();
	}

	/**
	 * id에 부합하는 LowInfo 정보를 가지고 옵니다
	 * @return
	 */
	@GetMapping("/select")
	public List<LandScapeAnals> getLandScapeAllById(Integer id) {
		return this.landScapeAnalsService.findAllById(Long.valueOf(id));
	}

	/**
	 * Uk에 대한 SubType이 존재하는지 확인합니다
	 * @return
	 */
	@GetMapping("/exists")
	public boolean existsCityPlanReportDet(Integer id, LandScapeAnalsType landScapeAnalsType) {
		var spdt = new LandScapeAnals();
		spdt.setId(Long.valueOf(id));
		spdt.setLandScapeAnalsType(landScapeAnalsType);
		return this.landScapeAnalsService.existVoByUk(spdt);
	}

	/**
	 * 하나의 데이터를 입력합니다
	 *
	 * @return
	 */
	@PostMapping(path = "/insert")
	public LandScapeAnals insertCityPlanReportDet(LandScapeAnalsParam cprd) {
		LandScapeAnals lsa = new LandScapeAnals();
		lsa.setLandScapeAnalsName(cprd.getLandScapeAnalsName());

		if(cprd.getStartPosX() != null && cprd.getStartPosY() != null) {
			Point p0 = new Point(cprd.getStartPosX(), cprd.getStartPosY());
			lsa.setStartLandScapePos(p0);
			lsa.setStartAltitude(cprd.getStartPosZ());
		}
		if(cprd.getEndPosX() != null && cprd.getEndPosY() != null) {
			Point p1 = new Point(cprd.getEndPosX(), cprd.getEndPosY());
			lsa.setEndLandScapePos(p1);
			lsa.setEndAltitude(cprd.getEndPosZ());
		}
		lsa.setLandScapeAnalsType(cprd.getLandScapeAnalsType());
		var p = this.landScapeAnalsService.registByUk(lsa);
		if (p == null) {
			return null;
		} else {
			return p;
		}
	}

	/**
	 * 특정 데이터를 업데이트합니다.
	 *
	 * @return
	 */
	@PutMapping("/update")
	public LandScapeAnals updateCityPlanReportDet(LandScapeAnals cprdt) {
		var p = this.landScapeAnalsService.findOneById(cprdt.getId());
		p.setLandScapeAnalsName(cprdt.getLandScapeAnalsName());
		p.setStartLandScapePos(cprdt.getStartLandScapePos());
		p.setEndLandScapePos(cprdt.getEndLandScapePos());
		p.setLandScapeAnalsType(cprdt.getLandScapeAnalsType());
		p = this.landScapeAnalsService.update(p);
		if(p == null) {
			return null;
		} else {
			return p;
		}
	}

	/**
	 * 데이터를 삭제합니다
	 * @return
	 */
	@DeleteMapping("/delete")
	public boolean deleteExample(Integer id) {
		this.landScapeAnalsService.deleteAllById(Long.valueOf(id));
		return true;
	}

	/**
	 * 지도 캡처 이미지 업로드 & 분석서버에 전달 & get스카이라인 이미지(base64) & 응답
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@CrossOrigin(origins = "*")
	@PostMapping(value="/uploadFileAndGetSkylineImage")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> uploadFile(MultipartHttpServletRequest request) throws IllegalStateException, IOException {
		class InnerClass{
			/**
			 * 파일 저장
			 * @param source
			 * @param path
			 * @param filename
			 * @throws IllegalStateException
			 * @throws IOException
			 */
			void saveToFile(MultipartFile source, Path path, String filename) throws IllegalStateException, IOException {
				source.transferTo(path.resolve(filename));				
			}



			/**
			 * 분석서버에 파일 전송 & get base64
			 * @param file 업로드된 파일
			 * @return
			 * @throws IOException
			 */
			@SuppressWarnings({ "unchecked", "rawtypes" })
			String requestSkylineImage(MultipartFile file) throws IOException {
				//
				Path path = Paths.get(fileUploadPath);
				String filename = System.nanoTime() + ".png";

				//임시 파일로 저장
				saveToFile(file, path, filename);

				//
				try(CloseableHttpClient httpclient = HttpClients.createDefault()){
					HttpPost httppost = new HttpPost(predictServerUrl);

					//
					org.apache.http.HttpEntity reqEntity = MultipartEntityBuilder.create()
							.addPart("image", new FileBody(path.resolve(filename).toFile()))
							.addPart("format", new StringBody("base64", org.apache.http.entity.ContentType.TEXT_PLAIN))
							.addPart("command", new StringBody("skyline_detection", org.apache.http.entity.ContentType.TEXT_PLAIN))
							.build();

					//
					httppost.setEntity(reqEntity);

					//
					log.debug("executing request {}", httppost.getRequestLine());
					org.apache.http.HttpEntity resEntity = null;

					//
					try( CloseableHttpResponse response = httpclient.execute(httppost)){

						//
						resEntity = response.getEntity();

						//
						if(null == resEntity) {
							return "";
						}

						//
						String str = EntityUtils.toString(resEntity);
						if(null == str || 0 == str.length()) {
							log.warn("<<.requestskylineImage - empty str");
							return "";
						}

						//
						if(!str.trim().startsWith("{")) {
							log.warn("<<.requestskylineImage - not json string {}", str);
							return "";
						}

						//
						Map<String,Object> map = new ObjectMapper().readValue(str, Map.class);

						//
						return ((Map)map.get("result")).get("output_img").toString();
					}catch(Exception e) {
						log.error("{}",e);
					}finally {
						EntityUtils.consume(resEntity);

						//						
						Files.deleteIfExists(path.resolve(filename));
					}
				}catch(Exception e) {
					log.error("{}",e);
				}finally {
					//
					log.info("<<.requestSkylineImage");
				}
				
				//
				return "";

			}

		}//

		//		log.debug("{}",request.getFileMap());


		//
		InnerClass ic = new InnerClass();

		//
		Map<String,Object> resultMap = new HashMap<>();

		//
		Map<String, MultipartFile> fileMap = request.getFileMap();		
		resultMap.put("base64"	, ic.requestSkylineImage(fileMap.get("blob")));		
		resultMap.put("dt", new Date());


		//
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
	}
	
	
	/**
	 * 캡처 이미지, 스카이라인 이미지 파일로 저장
	 * TODO 테이블 설계 필요. 이미지 정보 테이블에 저장해야 함
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@CrossOrigin(origins = "*")
	@PostMapping(value="/saveCaptureAndSkylineImages")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> saveCaptureAndSkylineImages(MultipartHttpServletRequest request) throws IllegalStateException, IOException{
		Map<String,MultipartFile> fileMap = request.getFileMap();
		
		//
		String groupId = registGroupDummy("dummyUserId");
		
		//
		for(int i=0; i<3; i++) {
			//
			MultipartFile captureFile = fileMap.get("capture["+i+"]");
			String captureFilename = groupId + "_" + i + SvcUtils.createShortUid("_") + ".png";
			saveToFile(captureFile, captureFilename);
			registDummy(groupId, "capture", i, captureFilename);

			//
			MultipartFile skylineFile = fileMap.get("skyline["+i+"]");
			String skylineFilename = groupId + "_" + i + SvcUtils.createShortUid("_") + ".png";
			saveToFile(skylineFile, skylineFilename);
			registDummy(groupId, "skyline", i, skylineFilename);
		}

		//
		return null;
	}
	
	/**
	 * 데이터(이미지 정보) 등록. 임시
	 * @param groupId
	 * @param imageGbn
	 * @param gbn
	 * @param filename
	 */
	private void registDummy(String groupId, String imageGbn, int gbn, String filename) {
		log.debug("{} {} {} {}", groupId, imageGbn, gbn, filename);
	}

	/**
	 * 그룹에 데이터 등록. 임시
	 * @param userId
	 * @return
	 */
	private String registGroupDummy(String userId) {
		String groupId = SvcUtils.createShortUid("G");
		
		//
		log.debug(".registGroup - {} {}", userId, new Date());
		
		//
		return groupId;
	}
	
	
	/**
	 * 파일 저장
	 * @param mfile
	 * @param filename
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private String saveToFile(MultipartFile mfile, String filename) throws IllegalStateException, IOException {
		mfile.transferTo(Paths.get(fileUploadPath, filename));
		
		//
		return filename;
	}

}
