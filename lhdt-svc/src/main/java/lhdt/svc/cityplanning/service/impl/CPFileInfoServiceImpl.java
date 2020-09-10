package lhdt.svc.cityplanning.service.impl;

import lhdt.svc.cityplanning.domain.CPFileInfo;
import lhdt.svc.cityplanning.persistence.CPFileInfoRepository;
import lhdt.svc.cityplanning.service.CPFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CPFileInfoServiceImpl extends CPFileInfoService {

    @Autowired
    CPFileInfoRepository cpFileInfoRepository;

    @Override
    public CPFileInfo save(CPFileInfo vo) {
        return this.cpFileInfoRepository.save(vo);
    }

    @Override
    public List<CPFileInfo> findAll() {
        ArrayList<CPFileInfo> result = new ArrayList<>();
        this.cpFileInfoRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<CPFileInfo> findAllById(Long id) {
        ArrayList<Long> param = new ArrayList<>();
        param.add(id);
        ArrayList<CPFileInfo> result = new ArrayList<>();
        this.cpFileInfoRepository.findAllById(param).forEach(result::add);
        return result;
    }

    @Override
    public Page<CPFileInfo> findAllByPage(Pageable pageable) {
        return this.cpFileInfoRepository.findAll(pageable);
    }

    @Override
    public boolean existVoByUk(CPFileInfo vo) {
        return this.cpFileInfoRepository
                .existsByFileNameAndFilePath(vo.getFileName(), vo.getFilePath());
    }

    @Override
    public CPFileInfo findByUk(CPFileInfo vo) {
        return this.cpFileInfoRepository
                .findByFileNameAndFilePath(vo.getFileName(), vo.getFilePath());
    }

    @Override
    public List<CPFileInfo> findAllByUk(CPFileInfo vo) {
        return this.cpFileInfoRepository
                .findAllByFileNameAndFilePath(vo.getFileName(), vo.getFilePath());
    }

    @Override
    public void deleteByVo(CPFileInfo vo) {
        this.cpFileInfoRepository.delete(vo);
    }
}
