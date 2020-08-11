package lhdt.anals.landscape.service.impl;

import lhdt.anals.landscape.domain.LandScapeDiffDet;
import lhdt.anals.landscape.persistance.LandScapeDiffDetRepository;
import lhdt.anals.landscape.persistance.LandScapeDiffRepository;
import lhdt.anals.landscape.service.LandScapeDiffDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LandScapeDiffDetServiceImpl extends LandScapeDiffDetService {
    @Autowired
    LandScapeDiffDetRepository landScapeDiffDetRepository;

    @Autowired
    LandScapeDiffRepository landScapeDiffRepository;

    @Override
    public LandScapeDiffDet save(LandScapeDiffDet vo) {
        return this.landScapeDiffDetRepository.save(vo);
    }

    @Override
    public List<LandScapeDiffDet> findAll() {
        ArrayList<LandScapeDiffDet> result = new ArrayList<>();
        this.landScapeDiffDetRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public LandScapeDiffDet findById(Long id) {
        return this.landScapeDiffDetRepository.findById(id).orElse(null);
    }

    @Override
    public List<LandScapeDiffDet> findAllById(Long id) {
        ArrayList<LandScapeDiffDet> result = new ArrayList<>();
        this.landScapeDiffDetRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public boolean existVoByUk(LandScapeDiffDet vo) {
        var p = this.landScapeDiffRepository.findById(vo.getId()).orElse(null);
        return this.landScapeDiffDetRepository.existsByFileNameAndFilePathAndLandScapeDiff(
                vo.getFileName(),vo.getFilePath(),p
        );
    }

    @Override
    public LandScapeDiffDet findByUk(LandScapeDiffDet vo) {
        var p = this.landScapeDiffRepository.findById(vo.getId()).orElse(null);
        return this.landScapeDiffDetRepository.findByFileNameAndFilePathAndLandScapeDiff(
                vo.getFileName(),vo.getFilePath(),p
        );
    }

    @Override
    public List<LandScapeDiffDet> findAllByUk(LandScapeDiffDet vo) {
        var p = this.landScapeDiffRepository.findById(vo.getId()).orElse(null);
        return this.landScapeDiffDetRepository.findAllByFileNameAndFilePathAndLandScapeDiff(
                vo.getFileName(),vo.getFilePath(),p
        );
    }

    @Override
    public void deleteByVo(LandScapeDiffDet vo) {
        this.landScapeDiffDetRepository.delete(vo);
    }
}
