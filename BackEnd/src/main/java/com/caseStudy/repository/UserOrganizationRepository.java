package com.caseStudy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caseStudy.model.OrganizationModel;
import com.caseStudy.model.UserModel;
import com.caseStudy.model.UserOrganizationModel;;

@Repository
public interface UserOrganizationRepository extends JpaRepository<UserOrganizationModel, Long> {
	UserOrganizationModel findByuserModel(UserModel userModel);

	List<UserOrganizationModel> findByorganizationModel(OrganizationModel organizationModel);
}
