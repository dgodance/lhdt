package lhdt.anals.lowinfo.service.impl;

import lhdt.anals.lowinfo.domain.LowInfo;
import lhdt.anals.lowinfo.persistance.LowInfoRepository;
import lhdt.anals.lowinfo.service.LowInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public LowInfo findById(Long id) {
        return this.lowInfoRepository.findById(id).orElse(null);
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
