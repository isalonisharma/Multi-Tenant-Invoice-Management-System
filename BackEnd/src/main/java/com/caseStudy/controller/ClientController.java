package com.caseStudy.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.caseStudy.bean.CreateClientOrganizationBean;
import com.caseStudy.dto.ClientDTO;
import com.caseStudy.exception.ClientNotFoundException;
import com.caseStudy.exception.ClientOrganizationNotFoundException;
import com.caseStudy.exception.OrganizationNotFoundException;
import com.caseStudy.exception.UserNotFoundException;
import com.caseStudy.exception.UserOrganizationNotFoundException;
import com.caseStudy.model.ClientModel;
import com.caseStudy.model.GroupModel;
import com.caseStudy.security.GetCredentials;
import com.caseStudy.service.ClientOrganizationService;
import com.caseStudy.service.ClientService;
import com.caseStudy.service.FileStorageService;
import com.caseStudy.service.OrganizationService;
import com.caseStudy.service.UserService;

@RestController
public class ClientController {
	
	static final Logger logger = Logger.getLogger(ClientController.class);

	@Autowired
	@Qualifier("fileStorageService")
	private FileStorageService fileStorageService;

	@Autowired
	@Qualifier("clientService")
	private ClientService clientService;

	@Autowired
	@Qualifier("clientOrganizationService")
	private ClientOrganizationService clientOrganizationService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("organizationService")
	private OrganizationService organizationService;

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/clients")
	List<ClientDTO> getAllClientModelByOrganizationId()
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException {

		logger.info("ClientController--->>getAllClientModelByOrganizationId--->>Start");
		
		List<ClientDTO> clientDTOList = new ArrayList<ClientDTO>();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		List<ClientModel> clientModelList = clientService
				.getAllClientModelByOrganizationId(organizationIdOfCurrentUser);

		for (ClientModel clientModel : clientModelList) {
			clientDTOList.add(new ClientDTO(clientModel));
		}

		logger.info("ClientController--->>getAllClientModelByOrganizationId--->>Ended");

		return clientDTOList;
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/client/{id}")
	ResponseEntity<ClientDTO> getClientModelById(@PathVariable(value = "id") Long clientId)
			throws ClientNotFoundException, UserOrganizationNotFoundException, UserNotFoundException,
			ClientOrganizationNotFoundException {

		logger.info("ClientController--->>getClientModelById--->>Start");

		ClientDTO clientDTO = new ClientDTO();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdOfRequestedClient = clientOrganizationService.getClientOrganizationModelByClientId(clientId)
				.getOrganizationModel().getOrganizationId();

		if (organizationIdOfCurrentUser == organizationIdOfRequestedClient) {
			
			ClientModel clientModel = clientService.getClientModelById(clientId);

			BeanUtils.copyProperties(clientModel, clientDTO);
		}

		logger.info("ClientController--->>getClientModelById--->>Ended");

		return ResponseEntity.ok().body(clientDTO);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("/createclient")
	ResponseEntity<ClientDTO> createClientModel(@Valid @RequestBody ClientModel client)
			throws BeansException, ClientNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException {

		logger.info("ClientController--->>createClientModel--->>Start");

		ClientDTO clientDTO = new ClientDTO();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		BeanUtils.copyProperties(clientService.createClientModel(client, organizationIdOfCurrentUser), clientDTO);

		logger.info("ClientController--->>createClientModel--->>Ended");

		return ResponseEntity.ok().body(clientDTO);
	}

	@PreAuthorize("hasAnyRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("/createclients/")
	ResponseEntity<List<ClientDTO>> createClientModelsFromXML(@RequestParam("file") MultipartFile file)
			throws IOException, UserOrganizationNotFoundException, UserNotFoundException, ClientNotFoundException,
			OrganizationNotFoundException {
		logger.info("ClientController--->>createClientModelsFromXML--->>Start");

		String fileName = fileStorageService.storeFile(file);

		@SuppressWarnings("unused")
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		String directory = "uploads/";

		fileName = directory + fileName;
		File xmlfile = new File(fileName);

		GroupModel groupModel = clientService.createClientModelsFromXML(xmlfile);
		
		List<ClientModel> clientModelList = groupModel.getClientModel();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		List<ClientDTO> clientDTOList = new ArrayList<ClientDTO>();

		for (ClientModel clientModel : clientModelList) {
			
			clientOrganizationService.createClientOrganizationModel(
					new CreateClientOrganizationBean(clientModel.getClientId(), organizationIdOfCurrentUser));

			clientDTOList.add(new ClientDTO(clientModel));
		}

		logger.info("ClientController--->>createClientModelsFromXML--->>Ended");

		return ResponseEntity.ok().body(clientDTOList);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("/deleteclient/{id}")
	ResponseEntity<ClientDTO> deleteClientModel(@PathVariable(value = "id") Long clientId)
			throws ClientNotFoundException, BeansException, ClientOrganizationNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException {

		logger.info("ClientController--->>deleteClientModel--->>Start");

		ClientDTO clientDTO = new ClientDTO();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdOfRequestedClient = clientOrganizationService.getClientOrganizationModelByClientId(clientId)
				.getOrganizationModel().getOrganizationId();

		if (organizationIdOfCurrentUser == organizationIdOfRequestedClient) {
			
			ClientModel clientModel = clientService.deleteClientModel(clientId);

			BeanUtils.copyProperties(clientModel, clientDTO);
		}
		logger.info("ClientController--->>deleteClientModel--->>Ended");

		return ResponseEntity.ok().body(clientDTO);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("/updateclient/{id}")
	ResponseEntity<ClientDTO> updateClientModel(@PathVariable(value = "id") Long clientId,
			@Valid @RequestBody ClientModel clientDetails)
			throws ClientNotFoundException, UserOrganizationNotFoundException, UserNotFoundException, BeansException,
			ClientOrganizationNotFoundException {
		
		logger.info("ClientController--->>updateClientModel--->>Start");

		ClientDTO clientDTO = new ClientDTO();

		long organizationIdOfCurrentUser = organizationService
				.getOrganizationModelByName(userService.findOrganizationByUsername(GetCredentials.checkUserName()))
				.getOrganizationId();

		long organizationIdOfRequestedClient = clientOrganizationService.getClientOrganizationModelByClientId(clientId)
				.getOrganizationModel().getOrganizationId();

		if (organizationIdOfCurrentUser == organizationIdOfRequestedClient) {
			
			ClientModel clientModel = clientService.updateClientModel(clientId, clientDetails);

			BeanUtils.copyProperties(clientModel, clientDTO);
		}

		logger.info("ClientController--->>updateClientModel--->>Ended");

		return ResponseEntity.ok().body(clientDTO);
	}
}