/**
 * 
 */
package lhdt.cmmn.interceptor;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

/**
 * 기타등등 interceptor
 * @author gravity@daumsoft.com
 *
 */
@Slf4j
@Component(value = "cmmnMiscInterceptor")
public class CmmnMiscInterceptor extends HandlerInterceptorAdapter {
	
	@PostConstruct
	private void init() {
		log.info("<< {}", this);
	}


	/**
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)	throws Exception {
		request.setAttribute("beginDt", new Date());
		
		return true;
	}

	/**
	 * 
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,	@Nullable ModelAndView modelAndView) throws Exception {
		Date beginDt = (Date) request.getAttribute("beginDt");
		Date endDt = new Date();
		
		//
//		log.debug("<< DURATION:{}ms	mem(MB):{}/{}"
//				, (endDt.getTime() - beginDt.getTime())
//				, CmmnUtils.getFreeMemory(), CmmnUtils.getTotalMemory());
		
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,	@Nullable Exception ex) throws Exception {
	}

}
