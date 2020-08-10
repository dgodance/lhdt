package lhdt.anals.hello.controller;

import lhdt.anals.hello.domain.ViewAnalsLoca;
import lhdt.anals.hello.service.HelloService;
import lhdt.anals.hello.service.ViewAnalsLocaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gis/")
public class ViewAnalsController {
    @Autowired
    private ViewAnalsLocaService service;

    @GetMapping
    public String postViewAnalsRegist() {
        ViewAnalsLoca dumi = new ViewAnalsLoca();
        dumi.setCateId(Long.valueOf(0));
        dumi.setPoint(new Point(128, 52));
        ViewAnalsLoca val = this.service.registByUk(dumi);
        return "1";
    }

    @GetMapping("getItem")
    public List<ViewAnalsLoca> getAllViewAnals() {
        return this.service.findAll();
    }
}
