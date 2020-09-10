package lhdt.svc.cityplanning.persistence;

import lhdt.svc.cityplanning.domain.CPFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CPFileInfoRepository extends JpaRepository<CPFileInfo, Long> {
    boolean existsByFileNameAndFilePath(String fileName, String filePath);
    CPFileInfo findByFileNameAndFilePath(String fileName, String filePath);
    List<CPFileInfo> findAllByFileNameAndFilePath(String fileName, String filePath);
}
