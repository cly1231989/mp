package com.koanruler.mp.repository;

import com.koanruler.mp.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Integer>{
	Terminal findByTerminalnumber(String terminalNum);
}
