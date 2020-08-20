package lhdt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lhdt.domain.accesslog.AccessLog;
import lhdt.persistence.AccessLogMapper;
import lhdt.service.AccessLogService;

/**
 * 로그 처리
 * @author jeongdae
 *
 */
@Service
public class AccessLogServiceImpl implements AccessLogService {

	@Autowired
	private AccessLogMapper accessLogMapper;

	/**
	 * 모든 서비스 요청에 대한 이력
	 * @param accessLog
	 * @return
	 */
	@Transactional
	public int insertAccessLog(AccessLog accessLog) {
		return accessLogMapper.insertAccessLog(accessLog);
	}
}
