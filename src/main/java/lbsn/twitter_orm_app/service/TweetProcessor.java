package lbsn.twitter_orm_app.service;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import lbsn.twitter_orm_app.domain.TweetEntity;
import lbsn.twitter_orm_app.repository.TweetDao;

@Component
@Scope("prototype")
public class TweetProcessor implements Runnable{
	@Autowired
	private TweetDao tweetDao;
	@Autowired
	SentimentClassifier sentClassifier;
	
	private final BlockingQueue<Tweet> queue;
	
	public TweetProcessor(BlockingQueue<Tweet> queue){
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while(true){
			try{
				Tweet tweet = this.queue.take();
				this.process(tweet);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}			
	}
	
	public void process(Tweet tweet){
		TweetEntity entity = new TweetEntity(tweet);

		// Get sentiment
		entity.setSentiment(this.scoreSentiment(tweet.getText()));
		
		// Save
		this.tweetDao.save(entity);
		System.out.println("saved");
	}
	
	/**
	 * Sentiment classification
	 */
	private String scoreSentiment(String text){
		return this.sentClassifier.score(text);
	}
}
