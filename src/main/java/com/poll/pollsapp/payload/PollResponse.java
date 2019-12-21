package com.poll.pollsapp.payload;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class PollResponse {

	private Long id;
	private String question;
	private UserSummary createdBy;
	private List<ChoiceResponse> choices;
	private Instant creationDateTime;
	private Instant expirationDateTime;
    private Boolean isExpired;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public UserSummary getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserSummary createdBy) {
		this.createdBy = createdBy;
	}
	public List<ChoiceResponse> getChoices() {
		return choices;
	}
	public void setChoices(List<ChoiceResponse> choices) {
		this.choices = choices;
	}
	public Instant getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(Instant creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	public Instant getExpirationDateTime() {
		return expirationDateTime;
	}
	public void setExpirationDateTime(Instant expirationDateTime) {
		this.expirationDateTime = expirationDateTime;
	}
	public Boolean getExpired() {
		return isExpired;
	}
	public void setExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}
	public Long getSelectedChoice() {
		return selectedChoice;
	}
	public void setSelectedChoice(Long selectedChoice) {
		this.selectedChoice = selectedChoice;
	}
	public Long getTotalVotes() {
		return totalVotes;
	}
	public void setTotalVotes(Long totalVotes) {
		this.totalVotes = totalVotes;
	}
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long selectedChoice;
	private Long totalVotes;
}
