package lhdt.svc.cityplanning.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lhdt.svc.cityplanning.domain.CityPlanReportDetail;
import lhdt.svc.cityplanning.persistence.CityPlanReportDetailRepository;
import lhdt.svc.cityplanning.service.CityPlanReportDetailService;

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
        ArrayList<CityPlanReportDetail> result = new ArrayList<>();
        this.cityPlanReportDetailRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<CityPlanReportDetail> findAllById(Long id) {
        ArrayList<CityPlanReportDetail> result = new ArrayList<>();
        this.cityPlanReportDetailRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Page<CityPlanReportDetail> findAllByPage(Pageable pageable) {
        return this.cityPlanReportDetailRepository.findAll(pageable);
    }

    @Override
    public boolean existVoByUk(CityPlanReportDetail vo) {
        return this.cityPlanReportDetailRepository.existsByCityInfoAndDrawingIdAndHouseholdId(
                vo.getCityInfo(), vo.getDrawingId(), vo.getHouseholdId());
    }

    @Override
    public CityPlanReportDetail findByUk(CityPlanReportDetail vo) {
        return this.cityPlanReportDetailRepository.findByCityInfoAndDrawingIdAndHouseholdId(
                vo.getCityInfo(), vo.getDrawingId(), vo.getHouseholdId());
    }

    @Override
    public List<CityPlanReportDetail> findAllByUk(CityPlanReportDetail vo) {
        return this.cityPlanReportDetailRepository
                .findAllByCityInfoAndDrawingIdAndHouseholdId(
                        vo.getCityInfo(), vo.getDrawingId(), vo.getHouseholdId());
    }

    @Override
    public void deleteByVo(CityPlanReportDetail vo) {
        this.cityPlanReportDetailRepository.delete(vo);
    }
}
