package lhdt.controller.rest;


import lhdt.config.PropertiesConfig;
import lhdt.controller.AuthorizationController;
import lhdt.domain.FileType;
import lhdt.domain.Key;
import lhdt.domain.UploadDirectoryType;
import lhdt.domain.board.Board;
import lhdt.domain.board.BoardNoticeFile;
import lhdt.domain.policy.Policy;
import lhdt.domain.uploaddata.UploadData;
import lhdt.domain.uploaddata.UploadDataFile;
import lhdt.domain.user.UserSession;
import lhdt.service.BoardService;
import lhdt.service.PolicyService;
import lhdt.utils.DateUtils;
import lhdt.utils.FileUtils;
import lhdt.utils.FormatUtils;
import lhdt.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardRestController implements AuthorizationController {

	@Autowired
    private BoardService boardService;
	@Autowired
    private PolicyService policyService;
    @Autowired
    private PropertiesConfig propertiesConfig;

    // 파일 copy 시 버퍼 사이즈
    public static final int BUFFER_SIZE = 8192;

    
    
    /**
	 * 게시물 삭제 삭제
	 * @param boardNoticeId
	 * @return
	 */
	@DeleteMapping(value = "/delete/{boardNoticeId:[0-9]+}")
	public Map<String, Object> delete(@PathVariable Long boardNoticeId) {
		Map<String, Object> result = new HashMap<>();
		String errorCode = null;
		String message = null;
		
		boardService.deleteBoard(boardNoticeId);
		int statusCode = HttpStatus.OK.value();

		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}
	
	/*
	 * @PostMapping(value = "/update-board") public Map<String, Object>
	 * updateBoard(HttpServletRequest request, Board board) throws Exception {
	 * Map<String, Object> result = new HashMap<>(); String errorCode = null; String
	 * message = null;
	 * 
	 * UserSession userSession = (UserSession)
	 * request.getSession().getAttribute(Key.USER_SESSION.name()); String userId =
	 * userSession.getUserId(); board.setUserId(userId);
	 * boardService.updateBoard(board);
	 * 
	 * int statusCode = HttpStatus.OK.value();
	 * 
	 * result.put("statusCode", statusCode); result.put("errorCode", errorCode);
	 * result.put("message", message); return result; }
	 */
	
	/**
	 * shape 파일 변환 TODO dropzone 이 파일 갯수만큼 form data를 전송해 버려서 command 패턴을(Layer
	 * layer) 사용할 수 없음 dropzone 이 예외 처리가 이상해서 BAD_REQUEST 를 던지지 않고 OK 를 넣짐
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/insert-board")
public Map<String, Object> insert(MultipartHttpServletRequest request) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		String errorCode = null;
		String message = null;
		
		Board board = new Board();
		board.setContents(request.getParameter("contents"));
		board.setTitle(request.getParameter("title"));
		board.setStart_date(request.getParameter("start_date"));
		board.setStart_day(request.getParameter("start_day"));
		board.setStart_hour(request.getParameter("start_hour"));
		board.setStart_minute(request.getParameter("start_minute"));
		board.setEnd_date(request.getParameter("end_date"));
		board.setEnd_day(request.getParameter("end_day"));
		board.setEnd_hour(request.getParameter("end_hour"));
		board.setEnd_minute(request.getParameter("end_minute"));
		board.setUseYn(request.getParameter("use_yn"));
		
		UserSession userSession = (UserSession) request.getSession().getAttribute(Key.USER_SESSION.name());
		board.setUserId(userSession.getUserId());
		board.setUserName(userSession.getUserName());
		String client_ip = WebUtils.getClientIp(request);
		board.setClientIp(client_ip);
		log.info("@@@ board = {}", board);
		
		Map<String, MultipartFile> fileMap = request.getFileMap();
		int fileCount = fileMap.values().size();
		List<BoardNoticeFile> boardNoticeFileList = new ArrayList<>();
		
		boolean fileExist = false;
		
		//
		
		if(fileCount>0) {
			fileExist = true;
			
		}else {
			boardService.insertBoard(board, boardNoticeFileList, fileExist);
			int statusCode = HttpStatus.OK.value();
			result.put("statusCode", statusCode);
			return result;
		}
		
		//
		
		Policy policy = policyService.getPolicy();
		// 여긴 null 체크를 안 하는게 맞음. 없음 장애가 나야 함
		// 업로딩 가능한 파일 타입
		String[] uploadTypes = policy.getUserUploadType().toLowerCase().split(",");
		// 변환 가능한 파일 타입
		String[] converterTypes = policy.getUserConverterType().split(",");
		List<String> uploadTypeList = Arrays.asList(uploadTypes);
		List<String> converterTypeList = Arrays.asList(converterTypes);
		log.info("@@@@@@@@@@@@ converterTypes = {}", converterTypeList);
		if(!StringUtils.isEmpty(errorCode)) return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
		
		String userId = userSession.getUserId();
		
		
		Map<String, Object> uploadMap = null;
		String today = DateUtils.getToday(FormatUtils.YEAR_MONTH_DAY_TIME14);
		
		// 1 directory 생성
		String makedDirectory = FileUtils.makeDirectory(userId, UploadDirectoryType.YEAR_MONTH, propertiesConfig.getDataUploadDir());
		log.info("@@@@@@@ = {}", makedDirectory);
		
		// 2 한건이면서 zip 의 경우
		boolean isZipFile = false;
		
		
		if(fileCount == 1) {
			// processAsync(policy, userId, fileMap, makedDirectory);
			for (MultipartFile multipartFile : fileMap.values()) {
				String[] divideNames = multipartFile.getOriginalFilename().split("\\.");
				String fileExtension = divideNames[divideNames.length - 1];
				if(UploadData.ZIP_EXTENSION.equalsIgnoreCase(fileExtension)) {
					isZipFile = true;
					// zip 파일
					uploadMap = unzip(policy, uploadTypeList, converterTypeList, today, userId, multipartFile, makedDirectory);
					log.info("@@@@@@@ uploadMap = {}", uploadMap);
					
					// validation 체크
					if(uploadMap.containsKey("errorCode")) {
						errorCode = (String)uploadMap.get("errorCode");
						return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
					}
					
					boardNoticeFileList = (List<BoardNoticeFile>)uploadMap.get("uploadDataFileList");
				}
			}
		}
		
		if(!isZipFile) {
			// zip 파일이 아니면 기본적으로 한 폴더에 넣어야 함
			
			String tempDirectory = userId + "_" + System.nanoTime();
			// 파일을 upload 디렉토리로 복사
			FileUtils.makeDirectory(makedDirectory + tempDirectory);
			// 3 그 외의 경우는 재귀적으로 파일 복사
			
			
			int fileIndex = 0;
			
			for (MultipartFile multipartFile : fileMap.values()) {

				fileIndex++;
				
				log.info("@@@@@@@@@@@@@@@ name = {}, originalName = {}", multipartFile.getName(), multipartFile.getOriginalFilename());
				
				// 파일 기본 validation 체크
				errorCode = fileValidate(policy, uploadTypeList, multipartFile);
				if(!StringUtils.isEmpty(errorCode)) {
					return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
				}
				
				String originalName = multipartFile.getOriginalFilename();
				String[] divideFileName = originalName.split("\\.");
    			String saveFileName = originalName;
    			String extension = divideFileName[divideFileName.length - 1];
    			
					saveFileName = userId + "_" + today + "_" + System.nanoTime() + "." + extension;
				
				long size = 0L;
				
				try (	InputStream inputStream = multipartFile.getInputStream();
						OutputStream outputStream = new FileOutputStream(makedDirectory + tempDirectory + File.separator + saveFileName)) {
					
					int bytesRead;
					byte[] buffer = new byte[BUFFER_SIZE];
					while ((bytesRead = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
						size += bytesRead;
						outputStream.write(buffer, 0, bytesRead);
					}
					BoardNoticeFile boardNoticeFile = new BoardNoticeFile();
					boardNoticeFile.setFileExt(extension);
					boardNoticeFile.setFileName(multipartFile.getOriginalFilename());
					boardNoticeFile.setFileRealName(saveFileName);
					boardNoticeFile.setFilePath(makedDirectory + tempDirectory + File.separator);
					boardNoticeFile.setFileSubPath(tempDirectory);
					boardNoticeFile.setFileSize(String.valueOf(size));
					
					boardNoticeFileList.add(boardNoticeFile);
				
					if(fileCount == fileIndex) {
						boardService.insertBoard(board, boardNoticeFileList, fileExist);
					}
					
				} catch(IOException e) {
					log.info("@@@@@@@@@@@@ io exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
					FileUtils.deleteFileReculsive(makedDirectory + tempDirectory);
					return getResultMap(result, HttpStatus.INTERNAL_SERVER_ERROR.value(), "io.exception", message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
				} catch(Exception e) {
					log.info("@@@@@@@@@@@@ file copy exception.");
					FileUtils.deleteFileReculsive(makedDirectory + tempDirectory);
					return getResultMap(result, HttpStatus.INTERNAL_SERVER_ERROR.value(), "file.copy.exception", message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
				} 
				
			}
		}
						
		int statusCode = HttpStatus.OK.value();
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/update-board")
public Map<String, Object> upadte(MultipartHttpServletRequest request) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		String errorCode = null;
		String message = null;
		
		Board board = new Board();

		board.setBoardNoticeId(Long.valueOf(request.getParameter("boardNoticeId")));
		board.setContents(request.getParameter("contents"));
		board.setTitle(request.getParameter("title"));
		board.setStart_date(request.getParameter("start_date"));
		board.setStart_day(request.getParameter("start_day"));
		board.setStart_hour(request.getParameter("start_hour"));
		board.setStart_minute(request.getParameter("start_minute"));
		board.setEnd_date(request.getParameter("end_date"));
		board.setEnd_day(request.getParameter("end_day"));
		board.setEnd_hour(request.getParameter("end_hour"));
		board.setEnd_minute(request.getParameter("end_minute"));
		board.setUseYn(request.getParameter("use_yn"));
		
		UserSession userSession = (UserSession) request.getSession().getAttribute(Key.USER_SESSION.name());
		board.setUserId(userSession.getUserId());
		board.setUserName(userSession.getUserName());
		String client_ip = WebUtils.getClientIp(request);
		board.setClientIp(client_ip);
		log.info("@@@ board = {}", board);
		
		Map<String, MultipartFile> fileMap = request.getFileMap();
		int fileCount = fileMap.values().size();
		List<BoardNoticeFile> boardNoticeFileList = new ArrayList<>();
		
		boolean fileExist = false;
		if(fileCount>0) {
			fileExist = true;
			
		}else {
			boardService.updateBoard(board, boardNoticeFileList, fileExist);
			int statusCode = HttpStatus.OK.value();
			result.put("statusCode", statusCode);
			return result;
		}
		
		Policy policy = policyService.getPolicy();
		// 여긴 null 체크를 안 하는게 맞음. 없음 장애가 나야 함
		// 업로딩 가능한 파일 타입
		String[] uploadTypes = policy.getUserUploadType().toLowerCase().split(",");
		// 변환 가능한 파일 타입
		String[] converterTypes = policy.getUserConverterType().split(",");
		List<String> uploadTypeList = Arrays.asList(uploadTypes);
		List<String> converterTypeList = Arrays.asList(converterTypes);
		log.info("@@@@@@@@@@@@ converterTypes = {}", converterTypeList);
		if(!StringUtils.isEmpty(errorCode)) return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
		
		String userId = userSession.getUserId();
		
		Map<String, Object> uploadMap = null;
		String today = DateUtils.getToday(FormatUtils.YEAR_MONTH_DAY_TIME14);
		
		// 1 directory 생성
		String makedDirectory = FileUtils.makeDirectory(userId, UploadDirectoryType.YEAR_MONTH, propertiesConfig.getDataUploadDir());
		log.info("@@@@@@@ = {}", makedDirectory);
		
		// 2 한건이면서 zip 의 경우
		boolean isZipFile = false;
		
		
		if(fileCount == 1) {
			// processAsync(policy, userId, fileMap, makedDirectory);
			for (MultipartFile multipartFile : fileMap.values()) {
				String[] divideNames = multipartFile.getOriginalFilename().split("\\.");
				String fileExtension = divideNames[divideNames.length - 1];
				if(UploadData.ZIP_EXTENSION.equalsIgnoreCase(fileExtension)) {
					isZipFile = true;
					// zip 파일
					uploadMap = unzip(policy, uploadTypeList, converterTypeList, today, userId, multipartFile, makedDirectory);
					log.info("@@@@@@@ uploadMap = {}", uploadMap);
					
					// validation 체크
					if(uploadMap.containsKey("errorCode")) {
						errorCode = (String)uploadMap.get("errorCode");
						return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
					}
					
					boardNoticeFileList = (List<BoardNoticeFile>)uploadMap.get("uploadDataFileList");
				}
			}
		}
		
		if(!isZipFile) {
			// zip 파일이 아니면 기본적으로 한 폴더에 넣어야 함
			
			String tempDirectory = userId + "_" + System.nanoTime();
			// 파일을 upload 디렉토리로 복사
			FileUtils.makeDirectory(makedDirectory + tempDirectory);
			// 3 그 외의 경우는 재귀적으로 파일 복사
			int fileIndex = 0;
			for (MultipartFile multipartFile : fileMap.values()) {
				fileIndex++;
				log.info("@@@@@@@@@@@@@@@ name = {}, originalName = {}", multipartFile.getName(), multipartFile.getOriginalFilename());
				
				// 파일 기본 validation 체크
				errorCode = fileValidate(policy, uploadTypeList, multipartFile);
				if(!StringUtils.isEmpty(errorCode)) {
					return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
				}
				
				String originalName = multipartFile.getOriginalFilename();
				String[] divideFileName = originalName.split("\\.");
    			String saveFileName = originalName;
    			String extension = divideFileName[divideFileName.length - 1];
				
					saveFileName = userId + "_" + today + "_" + System.nanoTime() + "." + extension;
					
				long size = 0L;
				
				try (	InputStream inputStream = multipartFile.getInputStream();
						OutputStream outputStream = new FileOutputStream(makedDirectory + tempDirectory + File.separator + saveFileName)) {
					
					int bytesRead;
					byte[] buffer = new byte[BUFFER_SIZE];
					while ((bytesRead = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
						size += bytesRead;
						outputStream.write(buffer, 0, bytesRead);
					}
					BoardNoticeFile boardNoticeFile = new BoardNoticeFile();
					boardNoticeFile.setFileExt(extension);
					boardNoticeFile.setFileName(multipartFile.getOriginalFilename());
					boardNoticeFile.setFileRealName(saveFileName);
					boardNoticeFile.setFilePath(makedDirectory + tempDirectory + File.separator);
					boardNoticeFile.setFileSubPath(tempDirectory);
					boardNoticeFile.setFileSize(String.valueOf(size));
					
					boardNoticeFileList.add(boardNoticeFile);
					
					if(fileCount == fileIndex)
						boardService.updateBoard(board, boardNoticeFileList, fileExist);
					
				} catch(IOException e) {
					log.info("@@@@@@@@@@@@ io exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
					return getResultMap(result, HttpStatus.INTERNAL_SERVER_ERROR.value(), "io.exception", message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
				} catch(Exception e) {
					log.info("@@@@@@@@@@@@ file copy exception.");
					FileUtils.deleteFileReculsive(makedDirectory + tempDirectory);
					return getResultMap(result, HttpStatus.INTERNAL_SERVER_ERROR.value(), "file.copy.exception", message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
				}
				
			}
		}
		
		Long boardId = Long.getLong(request.getParameter("boardId"));
		
		log.info("@@@@@@@@@@@@ boardId = {}", boardId);
		//boardService.insertFile(boardId, boardNoticeFileList);    
		
		int statusCode = HttpStatus.OK.value();
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}
	
	
	    
	    private Map<String, Object> getResultMap(Map<String, Object> result, int statusCode, String errorCode, String message) {
			log.info("@@@@@@@@@@@@ errorCode = {}", errorCode);
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
											String targetDirectory
											) throws Exception {
			
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

			List<UploadDataFile> uploadDataFileList = new ArrayList<>();
			// zip 파일을 압축할때 한글이나 다국어가 포함된 경우 java.lang.IllegalArgumentException: malformed input off 같은 오류가 발생. 윈도우가 CP949 인코딩으로 파일명을 저장하기 때문.
			// Charset CP949 = Charset.forName("UTF-8");
//			try ( ZipFile zipFile = new ZipFile(uploadedFile, CP949);) {
			try ( ZipFile zipFile = new ZipFile(uploadedFile)) {
				String directoryPath = targetDirectory;
				String subDirectoryPath = "";
				String directoryName = null;
				int depth = 1;
				Enumeration<? extends ZipEntry> entries = zipFile.entries();
				
				while( entries.hasMoreElements() ) {
	            	UploadDataFile uploadDataFile = new UploadDataFile();
	            	
	            	ZipEntry entry = entries.nextElement();
	            	String unzipfileName = targetDirectory + entry.getName();
	            	boolean converterTarget = false;
	            	
	            	if( entry.isDirectory() ) {
	            		// 디렉토리인 경우
	            		uploadDataFile.setFileType(FileType.DIRECTORY.name());
	            		if(directoryName == null) {
	            			uploadDataFile.setFileName(entry.getName());
	            			uploadDataFile.setFileRealName(entry.getName());
	            			directoryName = entry.getName();
	            			directoryPath = directoryPath + directoryName;
	            			//subDirectoryPath = directoryName;
	            		} else {
	            			String fileName;
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
	            			uploadDataFile.setFileName(fileName);
	            			uploadDataFile.setFileRealName(fileName);
	            			directoryName = fileName;
	            			directoryPath = directoryPath + fileName;
	            			subDirectoryPath = fileName;
	            		}
	            		
	                	File file = new File(unzipfileName);
	                    file.mkdirs();
	                    uploadDataFile.setFilePath(directoryPath);
	                    uploadDataFile.setFileSubPath(subDirectoryPath);
	                    uploadDataFile.setDepth(depth);
	                    depth++;
	            	} else {
	            		// 파일인 경우
	            		String fileName;
	            		String extension = null;
	            		String[] divideFileName;
	            		String saveFileName;
	            		
	            		// TODO zip 파일도 확장자 validation 체크를 해야 함
	            		if(directoryName == null) {
	            			fileName = entry.getName();
	            			divideFileName = fileName.split("\\.");
	            			saveFileName = fileName;
	            			if(divideFileName.length != 0) {
	            				extension = divideFileName[divideFileName.length - 1];
	            				if(uploadTypeList.contains(extension.toLowerCase())) {

	            					if(converterTypeList.contains(extension.toLowerCase())) {

	            						// 변환 대상 파일만 이름을 변경하고 나머지 파일은 그대로 이름 유지
	            						saveFileName = userId + "_" + today + "_" + System.nanoTime() + "." + extension;
	            						converterTarget = true;
										converterTargetCount++;
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
	            						
	            						// 변환 대상 파일만 이름을 변경하고 나머지 파일은 그대로 이름 유지
	            						saveFileName = userId + "_" + today + "_" + System.nanoTime() + "." + extension;
										converterTarget = true;
										converterTargetCount++;
	            					}
		        				} else {
		        					// 예외 처리
		        					log.info("@@ file.ext.invalid. extList = {}, extension = {}", uploadTypeList, fileName);
		        					result.put("errorCode", "file.ext.invalid");
		        					return result;
		        				}
	            			}
	            		}
	            		uploadDataFile = fileCopyInUnzip(uploadDataFile, zipFile, entry, directoryPath, saveFileName, extension, fileName, subDirectoryPath, depth);
	                }

	            	uploadDataFile.setConverterTarget(converterTarget);
	            	uploadDataFile.setFileSize(String.valueOf(entry.getSize()));
	            	uploadDataFileList.add(uploadDataFile);
	            }
			} catch(RuntimeException ex) {
				log.info("@@@@@@@@@@@@ RuntimeException. message = {}", ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
			} catch(IOException ex) {
				log.info("@@@@@@@@@@@@ IOException. message = {}", ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
			}

			result.put("converterTargetCount", converterTargetCount);
			result.put("uploadDataFileList", uploadDataFileList);
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
//			if(fileNameValues.length != 2) {
//				log.info("@@ fileNameValues.length = {}, fileName = {}", fileNameValues.length, fileName);
//				uploadLog.setError_code("fileinfo.name.invalid");
//				return uploadLog;
//			}
//			if(fileNameValues[0].indexOf(".") >= 0 || fileNameValues[0].indexOf("..") >= 0) {
//				log.info("@@ fileNameValues[0] = {}", fileNameValues[0]);
//				uploadLog.setError_code("fileinfo.name.invalid");
//				return uploadLog;
//			}
			// LowerCase로 비교
			String extension = fileNameValues[fileNameValues.length - 1];
			
			/*
			 * if(!extList.contains(extension.toLowerCase())) {
			 * log.info("@@ extList = {}, extension = {}", extList, extension); return
			 * "file.ext.invalid"; }
			 */
			
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
		
		/*
		 * unzip 로직 안에서 파일 복사
		 */
		private UploadDataFile fileCopyInUnzip(UploadDataFile uploadDataFile, ZipFile zipFile, ZipEntry entry, String directoryPath, String saveFileName,
										String extension, String fileName, String subDirectoryPath, int depth) {
			long size = 0L;
	    	try ( 	InputStream inputStream = zipFile.getInputStream(entry);
	    			FileOutputStream outputStream = new FileOutputStream(directoryPath + saveFileName); ) {

	    		int bytesRead = 0;
	            byte[] buffer = new byte[BUFFER_SIZE];
	            while ((bytesRead = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
	                size += bytesRead;
	                outputStream.write(buffer, 0, bytesRead);
	            }

	    		uploadDataFile.setFileType(FileType.FILE.name());
	    		uploadDataFile.setFileExt(extension);
	    		uploadDataFile.setFileName(fileName);
	    		uploadDataFile.setFileRealName(saveFileName);
	    		uploadDataFile.setFilePath(directoryPath);
	    		uploadDataFile.setFileSubPath(subDirectoryPath);
	    		uploadDataFile.setDepth(depth);
	    		uploadDataFile.setFileSize(String.valueOf(size));

	    	} catch(IOException e) {
	    		e.printStackTrace();
	    		log.info("@@@@@@@@@@@@ io exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
	    		uploadDataFile.setErrorMessage(e.getMessage());
	        } catch(Exception e) {
	        	e.printStackTrace();
	        	log.info("@@@@@@@@@@@@ exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
	        	uploadDataFile.setErrorMessage(e.getMessage());
	        }

	    	return uploadDataFile;
		}
}


