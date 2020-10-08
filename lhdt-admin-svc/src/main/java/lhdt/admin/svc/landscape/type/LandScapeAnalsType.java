package lhdt.admin.svc.landscape.type;

import lhdt.cmmn.misc.CmmnEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 경관분석 종류에 대하여 정의합니다
 */
@Getter
@AllArgsConstructor
public enum LandScapeAnalsType implements CmmnEnum {
    점 (0),
    선 (1);
    private Integer code;

    /**
     * 문자열로 코드값을 돌려줍니다
     * @return
     */
    @Override
    public String toString() {
        return this.code + "";
    }
}