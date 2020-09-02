package lhdt.service.impl;

import lhdt.domain.common.SpatialOperationInfo;
import lhdt.domain.data.DataInfo;
import lhdt.domain.extrusionmodel.DesignLayerBuilding;
import lhdt.domain.extrusionmodel.DesignLayerLand;
import lhdt.persistence.GeometryMapper;
import lhdt.service.GeometryService;
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
    public List<DesignLayerLand> getIntersectionDesignLayerLands(SpatialOperationInfo spatialOperationInfo) {
        return geometryMapper.getIntersectionDesignLayerLands(spatialOperationInfo);
    }

    @Transactional(readOnly=true)
    public List<DesignLayerBuilding> getIntersectionDesignLayerBuildings(SpatialOperationInfo spatialOperationInfo) {
        return geometryMapper.getIntersectionDesignLayerBuildings(spatialOperationInfo);
    }

    @Transactional(readOnly=true)
    public List<DataInfo> getIntersectionDatas(SpatialOperationInfo spatialOperationInfo) {
        return geometryMapper.getIntersectionDatas(spatialOperationInfo);
    }
}
