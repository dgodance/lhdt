package lhdt.svc.cityplanning.controller.rest;

import lhdt.svc.cityplanning.domain.CPDistricInfo;
import lhdt.svc.cityplanning.domain.CPFileInfo;
import lhdt.svc.cityplanning.domain.CPLocalInfo;
import lhdt.svc.cityplanning.service.CPFileInfoService;
import lhdt.svc.cityplanning.service.CPLocalInfoService;
import lhdt.svc.common.SvcController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cityinfo/")
public class CityInfoController extends SvcController {
    @Autowired
    private CPLocalInfoService cpLocalInfoService;

    /**
     * 기본 City정보를 생성합니다
     * @return
     */
    @GetMapping("/init")
    public List<CPLocalInfo> initCityInfo() {
        String[] local = {"남양주", "하남", "인천", "고양", "부천", "과천"};
        String[] districs = {"왕숙", "교산", "계양", "창릉", "대장", "과천"};
        ArrayList<CPLocalInfo> cpLocalInfos = new ArrayList<>();
        for (var i = 0; i < local.length; i++) {
            var p = new CPLocalInfo(local[i]);
            var d = new CPDistricInfo(districs[i]);
            p.addCityInfo(d);
            cpLocalInfos.add(p);
        }

        var result = this.cpLocalInfoService.registAllByUk(cpLocalInfos);

        if (result == null) {
            return null;
        } else {
            return result;
        }
    }

    /**
     * 모든 SybType 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select_all")
    public List<CPLocalInfo> selectCityInfo() {
        return this.cpLocalInfoService.findAll();
    }

    /**
     * Id를 기반으로 SybType 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select")
    public CPLocalInfo selectCityInfoById(Long id) {
        return this.cpLocalInfoService.findOneById(id);
    }

    /**
     * Uk에 대한 SubType이 존재하는지 확인합니다
     * @return
     */
    @GetMapping("/exists")
    public boolean existsCityInfoByCityName(String localName) {
        var sp = new CPLocalInfo();
        sp.setLocalName(localName);
        return this.cpLocalInfoService.existVoByUk(sp);
    }

    /**
     * 하나의 데이터를 입력합니다
     * @return
     */
    @GetMapping("/insert")
    public CPLocalInfo insertCityInfo(String cityName) {
        var sp = new CPLocalInfo();
        sp.setLocalName(cityName);
        var p = this.cpLocalInfoService.registByUk(sp);
        if (p == null) {
            return null;
        } else {
            return p;
        }
    }

    /**
     * 특정 데이터를 업데이트합니다
     * @return
     */
    @GetMapping("/update")
    public List<CPLocalInfo> updateExample(Long cityId, String localName) {
        ArrayList<CPLocalInfo> result = new ArrayList<>();
        var p = this.cpLocalInfoService.findAllById(cityId);
        p.forEach(obj -> {
            obj.setLocalName(localName);
            result.add(this.cpLocalInfoService.update(obj));
        });
        return result;
    }

    /**
     * 데이터를 삭제합니다
     * @return
     */
    @GetMapping("/delete")
    public String deleteExample(Long id) {
        this.cpLocalInfoService.deleteAllById(id);
        return "1";
    }
}
