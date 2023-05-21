package com.case_study.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.case_study.entity.Organization;
import com.case_study.entity.User;
import com.case_study.entity.UserOrganization;;

@Repository
public interface UserOrganizationRepository extends JpaRepository<UserOrganization, Long> {
	UserOrganization findByUser(User user);

	List<UserOrganization> findByOrganization(Organization organization);
}