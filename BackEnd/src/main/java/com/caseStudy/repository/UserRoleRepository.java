package com.caseStudy.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.UserModel;
import com.caseStudy.model.UserRoleModel;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleModel, Long> {
	List<UserRoleModel> findByuserModel(UserModel userModel);
}
