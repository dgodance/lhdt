package lhdt.svc.lowinfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lhdt.svc.lowinfo.domain.LowInfoDet;
import lhdt.svc.lowinfo.persistence.LowInfoDetRepository;
import lhdt.svc.lowinfo.persistence.LowInfoRepository;
import lhdt.svc.lowinfo.service.LowInfoDetService;

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
    public List<LowInfoDet> findAllById(Long id) {
        ArrayList<LowInfoDet> result = new ArrayList<LowInfoDet>();
        this.lowInfoDetRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Page<LowInfoDet> findAllByPage(Pageable pageable) {
        return this.lowInfoDetRepository.findAll(pageable);
    }

    @Override
    public boolean existVoByUk(LowInfoDet vo) {
        var lowInfo = this.lowInfoRepository.findById(vo.getId()).orElse(null);
        vo.setLowInfo(lowInfo);
        return this.lowInfoDetRepository.existsByLowInfoDetNameAndLowInfo(vo.getLowInfoDetName(), vo.getLowInfo());
    }

    @Override
    public LowInfoDet findByUk(LowInfoDet vo) {
        var lowInfo = this.lowInfoRepository.findById(vo.getId()).orElse(null);
        vo.setLowInfo(lowInfo);
        return this.lowInfoDetRepository.findByLowInfoDetNameAndLowInfo(vo.getLowInfoDetName(), vo.getLowInfo());
    }

    @Override
    public List<LowInfoDet> findAllByUk(LowInfoDet vo) {
        var lowInfo = this.lowInfoRepository.findById(vo.getId()).orElse(null);
        vo.setLowInfo(lowInfo);
        return this.lowInfoDetRepository.findAllByLowInfoDetNameAndLowInfo(vo.getLowInfoDetName(), vo.getLowInfo());
    }

    @Override
    public void deleteByVo(LowInfoDet vo) {
        this.lowInfoDetRepository.delete(vo);
    }
}
