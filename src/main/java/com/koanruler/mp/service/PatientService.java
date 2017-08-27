package com.koanruler.mp.service;

import com.koanruler.mp.entity.Patient;
import com.koanruler.mp.entity.QPatient;
import com.koanruler.mp.repository.PatientRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private JPAQueryFactory queryFactory;

    public Integer getCount(int userID, String patientName, boolean inhospital) {
        return patientRepository.countByUseridAndNameAndState(userID, patientName, inhospital ? 1 : 0);
    }

    public List<Patient> getPatientsInfo(List<Integer> patientIDList) {
        return patientRepository.findByIdIn(patientIDList);
    }

    public List<Patient> getOneGroupPatientInfo(int userID, String patientName, boolean inhospital, int firstPatientIndex,
                                                int patientCount) {

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and( QPatient.patient.userid.eq(userID).and(QPatient.patient.state.eq(inhospital)) );
        if (!patientName.isEmpty())
            predicate.and(QPatient.patient.name.contains(patientName).or(QPatient.patient.bednumber.contains(patientName)));

        QueryResults results = queryFactory.selectFrom(QPatient.patient)
                .where(predicate)
                .offset(firstPatientIndex)
                .limit(patientCount)
                .fetchResults();

        //results.getTotal():总数
        return results.getResults();

//		if(patientName.length() == 0)
//		{
//			Query q = em.createNativeQuery("SELECT * FROM Patient p where p.userid=:id and p.state=:state LIMIT :fisrt, :count", Patient.class);
//			q.setParameter("id", userID);
//			q.setParameter("state", inhospital?1:0);
//			q.setParameter("fisrt", firstPatientIndex-1);
//			q.setParameter("count", patientCount);
//			return q.getResultList();
//		}
//		else
//		{
//			Query q = em.createNativeQuery("SELECT * FROM Patient p where p.userid=:id and p.state=:state and (p.name LIKE %:name% or p.bednumber LIKE %:bed%) LIMIT :fisrt, :count", Patient.class);
//			q.setParameter("id", userID);
//			q.setParameter("state", inhospital?1:0);
//			q.setParameter("fisrt", firstPatientIndex-1);
//			q.setParameter("count", patientCount);
//			q.setParameter("name", patientName);
//			q.setParameter("bed", patientName);
//			return q.getResultList();
//		}
    }

    public List<Patient> getAllPatientInfo(int userID) {
        return patientRepository.findByUserid(userID);

    }

    public List<Patient> searchPatient(String patientName) {
        return patientRepository.findByNameContains(patientName);
    }

    public boolean addPatient(Patient patientInfo) {
        patientRepository.save(patientInfo);
        return true;
    }

    public Patient getPatient(int patientID) {
        return patientRepository.findOne(patientID);
    }

    List<Integer> getPatientIds(List<Integer> userIDList) {
        if (userIDList.size() == 0)
            return null;

        return patientRepository.getPatientIDByUseridIn(userIDList);
    }

    List<Patient> getPatientsByUserId(List<Integer> userIDList, int count) {
        return queryFactory.selectFrom(QPatient.patient)
                .where(QPatient.patient.userid.in(userIDList))
                .orderBy(QPatient.patient.id.desc())
                .limit(count)
                .fetch();
//        String sql = "select p From Patient p WHERE (p.userid in :userIDList) and exists (select 1 from Data d where d.patientid = p.id and d.endtime <> '0000-00-00 00:00:00') order by p.id DESC";
//
//        Query query = em.createQuery(sql);
//        query.setParameter("userIDList", userIDList);
//        query.setFirstResult(0).setMaxResults(count);
//
//        return query.getResultList();
    }

    void Save(Patient patient) {
        patientRepository.save(patient);
    }
}
