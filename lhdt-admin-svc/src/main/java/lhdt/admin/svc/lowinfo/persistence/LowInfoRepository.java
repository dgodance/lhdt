/**
 * 
 */
package lhdt.admin.svc.lowinfo.persistence;

import lhdt.admin.svc.lowinfo.domain.LowInfo;
import lhdt.admin.svc.lowinfo.domain.LowInfoDet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 14.
 *
 */
public interface LowInfoRepository extends JpaRepository<LowInfo, Long> {
    LowInfo findByLowInfoName(String lowInfoName);
}
