package lhdt.service;

import lhdt.domain.newtown.NewTownGroup;

import java.util.List;

public interface NewTownGroupService {

	/**
     * 뉴타운 그룹 목록
     * @return
     */
    List<NewTownGroup> getListNewTownGroup();

	/**
	 * 뉴타운 그룹 정보 조회
	 * @param newTownGroup
	 * @return
	 */
	NewTownGroup getNewTownGroup(NewTownGroup newTownGroup);

    /**
     * 뉴타운 그룹 목록 및 하위 뉴타운 조회
     * @return
     */
    List<NewTownGroup> getListNewTownGroupAndLayer();

	/**
	 * 뉴타운 그룹 등록
	 *
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
}
