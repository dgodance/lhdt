package lhdt.admin.svc.landscape.controller.rest;

import lhdt.admin.svc.cityplanning.service.CPDistricInfoService;
import lhdt.admin.svc.cityplanning.service.CPLocalInfoService;
import lhdt.admin.svc.common.model.PageParam;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffDefault;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroupDTO.LSDiffGroupMeta;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroupDTO.LSDiffGroupTable;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroupDTO.LSDiffGroupTableDefault;
import lhdt.admin.svc.landscape.domain.LandScapePoint;
import lhdt.admin.svc.landscape.domain.landScapeAnalsDTO.LandScapeAnalsTable;
import lhdt.admin.svc.landscape.model.LandScapeRegistParam;
import lhdt.admin.svc.landscape.service.LandScapePointService;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ls-point-rest")
public class LandScapePointRestController {
    @Autowired
    private LandScapePointService landScapeService;
    @Autowired
    private CPDistricInfoService cpDistricInfoService;
    @Autowired
    private CPLocalInfoService cpLocalInfoService;

    @GetMapping
    public PageParam<LandScapeAnalsTable> getNoticePage(
            @RequestParam(value = "lsDiffPage", defaultValue = "1") Integer nowPageNum) {
        Page<LandScapePoint> cpLocalInfoPage = landScapeService.findAllPgByStartPg(nowPageNum-1, DSPageSize.NOTICE.getContent());

        DSPaginatorInfo cpLocalPageNav = DSPaginator.getPaginatorMap(cpLocalInfoPage, DSPageSize.NOTICE);
        List<LandScapeAnalsTable> result = new ArrayList<>();
        for (LandScapePoint landScapePoint : cpLocalInfoPage.getContent()) {
            var lsat = new LandScapeAnalsTable();
            lsat.setId(landScapePoint.getId());
            lsat.setLandScapePointName(landScapePoint.getLandScapePointName());
            lsat.setEndLandScapePos(landScapePoint.getEndLandScapePos());
            lsat.setStartAltitude(landScapePoint.getStartAltitude());
            lsat.setEndAltitude(landScapePoint.getEndAltitude());
            lsat.setLandScapePointType(landScapePoint.getLandScapePointType());
            if(landScapePoint.getLandScapePointType() == LandScapeAnalsType.선) {
                lsat.setViewAction(true);
                lsat.setAnalsAction(true);
            } else {
                lsat.setViewAction(true);
                lsat.setAnalsAction(false);
            }
            result.add(lsat);
        }

        var sendParam = new PageParam<LandScapeAnalsTable>();
        sendParam.setPage(result);
        sendParam.setPagenationInfo(cpLocalPageNav);
        sendParam.setSize(cpLocalInfoPage.getSize());
        sendParam.setNowPageNum(nowPageNum -1);;
        return sendParam;
    }

    @PostMapping()
    public LSDiffGroupTable getNoticePage(
            @RequestParam(value = "lsGroupPage", defaultValue = "1") Integer landscape_page,
            Model model) {
        Page<LandScapePoint> cpLocalInfoPage = landScapeService
                .findAllPgByStartPg(landscape_page -1, DSPageSize.NOTICE.getContent());

        DSPaginatorInfo cpLocalPageNav = DSPaginator.getPaginatorMap(cpLocalInfoPage, DSPageSize.NOTICE);

        LSDiffGroupTable p = new LSDiffGroupTable();
        var meta = new LSDiffGroupMeta(1,1,-1,100,"asc","id");
        p.setMeta(meta);
        landScapeService.findAll().forEach(obj -> {
            var lsdiffGroupTableDefault =  LSDiffGroupTableDefault.builder()
                    .Id(obj.getId()).lsDiffGrupName(obj.getLandScapePointName())
                    .registDt(obj.getRegistDt()).updtDt(obj.getUpdtDt())
                    .build();
            p.getData().add(lsdiffGroupTableDefault);
        });

        return p;
    }

    @PostMapping("/edit")
    public String registLandScape(LandScapeRegistParam landScapeRegistParam) {
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

        return "/adminsvc/ls-point";
    }
}
