package lhdt.sender;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lhdt.config.PropertiesConfig;
import lhdt.domain.*;
import lhdt.support.LogMessageSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class ResultSender {

    /**
     * 전송할 파일의 존재 여부 검사
     * @param filePath
     * @return 전송할 파일
     * @throws IOException
     */
    private static File validationFilePath(String filePath) throws IOException {
        Path sendFilePath = Paths.get(filePath);
        File sendFile = sendFilePath.toFile();
        if (!sendFile.exists()) {
            throw new IOException("지정된 파일을 찾을 수 없습니다. : " + filePath);
        }
        return sendFile;
    }

    /**
     * 변환을 수행한 서버(ADMIN, USER)로 전송하기 위해 호출할 URI를 생성
     * @param propertiesConfig
     * @param target
     * @param url
     * @return 호출할 URI
     * @throws URISyntaxException
     */
    private static URI getSendURI(PropertiesConfig propertiesConfig, ServerTarget target, String url)
            throws URISyntaxException {
        URI uri;
        if (ServerTarget.USER == target) {
            uri = new URI(propertiesConfig.getCmsUserRestServer() + url);
        } else {
            uri = new URI(propertiesConfig.getCmsAdminRestServer() + url);
        }
        return uri;
    }

    /**
     * Converter 실행 후 로그파일 전송
     * @param objectMapper
     * @param propertiesConfig
     * @param restTemplate
     * @param target
     * @param filePath
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void sendLog(ConverterJob converterJob, ObjectMapper objectMapper, PropertiesConfig propertiesConfig,
                               RestTemplate restTemplate, ServerTarget target, String filePath)
            throws IOException, URISyntaxException {
        File logFile = validationFilePath(filePath);
        ConverterResultLog resultLog = objectMapper.readValue(logFile, ConverterResultLog.class);
        log.info("getNumberOfFilesConverted = " + resultLog.getNumberOfFilesConverted());
        log.info("getNumberOfFilesToBeConverted = " + resultLog.getNumberOfFilesToBeConverted());
        resultLog.setConverterJob(converterJob);
        URI uri = getSendURI(propertiesConfig, target, "/api/agent/log");
        ResponseEntity<ConverterResultLog> responseEntity = restTemplate.postForEntity(uri, resultLog, ConverterResultLog.class);
        log.info(responseEntity.toString());
    }

    /**
     * Converter 실행 후 위치파일 전송
     * @param objectMapper
     * @param propertiesConfig
     * @param restTemplate
     * @param target
     * @param filePath
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void sendLocation(ConverterJob converterJob, ObjectMapper objectMapper, PropertiesConfig propertiesConfig,
                                    RestTemplate restTemplate, ServerTarget target, String filePath)
            throws IOException, URISyntaxException {
        File locationFile = validationFilePath(filePath);
        ConverterLocation location = objectMapper.readValue(locationFile, ConverterLocation.class);
        location.setConverterJob(converterJob);
        log.info(location.toString());
        URI uri = getSendURI(propertiesConfig, target, "/api/agent/location");
        ResponseEntity<ConverterLocation> responseEntity = restTemplate.postForEntity(uri, location, ConverterLocation.class);
        log.info(responseEntity.toString());
    }

    /**
     * Converter 실행 후 속성파일 전송
     * @param objectMapper
     * @param propertiesConfig
     * @param restTemplate
     * @param target
     * @param filePath
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void sendAttribute(ConverterJob converterJob, ObjectMapper objectMapper, PropertiesConfig propertiesConfig,
                                     RestTemplate restTemplate, ServerTarget target, String filePath)
            throws IOException, URISyntaxException {
        File attributeFile = validationFilePath(filePath);
        List<Map<String, Object>> attributes = objectMapper.readValue(attributeFile,
                new TypeReference<List<Map<String, Object>>>() {});
        ConverterAttribute attribute = new ConverterAttribute();
        attribute.setConverterJob(converterJob);
        attribute.setAttributes(attributes);
        URI uri = getSendURI(propertiesConfig, target, "/api/agent/attributes");
        ResponseEntity<ConverterAttribute> responseEntity = restTemplate.postForEntity(uri, attribute, ConverterAttribute.class);
        log.info(responseEntity.toString());
    }

    /**
     * Converter 실행 후 변환결과 전송
     * @param converterJob
     * @param propertiesConfig
     * @param restTemplate
     * @param target
     * @throws URISyntaxException
     */
    public static void sendConverterJobStatus(ConverterJob converterJob, PropertiesConfig propertiesConfig, RestTemplate restTemplate, ServerTarget target) {
        URI uri = null;
        try {
            uri = getSendURI(propertiesConfig, target, "/api/converters/status");
            ResponseEntity<ConverterJob> responseEntity = restTemplate.postForEntity(uri, converterJob, ConverterJob.class);
            log.info(responseEntity.toString());
        } catch (URISyntaxException e) {
            LogMessageSupport.printMessage(e);
        }
    }
}