package edu.eci.arsw.exam.events;

import edu.eci.arsw.exam.IdentityGenerator;
import edu.eci.arsw.exam.Product;
import edu.eci.arsw.exam.models.Offert;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OffertMessageListener implements MessageListener {

    private static final int MAX_RANDOM_OFFER = 99_999_999;
    private static final String EXCHANGE = "QDM-EXCHANGE";
    private static final String OFFER_ROUTING_KEY = "offert.submit";

    private final Random rand = new Random(System.currentTimeMillis());
    private static final Map<String, Integer> offertList = new HashMap<>();

    protected AmqpTemplate amqpTemplate;

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public OffertMessageListener() {
        super();
        System.out.println("Comprador #" + IdentityGenerator.actualIdentity + " esperando eventos...");
    }

    @Override
    public void onMessage(Message message) throws AmqpException {
        try {
            Product receivedProduct = new Product(message.getBody());
            String productCode = receivedProduct.getCode();
            System.out.println("Comprador #" + IdentityGenerator.actualIdentity + " recibió: " + productCode);

            if (offertList.containsKey(productCode)) {
                System.out.println("Comprador #" + IdentityGenerator.actualIdentity + " ya envio una oferta para " + productCode);
                return;
            }

            int montoOferta = generateOfferAmount(receivedProduct.getStartPrice());
            Offert offert = new Offert(IdentityGenerator.actualIdentity, productCode, montoOferta);
            offertList.put(productCode, montoOferta);

            amqpTemplate.convertAndSend(EXCHANGE, OFFER_ROUTING_KEY, offert);

            System.out.println("Comprador #" + IdentityGenerator.actualIdentity + " envio oferta por: " + montoOferta);

        } catch (Exception e) {
            throw new RuntimeException("An exception occured while trying to get a AMQP object:" + e.getMessage(), e);
        }

    }

    private int generateOfferAmount(int startPrice) {
        int offer = Math.abs(rand.nextInt(MAX_RANDOM_OFFER));
        while (offer < startPrice) {
            offer = Math.abs(rand.nextInt(MAX_RANDOM_OFFER));
        }
        return offer;
    }
}
