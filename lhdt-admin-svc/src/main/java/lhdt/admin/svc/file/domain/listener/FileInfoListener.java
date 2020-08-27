package lhdt.admin.svc.file.domain.listener;

import dev.hyunlab.core.vo.PpFileVO;
import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.ds.common.misc.DsFile;
import lhdt.ds.common.misc.DsUtils;

import javax.persistence.PreRemove;
import java.io.File;

public class FileInfoListener {
    @PreRemove
    public void preRemove(FileInfo fileInfo) {
        fileInfo.delete();
    }
}
