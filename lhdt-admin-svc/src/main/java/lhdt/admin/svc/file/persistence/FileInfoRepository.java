package lhdt.admin.svc.file.persistence;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * 파일인포 테이블에 대한 jpa 연결 클래스
 */
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

}
