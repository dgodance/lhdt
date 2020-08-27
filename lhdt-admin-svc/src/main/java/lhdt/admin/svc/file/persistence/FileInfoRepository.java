package lhdt.admin.svc.file.persistence;

import lhdt.admin.svc.file.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
