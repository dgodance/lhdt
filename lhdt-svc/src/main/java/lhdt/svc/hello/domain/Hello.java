/**
 * 
 */
package lhdt.svc.hello.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lhdt.svc.common.Domain;
import lhdt.svc.common.SvcField;
import lombok.*;

/**
 * 안녕 
 * @author gravity@daumsoft.com
 *
 */
@Entity
@Table(name="hello")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Hello extends Domain {
	/**
	 * 안녕 명
	 */
	@Column(name = "hello_name")
	private String helloName;
	
	/**
	 * 내용
	 */
	@Column(name = "cn")
	private String cn;
	
	/**
	 * 업무키
	 */
	@Column(name="hello_group_id")
	@SvcField(bizKey = true, order = 0)
	private String helloGroupId;
	
	/**
	 * 업무키
	 */
	@Column(name="hello_group_no")
	@SvcField(bizKey = true, order = 1)
	private Long helloGroupNo;
	
	/**
	 * 검색조건 -  안녕 명
	 */
	private String searchHelloName;
}
