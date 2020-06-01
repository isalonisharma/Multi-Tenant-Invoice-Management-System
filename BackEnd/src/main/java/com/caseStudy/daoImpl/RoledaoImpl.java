package com.caseStudy.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.dao.Roledao;
import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.model.RoleModel;
import com.caseStudy.repository.RoleRepository;

@Repository
public class RoledaoImpl implements Roledao {
	static final Logger logger = Logger.getLogger(RoledaoImpl.class);

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<RoleModel> getAllRoleModels() throws SQLException {
		logger.info("RoledaoImpl--->>getAllRoleModels--->>Start");

		List<RoleModel> roleModelList = roleRepository.findAll();

		logger.info("RoledaoImpl--->>getAllRoleModels--->>End");

		return roleModelList;
	}

	@Override
	public RoleModel getRoleModelById(Long roleId) throws RoleNotFoundException {
		logger.info("RoledaoImpl--->>getRoleModelById--->>Start");

		RoleModel roleModel = roleRepository.findById(roleId)
				.orElseThrow(() -> new RoleNotFoundException("Role not found :: " + roleId));

		logger.info("RoledaoImpl--->>getRoleModelById--->>End");

		return roleModel;
	}

	@Override
	public RoleModel createRoleModel(RoleModel role) {
		logger.info("RoledaoImpl--->>createRoleModel--->>Start");

		RoleModel roleModel = roleRepository.save(role);

		logger.info("RoledaoImpl--->>createRoleModel--->>End");

		return roleModel;
	}

	@Override
	public RoleModel deleteRoleModel(Long roleId) throws RoleNotFoundException {
		logger.info("RoledaoImpl--->>deleteRoleModel--->>Start");

		RoleModel roleModel = roleRepository.findById(roleId)
				.orElseThrow(() -> new RoleNotFoundException("Role not found :: " + roleId));

		roleRepository.delete(roleModel);

		logger.info("RoledaoImpl--->>deleteRoleModel--->>End");

		return roleModel;
	}

	@Override
	public RoleModel updateRoleModel(Long roleId, RoleModel roleDetails) throws RoleNotFoundException {
		logger.info("RoledaoImpl--->>updateRoleModel--->>Start");

		RoleModel roleModel = roleRepository.findById(roleId)
				.orElseThrow(() -> new RoleNotFoundException("Role not found :: " + roleId));

		roleModel.setRole(roleDetails.getRole());

		final RoleModel updatedRoleModel = roleRepository.save(roleModel);

		logger.info("RoledaoImpl--->>updateRoleModel--->>End");

		return updatedRoleModel;
	}

	@Override
	public RoleModel findByRole(String role) {
		logger.info("RoledaoImpl--->>findByRole--->>Start");
		
		RoleModel roleModel = roleRepository.findByRole(role);
		
		logger.info("RoledaoImpl--->>findByRole--->>End");
		return roleModel;
	}
}
