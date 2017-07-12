package lbsn.twitter_orm_app.tweetsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public final class TweetSearch {
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
	public TweetSearch(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(TweetSearch.CONSUMER_KEY)
		.setOAuthConsumerSecret(TweetSearch.CONSUMER_SECRET)
		.setOAuthAccessToken(TweetSearch.ACCESS_TOKEN)
		.setOAuthAccessTokenSecret(TweetSearch.ACCESS_SECRET)
		.setJSONStoreEnabled(true);

		this.twitter = new TwitterFactory(cb.build()).getInstance();
	}

	/**
	 * Retrieves tweets
	 * @param keyword
	 * @throws TwitterException 
	 */
	public ArrayList<Status> search() throws TwitterException{
		ArrayList<Status> fetchedTweets = new ArrayList<Status>(); 
		Query query = new Query(this.keyword);
		query.setCount(TweetSearch.NUM_TWEETS);
		QueryResult result = null;

		// Retrieves tweets until NUM_TWEETS is reached.
		int nTweets = 0;
		int iTweet = 0;
		while (nTweets < NUM_TWEETS && query != null) {

			result = this.twitter.search(query);
			query = result.nextQuery();
			List<Status> tweets = result.getTweets();

			for (Status tweet : tweets ) {
				if (tweet.getUser().getLang().equalsIgnoreCase(LANG)) {
					fetchedTweets.add(tweet);
					iTweet++;
				}
				if (iTweet >= NUM_TWEETS) {
					break;
				}
			}

			nTweets += tweets.size();

			// Waits in case of rate limit exceeded
			boolean bWait = true;
			while (bWait) {
				try {
					Map<String, RateLimitStatus> oRT = twitter.getRateLimitStatus();
					RateLimitStatus rateLimit = oRT.get("/search/tweets");
					int remaining = rateLimit.getRemaining();
					System.out.println("Remaining API calls: " + remaining);
					int remainingTime = rateLimit.getSecondsUntilReset();

					if (remaining <= 1) {
						System.out.println("Waiting " + remainingTime + " seconds");
						Thread.sleep(remainingTime * 1000);
					} 
					else {
						bWait = false;
					}
				} 
				catch (Exception te) {
					try {
						Thread.sleep(60 * 1000);
					} 
					catch (InterruptedException ex) {

					}
				}
			}
		}
		return fetchedTweets;
	}
	
	public void setKeyword(String val){
		this.keyword = val;
	}
	
	public String getKeyword(){
		return this.keyword;
	}
	
}
