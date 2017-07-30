package lbsn.twitter_orm_app.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.social.twitter.api.Tweet;

@Document(collection="tweets")
public class TweetEntity{
	private Tweet tweet;
	private String sentiment;
	
	public TweetEntity(){};
	
	public TweetEntity(Tweet tweet){
		this.tweet = tweet;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	
	
}
