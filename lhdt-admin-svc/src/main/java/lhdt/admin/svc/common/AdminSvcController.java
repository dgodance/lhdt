/**
 * 
 */
package lhdt.admin.svc.common;

import javax.servlet.http.HttpServletRequest;

import lhdt.admin.svc.lhdt.domain.UserSession;
import lhdt.ds.common.misc.DsConst;
import lhdt.ds.common.misc.DsController;
import lombok.extern.slf4j.Slf4j;

/**
 * admin svc controller의 부모
 * @author gravity
 * @since 2020. 8. 18.
 *
 */
@Slf4j
public class AdminSvcController extends DsController {
	
	
	protected UserSession getUserSession(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(DsConst.USER_SESSION);
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
