package com.koanruler.mp.service;

import com.koanruler.mp.entity.*;
import com.koanruler.mp.repository.TerminalRepository;
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
	private PatientService patientService;
	
	@PersistenceContext
	private EntityManager em;

	public List<BindTerminalInfo> getAllTerminalInfo(int userID) {
		List<Integer> userIDList = userService.getAllChildID(userID);
		userIDList.add(userID);

		List<BindTerminalInfo> bindTerminalInfoList = new ArrayList<BindTerminalInfo>();
		
		String sql = "select t From Terminal t WHERE (t.userid in :userIDList) and t.deleteflag <> true order by t.patientid desc";
//		for(int id : userIDList){
//			sql.append("t.userid = ");
//			sql.append(id);
//			sql.append(" or ");
//		}
//
//		sql.delete(sql.length()-4, sql.length());
//		sql.append(") and t.deleteflag != '1' order by t.patientid desc");
		Query query = em.createQuery(sql);
		query.setParameter("userIDList", userIDList);
		List<Terminal> terminalInfoList = query.getResultList();
		
		for(int i = 0; i < terminalInfoList.size(); i++){
			Terminal terminal = terminalInfoList.get(i);
			
			BindTerminalInfo bindTerminalInfo = new BindTerminalInfo();
			bindTerminalInfo.setTerminal(terminal);
			
			PatientInfo patientinfo = new PatientInfo();
			User user = userService.getUser( terminal.getUserid() );

			if(user.getType() == 5){   //科室
				patientinfo.setDepartment( user.getName() );
				
				int parentID = user.getParentuserid();				
				user = userService.getUser(parentID);;
				patientinfo.setHospital(user.getName());
			}else if(user.getType() == 4){   //医院
				patientinfo.setDepartment("");
				patientinfo.setHospital(user.getName());
			}
			
			if(terminal.getPatientid() == 0){
				patientinfo.setPatient(null);
			}else{
				Patient patient = patientService.getPatient( terminal.getPatientid() );
				patientinfo.setPatient(patient);
			}
								
			bindTerminalInfo.setPatientinfo(patientinfo);
			bindTerminalInfoList.add(bindTerminalInfo);
		}

		return bindTerminalInfoList ;
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
