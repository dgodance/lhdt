package lhdt.api;

import lhdt.domain.data.DataAttribute;
import lhdt.domain.data.DataInfo;
import lhdt.domain.data.DataObjectAttribute;
import lhdt.service.DataAttributeService;
import lhdt.service.DataObjectAttributeService;
import lhdt.service.DataService;
import lhdt.support.LogMessageSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Data API
 * @author jeongdae
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/datas")
public class DataAPIController implements APIController {
	
	@Autowired
	private DataService dataService;
	
	@Autowired
	private DataAttributeService dataAttributeService;
	
	@Autowired
	private DataObjectAttributeService dataObjectAttributeService;
	
	/**
	 * 데이터 상세 정보
	 * @param dataId
	 * @return
	 */
	@GetMapping("/{dataId:[0-9]+}")
	public Map<String, Object> detail(HttpServletRequest request, @PathVariable Long dataId) {
		log.info("@@@ dataId = {}", dataId);
		
		Map<String, Object> result = new HashMap<>();
		int statusCode = 0;
		String errorCode = null;
		String message = null;
		try {
			DataInfo dataInfo = new DataInfo();
			//dataInfo.setUserId(userSession.getUserId());
			dataInfo.setDataId(dataId);
			dataInfo = dataService.getData(dataInfo);
			statusCode = HttpStatus.OK.value();
			
			result.put("dataInfo", dataInfo);
		} catch(DataAccessException e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "db.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			LogMessageSupport.printMessage(e, "@@ db.exception. message = {}", message);
		} catch(RuntimeException e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "runtime.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			LogMessageSupport.printMessage(e, "@@ runtime.exception. message = {}", message);
		} catch(Exception e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "unknown.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			LogMessageSupport.printMessage(e, "@@ exception. message = {}", message);
		}
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		
		return result;
	}
	
	/**
	 * 데이터 속성 정보
	 * @param dataId
	 * @return
	 */
	@GetMapping("/attributes/{dataId:[0-9]+}")
	public Map<String, Object> detailAttribute(HttpServletRequest request, @PathVariable Long dataId) {
		log.info("@@@@@ dataId = {}", dataId);
		
		Map<String, Object> result = new HashMap<>();
		int statusCode = 0;
		String errorCode = null;
		String message = null;
		
		try {
			DataAttribute dataAttribute = dataAttributeService.getDataAttribute(dataId);
			statusCode = HttpStatus.OK.value();
			
			result.put("dataAttribute", dataAttribute);
		} catch(DataAccessException e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "db.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			LogMessageSupport.printMessage(e, "@@ db.exception. message = {}", message);
		} catch(RuntimeException e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "runtime.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			LogMessageSupport.printMessage(e, "@@ runtime.exception. message = {}", message);
		} catch(Exception e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "unknown.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			LogMessageSupport.printMessage(e, "@@ exception. message = {}", message);
		}
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		
		return result;
	}
	
	/**
	 * 데이터 속성 정보
	 * @param dataId
	 * @return
	 */
	@GetMapping("/object/attributes/{dataId:[0-9]+}")
	public Map<String, Object> detailObjectAttribute(HttpServletRequest request, @PathVariable Long dataId) {
		log.info("@@@@@ dataId = {}", dataId);
		
		Map<String, Object> result = new HashMap<>();
		int statusCode = 0;
		String errorCode = null;
		String message = null;
		
		try {
			DataObjectAttribute dataObjectAttribute = dataObjectAttributeService.getDataObjectAttribute(dataId);
			statusCode = HttpStatus.OK.value();
			
			result.put("dataObjectAttribute", dataObjectAttribute);
		} catch(DataAccessException e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "db.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			LogMessageSupport.printMessage(e, "@@ db.exception. message = {}", message);
		} catch(RuntimeException e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "runtime.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			LogMessageSupport.printMessage(e, "@@ runtime.exception. message = {}", message);
		} catch(Exception e) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			errorCode = "unknown.exception";
			message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			LogMessageSupport.printMessage(e, "@@ exception. message = {}", message);
		}
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		
		return result;
	}

}