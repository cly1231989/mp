package com.koanruler.mp;

import com.koanruler.mp.service.RmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.ws.Endpoint;

@SpringBootApplication
public class MpApplication implements CommandLineRunner {

	@Value("${service.port}")
	private String servicePort;

	@Autowired
    private RmpService rmpService;

	public static void main(String[] args) {
		SpringApplication.run(MpApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		Endpoint.publish("http://localhost:" + servicePort
				+ "/mp/cxf/clientservice", rmpService );
	}
}
