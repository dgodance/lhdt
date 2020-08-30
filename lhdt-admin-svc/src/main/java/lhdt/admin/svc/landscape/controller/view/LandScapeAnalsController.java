package lhdt.admin.svc.landscape.controller.view;

import lhdt.admin.svc.cityplanning.service.CPDistricInfoService;
import lhdt.admin.svc.cityplanning.service.CPLocalInfoService;
import lhdt.admin.svc.landscape.domain.LandScapeAnals;
import lhdt.admin.svc.landscape.model.CPFullName;
import lhdt.admin.svc.landscape.model.CPFullNameAndId;
import lhdt.admin.svc.landscape.model.LandScapeRegistParam;
import lhdt.admin.svc.landscape.service.LandScapeAnalsService;
import lhdt.admin.svc.landscape.type.LandScapeAnalsType;
import lhdt.ds.common.misc.DSPageSize;
import lhdt.ds.common.misc.DSPaginator;
import lhdt.ds.common.misc.DSPaginatorInfo;
import lhdt.ds.common.misc.DsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * 조망점 관리 View
 */
@Slf4j
@Controller
@RequestMapping("/ls-anals")
public class LandScapeAnalsController {
    @Autowired
    private LandScapeAnalsService landScapeService;
    @Autowired
    private CPDistricInfoService cpDistricInfoService;
    @Autowired
    private CPLocalInfoService cpLocalInfoService;

    @GetMapping()
    public String getNoticePage(
            @RequestParam(value = "landScapePage", defaultValue = "1") Integer landscape_page,
            Model model) {
        Page<LandScapeAnals> cpLocalInfoPage = landScapeService
                .findAllPgByStartPg(landscape_page -1, DSPageSize.NOTICE.getContent());
        model.addAttribute("landScapePage", cpLocalInfoPage);

        DSPaginatorInfo cpLocalPageNav = DSPaginator.getPaginatorMap(cpLocalInfoPage, DSPageSize.NOTICE);
        model.addAttribute("landScapePageInfo", cpLocalPageNav);

        return "/landscape/index";
    }

    @GetMapping("/{id}")
    public String getCPLocalInfo(@PathVariable(value = "id") Long id, Model model) {
        LandScapeAnals board = landScapeService.findById(id);
        model.addAttribute("landScapeInfo", board);
        return "content.back";
    }

    @GetMapping("/edit")
    public String addCityInfoByParam(Model model) {
        var apara2 = DsUtils.getEnum2Map(LandScapeAnalsType.class, 0);
        model.addAttribute("landScapeAnalsType", apara2);
        return "/landscape/edit";
    }

    @GetMapping("/edit/{id}")
    public String editLandScapeView(@PathVariable(value = "id") Long id, Model model) {
        LandScapeAnals localInfo = landScapeService.findById(id);
        model.addAttribute("landScapeInfo", localInfo);

        var landDistric = cpDistricInfoService.findAll();

        landDistric.stream()
                .map(p -> CPFullNameAndId.builder()
                        .id(p.getId())
                        .cpFullName(new CPFullName(p.getCpLocalInfo().getLocalName(), p.getDistrictName()))
                        .build()).collect(Collectors.toList());

        model.addAttribute("cpFullNameAndId", landDistric);
        return "/landscape/edit";
    }

    @PostMapping("/edit")
    public String registLandScape(Model model, LandScapeRegistParam landScapeRegistParam) {
        System.out.println(landScapeRegistParam.toString());
        LandScapeAnals landScapeAnals = new LandScapeAnals();
        landScapeAnals.setLandScapeAnalsName(landScapeRegistParam.getLandScapeAnalsName());
        if (landScapeRegistParam.getLandScapeAnalsType() == LandScapeAnalsType.점) {
            Point start_p = new Point(landScapeRegistParam.getStartPosX(), landScapeRegistParam.getStartPosY());
            landScapeAnals.setStartLandScapePos(start_p);
        } else {
            Point start_p = new Point(landScapeRegistParam.getStartPosX(), landScapeRegistParam.getStartPosY());
            landScapeAnals.setStartLandScapePos(start_p);
            Point end_p = new Point(landScapeRegistParam.getEndPosX(), landScapeRegistParam.getEndPosY());
            landScapeAnals.setEndLandScapePos(end_p);
        }
        landScapeAnals.setLandScapeAnalsType(landScapeRegistParam.getLandScapeAnalsType());
        var localInfo = landScapeService.regist(landScapeAnals);
        model.addAttribute("localInfo", localInfo);

        return "redirect:/ls-anals";
    }

    @PutMapping("/edit")
    public String editLandScape(Model model, LandScapeRegistParam landScapeRegistParam) {
        LandScapeAnals landScapeAnals = new LandScapeAnals();
        landScapeAnals.setLandScapeAnalsName(landScapeRegistParam.getLandScapeAnalsName());
        if (landScapeRegistParam.getLandScapeAnalsType() == LandScapeAnalsType.점) {
            Point start_p = new Point(landScapeRegistParam.getStartPosX(), landScapeRegistParam.getStartPosY());
            landScapeAnals.setStartLandScapePos(start_p);
        } else {
            Point start_p = new Point(landScapeRegistParam.getStartPosX(), landScapeRegistParam.getStartPosY());
            landScapeAnals.setStartLandScapePos(start_p);
            Point end_p = new Point(landScapeRegistParam.getEndPosX(), landScapeRegistParam.getEndPosY());
            landScapeAnals.setEndLandScapePos(end_p);
        }
        var localInfo = landScapeService.update(landScapeRegistParam.getId(), landScapeAnals);
        model.addAttribute("localInfo", localInfo);

        return "redirect:/ls-anals";
    }

}
