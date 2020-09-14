/**
 * 
 */
package lhdt.cmmn.misc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import dev.hyunlab.web.util.PpWebUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 14.
 *
 */
@Slf4j
public class CmmnWebUtils extends PpWebUtil {

	/**
	 * 어디서나 HttpServletRequest 구하기
	 * @see http://dveamer.github.io/backend/SpringRequestContextHolder.html
	 * @since
	 * 	20200817	init
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}
	
	/**
	 * 어디서나 HttpSession 구하기
	 * @see http://dveamer.github.io/backend/SpringRequestContextHolder.html
	 * @since
	 * 	20200817	init
	 */
	public static HttpSession getSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		if(null != request) {
			return request.getSession();
		}
		
		//
		log.error("<<.getSession - null request");
		return null;
	}
	
	
	/**
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
}
