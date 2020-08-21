package lhdt.admin.svc.cityplanning.service.impl;

import lhdt.admin.svc.cityplanning.domain.CPAreaColor;
import lhdt.admin.svc.cityplanning.domain.CPDistricInfo;
import lhdt.admin.svc.cityplanning.domain.CPLocalInfo;
import lhdt.admin.svc.cityplanning.persistence.*;
import lhdt.admin.svc.cityplanning.service.CPAreaColorService;
import lhdt.admin.svc.cityplanning.service.CPDistricInfoService;
import lhdt.admin.svc.common.AdminSvcService;
import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CPDistricInfoServiceImpl
        extends AdminSvcServiceImpl<CPDistricInfoRepository, CPDistricInfoMapper, CPDistricInfo, Long>
        implements CPDistricInfoService {
    private final CPDistricInfoRepository jpaRepo;
    private final CPDistricInfoMapper mapper;
    private final CPLocalInfoRepository localInfoRepository;

    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new CPDistricInfo());
    }

    @Override
    public List<CPDistricInfo> findAllByCpLocalInfo(Long cpLocalInfoId) {
        CPLocalInfo localInfo = localInfoRepository.findOneById(cpLocalInfoId);
        var result = this.jpaRepo.findAllByCpLocalInfo(localInfo);
        return result;
    }
}
