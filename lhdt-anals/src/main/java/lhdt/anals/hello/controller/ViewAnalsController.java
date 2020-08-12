package lhdt.anals.hello.controller;

import java.io.*;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lhdt.anals.common.AnalsController;
import lhdt.anals.hello.domain.ViewAnalsLoca;
import lhdt.anals.hello.service.ViewAnalsLocaService;
import org.springframework.web.multipart.MultipartFile;

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
            return super.res(mapper.readValue(targetStream, Object.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
