package lhdt.svc.hello.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lhdt.svc.common.SvcController;
import lhdt.svc.hello.domain.ViewAnalsLoca;
import lhdt.svc.hello.service.ViewAnalsLocaService;

@RestController
@RequestMapping("/gis/")
public class ViewAnalsController extends SvcController {
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

    @GetMapping("get_data_info_by_poly")
    public String getDataInfoByPoly() {
        String result = "";
        try(
            FileReader rw = new FileReader("D:\\data\\dumi_sample\\height_anals_sample.json");
            BufferedReader br = new BufferedReader( rw );
        ){

            //읽을 라인이 없을 경우 br은 null을 리턴한다.
            String readLine = null ;
            while( ( readLine =  br.readLine()) != null ){
//                System.out.println(readLine);
                result += readLine;
            }
        }catch (IOException e ) {
            System.out.println(e);
        }
        return result;
    }

    @RequestMapping(value = "/get_cityplan_data_by_point", method = RequestMethod.GET)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getCityPlanDataByPoint() {
        File fi = new File("C:\\data\\mago3d\\building_obj\\6-4_disSchool.geojson");
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream targetStream = new FileInputStream(fi);
            var result = mapper.readValue(targetStream, Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
