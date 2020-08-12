package lhdt.anals.hello.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
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

    /**
     * Test를 위한 SHP2DB 생성 Command line
     *
     * ogr2ogr -s_srs EPSG:4326 -t_srs EPSG:4326 --config SHAPE_ENCODING CP949 -f PostgreSQL "PG:host=localhost port=15432 dbname=lhdt user=postgres password=postgres" filePath -nlt PROMOTE_TO_MULTI -nln ggg
     * @return
     */
    @GetMapping("get_cityplan_data_by_point")
    public String getCityPlanDataByPoint() {
        String result = "";
        try(
                FileReader rw = new FileReader("D:\\data\\dumi_sample\\cityplandata.json");
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
}
