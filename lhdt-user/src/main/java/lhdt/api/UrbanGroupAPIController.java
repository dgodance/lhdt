package lhdt.api;

import lhdt.domain.urban.UrbanGroupDto;
import lhdt.service.UrbanGroupService;
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
@RequestMapping(value = "/api/urban-groups", produces = MediaTypes.HAL_JSON_VALUE)
public class UrbanGroupAPIController {

    private final UrbanGroupService urbanGroupService;
    private final ModelMapper modelMapper;

    /**
     * 도시 그룹 목록 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UrbanGroupDto>>> getDesignLayerGroups() {
        List<EntityModel<UrbanGroupDto>> urbanGroupList = urbanGroupService.getListUrbanGroup()
                .stream()
                .map(f -> EntityModel.of(modelMapper.map(f, UrbanGroupDto.class))
                        .add(linkTo(DesignLayerGroupAPIController.class).slash(f.getUrbanGroupId()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<UrbanGroupDto>> model = CollectionModel.of(urbanGroupList);

        model.add(linkTo(UrbanGroupAPIController.class).withSelfRel());
        model.add(Link.of("/docs/index.html#resource-urban-group-list").withRel("profile"));

        return ResponseEntity.ok(model);
    }

    /**
     * 도시 그룹 한건 조회
     *
     * @param id 도시 그룹 아이디
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UrbanGroupDto>> getUrbanGroupById(@PathVariable("id") Integer id) {
        UrbanGroupDto dto = modelMapper.map(urbanGroupService.getUrbanGroup(id), UrbanGroupDto.class);
        EntityModel<UrbanGroupDto> urbanGroup = EntityModel.of(dto);
        urbanGroup.add(linkTo(UrbanGroupAPIController.class).slash(id).withSelfRel());
        urbanGroup.add(Link.of("/docs/index.html#resource-urban-group-get").withRel("profile"));

        return ResponseEntity.ok(urbanGroup);
    }

    /**
     * depth 에 대한 도시 그릅 목록 조회
     * @param id
     * @return
     */
    @GetMapping("/depth/{id}")
    public ResponseEntity<CollectionModel<EntityModel<UrbanGroupDto>>> getUrbanGroupByDepth(@PathVariable("id") Integer id) {
        List<EntityModel<UrbanGroupDto>> urbanGroupList = urbanGroupService.getListUrbanGroupByDepth(id)
                .stream()
                .map(f -> EntityModel.of(modelMapper.map(f, UrbanGroupDto.class))
                        .add(linkTo(DesignLayerGroupAPIController.class).slash(f.getUrbanGroupId()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<UrbanGroupDto>> model = CollectionModel.of(urbanGroupList);

        model.add(linkTo(UrbanGroupAPIController.class).withSelfRel());
        model.add(Link.of("/docs/index.html#resource-urban-group-depth").withRel("profile"));

        return ResponseEntity.ok(model);
    }

    /**
     * parent 에 대한 도시 그룹 목록 조회
     * @param id
     * @return
     */
    @GetMapping("/parent/{id}")
    public ResponseEntity<CollectionModel<EntityModel<UrbanGroupDto>>> getUrbanGroupByParent(@PathVariable("id") Integer id) {
        List<EntityModel<UrbanGroupDto>> urbanGroupList = urbanGroupService.getListUrbanGroupByParent(id)
                .stream()
                .map(f -> EntityModel.of(modelMapper.map(f, UrbanGroupDto.class))
                        .add(linkTo(DesignLayerGroupAPIController.class).slash(f.getUrbanGroupId()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<UrbanGroupDto>> model = CollectionModel.of(urbanGroupList);

        model.add(linkTo(UrbanGroupAPIController.class).withSelfRel());
        model.add(Link.of("/docs/index.html#resource-urban-group-parent").withRel("profile"));

        return ResponseEntity.ok(model);
    }
}
