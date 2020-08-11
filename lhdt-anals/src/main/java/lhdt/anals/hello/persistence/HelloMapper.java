/**
 * 
 */
package lhdt.anals.hello.persistence;

import lhdt.anals.common.AnalsMapper;
import lhdt.anals.config.AnalsConnMapper;
import lhdt.anals.hello.domain.Hello;

/**
 * 데이터 조회전용 mybatis mapper
 * @author gravity@daumsoft.com
 *
 */
@AnalsConnMapper
public interface HelloMapper extends AnalsMapper<Hello, Long> {

	
}
