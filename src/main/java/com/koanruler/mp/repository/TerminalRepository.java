package com.koanruler.mp.repository;

import com.koanruler.mp.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Integer>, QueryDslPredicateExecutor<Terminal>{
	Terminal findByTerminalnumber(String terminalNum);
}
