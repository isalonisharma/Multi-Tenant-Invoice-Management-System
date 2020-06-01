package com.caseStudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
	UserModel findByuserId(long userId);

	UserModel findByUsername(String username);

	UserModel findByUsernameAndPassword(String username, String password);
}
