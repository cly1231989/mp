package com.koanruler.mp;

import com.koanruler.mp.entity.BindTerminalInfo;
import com.koanruler.mp.service.TerminalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MpApplicationTests {

	@Autowired
	TerminalService terminalService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllTerminalInfo()
	{
		List<BindTerminalInfo> result = terminalService.getAllTerminalInfo(2);
	}

}
