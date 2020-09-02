package lhdt.domain.common;

import lombok.*;

import java.util.List;

/**
 * 공간 연산을 위한 정보
 */
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpatialOperationInfo {

    private String wkt;
    private String type;
    private Float buffer;
    private List<GeometryInfo> geometryInfo;
}
