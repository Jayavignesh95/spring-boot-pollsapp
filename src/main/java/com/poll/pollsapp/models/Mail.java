package com.poll.pollsapp.models;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Email")
public class Mail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Mail() {
		super();
		this.bolbId ="EMPTY";
		this.createdAt = Instant.now().getEpochSecond();
		this.emailType ="INSTANT";
	}

	private String toEmail;
	
	private String subject;
	
	private String text;
	
	private String emailType;
	
	private Long createdAt;
	
	private boolean isInstant =false;
	
	private boolean isScheduled = false;
	
	private Long scheduledTo;
	
	private boolean isAttachment =false;
	
	private String bolbId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTo() {
		return toEmail;
	}

	public void setTo(String to) {
		this.toEmail = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isInstant() {
		return isInstant;
	}

	public void setInstant(boolean isInstant) {
		this.isInstant = isInstant;
	}

	public boolean isScheduled() {
		return isScheduled;
	}

	public void setScheduled(boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	public Long getScheduledTo() {
		return scheduledTo;
	}

	public void setScheduledTo(Long scheduledTo) {
		this.scheduledTo = scheduledTo;
	}

	public boolean isAttachment() {
		return isAttachment;
	}

	public void setAttachment(boolean isAttachment) {
		this.isAttachment = isAttachment;
	}

	public String getBolbId() {
		return bolbId;
	}

	public void setBolbId(String bolbId) {
		this.bolbId = bolbId;
	}
}
