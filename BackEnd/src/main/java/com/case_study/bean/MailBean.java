package com.case_study.bean;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

/**
 * Class: MailBean
 * 
 * Use: It is used to create a simple mail message including from, to, cc,
 * subject and text messages.
 * 
 * @author saloni.sharma
 *
 */
public class MailBean {
	private String mailFrom;
	private String mailTo;
	private String mailCc;
	private String mailBcc;
	private String mailSubject;
	private String mailContent;
	private String contentType;
	private List<Object> attachments;
	private String attachmentName;
	private ByteArrayInputStream attachment;

	public MailBean() {
		contentType = "text/plain";
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getMailBcc() {
		return mailBcc;
	}

	public void setMailBcc(String mailBcc) {
		this.mailBcc = mailBcc;
	}

	public String getMailCc() {
		return mailCc;
	}

	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public Date getMailSendDate() {
		return new Date();
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public List<Object> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Object> attachments) {
		this.attachments = attachments;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public ByteArrayInputStream getAttachment() {
		return attachment;
	}

	public void setAttachment(ByteArrayInputStream attachment) {
		this.attachment = attachment;
	}
}