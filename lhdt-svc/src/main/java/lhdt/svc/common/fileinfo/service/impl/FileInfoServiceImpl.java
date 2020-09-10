/**
 * 
 */
package lhdt.svc.common.fileinfo.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lhdt.ds.common.misc.DsServiceImpl;
import lhdt.svc.common.fileinfo.model.FileInfo;
import lhdt.svc.common.fileinfo.persistence.FileInfoMapper;
import lhdt.svc.common.fileinfo.persistence.FileInfoRepository;
import lhdt.svc.common.fileinfo.service.FileInfoService;

/**
 * @author gravity
 * @since 2020. 9. 4.
 *
 */
@Service("fileInfoService")
public class FileInfoServiceImpl extends DsServiceImpl<FileInfoRepository, FileInfoMapper, FileInfo, Long> implements FileInfoService {

	@Autowired
	private FileInfoRepository jpaRepo;
	
	@Autowired
	private FileInfoMapper mapper;
	
	@PostConstruct
	private void init() {
		super.set(jpaRepo, mapper, new FileInfo());
	}

}
