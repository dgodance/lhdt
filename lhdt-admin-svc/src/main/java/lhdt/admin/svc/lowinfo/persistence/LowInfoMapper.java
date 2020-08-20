/**
 * 
 */
package lhdt.admin.svc.lowinfo.persistence;

import lhdt.admin.svc.common.AdminSvcMapper;
import lhdt.admin.svc.lowinfo.domain.LowInfo;
import lhdt.ds.common.config.AnalsConnMapper;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 14.
 *
 */
@AnalsConnMapper
public interface LowInfoMapper extends AdminSvcMapper<LowInfo, Long> {

}
