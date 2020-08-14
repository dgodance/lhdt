package lhdt.svc.landscape.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lhdt.svc.landscape.domain.LandScapeDiff;
import lhdt.svc.landscape.persistence.LandScapeDiffRepository;
import lhdt.svc.landscape.service.LandScapeDiffService;

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
