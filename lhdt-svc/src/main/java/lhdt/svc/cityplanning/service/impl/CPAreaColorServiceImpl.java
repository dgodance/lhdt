package lhdt.svc.cityplanning.service.impl;

import lhdt.svc.cityplanning.domain.CPAreaColor;
import lhdt.svc.cityplanning.persistence.CPAreaColorRepository;
import lhdt.svc.cityplanning.service.CPAreaColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CPAreaColorServiceImpl extends CPAreaColorService {

    @Autowired
    CPAreaColorRepository cpacr;

    @Override
    public CPAreaColor save(CPAreaColor vo) {
        return this.cpacr.save(vo);
    }

    @Override
    public List<CPAreaColor> findAll() {
        ArrayList<CPAreaColor> result = new ArrayList<>();
        this.cpacr.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<CPAreaColor> findAllById(Long id) {
        ArrayList<Long> param = new ArrayList<>();
        param.add(id);
        ArrayList<CPAreaColor> result = new ArrayList<>();
        this.cpacr.findAllById(param).forEach(result::add);
        return result;
    }

    @Override
    public Page<CPAreaColor> findAllByPage(Pageable pageable) {
        return this.cpacr.findAll(pageable);
    }

    @Override
    public boolean existVoByUk(CPAreaColor vo) {
        return this.cpacr.existsByAreaName(vo.getAreaName());
    }

    @Override
    public CPAreaColor findByUk(CPAreaColor vo) {
        return this.cpacr.findByAreaName(vo.getAreaName());
    }

    @Override
    public List<CPAreaColor> findAllByUk(CPAreaColor vo) {
        return this.cpacr.findAllByAreaName(vo.getAreaName());
    }

    @Override
    public void deleteByVo(CPAreaColor vo) {
        this.cpacr.delete(vo);
    }
}
