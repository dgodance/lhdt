/**
 * 
 */
package lhdt.admin.svc.lowinfo.persistence;

import lhdt.admin.svc.common.AdminSvcMapper;
import lhdt.admin.svc.lowinfo.domain.LowInfoDet;
import lhdt.cmmn.config.AnalsConnMapper;

/**
 * @author gravity
 * @since 2020. 8. 14.
 *
 */
@AnalsConnMapper
public interface LowInfoDetMapper extends AdminSvcMapper<LowInfoDet, Long> {

}
