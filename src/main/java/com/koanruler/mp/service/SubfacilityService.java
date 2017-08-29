package com.koanruler.mp.service;

import com.koanruler.mp.entity.Subfacilities;
import com.koanruler.mp.repository.SubfacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class SubfacilityService {
	@Autowired
	private SubfacilityRepository subfacilityRepository;

	public List<Subfacilities> getAllDepartmentInfo(int userID) {
		return subfacilityRepository.findByUseridAndType(userID, 1);
	}

}
