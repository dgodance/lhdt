/**
 * 
 */
package lhdt.svc.common.fileinfo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.svc.common.fileinfo.model.FileInfo;

/**
 * @author gravity
 * @since 2020. 9. 4.
 *
 */
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

}
