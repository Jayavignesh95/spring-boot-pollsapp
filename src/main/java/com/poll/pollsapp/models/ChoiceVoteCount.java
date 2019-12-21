package com.poll.pollsapp.models;

public class ChoiceVoteCount {
	private Long ChoiceId;
	private Long voteCount;

	public Long getChoiceId() {
		return ChoiceId;
	}

	public void setChoiceId(Long choiceId) {
		ChoiceId = choiceId;
	}

	public Long getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Long voteCount) {
		this.voteCount = voteCount;
	}

	public ChoiceVoteCount(Long choiceId, Long voteCount) {
		super();
		ChoiceId = choiceId;
		this.voteCount = voteCount;
	}
}
