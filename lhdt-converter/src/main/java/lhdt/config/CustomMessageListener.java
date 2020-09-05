package lhdt.config;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import lhdt.domain.*;
import lhdt.conversion.PostProcess;
import lhdt.support.LogMessageSupport;
import lhdt.support.ProcessBuilderSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@EnableAsync
@PropertySource("classpath:lhdt.properties")
@Component
public class CustomMessageListener {

	@Autowired
	private ObjectMapper objectMapper;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Async
    @RabbitListener(queues = {"${lhdt.queue-name}"})
    public void receiveMessage(final QueueMessage queueMessage) {
		log.info("--------------- queueMessage = {}", queueMessage);

		CompletableFuture.supplyAsync( () -> {
			List<String> command = new ArrayList<>();
			command.add(propertiesConfig.getConverterDir());
			command.add("#inputFolder");
			command.add(queueMessage.getInputFolder());
			command.add("#outputFolder");
			command.add(queueMessage.getOutputFolder());
			command.add("#meshType");
			command.add(queueMessage.getMeshType());
			if (!StringUtils.isEmpty(queueMessage.getSkinLevel())) {
				command.add("#skinLevel");
				command.add(queueMessage.getSkinLevel());
			}
			command.add("#log");
			command.add(queueMessage.getLogPath());
			command.add("#indexing");
			command.add(queueMessage.getIndexing());
			command.add("#usf");
			command.add(queueMessage.getUsf().toString());
			command.add("#isYAxisUp");
			command.add(queueMessage.getIsYAxisUp());

			log.info(" >>>>>> command = {}", command.toString());

			String result;
			try {
				int exitCode = ProcessBuilderSupport.execute(command);
				if(exitCode == 0) result = ConverterJobStatus.SUCCESS.name().toLowerCase();
				else result = ConverterJobStatus.FAIL.name().toLowerCase();
			} catch(InterruptedException e1) {
				result = ConverterJobStatus.FAIL.name().toLowerCase();
				LogMessageSupport.printMessage(e1);
			} catch(IOException e1) {
				result = ConverterJobStatus.FAIL.name().toLowerCase();
				LogMessageSupport.printMessage(e1);
			} catch (Exception e1) {
				result = ConverterJobStatus.FAIL.name().toLowerCase();
				LogMessageSupport.printMessage(e1);
			}
			return result;
        })
		.exceptionally(e -> {
			handlerException(queueMessage, e.getMessage());
			log.info("exceptionally exception = {}", e.getMessage());
        	return null;

        })
		// 앞의 비동기 작업의 결과를 받아 사용하며 return이 없다.
		.thenAccept(status -> {
			log.info("thenAccept status = {}", status);
			try {
				if(ConverterType.DATA == queueMessage.getConverterType()) {
					// 데이터 변환
					ConverterJob converterJob = new ConverterJob();
					converterJob.setUserId(queueMessage.getUserId());
					converterJob.setConverterJobId(queueMessage.getConverterJobId());
					converterJob.setStatus(status);
					converterJob.setErrorCode(null);
					// 로그파일 전송
					PostProcess.execute(converterJob, objectMapper, propertiesConfig, restTemplate, queueMessage);
				} else {
					DataLibraryConverterJob dataLibraryConverterJob = new DataLibraryConverterJob();
					dataLibraryConverterJob.setUserId(queueMessage.getUserId());
					dataLibraryConverterJob.setDataLibraryConverterJobId(queueMessage.getDataLibraryConverterJobId());
					dataLibraryConverterJob.setStatus(status);
					dataLibraryConverterJob.setErrorCode(null);
					PostProcess.execute(dataLibraryConverterJob, objectMapper, propertiesConfig, restTemplate, queueMessage);
				}
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();

				// 로그파일 전송 오류 시 변환 실패 전송
				handlerException(queueMessage, e.getMessage());
				LogMessageSupport.printMessage(e);
			}

			log.info("thenAccept end");
		});
		log.info("receiveMessage end");
	}

	/**
	 * 예외 처리
	 * @param queueMessage
	 * @param message
	 */
	private void handlerException(QueueMessage queueMessage, String message) {
		if(ConverterType.DATA == queueMessage.getConverterType()) {
			// 데이터 변환
			ConverterJob converterJob = new ConverterJob();
			converterJob.setServerTarget(queueMessage.getServerTarget());
			converterJob.setUserId(queueMessage.getUserId());
			converterJob.setConverterJobId(queueMessage.getConverterJobId());
			converterJob.setStatus(ConverterJobStatus.FAIL.name().toLowerCase());
			converterJob.setErrorCode(message);

			PostProcess.executeException(converterJob, propertiesConfig, restTemplate);
		} else {
			// 데이터 라이브러리 변환
			DataLibraryConverterJob dataLibraryConverterJob = new DataLibraryConverterJob();
			dataLibraryConverterJob.setServerTarget(queueMessage.getServerTarget());
			dataLibraryConverterJob.setUserId(queueMessage.getUserId());
			dataLibraryConverterJob.setDataLibraryConverterJobId(queueMessage.getDataLibraryConverterJobId());
			dataLibraryConverterJob.setStatus(ConverterJobStatus.FAIL.name().toLowerCase());
			dataLibraryConverterJob.setErrorCode(message);

			PostProcess.executeException(dataLibraryConverterJob, propertiesConfig, restTemplate);
		}
	}
}
