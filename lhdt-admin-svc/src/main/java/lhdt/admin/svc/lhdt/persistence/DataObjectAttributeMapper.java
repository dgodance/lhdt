package lhdt.admin.svc.lhdt.persistence;

import lhdt.admin.svc.lhdt.domain.DataObjectAttribute;
import lhdt.admin.svc.lhdt.domain.DataObjectAttributeFileInfo;
import lhdt.ds.common.config.LhdtConnMapper;


/**
 * 데이터 속성 파일 관리
 * @author jeongdae
 *
 */
@LhdtConnMapper
public interface DataObjectAttributeMapper {
	
	/**
	 * 데이터 Object 속성 정보를 취득
	 * @param dataId
	 * @return
	 */
	DataObjectAttribute getDataObjectAttribute(Long dataId);
	
	/**
	 * 데이터 Object 속성 정보 등록
	 * @param dataObjectAttribute
	 * @return
	 */
	int insertDataObjectAttribute(DataObjectAttribute dataObjectAttribute);
	
	/**
	 * Data Object Attribute 파일 정보 등록
	 * @param dataObjectAttributeFileInfo
	 * @return
	 */
	int insertDataObjectAttributeFileInfo(DataObjectAttributeFileInfo dataObjectAttributeFileInfo);
	
	/**
	 * 데이터 Object 속성 정보 수정
	 * @param dataObjectAttribute
	 * @return
	 */
	int updateDataObjectAttribute(DataObjectAttribute dataObjectAttribute);
	
	/**
	 * 데이터 Object 속성 파일 정보 수정
	 * @param dataObjectAttributeFileInfo
	 * @return
	 */
	int updateDataObjectAttributeFileInfo(DataObjectAttributeFileInfo dataObjectAttributeFileInfo);
	
}
