package com.poll.pollsapp.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;


@Entity
@Table(name="polls")
public class Poll extends UserDateAudit{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 140)
	private String question;
	
	@NotNull
	private Instant expirationDateTime;
	

	/*
	 * @OneToMany( mappedBy = "poll", cascade = CascadeType.ALL, fetch =
	 * FetchType.EAGER, orphanRemoval = true )
	 * 
	 * @Size(min = 2, max = 6)
	 * 
	 * @Fetch(FetchMode.SELECT)
	 * 
	 * @BatchSize(size = 30)
	 * 
	 * @NotBlank private ArrayList<Choice> choices = new ArrayList<>();
	 */

	@OneToMany(
            mappedBy = "poll",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Size(min = 2, max = 6)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<Choice> choices = new ArrayList<>();

    public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Instant getExpirationDateTime() {
		return expirationDateTime;
	}

	public void setExpirationDateTime(Instant expirationDateTime) {
		this.expirationDateTime = expirationDateTime;
	}
	
    public void addChoice(Choice choice) {
        choices.add(choice);
        choice.setPoll(this);
    }

    public void removeChoice(Choice choice) {
        choices.remove(choice);
        choice.setPoll(null);
    }


}
