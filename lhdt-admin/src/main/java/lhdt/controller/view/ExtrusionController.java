package lhdt.controller.view;

import lhdt.domain.Key;
import lhdt.domain.PageType;
import lhdt.domain.common.Pagination;
import lhdt.domain.data.DataGroup;
import lhdt.domain.data.DataInfoLog;
import lhdt.domain.urban.UrbanGroup;
import lhdt.domain.user.UserSession;
import lhdt.service.DataGroupService;
import lhdt.service.DataLogService;
import lhdt.service.UrbanGroupService;
import lhdt.support.SQLInjectSupport;
import lhdt.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Controller
@RequestMapping("/extrusion")
public class ExtrusionController {

    @Autowired
    private UrbanGroupService urbanGroupService;

    /**
     * extrusion model example
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/list")
    public String list(HttpServletRequest request, Model model) {

        UrbanGroup urbanGroup = UrbanGroup.builder().depth(1).build();
        List<UrbanGroup> oneDepthUrbanGroupList  = urbanGroupService.getListUrbanGroup();

        model.addAttribute("oneDepthUrbanGroupList", oneDepthUrbanGroupList);
        return "/extrusion/list";
    }

    /**
     * 검색 조건
     * @param pageType
     * @param dataInfoLog
     * @return
     */
    private String getSearchParameters(PageType pageType, DataInfoLog dataInfoLog) {
        return dataInfoLog.getParameters();
    }
}