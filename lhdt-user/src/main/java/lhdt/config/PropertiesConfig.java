package lhdt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:lhdt.properties")
@ConfigurationProperties(prefix = "lhdt")
public class PropertiesConfig {
	
	/**
	 * 로컬 : local, 개발 : develop, 운영 : product
	 */
	private String profile;
	
    /**
     * 로컬 환경은 WINDOW, 운영 환경은 LINUX 
     */
    private String osType;
    
    /**
     * 로컬 환경에서 mock 사용여부
     */
    private boolean mockEnable;
    private boolean callRemoteEnable;
    private String serverIp;
    
    // http, https
    private String restTemplateMode;
    private String restAuthKey;
    private String gisRestServer;

    // layer 파일 업로딩 디렉토리
    private String layerUploadDir;
    // layer export 용 임시 디렉토리
    private String layerExportDir;
    // 디자인 레이어 파일 업로딩 디렉토리
    private String designLayerUploadDir;
    // 디자인 레이어 export 용 임시 디렉토리
    private String deisgnLayerExportDir;

    private String queueServerHost;
	private String queueServerPort;
	private String queueUser;
	private String queuePassword;
	private String queueName;
	private String exchange;
	private String routingKey;
    
    // F4D 파일이 변환되는 Root 경로 이자, mago3DJS 에서 요청되는 파일의 Root 경로. ServletConfig 에서 매핑
    private String dataServiceDir;
    // F4D 변환 결과 로그 저장 경로
    private String dataConverterLogDir;
    // 관리자용
    private String adminDataServiceDir;
    private String adminDataLibraryServiceDir;
    private String adminDataServicePath;
    private String adminDataLibraryServicePath;
    // 사용자용
    private String userDataServiceDir;
    private String userDataLibraryServiceDir;
    private String userDataServicePath;
    private String userDataLibraryServicePath;

    private String dataUploadDir;
    private String dataBulkUploadDir;
    // smart tiling 데이터 업로드 디렉토리
    private String dataSmartTilingDir;
    private String dataAttributeDir;
    private String dataAttributeUploadDir;
    private String dataObjectAttributeUploadDir;
    
    private String guideDataServiceDir;

    // extrusion model 시뮬레이션 토지 shape 파일 테이블 이름. design_layer_land
    private String designLayerLandTable;
    // extrusion model 시뮬레이션 빌딩 shape 파일 테이블 이름. design_layer_building
    private String designLayerBuildingTable;
}
