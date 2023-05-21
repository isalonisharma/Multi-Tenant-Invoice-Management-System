package com.case_study.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.case_study.bean.CreateClientOrganizationBean;
import com.case_study.entity.Client;
import com.case_study.exception.ClientNotFoundException;
import com.case_study.exception.ClientOrganizationNotFoundException;
import com.case_study.exception.OrganizationNotFoundException;
import com.case_study.exception.UserNotFoundException;
import com.case_study.exception.UserOrganizationNotFoundException;
import com.case_study.model.ClientModel;
import com.case_study.model.GroupModel;
import com.case_study.security.GetCredentials;
import com.case_study.service.ClientOrganizationService;
import com.case_study.service.ClientService;
import com.case_study.service.FileStorageService;
import com.case_study.service.OrganizationService;
import com.case_study.service.UserService;

@RestController
public class ClientController {
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
	@GetMapping("clients")
	ResponseEntity<List<ClientModel>> getActiveClients()
			throws UserOrganizationNotFoundException, UserNotFoundException, OrganizationNotFoundException {
		List<ClientModel> clientModels = new ArrayList<>();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		List<Client> clients = clientService.findByOrganizationId(organizationIdOfCurrentUser);
		for (Client client : clients) {
			clientModels.add(new ClientModel(client));
		}
		return ResponseEntity.ok().body(clientModels);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@GetMapping("clients/{id}")
	ResponseEntity<ClientModel> findById(@PathVariable(value = "id") Long id) throws ClientNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException, ClientOrganizationNotFoundException {
		ClientModel clientModel = new ClientModel();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdOfRequestedClient = clientOrganizationService.findByClientId(id).getOrganization().getId();
		if (organizationIdOfCurrentUser == organizationIdOfRequestedClient) {
			Client client = clientService.findById(id);
			BeanUtils.copyProperties(client, clientModel);
		}
		return ResponseEntity.ok().body(clientModel);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("clients")
	ResponseEntity<ClientModel> createClient(@Valid @RequestBody ClientModel requestClientModel)
			throws BeansException, ClientNotFoundException, OrganizationNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException {
		ClientModel clientModel = new ClientModel();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		BeanUtils.copyProperties(clientService.createClient(requestClientModel, organizationIdOfCurrentUser),
				clientModel);
		return ResponseEntity.ok().body(clientModel);
	}

	@PreAuthorize("hasAnyRole('ROLE_ORGANIZATION_ADMIN')")
	@PostMapping("clients/import/xml")
	ResponseEntity<List<ClientModel>> createClientsFromXML(@RequestParam("file") MultipartFile file)
			throws IOException, UserOrganizationNotFoundException, UserNotFoundException, ClientNotFoundException,
			OrganizationNotFoundException {
		String fileName = fileStorageService.storeFile(file);
		@SuppressWarnings("unused")
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();
		String directory = "uploads/";
		fileName = directory + fileName;
		File xmlfile = new File(fileName);
		GroupModel groupModel = clientService.createClientsFromXML(xmlfile);
		List<Client> clients = groupModel.getClients();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		List<ClientModel> clientModels = new ArrayList<>();
		for (Client client : clients) {
			clientOrganizationService.createClientOrganization(
					new CreateClientOrganizationBean(client.getId(), organizationIdOfCurrentUser));
			clientModels.add(new ClientModel(client));
		}
		return ResponseEntity.ok().body(clientModels);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@DeleteMapping("clients/{id}")
	ResponseEntity<ClientModel> deleteById(@PathVariable(value = "id") Long id)
			throws ClientNotFoundException, BeansException, ClientOrganizationNotFoundException,
			UserOrganizationNotFoundException, UserNotFoundException {
		ClientModel clientModel = new ClientModel();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdOfRequestedClient = clientOrganizationService.findByClientId(id).getOrganization().getId();
		if (organizationIdOfCurrentUser == organizationIdOfRequestedClient) {
			Client client = clientService.deleteById(id);
			BeanUtils.copyProperties(client, clientModel);
		}
		return ResponseEntity.ok().body(clientModel);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZATION_ADMIN')")
	@PutMapping("clients/{id}")
	ResponseEntity<ClientModel> updateById(@PathVariable(value = "id") Long clientId,
			@Valid @RequestBody ClientModel requestClientModel)
			throws ClientNotFoundException, UserOrganizationNotFoundException, UserNotFoundException, BeansException,
			ClientOrganizationNotFoundException {
		ClientModel clientModel = new ClientModel();
		long organizationIdOfCurrentUser = organizationService
				.findByName(userService.findOrganizationByUsername(GetCredentials.checkUserName())).getId();
		long organizationIdOfRequestedClient = clientOrganizationService.findByClientId(clientId).getOrganization()
				.getId();
		if (organizationIdOfCurrentUser == organizationIdOfRequestedClient) {
			Client client = clientService.updateById(clientId, requestClientModel);
			BeanUtils.copyProperties(client, clientModel);
		}
		return ResponseEntity.ok().body(clientModel);
	}
}