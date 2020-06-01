package com.caseStudy.model;

import java.util.List;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "group")
public final class GroupModel {
	@JacksonXmlProperty(localName = "client")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ClientModel> clientModel;

	public GroupModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GroupModel(List<ClientModel> clientModel) {
		super();
		this.clientModel = clientModel;
	}

	public List<ClientModel> getClientModel() {
		return clientModel;
	}

	public void setClientModel(List<ClientModel> clientModel) {
		this.clientModel = clientModel;
	}

	@Override
	public String toString() {
		return "GroupModel [userModel=" + clientModel + "]";
	}
}