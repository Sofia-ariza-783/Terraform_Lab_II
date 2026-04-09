package edu.eci.arsw.exam.events;

import edu.eci.arsw.exam.IdentityGenerator;
import edu.eci.arsw.exam.models.Offert;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class WinningOfferListener implements MessageListener {

    @Override
    public void onMessage(Message message) throws AmqpException {
        try {
            Offert winningOffert = new Offert(message.getBody());
            if (IdentityGenerator.actualIdentity.equals(winningOffert.getBuyer())) {
                System.out.println("El comprador " + winningOffert.getBuyer()
                        + " compro el producto " + winningOffert.getProductCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("An exception occured while trying to process winner AMQP object:" + e.getMessage(), e);
        }
    }
}

