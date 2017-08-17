package com.koanruler.mp.repository;

import com.koanruler.mp.entity.Subfacilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubfacilityRepository extends JpaRepository<Subfacilities, Integer>{

	List<Subfacilities> findByUseridAndType(int userID, int type);

}
