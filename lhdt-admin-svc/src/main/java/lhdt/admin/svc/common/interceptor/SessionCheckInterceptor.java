/**
 * 
 */
package lhdt.admin.svc.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.hyunlab.web.util.PpWebSessionUtil;
import lhdt.admin.svc.lhdt.domain.UserSession;
import lhdt.ds.common.interceptor.DsSessionCheckInterceptor;
import lhdt.ds.common.misc.DsConst;
import lombok.extern.slf4j.Slf4j;

/**
 * 세션검사
 * @author gravity@daumsoft.com
 * @since 2020. 8. 14.
 *
 */
@Slf4j
@Component(value="sessionCheckInterceptor")
public class SessionCheckInterceptor extends DsSessionCheckInterceptor {

	@Value("#{servletContext.contextPath}")
	private String contextPath;
	
	@Value("${app.login.uri}")
	private String loginUrl;

	/**
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)	throws Exception {
		//
//		log.debug("{} {}", contextPath, request.getRequestURI());
		
		//
		UserSession userSession = (UserSession) PpWebSessionUtil.get(request, DsConst.USER_SESSION);
		
		//로그인 정보 없음
		if(null == userSession) {
			response.sendRedirect(contextPath + loginUrl);
			return false;
		}

		//
		return true;
	}
}
