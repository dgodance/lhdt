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
@RequestMapping("/data-librarys")
public class DataLibraryRestController {
	
	// 파일 copy 시 버퍼 사이즈
	public static final int BUFFER_SIZE = 8192;
	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private PropertiesConfig propertiesConfig;
	
	@Autowired
	private DataLibraryService dataLibraryService;


}
