package lhdt.svc.cityplanning.controller;

import java.io.IOException;
import java.util.List;

import lhdt.svc.cityplanning.domain.CityInfo;
import lhdt.svc.cityplanning.service.CityInfoService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lhdt.svc.cityplanning.domain.CityPlanReportDetail;
import lhdt.svc.cityplanning.service.CityPlanReportDetailService;
import lhdt.svc.cityplanning.service.CityPlanReportParserService;
import lhdt.svc.common.SvcController;

@RestController
@RequestMapping("/cityplanning")
public class CityPlanAreaController extends SvcController {
    @Autowired
    private CityInfoService cityInfoService;

    @Autowired
    private CityPlanReportDetailService cityPlanReportDetailService;

    @Autowired
    private CityPlanReportParserService cityPlanReportParserService;

    /**
     * 모든 LowInfo 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select")
    public List<CityPlanReportDetail> getCityPlanReportDet() {
        return this.cityPlanReportDetailService.findAll();
    }

    /**
     * id에 부합하는 LowInfo 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select_one")
    public List<CityPlanReportDetail> getCityPlanReportDetOne(Integer id) {
        return this.cityPlanReportDetailService.findAllById(Long.valueOf(id));
    }

    /**
     * Uk에 대한 SubType이 존재하는지 확인합니다
     * @return
     */
    @GetMapping("/exists")
    public boolean existsCityPlanReportDet(Long cityPlanId, String drawingId, String householdId) {
        var spdt = new CityPlanReportDetail();
        var p = this.cityInfoService.findOneById(cityPlanId);
        spdt.setCityInfo(p);
        spdt.setDrawingId(drawingId);
        spdt.setHouseholdId(householdId);
        return this.cityPlanReportDetailService.existVoByUk(spdt);
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
    public CityPlanReportDetail insertCityPlanReportDet(CityPlanReportDetail cprd) {
        var p = this.cityPlanReportDetailService.registByUk(cprd);
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
    public CityPlanReportDetail updateCityPlanReportDet(CityPlanReportDetail cprdt) {
        var p = this.cityPlanReportDetailService.findOneById(cprdt.getId());
        p.setAllowableUse(cprdt.getAllowableUse());
        p.setNotAllowableUse(cprdt.getAllowableUse());
        p.setBuildingToLandRatio(cprdt.getBuildingToLandRatio());
        p.setFloorAreaRatio(cprdt.getFloorAreaRatio());
        p.setAreaMaxHeight(cprdt.getAreaMaxHeight());
        p.setFloorMaxHeight(cprdt.getFloorMaxHeight());
        p.setAreaUpDownType(cprdt.getAreaUpDownType());
        p.setFloorUpDownType(cprdt.getFloorUpDownType());
        p = this.cityPlanReportDetailService.update(p);
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
        this.cityPlanReportDetailService.deleteAllById(Long.valueOf(id));
        return true;
    }

    @GetMapping("/area")
    public Object getMockAreaByFilePath(String fileName) {
        return super.file2Object("D:\\data\\dumi_sample\\" + fileName);
    }

    @GetMapping("cityplanExcel")
    public List<CityPlanReportDetail> getCityPlanExcel() throws IOException, InvalidFormatException {
        var cityId = this.cityInfoService.findOneById(Long.valueOf(4));
        String fullFilePath = "D:\\Depot_Paper\\2020_LH디지털트윈1단계구축\\기본데이터셋\\sample_excel.xlsx";
        List<CityPlanReportDetail> p = null;
        p = this.cityPlanReportParserService.procExcelDataByCityPlan(fullFilePath);
        if(p == null)
            throw new NullPointerException();
        for(var obj : p) {
            obj.setCityInfo(cityId);
        }
        return this.cityPlanReportDetailService.registAllByUk(p);
    }

    @PostMapping("")
    public void postShpProcess() {

    }
}
