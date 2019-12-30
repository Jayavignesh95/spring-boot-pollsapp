package com.poll.pollsapp.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poll.pollsapp.config.RabbitConfig;
import com.poll.pollsapp.models.EmailQueuePayload;

@Service
public class  EmailQueueService{

    private final RabbitTemplate rabbitTemplate;
    
    @Autowired
    public EmailQueueService(RabbitTemplate rabbitTemplate )
    {
    	this.rabbitTemplate = rabbitTemplate;
    }
    
    @Autowired
    private ObjectMapper objectMapper;
          

    public void SendMessage(EmailQueuePayload emailQueuePayload)
    {
    	 try {
    	        String emailJson = objectMapper.writeValueAsString(emailQueuePayload);
    	        Message message = MessageBuilder
    	                            .withBody(emailJson.getBytes())
    	                            .setContentType(MessageProperties.CONTENT_TYPE_JSON)
    	                            .build();
    	        this.rabbitTemplate.convertAndSend(RabbitConfig.MAIL_QUEUE, message);
    	    } catch (JsonProcessingException e) {
    	        e.printStackTrace();
    	    }
    	
    }
}
