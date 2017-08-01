package lbsn.twitter_orm_app.domain;

import java.util.List;

public class AjaxResponse {
	private String keyword;
	private List<TweetEntity> tweets;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public List<TweetEntity> getTweets() {
		return tweets;
	}
	public void setTweets(List<TweetEntity> tweets) {
		this.tweets = tweets;
	}
	
}
