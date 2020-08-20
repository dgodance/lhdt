package lhdt.api;

import lhdt.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DesignLayerGroupAPIControllerTests extends BaseControllerTest {

    @Test
    @DisplayName("DesignLayerGroup 목록 조회 하기")
    public void getDesignLayerGroup() throws Exception {
        this.mockMvc.perform(get("/api/design-layer-groups"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.designLayerGroups[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("design-layer-group-list"));
    }

    @Test
    @DisplayName("DesignLayerGroup 단일 조회 하기")
    public void getDesignLayerGroupById() throws Exception {
        this.mockMvc.perform(get("/api/design-layer-groups/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("designLayerGroupId").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("design-layer-group-get"));
    }
}