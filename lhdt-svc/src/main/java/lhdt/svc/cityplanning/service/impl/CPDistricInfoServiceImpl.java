package lhdt.svc.cityplanning.service.impl;

import lhdt.svc.cityplanning.domain.CPDistricInfo;
import lhdt.svc.cityplanning.persistence.CPAreaColorRepository;
import lhdt.svc.cityplanning.persistence.CPDistricInfoRepository;
import lhdt.svc.cityplanning.service.CPDistricInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CPDistricInfoServiceImpl extends CPDistricInfoService {

    @Autowired
    CPDistricInfoRepository cpDistricInfoRepository;

    @Override
    public CPDistricInfo save(CPDistricInfo vo) {
        return this.cpDistricInfoRepository.save(vo);
    }

    @Override
    public List<CPDistricInfo> findAll() {
        ArrayList<CPDistricInfo> result = new ArrayList<>();
        this.cpDistricInfoRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<CPDistricInfo> findAllById(Long id) {
        ArrayList<Long> param = new ArrayList<>();
        param.add(id);
        ArrayList<CPDistricInfo> result = new ArrayList<>();
        this.cpDistricInfoRepository.findAllById(param).forEach(result::add);
        return result;
    }

    @Override
    public Page<CPDistricInfo> findAllByPage(Pageable pageable) {
        return this.cpDistricInfoRepository.findAll(pageable);
    }

    @Override
    public boolean existVoByUk(CPDistricInfo vo) {
        return this.cpDistricInfoRepository
                .existsByDistrictNameAndCpLocalInfo(vo.getDistrictName(), vo.getCpLocalInfo());
    }

    @Override
    public CPDistricInfo findByUk(CPDistricInfo vo) {
        return this.cpDistricInfoRepository
                .findByDistrictNameAndCpLocalInfo(vo.getDistrictName(), vo.getCpLocalInfo());
    }

    @Override
    public List<CPDistricInfo> findAllByUk(CPDistricInfo vo) {
        return this.cpDistricInfoRepository
                .findAllByDistrictNameAndCpLocalInfo(vo.getDistrictName(), vo.getCpLocalInfo());
    }

    @Override
    public void deleteByVo(CPDistricInfo vo) {
        this.cpDistricInfoRepository.delete(vo);
    }
}
