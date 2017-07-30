package lbsn.twitter_orm_app.service;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import lbsn.twitter_orm_app.repository.TweetDao;

public class TweetProcessor implements Runnable{
	private TweetDao tweetDao;
	private final BlockingQueue<Tweet> queue;
	
	public TweetProcessor(TweetDao tweetDao, BlockingQueue<Tweet> queue){
		this.queue = queue;
		this.tweetDao = tweetDao;
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
		this.tweetDao.save(tweet);
		System.out.println("saved");
	}
}
