package koanruler.repository;

import koanruler.entity.Subfacilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubfacilityRepository extends JpaRepository<Subfacilities, Integer>, QueryDslPredicateExecutor<Subfacilities>{

	List<Subfacilities> findByUseridAndType(int userID, int type);

}
