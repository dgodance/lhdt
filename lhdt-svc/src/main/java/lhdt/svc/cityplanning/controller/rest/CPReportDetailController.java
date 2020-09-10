package lhdt.svc.cityplanning.controller.rest;

import java.io.IOException;
import java.util.List;

import lhdt.svc.cityplanning.service.CPFileInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lhdt.svc.cityplanning.domain.CPReportDetail;
import lhdt.svc.cityplanning.service.CPReportDetailService;
import lhdt.svc.cityplanning.service.CPReportParserService;
import lhdt.svc.common.SvcController;

@RestController
@RequestMapping("/cityplanning")
@RequiredArgsConstructor
public class CPReportDetailController extends SvcController {
    private final CPFileInfoService CPFileInfoService;

    private final CPReportDetailService CPReportDetailService;

    private final CPReportParserService CPReportParserService;

    /**
     * 모든 LowInfo 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select")
    public List<CPReportDetail> getCityPlanReportDet() {
        return this.CPReportDetailService.findAll();
    }

    /**
     * id에 부합하는 LowInfo 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select_one")
    public List<CPReportDetail> getCityPlanReportDetOne(Integer id) {
        return this.CPReportDetailService.findAllById(Long.valueOf(id));
    }

    /**
     * 하나의 데이터를 입력합니다
     *
     * cityPlanId:5
     * area:50
     * allowableUse:aaa
     * notAllowableUse:aaa
     * buildingToLandRatio:50
     * floorAreaRatio:50
     * areaMaxHeight:5
     * floorMaxHeight:10
     * areaUpDownType:UP
     * floorUpDownType:DOWN
     *
     * @return
     */
    @PostMapping(path = "/insert")
    public CPReportDetail insertCityPlanReportDet(CPReportDetail cprd) {
        var p = this.CPReportDetailService.registByUk(cprd);
        if (p == null) {
            return null;
        } else {
            return p;
        }
    }

    /**
     * 특정 데이터를 업데이트합니다.
     *
     * @return
     */
    @PutMapping("/update")
    public CPReportDetail updateCityPlanReportDet(CPReportDetail cprdt) {
        var p = this.CPReportDetailService.findOneById(cprdt.getId());
        p.setAllowableUse(cprdt.getAllowableUse());
        p.setNotAllowableUse(cprdt.getAllowableUse());
        p.setBuildingToLandRatio(cprdt.getBuildingToLandRatio());
        p.setFloorAreaRatio(cprdt.getFloorAreaRatio());
        p.setAreaMaxHeight(cprdt.getAreaMaxHeight());
        p.setFloorMaxHeight(cprdt.getFloorMaxHeight());
        p.setAreaUpDownType(cprdt.getAreaUpDownType());
        p.setFloorUpDownType(cprdt.getFloorUpDownType());
        p = this.CPReportDetailService.update(p);
        if(p == null) {
            return null;
        } else {
            return p;
        }
    }

    /**
     * 데이터를 삭제합니다
     * @return
     */
    @DeleteMapping("/delete")
    public boolean deleteExample(Integer id) {
        this.CPReportDetailService.deleteAllById(Long.valueOf(id));
        return true;
    }

    @GetMapping("/area")
    public Object getMockAreaByFilePath(String fileName) {
        return super.file2Object("D:\\data\\dumi_sample\\" + fileName);
    }

    @GetMapping("cityplanExcel")
    public List<CPReportDetail> getCityPlanExcel() throws IOException, InvalidFormatException {
        var cityId = this.CPFileInfoService.findOneById(Long.valueOf(4));
        String fullFilePath = "D:\\Depot_Paper\\2020_LH디지털트윈1단계구축\\기본데이터셋\\sample_excel.xlsx";
        List<CPReportDetail> p = null;
        p = this.CPReportParserService.procExcelDataByCityPlan(fullFilePath);
        if(p == null)
            throw new NullPointerException();
        for(var obj : p) {
            obj.setCPFileInfo(cityId);
        }
        return this.CPReportDetailService.registAllByUk(p);
    }

    @PostMapping("")
    public void postShpProcess() {

    }
}
