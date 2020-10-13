/**
 * 
 */
package lhdt.svc.common.fileinfo.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lhdt.cmmn.misc.CmmnServiceImpl;
import lhdt.svc.common.fileinfo.model.FileInfo;
import lhdt.svc.common.fileinfo.persistence.FileInfoMapper;
import lhdt.svc.common.fileinfo.persistence.FileInfoRepository;
import lhdt.svc.common.fileinfo.service.FileInfoService;

/**
 * @author gravity
 * @since 2020. 9. 4.
 * 파일정보 제공 서비스 상세 구현 클래스
 */
@Service("fileInfoService")
public class FileInfoServiceImpl extends CmmnServiceImpl<FileInfoRepository, FileInfoMapper, FileInfo, Long> implements FileInfoService {

	@Autowired
	private FileInfoRepository jpaRepo;
	
	@Autowired
	private FileInfoMapper mapper;

	/**
	 * 데이터 베이스 연결 파일 정보 주입
	 */
	@PostConstruct
	private void init() {
		super.set(jpaRepo, mapper, new FileInfo());
	}

}
