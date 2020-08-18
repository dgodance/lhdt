package lhdt.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import lhdt.config.PropertiesConfig;
import lhdt.domain.ConverterResultLog;
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

@Slf4j
@Component
public class ResultLogSender implements ResultSender {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void send(String filePath) throws IOException, URISyntaxException {
        Path logFilePath = Paths.get(filePath);
        File logFile = logFilePath.toFile();
        if (!logFile.exists()) {
            throw new IOException("지정된 파일을 찾을 수 없습니다. : " + filePath);
        }
        ConverterResultLog resultLog = objectMapper.readValue(logFilePath.toFile(), ConverterResultLog.class);
        URI uri = new URI(propertiesConfig.getCmsAdminRestServer() + "/api/agent/log");
        ResponseEntity<ConverterResultLog> responseEntity =
                restTemplate.postForEntity(uri, resultLog, ConverterResultLog.class);
        log.info(responseEntity.toString());
    }

}