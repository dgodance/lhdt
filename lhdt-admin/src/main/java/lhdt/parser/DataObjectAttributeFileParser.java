package lhdt.parser;

import java.util.Map;

import lhdt.domain.data.DataObjectAttributeFileInfo;

/**
 * 데이터 속성 파일 분석
 * @author Cheon JeongDae
 *
 */
public interface DataObjectAttributeFileParser {
	
	/**
	 * @param dataId
	 * @param dataObjectAttributeFileInfo
	 * @return
	 */
	Map<String, Object> parse(Long dataId, DataObjectAttributeFileInfo dataObjectAttributeFileInfo);
}
