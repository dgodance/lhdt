package lhdt.api;

import lhdt.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DesignLayerAPIControllerTests extends BaseControllerTest {

    @Test
    @DisplayName("DesignLayer 목록 조회 하기")
    public void getDesignLayer() throws Exception {
//        DesignLayer designLayer = new DesignLayer();
//        designLayer.setListCounter(10L);

        this.mockMvc.perform(get("/api/design-layers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
//                .content(objectMapper.writeValueAsString(designLayer))
                .param("pageNo", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.designLayers[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("design-layer-list"));
    }

    @Test
    @DisplayName("DesignLayer 단일 조회 하기")
    public void getDesignLayerById() throws Exception {
        this.mockMvc.perform(get("/api/design-layers/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("designLayerId").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("design-layer-get",
                        /**
                         * relaxedResponseFields 를 쓰면 모든 필드를 기술할 필요가 없다.
                         * 하지만 모든 필드를 기술하지 않으므로 정확한 문서를 만들지 못한다.
                         * responseFields를 쓰면 모든 필드를 기술해야 한다.
                         */
                        relaxedResponseFields(
                                fieldWithPath("designLayerId").description("design layer 고유번호"),
                                fieldWithPath("designLayerGroupId").description("design layer 그룹 고유번호"),
                                fieldWithPath("designLayerKey").description("design layer 고유키(API용)"),
                                fieldWithPath("designLayerName").description("design layer 명"),
                                fieldWithPath("designLayerCategory").description("design layer 분류. land : 땅, building : 빌딩"),
                                fieldWithPath("userId").description("사용자명"),
                                fieldWithPath("ogcWebServices").description("OGC Web Services (wms, wfs, wcs, wps)"),
                                fieldWithPath("geometryType").description("도형 타입"),
                                fieldWithPath("layerFillColor").description("외곽선 색상"),
                                fieldWithPath("layerLineColor").description("외곽선 두께"),
                                fieldWithPath("layerLineStyle").description("채우기 색상"),
                                fieldWithPath("layerAlphaStyle").description("투명도"),
                                fieldWithPath("viewOrder").description("나열 순서"),
                                fieldWithPath("zindex").description("지도위에 노출 순위(css z-index와 동일)"),
                                fieldWithPath("available").description("사용유무"),
                                fieldWithPath("cacheAvailable").description("캐시 사용 유무"),
                                fieldWithPath("coordinate").description("좌표계 정보"),
                                fieldWithPath("description").description("설명"),
                                fieldWithPath("updateDate").description("수정일"),
                                fieldWithPath("insertDate").description("등록일")
                        )
                    ));
    }
}