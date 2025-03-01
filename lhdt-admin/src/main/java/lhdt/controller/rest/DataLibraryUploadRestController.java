package lhdt.controller.rest;

import lhdt.config.PropertiesConfig;
import lhdt.domain.FileType;
import lhdt.domain.Key;
import lhdt.domain.UploadDataType;
import lhdt.domain.UploadDirectoryType;
import lhdt.domain.extrusionmodel.DataLibraryUpload;
import lhdt.domain.extrusionmodel.DataLibraryUploadFile;
import lhdt.domain.policy.Policy;
import lhdt.domain.user.UserSession;
import lhdt.service.DataLibraryService;
import lhdt.service.DataLibraryUploadService;
import lhdt.service.PolicyService;
import lhdt.support.LogMessageSupport;
import lhdt.utils.DateUtils;
import lhdt.utils.FileUtils;
import lhdt.utils.FormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 데이터 라이브러리 파일 업로더
 * TODO 설계 파일 안의 texture 의 경우 설계 파일에서 참조하는 경우가 있으므로 이름 변경 불가.
 * @author jeongdae
 *
 */
@Slf4j
@RestController
@RequestMapping("/data-library-uploads")
public class DataLibraryUploadRestController {
	
	// 파일 copy 시 버퍼 사이즈
	public static final int BUFFER_SIZE = 8192;
	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private PropertiesConfig propertiesConfig;
	
	@Autowired
	private DataLibraryUploadService dataLibraryUploadService;

	/**
	 * TODO 비동기로 처리해야 할듯
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@PostMapping
	public Map<String, Object> insert(MultipartHttpServletRequest request) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		String errorCode = null;
		String message = null;
		String dataType = request.getParameter("dataType");
		
		// converter 변환 대상 파일 수
		int converterTargetCount = 0;
		
		Policy policy = policyService.getPolicy();
		// 여긴 null 체크를 안 하는게 맞음. 없음 장애가 나야 함
		// 업로딩 가능한 파일 타입
		String[] uploadTypes = policy.getUserUploadType().toLowerCase().split(",");
		// 변환 가능한 파일 타입
		String[] converterTypes = policy.getUserConverterType().split(",");
		List<String> uploadTypeList = Arrays.asList(uploadTypes);
		List<String> converterTypeList = Arrays.asList(converterTypes);
		
		errorCode = dataLibraryValidate(request);
		if(!StringUtils.isEmpty(errorCode)) {
			log.info("@@@@@@@@@@@@ errorCode = {}", errorCode);
			result.put("statusCode", HttpStatus.BAD_REQUEST.value());
			result.put("errorCode", errorCode);
			result.put("message", message);
            return result;
		}
		
		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
		String userId = userSession.getUserId();
		List<DataLibraryUploadFile> dataLibraryUploadFileList = new ArrayList<>();
		Map<String, MultipartFile> fileMap = request.getFileMap();
		
		Map<String, Object> uploadMap = null;
		String today = DateUtils.getToday(FormatUtils.YEAR_MONTH_DAY_TIME14);
		
		// 1 directory 생성
		String makedDirectory = FileUtils.makeDirectory(userId, UploadDirectoryType.YEAR_MONTH, propertiesConfig.getDataLibraryUploadDir());
		log.info("@@@@@@@ = {}", makedDirectory);
		
		// 2 한건이면서 zip 의 경우
		boolean isZipFile = false;
		int fileCount = fileMap.values().size();
		if(fileCount == 1) {
			// processAsync(policy, userId, fileMap, makedDirectory);
			for (MultipartFile multipartFile : fileMap.values()) {
				String[] divideNames = multipartFile.getOriginalFilename().split("\\.");
				String fileExtension = divideNames[divideNames.length - 1];
				if(DataLibraryUpload.ZIP_EXTENSION.equalsIgnoreCase(fileExtension)) {
					isZipFile = true;
					// zip 파일
					uploadMap = unzip(policy, uploadTypeList, converterTypeList, today, userId, multipartFile, makedDirectory, dataType);
					log.info("@@@@@@@ uploadMap = {}", uploadMap);
					
					// validation 체크
					if(uploadMap.containsKey("errorCode")) {
						errorCode = (String)uploadMap.get("errorCode");
						log.info("@@@@@@@@@@@@ errorCode = {}", errorCode);
						result.put("statusCode", HttpStatus.BAD_REQUEST.value());
						result.put("errorCode", errorCode);
						result.put("message", message);
			            return result;
					}
					
					// converter 변환 대상 파일 수
					converterTargetCount = (Integer)uploadMap.get("converterTargetCount");
					if(converterTargetCount <= 0) {
						log.info("@@@@@@@@@@@@ converterTargetCount = {}", converterTargetCount);
						result.put("statusCode", HttpStatus.BAD_REQUEST.value());
						result.put("errorCode", "converter.target.count.invalid");
						result.put("message", message);
			            return result;
					}
					
					dataLibraryUploadFileList = (List<DataLibraryUploadFile>)uploadMap.get("dataLibraryUploadFileList");
				}
			}
		}
		
		if(!isZipFile) {
			// zip 파일이 아니면 기본적으로 한 폴더에 넣어야 함
			
			String tempDirectory = userId + "_" + System.nanoTime();
			// 파일을 upload 디렉토리로 복사
			FileUtils.makeDirectory(makedDirectory + tempDirectory);
			// 3 그 외의 경우는 재귀적으로 파일 복사
			for (MultipartFile multipartFile : fileMap.values()) {
				log.info("@@@@@@@@@@@@@@@ name = {}, originalName = {}", multipartFile.getName(), multipartFile.getOriginalFilename());
				
				DataLibraryUploadFile dataLibraryUploadFile = new DataLibraryUploadFile();
				boolean converterTarget = false;
				
				// 파일 기본 validation 체크
				errorCode = fileValidate(policy, uploadTypeList, multipartFile);
				if(!StringUtils.isEmpty(errorCode)) {
					log.info("@@@@@@@@@@@@ errorCode = {}", errorCode);
					result.put("statusCode", HttpStatus.BAD_REQUEST.value());
					result.put("errorCode", errorCode);
					result.put("message", message);
		            return result;
				}
				
				String originalName = multipartFile.getOriginalFilename();
				String[] divideFileName = originalName.split("\\.");
    			String saveFileName = originalName;
    			
    			// validation
    			if(divideFileName.length == 0) {
    				log.info("@@@@@@@@@@@@ upload.file.type.invalid. originalName = {}", originalName);
					result.put("statusCode", HttpStatus.BAD_REQUEST.value());
					result.put("errorCode", "upload.file.type.invalid");
					result.put("message", message);
		            return result;
    			}
				
    			String extension = divideFileName[divideFileName.length - 1];
    			// !extList.contains(extension.toLowerCase())
				if(DataLibraryUpload.ZIP_EXTENSION.equalsIgnoreCase(extension) || !uploadTypeList.contains(extension.toLowerCase())) {
					log.info("@@@@@@@@@@@@ upload.file.type.invalid. originalName = {}", originalName);
					result.put("statusCode", HttpStatus.BAD_REQUEST.value());
					result.put("errorCode", "upload.file.type.invalid");
					result.put("message", message);
					return result;
				}
				
				if(converterTypeList.contains(extension.toLowerCase())) {
					if(!dataType.equalsIgnoreCase(extension)) {
						// 데이터 타입과 업로딩 파일 확장자가 같지 않고
						if(	UploadDataType.CITYGML == UploadDataType.findBy(dataType)
								&& UploadDataType.GML.getValue().equalsIgnoreCase(extension)){
							// 데이터 타입은 citygml 인데 확장자는 gml 인 경우 통과
						} else if(UploadDataType.INDOORGML == UploadDataType.findBy(dataType)
								&& UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
							// 데이터 타입은 indoorgml 인데 확장자는 gml 인 경우 통과
						} else {
							// 전부 예외
							log.info("@@@@@@@@@@@@ datatype = {}, extension = {}", dataType, extension);
	    					result.put("statusCode", HttpStatus.BAD_REQUEST.value());
	    					result.put("errorCode", "upload.file.type.invalid");
	    					result.put("message", message);
	    					return result;
						}
					}
					
					if(UploadDataType.CITYGML == UploadDataType.findBy(dataType) && UploadDataType.INDOORGML == UploadDataType.findBy(extension)) {
						// 전부 예외
						log.info("@@@@@@@@@@@@ 데이터 타입이 다른 경우. datatype = {}, extension = {}", dataType, extension);
						result.put("errorCode", "file.ext.invalid");
						return result;
					}
					
					if (UploadDataType.CITYGML.getValue().equalsIgnoreCase(dataType) && UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
						extension = UploadDataType.CITYGML.getValue();
					} else if (UploadDataType.INDOORGML.getValue().equalsIgnoreCase(dataType) && UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
						extension = UploadDataType.INDOORGML.getValue();
					}
					// 변환 대상 파일만 이름을 변경하고 나머지 파일은 그대로 이름 유지
					saveFileName = userId + "_" + today + "_" + System.nanoTime() + "." + extension;
					converterTarget = true;
					converterTargetCount++;
				}
    			
				long size = 0L;
				try (	InputStream inputStream = multipartFile.getInputStream();
						OutputStream outputStream = new FileOutputStream(makedDirectory + tempDirectory + File.separator + saveFileName)) {
				
					int bytesRead;
					byte[] buffer = new byte[BUFFER_SIZE];
					while ((bytesRead = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
						size += bytesRead;
						outputStream.write(buffer, 0, bytesRead);
					}
				
					dataLibraryUploadFile.setFileType(FileType.FILE.name());
					dataLibraryUploadFile.setFileExt(extension);
        			dataLibraryUploadFile.setFileName(multipartFile.getOriginalFilename());
        			dataLibraryUploadFile.setFileRealName(saveFileName);
        			dataLibraryUploadFile.setFilePath(makedDirectory + tempDirectory + File.separator);
        			dataLibraryUploadFile.setFileSubPath(tempDirectory);
        			dataLibraryUploadFile.setFileSize(String.valueOf(size));
        			dataLibraryUploadFile.setConverterTarget(converterTarget);
        			dataLibraryUploadFile.setDepth(1);
				} catch(IOException e) {
					LogMessageSupport.printMessage(e, "@@@@@@@@@@@@ io exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
					result.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
					result.put("errorCode", "io.exception");
					result.put("message", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
		            return result;
				} catch(Exception e) {
					LogMessageSupport.printMessage(e, "@@@@@@@@@@@@ file copy exception.");
					result.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
					result.put("errorCode", "file.copy.exception");
					result.put("message", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
		            return result;
				}

				dataLibraryUploadFileList.add(dataLibraryUploadFile);
			}
		}
		
		if(converterTargetCount <= 0) {
			log.info("@@@@@@@@@@@@ converterTargetCount = {}", converterTargetCount);
			result.put("statusCode", HttpStatus.BAD_REQUEST.value());
			result.put("errorCode", "converter.target.count.invalid");
			result.put("message", message);
            return result;
		}

		DataLibraryUpload dataLibraryUpload = new DataLibraryUpload();
		dataLibraryUpload.setDataLibraryName(request.getParameter("dataLibraryName"));
		dataLibraryUpload.setDataLibraryGroupId(Integer.valueOf(request.getParameter("dataLibraryGroupId")));
		dataLibraryUpload.setSharing(request.getParameter("sharing"));
		dataLibraryUpload.setDataType(dataType);
		dataLibraryUpload.setUserId(userId);
		dataLibraryUpload.setFileCount(dataLibraryUploadFileList.size());
		dataLibraryUpload.setConverterTargetCount(converterTargetCount);
		if(!StringUtils.isEmpty(request.getParameter("basicWidth").replace("undefined", ""))) {
			dataLibraryUpload.setBasicWidth(Integer.valueOf(request.getParameter("basicWidth")));
		}
		if(!StringUtils.isEmpty(request.getParameter("basicDepth").replace("undefined", ""))) {
			dataLibraryUpload.setBasicDepth(Integer.valueOf(request.getParameter("basicDepth")));
		}
		if(!StringUtils.isEmpty(request.getParameter("basicHeight").replace("undefined", ""))) {
			dataLibraryUpload.setBasicHeight(Integer.valueOf(request.getParameter("basicHeight")));
		}
		dataLibraryUpload.setDescription(request.getParameter("description"));
		
		log.info("@@@@@@@@@@@@ dataLibraryUpload = {}", dataLibraryUpload);
		dataLibraryUploadService.insertDataLibraryUpload(dataLibraryUpload, dataLibraryUploadFileList);
		int statusCode = HttpStatus.OK.value();
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}
	
	/**
	 * 업로딩 파일을 압축 해제
	 * @param policy
	 * @param uploadTypeList
	 * @param converterTypeList
	 * @param today
	 * @param userId
	 * @param multipartFile
	 * @param targetDirectory
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> unzip(	Policy policy,
										List<String> uploadTypeList, 
										List<String> converterTypeList, 
										String today, 
										String userId, 
										MultipartFile multipartFile, 
										String targetDirectory,
										String dataType) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		// converter 변환 대상 파일 수
		int converterTargetCount = 0;
		
		String errorCode = fileValidate(policy, uploadTypeList, multipartFile);
		if(!StringUtils.isEmpty(errorCode)) {
			result.put("errorCode", errorCode);
			return result;
		}
		
		// input directory 생성
		targetDirectory = targetDirectory + userId + "_" + System.nanoTime() + File.separator;
		FileUtils.makeDirectory(targetDirectory);
		
		File uploadedFile = new File(targetDirectory + multipartFile.getOriginalFilename());
		multipartFile.transferTo(uploadedFile);

		Map<String, String> fileNameCoupleMap = new HashMap<>();
		List<DataLibraryUploadFile> dataLibraryUploadFileList = new ArrayList<>();
		// zip 파일을 압축할때 한글이나 다국어가 포함된 경우 java.lang.IllegalArgumentException: malformed input off 같은 오류가 발생.
		// 윈도우가 CP949 인코딩으로 파일명을 저장하기 때문.
		// Charset CP949 = Charset.forName("UTF-8");
//		try ( ZipFile zipFile = new ZipFile(uploadedFile, CP949);) {
		try ( ZipFile zipFile = new ZipFile(uploadedFile)) {
			String directoryPath = targetDirectory;
			String subDirectoryPath = "";
			String directoryName = null;
			int depth = 1;
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			
			while( entries.hasMoreElements() ) {
            	DataLibraryUploadFile dataLibraryUploadFile = new DataLibraryUploadFile();
            	
            	ZipEntry entry = entries.nextElement();
            	String unzipfileName = targetDirectory + entry.getName();
            	boolean converterTarget = false;
            	
            	if( entry.isDirectory() ) {
            		// 디렉토리인 경우
            		dataLibraryUploadFile.setFileType(FileType.DIRECTORY.name());
            		if(directoryName == null) {
            			dataLibraryUploadFile.setFileName(entry.getName());
            			dataLibraryUploadFile.setFileRealName(entry.getName());
            			directoryName = entry.getName();
            			directoryPath = directoryPath + directoryName;
            			//subDirectoryPath = directoryName;
            		} else {
            			String fileName = null;
            			if(entry.getName().indexOf(directoryName) >=0) {
            				fileName = entry.getName().substring(entry.getName().indexOf(directoryName) + directoryName.length());  
            			} else {
            				// 이전이 디렉토리, 현재도 디렉토리인데 서로 다른 디렉토리
            				if(directoryPath.indexOf(directoryName) >=0) {
            					directoryPath = directoryPath.replace(directoryName, "");
            					directoryName = null;
            				}
            				fileName = entry.getName();
            			}
            			dataLibraryUploadFile.setFileName(fileName);
            			dataLibraryUploadFile.setFileRealName(fileName);
            			directoryName = fileName;
            			directoryPath = directoryPath + fileName;
            			subDirectoryPath = fileName;
            		}
            		
                	File file = new File(unzipfileName);
                    file.mkdirs();
                    dataLibraryUploadFile.setFilePath(directoryPath);
                    dataLibraryUploadFile.setFileSubPath(subDirectoryPath);
                    dataLibraryUploadFile.setDepth(depth);
                    depth++;
            	} else {
            		// 파일인 경우
            		String fileName = null;
            		String extension = null;
            		String[] divideFileName = null;
            		String saveFileName = null;
					String coupleKey = null;
            		
            		// TODO zip 파일도 확장자 validation 체크를 해야 함
            		if(directoryName == null) {
            			fileName = entry.getName();
            			divideFileName = fileName.split("\\.");
            			saveFileName = fileName;
            			if(divideFileName.length != 0) {
            				extension = divideFileName[divideFileName.length - 1];
            				if(uploadTypeList.contains(extension.toLowerCase())) {
            					if(converterTypeList.contains(extension.toLowerCase())) {
            						if(!dataType.equalsIgnoreCase(extension)) {
                						// 데이터 타입과 업로딩 파일 확장자가 같지 않고
                						if(	UploadDataType.CITYGML == UploadDataType.findBy(dataType)
                								&& UploadDataType.GML.getValue().equalsIgnoreCase(extension)){
                							// 데이터 타입은 citygml 인데 확장자는 gml 인 경우 통과
                						} else if(UploadDataType.INDOORGML == UploadDataType.findBy(dataType)
                								&& UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
                							// 데이터 타입은 indoorgml 인데 확장자는 gml 인 경우 통과
                						} else {
                							// 전부 예외
                							log.info("@@@@@@@@@@@@ datatype = {}, extension = {}", dataType, extension);
                							result.put("errorCode", "file.ext.invalid");
                							return result;
                						}
                					}
            						
            						if(UploadDataType.CITYGML == UploadDataType.findBy(dataType) && UploadDataType.INDOORGML == UploadDataType.findBy(extension)) {
            							// 전부 예외
            							log.info("@@@@@@@@@@@@ 데이터 타입이 다른 경우. datatype = {}, extension = {}", dataType, extension);
            							result.put("errorCode", "file.ext.invalid");
            							return result;
            						}
            						
            						if (UploadDataType.CITYGML.getValue().equalsIgnoreCase(dataType) && UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
                						extension = UploadDataType.CITYGML.getValue();
                					} else if (UploadDataType.INDOORGML.getValue().equalsIgnoreCase(dataType) && UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
                						extension = UploadDataType.INDOORGML.getValue();
                					}

									// Obj 파일이거나 확장자가 mtl 인 경우
									String coupleFileName = null;
									if(UploadDataType.OBJ.getValue().equalsIgnoreCase(dataType) || UploadDataType.MTL.getValue().equalsIgnoreCase(extension)) {
										coupleFileName = fileNameCoupleMap.get(fileName);
										if(StringUtils.isEmpty(coupleFileName)) {
											// 변환 대상 파일만 이름을 변경하고 나머지 파일은 그대로 이름 유지
											saveFileName = userId + "_" + today + "_" + System.nanoTime() + "." + extension;
											converterTarget = true;
											converterTargetCount++;

											coupleKey = fileName.substring(0, fileName.length() - extension.length() -1);
											fileNameCoupleMap.put(coupleKey, saveFileName);
										} else {
											// 변환 대상 파일만 이름을 변경하고 나머지 파일은 그대로 이름 유지
											saveFileName = coupleFileName;
											converterTarget = true;
											converterTargetCount++;
										}
									} else {
										// 변환 대상 파일만 이름을 변경하고 나머지 파일은 그대로 이름 유지
										saveFileName = userId + "_" + today + "_" + System.nanoTime() + "." + extension;
										converterTarget = true;
										converterTargetCount++;
									}
            					}
	        				}
            			}
            		} else {
            			if(entry.getName().indexOf(directoryName) >= 0) {
            				// 디렉토리내 파일의 경우
            				fileName = entry.getName().substring(entry.getName().indexOf(directoryName) + directoryName.length());  
            			} else {
            				fileName = entry.getName();
            				if(directoryPath.indexOf(directoryName) >= 0) {
            					directoryPath = directoryPath.replace(directoryName, "");
            					directoryName = null;
            				}
            			}
            			divideFileName = fileName.split("\\.");
            			saveFileName = fileName;
            			if(divideFileName.length != 0) {
            				extension = divideFileName[divideFileName.length - 1];
            				if(uploadTypeList.contains(extension.toLowerCase())) {
            					if(converterTypeList.contains(extension.toLowerCase())) {
            						if(!dataType.equalsIgnoreCase(extension)) {
                						// 데이터 타입과 업로딩 파일 확장자가 같지 않고
                						if(	UploadDataType.CITYGML == UploadDataType.findBy(dataType)
                								&& UploadDataType.GML.getValue().equalsIgnoreCase(extension)){
                							// 데이터 타입은 citygml 인데 확장자는 gml 인 경우 통과
                						} else if(UploadDataType.INDOORGML == UploadDataType.findBy(dataType)
                								&& UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
                							// 데이터 타입은 indoorgml 인데 확장자는 gml 인 경우 통과
                						} else {
                							// 전부 예외
                							log.info("@@@@@@@@@@@@ datatype = {}, extension = {}", dataType, extension);
                							result.put("errorCode", "file.ext.invalid");
                							return result;
                						}
                					}
            						
            						if(UploadDataType.CITYGML == UploadDataType.findBy(dataType) && UploadDataType.INDOORGML == UploadDataType.findBy(extension)) {
            							// 전부 예외
            							log.info("@@@@@@@@@@@@ 데이터 타입이 다른 경우. datatype = {}, extension = {}", dataType, extension);
            							result.put("errorCode", "file.ext.invalid");
            							return result;
            						}
            						
            						if (UploadDataType.CITYGML.getValue().equalsIgnoreCase(dataType) && UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
                						extension = UploadDataType.CITYGML.getValue();
                					} else if (UploadDataType.INDOORGML.getValue().equalsIgnoreCase(dataType) && UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
                						extension = UploadDataType.INDOORGML.getValue();
                					}

									// Obj 파일이거나 확장자가 mtl 인 경우
									String coupleFileName = null;
									if(UploadDataType.OBJ.getValue().equalsIgnoreCase(dataType) || UploadDataType.MTL.getValue().equalsIgnoreCase(extension)) {
										coupleFileName = fileNameCoupleMap.get(fileName);
										if(StringUtils.isEmpty(coupleFileName)) {
											// 변환 대상 파일만 이름을 변경하고 나머지 파일은 그대로 이름 유지
											saveFileName = userId + "_" + today + "_" + System.nanoTime() + "." + extension;
											converterTarget = true;
											converterTargetCount++;

											coupleKey = fileName.substring(0, fileName.length() - extension.length() -1);
											fileNameCoupleMap.put(coupleKey, saveFileName);
										} else {
											// 변환 대상 파일만 이름을 변경하고 나머지 파일은 그대로 이름 유지
											saveFileName = coupleFileName;
											converterTarget = true;
											converterTargetCount++;
										}
									} else {
										// 변환 대상 파일만 이름을 변경하고 나머지 파일은 그대로 이름 유지
										saveFileName = userId + "_" + today + "_" + System.nanoTime() + "." + extension;
										converterTarget = true;
										converterTargetCount++;
									}
            					}
	        				} else {
	        					// 예외 처리
	        					log.info("@@ file.ext.invalid. extList = {}, extension = {}", uploadTypeList, fileName);
	        					result.put("errorCode", "file.ext.invalid");
	        					return result;
	        				}
            			}
            		}
            		
            		long size = 0L;
                	try ( 	InputStream inputStream = zipFile.getInputStream(entry);
                			FileOutputStream outputStream = new FileOutputStream(directoryPath + saveFileName) ) {
                		
                		int bytesRead;
                        byte[] buffer = new byte[BUFFER_SIZE];
                        while ((bytesRead = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
                            size += bytesRead;
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        
                		dataLibraryUploadFile.setFileType(FileType.FILE.name());
                		dataLibraryUploadFile.setFileExt(extension);
                		dataLibraryUploadFile.setFileName(fileName);
                		dataLibraryUploadFile.setFileRealName(saveFileName);
                		dataLibraryUploadFile.setFilePath(directoryPath);
                		dataLibraryUploadFile.setFileSubPath(subDirectoryPath);
                		dataLibraryUploadFile.setDepth(depth);
                		dataLibraryUploadFile.setFileSize(String.valueOf(size));
                		
                	} catch(IOException e) {
                		LogMessageSupport.printMessage(e, "@@@@@@@@@@@@ io exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
                		dataLibraryUploadFile.setErrorMessage(e.getMessage());
                    } catch(Exception e) {
                		LogMessageSupport.printMessage(e, "@@@@@@@@@@@@ exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
                    	dataLibraryUploadFile.setErrorMessage(e.getMessage());
                    }
                }
            	
            	dataLibraryUploadFile.setConverterTarget(converterTarget);
            	dataLibraryUploadFile.setFileSize(String.valueOf(entry.getSize()));
            	dataLibraryUploadFileList.add(dataLibraryUploadFile);
            }
		} catch(RuntimeException ex) {
			LogMessageSupport.printMessage(ex, "@@@@@@@@@@@@ RuntimeException. message = {}", ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
		} catch(IOException ex) {
			LogMessageSupport.printMessage(ex, "@@@@@@@@@@@@ IOException. message = {}", ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
		}
		
		result.put("converterTargetCount", converterTargetCount);
		result.put("dataLibraryUploadFileList", dataLibraryUploadFileList);
		return result;
	}
	
	/**
	 * @param policy
	 * @param multipartFile
	 * @return
	 */
	private static String fileValidate(Policy policy, List<String> extList, MultipartFile multipartFile) {
		
		// 2 파일 이름
		String fileName = multipartFile.getOriginalFilename();
		if(fileName == null) {
			log.info("@@ fileName is null");
			return "file.name.invalid";
		} else if(fileName.contains("..") || fileName.indexOf("/") >= 0) {
			// TODO File.seperator 정규 표현식이 안 먹혀서 이렇게 처리함
			log.info("@@ fileName = {}", fileName);
			return "file.name.invalid";
		}
		
		// 3 파일 확장자
		String[] fileNameValues = fileName.split("\\.");
//		if(fileNameValues.length != 2) {
//			log.info("@@ fileNameValues.length = {}, fileName = {}", fileNameValues.length, fileName);
//			uploadLog.setError_code("fileinfo.name.invalid");
//			return uploadLog;
//		}
//		if(fileNameValues[0].indexOf(".") >= 0 || fileNameValues[0].indexOf("..") >= 0) {
//			log.info("@@ fileNameValues[0] = {}", fileNameValues[0]);
//			uploadLog.setError_code("fileinfo.name.invalid");
//			return uploadLog;
//		}
		// LowerCase로 비교
		String extension = fileNameValues[fileNameValues.length - 1];
		
		if(!extList.contains(extension.toLowerCase())) {
			log.info("@@ extList = {}, extension = {}", extList, extension);
			return "file.ext.invalid";
		}
		
		// 4 파일 사이즈
		// TODO 파일은 사이즈가 커서 제한을 해야 할지 의문?
		long fileSize = multipartFile.getSize();
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@ user upload file size = {} KB", (fileSize / 1000));
		if( fileSize > (policy.getUserUploadMaxFilesize() * 1000000L)) {
			log.info("@@ fileSize = {}, user upload max filesize = {} M", (fileSize / 1000), policy.getUserUploadMaxFilesize());
			return "file.size.invalid";
		}
		
		return null;
	}
	
//	/**
//	 * 업로드 데이트 수정
//	 * @param request
//	 * @param uploadData
//	 * @param bindingResult
//	 * @return
//	 */
//	@PostMapping(value = "/{uploadDataId:[0-9]+}")
//	public Map<String, Object> update(HttpServletRequest request, @PathVariable Long uploadDataId, @Valid UploadData uploadData, BindingResult bindingResult) {
//		log.info("@@ uploadData = {}", uploadData);
//		Map<String, Object> result = new HashMap<>();
//		String errorCode = null;
//		String message = null;
//
//		if(bindingResult.hasErrors()) {
//			message = bindingResult.getAllErrors().get(0).getDefaultMessage();
//			log.info("@@@@@ message = {}", message);
//			result.put("statusCode", HttpStatus.BAD_REQUEST.value());
//			result.put("errorCode", errorCode);
//			result.put("message", message);
//            return result;
//		}
//
//		if(StringUtils.isEmpty(uploadData.getDataName())) {
//			errorCode = "data.name.empty";
//		}
//		if(StringUtils.isEmpty(uploadData.getDataGroupId())) {
//			errorCode = "data.group.id.empty";
//		}
//		if(StringUtils.isEmpty(uploadData.getSharing())) {
//			errorCode = "data.sharing.empty";
//		}
//		if(StringUtils.isEmpty(uploadData.getDataType())) {
//			errorCode = "data.type.empty";
//		}
//
//		// TODO citygml, indoorgml 의 경우 위도, 경도, 높이를 포함하고 있어서 validation 체크를 하지 않음
//		// 지금은 converter 가 update를 해 주지 않아서 기본 체크 함
////			if(!dataType.equals(DataType.CITYGML.getValue()) && !dataType.equals(DataType.INDOORGML.getValue())) {
//		if(uploadData.getLongitude() == null) {
//			errorCode = "data.longitude.empty";
//		}
//		if(uploadData.getLatitude() == null) {
//			errorCode = "data.latitude.empty";
//		}
//		if(uploadData.getAltitude() == null) {
//			errorCode = "data.altitude.empty";
//		}
////			}
//
//		if(!StringUtils.isEmpty(errorCode)) {
//			log.info("@@@@@ errorCode = {}", errorCode);
//			result.put("statusCode", HttpStatus.BAD_REQUEST.value());
//			result.put("errorCode", errorCode);
//			result.put("message", message);
//            return result;
//		}
//
//		uploadData.setLocation("POINT(" + uploadData.getLongitude() + " " + uploadData.getLatitude() + ")");
//		//uploadDataService.updateUploadData(uploadData);
//
//		// 원본이 gml 파일일 경우, 데이터 타입을 citygml/indoorgml로 변경할 경우에 DB를 갱신하고 업로드 된 경로의 파일 확장자를 변경.
//		// DB 갱신과 파일 확장자 변경
//		uploadDataService.updateUploadDataAndFile(uploadData);
//		int statusCode = HttpStatus.OK.value();
//
//		result.put("statusCode", statusCode);
//		result.put("errorCode", errorCode);
//		result.put("message", message);
//		return result;
//	}
//
//	/**
//	 * 선택 upload-data 삭제
//	 * @param request
//	 * @param uploadDataId
//	 * @return
//	 */
//	@DeleteMapping(value = "/{uploadDataId:[0-9]+}")
//	public Map<String, Object> deleteDatas(HttpServletRequest request, @PathVariable Long uploadDataId) {
//
//		log.info("@@@@@@@ uploadDataId = {}", uploadDataId);
//		Map<String, Object> result = new HashMap<>();
//		String errorCode = null;
//		String message = null;
//
//		UploadData uploadData = new UploadData();
//		//uploadData.setUserId(userSession.getUserId());
//		uploadData.setUploadDataId(uploadDataId);
//
//		uploadDataService.deleteUploadData(uploadData);
//		int statusCode = HttpStatus.OK.value();
//
//		result.put("statusCode", statusCode);
//		result.put("errorCode", errorCode);
//		result.put("message", message);
//		return result;
//	}
	
	/**
	 * validation 체크
	 * @param request
	 * @return
	 */
	private String dataLibraryValidate(MultipartHttpServletRequest request) {
		if(StringUtils.isEmpty(request.getParameter("dataLibraryName"))) {
			return "data.library.name.empty";
		}
		if(StringUtils.isEmpty(request.getParameter("dataLibraryGroupId"))) {
			return "data.library.group.id.empty";
		}
		if(StringUtils.isEmpty(request.getParameter("sharing"))) {
			return "data.sharing.empty";
		}
		
		String dataType = request.getParameter("dataType");
		if(StringUtils.isEmpty(dataType)) {
			return "data.type.empty";
		}
		
		Map<String, MultipartFile> fileMap = request.getFileMap();
		if(fileMap.isEmpty()) {
			return "data.file.empty";
		}
		
		return null;
	}
}
