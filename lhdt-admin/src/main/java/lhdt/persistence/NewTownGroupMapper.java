package lhdt.persistence;

import lhdt.domain.newtown.NewTownGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewTownGroupMapper {

	/**
     * 뉴타운 그룹 목록
     * @return
     */
    List<NewTownGroup> getListNewTownGroup();

    /**
     * 뉴타운 정보 조회
     * @param newTownGroup
     * @return
     */
    NewTownGroup getNewTownGroup(NewTownGroup newTownGroup);

    /**
     * 부모와 표시 순서로 메뉴 조회
     * @param newTownGroup
     * @return
     */
    NewTownGroup getNewTownGroupByParentAndViewOrder(NewTownGroup newTownGroup);

    /**
     * 뉴타운 그룹 등록
     * @param newTownGroup
     * @return
     */
    int insertNewTownGroup(NewTownGroup newTownGroup);

	/**
	 * 뉴타운 그룹 수정
	 * @param newTownGroup
	 * @return
	 */
	int updateNewTownGroup(NewTownGroup newTownGroup);

    /**
	 * 뉴타운 그룹 표시 순서 수정 (up/down)
	 * @param newTownGroup
	 * @return
	 */
	int updateNewTownGroupViewOrder(NewTownGroup newTownGroup);

	/**
	 * 뉴타운 그룹 삭제
	 * @param newTownGroup
	 * @return
	 */
	int deleteNewTownGroup(NewTownGroup newTownGroup);

	/**
	 * ancestor를 이용하여 뉴타운 그룹 삭제
	 * @param newTownGroup
	 * @return
	 */
	int deleteNewTownGroupByAncestor(NewTownGroup newTownGroup);

	/**
	 * parent를 이용하여 뉴타운 그룹 삭제
	 * @param newTownGroup
	 * @return
	 */
	int deleteNewTownGroupByParent(NewTownGroup newTownGroup);
}
