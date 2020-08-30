package lhdt.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import lhdt.config.PropertiesConfig;
import lhdt.domain.*;
import lhdt.support.LogMessageSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Slf4j
public class ResultSender {

    /**
     * 로그파일 파싱 및 전송
     * @param converterJob  converterJob
     * @param objectMapper  objectMapper
     * @param propertiesConfig  propertiesConfig
     * @param restTemplate  restTemplate
     * @param queueMessage  queueMessage
     * @throws IOException  IOException
     * @throws URISyntaxException   URISyntaxException
     */
    public static void sendLog(ConverterJob converterJob, ObjectMapper objectMapper, PropertiesConfig propertiesConfig,
                               RestTemplate restTemplate, QueueMessage queueMessage) throws IOException, URISyntaxException {

        // 1. 로그 파일 파싱
        ConverterResultLog resultLog = parseLogFile(objectMapper, queueMessage);
        resultLog.setConverterJob(converterJob);

        // 2. 위치, 속성 파일 파싱
        UploadDataType uploadDataType = queueMessage.getUploadDataType();
        if (UploadDataType.CITYGML == uploadDataType || UploadDataType.INDOORGML == uploadDataType) {
            parseLocationAttribute(objectMapper, queueMessage, resultLog);
        }

        // 3. API 호출
        String url = "/api/converters/" + converterJob.getConverterJobId() + "/logs";
        URI uri = getSendURI(propertiesConfig, queueMessage.getServerTarget(), url);
        ResponseEntity<ConverterResultLog> responseEntity =
                restTemplate.postForEntity(uri, resultLog, ConverterResultLog.class);

        // 4. 응답 확인
        log.info(">>>>>>>>>> status : {}", responseEntity.getStatusCode());
        log.info(">>>>>>>>>> errorCode : {}", Objects.requireNonNull(responseEntity.getBody()).getFailureLog());

    }

    /**
     * Converter 실행 후 변환결과 전송
     * @param converterJob  converterJob
     * @param propertiesConfig  propertiesConfig
     * @param restTemplate  restTemplate
     * @param serverTarget    serverTarget
     */
    public static void sendConverterJobStatus(ConverterJob converterJob, PropertiesConfig propertiesConfig,
                                              RestTemplate restTemplate, ServerTarget serverTarget) {
        URI uri;
        try {
            String url = "/api/converters/" + converterJob.getConverterJobId() + "/status";
            uri = getSendURI(propertiesConfig, serverTarget, url);
            ResponseEntity<ConverterJob> responseEntity = restTemplate.postForEntity(uri, converterJob, ConverterJob.class);
            log.info(">>>>>>>>>> status : {}", responseEntity.getStatusCode());
            log.info(">>>>>>>>>> errorCode : {}", Objects.requireNonNull(responseEntity.getBody()).getErrorCode());
        } catch (URISyntaxException e) {
            LogMessageSupport.printMessage(e);
        }
    }

    /**
     * 전송할 파일의 유효성 검사
     * @param filePath  filePath
     * @return 파일 유효성 검사
     */
    private static boolean invalidFilePath(String filePath) {
        boolean result = false;
        Path sendFilePath = Paths.get(filePath);
        File sendFile = sendFilePath.toFile();
        if (!sendFile.exists()) {
            result = true;
        }
        return result;
    }

    /**
     * 로그파일 파싱
     * @param objectMapper  objectMapper
     * @param queueMessage  queueMessage
     * @return  로그파일 파싱 결과
     * @throws IOException  IOException
     */
    private static ConverterResultLog parseLogFile(ObjectMapper objectMapper, QueueMessage queueMessage)
            throws IOException {
        String logFilePath = queueMessage.getLogPath();
        if (invalidFilePath(logFilePath))
            throw new FileNotFoundException("The file in the specified path cannot be found. filePath : [" + logFilePath + "]");
        Path sendFilePath = Paths.get(logFilePath);
        File logFile = sendFilePath.toFile();
        ConverterResultLog resultLog = objectMapper.readValue(logFile, ConverterResultLog.class);
        return resultLog;
    }

    /**
     * 위치, 속성 파일 파싱
     * @param objectMapper  objectMapper
     * @param queueMessage  queueMessage
     * @param resultLog resultLog
     * @throws IOException  IOException
     */
    private static void parseLocationAttribute(ObjectMapper objectMapper, QueueMessage queueMessage,
                                               ConverterResultLog resultLog) throws IOException {

        for (ConversionJobResult result : resultLog.getConversionJobResult()) {
            if (!ConverterJobResultStatus.SUCCESS.equals(result.getResultStatus())) {
                continue;
            }

            String fileName = result.getFileName();
            String dataKey = fileName.substring(0, fileName.lastIndexOf("."));
            String outputFilePath = queueMessage.getOutputFolder() + File.separator + QueueMessage.F4D_PREFIX + dataKey;
            String locationFilePath = outputFilePath + File.separator + "lonsLats.json";
            String attributeFilePath = outputFilePath + File.separator + "attributes.json";

            if (invalidFilePath(locationFilePath) || invalidFilePath(attributeFilePath))
                throw new FileNotFoundException("The file in the specified path cannot be found.");

            File locationFile = Paths.get(locationFilePath).toFile();
            ConverterLocation location = objectMapper.readValue(locationFile, ConverterLocation.class);
            log.info("longitude = {}, latitude = {}", location.getLongitude(), location.getLatitude());
            result.setLocation(location);

            // File attributeFile = attributeFilePath.toFile();
            // List<Map<String, Object>> attributes = objectMapper.readValue(attributeFile, new TypeReference<>() {});

            // json 파일을 Object로 변환하여 전송할 예정이였으나, json string으로 DB에 넣기 때문에 string 변경함.
            byte[] jsonData = Files.readAllBytes(Paths.get(attributeFilePath));
            String attributes = new String(jsonData, StandardCharsets.UTF_8);
            log.info(">>>>>>>>>> attributesJson : {}", attributes);
            result.setAttributes(attributes);

        }
    }

    /**
     * 변환을 수행한 서버(ADMIN, USER)로 전송하기 위해 호출할 URI를 생성
     * @param propertiesConfig  propertiesConfig
     * @param serverTarget    serverTarget
     * @param url   url
     * @return 호출할 URI
     * @throws URISyntaxException   URISyntaxException
     */
    private static URI getSendURI(PropertiesConfig propertiesConfig, ServerTarget serverTarget, String url)
            throws URISyntaxException {
        URI uri;
        if (ServerTarget.USER == serverTarget) {
            uri = new URI(propertiesConfig.getCmsUserRestServer() + url);
        } else {
            uri = new URI(propertiesConfig.getCmsAdminRestServer() + url);
        }
        return uri;
    }

}