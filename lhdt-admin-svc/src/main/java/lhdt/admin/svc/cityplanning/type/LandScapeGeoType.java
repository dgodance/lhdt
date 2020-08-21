package lhdt.admin.svc.cityplanning.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LandScapeGeoType {
    폴리곤(0),
    선(1),
    점(2);
    private Integer code;
}
