package lhdt.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import lhdt.config.PropertiesConfig;
import lhdt.domain.ConverterLocation;
import lhdt.domain.ConverterResultLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class LocationSender implements ResultSender {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void send(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(filePath);
        ConverterLocation location = objectMapper.readValue(path.toFile(), ConverterLocation.class);
        log.info(location.toString());
        URI uri = new URI(propertiesConfig.getCmsAdminRestServer() + "/api/agent/location");
        ResponseEntity<ConverterLocation> responseEntity =
                restTemplate.postForEntity(uri, location, ConverterLocation.class);
        log.info(responseEntity.toString());
    }
}
