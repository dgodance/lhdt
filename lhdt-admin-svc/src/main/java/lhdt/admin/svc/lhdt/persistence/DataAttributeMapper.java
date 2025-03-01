package lhdt.admin.svc.lhdt.persistence;

import lhdt.admin.svc.lhdt.domain.DataAttribute;
import lhdt.admin.svc.lhdt.domain.DataAttributeFileInfo;
import lhdt.cmmn.config.LhdtConnMapper;

/**
 * 데이터 속성 파일 관리
 * @author jeongdae
 *
 */
@LhdtConnMapper
public interface DataAttributeMapper {
	
	/**
	 * 데이터 속성 정보를 취득
	 * @param dataId
	 * @return
	 */
	DataAttribute getDataAttribute(Long dataId);
	
	/**
	 * 데이터 속성 정보 등록
	 * @param dataAttribute
	 * @return
	 */
	int insertDataAttribute(DataAttribute dataAttribute);
	
	/**
	 * Data Attribute 파일 정보 등록
	 * @param dataAttributeFileInfo
	 * @return
	 */
	int insertDataAttributeFileInfo(DataAttributeFileInfo dataAttributeFileInfo);
	
	/**
	 * 데이터 속성 정보 수정
	 * @param dataAttribute
	 * @return
	 */
	int updateDataAttribute(DataAttribute dataAttribute);
	
	/**
	 * 데이터 속성 파일 정보 수정
	 * @param dataAttributeFileInfo
	 * @return
	 */
	int updateDataAttributeFileInfo(DataAttributeFileInfo dataAttributeFileInfo);
}
