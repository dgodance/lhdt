package lhdt.admin.svc.lhdt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lhdt.admin.svc.lhdt.domain.UserInfo;
import lhdt.admin.svc.lhdt.domain.UserSession;
import lhdt.admin.svc.lhdt.persistence.SigninMapper;
import lhdt.admin.svc.lhdt.service.SigninService;

/**
 * Sign in 관련 처리
 * @author jeongdae
 *
 */
@Service
public class SigninServiceImpl implements SigninService {

	@Autowired
	private SigninMapper signinMapper;
	
	/**
	 * 회원 세션 정보를 취득
	 * TODO API
	 * @param userInfo
	 * @return
	 */
	@Transactional(readOnly=true)
	public UserSession getUserSession(UserInfo userInfo) {
		String json = "{\"signinIp\":null,\"userId\":\"admin\",\"userGroupId\":1,\"userGroupName\":null,\"userName\":\"슈퍼관리자\",\"password\":\"$2a$10$ITsA9xBaHMI7rTEVtZkdOe.u8ClRXf4jO2SzeCBPbH7oC3w5SDguK\",\"salt\":null,\"userRoleCheckYn\":\"N\",\"status\":\"0\",\"failSigninCount\":0,\"userLastSigninLockOver\":false,\"passwordChangeTermOver\":false}";
		try {
			return new ObjectMapper().readValue(json, UserSession.class);
		} catch (JsonProcessingException e) {
			return null;
		}
//		return signinMapper.getUserSession(userInfo);
	}

	/**
	 * Sign in 성공 후 회원 정보를 갱신
	 * TODO API
	 * @param userSession
	 * @return
	 */
	@Transactional
	public int updateSigninUserSession(UserSession userSession) {
		return 0;
//		return signinMapper.updateSigninUserSession(userSession);
	}
}
