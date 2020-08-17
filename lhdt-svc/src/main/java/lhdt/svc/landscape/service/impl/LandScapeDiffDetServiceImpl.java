package lhdt.svc.landscape.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lhdt.svc.landscape.domain.LandScapeDiffDet;
import lhdt.svc.landscape.persistence.LandScapeDiffDetRepository;
import lhdt.svc.landscape.persistence.LandScapeDiffRepository;
import lhdt.svc.landscape.service.LandScapeDiffDetService;

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
    public List<LandScapeDiffDet> findAllById(Long id) {
        ArrayList<LandScapeDiffDet> result = new ArrayList<>();
        this.landScapeDiffDetRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Page<LandScapeDiffDet> findAllByPage(Pageable pageable) {
        return this.landScapeDiffDetRepository.findAll(pageable);
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
