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
	@Autowired
	RepDimClassifier repDimClassifier;
	
	private final BlockingQueue<Tweet> queue;
	private String keyword;
	
	public TweetProcessor(BlockingQueue<Tweet> queue, String keyword){
		this.queue = queue;
		this.keyword = keyword;
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
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
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
		
		// Save
		this.tweetDao.save(entity);
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
		// Text preprocessing
		TweetCleanerConfiguration cleanerConfig = new TweetCleanerConfiguration.
				Builder().
				url(CleanOptions.URL.REMOVE).
				changeCase(CleanOptions.CHANGE_CASE.RETAIN).
				number(CleanOptions.NUMBER.RETAIN).
				emoticon(CleanOptions.EMOTICON.REMOVE).
				stemming(CleanOptions.STEMMING.TRUE).
				stopwords(CleanOptions.STOPWORDS.TRUE).
				build();

		TweetCleaner tc = new TweetCleaner(cleanerConfig);
		this.repDimClassifier.makeInstance(tc.clean(text));
		return this.repDimClassifier.classify();
	}
}
