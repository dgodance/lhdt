package lhdt.admin.svc.lhdt.persistence;

import java.util.List;

import lhdt.admin.svc.lhdt.domain.DesignLayer;
import lhdt.cmmn.config.LhdtConnMapper;

@LhdtConnMapper
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
     * designLayerKey 중복 체크
     * @param designLayerKey
     * @return
     */
    Boolean isDesignLayerKeyDuplication(String designLayerKey);

    /**
    * 자식 Design Layer 중 순서가 최대인 Design Layer를 검색
    * @param designLayerId
    * @return
    */
    DesignLayer getMaxViewOrderChildDesignLayer(Long designLayerId);

    /**
    * 자식 Design Layer 개수
    * @param designLayerId
    * @return
    */
    int getChildDesignLayerCount(Long designLayerId);

    /**
    * Design Layer 트리 부모와 순서로 그룹 정보 취득
    * @param designLayer
    * @return
    */
    DesignLayer getDesignLayerByParentAndViewOrder(DesignLayer designLayer);

    /**
    * Design Layer 테이블의 컬럼 타입이 어떤 geometry 타입인지를 구함
    * @param designLayerKey
    * @return
    */
    String getGeometryType(String designLayerKey);

    /**
    * Design Layer 등록
    * @param designLayer
    * @return
    */
    int insertDesignLayer(DesignLayer designLayer);

    /**
    * Design Layer 트리 정보 수정
    * @param designLayer
    * @return
    */
    int updateTreeDesignLayer(DesignLayer designLayer);

    /**
    * Design Layer 트리 순서 수정
    * @param designLayer
    * @return
    */
    int updateViewOrderDesignLayer(DesignLayer designLayer);

    /**
    * Design Layer 정보 수정
    * @param designLayer
    * @return
    */
    int updateDesignLayer(DesignLayer designLayer);

    /**
    * Design Layer 삭제
    * @param designLayerId
    * @return
    */
    int deleteDesignLayer(Long designLayerId);
    
    /**
     * Design Layer 테이블 삭제
     * @param designLayerKey
     * @return
     */
    int deleteDesignLayerTable(String designLayerKey);
    
    /**
     * Design Layer 칼럼 목록을 조회
     * @param designLayerKey
     * @return
     */
    String getDesignLayerColumn(String designLayerKey);
    
    /**
     * Design Layer 존재 하는지 확인
     * @param designLayerKey
     * @return
     */
    String isDesignLayerExists(String designLayerKey);
}
