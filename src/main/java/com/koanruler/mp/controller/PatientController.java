package com.koanruler.mp.controller;

import com.koanruler.mp.entity.Patient;
import com.koanruler.mp.entity.PatientInfo;
import com.koanruler.mp.entity.PatientSearchCondition;
import com.koanruler.mp.entity.User;
import com.koanruler.mp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
	@Autowired
	private PatientService patientService;

	@RequestMapping("/page")
	public String searchPatient(@RequestBody PatientSearchCondition patientSearchCondition){

		Integer[] totalCount = {0};

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Patient> patients = patientService.getOneGroupPatientInfo(user.getId(),
											  patientSearchCondition.getPatientNameOrBedNum(),
											  false,
											  patientSearchCondition.getCountPerPage() * (patientSearchCondition.getPageth()-1),
											  patientSearchCondition.getCountPerPage(),
											  true);

		List<PatientInfo> patientinfolist = new ArrayList<PatientInfo>();
		for(Patient p: patients){
			PatientInfo patientinfo = new PatientInfo();
			patientinfo.setPatient(p);
			patientinfo.setUsername( userService.getFullName(p.getUserid()) );
			patientinfolist.add(patientinfo);
		}

		int totalPage = totalCount[0]/rows;
		if(totalCount[0]%rows != 0)
			totalPage++;
	}
	
	public long getCount(@WebParam(name="userID") int userID, @WebParam(name="patientName")String patientName, @WebParam(name="inhospital")boolean inhospital)
	{
		return patientService.getCount(userID, patientName, inhospital);
	}
	
	public List<Patient> getBindPatientInfo(@WebParam(name="patientIDList") List<Integer> patientIDList )
	{
		return patientService.getPatientsInfo(patientIDList);
	}
	
	public List<Patient> getOneGroupPatientInfo(@WebParam(name="userID") int userID, @WebParam(name="patientName")String patientName, @WebParam(name="inhospital")boolean inhospital, @WebParam(name="firstPatientIndex") int firstPatientIndex, @WebParam(name="patientCount") int patientCount)
	{
		return patientService.getOneGroupPatientInfo(userID, patientName, inhospital, firstPatientIndex, patientCount);
	}
	
	public List<Patient> getAllPatientInfo(@WebParam(name="userID") int userID )
	{
		return patientService.getAllPatientInfo(userID);
	}
	
	public List<Patient> searchPatient(@WebParam(name="patientName") String patientName )
	{
		return patientService.searchPatient(patientName);
	}
	
	public boolean addPatient( @WebParam(name="patientinfo") Patient patientInfo )
	{
		return patientService.addPatient(patientInfo);
	}
	
	public Patient getPatientInfo(@WebParam(name="patientID") int patientID )
	{
		return patientService.getPatient(patientID);
	}
}
