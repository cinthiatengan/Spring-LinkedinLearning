package com.frankmoley.boot.landon;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RoomCleanerConsumerApplication {

	@Value("${amqp.queue.name}")
	private String queueName;

	@Value("${amqp.exchange.name}")
	private String exchangeName;

	@Bean
	public Queue queue(){
		return new Queue(queueName, false);
	}

	@Bean
	public TopicExchange topicExchange(){
		return new TopicExchange(exchangeName);
	}

	@Bean
	public Binding binding(Queue queue, TopicExchange topicExchange){
		return BindingBuilder.bind(queue).to(topicExchange).with(queueName);
	}

	@Bean
	public MessageListenerAdapter listenerAdapter(RoomCleanerProcessor processor){
		return new MessageListenerAdapter(processor, "receiveMessage");
	}

	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	public static void main(String[] args) {
		SpringApplication.run(RoomCleanerConsumerApplication.class, args);
	}
}
