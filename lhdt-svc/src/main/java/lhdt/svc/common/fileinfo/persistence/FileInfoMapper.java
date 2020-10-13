/**
 * 
 */
package lhdt.svc.common.fileinfo.persistence;

import lhdt.cmmn.config.AnalsConnMapper;
import lhdt.cmmn.misc.CmmnMapper;
import lhdt.svc.common.fileinfo.model.FileInfo;

/**
 * @author gravity
 * @since 2020. 9. 4.
 * MyBatis를 통한 파일정보 테이블 매핑 클래스
 */
@AnalsConnMapper
public interface FileInfoMapper extends CmmnMapper<FileInfo, Long> {

}
