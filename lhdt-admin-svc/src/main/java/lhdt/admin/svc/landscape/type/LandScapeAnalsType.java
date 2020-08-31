package lhdt.admin.svc.landscape.type;

import lhdt.ds.common.misc.DSEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LandScapeAnalsType implements DSEnum {
    점 (0),
    선 (1);
    private Integer code;
    @Override
    public String toString() {
        return this.code + "";
    }
}