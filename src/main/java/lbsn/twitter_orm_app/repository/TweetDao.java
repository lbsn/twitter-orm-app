package lbsn.twitter_orm_app.repository;

import lbsn.twitter_orm_app.model.Tweet;
import org.springframework.data.repository.CrudRepository;

public interface TweetDao extends CrudRepository<Tweet,Long> {

}
