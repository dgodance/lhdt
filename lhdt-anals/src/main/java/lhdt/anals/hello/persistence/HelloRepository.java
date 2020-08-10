/**
 * 
 */
package lhdt.anals.hello.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.anals.hello.domain.Hello;

/**
 * jpa
 * @author gravity@daumsoft.com
 *
 */
public interface HelloRepository extends JpaRepository<Hello, Long> {

}
