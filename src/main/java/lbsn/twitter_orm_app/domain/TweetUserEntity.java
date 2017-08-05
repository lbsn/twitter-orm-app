package lbsn.twitter_orm_app.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.social.twitter.api.TwitterProfile;

import twitter4j.User;

@Document(collection="users")
public class TweetUserEntity{
	@Id
	private Long id;
	private User profile;
	private boolean isInfluencer;
	
	/**
	 * Default constructor
	 */
	public TweetUserEntity(){}
	
	/**
	 * Constructor with TwitterProfile parameter
	 * @param profile
	 */
	public TweetUserEntity(User profile){
		this.id = profile.getId();
		this.setProfile(profile);
	}
	public Long getId() {
		return id;
	}

	public User getProfile() {
		return profile;
	}

	public void setProfile(User profile) {
		this.profile = profile;
	}

	public boolean isInfluencer() {
		return isInfluencer;
	}

	public void setInfluencer(boolean isInfluencer) {
		this.isInfluencer = isInfluencer;
	}
}
