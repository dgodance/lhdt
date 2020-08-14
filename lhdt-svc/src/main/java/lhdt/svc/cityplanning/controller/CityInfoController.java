package lhdt.svc.cityplanning.controller;

import lhdt.svc.cityplanning.domain.CityInfo;
import lhdt.svc.cityplanning.service.CityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cityinfo/")
public class CityInfoController {
    @Autowired
    private CityInfoService cis;

    /**
     * 기본 City정보를 생성합니다
     * @return
     */
    @GetMapping("/init")
    public List<CityInfo> initCityInfo() {
        ArrayList<CityInfo> insertCityInfo = new ArrayList<>();
        insertCityInfo.add(CityInfo.builder().cityName("창릉").build());
        insertCityInfo.add(CityInfo.builder().cityName("대장").build());
        insertCityInfo.add(CityInfo.builder().cityName("왕숙").build());
        insertCityInfo.add(CityInfo.builder().cityName("과천").build());
        insertCityInfo.add(CityInfo.builder().cityName("장산").build());
        insertCityInfo.add(CityInfo.builder().cityName("교산").build());
        ArrayList<CityInfo> result = new ArrayList<>();
        insertCityInfo.forEach(p -> {
            result.add(this.cis.registByUk(p));
        });
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
    public List<CityInfo> selectCityInfo() {
        return this.cis.findAll();
    }

    /**
     * Id를 기반으로 SybType 정보를 가지고 옵니다
     * @return
     */
    @GetMapping("/select")
    public CityInfo selectCityInfoById(Long id) {
        return this.cis.findOneById(id);
    }

    /**
     * Uk에 대한 SubType이 존재하는지 확인합니다
     * @return
     */
    @GetMapping("/exists")
    public boolean existsCityInfoByCityName(String cityName) {
        var sp = new CityInfo();
        sp.setCityName(cityName);
        return this.cis.existVoByUk(sp);
    }

    /**
     * 하나의 데이터를 입력합니다
     * @return
     */
    @GetMapping("/insert")
    public CityInfo insertCityInfo(String cityName) {
        var sp = new CityInfo();
        sp.setCityName(cityName);
        var p = this.cis.registByUk(sp);
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
    public List<CityInfo> updateExample(Long cityId, String cityName) {
        ArrayList<CityInfo> result = new ArrayList<CityInfo>();
        var p = this.cis.findAllById(cityId);
        p.forEach(obj -> {
            obj.setCityName(cityName);
            result.add(this.cis.update(obj));
        });
        if(result == null) {
            return null;
        } else {
            return result;
        }
    }

    /**
     * 데이터를 삭제합니다
     * @return
     */
    @GetMapping("/delete")
    public String deleteExample(Long id) {
        this.cis.deleteAllById(id);
        return "1";
    }
}
