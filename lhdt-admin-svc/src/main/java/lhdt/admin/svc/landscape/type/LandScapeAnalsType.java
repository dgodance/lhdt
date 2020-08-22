package lhdt.admin.svc.landscape.type;

import lhdt.admin.svc.landscape.domain.LandScapeAnals;
import lhdt.ds.common.misc.DSEnum;
import lhdt.ds.common.misc.DsUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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