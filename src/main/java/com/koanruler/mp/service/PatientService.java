package com.koanruler.mp.service;

import com.koanruler.mp.entity.Patient;
import com.koanruler.mp.entity.QPatient;
import com.koanruler.mp.repository.PatientRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    public long getCount(int userID, String patientName, boolean inhospital) {
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(QPatient.patient.userid.eq(userID)).and(QPatient.patient.state.eq(inhospital));
        if (patientName != null && !patientName.isEmpty())
            predicate.and(QPatient.patient.name.contains(patientName));

        return queryFactory.selectFrom(QPatient.patient).where(predicate).fetchCount();
        //return patientRepository.countByUseridAndNameAndState(userID, patientName, inhospital);
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
    }

    void Save(Patient patient) {
        patientRepository.save(patient);
    }
}
