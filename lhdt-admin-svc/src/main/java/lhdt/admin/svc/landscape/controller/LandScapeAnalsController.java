package lhdt.admin.svc.landscape.controller;

import lhdt.admin.svc.cityplanning.service.CPDistricInfoService;
import lhdt.admin.svc.landscape.service.LandScapeAnalsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/landscape-anals")
public class LandScapeAnalsController {
    @Autowired
    private LandScapeAnalsService cpLocalInfoService;
    @Autowired
    private CPDistricInfoService cpDistricInfoService;

}
