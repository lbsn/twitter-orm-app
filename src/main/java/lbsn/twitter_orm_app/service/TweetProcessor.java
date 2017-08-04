package lbsn.twitter_orm_app.service;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

import lbsn.twitter_orm_app.domain.TweetEntity;
import lbsn.twitter_orm_app.domain.TweetUserEntity;
import lbsn.twitter_orm_app.repository.TweetDao;
import lbsn.twitter_orm_app.repository.TweetUserDao;

@Component
@Scope("prototype")
public class TweetProcessor implements Runnable{
	@Autowired
	private TweetDao tweetDao;
	@Autowired
	private TweetUserDao tweetUserDao;
	@Autowired
	SentimentClassifier sentClassifier;
	@Autowired
	RepDimClassifier repDimClassifier;
	@Autowired
	AuthRankClassifier authRankClassifier;
	
	private final BlockingQueue<Tweet> queue;
	private String keyword;
	
	public TweetProcessor(BlockingQueue<Tweet> queue, String keyword){
		this.queue = queue;
		this.keyword = keyword;
	}
	
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			try{
				Tweet tweet = this.queue.take();
				this.process(tweet);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
	}
	
	public void stop(){
		Thread.currentThread().interrupt();
	}
	
	public void process(Tweet tweet) throws Exception{
		TweetEntity entity = new TweetEntity();
		
		// Set keyword
		entity.setKeyword(this.keyword);
		// Set sentiment
		entity.setSentiment(this.computeSentiment(tweet.getText()));
		// Set reputation dimension
		entity.setRepDimension(this.computeRepDim(tweet.getText()));
		// Set text
		entity.setText(tweet.getText());
		// Save tweet
		this.tweetDao.save(entity);
		
		TweetUserEntity user = new TweetUserEntity(tweet.getUser());
		// Set author influence
		user.setInfluencer(this.computeAuthInfluence(user.getProfile()));
		// Save tweet user
		this.tweetUserDao.save(user);
		System.out.println("saved for keyword: " + this.keyword);
	}
	
	/**
	 * Sentiment classification
	 */
	private String computeSentiment(String text){
		return this.sentClassifier.score(text);
	}
	
	/**
	 * Reputation dimension classification
	 * @throws Exception 
	 */
	private String computeRepDim(String text) throws Exception{
		this.repDimClassifier.makeInstance(text);
		return this.repDimClassifier.classify();
	}
	
	/**
	 * Author influence classficiation
	 * @throws Exception 
	 */
	private boolean computeAuthInfluence(TwitterProfile profile) throws Exception{
		this.authRankClassifier.makeInstance(profile);
		return this.authRankClassifier.classify();
	}
}
