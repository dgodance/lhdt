package lhdt.controller.api;

import lhdt.domain.agent.ConversionJobResult;
import lhdt.domain.agent.ConverterAttribute;
import lhdt.domain.agent.ConverterLocation;
import lhdt.domain.agent.ConverterResultLog;
import lhdt.domain.converter.ConverterJob;
import lhdt.domain.converter.ConverterJobFile;
import lhdt.domain.converter.ConverterJobStatus;
import lhdt.domain.data.DataInfo;
import lhdt.domain.uploaddata.UploadData;
import lhdt.domain.uploaddata.UploadDataFile;
import lhdt.service.ConverterService;
import lhdt.service.DataService;
import lhdt.service.UploadDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/agent")
public class AgentAPIController {

    @Autowired
    private ConverterService converterService;

    @PostMapping(value = "/logs", consumes = "application/json", produces = "application/json")
    public ConverterResultLog logs(@RequestBody ConverterResultLog converterResultLog, HttpServletResponse response) {
        int resultCnt = converterService.updateConverterJobStatus(converterResultLog);
        if (resultCnt <= 0) {
            ConverterJob converterJob = converterResultLog.getConverterJob();
            converterJob.setConverterJobStatus(ConverterJobStatus.FAIL);
            converterJob.setErrorCode("변환상태 갱신작업에 실패하였습니다.");
        }
        return converterResultLog;
    }

    @PostMapping(value = "/locations", consumes = "application/json", produces = "application/json")
    public ConverterLocation locations(@RequestBody ConverterLocation converterLocation, HttpServletResponse response) {
        int resultCnt = converterService.updateConverterLocation(converterLocation);
        if (resultCnt <= 0) {
            ConverterJob converterJob = converterLocation.getConverterJob();
            converterJob.setConverterJobStatus(ConverterJobStatus.FAIL);
            converterJob.setErrorCode("변환상태 갱신작업에 실패하였습니다.");
        }
        return converterLocation;
    }

    @PostMapping(value = "/attributes", consumes = "application/json", produces = "application/json")
    public ConverterAttribute attributes(@RequestBody ConverterAttribute converterAttribute, HttpServletResponse response) {
        int resultCnt = converterService.updateConverterAttribute(converterAttribute);
        if (resultCnt <= 0) {
            ConverterJob converterJob = converterAttribute.getConverterJob();
            converterJob.setConverterJobStatus(ConverterJobStatus.FAIL);
            converterJob.setErrorCode("변환상태 갱신작업에 실패하였습니다.");
        }
        return converterAttribute;
    }

}