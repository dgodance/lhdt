package lhdt.svc.landscape.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.svc.landscape.domain.LandScapeAnals;
import lhdt.svc.landscape.types.LandScapeAnalsType;

/**
 * 경관 분석 데이터 베이스 연결 클래스
 */
public interface LandScapeAnalsRepository extends JpaRepository<LandScapeAnals, Long> {
    /**
     * 경관명과 경관점 종류를 통하여 데이터가 존재하는지 확인합니다
     * @param lsn
     * @param lsat
     * @return
     */
    boolean existsByLandScapeAnalsNameAndLandScapeAnalsType(String lsn, LandScapeAnalsType lsat);

    /**
     * 경관명과 경관점 종류를 통하여 하나의 데이터를 가져옵니다
     * @param lsn
     * @param lsat
     * @return
     */
    LandScapeAnals findByLandScapeAnalsNameAndLandScapeAnalsType(String lsn, LandScapeAnalsType lsat);

    /**
     * 경관명과 경관점 종류를 통하여 모든 데이터를 가져옵니다
     * @param lsn
     * @param lsat
     * @return
     */
    List<LandScapeAnals> findAllByLandScapeAnalsNameAndLandScapeAnalsType(String lsn, LandScapeAnalsType lsat);
}
