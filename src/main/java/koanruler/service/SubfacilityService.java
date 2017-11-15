package koanruler.service;

import koanruler.entity.Subfacilities;
import koanruler.repository.SubfacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubfacilityService {
	@Autowired
	private SubfacilityRepository subfacilityRepository;

	public List<Subfacilities> getAllDepartmentInfo(int userID) {
		return subfacilityRepository.findByUseridAndType(userID, 1);
	}

}
