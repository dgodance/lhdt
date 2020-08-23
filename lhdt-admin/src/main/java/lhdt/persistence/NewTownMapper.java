package lhdt.persistence;

import lhdt.domain.newtown.NewTown;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewTownMapper {

    /**
     * 뉴타운 수
     * @param newTown
     * @return
     */
    Long getNewTownTotalCount(NewTown newTown);

    /**
     * 뉴타운 목록
     * @param newTown
     * @return
     */
    List<NewTown> getListNewTown(NewTown newTown);

    /**
     * 뉴타운 정보
     * @param newTownId
     * @return
     */
    NewTown getNewTown(Integer newTownId);

    /**
     * 뉴타운 등록
     * @param newTown
     * @return
     */
    int insertNewTown(NewTown newTown);

    /**
     * 뉴타운 정보 수정
     * @param newTown
     * @return
     */
    int updateNewTown(NewTown newTown);

    /**
     * 뉴타운 삭제
     * @param newTownId
     * @return
     */
    int deleteNewTown(Integer newTownId);
    
}
