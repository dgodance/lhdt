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
import lhdt.admin.svc.landscape.type.LSPointActionType;
import lhdt.admin.svc.landscape.type.LandScapeAnalsType;
import lhdt.cmmn.misc.CmmnPageSize;
import lhdt.cmmn.misc.CmmnPaginator;
import lhdt.cmmn.misc.CmmnPaginatorInfo;
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

    @GetMapping("/init")
    public void initLandScapePoint() {
        var 관문체육공원 = new LandScapePoint();
            관문체육공원.setLandScapePointName("관문체육공원");
            관문체육공원.setLandScapePointType(LandScapeAnalsType.점);
            관문체육공원.setStartLandScapePos(new Point(126.99875270102235, 37.44287398124129));
        var 국립과천과학관 = new LandScapePoint();
            국립과천과학관.setLandScapePointName("국립과천과학관");
            국립과천과학관.setLandScapePointType(LandScapeAnalsType.점);
            국립과천과학관.setStartLandScapePos(new Point(127.00540150129355, 37.43927881140079));
        var 렛츠런파크 = new LandScapePoint();
            렛츠런파크.setLandScapePointName("렛츠런파크(경마공원)");
            렛츠런파크.setLandScapePointType(LandScapeAnalsType.점);
            렛츠런파크.setStartLandScapePos(new Point(127.01562276173183, 37.44720187165418));
        var 경관축1 = new LandScapePoint();
            경관축1.setLandScapePointName("경관축-1");
            경관축1.setLandScapePointType(LandScapeAnalsType.선);
            경관축1.setStartLandScapePos(new Point(126.99896976329445, 37.44853931256331));
            경관축1.setEndLandScapePos(new Point(127.01533500746086, 37.454836635719914));
        var 경관축2 = new LandScapePoint();
            경관축2.setLandScapePointName("경관축-2");
            경관축2.setLandScapePointType(LandScapeAnalsType.선);
            경관축2.setStartLandScapePos(new Point(127.0097119874613, 37.45487159803625));
            경관축2.setEndLandScapePos(new Point(127.01207488668167, 37.450720315122005));
        var 경관축3  = new LandScapePoint();
            경관축3.setLandScapePointName("경관축-3");
            경관축3.setLandScapePointType(LandScapeAnalsType.선);
            경관축3.setStartLandScapePos(new Point(127.00667087736848, 37.45249937252622));
            경관축3.setEndLandScapePos(new Point(127.00758985145977, 37.45098191657632));
        var resultList = new ArrayList<LandScapePoint>();
        resultList.add(관문체육공원);
        resultList.add(국립과천과학관);
        resultList.add(렛츠런파크);
        resultList.add(경관축1);
        resultList.add(경관축2);
        resultList.add(경관축3);
        resultList.forEach(p -> landScapeService.regist(p));
    }

    @GetMapping
    public PageParam<LandScapeAnalsTable> getNoticePage(
            @RequestParam(value = "lsDiffPage", defaultValue = "1") Integer nowPageNum) {
        Page<LandScapePoint> cpLocalInfoPage = landScapeService.findAllPgByStartPg(nowPageNum-1, CmmnPageSize.NOTICE.getContent());

        CmmnPaginatorInfo cpLocalPageNav = CmmnPaginator.getPaginatorMap(cpLocalInfoPage, CmmnPageSize.NOTICE);
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

    @GetMapping("/{id}")
    public LandScapePoint getLSPointById(
            @PathVariable(value = "id") Long id) {
        LandScapePoint lp = landScapeService.findById(id);
        return lp;
    }

    @PostMapping()
    public LSDiffGroupTable getNoticePage(
            @RequestParam(value = "lsGroupPage", defaultValue = "1") Integer landscape_page,
            Model model) {
        Page<LandScapePoint> cpLocalInfoPage = landScapeService
                .findAllPgByStartPg(landscape_page -1, CmmnPageSize.NOTICE.getContent());

        CmmnPaginatorInfo cpLocalPageNav = CmmnPaginator.getPaginatorMap(cpLocalInfoPage, CmmnPageSize.NOTICE);

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
