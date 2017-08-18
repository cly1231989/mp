package com.koanruler.mp.service;

import com.koanruler.mp.entity.Patient;
import com.koanruler.mp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
	@Autowired
	private PatientRepository patientRepository;
	
	@PersistenceContext
	private EntityManager em;

	public Integer getCount(int userID, String patientName, boolean inhospital) {
		return patientRepository.countByUseridAndNameAndState(userID, patientName, inhospital?1:0);
	}

	public List<Patient> getPatientsInfo(int[] patientIDList) {
		List<Patient> patientList = new ArrayList<Patient>();
		
		for(int patientID: patientIDList)
		{
			patientList.add( patientRepository.findById(patientID) );
		}
		return patientList;
	}

	public List<Patient> getOneGroupPatientInfo(int userID, String patientName, boolean inhospital, int firstPatientIndex,
                                                int patientCount) {
		if(patientName.length() == 0)
		{
			Query q = em.createNativeQuery("SELECT * FROM Patient p where p.userid=:id and p.state=:state LIMIT :fisrt, :count", Patient.class);
			q.setParameter("id", userID);
			q.setParameter("state", inhospital?1:0);	
			q.setParameter("fisrt", firstPatientIndex-1);	
			q.setParameter("count", patientCount);	
			return q.getResultList();
		}
		else
		{
			Query q = em.createNativeQuery("SELECT * FROM Patient p where p.userid=:id and p.state=:state and (p.name LIKE %:name% or p.bednumber LIKE %:bed%) LIMIT :fisrt, :count", Patient.class);
			q.setParameter("id", userID);
			q.setParameter("state", inhospital?1:0);
			q.setParameter("fisrt", firstPatientIndex-1);	
			q.setParameter("count", patientCount);	
			q.setParameter("name", patientName);
			q.setParameter("bed", patientName);
			return q.getResultList();
		}
	}

	public List<Patient> getAllPatientInfo(int userID) {
		return patientRepository.findByUserid(userID);
		
	}

	public List<Patient> searchPatient(String patientName) {
		return patientRepository.findByNameLike("%"+patientName+"%");   
	}

	public boolean addPatient(Patient patientInfo) {
		patientRepository.save(patientInfo);
		return true;
	}

	public Patient getPatient(int patientID) {
		return patientRepository.findOne(patientID);
	}

	public List<Integer> getPatientIds(List<Integer> userIDList) {
	    if(userIDList.size() == 0)
	        return null;

	    return patientRepository.getPatientIDByUserid(userIDList);
	}

    public List<Patient> getPatientsByUserId(List<Integer> userIDList, int count) {
        String sql = "select patient From Patient p WHERE (p.userid in :userIDList) and exists (select 1 from Data d where d.patientid = p.id and d.endtime != '0000-00-00 00:00:00') order by p.id DESC";

        Query query = em.createQuery(sql);
        query.setParameter("userIDList", userIDList);
        query.setFirstResult(0).setMaxResults(count);

        return query.getResultList();
    }

    public void Save(Patient patient) {
	    patientRepository.save(patient);
    }
}
