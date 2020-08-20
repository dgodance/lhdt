package lhdt.api;

import lhdt.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DesignLayerAPIControllerTests extends BaseControllerTest {

    @Test
    @DisplayName("DesignLayer 목록 조회 하기")
    public void getDesignLayer() throws Exception {
        //DesignLayer designLayer = new DesignLayer();
        //designLayer.setListCounter(10L);

        this.mockMvc.perform(get("/api/design-layers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                //.content(objectMapper.writeValueAsString(designLayer))
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
                .andDo(document("design-layer-get"));
    }
}