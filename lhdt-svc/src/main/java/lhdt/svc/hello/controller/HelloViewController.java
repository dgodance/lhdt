package lhdt.svc.hello.controller;

import lhdt.svc.common.PageSize;
import lhdt.svc.common.Paginator;
import lhdt.svc.common.PaginatorInfo;
import lhdt.svc.hello.domain.SubType0;
import lhdt.svc.hello.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class HelloViewController {
    private final SampleService sampleService;

    @GetMapping("/board")
    public String getNoticePage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model) {

        Page<SubType0> noticePage =sampleService.findAllPgByStartPg(page -1, PageSize.NOTICE.getContent());
        model.addAttribute("boardPage", noticePage);

        PaginatorInfo pageNav = Paginator.getPagenatorMap(noticePage, PageSize.NOTICE);
        model.addAttribute("pageInfo", pageNav);

        return "board";
    }

    @GetMapping("/board/{id}")
    public String getNotice(@PathVariable(value = "id") Long id, Model model) {
        SubType0 board = sampleService.findOneById(id);
        model.addAttribute("board", board);
        return "content";
    }

    @PostMapping("/board")
    public String addNotice(@Valid @ModelAttribute SubType0 boardForm) {
        this.sampleService.registByUk(boardForm);
        return "redirect:/board";
    }

    @DeleteMapping("/board/{id}")
    public String deleteNotice(@PathVariable(value = "id") Long id) {
        this.sampleService.deleteAllById(id);
        return "redirect:/board";
    }

    @PutMapping("/board")
    public String modifyNotice(@Valid @ModelAttribute SubType0 boardForm) {
        // ???
        this.sampleService.update(boardForm);
        return "redirect:/board";
    }

    @GetMapping("/edit")
    public String getEditForm() {
        return "edit";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable(value = "id") Long id, Model model) {
        SubType0 board = sampleService.findOneById(id);
        model.addAttribute("board", board);
        return "edit";
    }
}
