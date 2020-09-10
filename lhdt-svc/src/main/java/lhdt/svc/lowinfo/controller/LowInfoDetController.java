package lhdt.svc.lowinfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lhdt.svc.common.SvcController;
import lhdt.svc.lowinfo.domain.LowInfoDet;
import lhdt.svc.lowinfo.service.LowInfoDetService;

@RestController
@RequestMapping("/low_info_det")
public class LowInfoDetController extends SvcController {
    @Autowired
    private LowInfoDetService lowInfoDetService;

    /**
     * 모든 LowInfo 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select")
    public List<LowInfoDet> getCityPlanReportDet() {
        return this.lowInfoDetService.findAll();
    }

    /**
     * id에 부합하는 LowInfo 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select_one")
    public List<LowInfoDet> getCityPlanReportDetOne(Integer id) {
        return this.lowInfoDetService.findAllById(Long.valueOf(id));
    }

    /**
     * Uk에 대한 SubType이 존재하는지 확인합니다
     * @return
     */
    @GetMapping("/exists")
    public boolean existsCityPlanReportDet(Integer lowInfoId) {
        var spdt = new LowInfoDet();
        spdt.setId(Long.valueOf(lowInfoId));
        return this.lowInfoDetService.existVoByUk(spdt);
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
    public LowInfoDet insertCityPlanReportDet(LowInfoDet cprd) {
        var p = this.lowInfoDetService.registByUk(cprd);
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
    public LowInfoDet updateCityPlanReportDet(LowInfoDet cprdt) {
        var p = this.lowInfoDetService.findOneById(cprdt.getId());
        p.setLowInfoDetName(cprdt.getLowInfoDetName());
        p.setLowInfoDetContents(cprdt.getLowInfoDetContents());
        p = this.lowInfoDetService.update(p);
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
        this.lowInfoDetService.deleteAllById(Long.valueOf(id));
        return true;
    }
}
