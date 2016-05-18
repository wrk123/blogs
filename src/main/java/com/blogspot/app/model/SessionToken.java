package com.blogspot.app.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="sessionToken")
public class SessionToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="session_id")
	private Long sessionId;
	
	@Column(name="user_id")
	private Long userId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedTime;
	
	private Integer authToken;
	
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id",insertable=false, updatable=false)
	private User userObj;
	
	
	public SessionToken() {
		// TODO Auto-generated constructor stub
	}

	public SessionToken(Date lastModifiedTime, Integer authToken, User userObj) {
		super();
		this.lastModifiedTime = lastModifiedTime;
		this.authToken = authToken;
		this.userObj = userObj;
	}
	
	

	public SessionToken(Long userId) {
		super();
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSessionId() {
		return sessionId;
	}


	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}


	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}


	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}


	public Integer getAuthToken() {
		return authToken;
	}


	public void setAuthToken(Integer authToken) {
		this.authToken = authToken;
	}


	public User getUserObj() {
		return userObj;
	}


	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}
	
	@Override
	public String toString() {
		return "SessionToken [sessionId=" + sessionId + ", userId=" + userId + ", lastModifiedTime=" + lastModifiedTime
				+ ", authToken=" + authToken + ", user=" + userObj + "]";
	}

	
	
	
}
