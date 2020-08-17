/**
 * 
 */
package lhdt.svc.lhdt.userinfo.persistence;

import lhdt.svc.config.LhdtConnMapper;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 13.
 *
 */
@LhdtConnMapper
public interface UserInfoMapper {

//	@Select({"select user_id as userId from user_info"})
	int getTotcnt();
}
