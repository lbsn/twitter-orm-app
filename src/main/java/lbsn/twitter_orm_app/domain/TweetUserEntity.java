package lbsn.twitter_orm_app.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.social.twitter.api.TwitterProfile;

@Document(collection="users")
public class TweetUserEntity{
	@Id
	private Long id;
	private TwitterProfile profile;
	
	public TweetUserEntity(){}
	
	public TweetUserEntity(TwitterProfile profile){
		this.id = profile.getId();
		this.setProfile(profile);
	}
	public Long getId() {
		return id;
	}

	public TwitterProfile getProfile() {
		return profile;
	}

	public void setProfile(TwitterProfile profile) {
		this.profile = profile;
	}
}
