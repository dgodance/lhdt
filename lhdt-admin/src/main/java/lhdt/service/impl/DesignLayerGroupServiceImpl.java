package lhdt.service.impl;

import lhdt.domain.*;
import lhdt.domain.extrusionmodel.DesignLayer;
import lhdt.domain.extrusionmodel.DesignLayerGroup;
import lhdt.persistence.DesignLayerGroupMapper;
import lhdt.service.DesignLayerGroupService;
import lhdt.service.DesignLayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DesignLayerGroupServiceImpl implements DesignLayerGroupService {

	@Autowired
	private DesignLayerService designLayerService;

	@Autowired
	private DesignLayerGroupMapper designLayerGroupMapper;

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

	/**
	 * 데이터 그룹 표시 순서 수정 (up/down)
	 * @param designLayerGroup
	 * @return
	 */
    @Transactional
	public int updateDesignLayerGroupViewOrder(DesignLayerGroup designLayerGroup) {

    	DesignLayerGroup dbDesignLayerGroup = designLayerGroupMapper.getDesignLayerGroup(designLayerGroup);
    	dbDesignLayerGroup.setUpdateType(designLayerGroup.getUpdateType());

    	Integer modifyViewOrder = dbDesignLayerGroup.getViewOrder();
    	DesignLayerGroup searchDesignLayerGroup = new DesignLayerGroup();
    	searchDesignLayerGroup.setUpdateType(dbDesignLayerGroup.getUpdateType());
    	searchDesignLayerGroup.setParent(dbDesignLayerGroup.getParent());

    	if(Move.UP == Move.valueOf(dbDesignLayerGroup.getUpdateType())) {
    		// 바로 위 메뉴의 view_order 를 +1
    		searchDesignLayerGroup.setViewOrder(dbDesignLayerGroup.getViewOrder());
    		searchDesignLayerGroup = getDataLayerByParentAndViewOrder(searchDesignLayerGroup);

    		if(searchDesignLayerGroup == null) return 0;

	    	dbDesignLayerGroup.setViewOrder(searchDesignLayerGroup.getViewOrder());
	    	searchDesignLayerGroup.setViewOrder(modifyViewOrder);
    	} else {
    		// 바로 아래 메뉴의 view_order 를 -1 함
    		searchDesignLayerGroup.setViewOrder(dbDesignLayerGroup.getViewOrder());
    		searchDesignLayerGroup = getDataLayerByParentAndViewOrder(searchDesignLayerGroup);

    		if(searchDesignLayerGroup == null) return 0;

    		dbDesignLayerGroup.setViewOrder(searchDesignLayerGroup.getViewOrder());
    		searchDesignLayerGroup.setViewOrder(modifyViewOrder);
    	}

    	updateViewOrderDesignLayerGroup(searchDesignLayerGroup);
		return updateViewOrderDesignLayerGroup(dbDesignLayerGroup);
    }

    /**
	 * 디자인 레이어 그룹 등록
	 */
	@Transactional
	public int insertDesignLayerGroup(DesignLayerGroup designLayerGroup) {
		
		Integer parentDesignLayerGroupId = 0;
    	
    	DesignLayerGroup parentDesignLayerGroup = new DesignLayerGroup();
    	int depth = 0;
    	if(designLayerGroup.getParent() > 0) {
    		parentDesignLayerGroupId = designLayerGroup.getParent();
    		parentDesignLayerGroup.setDesignLayerGroupId(parentDesignLayerGroupId);
    		parentDesignLayerGroup = designLayerGroupMapper.getDesignLayerGroup(parentDesignLayerGroup);
	    	depth = parentDesignLayerGroup.getDepth() + 1;
    	}
    	
    	int result = designLayerGroupMapper.insertDesignLayerGroup(designLayerGroup);
		
    	if(depth > 1) {
	    	// parent 의 children update
    		Integer children = parentDesignLayerGroup.getChildren();
    		if(children == null) children = 0;
    		children += 1;
    		
    		parentDesignLayerGroup = new DesignLayerGroup();
    		parentDesignLayerGroup.setDesignLayerGroupId(parentDesignLayerGroupId);
    		parentDesignLayerGroup.setChildren(children);
	    	return designLayerGroupMapper.updateDesignLayerGroup(parentDesignLayerGroup);
    	}
    	
		return result; 
	}

	/**
	 * 디자인 레이어 그룹 수정
	 * @param designLayerGroup
	 * @return
	 */
    @Transactional
	public int updateDesignLayerGroup(DesignLayerGroup designLayerGroup) {
    	return designLayerGroupMapper.updateDesignLayerGroup(designLayerGroup);
    }

    /**
     * 부모와 표시 순서로 메뉴 조회
     * @param designLayerGroup
     * @return
     */
    private DesignLayerGroup getDataLayerByParentAndViewOrder(DesignLayerGroup designLayerGroup) {
    	return designLayerGroupMapper.getDesignLayerGroupByParentAndViewOrder(designLayerGroup);
    }

    /**
	 * 사용자 그룹 표시 순서 수정 (up/down)
	 * @param designLayerGroup
	 * @return
	 */
	private int updateViewOrderDesignLayerGroup(DesignLayerGroup designLayerGroup) {
		return designLayerGroupMapper.updateDesignLayerGroupViewOrder(designLayerGroup);
	}

    /**
	 * 디자인 레이어 그룹 삭제
	 * @param designLayerGroup
	 * @return
	 */
    @Transactional
	public int deleteDesignLayerGroup(DesignLayerGroup designLayerGroup) {
    	// 삭제하고, children update

    	designLayerGroup = designLayerGroupMapper.getDesignLayerGroup(designLayerGroup);
    	log.info("--- 111111111 delete dataGroup = {}", designLayerGroup);

    	int result = 0;
    	if(Depth.ONE == Depth.findBy(designLayerGroup.getDepth())) {
    		log.info("--- one ================");
    		result = designLayerGroupMapper.deleteDesignLayerGroupByAncestor(designLayerGroup);
    	} else if(Depth.TWO == Depth.findBy(designLayerGroup.getDepth())) {
    		log.info("--- two ================");

    		DesignLayerGroup ancestorDesignLayerGroup = new DesignLayerGroup();
    		ancestorDesignLayerGroup.setDesignLayerGroupId(designLayerGroup.getAncestor());
    		ancestorDesignLayerGroup = designLayerGroupMapper.getDesignLayerGroup(ancestorDesignLayerGroup);
    		ancestorDesignLayerGroup.setChildren(ancestorDesignLayerGroup.getChildren() - 1);
			designLayerGroupMapper.updateDesignLayerGroup(ancestorDesignLayerGroup);
	    	
	    	result = designLayerGroupMapper.deleteDesignLayerGroupByParent(designLayerGroup);
    		// ancestor - 1
    	} else if(Depth.THREE == Depth.findBy(designLayerGroup.getDepth())) {
    		log.info("--- three ================");
    		log.info("--- dataGroup ================ {}", designLayerGroup);

    		DesignLayerGroup parentDataGroup = new DesignLayerGroup();
	    	parentDataGroup.setDesignLayerGroupId(designLayerGroup.getParent());
	    	parentDataGroup = designLayerGroupMapper.getDesignLayerGroup(parentDataGroup);
	    	parentDataGroup.setChildren(parentDataGroup.getChildren() - 1);
			designLayerGroupMapper.updateDesignLayerGroup(parentDataGroup);
	    	
	    	result = designLayerGroupMapper.deleteDesignLayerGroup(designLayerGroup);
    	} else {

    	}

    	return result;
    }
}
