package lbsn.twitter_orm_app.domain;

import java.util.List;
import java.util.Map;

import twitter4j.User;

public class AjaxResponse {
	private String keyword;
	private List<TweetEntity> tweets;
	private List<TweetUserEntity> users;
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
	public List<TweetUserEntity> getUsers() {
		return users;
	}
	public void setUsers(List<TweetUserEntity> users) {
		this.users = users;
	}
	
}
