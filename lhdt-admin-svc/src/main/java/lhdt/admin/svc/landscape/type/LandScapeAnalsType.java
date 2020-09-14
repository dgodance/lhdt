package lhdt.admin.svc.landscape.type;

import lhdt.cmmn.misc.CmmnEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LandScapeAnalsType implements CmmnEnum {
    점 (0),
    선 (1);
    private Integer code;
    @Override
    public String toString() {
        return this.code + "";
    }
}