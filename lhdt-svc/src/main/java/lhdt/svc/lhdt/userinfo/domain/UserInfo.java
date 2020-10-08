/**
 * 
 */
package lhdt.svc.lhdt.userinfo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lhdt.cmmn.domain.LhdtDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author gravity
 * @since 2020. 8. 13.
 *
 */
@Entity
@Table(name = "user_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfo extends LhdtDomain {
	/**
	 * COMMENT '사용자 id'"
	 */
	@Id
	@Column(name = "user_id")
	private String userId;
	
	/**
	 * COMMENT '사용자 명'"
	 */
	@Column(name = "user_nm")
	private String userName;
}
