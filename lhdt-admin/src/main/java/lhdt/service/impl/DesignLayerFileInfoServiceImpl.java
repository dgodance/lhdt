package lhdt.service.impl;

import lhdt.domain.extrusionmodel.DesignLayerFileInfo;
import lhdt.persistence.DesignLayerFileInfoMapper;
import lhdt.service.DesignLayerFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DesignLayerFileInfoServiceImpl implements DesignLayerFileInfoService {

	@Autowired
	private DesignLayerFileInfoMapper designLayerFileInfoMapper;

	/**
	 * design layer shape 파일 목록
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DesignLayerFileInfo> getListDesignLayerFileInfo(Long designLayerId) {
		return designLayerFileInfoMapper.getListDesignLayerFileInfo(designLayerId);
	}
	
	/**
	 * designLayerId에 해당하는 모든 파일 경로 목록
	 * @param designLayerId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<String> getListDesignLayerFilePath(Long designLayerId) {
		return designLayerFileInfoMapper.getListDesignLayerFilePath(designLayerId);
	}
	
	/**
	 * 파일 정보 취득
	 * @param designLayerFileInfoId
	 * @return
	 */
	@Transactional(readOnly=true)
	public DesignLayerFileInfo getDesignLayerFileInfo(Long designLayerFileInfoId) {
		return designLayerFileInfoMapper.getDesignLayerFileInfo(designLayerFileInfoId);
	}

	/**
	 * design layer shape 파일 그룹 정보 취득
	 * @param designLayerFileInfoTeamId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DesignLayerFileInfo> getDesignLayerFileInfoTeam(Long designLayerFileInfoTeamId) {
		return designLayerFileInfoMapper.getDesignLayerFileInfoTeam(designLayerFileInfoTeamId);
	}
	
	/**
	 * design layer shape 파일 version
	 * @param designLayerFileInfoId
	 * @return
	 */
	@Transactional(readOnly=true)
	public Integer getDesignLayerShapeFileVersion(Long designLayerFileInfoId) {
		return designLayerFileInfoMapper.getDesignLayerShapeFileVersion(designLayerFileInfoId);
	}
	
	/**
	 * design 레이어 이력이 존재하는지 검사
	 * @param designLayerId
	 * @return
	 */
	@Transactional(readOnly=true)
	public boolean isDesignLayerFileInfoExist(Long designLayerId) {
		return designLayerFileInfoMapper.isDesignLayerFileInfoExist(designLayerId);
	}
	
	/**
	 * design 레이어 이력내 활성화 된 데이터 정보를 취득
	 * @param designLayerId
	 * @return
	 */
	@Transactional(readOnly=true)
	public DesignLayerFileInfo getEnableDesignLayerFileInfo(Long designLayerId) {
		return designLayerFileInfoMapper.getEnableDesignLayerFileInfo(designLayerId);
	}

	/**
	 * design layer shape 파일 정보 수정
	 * @param designLayerFileInfo
	 * @return
	 */
	@Transactional
	public int updateDesignLayerFileInfo(DesignLayerFileInfo designLayerFileInfo) {
		return designLayerFileInfoMapper.updateDesignLayerFileInfo(designLayerFileInfo);
	}
	
	/**
	 * org2org를 이용해서 생성한 테이블을 데이터 버전 갱신
	 * @param map
	 * @return
	 */
	@Transactional
	public int updateOgr2OgrDataFileVersion(Map<String, String> map) {
		return designLayerFileInfoMapper.updateOgr2OgrDataFileVersion(map);
	}
	
	/**
	 * team id 로 design 레이어 파일 이력을 삭제
	 * @param designLayerFileInfoTeamId
	 * @return
	 */
	@Transactional
	public int deleteDesignLayerFileInfoByTeamId(Long designLayerFileInfoTeamId) {
		return designLayerFileInfoMapper.deleteDesignLayerFileInfoByTeamId(designLayerFileInfoTeamId);
	}
}
