package lhdt.persistence;

import lhdt.domain.extrusionmodel.DesignLayer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignLayerMapper {
	
	/**
	 * Design Layer 총 건수
	 * @param designLayer
	 * @return
	 */
	Long getDesignLayerTotalCount(DesignLayer designLayer);

    /**
    * Design Layer 목록
    * @param designLayer
    * @return
    */
    List<DesignLayer> getListDesignLayer(DesignLayer designLayer);

    /**
    * Design Layer 정보 취득
    * @param designLayerId
    * @return
    */
    DesignLayer getDesignLayer(Long designLayerId);
    

    /**
    * Design Layer 테이블의 컬럼 타입이 어떤 geometry 타입인지를 구함
    * @param designLayerKey
    * @return
    */
    String getGeometryType(String designLayerKey);

    /**
     * Design Layer 칼럼 목록을 조회
     * @param designLayerKey
     * @return
     */
    String getDesignLayerColumn(String designLayerKey);
}
