package lhdt.svc.cityplanning.controller.rest;

import lhdt.svc.cityplanning.domain.CPAreaColor;
import lhdt.svc.cityplanning.service.CPAreaColorService;
import lhdt.svc.common.SvcController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
public class CPAreaColorController extends SvcController {

    @Autowired
    CPAreaColorService CPAreaColorService;

    @GetMapping("/init")
    public String initCityPlanAreaColorData() throws IOException {
        List<CPAreaColor> result = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("data/cityplanareacolor.txt");
        try {
            Path path = Paths.get(resource.getURI());
            List<String> content = Files.readAllLines(path);
            content.forEach(System.out::println);
            for (String s : content) {
                var contents = s.split(",");
                var p = CPAreaColor.builder().areaName(contents[0]).color(contents[1]).build();
                this.CPAreaColorService.registByUk(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "1";
    }

}
