package lhdt.svc.landscape.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lhdt.svc.landscape.domain.LandScapeAnals;
import lhdt.svc.landscape.persistence.LandScapeAnalsRepository;
import lhdt.svc.landscape.service.LandScapeAnalsService;

/**
 * 경관분석에 필요한 서비스 로직 구현 클래스
 */
@Service
public class LandScapeAnalsServiceImpl extends LandScapeAnalsService {
    @Autowired
    LandScapeAnalsRepository landScapeAnalsRepository;

    /**
     * 경관점 등록
     * @param vo
     * @return
     */
    @Override
    public LandScapeAnals save(LandScapeAnals vo) {
        return this.landScapeAnalsRepository.save(vo);
    }

    /**
     * 모든 경관점을 가져옵니다
     * @param vo
     * @return
     */
    @Override
    public List<LandScapeAnals> findAll() {
        ArrayList<LandScapeAnals> result = new ArrayList<>();
        this.landScapeAnalsRepository.findAll().forEach(result::add);
        return result;
    }

    /**
     * ID로 부터 모든 경관점을 가져옵니다
     * @param vo
     * @return
     */
    @Override
    public List<LandScapeAnals> findAllById(Long id) {
        ArrayList<LandScapeAnals> result = new ArrayList<>();
        this.landScapeAnalsRepository.findAll().forEach(result::add);
        return result;
    }

    /**
     * 페이징 정보를 통해 모든 경관점을 가져옵니다
     * @param vo
     * @return
     */
    @Override
    public Page<LandScapeAnals> findAllByPage(Pageable pageable) {
        return this.landScapeAnalsRepository.findAll(pageable);
    }

    /**
     * 식별자를 기반으로 등록된 경관점이 있는지 확인한다
     * @param vo
     * @return
     */
    @Override
    public boolean existVoByUk(LandScapeAnals vo) {
        return this.landScapeAnalsRepository
                .existsByLandScapeAnalsNameAndLandScapeAnalsType(vo.getLandScapeAnalsName(),
                        vo.getLandScapeAnalsType());
    }
    
    /**
     * 식별자를 기반으로 등록된 경관점이 있는지 확인한다
     * @param vo
     * @return
     */
    @Override
    public LandScapeAnals findByUk(LandScapeAnals vo) {
        return this.landScapeAnalsRepository.findByLandScapeAnalsNameAndLandScapeAnalsType(vo.getLandScapeAnalsName(),
                vo.getLandScapeAnalsType());
    }

    /**
     * 식별자를 기반으로 모든 데이터를 찾는다
     * @param vo
     * @return
     */
    @Override
    public List<LandScapeAnals> findAllByUk(LandScapeAnals vo) {
        return this.landScapeAnalsRepository.findAllByLandScapeAnalsNameAndLandScapeAnalsType(vo.getLandScapeAnalsName(),
                vo.getLandScapeAnalsType());
    }

    /**
     * 객체를 기반으로 데이터를 삭제한다
     * @param vo
     * @return
     */
    @Override
    public void deleteByVo(LandScapeAnals vo) {
        this.landScapeAnalsRepository.delete(vo);
    }
}
