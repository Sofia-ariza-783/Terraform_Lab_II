package edu.eci.arsw.exam.events;

import edu.eci.arsw.exam.FachadaPersistenciaOfertas;
import edu.eci.arsw.exam.MainFrame;
import edu.eci.arsw.exam.models.Offert;
import edu.eci.arsw.exam.remote.ManejadorOfertas;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class OffertMessageListener implements MessageListener {

    private static final int MAX_OFFERS_PER_PRODUCT = 3;

    private ManejadorOfertas manejadorOfertas;
    private MainFrame mainFrame;
    private FachadaPersistenciaOfertas fachadaPersistenciaOfertas;
    private OffertMessageProducer messageProducer;

    public void setManejadorOfertas(ManejadorOfertas manejadorOfertas) {
        this.manejadorOfertas = manejadorOfertas;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setFachadaPersistenciaOfertas(FachadaPersistenciaOfertas fachadaPersistenciaOfertas) {
        this.fachadaPersistenciaOfertas = fachadaPersistenciaOfertas;
    }

    public void setMessageProducer(OffertMessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    public void onMessage(Message message) throws AmqpException {
        try {
            Offert receivedOffert = new Offert(message.getBody());
            String productCode = receivedOffert.getProductCode();
            int offeredAmount = (int) Math.round(receivedOffert.getPrice());

            manejadorOfertas.agregarOferta(receivedOffert.getBuyer(), productCode, offeredAmount);
            publishWinnerIfReady(productCode);
            notifyUi();

            System.out.println("Oferta recibida de " + receivedOffert.getBuyer() + " para " + productCode);

        } catch (Exception e) {
            throw new RuntimeException("An exception occured while trying to process an AMQP offert:" + e.getMessage(), e);
        }
    }

    private void publishWinnerIfReady(String productCode) {
        int offers = fachadaPersistenciaOfertas.getMapaOfertasRecibidas().getOrDefault(productCode, 0);
        boolean alreadyNotified = fachadaPersistenciaOfertas.getMapaGanadorNotificado().getOrDefault(productCode, false);

        if (offers < MAX_OFFERS_PER_PRODUCT || alreadyNotified) {
            return;
        }

        String winnerBuyer = fachadaPersistenciaOfertas.getMapaOferentesAsignados().get(productCode);
        Integer winnerAmount = fachadaPersistenciaOfertas.getMapaMontosAsignados().get(productCode);
        if (winnerBuyer == null || winnerAmount == null) {
            return;
        }

        Offert winningOffert = new Offert(winnerBuyer, productCode, winnerAmount);
        messageProducer.sendWinnerNotification(winningOffert);
        fachadaPersistenciaOfertas.getMapaGanadorNotificado().put(productCode, true);
    }

    private void notifyUi() {
        if (mainFrame != null) {
            mainFrame.notifyOffersUpdated();
        }
    }
}
