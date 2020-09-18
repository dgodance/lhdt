/**
 * 
 */
package lhdt.admin.svc.common;

import javax.servlet.http.HttpServletRequest;

import lhdt.admin.svc.lhdt.domain.UserSession;
import lhdt.cmmn.misc.CmmnConst;
import lhdt.cmmn.misc.CmmnUtils;
import lhdt.cmmn.misc.PpWebSessionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 유틸리티
 * @author gravity@daumsoft.com
 * @since 2020. 8. 18.
 *
 */
@Slf4j
public class AdminSvcUtils extends CmmnUtils {
	
	
	/**
	 * 세션에서 usersession정보 추출
	 * @param request
	 * @return
	 */
	public static UserSession getUserSession(HttpServletRequest request) {
		Object obj = PpWebSessionUtil.get(request, CmmnConst.USER_SESSION);
		
		if(null == obj) {
			log.warn("<<.getUserSession - null userSession");
			return null;
		}
		
		//
		return (UserSession)obj;
	}

}
