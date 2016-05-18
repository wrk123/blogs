package com.blogspot.app.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*@Entity
@Table(name="user_credit")
public class Credit {

	
	@Id
	@Column(name="credit_id")
	private Long creditId;
	
	private Integer userCredit;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;	
	
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="blog_id")
	private Blog blogs;
	
	public Credit() {
		// TODO Auto-generated constructor stub
	}

	public Long getCreditId() {
		return creditId;
	}

	public void setId(Long creditId) {
		this.creditId = creditId;
	}

	public Integer getUserCredit() {
		return userCredit;
	}

	public void setUserCredit(Integer userCredit) {
		this.userCredit = userCredit;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Blog getBlogs() {
		return blogs;
	}

	public void setBlogs(Blog blogs) {
		this.blogs = blogs;
	}

	@Override
	public String toString() {
		return "Credit [creditId=" + creditId + ", userCredit=" + userCredit + ", creationTime=" + creationTime
				+ ", blogs=" + blogs + "]";
	}	
}
*/