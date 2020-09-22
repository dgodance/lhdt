/**
 * 
 */
package lhdt.svc.lhdt.userinfo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.svc.lhdt.userinfo.domain.UserInfo;

/**
 * @author gravity
 * @since 2020. 8. 13.
 *
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

}
