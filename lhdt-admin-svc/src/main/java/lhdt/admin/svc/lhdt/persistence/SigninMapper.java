package lhdt.admin.svc.lhdt.persistence;

import lhdt.admin.svc.lhdt.domain.UserInfo;
import lhdt.admin.svc.lhdt.domain.UserSession;
import lhdt.cmmn.config.LhdtConnMapper;


/**
 * Sign in
 * @author jeongdae
 *
 */
@LhdtConnMapper
public interface SigninMapper {

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
