package lbsn.twitter_orm_app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetDao extends CrudRepository<Tweet,Long> {

}
