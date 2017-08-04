package lbsn.twitter_orm_app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Repository;

import lbsn.twitter_orm_app.domain.TweetEntity;
import lbsn.twitter_orm_app.domain.TweetUserEntity;

@Repository
public interface TweetUserDao extends MongoRepository<TweetUserEntity, Long> {
	
}
