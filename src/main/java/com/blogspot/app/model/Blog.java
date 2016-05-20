package com.blogspot.app.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "blog_details")
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "blog_id")
	private Long blogId;

	private String blogTitle;

	private String blogContent;

	@Column(name = "user_id")
	private Long userId;

	private boolean draft;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_time")
	private Date creationTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "publish_time")
	private Date publishTime;

	private Integer blogLikes;

	private Integer blogDislikes;

	private boolean isActive;

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)	
	private User user;
	
	@OneToMany(mappedBy = "blogId")
	private List<Review> review;

	public  Blog() {
		// TODO Auto-generated constructor stub
	}

	public Blog(String blogTitle, String blogContent, Long userId, boolean draft, Date creationTime, Date publishTime,
			Integer blogLikes, Integer blogDislikes, boolean isActive) {
		super();
		this.blogTitle = blogTitle;
		this.blogContent = blogContent;
		this.draft = draft;
		this.userId = userId;
		this.creationTime = creationTime;
		this.publishTime = publishTime;
		this.blogLikes = blogLikes;
		this.blogDislikes = blogDislikes;
		this.isActive = isActive;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getBlogContent() {
		return blogContent;
	}

	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}
	
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean getDraft() {
		return draft;
	}

	public void setDraft(boolean draft) {
		this.draft = draft;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getBlogLikes() {
		return blogLikes;
	}

	public void setBlogLikes(Integer blogLikes) {
		this.blogLikes = blogLikes;
	}

	public Integer getBlogDislikes() {
		return blogDislikes;
	}

	public void setBlogDislikes(Integer blogDislikes) {
		this.blogDislikes = blogDislikes;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	@Override
	public String toString() {
		return "Blog [blogId=" + blogId + ", blogTitle=" + blogTitle + ", blogContent=" + blogContent + ", userId="
				+ userId + ", draft=" + draft + ", creationTime=" + creationTime + ", publishTime=" + publishTime
				+ ", blogLikes=" + blogLikes + ", blogDislikes=" + blogDislikes + ", isActive=" + isActive + ", user="
				+ user + ", review=" + review + "]";
	}

}
