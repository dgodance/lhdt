package lhdt.anals.landscape.service.impl;

import lhdt.anals.landscape.domain.LandScapeAnals;
import lhdt.anals.landscape.persistance.LandScapeAnalsRepository;
import lhdt.anals.landscape.service.LandScapeAnalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public LandScapeAnals findById(Long id) {
        return this.landScapeAnalsRepository.findById(id).orElse(null);
    }

    @Override
    public List<LandScapeAnals> findAllById(Long id) {
        ArrayList<LandScapeAnals> result = new ArrayList<>();
        this.landScapeAnalsRepository.findAll().forEach(result::add);
        return result;
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
