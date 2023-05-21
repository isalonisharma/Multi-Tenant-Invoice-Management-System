package com.case_study.controller;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.case_study.entity.Role;
import com.case_study.exception.RoleNotFoundException;
import com.case_study.model.RoleModel;
import com.case_study.service.RoleService;

@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
@RestController
public class RoleController {
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;

	@GetMapping("roles")
	ResponseEntity<List<Role>> findAll() throws SQLException {
		return ResponseEntity.ok().body(roleService.findAll());
	}

	@GetMapping("roles/{id}")
	ResponseEntity<Role> findById(@PathVariable(value = "id") Long roleId) throws RoleNotFoundException {
		return ResponseEntity.ok().body(roleService.findById(roleId));
	}

	@PostMapping("roles")
	ResponseEntity<RoleModel> createRole(@Valid @RequestBody RoleModel roleModel) {
		return ResponseEntity.ok().body(new RoleModel(roleService.createRole(roleModel)));
	}

	@DeleteMapping("roles/{id}")
	ResponseEntity<Role> deleteById(@PathVariable(value = "id") Long roleId) throws RoleNotFoundException {
		return ResponseEntity.ok().body(roleService.deleteById(roleId));
	}

	@PutMapping("roles/{id}")
	ResponseEntity<RoleModel> updateById(@PathVariable(value = "id") Long roleId,
			@Valid @RequestBody RoleModel roleModel) throws RoleNotFoundException {
		return ResponseEntity.ok().body(new RoleModel(roleService.updateById(roleId, roleModel)));
	}
}