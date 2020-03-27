package hu.bme.aut.webshop;

import hu.bme.aut.webshop.domain.Advertisement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@SpringBootApplication
@EnableJms
public class Caring {

    public static void main(String[] args) {
        SpringApplication.run(Caring.class, args);
    }

    @Bean
    public Context namingContext() throws NamingException {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        env.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
        return new InitialContext(env);
    }

    @Bean
    public ConnectionFactory connectionFactory(Context namingContext) throws NamingException {
        ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup("jms/RemoteConnectionFactory");
        UserCredentialsConnectionFactoryAdapter adapter = new UserCredentialsConnectionFactoryAdapter();
        adapter.setTargetConnectionFactory(connectionFactory);
        return adapter;
    }

    @Bean
    public Queue reservationQueue(Context namingContext) throws NamingException {
            return (Queue) namingContext.lookup("jms/queue/orderQueue");
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, Queue queue) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(queue);

        return jmsTemplate;
    }

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurerAdapter() {

            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.setBasePath("/api");
                config.exposeIdsFor(Advertisement.class);
                config.getCorsRegistry().addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT",
                        "DELETE");
            }
        };
    }

    @Bean
    public ServerEndpointExporter endpointExporter() {
        return new ServerEndpointExporter();
    }
}
