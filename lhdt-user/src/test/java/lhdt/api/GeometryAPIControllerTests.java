package lhdt.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lhdt.common.BaseControllerTest;
import lhdt.domain.common.GeometryInfo;
import lhdt.domain.common.SpatialOperationInfo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GeometryAPIController.class)
class GeometryAPIControllerTests extends BaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("디자인 레이어 필지 intersection point(한점)")
    void getDesignLayerLandsByPoint() throws Exception {
        List<GeometryInfo> geometry = new ArrayList<>();
        geometry.add(new GeometryInfo(127.0018109, 37.4440647));

        SpatialOperationInfo info = SpatialOperationInfo.builder()
                .type("land")
                .buffer(0.002f)
                .geometryInfo(geometry)
                .maxFeatures(3)
                .build();
        this.mockMvc.perform(get("/api/geometry/intersection/design-layers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info)))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("_embedded.designLayerLands[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("geometry-intersection-design-layer-land-point"));
    }

    @Test
    @DisplayName("디자인 레이어 필지 intersection lineString(처음 위치와 마지막 위치가 같지 않을 경우)")
    void getDesignLayerLandsByLineString() throws Exception {
        List<GeometryInfo> geometry = new ArrayList<>();
        geometry.add(new GeometryInfo(126.997892834868054, 37.445044223911694));
        geometry.add(new GeometryInfo(126.999715202064294, 37.442834603686244));
        geometry.add(new GeometryInfo(127.000876961151903, 37.442379011887184));
        geometry.add(new GeometryInfo(127.002152618189271, 37.44215121598765));
        geometry.add(new GeometryInfo(127.002448752858669, 37.444019142363807));

        SpatialOperationInfo info = SpatialOperationInfo.builder()
                .type("land")
                .buffer(0.002f)
                .geometryInfo(geometry)
                .maxFeatures(3)
                .build();
        this.mockMvc.perform(get("/api/geometry/intersection/design-layers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info)))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("_embedded.designLayerLands[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("geometry-intersection-design-layer-land-linestring"));
    }

    @Test
    @DisplayName("디자인 레이어 필지 intersection polygon(처음 위치와 마지막 위치가 같은 경우)")
    void getDesignLayerLandsByPolygon() throws Exception {
        List<GeometryInfo> geometry = new ArrayList<>();
        geometry.add(new GeometryInfo(126.998530663386731, 37.445613713660521));
        geometry.add(new GeometryInfo(126.997414463479032, 37.446342660539017));
        geometry.add(new GeometryInfo(126.99957852452458, 37.442219554757514));
        geometry.add(new GeometryInfo(127.000990859101663, 37.441946199678078));
        geometry.add(new GeometryInfo(127.003633291536218, 37.446843811517979));
        geometry.add(new GeometryInfo(127.003633291536218, 37.446843811517979));
        geometry.add(new GeometryInfo(126.998530663386731,37.445613713660521));

        SpatialOperationInfo info = SpatialOperationInfo.builder()
                .type("land")
                .geometryInfo(geometry)
                .maxFeatures(3)
                .build();
        this.mockMvc.perform(get("/api/geometry/intersection/design-layers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info)))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("_embedded.designLayerLands[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("geometry-intersection-design-layer-land-polygon"));

    }

    @Test
    @DisplayName("buffer 입력범위 초과 테스트")
    void bufferValidTest() throws Exception {
        List<GeometryInfo> geometry = new ArrayList<>();
        geometry.add(new GeometryInfo(127.0018109, 37.4440647));

        SpatialOperationInfo info = SpatialOperationInfo.builder()
                .type("land")
                .buffer(10f)
                .geometryInfo(geometry)
                .maxFeatures(3)
                .build();
        this.mockMvc.perform(get("/api/geometry/intersection/design-layers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").exists())
                .andExpect(jsonPath("statusCode").exists())
                .andExpect(jsonPath("message").exists());
    }

    @Disabled
    @DisplayName("디자인 레이어 빌딩 intersection point")
    void getDesignLayerBuildingsByPoint() {

    }

    @Disabled
    @DisplayName("디자인 레이어 빌딩 intersection lineString")
    void getDesignLayerBuildingsByLineString() {

    }

    @Disabled
    @DisplayName("디자인 레이어 빌딩 intersection polygon")
    void getDesignLayerBuildingsByPolygon() {

    }

    @Test
    @DisplayName("데이터 intersection point")
    void getDatasByPoint() throws Exception {
        List<GeometryInfo> geometry = new ArrayList<>();
        geometry.add(new GeometryInfo(127.262038223551698, 36.49698802728868));

        SpatialOperationInfo info = SpatialOperationInfo.builder()
                .buffer(0.002f)
                .geometryInfo(geometry)
                .maxFeatures(3)
                .build();
        this.mockMvc.perform(get("/api/geometry/intersection/datas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info)))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("_embedded.designLayerLands[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("geometry-intersection-data-point"));
    }

    @Test
    @DisplayName("데이터 intersection lineString")
    void getDatasByLineString() throws Exception {
        List<GeometryInfo> geometry = new ArrayList<>();
        geometry.add(new GeometryInfo(127.262133962040522, 36.497193516804202));
        geometry.add(new GeometryInfo(127.262154933683433, 36.497120922655668));
        geometry.add(new GeometryInfo(127.262171065716444, 36.497001545611411));
        geometry.add(new GeometryInfo(127.262364650112531, 36.496930564666179));

        SpatialOperationInfo info = SpatialOperationInfo.builder()
                .buffer(0.002f)
                .geometryInfo(geometry)
                .maxFeatures(3)
                .build();
        this.mockMvc.perform(get("/api/geometry/intersection/datas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info)))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("_embedded.designLayerLands[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("geometry-intersection-data-linestring"));
    }

    @Test
    @DisplayName("데이터 intersection polygon")
    void getDatasByPolygon() throws Exception {
        List<GeometryInfo> geometry = new ArrayList<>();
        geometry.add(new GeometryInfo(127.261939578507366, 36.497348461104508));
        geometry.add(new GeometryInfo(127.261994592089778, 36.497006997489514));
        geometry.add(new GeometryInfo(127.262430906708943, 36.496855235882848));
        geometry.add(new GeometryInfo(127.262550418974186, 36.497179626317092));
        geometry.add(new GeometryInfo(127.262550418974186, 36.497179626317092));
        geometry.add(new GeometryInfo(127.261939578507366, 36.497348461104508));

        SpatialOperationInfo info = SpatialOperationInfo.builder()
                .geometryInfo(geometry)
                .maxFeatures(3)
                .build();
        this.mockMvc.perform(get("/api/geometry/intersection/datas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info)))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("_embedded.designLayerLands[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("geometry-intersection-data-polygon"));
    }

}