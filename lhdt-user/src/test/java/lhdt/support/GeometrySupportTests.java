package lhdt.support;

import lhdt.domain.common.GeometryInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class GeometrySupportTests {

    @Test
    void test() {
        List<GeometryInfo> point = Collections.singletonList(new GeometryInfo(BigDecimal.valueOf(12.3133), BigDecimal.valueOf(313.44432)));

        List<GeometryInfo> line = Arrays.asList(
                new GeometryInfo(BigDecimal.valueOf(12.3133),BigDecimal.valueOf(313.44432)),
                new GeometryInfo(BigDecimal.valueOf(31.35634),BigDecimal.valueOf(12.1212)),
                new GeometryInfo(BigDecimal.valueOf(2121.212),BigDecimal.valueOf(123.13131)),
                new GeometryInfo(BigDecimal.valueOf(128.21212),BigDecimal.valueOf(32.1212)),
                new GeometryInfo(BigDecimal.valueOf(2121.212),BigDecimal.valueOf(123.13131)),
                new GeometryInfo(BigDecimal.valueOf(11.122121),BigDecimal.valueOf(85.1212212)));

        List<GeometryInfo> polygon = Arrays.asList(
                new GeometryInfo(BigDecimal.valueOf(12.3133),BigDecimal.valueOf(313.44432)),
                new GeometryInfo(BigDecimal.valueOf(2121.212),BigDecimal.valueOf(123.13131)),
                new GeometryInfo(BigDecimal.valueOf(128.21212),BigDecimal.valueOf(32.1212)),
                new GeometryInfo(BigDecimal.valueOf(2121.212),BigDecimal.valueOf(123.13131)),
                new GeometryInfo(BigDecimal.valueOf(12.3133),BigDecimal.valueOf(313.44432)));

        log.info("point ============= {} ", GeometrySupport.toWKT(point));
        log.info("line ==============={} ", GeometrySupport.toWKT(line));
        log.info("polygon ============== {} ", GeometrySupport.toWKT(polygon));

        assertTrue(GeometrySupport.toWKT(point).startsWith("POINT"));
        assertTrue(GeometrySupport.toWKT(line).startsWith("LINE"));
        assertTrue(GeometrySupport.toWKT(polygon).startsWith("POLYGON"));

    }
}