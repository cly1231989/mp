package koanruler.service;

import koanruler.entity.Patient;
import koanruler.entity.PatientSearchCondition;
import koanruler.entity.QPatient;
import koanruler.repository.PatientRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    public List<Patient> getPatientsInfo(List<Integer> patientIDList) {
        return patientRepository.findByIdIn(patientIDList);
    }

    public QueryResults<Patient> getOneGroupPatientInfo(List<Integer> userIds, PatientSearchCondition patientSearchCondition) {

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and( QPatient.patient.userid.in(userIds) );

        if (patientSearchCondition.getInHospitalStatus() == PatientSearchCondition.InHospitalStatus.inHospital)
            predicate.and(QPatient.patient.state.eq(true));
        else if (patientSearchCondition.getInHospitalStatus() == PatientSearchCondition.InHospitalStatus.outHospital)
            predicate.and(QPatient.patient.state.eq(false));

        if (!patientSearchCondition.getNameOrBedNum().isEmpty())
            predicate.and(QPatient.patient.name.contains(patientSearchCondition.getNameOrBedNum())
                    .or(QPatient.patient.bednumber.contains(patientSearchCondition.getNameOrBedNum())));

        OrderSpecifier orderSpecifier = QPatient.patient.id.asc();
        if (patientSearchCondition.isDesc())
            orderSpecifier = QPatient.patient.id.desc();

        return queryFactory.selectFrom(QPatient.patient)
                .where(predicate)
                //.orderBy(QPatient.patient.id.desc())
                .offset(patientSearchCondition.getFirstIndex())
                .limit(patientSearchCondition.getCount())
                .orderBy(orderSpecifier)
                .fetchResults();
    }

    public Patient getPatient(int patientID) {
        return patientRepository.findOne(patientID);
    }

    List<Integer> getPatientIds(List<Integer> userIDList) {
        if (userIDList.size() == 0)
            return null;

        return patientRepository.findByUseridIn(userIDList)
                                .stream()
                                .map(patient -> patient.getId())
                                .collect(Collectors.toList());
    }

    public Patient Save(Patient patient) {
        return patientRepository.save(patient);
    }
}
