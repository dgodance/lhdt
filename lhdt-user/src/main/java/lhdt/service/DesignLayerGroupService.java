package lhdt.service;

import lhdt.domain.extrusionmodel.DesignLayerGroup;

import java.util.List;

public interface DesignLayerGroupService {

	/**
     * 디자인 레이어 그룹 목록
     * @return
     */
    List<DesignLayerGroup> getListDesignLayerGroup();

	/**
	 * 디자인 레이어 그룹 정보 조회
	 * @param designLayerGroupId
	 * @return
	 */
	DesignLayerGroup getDesignLayerGroup(Integer designLayerGroupId);

    /**
     * 디자인 레이어 그룹 목록 및 하위 레이어 조회
     * @return
     */
    List<DesignLayerGroup> getListDesignLayerGroupAndLayer();
}
