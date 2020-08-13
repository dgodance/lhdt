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

	/**
	 * 업무키로 데이터 존재여부 확인
	 * @param helloGroupId
	 * @param helloGroupNo
	 * @return
	 */
	boolean existsByHelloGroupIdAndHelloGroupNo(String helloGroupId, Long helloGroupNo);
	
	/**
	 * 업무키로 데이터 1건 조회
	 * @param helloGroupId
	 * @param helloGroupNo
	 * @return
	 */
	Hello findByHelloGroupIdAndHelloGroupNo(String helloGroupId, Long helloGroupNo);
}
