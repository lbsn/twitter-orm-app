package lbsn.twitter_orm_app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.social.twitter.api.Tweet;

@Document(collection="tweets")
public class TweetEntity{
	@Id
	private String id;
	private String text;
	private String sentiment;
	private String repDimension;
	private String keyword;
	
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
	
}
