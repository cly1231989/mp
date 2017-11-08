package com.koanruler.mp.service;

import com.koanruler.mp.entity.*;
import com.koanruler.mp.repository.TerminalRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TerminalService {
	@Autowired
	private TerminalRepository terminalRepository;
	
	@Autowired
	private UserService userService;

    @Autowired
    private JPAQueryFactory queryFactory;

    /**
     * 获取某一用户及下级用户的终端绑定信息，可根据终端编号进行搜索
     * @param userID
     * @param terNum
     * @return
     */
    private QueryResults<Tuple> searchTerminalInfo(int userID, String terNum) {
        List<Integer> userIDList = userService.getAllChildID(userID);

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(QTerminal.terminal.deleteflag.ne(true));
        if (terNum != null && !terNum.isEmpty())
            predicate.and(QTerminal.terminal.terminalnumber.contains(terNum));

        return queryFactory.select(QTerminal.terminal, QUser.user, QPatient.patient)
                .from(QTerminal.terminal)
                .join(QUser.user)
                .on(QTerminal.terminal.userid.eq(QUser.user.id))
                .leftJoin(QPatient.patient)
                .on(QTerminal.terminal.patientid.eq(QPatient.patient.id))
                .where(predicate)
                .orderBy(QTerminal.terminal.patientid.desc())
                .where(QTerminal.terminal.deleteflag.eq(false).and(QTerminal.terminal.userid.in(userIDList)))
                .fetchResults();
    }

    /**
     * 获取某一用户及下级用户的终端绑定信息
     * @param userID
     * @return
     */
	public List<BindTerminalInfo> getAllTerminalInfo(int userID) {
        List<BindTerminalInfo> bindTerminalInfoList = new ArrayList<>();
        List<Tuple> result = searchTerminalInfo(userID, null).getResults();

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

	Terminal getTerminal(String terminalNum) {
		return terminalRepository.findByTerminalnumberAndDeleteflag(terminalNum,false);
	}

    public Terminal getTerminal(Integer id) {
        return terminalRepository.findOne(id);
    }

//	public boolean bindTerminal(String terminalNum, int patientID) {
//		Terminal terminal = terminalRepository.findByTerminalnumberAndDeleteflag(terminalNum, false);
//		terminal.setPatientid(patientID);
//		terminalRepository.Save(terminal);
//
//		return true;
//	}


    /**
     * 获取用户及下属机构的终端使用信息，可根据终端编号和绑定情况进行搜索
     * @param userId
     * @param terNum
     * @param onlyFindBoundTer: 是否只查找已绑定的终端
     * @return
     */
    public ResultList<TerminalUseInfo> getTerminalInfo(Integer userId, int page, int countPerPage, String terNum, boolean onlyFindBoundTer) {
	    List<Integer> userIds = userService.getAllChildID(userId);

	    BooleanBuilder predicate = new BooleanBuilder();
	    predicate.and(QTerminal.terminal.deleteflag.eq(false))
                 .and(QTerminal.terminal.userid.in(userIds));

	    if (onlyFindBoundTer)
	        predicate.and(QTerminal.terminal.patientid.ne(0));

	    if (terNum != null && !terNum.isEmpty()){
	        predicate.and(QTerminal.terminal.terminalnumber.contains(terNum));
        }

        QueryResults<Tuple> results;
        JPAQuery<Tuple> query;
	    if (onlyFindBoundTer)       //只查找已被绑定的终端
            query = queryFactory.select(QTerminal.terminal, QPatient.patient)
                .from(QTerminal.terminal)
                .join(QPatient.patient)
                .on(QTerminal.terminal.patientid.eq(QPatient.patient.id))
                .where(predicate);
	    else
            query = queryFactory.select(QTerminal.terminal, QPatient.patient)
                    .from(QTerminal.terminal)
                    .leftJoin(QPatient.patient)
                    .on(QTerminal.terminal.patientid.eq(QPatient.patient.id))
                    .where(predicate);

	    if (countPerPage == -1) {
            query = query.offset( (page-1) * countPerPage).limit(countPerPage);
        }

        results = query.fetchResults();
	    return new ResultList<>(results.getTotal(), results.getResults().stream().map(row -> {

	        Terminal terminal = row.get(QTerminal.terminal);
	        Patient patient = row.get(QPatient.patient);
	        if (patient == null)
	            patient = new Patient();

	        return new TerminalUseInfo(terminal.getId(),
                                       terminal.getUserid(),
                                       userService.getFullName(terminal.getUserid()),
                                       patient.getName(),
                                       patient.getAge(),
                                       patient.getSex(),
                                       terminal.getTerminalnumber(),
                                       terminal.getBindtime());
        }).collect(Collectors.toList()));
    }

    public void Save(Terminal terminal) {
        terminalRepository.save(terminal);
    }

    public void deleteTerminalById(Integer id) {
        terminalRepository.delete(id);
    }
}
