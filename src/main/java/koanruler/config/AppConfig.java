package koanruler.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sun.xml.ws.transport.http.servlet.WSServlet;
import com.sun.xml.ws.transport.http.servlet.WSServletContextListener;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by 远 on 2017/8/25.
 */

@Configuration
public class AppConfig {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(em);
    }

//    @Bean
//    public ServletRegistrationBean servletRegistrationBean(){
//        return new ServletRegistrationBean(new WSServlet(),"/cxf/clientservice");
//    }
//
//    @Bean
//    public WSServletContextListener wsServletContextListener(){
//        return new WSServletContextListener();
//    }
}
