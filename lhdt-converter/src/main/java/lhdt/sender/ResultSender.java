package lhdt.sender;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public interface ResultSender {
    void send(String filePath) throws IOException, URISyntaxException;
}
