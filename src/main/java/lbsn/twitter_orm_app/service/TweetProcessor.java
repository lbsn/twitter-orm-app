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
import twitter4j.Status;
import twitter4j.User;
import weka.core.Instances;

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
	@Autowired
	TweetStream tweetStream;
	private final BlockingQueue<Status> queue;
	
	public TweetProcessor(BlockingQueue<Status> queue){
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while(!Thread.interrupted()){
			Status tweet;
			try {
				tweet = this.queue.take();
				this.process(tweet);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}			
	}
	
	public TweetEntity process(Status tweet) throws Exception{
		TweetEntity entity = new TweetEntity();
		String text = tweet.getText();
		
		// Set keyword
		entity.setKeyword(this.tweetStream.getKeyword());
		// Set sentiment
		entity.setSentiment(this.computeSentiment(text));
		// Set reputation dimension
		entity.setRepDimension(this.computeRepDim(text));
		// Set createdAt
		
		entity.setCreatedAt(tweet.getCreatedAt());
		// Set text
		entity.setText(tweet.getText());
		// Set user id
		entity.setUserId(tweet.getUser().getId());
		// Save tweet
		this.tweetDao.save(entity);
		
		TweetUserEntity user = new TweetUserEntity(tweet.getUser());
		// Set author influence
		user.setInfluencer(this.computeAuthInfluence(user.getProfile()));
		// Save tweet user
		this.tweetUserDao.save(user);
		return entity;
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
	public String computeRepDim(String text){
		try{
			Instances dataset = this.repDimClassifier.makeDataset();
			dataset = this.repDimClassifier.addInstance(dataset, text);
			return this.repDimClassifier.classify(dataset);	
		}
		catch(Exception e){
			return null;
		}
		
	}
	
	/**
	 * Author influence classficiation
	 * @throws Exception 
	 */
	private boolean computeAuthInfluence(User user) throws Exception{
		Instances dataset = this.authRankClassifier.makeDataset();
		dataset = this.authRankClassifier.addInstance(dataset, user);
		return this.authRankClassifier.classify(dataset);
	}
}
