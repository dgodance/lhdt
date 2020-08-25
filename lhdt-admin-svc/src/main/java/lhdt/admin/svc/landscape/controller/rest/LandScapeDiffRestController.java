package lhdt.admin.svc.landscape.controller.rest;

import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.admin.svc.landscape.model.LandScapeDiffParam;
import lhdt.admin.svc.landscape.service.LandScapeDiffGroupService;
import lhdt.admin.svc.landscape.service.LandScapeDiffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ls-diff")
public class LandScapeDiffRestController {
    @Autowired
    private LandScapeDiffGroupService landScapeDiffGroupService;
    @Autowired
    private LandScapeDiffService landScapeDiffService;

    @GetMapping("/group")
    private List<LandScapeDiffGroup> getLSDiffGroup() {
        return this.landScapeDiffGroupService.findAll();
    };

    @GetMapping("/{id}")
    private List<LandScapeDiff> getLSDiff(@PathVariable(value = "id") Long id) {
        var landScapeDiffGroup = this.landScapeDiffGroupService.findById(id);
        return this.landScapeDiffService.findAllByLandScapeDiffGroup(landScapeDiffGroup);
    };

    @PostMapping()
    private LandScapeDiff addLSDiff(LandScapeDiffParam landScapeDiff) throws IOException {
        var id = landScapeDiff.getLandScapeDiffGroupId();
        var name = landScapeDiff.getLandscapeName();
        var image = landScapeDiff.getImage();
//        landScapeDiff
        var group = this.landScapeDiffGroupService.findById(id);
        var obj = new LandScapeDiff();
        obj.setLandScapeDiffGroup(group);
        obj.setLsDiffName(name);
        System.out.println(obj.toString());
        var result = this.landScapeDiffService.regist(obj);
        return result;
    }

}
