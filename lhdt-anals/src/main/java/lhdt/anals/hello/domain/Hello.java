/**
 * 
 */
package lhdt.anals.hello.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import dev.hyunlab.core.vo.PpVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
	 * 검색조건 -  안녕 명
	 */
	private String searchHelloName;
}
