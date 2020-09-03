package lhdt.service;

import lhdt.domain.common.SpatialOperationInfo;
import lhdt.domain.data.DataInfo;
import lhdt.domain.extrusionmodel.DesignLayerBuilding;
import lhdt.domain.extrusionmodel.DesignLayerLand;

import java.util.List;

public interface GeometryService {

    /**
     * geometry intersection 필지 정보 리턴
     * @param spatialOperationInfo geometry 정보
     * @return
     */
    List<DesignLayerLand> getIntersectionDesignLayerLands(SpatialOperationInfo spatialOperationInfo);

    /**
     * geometry intersection 빌딩 정보 리턴
     * @param spatialOperationInfo geometry 정보
     * @return
     */
    List<DesignLayerBuilding> getIntersectionDesignLayerBuildings(SpatialOperationInfo spatialOperationInfo);

    /**
     * geometry intersection 데이터 정보 리턴
     * @param spatialOperationInfo geometry 정보
     * @return
     */
    List<DataInfo> getIntersectionDatas(SpatialOperationInfo spatialOperationInfo);
}
