package lhdt.svc.cityplanning.service.impl;

import lhdt.svc.cityplanning.domain.CPLocalInfo;
import lhdt.svc.cityplanning.persistence.CPFileInfoRepository;
import lhdt.svc.cityplanning.persistence.CPLocalInfoRepository;
import lhdt.svc.cityplanning.service.CPLocalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CPLocalInfoServiceImpl extends CPLocalInfoService {

    @Autowired
    CPLocalInfoRepository cpLocalInfoRepository;

    @Override
    public CPLocalInfo save(CPLocalInfo vo) {
        return this.cpLocalInfoRepository.save(vo);
    }

    @Override
    public List<CPLocalInfo> findAll() {
        ArrayList<CPLocalInfo> result = new ArrayList<>();
        this.cpLocalInfoRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<CPLocalInfo> findAllById(Long id) {
        ArrayList<Long> param = new ArrayList<>();
        param.add(id);
        ArrayList<CPLocalInfo> result = new ArrayList<>();
        this.cpLocalInfoRepository.findAllById(param).forEach(result::add);
        return result;
    }

    @Override
    public Page<CPLocalInfo> findAllByPage(Pageable pageable) {
        return this.cpLocalInfoRepository.findAll(pageable);
    }

    @Override
    public boolean existVoByUk(CPLocalInfo vo) {
        return this.cpLocalInfoRepository
                .existsByLocalName(vo.getLocalName());
    }

    @Override
    public CPLocalInfo findByUk(CPLocalInfo vo) {
        return this.cpLocalInfoRepository
                .findByLocalName(vo.getLocalName());
    }

    @Override
    public List<CPLocalInfo> findAllByUk(CPLocalInfo vo) {
        return this.cpLocalInfoRepository
                .findAllByLocalName(vo.getLocalName());
    }

    @Override
    public void deleteByVo(CPLocalInfo vo) {
        this.cpLocalInfoRepository.delete(vo);
    }
}
