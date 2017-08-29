package com.koanruler.mp.service;

import com.koanruler.mp.entity.*;
import com.koanruler.mp.repository.TerminalRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class TerminalService {
	@Autowired
	private TerminalRepository terminalRepository;
	
	@Autowired
	private UserService userService;

    @Autowired
    private JPAQueryFactory queryFactory;

	//获取某一用户及下级用户的终端绑定信息
	public List<BindTerminalInfo> getAllTerminalInfo(int userID) {
		List<Integer> userIDList = userService.getAllChildID(userID);
		userIDList.add(userID);

        List<BindTerminalInfo> bindTerminalInfoList = new ArrayList<>();

        List<Tuple> result = queryFactory.select(QTerminal.terminal, QUser.user, QPatient.patient)
					.from(QTerminal.terminal)
                    .join(QUser.user)
                    .on(QTerminal.terminal.userid.eq(QUser.user.id))
					.leftJoin(QPatient.patient)
					.on(QTerminal.terminal.patientid.eq(QPatient.patient.id))
                    .orderBy(QTerminal.terminal.patientid.desc())
					.where(QTerminal.terminal.deleteflag.eq(false).and(QTerminal.terminal.userid.in(userIDList)))
					.fetch();

        for (Tuple row: result){
            Terminal terminal = row.get(QTerminal.terminal);
            User user = row.get(QUser.user);
            Patient patient = row.get(QPatient.patient);

            PatientInfo patientinfo = new PatientInfo();
            patientinfo.setPatient(patient);
            BindTerminalInfo bindTerminalInfo = new BindTerminalInfo();
            bindTerminalInfo.setTerminal(terminal);
            bindTerminalInfo.setPatientinfo(patientinfo);

            bindTerminalInfo.getPatientinfo().setUsername(user.getName());
            bindTerminalInfo.getPatientinfo().setPatient(patient);

            if(user.getType() == 5){   //科室
                bindTerminalInfo.getPatientinfo().setDepartment( user.getName() );
                User parent = userService.getUser(user.getParentuserid());
                bindTerminalInfo.getPatientinfo().setHospital(parent.getName());
            }else if(user.getType() == 4){   //医院
                bindTerminalInfo.getPatientinfo().setDepartment("");
                bindTerminalInfo.getPatientinfo().setHospital(user.getName());
            }

            bindTerminalInfoList.add(bindTerminalInfo);
        }

		return bindTerminalInfoList;
	}

	public Terminal getTerminal(String terminalNum) {
		return terminalRepository.findByTerminalnumber(terminalNum);
	}

	public boolean bindTerminal(String terminalNum, int patientID) {		
		Terminal terminal = terminalRepository.findByTerminalnumber(terminalNum);
		terminal.setPatientid(patientID);
		terminalRepository.save(terminal);
		
		return true;
	}

	
}
