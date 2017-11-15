package koanruler.controller;

import koanruler.entity.Subfacilities;
import koanruler.service.SubfacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.WebParam;
import java.util.List;

@RestController
@RequestMapping("/subfacility")
public class SubfacilityController {

	@Autowired
	private SubfacilityService subfacilityService;	

	public List<Subfacilities> getAllDepartmentInfo( @WebParam(name="userID") int userID )
	{
		return subfacilityService.getAllDepartmentInfo(userID);
	}
}
