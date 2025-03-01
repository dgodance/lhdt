package lhdt.admin.svc.lhdt.service;

import lhdt.admin.svc.lhdt.domain.UserInfo;
import lhdt.admin.svc.lhdt.domain.UserSession;

/**
 * Sign in 관련 처리
 * @author jeongdae
 *
 */
public interface SigninService {

	/**
	 * 회원 세션 정보를 취득
	 * @param userInfo
	 * @return
	 */
	UserSession getUserSession(UserInfo userInfo);
	
	/**
	 * Sign in 성공 후 회원 정보를 갱신
	 * @param userSession
	 * @return
	 */
	int updateSigninUserSession(UserSession userSession);
	
}
