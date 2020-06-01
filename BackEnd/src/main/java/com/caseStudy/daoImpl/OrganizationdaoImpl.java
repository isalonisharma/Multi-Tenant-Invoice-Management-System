package com.caseStudy.daoImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caseStudy.dao.Organizationdao;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.OrganizationModel;
import com.caseStudy.repository.OrganizationRepository;
import com.caseStudy.service.FileStorageService;
import com.itextpdf.text.DocumentException;

@Repository
public class OrganizationdaoImpl implements Organizationdao {
	static final Logger logger = Logger.getLogger(OrganizationdaoImpl.class);

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private FileStorageService fileStorageService;

	@Override
	public List<OrganizationModel> getAllOrganizationModels() {
		logger.info("OrganizationdaoImpl--->>getAllOrganizationModels--->>Start");

		List<OrganizationModel> organizationModelList = organizationRepository.findAll();

		organizationModelList
				.removeIf((OrganizationModel organizationModel) -> organizationModel.isOrganizationIsLocked());

		logger.info("OrganizationdaoImpl--->>getAllOrganizationModels--->>End");

		return organizationModelList;
	}

	@Override
	public OrganizationModel createOrganizationModel(OrganizationModel organization) {
		logger.info("OrganizationdaoImpl--->>createOrganizationModel--->>Start");

		OrganizationModel organizationModel = organizationRepository.save(organization);

		logger.info("OrganizationdaoImpl--->>createOrganizationModel--->>End");

		return organizationModel;
	}

	@Override
	public OrganizationModel getOrganizationModelById(Long organizationId) throws OrganizationNotFoundException {
		logger.info("OrganizationdaoImpl--->>getOrganizationModelById--->>Start");

		OrganizationModel organizationModel = organizationRepository.findById(organizationId)
				.orElseThrow(() -> new OrganizationNotFoundException("Organization not found :: " + organizationId));

		if (organizationModel.isOrganizationIsLocked()) {
			throw new OrganizationNotFoundException("Organization not available :: " + organizationId);
		}

		logger.info("OrganizationdaoImpl--->>getOrganizationModelById--->>End");

		return organizationModel;
	}

	@Override
	public OrganizationModel deleteOrganizationModel(Long organizationId) throws OrganizationNotFoundException {
		logger.info("OrganizationdaoImpl--->>deleteOrganizationModel--->>Start");

		OrganizationModel organizationModel = organizationRepository.findById(organizationId)
				.orElseThrow(() -> new OrganizationNotFoundException("Organization not found :: " + organizationId));

		organizationModel.setOrganizationIsLocked(true);

		final OrganizationModel deletedOrganizationModel = organizationRepository.save(organizationModel);

		logger.info("OrganizationdaoImpl--->>deleteOrganizationModel--->>End");

		return deletedOrganizationModel;
	}

	@Override
	public OrganizationModel updateOrganizationModel(Long organizationId, OrganizationModel organizationDetails)
			throws OrganizationNotFoundException {
		logger.info("OrganizationdaoImpl--->>updateOrganizationModel--->>Start");

		OrganizationModel organizationModel = organizationRepository.findById(organizationId)
				.orElseThrow(() -> new OrganizationNotFoundException("Organization not found :: " + organizationId));

		organizationModel.setOrganizationName(organizationDetails.getOrganizationName());
		organizationModel.setOrganizationLogo(organizationDetails.getOrganizationLogo());
		organizationModel.setOrganizationCurrency(organizationDetails.getOrganizationCurrency());
		organizationModel.setOrganizationDate(organizationDetails.getOrganizationDate());
		organizationModel.setOrganizationEmailId(organizationDetails.getOrganizationEmailId());
		organizationModel.setOrganizationReminderMail(organizationDetails.isOrganizationReminderMail());
		organizationModel.setOrganizationThankYouMail(organizationDetails.isOrganizationThankYouMail());

		final OrganizationModel updatedOrganizationModel = organizationRepository.save(organizationModel);

		logger.info("OrganizationdaoImpl--->>updateOrganizationModel--->>End");

		return updatedOrganizationModel;
	}

	@Override
	public OrganizationModel getOrganizationModelByName(String organizationName) {
		logger.info("OrganizationdaoImpl--->>getOrganizationModelByName--->>Start");

		OrganizationModel organizationModel = organizationRepository.findByorganizationName(organizationName);

		logger.info("OrganizationdaoImpl--->>getOrganizationModelByName--->>End");

		return organizationModel;
	}

	@Override
	public File getOrganizationLogo(Long organizationId) throws OrganizationNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException, IOException, DocumentException {

		logger.info("OrganizationdaoImpl--->>getOrganizationLogo--->>Start");

		logger.info("OrganizationdaoImpl--->>getOrganizationLogo--->>organizationId--->> " + organizationId);

		String organizationImagePath = null;

		File organizationImage = null;

		OrganizationModel organizationModel = organizationRepository.findById(organizationId)
				.orElseThrow(() -> new OrganizationNotFoundException("Organization not found :: " + organizationId));

		organizationImagePath = organizationModel.getOrganizationLogo();

		logger.info(
				"OrganizationdaoImpl--->>getOrganizationLogo--->>organizationImagePath--->> " + organizationImagePath);

		try {

			organizationImage = fileStorageService.loadFileAsResource(organizationImagePath).getFile();

			logger.info("OrganizationdaoImpl--->>getOrganizationLogo--->>organizationImage--->> " + organizationImage);

		} catch (Exception exception) {

			organizationImagePath = "defaultLogo.png";

			organizationImage = fileStorageService.loadFileAsResource(organizationImagePath).getFile();

			logger.info("OrganizationdaoImpl--->>getOrganizationLogo--->>organizationImage--->> " + organizationImage);
		}

		logger.info("OrganizationdaoImpl--->>getOrganizationLogo--->>End");

		return organizationImage;
	}
}
