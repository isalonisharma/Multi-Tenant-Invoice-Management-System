package com.case_study.model;

import java.util.List;

import com.case_study.entity.Client;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "group")
public final class GroupModel {
	@JacksonXmlProperty(localName = "client")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Client> clients;

	public GroupModel() {
		super();
	}

	public GroupModel(List<Client> clients) {
		super();
		this.clients = clients;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	@Override
	public String toString() {
		return "GroupModel [clients=" + clients + "]";
	}
}