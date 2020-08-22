package lhdt.api;

import lhdt.domain.common.Pagination;
import lhdt.domain.extrusionmodel.DesignLayer;
import lhdt.domain.extrusionmodel.DesignLayerDto;
import lhdt.service.DesignLayerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/design-layers", produces = MediaTypes.HAL_JSON_VALUE)
public class DesignLayerAPIController {

    private final DesignLayerService designLayerService;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * 디자인 레이어 목록 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DesignLayerDto>>> getDesignLayers(@RequestParam(defaultValue = "1") String pageNo, DesignLayer designLayer) {

        Long totalCount = designLayerService.getDesignLayerTotalCount(designLayer);
        Pagination pagination = new Pagination(null, designLayer.getParameters(), totalCount, Long.parseLong(pageNo), designLayer.getListCounter());
        designLayer.setOffset(pagination.getOffset());
        designLayer.setLimit(pagination.getPageRows());

        List<DesignLayer> designLayerList = new ArrayList<>();
        if (totalCount > 0L) {
            designLayerList = designLayerService.getListDesignLayer(designLayer);
        }

        List<EntityModel<DesignLayerDto>> designLayerDtoList = designLayerList.stream()
                .map(f -> EntityModel.of(modelMapper.map(f, DesignLayerDto.class))
                        .add(linkTo(DesignLayerAPIController.class).slash(f.getDesignLayerId()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<DesignLayerDto>> model = CollectionModel.of(designLayerDtoList);

        model.add(linkTo(DesignLayerAPIController.class).withSelfRel());
        model.add(Link.of("/docs/index.html#resource-design-layer-list").withRel("profile"));

        return ResponseEntity.ok(model);
    }

    /**
     * 디자인 레이어 한건 조회
     *
     * @param id 디자인 레이어 아이디
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DesignLayerDto>> getDesignLayerById(@PathVariable("id") Long id) {
        DesignLayerDto dto = modelMapper.map(designLayerService.getDesignLayer(id), DesignLayerDto.class);
        EntityModel<DesignLayerDto> designLayer = EntityModel.of(dto);
        designLayer.add(linkTo(DesignLayerAPIController.class).slash(id).withSelfRel());
        designLayer.add(Link.of("/docs/index.html#resource-design-layer-get").withRel("profile"));

        return ResponseEntity.ok(designLayer);
    }
}
