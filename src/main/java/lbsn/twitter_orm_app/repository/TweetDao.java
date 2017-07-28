package lbsn.twitter_orm_app.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lbsn.twitter_orm_app.domain.Tweet;

@Repository
public interface TweetDao extends CrudRepository<Tweet,Long> {

}
