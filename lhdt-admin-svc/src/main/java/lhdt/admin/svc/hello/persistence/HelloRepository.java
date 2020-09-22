/**
 * 
 */
package lhdt.admin.svc.hello.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.admin.svc.hello.domain.Hello;

/**
 * @author gravity
 * @since 2020. 8. 14.
 *
 */
public interface HelloRepository extends JpaRepository<Hello, Long> {

}
