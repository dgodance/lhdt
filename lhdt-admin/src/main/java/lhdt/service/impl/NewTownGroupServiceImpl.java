package lhdt.service.impl;

import lhdt.domain.Depth;
import lhdt.domain.Move;
import lhdt.domain.newtown.NewTown;
import lhdt.domain.newtown.NewTownGroup;
import lhdt.persistence.NewTownGroupMapper;
import lhdt.service.NewTownGroupService;
import lhdt.service.NewTownService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class NewTownGroupServiceImpl implements NewTownGroupService {

	@Autowired
	private NewTownService newTownService;

	@Autowired
	private NewTownGroupMapper newTownGroupMapper;

	/**
	 * 뉴타운 그룹 목록
	 */
	@Transactional(readOnly = true)
	public List<NewTownGroup> getListNewTownGroup() {
		return newTownGroupMapper.getListNewTownGroup();
	}

	/**
     * 뉴타운 그룹 정보 조회
     * @param newTownGroup
     * @return
     */
	@Transactional(readOnly = true)
    public NewTownGroup getNewTownGroup(NewTownGroup newTownGroup) {
		return newTownGroupMapper.getNewTownGroup(newTownGroup);
	}

	/**
	 * 뉴타운 그룹 목록 및 하위 뉴타운를 조회
     * @return
     */
	@Transactional(readOnly = true)
	public List<NewTownGroup> getListNewTownGroupAndLayer() {
		List<NewTownGroup> newTownGroupList = newTownGroupMapper.getListNewTownGroup();
		for(NewTownGroup newTownGroup : newTownGroupList) {
			NewTown newTown = new NewTown();
			newTown.setNewTownGroupId(newTownGroup.getNewTownGroupId());
			newTownGroup.setNewTownList(newTownService.getListNewTown(newTown));
		}

		return newTownGroupList;
	}

	/**
	 * 데이터 그룹 표시 순서 수정 (up/down)
	 * @param newTownGroup
	 * @return
	 */
    @Transactional
	public int updateNewTownGroupViewOrder(NewTownGroup newTownGroup) {

    	NewTownGroup dbNewTownGroup = newTownGroupMapper.getNewTownGroup(newTownGroup);
    	dbNewTownGroup.setUpdateType(newTownGroup.getUpdateType());

    	Integer modifyViewOrder = dbNewTownGroup.getViewOrder();
    	NewTownGroup searchNewTownGroup = new NewTownGroup();
    	searchNewTownGroup.setUpdateType(dbNewTownGroup.getUpdateType());
    	searchNewTownGroup.setParent(dbNewTownGroup.getParent());

    	if(Move.UP == Move.valueOf(dbNewTownGroup.getUpdateType())) {
    		// 바로 위 메뉴의 view_order 를 +1
    		searchNewTownGroup.setViewOrder(dbNewTownGroup.getViewOrder());
    		searchNewTownGroup = getDataLayerByParentAndViewOrder(searchNewTownGroup);

    		if(searchNewTownGroup == null) return 0;

	    	dbNewTownGroup.setViewOrder(searchNewTownGroup.getViewOrder());
	    	searchNewTownGroup.setViewOrder(modifyViewOrder);
    	} else {
    		// 바로 아래 메뉴의 view_order 를 -1 함
    		searchNewTownGroup.setViewOrder(dbNewTownGroup.getViewOrder());
    		searchNewTownGroup = getDataLayerByParentAndViewOrder(searchNewTownGroup);

    		if(searchNewTownGroup == null) return 0;

    		dbNewTownGroup.setViewOrder(searchNewTownGroup.getViewOrder());
    		searchNewTownGroup.setViewOrder(modifyViewOrder);
    	}

    	updateViewOrderNewTownGroup(searchNewTownGroup);
		return updateViewOrderNewTownGroup(dbNewTownGroup);
    }

    /**
	 * 뉴타운 그룹 등록
	 */
	@Transactional
	public int insertNewTownGroup(NewTownGroup newTownGroup) {
		
		Integer parentNewTownGroupId = 0;
    	
    	NewTownGroup parentNewTownGroup = new NewTownGroup();
    	int depth = 0;
    	if(newTownGroup.getParent() > 0) {
    		parentNewTownGroupId = newTownGroup.getParent();
    		parentNewTownGroup.setNewTownGroupId(parentNewTownGroupId);
    		parentNewTownGroup = newTownGroupMapper.getNewTownGroup(parentNewTownGroup);
	    	depth = parentNewTownGroup.getDepth() + 1;
    	}
    	
    	int result = newTownGroupMapper.insertNewTownGroup(newTownGroup); 
		
    	if(depth > 1) {
	    	// parent 의 children update
    		Integer children = parentNewTownGroup.getChildren();
    		if(children == null) children = 0;
    		children += 1;
    		
    		parentNewTownGroup = new NewTownGroup();
    		parentNewTownGroup.setNewTownGroupId(parentNewTownGroupId);
    		parentNewTownGroup.setChildren(children);
	    	return newTownGroupMapper.updateNewTownGroup(parentNewTownGroup);
    	}
    	
		return result; 
	}

	/**
	 * 뉴타운 그룹 수정
	 * @param newTownGroup
	 * @return
	 */
    @Transactional
	public int updateNewTownGroup(NewTownGroup newTownGroup) {
    	return newTownGroupMapper.updateNewTownGroup(newTownGroup);
    }

    /**
     * 부모와 표시 순서로 메뉴 조회
     * @param newTownGroup
     * @return
     */
    private NewTownGroup getDataLayerByParentAndViewOrder(NewTownGroup newTownGroup) {
    	return newTownGroupMapper.getNewTownGroupByParentAndViewOrder(newTownGroup);
    }

    /**
	 * 사용자 그룹 표시 순서 수정 (up/down)
	 * @param newTownGroup
	 * @return
	 */
	private int updateViewOrderNewTownGroup(NewTownGroup newTownGroup) {
		return newTownGroupMapper.updateNewTownGroupViewOrder(newTownGroup);
	}

    /**
	 * 뉴타운 그룹 삭제
	 * @param newTownGroup
	 * @return
	 */
    @Transactional
	public int deleteNewTownGroup(NewTownGroup newTownGroup) {
    	// 삭제하고, children update

    	newTownGroup = newTownGroupMapper.getNewTownGroup(newTownGroup);

    	int result = 0;
    	if(Depth.ONE == Depth.findBy(newTownGroup.getDepth())) {
    		result = newTownGroupMapper.deleteNewTownGroupByAncestor(newTownGroup);
    	} else if(Depth.TWO == Depth.findBy(newTownGroup.getDepth())) {

    		NewTownGroup ancestorNewTownGroup = new NewTownGroup();
    		ancestorNewTownGroup.setNewTownGroupId(newTownGroup.getAncestor());
    		ancestorNewTownGroup = newTownGroupMapper.getNewTownGroup(ancestorNewTownGroup);
    		ancestorNewTownGroup.setChildren(ancestorNewTownGroup.getChildren() - 1);
	    	newTownGroupMapper.updateNewTownGroup(ancestorNewTownGroup);
	    	
	    	result = newTownGroupMapper.deleteNewTownGroupByParent(newTownGroup);
    		// ancestor - 1
    	} else if(Depth.THREE == Depth.findBy(newTownGroup.getDepth())) {

    		NewTownGroup parentDataGroup = new NewTownGroup();
	    	parentDataGroup.setNewTownGroupId(newTownGroup.getParent());
	    	parentDataGroup = newTownGroupMapper.getNewTownGroup(parentDataGroup);
	    	parentDataGroup.setChildren(parentDataGroup.getChildren() - 1);
	    	newTownGroupMapper.updateNewTownGroup(parentDataGroup);
	    	
	    	result = newTownGroupMapper.deleteNewTownGroup(newTownGroup);
    	} else {

    	}

    	return result;
    }
}
