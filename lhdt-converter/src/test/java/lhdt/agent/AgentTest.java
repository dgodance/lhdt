package lhdt.agent;

import lhdt.LhdtConverterApplication;
import lhdt.sender.ResultSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LhdtConverterApplication.class)
@SpringBootTest
public class AgentTest {

    @Autowired
    private ResultSender resultLogSender;

    @Autowired
    private ResultSender attributeSender;

    @Autowired
    private ResultSender locationSender;

    @Test
    public void testSendPostConverterResultLog() throws IOException, URISyntaxException {
        String logPath = "D:\\data\\test\\log.txt";
        resultLogSender.send(logPath);
    }

    @Test
    public void testSendPostConverterAttributeLog() throws IOException, URISyntaxException {
        String attributePath = "D:\\data\\test\\F4D_design_3890_5818_2017\\attributes.json";
        attributeSender.send(attributePath);
    }

    @Test
    public void testSendPostConverterLocationLog() throws IOException, URISyntaxException {
        String locationPath = "D:\\data\\test\\F4D_design_3890_5818_2017\\lonsLats.json";
        locationSender.send(locationPath);
    }

}