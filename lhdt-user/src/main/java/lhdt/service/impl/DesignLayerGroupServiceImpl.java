package lhdt.service.impl;

import lhdt.domain.extrusionmodel.DesignLayer;
import lhdt.domain.extrusionmodel.DesignLayerGroup;
import lhdt.persistence.DesignLayerGroupMapper;
import lhdt.service.DesignLayerGroupService;
import lhdt.service.DesignLayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DesignLayerGroupServiceImpl implements DesignLayerGroupService {

	private final DesignLayerService designLayerService;
	private final DesignLayerGroupMapper designLayerGroupMapper;

	public DesignLayerGroupServiceImpl(DesignLayerService designLayerService, DesignLayerGroupMapper designLayerGroupMapper) {
		this.designLayerService = designLayerService;
		this.designLayerGroupMapper = designLayerGroupMapper;
	}

	/**
	 * 디자인 레이어 그룹 목록
	 */
	@Transactional(readOnly = true)
	public List<DesignLayerGroup> getListDesignLayerGroup() {
		return designLayerGroupMapper.getListDesignLayerGroup();
	}

	/**
     * 디자인 레이어 그룹 정보 조회
     * @param designLayerGroup
     * @return
     */
	@Transactional(readOnly = true)
    public DesignLayerGroup getDesignLayerGroup(DesignLayerGroup designLayerGroup) {
		return designLayerGroupMapper.getDesignLayerGroup(designLayerGroup);
	}

	/**
	 * 디자인 레이어 그룹 목록 및 하위 레이어를 조회
     * @return
     */
	@Transactional(readOnly = true)
	public List<DesignLayerGroup> getListDesignLayerGroupAndLayer() {
		List<DesignLayerGroup> designLayerGroupList = designLayerGroupMapper.getListDesignLayerGroup();
		for(DesignLayerGroup designLayerGroup : designLayerGroupList) {
			DesignLayer designLayer = new DesignLayer();
			designLayer.setDesignLayerGroupId(designLayerGroup.getDesignLayerGroupId());
			designLayerGroup.setDesignLayerList(designLayerService.getListDesignLayer(designLayer));
		}

		return designLayerGroupList;
	}
}
