/**
 * 
 */
package lhdt.svc.hello.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.svc.hello.domain.Hello;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 13.
 *
 */
public interface HelloRepository extends JpaRepository<Hello, Long>{

	Hello findByHelloGroupIdAndHelloGroupNo(String helloGroupId, Long helloGroupNo);
}
