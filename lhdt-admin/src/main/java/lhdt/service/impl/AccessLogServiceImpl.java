package lhdt.service.impl;

import java.util.List;

import lhdt.persistence.CommonMapper;
import lhdt.service.CommonService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class AccessLogServiceImpl implements AccessLogService {

	@Autowired
	private AccessLogMapper accessLogMapper;
	@Autowired
	private CommonService commonService;

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
	 * 스케줄러에 의한 다음년도 파티션 테이블 자동 생성
	 * @param tableName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Transactional
	public int createPartitionTable(String tableName, String startTime, String endTime) {
		Boolean exist = commonService.isTableExist("access_log_" + tableName);
		log.info("@@@ accessLog tableName = {}, isTableExist = {}", tableName, exist);

		if(!exist) return accessLogMapper.createPartitionTable(tableName, startTime, endTime);

		return 0;
	}
	
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
