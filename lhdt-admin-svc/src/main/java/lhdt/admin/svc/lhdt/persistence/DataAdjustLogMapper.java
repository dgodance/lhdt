package lhdt.admin.svc.lhdt.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import lhdt.admin.svc.config.LhdtConnMapper;
import lhdt.admin.svc.lhdt.domain.DataAdjustLog;

/**
 * 데이터 geometry 변경 이력
 * @author jeongdae
 *
 */
@LhdtConnMapper
public interface DataAdjustLogMapper {

	/**
	 * 데이터 geometry 변경 요청 수
	 * @param dataAdjustLog
	 * @return
	 */
	Long getDataAdjustLogTotalCount(DataAdjustLog dataAdjustLog);
	
	/**
	 * 데이터 geometry 변경 요청 목록
	 * @param dataAdjustLog
	 * @return
	 */
	List<DataAdjustLog> getListDataAdjustLog(DataAdjustLog dataAdjustLog);
	
	/**
	 * 데이터 geometry 변경 이력 조회
	 * @param dataAdjustLogId
	 * @return
	 */
	DataAdjustLog getDataAdjustLog(Long dataAdjustLogId);

	/**
	 * 최근 데이터 geometry 변경 요청 목록
	 * @return
	 */
	List<DataAdjustLog> getListRecentDataAdjustLog();
	
	/**
	 * 데이터 geometry 변경 이력 저장
	 * @param dataAdjustLog
	 * @return
	 */
	int insertDataAdjustLog(DataAdjustLog dataAdjustLog);
	
	/**
	 * 데이터 geometry 변경 요청 상태 변경
	 * @param dataAdjustLog
	 * @return
	 */
	int updateDataAdjustLogStatus(DataAdjustLog dataAdjustLog);
}
