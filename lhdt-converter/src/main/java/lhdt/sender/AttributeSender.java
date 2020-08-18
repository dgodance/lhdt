package lhdt.sender;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lhdt.config.PropertiesConfig;
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AttributeSender implements ResultSender {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void send(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(filePath);

        List<Map<String, Object>> attributes = objectMapper.readValue(path.toFile(), new TypeReference<List<Map<String, Object>>>() {});
        for (Map<String, Object> map : attributes) {
            Iterator<String> keys = map.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                log.info(String.format("key : %s, value : %s", key, map.get(key)));
            }
        }
        String attributesString = objectMapper.writeValueAsString(attributes);
        URI uri = new URI(propertiesConfig.getCmsAdminRestServer() + "/api/agent/attributes");
        String responseEntity =
                restTemplate.postForObject(uri, attributesString, String.class);
        log.info(responseEntity);
    }

}
