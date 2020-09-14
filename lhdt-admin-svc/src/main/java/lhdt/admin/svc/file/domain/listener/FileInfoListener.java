package lhdt.admin.svc.file.domain.listener;

import dev.hyunlab.core.vo.PpFileVO;
import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.cmmn.misc.CmmnFile;
import lhdt.cmmn.misc.CmmnUtils;

import javax.persistence.PreRemove;
import java.io.File;

public class FileInfoListener {
    @PreRemove
    public void preRemove(FileInfo fileInfo) {
        fileInfo.delete();
    }
}
