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
@RequestMapping("/data-library-converters")
public class DataLibraryConverterRestController {
	
	// 파일 copy 시 버퍼 사이즈
	public static final int BUFFER_SIZE = 8192;
	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private PropertiesConfig propertiesConfig;
	
	@Autowired
	private DataLibraryService dataLibraryService;

//	/**
//	 * TODO 우선은 여기서 적당히 구현해 두고... 나중에 좀 깊이 생각해 보자. converter에 어디까지 넘겨야 할지
//	 * @param request
//	 * @param dataLibraryConverterJob
//	 * @return
//	 */
//	@PostMapping(value = "/converter")
//	public Map<String, Object> converter(HttpServletRequest request, DataLibraryConverterJob dataLibraryConverterJob) {
//		log.info("@@@ dataLibraryConverterJob = {}", dataLibraryConverterJob);
//
//		Map<String, Object> result = new HashMap<>();
//		String errorCode = null;
//		String message = null;
//
//		if(dataLibraryConverterJob.getConverterCheckIds().length() <= 0) {
//			log.info("@@@@@ message = {}", message);
//			result.put("statusCode", HttpStatus.BAD_REQUEST.value());
//			result.put("errorCode", "check.value.required");
//			result.put("message", message);
//			return result;
//		}
//		if(StringUtils.isEmpty(dataLibraryConverterJob.getTitle())) {
//			result.put("statusCode", HttpStatus.BAD_REQUEST.value());
//			result.put("errorCode", "converter.title.empty");
//			result.put("message", message);
//			return result;
//		}
//		if(dataLibraryConverterJob.getUsf() == null) {
//			result.put("statusCode", HttpStatus.BAD_REQUEST.value());
//			result.put("errorCode", "converter.usf.empty");
//			result.put("message", message);
//			return result;
//		}
//
//		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
//		dataLibraryConverterJob.setUserId(userSession.getUserId());
//
//		converterService.insertConverter(dataLibraryConverterJob);
//		int statusCode = HttpStatus.OK.value();
//
//		result.put("statusCode", statusCode);
//		result.put("errorCode", errorCode);
//		result.put("message", message);
//		return result;
//	}
}
