package lhdt.anals.hello.service.impl;

import lhdt.anals.hello.domain.SubType0;
import lhdt.anals.hello.domain.ViewAnalsLoca;
import lhdt.anals.hello.persistence.ViewAnalsLocaRepository;
import lhdt.anals.hello.service.ViewAnalsLocaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("viewAnalsLocaService")
public class ViewAnalsLocaServiceImpl extends ViewAnalsLocaService {

    @Autowired
    public ViewAnalsLocaRepository viewAnalsLocaRepository;

    @Override
    public ViewAnalsLoca save(ViewAnalsLoca vo) {
        return viewAnalsLocaRepository.save(vo);
    }

    @Override
    public List<ViewAnalsLoca> findAll() {
        ArrayList<ViewAnalsLoca> result = new ArrayList<ViewAnalsLoca>();
        this.viewAnalsLocaRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public ViewAnalsLoca findById(Long id) {
        return this.viewAnalsLocaRepository.findById(id).orElse(null);
    }

    @Override
    public List<ViewAnalsLoca> findAllById(Long id) {
        ArrayList<ViewAnalsLoca> result = new ArrayList<ViewAnalsLoca>();
        this.viewAnalsLocaRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public boolean existVoByUk(ViewAnalsLoca vo) {
        return this.viewAnalsLocaRepository.existsByCateIdAndViewAnalsName(vo.getCateId(), vo.getViewAnalsName());
    }

    @Override
    public ViewAnalsLoca findByUk(ViewAnalsLoca vo) {
        return this.viewAnalsLocaRepository.findByCateIdAndViewAnalsName(vo.getCateId(), vo.getViewAnalsName());
    }

    @Override
    public List<ViewAnalsLoca> findAllByUk(ViewAnalsLoca vo) {
        return this.viewAnalsLocaRepository.findAllByCateIdAndViewAnalsName(vo.getCateId(), vo.getViewAnalsName());
    }

    @Override
    public void deleteByVo(ViewAnalsLoca vo) {
        this.viewAnalsLocaRepository.delete(vo);
    }
}
