package lhdt.admin.svc.cityplanning.persistence;

import lhdt.admin.svc.cityplanning.domain.CPFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CPFileInfoRepository extends JpaRepository<CPFileInfo, Long> {
}
