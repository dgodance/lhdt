package lhdt.admin.svc.cityplanning.domain.listener;

import lhdt.admin.svc.cityplanning.domain.CPFileInfo;

import javax.persistence.PreRemove;

public class CPFileInfoListener {
    @PreRemove
    public void preRemove(CPFileInfo cpFileInfo) {
        System.out.println(cpFileInfo.toString());
    }
}
