package lhdt.admin.svc.landscape.controller.rest;

import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroupDTO.LSDiffGroupMeta;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroupDTO.LSDiffGroupTable;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroupDTO.LSDiffGroupTableDefault;
import lhdt.admin.svc.landscape.service.LandScapeDiffGroupService;
import lhdt.cmmn.misc.CmmnPageSize;
import lhdt.cmmn.misc.CmmnPaginator;
import lhdt.cmmn.misc.CmmnPaginatorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 경관비교 기능 Rest API 클래스 
 */
@Slf4j
@RestController
@RequestMapping("/ls-diff-group-rest")
public class LandScapeDiffGroupRestController {
    @Autowired
    private LandScapeDiffGroupService landScapeDiffGroupService;

    /**
     * 경관 비교 그룹 페이지 정보를 제공합니다
     * @param landscape_page
     * @param model
     * @return
     */
    @GetMapping()
    public LSDiffGroupTable getLsDiffGroupPages(
            @RequestParam(value = "lsGroupPage", defaultValue = "1") Integer landscape_page,
            Model model) {
        Page<LandScapeDiffGroup> cpLocalInfoPage = landScapeDiffGroupService
                .findAllPgByStartPg(landscape_page -1, CmmnPageSize.NOTICE.getContent());
        model.addAttribute("lsGroupPage", cpLocalInfoPage);

        CmmnPaginatorInfo cpLocalPageNav = CmmnPaginator.getPaginatorMap(cpLocalInfoPage, CmmnPageSize.NOTICE);
        model.addAttribute("lsGroupPageInfo", cpLocalPageNav);

        LSDiffGroupTable p = new LSDiffGroupTable();
        var meta = new LSDiffGroupMeta(1,1,-1,100,"asc","id");
        p.setMeta(meta);
        landScapeDiffGroupService.findAll().forEach(obj -> {
            var lsdiffGroupTableDefault =  LSDiffGroupTableDefault.builder()
                    .Id(obj.getId()).lsDiffGrupName(obj.getLsDiffGrupName())
                    .registDt(obj.getRegistDt()).updtDt(obj.getUpdtDt())
                    .build();
            p.getData().add(lsdiffGroupTableDefault);
        });

        return p;
    }

    /**
     * 경관 비교 그룹 테이블 정보를 제공합니다
     * @return
     */
    @PostMapping()
    public LSDiffGroupTable getLsGroup() {
        LSDiffGroupTable p = new LSDiffGroupTable();
        landScapeDiffGroupService.findAll().forEach(obj -> {
            var lsdiffGroupTableDefault =  LSDiffGroupTableDefault.builder()
                    .Id(obj.getId()).lsDiffGrupName(obj.getLsDiffGrupName())
                    .registDt(obj.getRegistDt()).updtDt(obj.getUpdtDt())
                    .build();
            p.getData().add(lsdiffGroupTableDefault);
        });
        return p;
    }
}
