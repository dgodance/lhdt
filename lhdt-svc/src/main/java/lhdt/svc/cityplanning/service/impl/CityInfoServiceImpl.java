package lhdt.svc.cityplanning.service.impl;

import lhdt.svc.cityplanning.domain.CityInfo;
import lhdt.svc.cityplanning.persistence.CityInfoRepository;
import lhdt.svc.cityplanning.service.CityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityInfoServiceImpl extends CityInfoService {

    @Autowired
    CityInfoRepository cityInfoRepository;

    @Override
    public CityInfo save(CityInfo vo) {
        return this.cityInfoRepository.save(vo);
    }

    @Override
    public List<CityInfo> findAll() {
        ArrayList<CityInfo> result = new ArrayList<>();
        this.cityInfoRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<CityInfo> findAllById(Long id) {
        ArrayList<Long> param = new ArrayList<>();
        param.add(id);
        ArrayList<CityInfo> result = new ArrayList<>();
        this.cityInfoRepository.findAllById(param).forEach(result::add);
        return result;
    }

    @Override
    public Page<CityInfo> findAllByPage(Pageable pageable) {
        return this.cityInfoRepository.findAll(pageable);
    }

    @Override
    public boolean existVoByUk(CityInfo vo) {
        return this.cityInfoRepository.existsByCityName(vo.getCityName());
    }

    @Override
    public CityInfo findByUk(CityInfo vo) {
        return this.cityInfoRepository.findByCityName(vo.getCityName());
    }

    @Override
    public List<CityInfo> findAllByUk(CityInfo vo) {
        return this.cityInfoRepository.findAllByCityName(vo.getCityName());
    }

    @Override
    public void deleteByVo(CityInfo vo) {
        this.cityInfoRepository.delete(vo);
    }
}
