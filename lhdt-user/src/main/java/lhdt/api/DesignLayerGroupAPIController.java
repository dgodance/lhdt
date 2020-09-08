package lhdt.api;

import lhdt.domain.extrusionmodel.DesignLayerGroupDto;
import lhdt.service.DesignLayerGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/design-layer-groups", produces = MediaTypes.HAL_JSON_VALUE)
public class DesignLayerGroupAPIController {

    private final DesignLayerGroupService designLayerGroupService;
    private final ModelMapper modelMapper;

    /**
     * 디자인 레이어 그룹 목록 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DesignLayerGroupDto>>> getDesignLayerGroups() {
        List<EntityModel<DesignLayerGroupDto>> designLayerGroupList = designLayerGroupService.getListDesignLayerGroup()
                .stream()
                .map(f -> EntityModel.of(modelMapper.map(f, DesignLayerGroupDto.class))
                        .add(linkTo(DesignLayerGroupAPIController.class).slash(f.getDesignLayerGroupId()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<DesignLayerGroupDto>> model = CollectionModel.of(designLayerGroupList);

        model.add(linkTo(DesignLayerGroupAPIController.class).withSelfRel());
        model.add(Link.of("/docs/index.html#resources-design-layer-group-list").withRel("profile"));

        return ResponseEntity.ok(model);
    }

    /**
     * 디자인 레이어 그룹 한건 조회
     *
     * @param id 디자인 레이어 그룹 아이디
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DesignLayerGroupDto>> getDesignLayerGroupById(@PathVariable("id") Integer id) {
        DesignLayerGroupDto dto = modelMapper.map(designLayerGroupService.getDesignLayerGroup(id), DesignLayerGroupDto.class);
        EntityModel<DesignLayerGroupDto> designLayerGroup = EntityModel.of(dto);
        designLayerGroup.add(linkTo(DesignLayerGroupAPIController.class).slash(id).withSelfRel());
        designLayerGroup.add(Link.of("/docs/index.html#resources-design-layer-group-get").withRel("profile"));

        return ResponseEntity.ok(designLayerGroup);
    }
}
