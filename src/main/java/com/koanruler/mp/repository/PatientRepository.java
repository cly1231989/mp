package com.koanruler.mp.repository;

import com.koanruler.mp.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends PagingAndSortingRepository<Patient, Integer> {

	Integer countByUseridAndNameAndState(int userID, String patientName, int inhospital);

	Patient findById(int patientID);

	List<Patient> findByIdAndNameAndState(int userID, String patientName, int i);

	List<Patient> findByUserid(int userID);

    List<Patient> findByUseridIn(List<Integer> userIDList);

	List<Patient> findByNameLike(String patientName);

	@Query("select p.id From Patient p WHERE p.userid in :userIDList")
    List<Integer> getPatientIDByUserid(@Param("userIDList") List<Integer> userIDList);
}
