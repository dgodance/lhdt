package lhdt.admin.svc.landscape.service;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffDefault;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffScene;
import lhdt.cmmn.misc.CmmnService;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;

import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 경관비교 서비스를 추상화하는 인터페이스
 */
public interface LandScapeDiffService extends CmmnService<LandScapeDiff, Long> {
    /**
     * 경관비교그룹과 페이지 정보를 통하여 모든 데이터를 찾습니다
     * @param landScapeDiffGroup
     * @param startPage
     * @param contentsSize
     * @return
     */
    Page<LandScapeDiffDefault> findAllByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup,
                                                           Integer startPage, Integer contentsSize);

    /**
     * 경관비교그룹을 통하여 모든 데이터를 찾습니다
     * @param landScapeDiffGroup
     * @return
     */
    List<LandScapeDiffDefault> findALlByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup);

    /**
     * 아이디를 통하여 1개의 데이터를 찾습니다
     * @param id
     * @return
     */
    LandScapeDiffScene findTopById(Long id);

}
