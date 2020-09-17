package lhdt.admin.svc.lhdt.persistence;

import java.util.List;

import lhdt.admin.svc.lhdt.domain.DesignLayerGroup;
import lhdt.cmmn.config.LhdtConnMapper;

@LhdtConnMapper
public interface DesignLayerGroupMapper {

	/**
     * 디자인 레이어 그룹 목록
     * @return
     */
    List<DesignLayerGroup> getListDesignLayerGroup();

    /**
     * 디자인 레이어 정보 조회
     * @param designLayerGroup
     * @return
     */
    DesignLayerGroup getDesignLayerGroup(DesignLayerGroup designLayerGroup);

    /**
     * 부모와 표시 순서로 메뉴 조회
     * @param designLayerGroup
     * @return
     */
    DesignLayerGroup getDesignLayerGroupByParentAndViewOrder(DesignLayerGroup designLayerGroup);

    /**
     * 디자인 레이어 그룹 등록
     * @param designLayerGroup
     * @return
     */
    int insertDesignLayerGroup(DesignLayerGroup designLayerGroup);

	/**
	 * 디자인 레이어 그룹 수정
	 * @param designLayerGroup
	 * @return
	 */
	int updateDesignLayerGroup(DesignLayerGroup designLayerGroup);

    /**
	 * 디자인 레이어 그룹 표시 순서 수정 (up/down)
	 * @param designLayerGroup
	 * @return
	 */
	int updateDesignLayerGroupViewOrder(DesignLayerGroup designLayerGroup);

	/**
	 * 디자인 레이어 그룹 삭제
	 * @param designLayerGroup
	 * @return
	 */
	int deleteDesignLayerGroup(DesignLayerGroup designLayerGroup);

	/**
	 * ancestor를 이용하여 디자인 레이어 그룹 삭제
	 * @param designLayerGroup
	 * @return
	 */
	int deleteDesignLayerGroupByAncestor(DesignLayerGroup designLayerGroup);

	/**
	 * parent를 이용하여 디자인 레이어 그룹 삭제
	 * @param designLayerGroup
	 * @return
	 */
	int deleteDesignLayerGroupByParent(DesignLayerGroup designLayerGroup);
}
