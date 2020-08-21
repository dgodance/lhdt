/**
 * 
 */
package lhdt.ds.common.interceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lhdt.ds.common.misc.DsConst;
import lhdt.ds.common.misc.DsSessionUtils;
import lhdt.ds.common.misc.DsUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * csrf 검사 interceptor
 * @author gravity@daumsoft.com
 * @since	20200821	init
 */
@Slf4j
@Component
public class DsCsrfInterceptor extends HandlerInterceptorAdapter {
	
	@PostConstruct
	private void init() {
		log.info("<< {}", this);
	}


	/**
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)	throws Exception {
		
		//post일때만 검사
		if(!"POST".equalsIgnoreCase(request.getMethod())){
			return true;
		}
		
		//
		String csrfSession = DsSessionUtils.getAsString(request, DsConst.CSRF_TOKEN);
		//
		String csrfRequest = request.getParameter(DsConst.CSRF_TOKEN);
		
		
		//둘다 값이 없으면
		if(DsUtils.isEmpty(csrfRequest) && DsUtils.isEmpty(csrfSession)) {
			log.debug("<<.preHandler - empty csrf token");
			return true;
		}
		
		
		//둘의 값이 같으면
		if(csrfRequest.equals(csrfSession)) {
			DsSessionUtils.remove(request, DsConst.CSRF_TOKEN);
			return true;
		}
		
		
		//값이 같지 않으면
		log.error("<<.preHandler - request:{} vs session:{} key:{}", csrfRequest, csrfSession, DsConst.CSRF_TOKEN);
		
		
		return false;
	}

	/**
	 * modelAndView에 DsConst.CSRF_TOKEN이라는 키가 존재하면 같은 값을 세션에 저장
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,	@Nullable ModelAndView modelAndView) throws Exception {

		//
		if(null == modelAndView) {
			return;
		}
		
		//
		boolean b = modelAndView.getModel().containsKey(DsConst.CSRF_TOKEN);
		if(!b) {
			return;
		}
		
		//
		String csrfToken = (String) modelAndView.getModel().get(DsConst.CSRF_TOKEN);
		DsSessionUtils.set(request, DsConst.CSRF_TOKEN, csrfToken);
	
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,	@Nullable Exception ex) throws Exception {
	}
	
	
}
