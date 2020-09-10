package lhdt.svc.landscape.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lhdt.svc.landscape.domain.LandScapeAnals;
import lhdt.svc.landscape.persistence.LandScapeAnalsRepository;
import lhdt.svc.landscape.service.LandScapeAnalsService;

@Service
public class LandScapeAnalsServiceImpl extends LandScapeAnalsService {
    @Autowired
    LandScapeAnalsRepository landScapeAnalsRepository;

    @Override
    public LandScapeAnals save(LandScapeAnals vo) {
        return this.landScapeAnalsRepository.save(vo);
    }

    @Override
    public List<LandScapeAnals> findAll() {
        ArrayList<LandScapeAnals> result = new ArrayList<>();
        this.landScapeAnalsRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<LandScapeAnals> findAllById(Long id) {
        ArrayList<LandScapeAnals> result = new ArrayList<>();
        this.landScapeAnalsRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Page<LandScapeAnals> findAllByPage(Pageable pageable) {
        return this.landScapeAnalsRepository.findAll(pageable);
    }

    @Override
    public boolean existVoByUk(LandScapeAnals vo) {
        return this.landScapeAnalsRepository
                .existsByLandScapeAnalsNameAndLandScapeAnalsType(vo.getLandScapeAnalsName(),
                        vo.getLandScapeAnalsType());
    }

    @Override
    public LandScapeAnals findByUk(LandScapeAnals vo) {
        return this.landScapeAnalsRepository.findByLandScapeAnalsNameAndLandScapeAnalsType(vo.getLandScapeAnalsName(),
                vo.getLandScapeAnalsType());
    }

    @Override
    public List<LandScapeAnals> findAllByUk(LandScapeAnals vo) {
        return this.landScapeAnalsRepository.findAllByLandScapeAnalsNameAndLandScapeAnalsType(vo.getLandScapeAnalsName(),
                vo.getLandScapeAnalsType());
    }

    @Override
    public void deleteByVo(LandScapeAnals vo) {
        this.landScapeAnalsRepository.delete(vo);
    }
}
