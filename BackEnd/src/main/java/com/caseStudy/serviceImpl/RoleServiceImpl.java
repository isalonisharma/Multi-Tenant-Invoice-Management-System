package com.caseStudy.serviceImpl;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.dao.Roledao;
import com.caseStudy.exception.RoleNotFoundException;
import com.caseStudy.model.RoleModel;
import com.caseStudy.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private Roledao roleDao;

	@Override
	public List<RoleModel> getAllRoleModels() throws SQLException {
		return roleDao.getAllRoleModels();
	}

	@Override
	public RoleModel getRoleModelById(Long roleId) throws RoleNotFoundException {
		return roleDao.getRoleModelById(roleId);
	}

	@Override
	public RoleModel createRoleModel(RoleModel roleModel) {
		return roleDao.createRoleModel(roleModel);
	}

	@Override
	public RoleModel deleteRoleModel(Long roleId) throws RoleNotFoundException {
		return roleDao.deleteRoleModel(roleId);
	}

	@Override
	public RoleModel updateRoleModel(Long roleId, RoleModel roleDetails) throws RoleNotFoundException {
		return roleDao.updateRoleModel(roleId, roleDetails);
	}

	@Override
	public RoleModel findByRole(String role) {
		return roleDao.findByRole(role);
	}
}
