package lbsn.twitter_orm_app.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Repository;

import lbsn.twitter_orm_app.domain.TweetEntity;

@Repository
public interface TweetDao extends MongoRepository<TweetEntity, String> {
	List<TweetEntity> findByKeyword(String keyword);
}
