package lhdt.cmmn.misc;

import javax.servlet.http.HttpServletRequest;

public class PpWebUtil {

	/**
	 * 접속자 아이피 구하기
	 * @param request
	 * @return
	 */
	public static String getConectIp(HttpServletRequest request){
		String ip = request.getHeader("X-Forwarded-For");
		
		if(PpUtil.isEmpty(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(PpUtil.isEmpty(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(PpUtil.isEmpty(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if(PpUtil.isEmpty(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(PpUtil.isEmpty(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if(PpUtil.isEmpty(ip)) {
			ip = request.getRemoteAddr();
		}
		
		//
		return ip;
	}	
}
