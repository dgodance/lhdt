package lhdt.admin.svc.lhdt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lhdt.admin.svc.lhdt.domain.AccessLog;
import lhdt.admin.svc.lhdt.persistence.AccessLogMapper;
import lhdt.admin.svc.lhdt.service.AccessLogService;

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
	 * 서비스 요청 이력 총 건수
	 * @param accessLog
	 * @return
	 */
	@Transactional(readOnly=true)
	public Long getAccessLogTotalCount(AccessLog accessLog) {
		return accessLogMapper.getAccessLogTotalCount(accessLog);
	}
	
	/**
	 * 서비스 요청 이력 목록
	 * @param accessLog
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<AccessLog> getListAccessLog(AccessLog accessLog) {
		return accessLogMapper.getListAccessLog(accessLog);
	}
	
	/**
	 * 서비스 요청 이력 정보 취득
	 * @param accessLogId
	 * @return
	 */
	@Transactional(readOnly=true)
	public AccessLog getAccessLog(Long accessLogId) {
		return accessLogMapper.getAccessLog(accessLogId);
	}
	
	/**
	 * 모든 서비스 요청에 대한 이력
	 * TODO API
	 * @param accessLog
	 * @return
	 */
	@Transactional
	public int insertAccessLog(AccessLog accessLog) {
		return 0;
//		return accessLogMapper.insertAccessLog(accessLog);
	}
}

