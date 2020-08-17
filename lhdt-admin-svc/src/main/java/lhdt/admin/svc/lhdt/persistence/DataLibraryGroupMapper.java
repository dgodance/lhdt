package lhdt.admin.svc.lhdt.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import lhdt.admin.svc.config.LhdtConnMapper;
import lhdt.admin.svc.lhdt.domain.DataLibraryGroup;

@LhdtConnMapper
public interface DataLibraryGroupMapper {
	
	/**
	 * 사용자 Data Library Group 총건수
	 * @param dataLibraryGroup
	 * @return
	 */
	Long getDataLibraryGroupTotalCount(DataLibraryGroup dataLibraryGroup);

	/**
     * 사용자 데이터 library 그룹 전체 목록
     * @param dataLibraryGroup
     * @return
     */
    List<DataLibraryGroup> getAllListDataLibraryGroup(DataLibraryGroup dataLibraryGroup);
	
	/**
     * 데이터 library 그룹 목록
     * @return
     */
    List<DataLibraryGroup> getListDataLibraryGroup();

    /**
     * 데이터 정보 조회
     * @param dataLibraryGroup
     * @return
     */
    DataLibraryGroup getDataLibraryGroup(DataLibraryGroup dataLibraryGroup);

    /**
     * 기본 데이터 library 그룹 정보 조회
     * @return
     */
    DataLibraryGroup getBasicDataLibraryGroup();

    /**
     * 부모와 표시 순서로 메뉴 조회
     * @param dataLibraryGroup
     * @return
     */
    DataLibraryGroup getDataLibraryGroupByParentAndViewOrder(DataLibraryGroup dataLibraryGroup);

    /**
     * 데이터 library 그룹 Key 중복 확인
     * @param dataLibraryGroup
     * @return
     */
    Boolean isDataLibraryGroupKeyDuplication(DataLibraryGroup dataLibraryGroup);
    
    /**
     * 데이터 삭제를 위해 조상 dataLibraryGroupId를 이용해서 모든 하위 dataLibraryGroupId를 취득
     * @param dataLibraryGroup
     * @return
     */
    List<Integer> getDataLibraryGroupListByAncestor(DataLibraryGroup dataLibraryGroup);
    
    /**
     * 데이터 삭제를 위해 부모 dataLibraryGroupId를 이용해서 모든 하위 dataLibraryGroupId를 취득
     * @param dataLibraryGroup
     * @return
     */
    List<Integer> getDataLibraryGroupListByParent(DataLibraryGroup dataLibraryGroup);
    
    /**
     * 데이터 library 그룹 등록
     * @param dataLibraryGroup
     * @return
     */
    int insertDataLibraryGroup(DataLibraryGroup dataLibraryGroup);
    
    /**
     * 기본 데이터 library 그룹 등록
     * @param dataLibraryGroup
     * @return
     */
    int insertBasicDataLibraryGroup(DataLibraryGroup dataLibraryGroup);

	/**
	 * 데이터 library 그룹 수정
	 * @param dataLibraryGroup
	 * @return
	 */
	int updateDataLibraryGroup(DataLibraryGroup dataLibraryGroup);

	/**
	 * 사용자 데이터 library 그룹 표시 순서 수정. UP, DOWN
	 * @param dataLibraryGroup
	 * @return
	 */
	int updateDataLibraryGroupViewOrder(DataLibraryGroup dataLibraryGroup);

	/**
	 * 데이터 library 그룹 삭제
	 * @param dataLibraryGroup
	 * @return
	 */
	int deleteDataLibraryGroup(DataLibraryGroup dataLibraryGroup);

	/**
	 * ancestor를 이용하여 데이터 library 그룹 삭제
	 * @param dataLibraryGroup
	 * @return
	 */
	int deleteDataLibraryGroupByAncestor(DataLibraryGroup dataLibraryGroup);

	/**
	 * parent를 이용하여 데이터 library 그룹 삭제
	 * @param dataLibraryGroup
	 * @return
	 */
	int deleteDataLibraryGroupByParent(DataLibraryGroup dataLibraryGroup);
	
	/**
	 * user data library group delete
	 * @param userId
	 * @return
	 */
	int deleteDataLibraryGroupByUserId(String userId);
	
}
