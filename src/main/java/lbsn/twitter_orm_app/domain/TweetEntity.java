package lbsn.twitter_orm_app.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tweets")
public class TweetEntity{
	@Id
	private String id;
	private Date createdAt;
	private String text;
	private String sentiment;
	private String repDimension;
	private String keyword;
	private Long userId;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	public String getId() {
		return id;
	}
	public String getRepDimension() {
		return repDimension;
	}
	public void setRepDimension(String repDimension) {
		this.repDimension = repDimension;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
