package lhdt.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Disabled;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static org.springframework.amqp.core.ExchangeTypes.HEADERS;

@Slf4j
public class ApacheCommonCSVTests {

    @Disabled
    void test() throws IOException {
        String FILE_NAME = "D:\\샘플SHP_최종_real\\샘플SHP_최종\\획지 데이터 메타.csv";
//        Reader in = new FileReader(FILE_NAME);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(FILE_NAME), Charset.forName("CP949"));
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(isr);
        for (CSVRecord record : records) {
            System.out.println("record ========================= " + record);
            System.out.println("record1 ========================= " + record.get(0));
            System.out.println("record2 ========================= " + record.get(1));
            System.out.println("record3 ========================= " + record.get(2));
        }
    }
}
