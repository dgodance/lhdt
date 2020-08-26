package lhdt.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import lhdt.LhdtConverterApplication;
import lhdt.config.PropertiesConfig;
import lhdt.domain.ConverterJob;
import lhdt.domain.ServerTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LhdtConverterApplication.class)
@SpringBootTest
class ResultSenderTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private RestTemplate restTemplate;

    private ConverterJob converterJob;
    
    @BeforeEach
    void setUp() {
        converterJob = new ConverterJob();
        converterJob.setConverterJobFileId(1L);
        converterJob.setUserId("admin");
        converterJob.setConverterJobId(1L);
        converterJob.setStatus("success");
        converterJob.setErrorCode(null);
    }

    @Test
    void sendLog() throws IOException, URISyntaxException {
        String logPath = "src/test/resources/log.txt";
        ServerTarget target = ServerTarget.ADMIN;
        // 생성자 대신 정적 팩터리 매서드를 고려하라. (EffectiveJava 8page)
        // static 메서드와 인스턴스 메서드. (Java의 정석 1, 188~191page)
        ResultSender.sendLog(converterJob, objectMapper, propertiesConfig, restTemplate, target, logPath);
    }

    @Test
    void sendLocation() throws IOException, URISyntaxException {
        String locationPath = "src/test/resources/lonsLats.json";
        ServerTarget target = ServerTarget.ADMIN;
        ResultSender.sendLocation(converterJob, objectMapper, propertiesConfig, restTemplate, target, locationPath);
    }

    @Test
    void sendAttribute() throws IOException, URISyntaxException {
        String attributePath = "src/test/resources/attributes.json";
        ServerTarget target = ServerTarget.USER;
        ResultSender.sendAttribute(converterJob, propertiesConfig, restTemplate, target, attributePath);
    }

    @Test
    void sendConverterJobStatus() {
        ServerTarget target = ServerTarget.ADMIN;
        ResultSender.sendConverterJobStatus(converterJob, propertiesConfig, restTemplate, target);
    }
}