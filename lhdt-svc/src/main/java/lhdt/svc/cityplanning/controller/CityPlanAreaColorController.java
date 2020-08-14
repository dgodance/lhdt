package lhdt.svc.cityplanning.controller;

import lhdt.svc.cityplanning.domain.CityPlanAreaColor;
import lhdt.svc.cityplanning.domain.CityPlanReportDetail;
import lhdt.svc.cityplanning.service.CityPlanAreaColorService;
import lhdt.svc.common.SvcController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cityplanareacolor")
public class CityPlanAreaColorController extends SvcController {

    @Autowired
    CityPlanAreaColorService cityPlanAreaColorService;

    @GetMapping("/init")
    public String initCityPlanAreaColorData() throws IOException {
        List<CityPlanAreaColor> result = new ArrayList<>();
        // result.add(CityPlanAreaColor.builder().areaName("단독주택").areaNmSumy(null).color("#ffff81").build());
//        return this.cityPlanAreaColorService.registAllByUk();

        ClassPathResource resource = new ClassPathResource("data/cityplanareacolor.txt");
        try {
            Path path = Paths.get(resource.getURI());
            List<String> content = Files.readAllLines(path);
            content.forEach(System.out::println);
            for (String s : content) {
                var contents = s.split(",");
                var p = CityPlanAreaColor.builder().areaName(contents[0]).color(contents[1]).build();
                this.cityPlanAreaColorService.registByUk(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "1";
    }

}
