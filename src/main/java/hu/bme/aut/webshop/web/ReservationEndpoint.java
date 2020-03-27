package hu.bme.aut.webshop.web;

import hu.bme.aut.webshop.domain.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationEndpoint {

    @Autowired
    private JmsTemplate jmsTemplate;

    private Logger logger = LoggerFactory.getLogger(ReservationEndpoint.class);

    @PostMapping
    public Reservation save(@RequestBody Reservation o) {
        logger.info("Received reservation at the Caring application.");
        jmsTemplate.send(s -> s.createObjectMessage(o));
        return o;
    }
}
