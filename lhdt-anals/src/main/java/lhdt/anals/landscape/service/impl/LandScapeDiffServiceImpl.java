package lhdt.anals.landscape.service.impl;

import lhdt.anals.landscape.domain.LandScapeDiff;
import lhdt.anals.landscape.persistance.LandScapeDiffRepository;
import lhdt.anals.landscape.service.LandScapeDiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LandScapeDiffServiceImpl extends LandScapeDiffService {
    @Autowired
    LandScapeDiffRepository landScapeDiffRepository;

    @Override
    public LandScapeDiff save(LandScapeDiff vo) {
        return this.landScapeDiffRepository.save(vo);
    }

    @Override
    public List<LandScapeDiff> findAll() {
        ArrayList<LandScapeDiff> result = new ArrayList<LandScapeDiff>();
        this.landScapeDiffRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public LandScapeDiff findById(Long id) {
        return this.landScapeDiffRepository.findById(id).orElse(null);
    }

    @Override
    public List<LandScapeDiff> findAllById(Long id) {
        ArrayList<LandScapeDiff> result = new ArrayList<>();
        this.landScapeDiffRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public boolean existVoByUk(LandScapeDiff vo) {
        return this.landScapeDiffRepository.existsByLandScapeDiffName(vo.getLandScapeDiffName());
    }

    @Override
    public LandScapeDiff findByUk(LandScapeDiff vo) {
        return this.landScapeDiffRepository.findByLandScapeDiffName(vo.getLandScapeDiffName());
    }

    @Override
    public List<LandScapeDiff> findAllByUk(LandScapeDiff vo) {
        return this.landScapeDiffRepository.findAllByLandScapeDiffName(vo.getLandScapeDiffName());
    }

    @Override
    public void deleteByVo(LandScapeDiff vo) {
        this.landScapeDiffRepository.delete(vo);
    }
}
