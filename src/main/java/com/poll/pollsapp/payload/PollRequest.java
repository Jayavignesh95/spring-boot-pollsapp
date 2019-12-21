package com.poll.pollsapp.payload;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.poll.pollsapp.models.Choice;

public class PollRequest {

	@NotBlank
	@Size(max =140)
	private String question;
	
	@NotNull
	@Size(min =2,max=6)
	@Valid
	private List<Choice> choices;
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public PollLength getPollLength() {
		return pollLength;
	}

	public void setPollLength(PollLength pollLength) {
		this.pollLength = pollLength;
	}

	@NotNull
	@Valid
	private PollLength pollLength;
}
