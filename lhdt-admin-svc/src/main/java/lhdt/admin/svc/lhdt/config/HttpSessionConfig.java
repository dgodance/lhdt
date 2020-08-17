/**
 * 
 */
package lhdt.admin.svc.lhdt.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * 세션 관련 리스너
 * @author BD_DEV_03
 * @since 2020. 1. 16.
 */
@Slf4j
@Configuration
public class HttpSessionConfig {

	private static final Map<String,HttpSession> sessions = new HashMap<String, HttpSession>();
	
	public List<HttpSession> getActiveSessions(){
		return new ArrayList<HttpSession>(sessions.values());
	}
	
	public int clearActiveSessions() {
		int sessionSize = sessions.size();
		log.info("<<.clearActiveSessions - size:{}", sessionSize);
		sessions.clear();
		
		//
		return sessionSize;
	}
	
	@Bean
	public HttpSessionListener httpSessionListener() {
		return new HttpSessionListener() {
			@Override
			public void sessionCreated(HttpSessionEvent hse) {
				sessions.put(hse.getSession().getId(), hse.getSession());
				
				//
				log.info("<<.sessionCreated - {}", hse.getSession().getId());
			}
			
			@Override
			public void sessionDestroyed(HttpSessionEvent hse) {
				log.info("<<.sessionDestroyed - {}", hse.getSession().getId());
				
				sessions.remove(hse.getSession().getId());
			}
		};
	}

}
