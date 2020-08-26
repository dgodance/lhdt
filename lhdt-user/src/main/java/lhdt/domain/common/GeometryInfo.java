package lhdt.domain.common;

import lombok.*;

import java.math.BigDecimal;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeometryInfo {
    // 경도
    private BigDecimal longitude;
    // 위도
    private BigDecimal latitude;
}
