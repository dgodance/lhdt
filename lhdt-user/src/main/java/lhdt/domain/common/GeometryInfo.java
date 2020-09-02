package lhdt.domain.common;

import lombok.*;

import java.math.BigDecimal;

/**
 * 포인트 정보
 */
@ToString
@Builder
@Getter
@Setter
public class GeometryInfo {
    // 경도
    private BigDecimal longitude;
    // 위도
    private BigDecimal latitude;
    // 높이
    private BigDecimal altitude;

    public GeometryInfo(BigDecimal longitude, BigDecimal latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public GeometryInfo(BigDecimal longitude, BigDecimal latitude, BigDecimal altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }
}
