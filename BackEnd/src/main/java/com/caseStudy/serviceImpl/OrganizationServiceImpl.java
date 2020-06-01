package com.caseStudy.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caseStudy.dao.Organizationdao;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.OrganizationModel;
import com.caseStudy.service.OrganizationService;
import com.itextpdf.text.DocumentException;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	private Organizationdao organizationDao;

	@Override
	public List<OrganizationModel> getAllOrganizationModels() {
		return organizationDao.getAllOrganizationModels();
	}

	@Override
	public OrganizationModel createOrganizationModel(OrganizationModel organizationModel) {
		return organizationDao.createOrganizationModel(organizationModel);
	}

	@Override
	public OrganizationModel getOrganizationModelById(Long organizationId) throws OrganizationNotFoundException {
		return organizationDao.getOrganizationModelById(organizationId);
	}

	@Override
	public OrganizationModel deleteOrganizationModel(Long organizationId) throws OrganizationNotFoundException {
		return organizationDao.deleteOrganizationModel(organizationId);
	}

	@Override
	public OrganizationModel updateOrganizationModel(Long organizationId, OrganizationModel organizationDetails)
			throws OrganizationNotFoundException {
		return organizationDao.updateOrganizationModel(organizationId, organizationDetails);
	}

	@Override
	public OrganizationModel getOrganizationModelByName(String organizationName) {
		return organizationDao.getOrganizationModelByName(organizationName);
	}

	@Override
	public File getOrganizationLogo(Long organizationId) throws OrganizationNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException, IOException, DocumentException {
		return organizationDao.getOrganizationLogo(organizationId);
	}
}
