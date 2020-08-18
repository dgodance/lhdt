package lhdt.service;

import lhdt.domain.extrusionmodel.DesignLayer;
import lhdt.domain.policy.GeoPolicy;

import java.util.List;

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
}
