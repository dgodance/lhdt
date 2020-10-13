/**
 * 
 */
package lhdt.svc.common.fileinfo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.svc.common.fileinfo.model.FileInfo;

/**
 * @author gravity
 * @since 2020. 9. 4.
 * JPA를 통한 파일정보 테이블 매핑 클래스
 */
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

}

