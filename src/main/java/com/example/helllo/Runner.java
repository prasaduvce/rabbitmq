package com.example.helllo;

import com.example.RabbitmqApplication;
import java.util.concurrent.TimeUnit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

	private RabbitTemplate rabbitTemplate;

	private Receiver receiver;

	private ConfigurableApplicationContext context;

	public Runner(RabbitTemplate rabbitTemplate, Receiver receiver, ConfigurableApplicationContext context) {
		this.rabbitTemplate = rabbitTemplate;
		this.receiver = receiver;
		this.context = context;
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("Sending Message ... ");
		rabbitTemplate.convertAndSend(RabbitmqApplication.queueName, "Hello from Rabbit MQueue");

		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		context.close();
	}
}
