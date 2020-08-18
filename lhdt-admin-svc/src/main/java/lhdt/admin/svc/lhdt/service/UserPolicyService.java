package lhdt.admin.svc.lhdt.service;

import lhdt.admin.svc.lhdt.domain.UserPolicy;

public interface UserPolicyService {

	/**
	 * 사용자 설정 정보 취득
	 * @param userId
	 * @return
	 */
	UserPolicy getUserPolicy(String userId);

	/**
	 * 사용자 설정 수정
	 * @param userPolicy
	 * @return
	 */
	int updateUserPolicy(UserPolicy userPolicy);

}
