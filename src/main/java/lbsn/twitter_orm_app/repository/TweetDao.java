package lbsn.twitter_orm_app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Repository;

import lbsn.twitter_orm_app.domain.TweetEntity;

@Repository
public interface TweetDao extends CrudRepository<TweetEntity,Long> {

}
