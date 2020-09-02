package lhdt.api;

import lhdt.domain.common.SpatialOperationInfo;
import lhdt.domain.data.DataInfo;
import lhdt.domain.data.DataInfoDto;
import lhdt.domain.extrusionmodel.DesignLayer;
import lhdt.service.GeometryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/geometry", produces = MediaTypes.HAL_JSON_VALUE)
public class GeometryAPIController {

    private final GeometryService geometryService;
    private final ModelMapper modelMapper;

    /**
     * geoemtry 정보와 intersection 필지, 빌딩 정보 리턴
     * @param spatialOperationInfo
     * @return
     */
    @GetMapping("/intersection/design-layers")
    public ResponseEntity<CollectionModel<EntityModel<?>>> getIntersectionDesignLayers(@RequestBody SpatialOperationInfo spatialOperationInfo) {
        String type = spatialOperationInfo.getType().toUpperCase();
        List<?> designLayerList = new ArrayList<>();
        if(DesignLayer.DesignLayerType.LAND == DesignLayer.DesignLayerType.valueOf(type)) {
            designLayerList = geometryService.getIntersectionDesignLayerLands(spatialOperationInfo);
        } else if(DesignLayer.DesignLayerType.BUILDING == DesignLayer.DesignLayerType.valueOf(type)) {
            designLayerList = geometryService.getIntersectionDesignLayerBuildings(spatialOperationInfo);
        }
        List<EntityModel<?>> designLayerEntity = designLayerList.stream().map(EntityModel::of).collect(Collectors.toList());

        CollectionModel<EntityModel<?>> model = CollectionModel.of(designLayerEntity);

        model.add(linkTo(GeometryAPIController.class).withSelfRel());
        model.add(Link.of("/docs/index.html#resource-geometry-design-layer-list").withRel("profile"));

        return ResponseEntity.ok(model);
    }

    @GetMapping("/intersection/datas")
    public ResponseEntity<CollectionModel<EntityModel<DataInfoDto>>> getIntersectionDatas(@RequestBody SpatialOperationInfo spatialOperationInfo) {
        List<DataInfo> dataList = geometryService.getIntersectionDatas(spatialOperationInfo);
        List<EntityModel<DataInfoDto>> dataInfoEntity = dataList.stream()
                .map(f -> EntityModel.of(modelMapper.map(f, DataInfoDto.class)))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<DataInfoDto>> model = CollectionModel.of(dataInfoEntity);

        model.add(linkTo(GeometryAPIController.class).withSelfRel());
        model.add(Link.of("/docs/index.html#resource-geometry-data-list").withRel("profile"));

        return ResponseEntity.ok(model);
    }
}
