/**
 * 
 */
package lhdt.svc.common.fileinfo.persistence;

import lhdt.ds.common.config.AnalsConnMapper;
import lhdt.ds.common.misc.DsMapper;
import lhdt.svc.common.fileinfo.model.FileInfo;

/**
 * @author gravity
 * @since 2020. 9. 4.
 *
 */
@AnalsConnMapper
public interface FileInfoMapper extends DsMapper<FileInfo, Long> {

}
