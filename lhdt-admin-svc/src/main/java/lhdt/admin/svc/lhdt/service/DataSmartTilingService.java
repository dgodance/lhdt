package lhdt.admin.svc.lhdt.service;

import lhdt.admin.svc.lhdt.domain.DataSmartTilingFileInfo;

/**
 * Smart Tiling 데이터 파일 관리
 * @author jeongdae
 *
 */
public interface DataSmartTilingService {
	
	/**
	 * Smart Tiling Data 등록
	 * @param dataSmartTilingFileInfo
	 * @return
	 */
	DataSmartTilingFileInfo upsertDataSmartTiling(DataSmartTilingFileInfo dataSmartTilingFileInfo);
}
