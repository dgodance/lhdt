package lhdt.service;

import lhdt.domain.policy.GeoPolicy;
import lhdt.domain.extrusionmodel.DesignLayer;
import lhdt.domain.extrusionmodel.DesignLayerFileInfo;

import java.util.List;
import java.util.Map;

public interface DesignLayerService {

	/**
	 * design layer 총 건수
	 * @param designLayer
	 * @return
	 */
	Long getDesignLayerTotalCount(DesignLayer designLayer);
	
    /**
    * design layer 목록
    * @return
    */
    List<DesignLayer> getListDesignLayer(DesignLayer designLayer);
    
    /**
     * geoserver에 등록된 design 레이어 목록 조회
     * @return
     */
    String getListGeoserverDesignLayer(GeoPolicy geoPolicy);

    /**
    * design layer 정보 취득
    * @param designLayerId
    * @return
    */
    DesignLayer getDesignLayer(Long designLayerId);
    
    /**
     * designLayerKey 중복 체크
     * @param designLayer
     * @return
     */
    Boolean isDesignLayerKeyDuplication(String designLayer);
    
    /**
    * design 레이어 테이블의 컬럼 타입이 어떤 geometry 타입인지를 구함
    * @param designLayerKey
    * @return
    */
    String getGeometryType(String designLayerKey);

    /**
     * design 레이어의 칼럼 목록을 조회
     * @param designLayerKey
     * @return
     */
    String getDesignLayerColumn(String designLayerKey);

    /**
    * design 레이어 등록
    * @param designLayer
    * @return
    */
    Map<String, Object> insertDesignLayer(DesignLayer designLayer, List<DesignLayerFileInfo> designLayerFileInfoList);

    /**
    * shape 파일을 이용한 design layer 정보 수정
    * @param designLayer
    * @param isDesignLayerFileInfoExist
    * @param designLayerFileInfoList
    * @return
    * @throws Exception
    */
    Map<String, Object> updateDesignLayer(DesignLayer designLayer, boolean isDesignLayerFileInfoExist, List<DesignLayerFileInfo> designLayerFileInfoList);

    /**
    * Ogr2Ogr 실행
    * @param designLayer
    * @param isDesignLayerFileInfoExist
    * @param shapeFileName
    * @param shapeEncoding
    * @throws Exception
    */
    void insertOgr2Ogr(DesignLayer designLayer, boolean isDesignLayerFileInfoExist, String shapeFileName, String shapeEncoding) throws Exception;

    /**
     * shp파일 정보를 db정보를 기준으로 갱신
     * @param designLayerFileInfo
     * @param designLayer
     * @throws Exception
     */
    void exportOgr2Ogr(DesignLayerFileInfo designLayerFileInfo, DesignLayer designLayer) throws Exception;
    
    /**
    * design layer 가 등록 되어 있지 않은 경우 rest api 를 이용해서 design layer를 등록
     * @param geoPolicy
     * @param designLayerKey
     * @throws Exception
     */
    void registerDesignLayer(GeoPolicy geoPolicy, String designLayerKey) throws Exception;
    
    /**
	 * design 레이어의 스타일 정보를 수정
	 * @param designLayer
	 * @return
	 */
	int updateDesignLayerStyle(DesignLayer designLayer) throws Exception;

    /**
    * design 레이어 롤백 처리
    * @param designLayer
    * @param isDesignLayerFileInfoExist
    * @param designLayerFileInfo
    * @param deleteDesignLayerFileInfoTeamId
    */
    void rollbackDesignLayer(DesignLayer designLayer, boolean isDesignLayerFileInfoExist, DesignLayerFileInfo designLayerFileInfo, Long deleteDesignLayerFileInfoTeamId);

    /**
    * design layer 를 이 shape 파일로 활성화
    * @param designLayerId
    * @param deleteDesignLayerFileInfoTeamId
    * @param designLayerFileInfoId
    * @return
    */
    int updateDesignLayerByDesignLayerFileInfoId(Long designLayerId, Long deleteDesignLayerFileInfoTeamId, Long designLayerFileInfoId);

     /**
    * design 레이어 삭제
    * @param designLayerId
    * @return
    */
    int deleteDesignLayer(Long designLayerId);
}
