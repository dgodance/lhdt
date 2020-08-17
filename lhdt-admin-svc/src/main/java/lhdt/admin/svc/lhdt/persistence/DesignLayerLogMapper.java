package lhdt.admin.svc.lhdt.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import lhdt.admin.svc.config.LhdtConnMapper;
import lhdt.admin.svc.lhdt.domain.DesignLayerLog;


/**
 * 디자인 레이어 로그 처리
 * @author jeongdae
 *
 */
@LhdtConnMapper
public interface DesignLayerLogMapper {
	
	/**
	 * 디자인 레이어 이력 총 건수
	 * @param designLayerLog
	 * @return
	 */
	Long getDesignLayerLogTotalCount(DesignLayerLog designLayerLog);
	
	/**
	 * 디자인 레이어 이력 목록
	 * @param designLayerLog
	 * @return
	 */
	List<DesignLayerLog> getListDesignLayerLog(DesignLayerLog designLayerLog);
	
	/**
	 * 디자인 레이어 정보 취득
	 * @param designLayerLogId
	 * @return
	 */
	DesignLayerLog getDesignLayerLog(Long designLayerLogId);

	/**
	 * 디자인 레이어 이력 등록
	 * @param designLayerLog
	 * @return
	 */
	int insertDesignLayerLog(DesignLayerLog designLayerLog);
}
