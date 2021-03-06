package koanruler.repository;

import koanruler.entity.Patient;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends PagingAndSortingRepository<Patient, Integer>, QueryDslPredicateExecutor<Patient> {

	Integer countByUseridAndNameAndState(int userID, String patientName, boolean inHospital);

	Patient findById(int patientID);
	List<Patient> findByIdIn(List<Integer> patientIDList);

	List<Patient> findByIdAndNameAndState(int userID, String patientName, boolean inHospital);

	List<Patient> findByUserid(int userID);
	List<Patient> findByNameContains(String name);

    List<Patient> findByUseridIn(List<Integer> userIDList);

	List<Patient> findByNameLike(String patientName);
}
