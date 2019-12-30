package com.poll.pollsapp.models;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "VerificationToken")
public class VerificationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name="token")
	private String token;

	
	@OneToOne(targetEntity = User.class,fetch =FetchType.EAGER)
	@JoinColumn(name="user_id",nullable = false)
	private User user;
	
	@Column(name="created_date")
	private Date createdDate;


	@Column(name="expiry_date")
	private Date expiryDate;
	
	public VerificationToken(final String token,final User user)
	{
		super();
		Calendar calendar = Calendar.getInstance();
		this.token =token;
		this.user = user;
		this.createdDate = new Date(calendar.getTime().getTime());
		this.expiryDate = calculateExpirationDate( 1440);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	private Date calculateExpirationDate(int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Timestamp(calendar.getTime().getTime()));
		calendar.add(Calendar.MINUTE, i);
		return new Date(calendar.getTime().getTime());
	}
	
}
