package lhdt.persistence;

import lhdt.domain.extrusionmodel.DataLibraryGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataLibraryGroupMapper {
	
	/**
     * 데이터 라이브러리 그룹 목록
     * @return
     */
    List<DataLibraryGroup> getListDataLibraryGroup();

    /**
     * 데이터 라이브러리 그룹 정보
     * @param dataLibraryGroup
     * @return
     */
    DataLibraryGroup getDataLibraryGroup(DataLibraryGroup dataLibraryGroup);

	/**
	 * 기본 데이터 라이브러리 그룹 정보 조회
	 * @return
	 */
	DataLibraryGroup getBasicDataLibraryGroup();

    /**
     * 부모와 순서를 가지고 데이터 라이브러리 그룹 정보를 취득
     * @param dataLibraryGroup
     * @return
     */
    DataLibraryGroup getDataLibraryGroupByParentAndViewOrder(DataLibraryGroup dataLibraryGroup);

    /**
     * 데이터 라이브러리 그룹 Key 중복 확인
     * @param dataLibraryGroup
     * @return
     */
    Boolean isDataLibraryGroupKeyDuplication(DataLibraryGroup dataLibraryGroup);
    
    /**
     * 나를 부모로 가지는 자식 데이터 그룹 목록을 취득
     * @param dataLibraryGroup
     * @return
     */
    List<DataLibraryGroup> getChildrenDataLibraryGroupListByParent(DataLibraryGroup dataLibraryGroup);
    
    /**
     * 데이터 라이브러리 그룹 등록
     * @param dataLibraryGroup
     * @return
     */
    int insertDataLibraryGroup(DataLibraryGroup dataLibraryGroup);

	/**
	 * 기본 데이터 라이브러리 등록
	 * @param dataLibraryGroup
	 * @return
	 */
	int insertBasicDataLibraryGroup(DataLibraryGroup dataLibraryGroup);
    
    /**
	 * 데이터 라이브러리 그룹 수정
	 * @param dataLibraryGroup
	 * @return
	 */
	int updateDataLibraryGroup(DataLibraryGroup dataLibraryGroup);

	/**
	 * 데이터 라이브러리 그룹 표시 순서 수정. UP, DOWN
	 * @param dataLibraryGroup
	 * @return
	 */
	int updateDataLibraryGroupViewOrder(DataLibraryGroup dataLibraryGroup);

	/**
	 * 자식의 수를 + 또는 - 연산
	 */
	int updateDataLibraryGroupChildren(DataLibraryGroup dataLibraryGroup);

	/**
	 * 데이터 라이브러리 그룹 삭제
	 * @param dataLibraryGroup
	 * @return
	 */
	int deleteDataLibraryGroup(DataLibraryGroup dataLibraryGroup);

	/**
	 * ancestor를 이용하여 데이터 라이브러리 그룹 삭제
	 * @param dataLibraryGroup
	 * @return
	 */
	int deleteDataLibraryGroupByAncestor(DataLibraryGroup dataLibraryGroup);

	/**
	 * parent를 이용하여 데이터 라이브러리 그룹 삭제
	 * @param dataLibraryGroup
	 * @return
	 */
	int deleteDataLibraryGroupByParent(DataLibraryGroup dataLibraryGroup);
	
	/**
	 * 데이터 라이브러리 그룹 삭제
	 * @param userId
	 * @return
	 */
	int deleteDataLibraryGroupByUserId(String userId);
}
