/**
 * 
 */
package lhdt.svc.lhdt.userinfo.persistence;

import lhdt.cmmn.config.LhdtConnMapper;

/**
 * @author gravity
 * @since 2020. 8. 13.
 *
 */
@LhdtConnMapper
public interface UserInfoMapper {

//	@Select({"select user_id as userId from user_info"})
	int getTotcnt();
}
