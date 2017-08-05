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
		while(!Thread.currentThread().isInterrupted()){
			try{
				Status tweet = this.queue.take();
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
	
	public void process(Status tweet) throws Exception{
		TweetEntity entity = new TweetEntity();
		
		// Set keyword
		entity.setKeyword(this.tweetStream.getKeyword());
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
		System.out.println("saved for keyword: " + this.tweetStream.getKeyword());
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
		Instances dataset = this.repDimClassifier.makeDataset();
		dataset = this.repDimClassifier.addInstance(dataset, text);
		return this.repDimClassifier.classify(dataset);
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
