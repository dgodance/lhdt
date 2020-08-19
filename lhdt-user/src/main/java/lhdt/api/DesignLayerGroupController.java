package lhdt.api;

import lhdt.domain.extrusionmodel.DesignLayerGroup;
import lhdt.service.DesignLayerGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/design-layer-groups", produces = MediaTypes.HAL_JSON_VALUE)
public class DesignLayerGroupController {

    private final DesignLayerGroupService designLayerGroupService;

    /**
     * 디자인 레이어 그룹 목록 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<CollectionModel<DesignLayerGroup>> getDesignLayersGroups() {
        List<DesignLayerGroup> designLayerGroupList = designLayerGroupService.getListDesignLayerGroup()
                .stream()
                .map(f -> f.add(linkTo(methodOn(DesignLayerGroupController.class).getDesignLayerGroupById(f.getDesignLayerGroupId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<DesignLayerGroup> model = CollectionModel.of(designLayerGroupList);
        model.add(linkTo(methodOn(DesignLayerGroupController.class).getDesignLayersGroups()).withSelfRel());
        model.add(Link.of("/docs/index.html#resource-design-layer-group-list").withRel("profile"));

        return ResponseEntity.ok(model);
    }

    /**
     * 디자인 레이어 그룹 한건 조회
     * @param id 디자인 레이어 그룹 아이디
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<RepresentationModel<DesignLayerGroup>> getDesignLayerGroupById(@PathVariable("id") Integer id) {
        DesignLayerGroup designLayerGroup = designLayerGroupService.getDesignLayerGroup(id);
        designLayerGroup.add(linkTo(methodOn(DesignLayerGroupController.class).getDesignLayersGroups()).withSelfRel());
        designLayerGroup.add(Link.of("/docs/index.html#resource-design-layer-group-list").withRel("profile"));

        return ResponseEntity.ok(designLayerGroup);
    }
}
