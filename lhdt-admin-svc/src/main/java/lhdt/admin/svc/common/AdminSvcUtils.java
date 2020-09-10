/**
 * 
 */
package lhdt.admin.svc.common;

import javax.servlet.http.HttpServletRequest;

import dev.hyunlab.web.util.PpWebSessionUtil;
import lhdt.admin.svc.lhdt.domain.UserSession;
import lhdt.ds.common.misc.DsConst;
import lhdt.ds.common.misc.DsUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 유틸리티
 * @author gravity@daumsoft.com
 * @since 2020. 8. 18.
 *
 */
@Slf4j
public class AdminSvcUtils extends DsUtils {
	
	
	/**
	 * 세션에서 usersession정보 추출
	 * @param request
	 * @return
	 */
	public static UserSession getUserSession(HttpServletRequest request) {
		Object obj = PpWebSessionUtil.get(request, DsConst.USER_SESSION);
		
		if(null == obj) {
			log.warn("<<.getUserSession - null userSession");
			return null;
		}
		
		//
		return (UserSession)obj;
	}

}
