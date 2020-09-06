package lhdt.controller.rest;

import lhdt.domain.PageType;
import lhdt.domain.data.DataInfoLog;
import lhdt.domain.layer.LayerGroup;
import lhdt.domain.urban.UrbanGroup;
import lhdt.service.UrbanGroupService;
import lhdt.support.LayerDisplaySupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * extrusion model 예제
 */
@Slf4j
@RestController
@RequestMapping("/extrusions")
public class ExtrusionRestController {

    @Autowired
    private UrbanGroupService urbanGroupService;

    /**
     * extrusion model example
     * @param request
     * @param model
     * @return
     */
    @GetMapping
    public Map<String, Object> list(HttpServletRequest request, Model model) {

        Map<String, Object> result = new HashMap<>();
        String errorCode = null;
        String message = null;

        List<UrbanGroup> oneDepthUrbanGroupList = urbanGroupService.getListUrbanGroupByDepth(1);
        List<UrbanGroup> twoDepthUrbanGroupList = urbanGroupService.getListUrbanGroupByParent(oneDepthUrbanGroupList.get(0).getUrbanGroupId());

        int statusCode = HttpStatus.OK.value();

        result.put("oneDepthUrbanGroupList", oneDepthUrbanGroupList);
        result.put("twoDepthUrbanGroupList", twoDepthUrbanGroupList);
        result.put("statusCode", statusCode);
        result.put("errorCode", errorCode);
        result.put("message", message);

        return result;
    }
}