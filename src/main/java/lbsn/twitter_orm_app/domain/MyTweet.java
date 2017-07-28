package lbsn.twitter_orm_app.domain;

import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.social.twitter.api.Tweet;

@Document(collection="tweets")

public class MyTweet{
	
}
