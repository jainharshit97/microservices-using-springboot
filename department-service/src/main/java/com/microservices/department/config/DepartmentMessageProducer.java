package com.microservices.department.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentMessageProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private static final String EXCHANGE = "service_exchange";
	private static final String ROUTING_KEY = "user_routing_key";

	public void sendDepartmentCreatedMessage(String message) {
		rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
		System.out.println("Message sent to RabbitMQ: " + message);
	}
}
