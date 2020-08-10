package lhdt.anals.landscape.controller;

import lhdt.anals.common.AnalsController;
import lhdt.anals.landscape.domain.LandScapeDiff;
import lhdt.anals.landscape.service.LandScapeDiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/landscape_diff")
public class LandScapeDiffController extends AnalsController {
    @Autowired
    private LandScapeDiffService landScapeDiffService;


    /**
     * 모든 LowInfo 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select")
    public List<LandScapeDiff> getCityPlanReportDet() {
        return this.landScapeDiffService.findAll();
    }

    /**
     * id에 부합하는 LowInfo 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select_one")
    public List<LandScapeDiff> getCityPlanReportDetOne(Integer id) {
        return this.landScapeDiffService.findAllById(Long.valueOf(id));
    }

    /**
     * Uk에 대한 SubType이 존재하는지 확인합니다
     * @return
     */
    @GetMapping("/exists")
    public boolean existsCityPlanReportDet(String landScapeDiffName) {
        var spdt = new LandScapeDiff();
        spdt.setLandScapeDiffName(landScapeDiffName);
        return this.landScapeDiffService.existVoByUk(spdt);
    }

    /**
     * 하나의 데이터를 입력합니다
     *
     * @return
     */
    @PostMapping(path = "/insert")
    public LandScapeDiff insertCityPlanReportDet(LandScapeDiff cprd) {
        var p = this.landScapeDiffService.registByUk(cprd);
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
    public LandScapeDiff updateCityPlanReportDet(LandScapeDiff cprdt) {
        var p = this.landScapeDiffService.findById(cprdt.getId());
        p.setLandScapeDiffName(cprdt.getLandScapeDiffName());
        p = this.landScapeDiffService.update(p);
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
        this.landScapeDiffService.deleteAllById(Long.valueOf(id));
        return true;
    }
}
