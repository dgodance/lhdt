package lhdt.svc.cityplanning.service.impl;

import lhdt.svc.cityplanning.domain.CityInfo;
import lhdt.svc.cityplanning.domain.CityPlanAreaColor;
import lhdt.svc.cityplanning.persistence.CityInfoRepository;
import lhdt.svc.cityplanning.persistence.CityPlanAreaColorRepository;
import lhdt.svc.cityplanning.service.CityInfoService;
import lhdt.svc.cityplanning.service.CityPlanAreaColorService;
import lhdt.svc.cityplanning.service.CityPlanReportParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityPlanAreaColorServiceImpl extends CityPlanAreaColorService {

    @Autowired
    CityPlanAreaColorRepository cpacr;

    @Override
    public CityPlanAreaColor save(CityPlanAreaColor vo) {
        return this.cpacr.save(vo);
    }

    @Override
    public List<CityPlanAreaColor> findAll() {
        ArrayList<CityPlanAreaColor> result = new ArrayList<>();
        this.cpacr.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<CityPlanAreaColor> findAllById(Long id) {
        ArrayList<Long> param = new ArrayList<>();
        param.add(id);
        ArrayList<CityPlanAreaColor> result = new ArrayList<>();
        this.cpacr.findAllById(param).forEach(result::add);
        return result;
    }

    @Override
    public boolean existVoByUk(CityPlanAreaColor vo) {
        return this.cpacr.existsByAreaName(vo.getAreaName());
    }

    @Override
    public CityPlanAreaColor findByUk(CityPlanAreaColor vo) {
        return this.cpacr.findByAreaName(vo.getAreaName());
    }

    @Override
    public List<CityPlanAreaColor> findAllByUk(CityPlanAreaColor vo) {
        return this.cpacr.findAllByAreaName(vo.getAreaName());
    }

    @Override
    public void deleteByVo(CityPlanAreaColor vo) {
        this.cpacr.delete(vo);
    }
}
