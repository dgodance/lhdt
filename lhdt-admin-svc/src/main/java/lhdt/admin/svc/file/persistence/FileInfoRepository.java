package lhdt.admin.svc.file.persistence;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

}
