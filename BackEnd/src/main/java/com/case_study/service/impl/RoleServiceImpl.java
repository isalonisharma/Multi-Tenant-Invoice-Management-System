package com.case_study.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.entity.Role;
import com.case_study.exception.RoleNotFoundException;
import com.case_study.model.RoleModel;
import com.case_study.repository.RoleRepository;
import com.case_study.service.RoleService;
import com.case_study.utility.CommonConstants;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role createRole(RoleModel roleModel) {
		return roleRepository.save(new Role(roleModel));
	}

	@Override
	public Role deleteById(Long id) throws RoleNotFoundException {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new RoleNotFoundException(CommonConstants.ROLE_NOT_FOUND + id));
		roleRepository.delete(role);
		return role;
	}

	@Override
	public Role updateById(Long id, RoleModel roleModel) throws RoleNotFoundException {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new RoleNotFoundException(CommonConstants.ROLE_NOT_FOUND + id));
		role.setDesignation(roleModel.getDesignation());
		return roleRepository.save(role);
	}

	@Override
	public List<Role> findAll() throws SQLException {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(Long id) throws RoleNotFoundException {
		return roleRepository.findById(id)
				.orElseThrow(() -> new RoleNotFoundException(CommonConstants.ROLE_NOT_FOUND + id));
	}

	@Override
	public Role findByDesignation(String designation) {
		return roleRepository.findByDesignation(designation);
	}
}