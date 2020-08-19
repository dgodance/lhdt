package lhdt.parser;

import java.util.Map;

import lhdt.domain.data.DataAttributeFileInfo;

/**
 * @author Cheon JeongDae
 *
 */
public interface DataAttributeFileParser {

	/**
	 *
	 * @param dataId
	 * @param dataAttributeFileInfo
	 * @return
	 */
	Map<String, Object> parse(Long dataId, DataAttributeFileInfo dataAttributeFileInfo);
}
