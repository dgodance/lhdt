package lhdt.admin.svc.file.domain.listener;

import javax.persistence.PreRemove;

import lhdt.admin.svc.file.domain.FileInfo;

public class FileInfoListener {
    @PreRemove
    public void preRemove(FileInfo fileInfo) {
        fileInfo.delete();
    }
}
