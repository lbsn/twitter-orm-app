package lbsn.twitter_orm_app.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service
@ConfigurationProperties(prefix = "twitter")
public class TweetStream implements StatusListener{
	private static final String CONSUMER_KEY = "EUCjmzK9zUx1LRaD6eSGjailF";
	private static final String CONSUMER_SECRET = "wotBftJjZCceKHGoj29UHv2jq9IL4Mfwrcfpo6OXHWdSNAXfr8";
	private static final String ACCESS_TOKEN = "875757581498798081-1Ptkb4NAF2vPms4YJpqbD8q3gE2HWjJ";
	private static final String ACCESS_SECRET = "5TABAU7QF6t5CzboCOP2zhEwxKgBoekPvoXG1Azdlba8P";
	private static final String LANG = "en";
	private TwitterStream twitter;
	private String keyword;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private BeanFactory beanFactory;
	private BlockingQueue<Status> queue = new ArrayBlockingQueue<>(20);
	
	/**
	 * Constructor
	 */	
	public TweetStream(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(TweetStream.CONSUMER_KEY)
		.setOAuthConsumerSecret(TweetStream.CONSUMER_SECRET)
		.setOAuthAccessToken(TweetStream.ACCESS_TOKEN)
		.setOAuthAccessTokenSecret(TweetStream.ACCESS_SECRET)
		.setJSONStoreEnabled(false);
		
		this.twitter = new TwitterStreamFactory(cb.build()).getInstance();
		this.twitter.addListener(this);
	}
	
	@PostConstruct
	public void initProcessingThreads() throws Exception {
		for (int i = 0; i < this.taskExecutor.getMaxPoolSize(); i++) {
			this.taskExecutor.execute(
					(TweetProcessor) this.beanFactory.getBean("tweetProcessor", this.queue)
					);
		}
	}
	
	public void startStreaming(){
		FilterQuery tweetFilterQuery = new FilterQuery(); 
		tweetFilterQuery.track(this.keyword);
		tweetFilterQuery.language("en");
		this.twitter.filter(tweetFilterQuery);
		System.out.println("------------------- STARTED STREMING FOR " + this.keyword);
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatus(Status tweet) {
		this.queue.offer(tweet);		
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
