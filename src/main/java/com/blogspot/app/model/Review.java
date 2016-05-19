package com.blogspot.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name="review_blogs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="review_id")
	private Long reviewId;
	
	private String comments;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_time")
	private Date creationTime;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name="blog_id")
	private Long blogId;

	@ManyToOne
	@JoinColumn(name="blog_id", updatable= false, insertable=false)
	//@JsonManagedReference
	private Blog blog;
	
	public Review() {
	}

	public Review( String comments, Date creationTime, Long userId, Long blogId) {
		super();
		this.comments = comments;
		this.creationTime = creationTime;
		this.userId = userId;
		this.blogId = blogId;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	@JsonProperty(value = "review")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "reviewId")
	@JsonIdentityReference(alwaysAsId = true) 
	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", comments=" + comments + ", creationTime=" + creationTime
				+ ", userId=" + userId + ", blog=" + blogId + "]";
	}

	
	
}
