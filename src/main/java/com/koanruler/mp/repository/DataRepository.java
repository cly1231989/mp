package com.koanruler.mp.repository;

import com.koanruler.mp.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<Data, Integer>, QueryDslPredicateExecutor<Data>{

//    @Query("Select Count(d.id) from Data d left join Patient p on d.patientid = p.id where d.patientid IN :patientIDList and (d.terminalnum like '%:terminalNumOrPatientName%' or p.name like '%:terminalNumOrPatientName%' )")
//    Long getDataCountByUsersAndTerminalNumOrPatientName(@Param("patientIDList") List<Integer> patientIDList, @Param("terminalNumOrPatientName")String terminalNumOrPatientName);

    @Query("Select Count(d.id) from Data d where d.patientid IN :patientIDList")
    Long getDataCountByUsers(@Param("patientIDList") List<Integer> patientIDList);

    List<Data> findTopByPatientidOrderByIdDesc(int patientID);
    List<Data> findByPatientidAndType(int patientID, int type);
    List<Data> findByPatientidAndTypeOrderByIdDesc(int patientID, int type);
}
