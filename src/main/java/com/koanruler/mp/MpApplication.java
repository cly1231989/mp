package com.koanruler.mp;

import com.koanruler.mp.service.DataService;
import com.koanruler.mp.service.RmpService;
import com.koanruler.mp.service.TerminalService;
import com.koanruler.mp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import javax.xml.ws.Endpoint;

@SpringBootApplication
public class MpApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Value("${service.port}")
	private String servicePort;

	@Autowired
    private RmpService rmpService;

	@Autowired
	private UserService userService;

	@Autowired
	private DataService dataService;

	@Autowired
	private TerminalService terminalService;

	public static void main(String[] args) {
		SpringApplication.run(MpApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MpApplication.class);
	}

	@Override
	public void run(String... strings) throws Exception {
//		List<Integer> result = userService.getAllChildID(1);
//		userService.getFullName(28);
//		terminalService.getAllTerminalInfo(2);
//		PatientDataSearchCondition searchCondition = new PatientDataSearchCondition();
//		searchCondition.setPatientname("test");
//        searchCondition.setPatientcount(10);
//		dataService.searchReplayInfo1(2, searchCondition);
//		//long n = dataService.getDataCount(1, "test");
//		dataService.getOneGroupData(1, null, 1, 50);
		Endpoint.publish("http://localhost:" + servicePort
				+ "/mp/cxf/clientservice", rmpService );
	}
}
