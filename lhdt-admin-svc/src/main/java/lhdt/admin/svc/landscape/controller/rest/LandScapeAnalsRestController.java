package lhdt.admin.svc.landscape.controller.rest;

import lhdt.admin.svc.cityplanning.service.CPDistricInfoService;
import lhdt.admin.svc.cityplanning.service.CPLocalInfoService;
import lhdt.admin.svc.landscape.domain.LandScapeAnals;
import lhdt.admin.svc.landscape.model.LandScapeRegistParam;
import lhdt.admin.svc.landscape.service.LandScapeAnalsService;
import lhdt.admin.svc.landscape.type.LandScapeAnalsType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/landscape-anals-rest")
public class LandScapeAnalsRestController {
    @Autowired
    private LandScapeAnalsService landScapeService;
    @Autowired
    private CPDistricInfoService cpDistricInfoService;
    @Autowired
    private CPLocalInfoService cpLocalInfoService;

    @PostMapping("/edit")
    public String registLandScape(LandScapeRegistParam landScapeRegistParam) {
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

        return "/adminsvc/landscape-anals";
    }
}
