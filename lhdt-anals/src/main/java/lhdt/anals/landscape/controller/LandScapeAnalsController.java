package lhdt.anals.landscape.controller;

import lhdt.anals.common.AnalsController;
import lhdt.anals.landscape.domain.LandScapeAnals;
import lhdt.anals.landscape.service.LandScapeAnalsService;
import lhdt.anals.landscape.types.LandScapeAnalsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/landscape_anals")
public class LandScapeAnalsController extends AnalsController {
    @Autowired
    private LandScapeAnalsService landScapeAnalsService;


    /**
     * 모든 LowInfo 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select")
    public List<LandScapeAnals> getCityPlanReportDet() {
        return this.landScapeAnalsService.findAll();
    }

    /**
     * id에 부합하는 LowInfo 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select_one")
    public List<LandScapeAnals> getCityPlanReportDetOne(Integer id) {
        return this.landScapeAnalsService.findAllById(Long.valueOf(id));
    }

    /**
     * Uk에 대한 SubType이 존재하는지 확인합니다
     * @return
     */
    @GetMapping("/exists")
    public boolean existsCityPlanReportDet(Integer id, LandScapeAnalsType landScapeAnalsType) {
        var spdt = new LandScapeAnals();
        spdt.setId(Long.valueOf(id));
        spdt.setLandScapeAnalsType(landScapeAnalsType);
        return this.landScapeAnalsService.existVoByUk(spdt);
    }

    /**
     * 하나의 데이터를 입력합니다
     *
     * @return
     */
    @PostMapping(path = "/insert")
    public LandScapeAnals insertCityPlanReportDet(LandScapeAnals cprd) {
        var p = this.landScapeAnalsService.registByUk(cprd);
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
    public LandScapeAnals updateCityPlanReportDet(LandScapeAnals cprdt) {
        var p = this.landScapeAnalsService.findById(cprdt.getId());
        p.setLandScapeAnalsName(cprdt.getLandScapeAnalsName());
        p.setStartLandScape(cprdt.getStartLandScape());
        p.setEndLandScape(cprdt.getEndLandScape());
        p.setLandScapeAnalsType(cprdt.getLandScapeAnalsType());
        p = this.landScapeAnalsService.update(p);
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
        this.landScapeAnalsService.deleteAllById(Long.valueOf(id));
        return true;
    }
}
