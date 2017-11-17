package koanruler.config;

import koanruler.service.UserService;
import koanruler.webservice.RmpService;
import koanruler.webservice.RmpServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * Created by user1 on 2017/11/17.
 * soap service和spring boot的整合，见：https://www.dexcoder.com/selfly/article/4593
 */
@Configuration
public class WebServiceConfig {
    @Autowired
    private RmpService rmpService;

    @Bean
    public ServletRegistrationBean soapServiceServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/cxf/*");
    }
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), rmpService);
        endpoint.publish("/clientservice");
        return endpoint;
    }
}
