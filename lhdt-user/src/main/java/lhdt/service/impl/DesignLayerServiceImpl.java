package lhdt.service.impl;

import lhdt.config.PropertiesConfig;
import lhdt.domain.extrusionmodel.DesignLayer;
import lhdt.domain.policy.GeoPolicy;
import lhdt.persistence.DesignLayerMapper;
import lhdt.service.DesignLayerService;
import lhdt.service.GeoPolicyService;
import lhdt.support.LogMessageSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 여기서는 Geoserver Rest API 결과를 가지고 파싱 하기 때문에 RestTemplate을 커스트마이징하면 안됨
 * TODO DesignLayerFileInfoMapper 는 서비스를 호출해서 해야 하는데... 귀찮아서
 * @author Cheon JeongDae
 *
 */
@Slf4j
@Service
public class DesignLayerServiceImpl implements DesignLayerService {

	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;

    @Autowired
    private GeoPolicyService geoPolicyService;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private DesignLayerMapper designLayerMapper;

    /**
	 * Design Layer 총 건수
	 * @param designLayer
	 * @return
	 */
    @Transactional(readOnly=true)
	public Long getDesignLayerTotalCount(DesignLayer designLayer) {
    	return designLayerMapper.getDesignLayerTotalCount(designLayer);
    }
    
    /**
    * design layer 목록
    * @return
    */
    @Transactional(readOnly=true)
    public List<DesignLayer> getListDesignLayer(DesignLayer designLayer) {
        return designLayerMapper.getListDesignLayer(designLayer);
    }
    
    /**
     * geoserver design layer 목록 조회
     */
    @Transactional(readOnly=true)
    public String getListGeoserverDesignLayer(GeoPolicy geoPolicy) {
    	String geoserverDesignLayerJson = null;
    	try {
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
			// 클라이언트가 서버에 어떤 형식(MediaType)으로 달라는 요청을 할 수 있는데 이게 Accpet 헤더를 뜻함.
			List<MediaType> acceptList = new ArrayList<>();
			acceptList.add(MediaType.ALL);
			headers.setAccept(acceptList);
			// 클라이언트가 request에 실어 보내는 데이타(body)의 형식(MediaType)를 표현
			headers.setContentType(MediaType.TEXT_XML);
			// geoserver basic 암호화 아이디:비밀번호 를 base64로 encoding 
			headers.add("Authorization", "Basic " + Base64.getEncoder().
					encodeToString((geoPolicy.getGeoserverUser() + ":" + geoPolicy.getGeoserverPassword()).getBytes()));
			
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
			//Add the String Message converter
			messageConverters.add(new StringHttpMessageConverter());
			//Add the message converters to the restTemplate
			restTemplate.setMessageConverters(messageConverters);
		    
			HttpEntity<String> entity = new HttpEntity<>(headers);
			
			String url = geoPolicy.getGeoserverDataUrl() + "/rest/workspaces/" + geoPolicy.getGeoserverDataWorkspace()+ "/layers";
			ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			log.info("-------- statusCode = {}, body = {}", response.getStatusCodeValue(), response.getBody());
            geoserverDesignLayerJson = response.getBody().toString();
		
    	} catch(RestClientException e) {
            LogMessageSupport.printMessage(e, "@@@ RestClientException. message = {}", e.getMessage());
    	} catch(RuntimeException e) {
    	    LogMessageSupport.printMessage(e, "@@@ RuntimeException. message = {}", e.getMessage());
		} catch(Exception e) {
    	    LogMessageSupport.printMessage(e, "@@@ Exception. message = {}", e.getMessage());
		}
    	
    	return geoserverDesignLayerJson;
    }

    /**
    * design layer 정보 취득
    * @param designLayerId
    * @return
    */
    @Transactional(readOnly=true)
    public DesignLayer getDesignLayer(Long designLayerId) {
        return designLayerMapper.getDesignLayer(designLayerId);
    }
    
    /**
    * design 레이어 테이블의 컬럼 타입이 어떤 geometry 타입인지를 구함
    * @param designLayerKey
    * @return
    */
    @Transactional(readOnly=true)
    public String getGeometryType(String designLayerKey) {
        return designLayerMapper.getGeometryType(designLayerKey);
    }

    /**
     *
     * @param designLayerKey
     * @return
     */
    @Transactional(readOnly = true)
    public String getDesignLayerColumn(String designLayerKey) {
    	return designLayerMapper.getDesignLayerColumn(designLayerKey);
    }

}
