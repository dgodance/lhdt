/**
 * 
 */
package lhdt.svc.lhdt.userinfo.service;

import java.util.List;

import lhdt.svc.lhdt.userinfo.domain.UserInfo;

/**
 * @author gravity
 * @since 2020. 8. 13.
 *
 */
public interface UserInfoService {
	List<UserInfo> findAll();
	
	
	int getTotcnt();
}
