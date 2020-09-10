/**
 * 
 */
package lhdt.admin.svc.lowinfo.persistence;

import lhdt.admin.svc.common.AdminSvcMapper;
import lhdt.admin.svc.lowinfo.domain.LowInfoDet;
import lhdt.ds.common.config.AnalsConnMapper;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 14.
 *
 */
@AnalsConnMapper
public interface LowInfoDetMapper extends AdminSvcMapper<LowInfoDet, Long> {

}
