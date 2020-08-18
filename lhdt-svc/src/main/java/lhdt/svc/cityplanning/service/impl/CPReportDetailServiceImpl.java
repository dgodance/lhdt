package lhdt.svc.cityplanning.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lhdt.svc.cityplanning.domain.CPReportDetail;
import lhdt.svc.cityplanning.persistence.CPReportDetailRepository;
import lhdt.svc.cityplanning.service.CPReportDetailService;

@Service
public class CPReportDetailServiceImpl extends CPReportDetailService {

    @Autowired
    CPReportDetailRepository cpReportDetailRepository;

    @Override
    public CPReportDetail save(CPReportDetail vo) {
        return this.cpReportDetailRepository.save(vo);
    }

    @Override
    public List<CPReportDetail> findAll() {
        ArrayList<CPReportDetail> result = new ArrayList<>();
        this.cpReportDetailRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<CPReportDetail> findAllById(Long id) {
        ArrayList<Long> param = new ArrayList<>();
        param.add(id);
        ArrayList<CPReportDetail> result = new ArrayList<>();
        this.cpReportDetailRepository.findAllById(param).forEach(result::add);
        return result;
    }

    @Override
    public Page<CPReportDetail> findAllByPage(Pageable pageable) {
        return this.cpReportDetailRepository.findAll(pageable);
    }

    @Override
    public boolean existVoByUk(CPReportDetail vo) {
        return this.cpReportDetailRepository
                .existsByLocalNameAndDistrictNameAndBussinessWayAndPaperNameAndNomination(
                        vo.getLocalName(), vo.getDistrictName(), vo.getBussinessWay(),
                        vo.getPaperName(), vo.getNomination()
                );
    }

    @Override
    public CPReportDetail findByUk(CPReportDetail vo) {
        return this.cpReportDetailRepository
                .findByLocalNameAndDistrictNameAndBussinessWayAndPaperNameAndNomination(
                        vo.getLocalName(), vo.getDistrictName(), vo.getBussinessWay(),
                        vo.getPaperName(), vo.getNomination()
                );
    }

    @Override
    public List<CPReportDetail> findAllByUk(CPReportDetail vo) {
        return this.cpReportDetailRepository.findAllByLocalNameAndDistrictNameAndBussinessWayAndPaperNameAndNomination(
                vo.getLocalName(), vo.getDistrictName(), vo.getBussinessWay(),
                vo.getPaperName(), vo.getNomination()
        );
    }

    @Override
    public void deleteByVo(CPReportDetail vo) {
        this.cpReportDetailRepository.delete(vo);
    }
}
