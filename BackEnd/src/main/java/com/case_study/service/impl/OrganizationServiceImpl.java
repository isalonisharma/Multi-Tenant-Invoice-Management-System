package com.case_study.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.case_study.entity.Organization;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.OrganizationModel;
import com.case_study.repository.OrganizationRepository;
import com.case_study.service.FileStorageService;
import com.case_study.service.OrganizationService;
import com.case_study.utility.CommonConstants;
import com.itextpdf.text.DocumentException;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private FileStorageService fileStorageService;

	@Override
	public Organization createOrganization(OrganizationModel organizationModel) {
		return organizationRepository.save(new Organization(organizationModel));
	}

	@Override
	public Organization deleteById(Long id) throws OrganizationNotFoundException {
		Organization organization = organizationRepository.findById(id)
				.orElseThrow(() -> new OrganizationNotFoundException(CommonConstants.ORGANIZATION_NOT_FOUND + id));
		organization.setLocked(true);
		return organizationRepository.save(organization);
	}

	@Override
	public Organization updateById(Long id, OrganizationModel organizationModel) throws OrganizationNotFoundException {
		Organization organization = organizationRepository.findById(id)
				.orElseThrow(() -> new OrganizationNotFoundException(CommonConstants.ORGANIZATION_NOT_FOUND + id));
		organization.setName(organizationModel.getName());
		organization.setLogo(organizationModel.getLogo());
		organization.setCurrency(organizationModel.getCurrency());
		organization.setDateFormat(organizationModel.getDateFormat());
		organization.setEmailId(organizationModel.getEmailId());
		organization.setReminderEMail(organizationModel.isReminderEMail());
		organization.setThankYouEMail(organizationModel.isThankYouEMail());
		return organizationRepository.save(organization);
	}

	@Override
	public List<Organization> getActiveOrganizations() {
		List<Organization> organizations = organizationRepository.findAll();
		organizations.removeIf(Organization::isLocked);
		return organizations;
	}

	@Override
	public Organization findById(Long id) throws OrganizationNotFoundException {
		Organization organization = organizationRepository.findById(id)
				.orElseThrow(() -> new OrganizationNotFoundException(CommonConstants.ORGANIZATION_NOT_FOUND + id));
		if (organization.isLocked()) {
			throw new OrganizationNotFoundException(CommonConstants.ORGANIZATION_NOT_FOUND + id);
		}
		return organization;
	}

	@Override
	public Organization findByName(String name) {
		return organizationRepository.findByName(name);
	}

	@Override
	public File getLogoById(Long id) throws OrganizationNotFoundException, UserOrganizationNotFoundException,
			UserNotFoundException, IOException, DocumentException {
		String logoPath = null;
		File logo = null;
		Organization organization = organizationRepository.findById(id)
				.orElseThrow(() -> new OrganizationNotFoundException(CommonConstants.ORGANIZATION_NOT_FOUND + id));
		logoPath = organization.getLogo();
		try {
			logo = fileStorageService.loadFileAsResource(logoPath).getFile();
		} catch (Exception exception) {
			logoPath = "defaultLogo.png";
			logo = fileStorageService.loadFileAsResource(logoPath).getFile();
		}
		return logo;
	}
}