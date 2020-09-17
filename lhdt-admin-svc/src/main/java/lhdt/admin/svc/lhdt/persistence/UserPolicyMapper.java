package lhdt.admin.svc.lhdt.persistence;

import lhdt.admin.svc.lhdt.domain.UserPolicy;
import lhdt.cmmn.config.LhdtConnMapper;

@LhdtConnMapper
public interface UserPolicyMapper {

	/**
	 * 사용자 설정 정보 취득
	 * @param userId
	 * @return
	 */
	UserPolicy getUserPolicy(String userId);

	/**
	 * 사용자 설정 등록
	 * @param userPolicy
	 * @return
	 */
	int insertUserPolicy(UserPolicy userPolicy);

	/**
	 * 사용자 설정 수정
	 * @param userPolicy
	 * @return
	 */
	int updateUserPolicy(UserPolicy userPolicy);
}
