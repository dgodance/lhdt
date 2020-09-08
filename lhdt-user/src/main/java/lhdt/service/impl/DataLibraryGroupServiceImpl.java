package lhdt.service.impl;

import lhdt.domain.extrusionmodel.DataLibraryGroup;
import lhdt.persistence.DataLibraryGroupMapper;
import lhdt.service.DataLibraryGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DataLibraryGroupServiceImpl implements DataLibraryGroupService {

	private final DataLibraryGroupMapper dataLibraryGroupMapper;


	@Transactional(readOnly = true)
	public List<DataLibraryGroup> getListDataLibraryGroup() {
		return dataLibraryGroupMapper.getListDataLibraryGroup();
	}

	@Transactional(readOnly = true)
	public DataLibraryGroup getDataLibraryGroup(DataLibraryGroup dataLibraryGroup) {
		return dataLibraryGroupMapper.getDataLibraryGroup(dataLibraryGroup);
	}

	@Transactional(readOnly = true)
	public List<DataLibraryGroup> getChildrenDataLibraryGroupListByParent(DataLibraryGroup dataLibraryGroup) {
		return dataLibraryGroupMapper.getChildrenDataLibraryGroupListByParent(dataLibraryGroup);
	}

	@Transactional(readOnly = true)
	public List<DataLibraryGroup> getDataLibraryGroupListByDepth(DataLibraryGroup dataLibraryGroup) {
		return dataLibraryGroupMapper.getDataLibraryGroupListByDepth(dataLibraryGroup);
	}
}
