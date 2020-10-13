package lhdt.svc.landscape.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lhdt.svc.landscape.model.LSAnalsPredict;
import lhdt.svc.landscape.types.LSAnalsPredictType;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
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
import lhdt.svc.common.fileinfo.model.FileInfo;
import lhdt.svc.landscape.domain.LandScapeAnals;
import lhdt.svc.landscape.model.LandScapeAnalsParam;
import lhdt.svc.landscape.service.LandScapeAnalsService;
import lhdt.svc.landscape.types.LandScapeAnalsType;
import lombok.extern.slf4j.Slf4j;

/**
 * 경관 분석 데이터 제공 서비스
 * @author gravity
 * @since 2020. 9. 4.
 *
 */
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
	 * 등록된 모든 경관 점 정보를 가져옵니다
	 * @return
	 */
	@GetMapping("/select_all")
	public List<LandScapeAnals> getLsPosAll() {
		return this.landScapeAnalsService.findAll();
	}

	/**
	 * 아이디를 통하여 등록된 모든 경관 점 정보를 가져옵니다
	 * @return
	 */
	@GetMapping("/select")
	public List<LandScapeAnals> getLsPosAllById(Integer id) {
		return this.landScapeAnalsService.findAllById(Long.valueOf(id));
	}

	/**
	 * 아이디와 경관분석 종류를 통하여 등록된 모든 경관 점 정보를 가져옵니다
	 * @return
	 */
	@GetMapping("/exists")
	public boolean existsLsPosByBiz(Integer id, LandScapeAnalsType landScapeAnalsType) {
		var spdt = new LandScapeAnals();
		spdt.setId(Long.valueOf(id));
		spdt.setLandScapeAnalsType(landScapeAnalsType);
		return this.landScapeAnalsService.existVoByUk(spdt);
	}

	/**
	 *
	 * 경관점 정보를 저장합니다
	 * @return
	 */
	@PostMapping(path = "/insert")
	public LandScapeAnals insertLsPos(LandScapeAnalsParam cprd) {
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
	 * 경관점 정보를 수정합니다
	 * @return
	 */
	@PutMapping("/update")
	public LandScapeAnals updateLsPos(LandScapeAnals cprdt) {
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
	 * 경관점 정보를 삭제합니다
	 * @return
	 */
	@DeleteMapping("/delete")
	public boolean deleteLsPos(Integer id) {
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
			LSAnalsPredictType lsAnalsPredictType;

			public InnerClass() {

			}

			public InnerClass(LSAnalsPredictType lsAnalsPredictType) {
				this.lsAnalsPredictType = lsAnalsPredictType;
			}
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
			LSAnalsPredict requestPredictImage(MultipartFile file) throws IOException {
				//
				Path path = Paths.get(fileUploadPath);
				String filename = System.nanoTime() + ".png";

				//임시 파일로 저장
				saveToFile(file, path, filename);

				//
				try(CloseableHttpClient httpclient = HttpClients.createDefault()){
					HttpPost httppost = new HttpPost(predictServerUrl);

					org.apache.http.HttpEntity reqEntity;
					//
					if(this.lsAnalsPredictType == LSAnalsPredictType.스카이라인) {
						reqEntity = MultipartEntityBuilder.create()
								.addPart("image", new FileBody(path.resolve(filename).toFile()))
								.addPart("color", new StringBody("ff8da7", org.apache.http.entity.ContentType.TEXT_PLAIN))
								.addPart("thickness", new StringBody("7", org.apache.http.entity.ContentType.TEXT_PLAIN))
								.addPart("format", new StringBody("base64", org.apache.http.entity.ContentType.TEXT_PLAIN))
								.addPart("command", new StringBody("skyline_detection", org.apache.http.entity.ContentType.TEXT_PLAIN))
								.build();
					} else {
						reqEntity = MultipartEntityBuilder.create()
								.addPart("image", new FileBody(path.resolve(filename).toFile()))
								.addPart("color", new StringBody("ff8da7", org.apache.http.entity.ContentType.TEXT_PLAIN))
								.addPart("thickness", new StringBody("7", org.apache.http.entity.ContentType.TEXT_PLAIN))
								.addPart("format", new StringBody("base64", org.apache.http.entity.ContentType.TEXT_PLAIN))
								.addPart("command", new StringBody("view_shielding_rate", org.apache.http.entity.ContentType.TEXT_PLAIN))
								.build();
					}

					//
					httppost.setEntity(reqEntity);

					//
					log.debug("executing request {}", httppost.getRequestLine());
					org.apache.http.HttpEntity resEntity = null;

					int timeout = 5;

					//
					try( CloseableHttpResponse response = httpclient.execute(httppost)){

						//
						resEntity = response.getEntity();

						//
						if(null == resEntity) {
							return null;
						}

						//
						String str = EntityUtils.toString(resEntity);
						if(null == str || 0 == str.length()) {
							log.warn("<<.requestskylineImage - empty str");
							return null;
						}

						//
						if(!str.trim().startsWith("{")) {
							log.warn("<<.requestskylineImage - not json string {}", str);
							return null;
						}

						//
						Map<String,Object> map = new ObjectMapper().readValue(str, Map.class);
						LSAnalsPredict lsAnalsPredict = new LSAnalsPredict();
						var resultImg = ((Map)map.get("result")).get("output_img").toString();
						lsAnalsPredict.setLsAnalsPredictType(this.lsAnalsPredictType);
						lsAnalsPredict.setOutput_img(resultImg);
						if(this.lsAnalsPredictType == LSAnalsPredictType.조망차폐) {
							var resultRatio = ((Map)map.get("result")).get("shielding_rate").toString();
							BigDecimal b = new BigDecimal( resultRatio);
							lsAnalsPredict.setShielding_rate(b.longValue());

						}
						//
						return lsAnalsPredict;
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
				return null;

			}

		}//
		//		log.debug("{}",request.getFileMap());


		//
		var lsAnalsPredictTypeString = request.getParameter("lsAnalsPredictType");
		InnerClass ic = new InnerClass(LSAnalsPredictType.valueOf(lsAnalsPredictTypeString));

		//
		Map<String,Object> resultMap = new HashMap<>();
		//
		Map<String, MultipartFile> fileMap = request.getFileMap();		
		resultMap.put("predictInfo"	, ic.requestPredictImage(fileMap.get("blob")));
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
		regist(fileMap);

		//
		return null;
	}
	
	
	/**
	 * 경관분석 결과를 저장합니다
	 * 1. 이미지 파일 저장
	 * 2. regist file_info 테이블
	 * 3. regist 경관 분석 테이블
	 * TODO 이 메소드는 SERVICE로 이동해야 함
	 * @param fileMap
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private void regist(Map<String,MultipartFile> fileMap ) throws IllegalStateException, IOException {
		
		//캡처 이미지 처리
		for(int i=0; i<3; i++) {
			//
			MultipartFile captureFile = fileMap.get("capture["+i+"]");
			String captureFilename = i + SvcUtils.createShortUid("_") + ".png";
			saveToFile(captureFile, captureFilename);
			//
			FileInfo fileInfo = FileInfo.builder()
					.fileExt("png")
					.fileName(captureFilename)
					.filePath(fileUploadPath)
					.originFileName("blob")
					.build();
			fileInfoService.regist(fileInfo);
			
			//
			registDummy(fileInfo);
			
		}
		
		
		//스카이라인 이미지 처리
		for(int i=0; i<3; i++) {
			//
			MultipartFile skylineFile = fileMap.get("skyline["+i+"]");
			String skylineFilename = i + SvcUtils.createShortUid("_") + ".png";
			saveToFile(skylineFile, skylineFilename);
			//
			FileInfo fileInfo = FileInfo.builder()
					.fileExt("png")
					.fileName(skylineFilename)
					.filePath(fileUploadPath)
					.originFileName("blob")
					.build();
			fileInfoService.regist(fileInfo);
			
			//
			registDummy(fileInfo);
			
		}
		
	}

	/**
	 *
	 * @param fileInfo
	 */
	private void registDummy(FileInfo fileInfo) {
		log.debug("{}", fileInfo);
	}

	
	/**
	 * 물리적인 파일로 파일 저장
	 * @param mfile
	 * @param filename
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private String saveToFile(MultipartFile mfile, String filename) throws IllegalStateException, IOException {
		mfile.transferTo(Paths.get(fileUploadPath, filename));
		
		//
		log.debug("<<.saveToFile - {}", Paths.get(fileUploadPath, filename));
		return filename;
	}

}
