package lhdt.admin.svc.lhdt.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lhdt.admin.svc.lhdt.config.PropertiesConfig;
import lhdt.admin.svc.lhdt.domain.QueueMessage;
import lhdt.admin.svc.lhdt.service.AMQPPublishService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Cheon JeongDae
 *
 */
@Slf4j
@Service
public class AMQPPublishServiceImpl implements AMQPPublishService {

	@Autowired
	private PropertiesConfig propertiesConfig;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Transactional
	public void send(QueueMessage queueMessage) {
		log.info("@@ Publish send message >>> {}", queueMessage);
//		rabbitTemplate.convertAndSend(propertiesConfig.getQueueName(), queueMessage);
		rabbitTemplate.convertAndSend(propertiesConfig.getExchange(), propertiesConfig.getQueueName(), queueMessage);
	}
}
