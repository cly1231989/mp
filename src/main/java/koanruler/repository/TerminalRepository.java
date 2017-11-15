package koanruler.repository;

import koanruler.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Integer>, QueryDslPredicateExecutor<Terminal>{
	Terminal findByTerminalnumberAndDeleteflag(String terminalNum, boolean deleteFlag);
}
