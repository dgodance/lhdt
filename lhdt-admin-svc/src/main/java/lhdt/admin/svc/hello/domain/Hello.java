/**
 * 
 */
package lhdt.admin.svc.hello.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lhdt.ds.common.domain.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 14.
 *
 */
@Entity
@Table(name="hello")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hello extends Domain {

	@Column(name="hello_name")
	private String helloName;
}
