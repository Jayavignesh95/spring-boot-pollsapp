package com.poll.pollsapp.models.audit;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.repository.Modifying;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poll.pollsapp.models.DateAudit;

@MappedSuperclass
@JsonIgnoreProperties(
		value= {"createdBy","updatedBy"},allowGetters = true
		)
public class UserDateAudit extends DateAudit {

	@CreatedBy
	@Column(updatable = false)
	private Long createdBy;
	
	@LastModifiedBy
	private Long updatedBy;

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
}
