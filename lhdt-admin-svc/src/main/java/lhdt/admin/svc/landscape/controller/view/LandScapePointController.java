package lhdt.admin.svc.landscape.controller.view;

import lhdt.admin.svc.cityplanning.service.CPDistricInfoService;
import lhdt.admin.svc.cityplanning.service.CPLocalInfoService;
import lhdt.admin.svc.landscape.domain.LandScapePoint;
import lhdt.admin.svc.landscape.model.LandScapeRegistParam;
import lhdt.admin.svc.landscape.service.LandScapePointService;
import lhdt.admin.svc.landscape.type.LSPointActionType;
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

/**
 * 조망점 관리 View
 */
@Slf4j
@Controller
@RequestMapping("/ls-point")
public class LandScapePointController {
    @Autowired
    private LandScapePointService landScapeService;
    @Autowired
    private CPDistricInfoService cpDistricInfoService;
    @Autowired
    private CPLocalInfoService cpLocalInfoService;

    @GetMapping()
    public String getNoticePage(
            @RequestParam(value = "landScapePage", defaultValue = "1") Integer landscape_page,
            Model model) {
        Page<LandScapePoint> cpLocalInfoPage = landScapeService
                .findAllPgByStartPg(landscape_page -1, DSPageSize.NOTICE.getContent());
        model.addAttribute("landScapePage", cpLocalInfoPage);

        DSPaginatorInfo cpLocalPageNav = DSPaginator.getPaginatorMap(cpLocalInfoPage, DSPageSize.NOTICE);
        model.addAttribute("landScapePageInfo", cpLocalPageNav);

        return "/landscape-point/index";
    }

    @GetMapping("/regist")
    public String addCityInfoByParam(Model model) {
        var apara2 = DsUtils.getEnum2Map(LandScapeAnalsType.class, 0);
        model.addAttribute("landScapeAnalsType", apara2);
        return "/landscape-point/edit";
    }

    @GetMapping("/edit/{id}")
    public String editLandScapeView(@PathVariable(value = "id") Long id, Model model) {
        LandScapePoint localInfo = landScapeService.findById(id);
        localInfo.setLSPointActionType(LSPointActionType.EDIT);
        model.addAttribute("landScapePointInfo", localInfo);

//        var landDistric = cpDistricInfoService.findAll();
//        landDistric.stream()
//                .map(p -> CPFullNameAndId.builder()
//                        .id(p.getId())
//                        .cpFullName(new CPFullName(p.getCpLocalInfo().getLocalName(), p.getDistrictName()))
//                        .build()).collect(Collectors.toList());
//
//        model.addAttribute("cpFullNameAndId", landDistric);
        return "/landscape-point/edit";
    }

    @GetMapping("/content/{id}")
    public String viewLandScapeView(@PathVariable(value = "id") Long id, Model model) {
        LandScapePoint localInfo = landScapeService.findById(id);
        localInfo.setLSPointActionType(LSPointActionType.CONTENT);
        model.addAttribute("landScapePointInfo", localInfo);

        return "/landscape-point/edit";
    }

    @PostMapping("/edit")
    public String registLandScape(Model model, LandScapeRegistParam landScapeRegistParam) {
        System.out.println(landScapeRegistParam.toString());
        LandScapePoint landScapePoint = new LandScapePoint();
        landScapePoint.setLandScapePointName(landScapeRegistParam.getLandScapeAnalsName());
        if (landScapeRegistParam.getLandScapeAnalsType() == LandScapeAnalsType.점) {
            Point start_p = new Point(landScapeRegistParam.getStartPosX(), landScapeRegistParam.getStartPosY());
            landScapePoint.setStartLandScapePos(start_p);
        } else {
            Point start_p = new Point(landScapeRegistParam.getStartPosX(), landScapeRegistParam.getStartPosY());
            landScapePoint.setStartLandScapePos(start_p);
            Point end_p = new Point(landScapeRegistParam.getEndPosX(), landScapeRegistParam.getEndPosY());
            landScapePoint.setEndLandScapePos(end_p);
        }
        landScapePoint.setLandScapePointType(landScapeRegistParam.getLandScapeAnalsType());
        var localInfo = landScapeService.regist(landScapePoint);
        model.addAttribute("localInfo", localInfo);

        return "redirect:/ls-point";
    }

    @PutMapping("/edit")
    public String editLandScape(Model model, LandScapeRegistParam landScapeRegistParam) {
        LandScapePoint landScapePoint = new LandScapePoint();
        landScapePoint.setLandScapePointName(landScapeRegistParam.getLandScapeAnalsName());
        if (landScapeRegistParam.getLandScapeAnalsType() == LandScapeAnalsType.점) {
            Point start_p = new Point(landScapeRegistParam.getStartPosX(), landScapeRegistParam.getStartPosY());
            landScapePoint.setStartLandScapePos(start_p);
        } else {
            Point start_p = new Point(landScapeRegistParam.getStartPosX(), landScapeRegistParam.getStartPosY());
            landScapePoint.setStartLandScapePos(start_p);
            Point end_p = new Point(landScapeRegistParam.getEndPosX(), landScapeRegistParam.getEndPosY());
            landScapePoint.setEndLandScapePos(end_p);
        }
        var localInfo = landScapeService.update(landScapeRegistParam.getId(), landScapePoint);
        model.addAttribute("localInfo", localInfo);

        return "redirect:/ls-point";
    }

}
