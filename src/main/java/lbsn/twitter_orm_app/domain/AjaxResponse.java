package lbsn.twitter_orm_app.domain;

import java.util.List;
import java.util.Map;

public class AjaxResponse {
	private String keyword;
	private List<TweetEntity> tweets;
	private Map<String, Integer> clusters;
	
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
	public Map<String, Integer> getClusters() {
		return clusters;
	}
	public void setClusters(Map<String, Integer> clusters) {
		this.clusters = clusters;
	}
	
}
