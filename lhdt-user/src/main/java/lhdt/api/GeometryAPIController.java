package lhdt.api;

import lhdt.domain.data.DataInfoDto;
import lhdt.domain.extrusionmodel.DesignLayerDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/geometry", produces = MediaTypes.HAL_JSON_VALUE)
public class GeometryAPIController {

    @GetMapping("/intersection/design-layers")
    public ResponseEntity<CollectionModel<EntityModel<DesignLayerDto>>> getIntersectionDesignLayers(@RequestParam(defaultValue = "land") String type, @RequestParam Float buffer) {

        return null;
    }

    @GetMapping("/intersection/datas")
    public ResponseEntity<CollectionModel<EntityModel<DataInfoDto>>> getIntersectionDatas(@RequestParam Float buffer) {

        return null;
    }
}
