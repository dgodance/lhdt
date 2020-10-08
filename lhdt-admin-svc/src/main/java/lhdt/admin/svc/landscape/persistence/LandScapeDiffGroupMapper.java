package lhdt.admin.svc.landscape.persistence;

import lhdt.admin.svc.common.AdminSvcMapper;
import lhdt.admin.svc.landscape.domain.LandScapePoint;
import lhdt.cmmn.config.AnalsConnMapper;

/**
 * mybatis와 경관비교그룹 테이블을 매핑합니다
 */
@AnalsConnMapper
public interface LandScapeDiffGroupMapper extends AdminSvcMapper<LandScapePoint, Long> {
}
