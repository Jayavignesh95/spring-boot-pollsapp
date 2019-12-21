package com.poll.pollsapp.payload;

import java.time.Instant;

public class UserProfile {

	private String username;
	private String name;
	private Long id;
	private Instant joinedAt;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserProfile(String username, String name, Long id, Instant joinedAt, Long voteCount, Long pollCount) {
		super();
		this.username = username;
		this.name = name;
		this.id = id;
		this.joinedAt = joinedAt;
		this.voteCount = voteCount;
		this.pollCount = pollCount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getJoinedAt() {
		return joinedAt;
	}

	public void setJoinedAt(Instant joinedAt) {
		this.joinedAt = joinedAt;
	}

	public Long getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Long voteCount) {
		this.voteCount = voteCount;
	}

	public Long getPollCount() {
		return pollCount;
	}

	public void setPollCount(Long pollCount) {
		this.pollCount = pollCount;
	}

	private Long voteCount;
	private Long pollCount;

}
