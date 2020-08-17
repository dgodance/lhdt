/**
 * 
 */
package lhdt.admin.svc.hello.persistence;

import lhdt.admin.svc.config.AnalsConnMapper;
import lhdt.admin.svc.hello.domain.Hello;
import lhdt.ds.common.misc.DsMapper;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 14.
 *
 */
@AnalsConnMapper
public interface HelloMapper extends DsMapper<Hello, Long> {

}
