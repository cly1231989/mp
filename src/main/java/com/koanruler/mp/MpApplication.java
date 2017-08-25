package com.koanruler.mp;

import com.koanruler.mp.service.DataService;
import com.koanruler.mp.service.RmpService;
import com.koanruler.mp.service.TerminalService;
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

	@Autowired
	private DataService dataService;

	@Autowired
	private TerminalService terminalService;

	public static void main(String[] args) {
		SpringApplication.run(MpApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		terminalService.getAllTerminalInfo(1);
		long n = dataService.getDataCount(1, "test");
		dataService.getOneGroupData(1, null, 1, 50);
		Endpoint.publish("http://localhost:" + servicePort
				+ "/mp/cxf/clientservice", rmpService );
	}
}
