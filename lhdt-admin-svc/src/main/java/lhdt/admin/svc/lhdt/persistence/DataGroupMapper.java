package lhdt.admin.svc.lhdt.persistence;

import java.util.List;

import lhdt.admin.svc.lhdt.domain.DataGroup;
import lhdt.cmmn.config.LhdtConnMapper;

@LhdtConnMapper
public interface DataGroupMapper {
	
	/**
	 * 사용자 Data Group 총건수
	 * @param dataGroup
	 * @return
	 */
	Long getDataGroupTotalCount(DataGroup dataGroup);

	/**
     * 사용자 데이터 그룹 전체 목록
     * @param dataGroup
     * @return
     */
    List<DataGroup> getAllListDataGroup(DataGroup dataGroup);
	
	/**
     * 데이터 그룹 목록
     * @return
     */
    List<DataGroup> getListDataGroup();

    /**
     * 데이터 정보 조회
     * @param dataGroup
     * @return
     */
    DataGroup getDataGroup(DataGroup dataGroup);

    /**
     * 기본 데이터 그룹 정보 조회
     * @return
     */
    DataGroup getBasicDataGroup();

    /**
     * 부모와 표시 순서로 메뉴 조회
     * @param dataGroup
     * @return
     */
    DataGroup getDataGroupByParentAndViewOrder(DataGroup dataGroup);

    /**
     * 데이터 그룹 Key 중복 확인
     * @param dataGroup
     * @return
     */
    Boolean isDataGroupKeyDuplication(DataGroup dataGroup);
    
    /**
     * 데이터 삭제를 위해 조상 dataGroupId를 이용해서 모든 하위 dataGroupId를 취득
     * @param dataGroup
     * @return
     */
    List<Integer> getDataGroupListByAncestor(DataGroup dataGroup);
    
    /**
     * 데이터 삭제를 위해 부모 dataGroupId를 이용해서 모든 하위 dataGroupId를 취득
     * @param dataGroup
     * @return
     */
    List<Integer> getDataGroupListByParent(DataGroup dataGroup);
    
    /**
     * 데이터 그룹 등록
     * @param dataGroup
     * @return
     */
    int insertDataGroup(DataGroup dataGroup);
    
    /**
     * 기본 그룹 등록
     * @param dataGroup
     * @return
     */
    int insertBasicDataGroup(DataGroup dataGroup);

	/**
	 * 데이터 그룹 수정
	 * @param dataGroup
	 * @return
	 */
	int updateDataGroup(DataGroup dataGroup);

	/**
	 * 사용자 데이터 그룹 표시 순서 수정. UP, DOWN
	 * @param dataGroup
	 * @return
	 */
	int updateDataGroupViewOrder(DataGroup dataGroup);

	/**
	 * 데이터 그룹 삭제
	 * @param dataGroup
	 * @return
	 */
	int deleteDataGroup(DataGroup dataGroup);

	/**
	 * ancestor를 이용하여 데이터 그룹 삭제
	 * @param dataGroup
	 * @return
	 */
	int deleteDataGroupByAncestor(DataGroup dataGroup);

	/**
	 * parent를 이용하여 데이터 그룹 삭제
	 * @param dataGroup
	 * @return
	 */
	int deleteDataGroupByParent(DataGroup dataGroup);
	
	/**
	 * user data group delete
	 * @param userId
	 * @return
	 */
	int deleteDataGroupByUserId(String userId);
	
}
