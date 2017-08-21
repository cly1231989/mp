package com.koanruler.mp.repository;

import com.koanruler.mp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, QueryDslPredicateExecutor<User> {
	public User findByName(int userID);

	public List<User> findByParentuserid(int userID);

	public User findByAccount(String account);

	List<User> findByType(int type);

	List<User> findByIdIn(List<Integer> childUserIDList);
}
