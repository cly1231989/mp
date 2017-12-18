package koanruler;

import com.sun.xml.ws.transport.http.servlet.WSServletContextListener;
import koanruler.service.DataService;
import koanruler.webservice.RmpService;
import koanruler.service.TerminalService;
import koanruler.service.UserService;
import koanruler.webservice.RmpServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
// import javax.xml.ws.Endpoint;

@SpringBootApplication
public class MpApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MpApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MpApplication.class);
	}
}
