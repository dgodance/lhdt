/**
 * 
 */
package lhdt.svc.common;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lhdt.cmmn.misc.CmmnConst;
import lhdt.cmmn.misc.CmmnController;
import lhdt.svc.common.fileinfo.service.FileInfoService;
import lhdt.svc.lhdt.domain.UserSession;
import lombok.extern.slf4j.Slf4j;

/**
 * 모든 controller  의 부모
 * @author gravity
 *
 */
@Component
@Slf4j
public class SvcController extends CmmnController{
	@Autowired
	protected FileInfoService fileInfoService;

	/**
	 * 유저 세션을 추출합니다
	 * @param request
	 * @return
	 */
	protected UserSession getUserSession(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(CmmnConst.USER_SESSION);
		if(null == obj) {
			log.warn("<<.getUserSession - null userSession");
			return null;
		}
		
		//
		return (UserSession)obj;
	}

	/**
	 * 세션에 저장된 사용자 정보에서 userId 추출
	 * @param request
	 * @return
	 */
	protected String getUserId(HttpServletRequest request) {
		Object obj = getUserSession(request);
		if(null == obj) {
			log.warn("<<.getUserId - null userSession");
			return null;
		}
		
		//
		return ((UserSession)obj).getUserId();
	}
	
	/**
	 * 세션에 저장된 사용자 정보에서 userName 추출
	 * @param request
	 * @return
	 */
	protected String getUserName(HttpServletRequest request) {
		Object obj = getUserSession(request);
		if(null == obj) {
			log.warn("<<.getUserName - null userSession");
			return null;
		}
		
		//
		return ((UserSession)obj).getUserName();
	}
}
