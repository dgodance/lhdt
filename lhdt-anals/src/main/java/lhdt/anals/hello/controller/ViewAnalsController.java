package lhdt.anals.hello.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lhdt.anals.common.AnalsController;
import lhdt.anals.hello.domain.ViewAnalsLoca;
import lhdt.anals.hello.service.ViewAnalsLocaService;

@RestController
@RequestMapping("/gis/")
public class ViewAnalsController extends AnalsController {
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
    public ResponseEntity<Map<String,Object>> getAllViewAnals() {
        return super.res( this.service.findAll());
    }
}
