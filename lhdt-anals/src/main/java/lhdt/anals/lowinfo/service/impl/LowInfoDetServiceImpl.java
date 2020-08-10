package lhdt.anals.lowinfo.service.impl;

import lhdt.anals.lowinfo.domain.LowInfoDet;
import lhdt.anals.lowinfo.persistance.LowInfoDetRepository;
import lhdt.anals.lowinfo.persistance.LowInfoRepository;
import lhdt.anals.lowinfo.service.LowInfoDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LowInfoDetServiceImpl extends LowInfoDetService {
    @Autowired
    LowInfoDetRepository lowInfoDetRepository;

    @Autowired
    LowInfoRepository lowInfoRepository;


    @Override
    public LowInfoDet save(LowInfoDet vo) {
        return this.lowInfoDetRepository.save(vo);
    }

    @Override
    public List<LowInfoDet> findAll() {
        ArrayList<LowInfoDet> result = new ArrayList<LowInfoDet>();
        this.lowInfoDetRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public LowInfoDet findById(Long id) {
        return this.lowInfoDetRepository.findById(id).orElse(null);
    }

    @Override
    public List<LowInfoDet> findAllById(Long id) {
        ArrayList<LowInfoDet> result = new ArrayList<LowInfoDet>();
        this.lowInfoDetRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public boolean existVoByUk(LowInfoDet vo) {
        var lowInfo = this.lowInfoRepository.findById(vo.getId()).orElse(null);
        vo.setLowInfo(lowInfo);
        return this.lowInfoDetRepository.existsByIdAndLowInfoDetNameAndLowInfo(vo.getId(),vo.getLowInfoDetName(),
                vo.getLowInfo());
    }

    @Override
    public LowInfoDet findByUk(LowInfoDet vo) {
        var lowInfo = this.lowInfoRepository.findById(vo.getId()).orElse(null);
        vo.setLowInfo(lowInfo);
        return this.lowInfoDetRepository.findByIdAndLowInfoDetNameAndLowInfo(vo.getId(),vo.getLowInfoDetName(),
                vo.getLowInfo());
    }

    @Override
    public List<LowInfoDet> findAllByUk(LowInfoDet vo) {
        var lowInfo = this.lowInfoRepository.findById(vo.getId()).orElse(null);
        vo.setLowInfo(lowInfo);
        return this.lowInfoDetRepository.findAllByIdAndLowInfoDetNameAndLowInfo(vo.getId(),
                vo.getLowInfoDetName(), vo.getLowInfo());
    }

    @Override
    public void deleteByVo(LowInfoDet vo) {
        this.lowInfoDetRepository.delete(vo);
    }
}
