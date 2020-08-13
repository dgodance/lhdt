/**
 * 
 */
package lhdt.svc.lhdt.userinfo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lhdt.svc.common.LhdtDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 13.
 *
 */
@Entity
@Table(name = "user_info")
@NoArgsConstructor
@Data
public class UserInfo extends LhdtDomain {
	@Id
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "user_name")
	private String userName;
}
