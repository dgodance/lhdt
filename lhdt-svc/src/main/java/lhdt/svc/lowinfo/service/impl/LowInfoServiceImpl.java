package lhdt.svc.lowinfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lhdt.svc.lowinfo.domain.LowInfo;
import lhdt.svc.lowinfo.persistence.LowInfoRepository;
import lhdt.svc.lowinfo.service.LowInfoService;

@Service
public class LowInfoServiceImpl extends LowInfoService {
    @Autowired
    private LowInfoRepository lowInfoRepository;

    @Override
    public LowInfo save(LowInfo vo) {
        return this.lowInfoRepository.save(vo);
    }

    @Override
    public List<LowInfo> findAll() {
        ArrayList<LowInfo> result = new ArrayList<LowInfo>();
        this.lowInfoRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<LowInfo> findAllById(Long id) {
        ArrayList<LowInfo> result = new ArrayList<LowInfo>();
        this.lowInfoRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public boolean existVoByUk(LowInfo vo) {
        return this.lowInfoRepository.existsByLowInfoName(vo.getLowInfoName());
    }

    @Override
    public LowInfo findByUk(LowInfo vo) {
        return this.lowInfoRepository.findByLowInfoName(vo.getLowInfoName());
    }

    @Override
    public List<LowInfo> findAllByUk(LowInfo vo) {
        return this.lowInfoRepository.findAllByLowInfoName(vo.getLowInfoName());
    }

    @Override
    public void deleteByVo(LowInfo vo) {
        this.lowInfoRepository.delete(vo);
    }
}
