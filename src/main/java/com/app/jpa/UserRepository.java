package com.app.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
	User findByUsername(String username);
	
	List<User> findAll();
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.username = :username WHERE u.id = :id")
	int updateUsername(@Param("username") String username, @Param("id") Long id); 
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.email = :email")
	int updateEmail(@Param("email") String email); 
	
	@Transactional
	Long deleteByUsername(String username);
    
}
