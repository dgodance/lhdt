package lhdt.admin.svc.landscape.controller.rest;

import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.admin.svc.landscape.service.LandScapeDiffGroupService;
import lhdt.admin.svc.landscape.service.LandScapeDiffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
      return  this.landScapeDiffGroupService.findAll();
    };

    @PostMapping("")
    private List<LandScapeDiffGroup> addLSDiff(LandScapeDiff landScapeDiff) {
//        this.landScapeDiffService.regist(landScapeDiff)
        return null;
    }

}
