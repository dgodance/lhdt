package lhdt.service.impl;

import lhdt.domain.microservice.MicroService;
import lhdt.persistence.MicroServiceMapper;
import lhdt.service.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Role
 * @author jeongdae
 *
 */
@Service
public class MicroServiceServiceImpl implements MicroServiceService {
	
	@Autowired
	private MicroServiceMapper microServiceMapper;

	/**
	 * 마이크로 서비스 목록
	 * @param microService
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<MicroService> getListMicroService(MicroService microService) {
		return microServiceMapper.getListMicroService(microService);
	}
}
