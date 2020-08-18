package lhdt.svc.cityplanning.controller;

import lhdt.svc.cityplanning.domain.CityPlanReportDetail;
import lhdt.svc.cityplanning.service.CityPlanReportDetailService;
import lhdt.svc.common.PageSize;
import lhdt.svc.common.Paginator;
import lhdt.svc.common.PaginatorInfo;
import lhdt.svc.hello.domain.SubType0;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("view")
@RequiredArgsConstructor
public class CityPlanAreaViewController {
    private final CityPlanReportDetailService cityPlanReportDetailService;

    @GetMapping("/board")
    public String getNoticePage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model) {
        System.out.println("sadgasdg");
        model.addAttribute("board", "adgadg");

        Page<CityPlanReportDetail> noticePage =cityPlanReportDetailService
                .findAllPgByStartPg(page -1, PageSize.NOTICE.getContent());
        model.addAttribute("boardPage", noticePage);

        PaginatorInfo pageNav = Paginator.getPagenatorMap(noticePage, PageSize.NOTICE);
        model.addAttribute("pageInfo", pageNav);

        return "board";
    }
//
//    @GetMapping("/cityplanning-view/{id}")
//    public String getNotice(@PathVariable(value = "id") Long id, Model model) {
//        SubType0 board = cityPlanReportDetailService.findOneById(id);
//        model.addAttribute("board", board);
//        return "content";
//    }
//
//    @PostMapping("/cityplanning-view")
//    public String addNotice(@Valid @ModelAttribute BoardForm boardForm) {
//        var vo = boardForm2Board(boardForm);
//        this.boardService.save(vo);
//        return "redirect:/board";
//    }
//
//    @DeleteMapping("/cityplanning-view/{id}")
//    public String deleteNotice(@PathVariable(value = "id") Long id) {
//        boardService.deleteAllById(id);
//        return "redirect:/board";
//    }
//
//    @PutMapping("/cityplanning-view")
//    public String modifyNotice(@Valid @ModelAttribute BoardForm boardForm) {
//        // ???
//        Board board = new Board();
//        board.setTitle(boardForm.getTitle());
//        boardService.update(board);
//        return "redirect:/board";
//    }
}
