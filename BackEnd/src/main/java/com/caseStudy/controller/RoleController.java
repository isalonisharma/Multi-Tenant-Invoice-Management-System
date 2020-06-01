package com.caseStudy.controller;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.model.RoleModel;
import com.caseStudy.service.RoleService;

@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
@RestController
public class RoleController {
	static final Logger logger = Logger.getLogger(RoleController.class);

	public RoleController() {
	}

	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;

	@GetMapping("/roles")
	List<RoleModel> getAllRoleModels() throws SQLException {
		logger.info("RoleController--->>getAllRoleModels--->>Start");

		List<RoleModel> RoleModelList = roleService.getAllRoleModels();

		logger.info("RoleController--->>getAllRoleModels--->>End");

		return RoleModelList;
	}

	@GetMapping("/role/{id}")
	ResponseEntity<RoleModel> getRoleModelById(@PathVariable(value = "id") Long roleId)
			throws RoleNotFoundException {
		logger.info("RoleController--->>getRoleModelById--->>Start");

		RoleModel roleModel = roleService.getRoleModelById(roleId);

		logger.info("RoleController--->>getRoleModelById--->>Ended");

		return ResponseEntity.ok().body(roleModel);
	}

	@PostMapping("/createrole")
	RoleModel createRole(@Valid @RequestBody RoleModel role) {
		logger.info("RoleController--->>createRoleModel--->>Start");

		RoleModel roleModel = roleService.createRoleModel(role);

		logger.info("RoleController--->>createRoleModel--->>Ended");

		return roleModel;
	}

	@GetMapping("/deleterole/{id}")
	ResponseEntity<RoleModel> deleteRoleModel(@PathVariable(value = "id") Long roleId)
			throws RoleNotFoundException {
		logger.info("RoleController--->>deleteRoleModel--->>Start");

		RoleModel roleModel = roleService.deleteRoleModel(roleId);

		logger.info("RoleController--->>deleteRoleModel--->>Ended");

		return ResponseEntity.ok().body(roleModel);
	}

	@PostMapping("/updaterole/{id}")
	ResponseEntity<RoleModel> updateRoleModel(@PathVariable(value = "id") Long roleId,
			@Valid @RequestBody RoleModel roleDetails) throws RoleNotFoundException {
		logger.info("RoleController--->>updateRoleModel--->>Start");

		RoleModel roleModel = roleService.updateRoleModel(roleId, roleDetails);

		logger.info("RoleController--->>updateRoleModel--->>Ended");

		return ResponseEntity.ok().body(roleModel);
	}
}
