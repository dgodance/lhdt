package lhdt.anals.cityplanning.controller;

import lhdt.anals.cityplanning.domain.CityPlanReportDetail;
import lhdt.anals.cityplanning.service.CityPlanReportDetailService;
import lhdt.anals.common.AnalsController;
import lhdt.anals.hello.domain.Child;
import lhdt.anals.hello.domain.SubType0;
import lhdt.anals.hello.service.SampleService;
import lhdt.anals.hello.types.DefaultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cityplanning")
public class CityPlanAreaController extends AnalsController {

    @Autowired
    private CityPlanReportDetailService cityPlanReportDetailService;


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
    public boolean existsCityPlanReportDet(Integer id, Integer cityPlanId) {
        var spdt = new CityPlanReportDetail();
        spdt.setId(Long.valueOf(id));
        spdt.setCityPlanId(Long.valueOf(cityPlanId));
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
        var p = this.cityPlanReportDetailService.findById(cprdt.getId());
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
}
