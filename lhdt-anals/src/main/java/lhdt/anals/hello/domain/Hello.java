/**
 * 
 */
package lhdt.anals.hello.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lhdt.anals.common.AnalsField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 안녕 
 * @author gravity@daumsoft.com
 *
 */
@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
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
	@AnalsField(bizKey = true, order = 0)
	private String helloGroupId;
	
	/**
	 * 업무키
	 */
	@Column(name="hello_group_no")
	@AnalsField(bizKey = true, order = 1)
	private Long helloGroupNo;
	
	/**
	 * 검색조건 -  안녕 명
	 */
	private String searchHelloName;
}
