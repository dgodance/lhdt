package lhdt.service.impl;

import lhdt.domain.common.SpatialOperationInfo;
import lhdt.domain.data.DataInfo;
import lhdt.domain.extrusionmodel.DesignLayerBuildingDto;
import lhdt.domain.extrusionmodel.DesignLayerBuildingHeightDto;
import lhdt.domain.extrusionmodel.DesignLayerLandDto;
import lhdt.persistence.GeometryMapper;
import lhdt.service.GeometryService;
import lhdt.support.GeometrySupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GeometryServiceImpl implements GeometryService {

    private final GeometryMapper geometryMapper;

    public GeometryServiceImpl(GeometryMapper geometryMapper) {
        this.geometryMapper = geometryMapper;
    }

    @Transactional(readOnly=true)
    public List<DesignLayerLandDto> getIntersectionDesignLayerLands(SpatialOperationInfo spatialOperationInfo) {
        spatialOperationInfo.setWkt(GeometrySupport.toWKT(spatialOperationInfo.getGeometryInfo()));

        return geometryMapper.getIntersectionDesignLayerLands(spatialOperationInfo);
    }

    @Transactional(readOnly=true)
    public List<DesignLayerBuildingDto> getIntersectionDesignLayerBuildings(SpatialOperationInfo spatialOperationInfo) {
        spatialOperationInfo.setWkt(GeometrySupport.toWKT(spatialOperationInfo.getGeometryInfo()));

        return geometryMapper.getIntersectionDesignLayerBuildings(spatialOperationInfo);
    }

    @Transactional(readOnly=true)
    public List<DesignLayerBuildingHeightDto> getIntersectionDesignLayerBuildingHeights(SpatialOperationInfo spatialOperationInfo) {
        spatialOperationInfo.setWkt(GeometrySupport.toWKT(spatialOperationInfo.getGeometryInfo()));

        return geometryMapper.getIntersectionDesignLayerBuildingHeights(spatialOperationInfo);
    }

    @Transactional(readOnly=true)
    public List<DataInfo> getIntersectionDatas(SpatialOperationInfo spatialOperationInfo) {
        spatialOperationInfo.setWkt(GeometrySupport.toWKT(spatialOperationInfo.getGeometryInfo()));

        return geometryMapper.getIntersectionDatas(spatialOperationInfo);
    }
}
