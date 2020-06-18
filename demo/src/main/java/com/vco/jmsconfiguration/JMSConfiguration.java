package com.vco.jmsconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.vco.component.JMSReceiver;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;

@Configuration
public class JMSConfiguration {
	private static final String QUEUENAME_WRITE = "MYQUEUE";
    private static final String QUEUENAME_READ = "MYQUEUE";

    @Autowired
    JMSReceiver jmsReceiver;

    @Bean
    /**
     * Spring bean to WRITE/SEND/ENQUEUE messages on a queue with a certain name
     */
    public JmsTemplate jmsTemplate(ConnectionFactory conFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestinationName(QUEUENAME_WRITE);
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setConnectionFactory(conFactory);

        return jmsTemplate;
    }

    /**
     * Spring bean to READ/RECEIVE/DEQUEUE messages of a queue with a certain name
     * All of this happens under a code managed transaction
     * to commit the change on Oracle (remove of the message from the queue table)
     * Reference the application custom code handling the message here
     */
    @Bean
    public DefaultMessageListenerContainer messageListenerContainer(ConnectionFactory conFactory, DataSource dataSource) {
        DefaultMessageListenerContainer dmlc = new DefaultMessageListenerContainer();
        dmlc.setDestinationName(QUEUENAME_READ);
        dmlc.setSessionTransacted(true);
        dmlc.setConnectionFactory(conFactory);

        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        dmlc.setTransactionManager(manager);

        // Add here our self-written JMS Receiver
        dmlc.setMessageListener(jmsReceiver);
        return dmlc;
    }
}
