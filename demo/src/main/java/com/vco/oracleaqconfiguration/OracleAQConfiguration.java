package com.vco.oracleaqconfiguration;

import oracle.jdbc.pool.OracleDataSource;
import oracle.jms.AQjmsFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class OracleAQConfiguration {
	
	@Value("${myapplication.datasource.user}")
    private String user;

    @Value("${myapplication.datasource.password}")
    private String password;

    @Value("${myapplication.datasource.connectionstring}")
    private String connectionstring;

    @Bean
    /**
     * Spring bean with the configuration details of where the Oracle database is containing the QUEUES
     */
    public DataSource dataSource() throws SQLException {
        OracleDataSource ds = new OracleDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setURL(connectionstring);
        ds.setImplicitCachingEnabled(true);
        ds.setFastConnectionFailoverEnabled(true);
        return ds;
    }

    @Bean
    /**
     * The KEY component effectively connecting to the Oracle AQ system using the datasource input
     */
    public QueueConnectionFactory connectionFactory(DataSource dataSource) throws JMSException {
        return AQjmsFactory.getQueueConnectionFactory(dataSource);
    }

}
