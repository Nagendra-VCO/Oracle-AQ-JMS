package com.vco.component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class JMSReceiver implements SessionAwareMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(JMSReceiver.class);
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        // We know/assume the Queue Payload type was set to 'TextMessage'
        TextMessage txtMessage = (TextMessage) message;
        logger.info("JMS Text Message received: " + txtMessage.getText());

        // ... further implementation
    }

}
