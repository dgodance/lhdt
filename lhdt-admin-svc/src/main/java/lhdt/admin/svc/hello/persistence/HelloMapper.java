/**
 * 
 */
package lhdt.admin.svc.hello.persistence;

import lhdt.admin.svc.common.AdminSvcMapper;
import lhdt.admin.svc.hello.domain.Hello;
import lhdt.cmmn.config.AnalsConnMapper;

/**
 * @author gravity
 * @since 2020. 8. 14.
 *
 */
@AnalsConnMapper
public interface HelloMapper extends AdminSvcMapper<Hello, Long> {

}
