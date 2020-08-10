package lhdt.anals.cityplanning.service.impl;

import lhdt.anals.cityplanning.domain.CityPlanReportDetail;
import lhdt.anals.cityplanning.persistance.CityPlanReportDetailRepository;
import lhdt.anals.cityplanning.service.CityPlanReportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityPlanReportDetailServiceImpl extends CityPlanReportDetailService {
    @Autowired
    CityPlanReportDetailRepository cityPlanReportDetailRepository;

    @Override
    public CityPlanReportDetail save(CityPlanReportDetail vo) {
        return this.cityPlanReportDetailRepository.save(vo);
    }

    @Override
    public List<CityPlanReportDetail> findAll() {
        ArrayList<CityPlanReportDetail> result = new ArrayList<CityPlanReportDetail>();
        this.cityPlanReportDetailRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public CityPlanReportDetail findById(Long id) {
        return this.cityPlanReportDetailRepository.findById(id).orElse(null);
    }

    @Override
    public List<CityPlanReportDetail> findAllById(Long id) {
        ArrayList<CityPlanReportDetail> result = new ArrayList<CityPlanReportDetail>();
        this.cityPlanReportDetailRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public boolean existVoByUk(CityPlanReportDetail vo) {
        return this.cityPlanReportDetailRepository.existsByIdAndCityPlanId(vo.getId(), vo.getCityPlanId());
    }

    @Override
    public CityPlanReportDetail findByUk(CityPlanReportDetail vo) {
        return this.cityPlanReportDetailRepository.findByIdAndCityPlanId(vo.getId(), vo.getCityPlanId());
    }

    @Override
    public List<CityPlanReportDetail> findAllByUk(CityPlanReportDetail vo) {
        return this.cityPlanReportDetailRepository.findAllByIdAndCityPlanId(vo.getId(), vo.getCityPlanId());
    }

    @Override
    public void deleteByVo(CityPlanReportDetail vo) {
        this.cityPlanReportDetailRepository.delete(vo);
    }
}
