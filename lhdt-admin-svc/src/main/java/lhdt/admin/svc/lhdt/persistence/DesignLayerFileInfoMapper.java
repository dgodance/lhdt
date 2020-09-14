package lhdt.admin.svc.lhdt.persistence;

import java.util.List;
import java.util.Map;

import lhdt.admin.svc.lhdt.domain.DesignLayerFileInfo;
import lhdt.cmmn.config.LhdtConnMapper;

/**
 * design layer shape 파일 정보
 * @author Cheon JeongDae
 *
 */
@LhdtConnMapper
public interface DesignLayerFileInfoMapper {
	
	/**
	 * design 레이어 shape 파일 version
	 * @param designLayerFileInfoId
	 * @return
	 */
	Integer getDesignLayerShapeFileVersion(Long designLayerFileInfoId);
	
	/**
	 * designLayerId에 해당하는 모든 파일 경로 목록
	 * @param designLayerId
	 * @return
	 */
	List<String> getListDesignLayerFilePath(Long designLayerId);
	
	/**
	 * design layer shape 파일 목록
	 * @return
	 */
	List<DesignLayerFileInfo> getListDesignLayerFileInfo(Long designLayerId);
	
	/**
	 * design layer 파일 정보 취득
	 * @param designLayerFileInfoId
	 * @return
	 */
	DesignLayerFileInfo getDesignLayerFileInfo(Long designLayerFileInfoId);
	
	/**
	 * design layer shape 파일 그룹 정보 취득
	 * @param designLayerFileInfoTeamId
	 * @return
	 */
	List<DesignLayerFileInfo> getDesignLayerFileInfoTeam(Long designLayerFileInfoTeamId);
	
	/**
	 * design 레이어별 shape 파일 version 최대값 + 1 을 취득
	 * @param designLayerId
	 * @return
	 */
	Integer getMaxFileVersion(Long designLayerId);
	
	/**
	 * design layer shape 파일이 있는지 확인
	 * @param designLayerId
	 * @return
	 */
	boolean isDesignLayerFileInfoExist(Long designLayerId);
	
	/**
	 * design layer shape 파일 업데이트 날짜
	 * @param designLayerFileInfoId
	 * @return
	 */
	String getDesignLayerShapeFileUpdateDate(Long designLayerFileInfoId);
	
	/**
	 * design 레이어 이력내 활성화 된 데이터 정보를 취득
	 * @param designLayerId
	 * @return
	 */
	DesignLayerFileInfo getEnableDesignLayerFileInfo(Long designLayerId);
	
	/**
	 * design layer shape 파일 정보 등록
	 * @param designLayerFileInfo
	 * @return
	 */
	int insertDesignLayerFileInfoMapper(DesignLayerFileInfo designLayerFileInfo);
	
	/**
	 * designLayerId와 일치하는 모든 shape 파일의 상태를 design layer 비활성화로 함
	 * @param designLayerId
	 * @return
	 */
	int updateDesignLayerFileInfoAllDisabledByDesignLayerId(Long designLayerId);
	
	/**
	 * 동일 그룹 designLayerFileInfo 정보 활성화로 수정
	 * @param designLayerFileInfoTeamMap
	 * @return
	 */
	int updateDesignLayerFileInfoTeam(Map<String, Object> designLayerFileInfoTeamMap);
	
	/**
	 * design layer shape 파일 정보 수정
	 * @param designLayerFileInfo
	 * @return
	 */
	int updateDesignLayerFileInfo(DesignLayerFileInfo designLayerFileInfo);
	
	/**
	 * designLayerFileInfoTeamId에 의한 design layer shape 파일 정보 수정
	 * @param designLayerFileInfo
	 * @return
	 */
	int updateDesignLayerFileInfoByTeamId(DesignLayerFileInfo designLayerFileInfo);
	
	/**
	 * 해당 design 레이어의 이전 데이터를 전부 비활성화 상태로 수정
	 * @param tableName
	 * @return
	 */
	int updateShapePreDataDisable(String tableName);
	
	/**
	 * org2org를 이용해서 생성한 테이블을 데이터 version 갱신
	 * @param map
	 * @return
	 */
	int updateOgr2OgrDataFileVersion(Map<String, String> map);
	
	/**
	 * shape 테이블 데이터 상태 변경
	 * @param map
	 * @return
	 */
	int updateOgr2OgrStatus(Map<String, String> map);
	
	/**
	 * design 레이어 삭제
	 * @param designLayerId
	 * @return
	 */
	int deleteDesignLayerFileInfo(Long designLayerId);
	
	/**
	 * team id 로 design 레이어 파일 이력을 삭제
	 * @param designLayerFileInfoTeamId
	 * @return
	 */
	int deleteDesignLayerFileInfoByTeamId(Long designLayerFileInfoTeamId);
}
