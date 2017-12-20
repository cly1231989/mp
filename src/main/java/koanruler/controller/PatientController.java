package koanruler.controller;

import koanruler.entity.Patient;
import koanruler.entity.PatientSearchCondition;
import koanruler.entity.ResultData;
import koanruler.entity.TerminalUseInfo;
import koanruler.service.PatientService;
import koanruler.service.UserService;
import koanruler.util.UserUtil;
import com.querydsl.core.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    /**
     * 分页获取本用户及下属机构的病人信息
     * @param nameOrBedNum: 病人姓名或者床号
     * @param page: 当前页
     * @param countPerPage
     * @param sort
     * @return PatientSearchResult: 包括满足条件的病人总数和本次查询的病人信息
     */
    @GetMapping("/patients")
    public ResultData<TerminalUseInfo> searchPatient(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(name = "per_page", defaultValue = "-1") Integer countPerPage,
                                                     @RequestParam(name = "filter", defaultValue = "") String nameOrBedNum,
                                                     @RequestParam(name = "sort", defaultValue = "") Integer sort){

        PatientSearchCondition patientSearchCondition = new PatientSearchCondition(nameOrBedNum,
                (page-1) * countPerPage, countPerPage, true, PatientSearchCondition.InHospitalStatus.all);

        List<Integer> userIds = userService.getAllChildID(UserUtil.getCurUser().getId());
        QueryResults<Patient> results = patientService.getOneGroupPatientInfo(userIds,
                patientSearchCondition);
        results.getResults().forEach(patient -> patient.setUsername(userService.getFullName(patient.getUserid())));

        return new ResultData(page, countPerPage, null, null, results.getTotal(), results.getResults());
    }
}
