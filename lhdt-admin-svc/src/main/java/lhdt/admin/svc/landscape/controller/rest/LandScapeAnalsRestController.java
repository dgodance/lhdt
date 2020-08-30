package lhdt.admin.svc.landscape.controller.rest;

import lhdt.admin.svc.cityplanning.service.CPDistricInfoService;
import lhdt.admin.svc.cityplanning.service.CPLocalInfoService;
import lhdt.admin.svc.landscape.domain.LandScapeAnals;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroupDTO.LSDiffGroupMeta;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroupDTO.LSDiffGroupTable;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroupDTO.LSDiffGroupTableDefault;
import lhdt.admin.svc.landscape.model.LandScapeRegistParam;
import lhdt.admin.svc.landscape.service.LandScapeAnalsService;
import lhdt.admin.svc.landscape.type.LandScapeAnalsType;
import lhdt.ds.common.misc.DSPageSize;
import lhdt.ds.common.misc.DSPaginator;
import lhdt.ds.common.misc.DSPaginatorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.geo.Point;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/ls-anals-rest")
public class LandScapeAnalsRestController {
    @Autowired
    private LandScapeAnalsService landScapeService;
    @Autowired
    private CPDistricInfoService cpDistricInfoService;
    @Autowired
    private CPLocalInfoService cpLocalInfoService;

    @PostMapping()
    public LSDiffGroupTable getNoticePage(
            @RequestParam(value = "lsGroupPage", defaultValue = "1") Integer landscape_page,
            Model model) {
        Page<LandScapeAnals> cpLocalInfoPage = landScapeService
                .findAllPgByStartPg(landscape_page -1, DSPageSize.NOTICE.getContent());

        DSPaginatorInfo cpLocalPageNav = DSPaginator.getPaginatorMap(cpLocalInfoPage, DSPageSize.NOTICE);

        LSDiffGroupTable p = new LSDiffGroupTable();
        var meta = new LSDiffGroupMeta(1,1,-1,100,"asc","id");
        p.setMeta(meta);
        landScapeService.findAll().forEach(obj -> {
            var lsdiffGroupTableDefault =  LSDiffGroupTableDefault.builder()
                    .Id(obj.getId()).lsDiffGrupName(obj.getLandScapeAnalsName())
                    .registDt(obj.getRegistDt()).updtDt(obj.getUpdtDt())
                    .build();
            p.getData().add(lsdiffGroupTableDefault);
        });

        return p;
    }
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
