package lhdt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lhdt.domain.*;
import lhdt.sender.ResultSender;
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
		Long converterJobId = queueMessage.getConverterJobId();
		log.info(" @@@@@@ handleMessage start. converterJobId = {}", converterJobId);

		//Long converterJobFileId = queueMessage.getConverterJobFileId();
		String userId = queueMessage.getUserId();

		String logPath = queueMessage.getLogPath();
		String outputPath = queueMessage.getOutputFolder();
		String locationFilePath = outputPath + "/lonsLats.json";
		String attributeFilePath = outputPath + "/attributes.json";

		ConverterJob converterJob = new ConverterJob();
		//converterJob.setConverterJobFileId(converterJobFileId);
		//log.info(" >>>>>> converterJobFileId = {}", converterJobFileId);
		converterJob.setUserId(userId);
		converterJob.setConverterJobId(converterJobId);
		ServerTarget target = queueMessage.getServerTarget();

		CompletableFuture.supplyAsync( () -> {
			List<String> command = new ArrayList<>();
			command.add(propertiesConfig.getConverterDir());
			command.add("#inputFolder");
			command.add(queueMessage.getInputFolder());
			command.add("#outputFolder");
			command.add(outputPath);
			command.add("#meshType");
			command.add(queueMessage.getMeshType());
			if (!StringUtils.isEmpty(queueMessage.getSkinLevel())) {
				command.add("#skinLevel");
				command.add(queueMessage.getSkinLevel());
			}
			command.add("#log");
			command.add(logPath);
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
				log.info(" InterruptedException exception = {}", e1.getMessage());
			} catch(IOException e1) {
				result = ConverterJobStatus.FAIL.name().toLowerCase();
				log.info(" IOException exception = {}", e1.getMessage());
			} catch (Exception e1) {
				result = ConverterJobStatus.FAIL.name().toLowerCase();
				log.info(" Exception exception = {}", e1.getMessage());
			}
			return result;
        })
		.exceptionally(e -> {
        	log.info("exceptionally exception = {}", e.getMessage());
			converterJob.setStatus(ConverterJobStatus.FAIL.name().toLowerCase());
			converterJob.setErrorCode(e.getMessage());
			ResultSender.sendConverterJobStatus(converterJob, propertiesConfig, restTemplate, target);
        	return null;

        })
		// 앞의 비동기 작업의 결과를 받아 사용하며 return이 없다.
		.thenAccept(status -> {
			log.info("thenAccept result = {}", status);
			converterJob.setStatus(status);
			converterJob.setErrorCode(null);

			try {
				// 로그파일 전송
				ResultSender.sendLog(converterJob, objectMapper, propertiesConfig, restTemplate, target, logPath);
			} catch (IOException | URISyntaxException e) {
				// 로그파일 전송 오류 시 변환 실패 전송
//				converterJob.setStatus(ConverterJobStatus.FAIL.name().toLowerCase());
//				converterJob.setErrorCode(e.getMessage());
//				ResultSender.sendConverterJobStatus(converterJob, propertiesConfig, restTemplate, target);
				LogMessageSupport.printMessage(e);
			}

			try {
				// 위치, 속성파일 전송
				UploadDataType uploadDataType = queueMessage.getUploadDataType();
				if (UploadDataType.CITYGML == uploadDataType || UploadDataType.INDOORGML == uploadDataType) {
					ResultSender.sendLocation(converterJob, objectMapper, propertiesConfig, restTemplate, target, locationFilePath);
					ResultSender.sendAttribute(converterJob, propertiesConfig, restTemplate, target, attributeFilePath);
				}
			} catch (IOException | URISyntaxException e) {
				LogMessageSupport.printMessage(e);
			}

			// 변환결과 전송
//			ResultSender.sendConverterJobStatus(converterJob, propertiesConfig, restTemplate, target);
			log.info("thenAccept end");
		});
		log.info("receiveMessage end");
	}

//	/**
//	 * 데이터 변환 job 상태 변경
//	 * @param userId
//	 * @param serverTarget
//	 * @param converterJobId
//	 * @param status
//	 * @param errorCode
//	 */
//	private void updateConverterJobStatus(String userId, String serverTarget, Long converterJobId, Long converterJobFileId, String status, String errorCode) {
//		log.info("@@ updateConverterJobStatus converterJobId = {}, status = {}, errorCode = {}", converterJobId, status, errorCode);
//
//		ConverterJob converterJob = new ConverterJob();
//		converterJob.setConverterJobFileId(converterJobFileId);
//		converterJob.setUserId(userId);
//		converterJob.setConverterJobId(converterJobId);
//		converterJob.setStatus(status);
//		converterJob.setErrorCode(errorCode);
//
//		try {
//			URI uri;
//			if(ServerTarget.USER == ServerTarget.valueOf(serverTarget)) {
//				uri = new URI(propertiesConfig.getCmsUserRestServer() + "/api/converters/status");
//			} else {
//				uri = new URI(propertiesConfig.getCmsAdminRestServer() + "/api/converters/status");
//			}
//			ResponseEntity<ConverterJob> responseEntity = restTemplate.postForEntity(uri, converterJob, ConverterJob.class);
//			log.info(responseEntity.toString());
//
//		} catch (URISyntaxException e) {
//			log.info("데이터 converter 상태 변경 api 호출 실패 = {}", e.getMessage());
//		}
//
//	}

//	public void handleMessage2(QueueMessage queueMessage) {
//		log.info("@@ Subscribe receive message");
//		log.info("@@ queueMessage = {}", queueMessage);
//
//		Runnable connverterRun = () -> {
//			List<String> command = new ArrayList<>();
//			command.add(propertiesConfig.getConverterDir());
//			command.add("-inputFolder");
//			command.add(queueMessage.getInputFolder());
//			command.add("-outputFolder");
//			command.add(queueMessage.getOutputFolder());
//			command.add("-meshType");
//			command.add(queueMessage.getMeshType());
//			command.add("-log");
//			command.add(queueMessage.getLogPath());
//			command.add("-indexing");
//			command.add(queueMessage.getIndexing());
//
//			log.info(" >>>>>> command = {}", command.toString());
//
//			ProcessBuilderSupport.execute(command);
//		};
//
//		new Thread(connverterRun, "F4D-Converter-Thread-JobId=" + queueMessage.getConverterJobId()).start();
//	}
}
