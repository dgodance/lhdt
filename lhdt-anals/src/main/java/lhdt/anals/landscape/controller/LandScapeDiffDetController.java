package lhdt.anals.landscape.controller;

import lhdt.anals.common.AnalsController;
import lhdt.anals.landscape.domain.LandScapeDiffDet;
import lhdt.anals.landscape.service.LandScapeDiffDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/landscape_diff_det")
public class LandScapeDiffDetController extends AnalsController {
    @Autowired
    private LandScapeDiffDetService landScapeDiffDetService;


    /**
     * 모든 LowInfo 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select")
    public List<LandScapeDiffDet> getCityPlanReportDet() {
        return this.landScapeDiffDetService.findAll();
    }

    /**
     * id에 부합하는 LowInfo 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select_one")
    public List<LandScapeDiffDet> getCityPlanReportDetOne(Integer id) {
        return this.landScapeDiffDetService.findAllById(Long.valueOf(id));
    }

    /**
     * Uk에 대한 SubType이 존재하는지 확인합니다
     * @return
     */
    @GetMapping("/exists")
    public boolean existsCityPlanReportDet(LandScapeDiffDet lsdd) {
        var spdt = new LandScapeDiffDet();
        spdt.setFileName(lsdd.getFileName());
        spdt.setFilePath(lsdd.getFilePath());
        spdt.setLandScapeDiff(lsdd.getLandScapeDiff());
        return this.landScapeDiffDetService.existVoByUk(spdt);
    }

    /**
     * 하나의 데이터를 입력합니다
     *
     * @return
     */
    @PostMapping(path = "/insert")
    public LandScapeDiffDet insertCityPlanReportDet(LandScapeDiffDet cprd) {
        var p = this.landScapeDiffDetService.registByUk(cprd);
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
    public LandScapeDiffDet updateCityPlanReportDet(LandScapeDiffDet cprdt) {
        var p = this.landScapeDiffDetService.findById(cprdt.getId());
        p.setFileName(cprdt.getFileName());
        p.setFilePath(cprdt.getFilePath());
        p = this.landScapeDiffDetService.update(p);
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
        this.landScapeDiffDetService.deleteAllById(Long.valueOf(id));
        return true;
    }
}
