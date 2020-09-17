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
 *
 */
@AnalsConnMapper
public interface FileInfoMapper extends CmmnMapper<FileInfo, Long> {

}
