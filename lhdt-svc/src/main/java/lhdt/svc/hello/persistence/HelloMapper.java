/**
 * 
 */
package lhdt.svc.hello.persistence;

import lhdt.cmmn.config.AnalsConnMapper;
import lhdt.svc.common.SvcMapper;
import lhdt.svc.hello.domain.Hello;

/**
 * @author gravity
 * @since 2020. 8. 13.
 *
 */
@AnalsConnMapper
public interface HelloMapper extends SvcMapper<Hello, Long> {

//	@Select({"select id from hello"})
	Integer getTotcnt();
}
