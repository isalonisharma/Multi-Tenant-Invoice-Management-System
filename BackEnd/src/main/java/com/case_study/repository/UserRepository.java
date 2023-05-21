package com.case_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findById(long id);

	User findByUsername(String username);

	User findByUsernameAndPassword(String username, String password);
}