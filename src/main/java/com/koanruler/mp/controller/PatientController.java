package com.koanruler.mp.controller;

import com.koanruler.mp.entity.Patient;
import com.koanruler.mp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.WebParam;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
	@Autowired
	private PatientService patientService;
	
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
