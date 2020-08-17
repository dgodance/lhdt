package lhdt.admin.svc.lhdt.service;

import lhdt.admin.svc.lhdt.domain.QueueMessage;

public interface AMQPPublishService {

	/**
	 * message 전송
	 * @param queueMessage
	 */
	public void send(QueueMessage queueMessage);
}
