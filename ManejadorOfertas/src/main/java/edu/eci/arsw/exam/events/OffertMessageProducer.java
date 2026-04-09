package edu.eci.arsw.exam.events;

import java.io.Serializable;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.AmqpException;



public class OffertMessageProducer {

    private static final String PRODUCT_ROUTING_KEY = "my.routingkey.1";
    private static final String WINNER_ROUTING_KEY = "auction.winner";

    protected AmqpTemplate amqpTemplate;

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendMessages(Object message) throws AmqpException {
        amqpTemplate.convertAndSend(PRODUCT_ROUTING_KEY, message);
    }

    public void sendWinnerNotification(Object message) throws AmqpException {
        amqpTemplate.convertAndSend(WINNER_ROUTING_KEY, message);
    }
}
