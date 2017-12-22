package koanruler.repository;

import koanruler.entity.PwdEditInfo;
import koanruler.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, QueryDslPredicateExecutor<User> {
	public Optional<User> findByName(String userName);

	public List<User> findByParentuserid(int userID);

	public Optional<User> findByAccount(String account);

	List<User> findByType(int type);

	List<User> findByIdIn(List<Integer> childUserIDList);

	@Modifying
	@Query("UPDATE User u set u.pwd = :pwd where u.id = :id")
	void editPwd(@Param("id") Integer id, @Param("pwd") String pwd);
}
