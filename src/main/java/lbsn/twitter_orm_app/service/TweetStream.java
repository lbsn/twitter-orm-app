package lbsn.twitter_orm_app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "twitter")
public final class TweetStream implements StreamListener{
	private static final String CONSUMER_KEY = "EUCjmzK9zUx1LRaD6eSGjailF";
	private static final String CONSUMER_SECRET = "wotBftJjZCceKHGoj29UHv2jq9IL4Mfwrcfpo6OXHWdSNAXfr8";
	private static final String ACCESS_TOKEN = "875757581498798081-1Ptkb4NAF2vPms4YJpqbD8q3gE2HWjJ";
	private static final String ACCESS_SECRET = "5TABAU7QF6t5CzboCOP2zhEwxKgBoekPvoXG1Azdlba8P";
	private static final String LANG = "en";
	private static final int NUM_TWEETS = 200;

	private Twitter twitter;
	private String keyword;
	
	/**
	 * Constructor
	 */	
	public TweetStream(){
		this.twitter = new TwitterTemplate(
				this.CONSUMER_KEY,
				this.CONSUMER_SECRET,
				this.ACCESS_TOKEN,
				this.ACCESS_SECRET
				);
	}
	
	public void run(){
		this.twitter.
				streamingOperations().
				filter(this.keyword, Arrays.asList(this));
		System.out.println("-------------- STARTED");
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public void onDelete(StreamDeleteEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLimit(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTweet(Tweet tweet) {
		System.out.println(tweet.getText());
		
	}

	@Override
	public void onWarning(StreamWarningEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
