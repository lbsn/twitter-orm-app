package lbsn.twitter_orm_app.service.cleaning;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Configuration class for a TweetCleanerConfiguration
 * 
 * @author Luca Bisin (lbsn) - 2017
 *
 */
public final class TweetCleanerConfiguration {
	private HashMap<String, CleanOptions> options;
	
	
	/**
	 * Builder
	 */
	public static class Builder{
		private CleanOptions.URL url;
		private CleanOptions.CHANGE_CASE changeCase;
		private CleanOptions.EMOTICON emoticon;
		private CleanOptions.HASH hash;
		private CleanOptions.PUNCTUATION punctuation;
		private CleanOptions.NUMBER number;
		private CleanOptions.STEMMING stemming;
		private CleanOptions.STOPWORDS stopwords;
		
		/**
		 * Builder constructor
		 */
		public Builder(){
			// Default options
			this.url = CleanOptions.URL.REMOVE;
			this.changeCase = CleanOptions.CHANGE_CASE.LOWER;
			this.emoticon = CleanOptions.EMOTICON.REMOVE;
			this.hash = CleanOptions.HASH.REMOVE;
			this.punctuation = CleanOptions.PUNCTUATION.KEEP_APOSTROPHE;
			this.number = CleanOptions.NUMBER.REMOVE;
			this.stemming = CleanOptions.STEMMING.FALSE;
			this.stopwords = CleanOptions.STOPWORDS.FALSE;
		}
		
		/**
		 * Set url
		 * @param val
		 * @return Builder
		 */
		public Builder url(CleanOptions.URL val){
			this.url = val;
			return this;
		}
		
		/**
		 * Set changeGase
		 * @param val
		 * @return Builder
		 */
		public Builder changeCase(CleanOptions.CHANGE_CASE val){
			this.changeCase = val;
			return this;
		}
		
		/**
		 * Set emoticon
		 * @param val
		 * @return Builder
		 */
		public Builder emoticon(CleanOptions.EMOTICON val){
			this.emoticon = val;
			return this;
		}
		
		/**
		 * Set hash
		 * @param val
		 * @return Builder
		 */
		public Builder hash(CleanOptions.HASH val){
			this.hash = val;
			return this;
		}
		
		/**
		 * Set punctuation
		 * @param val
		 * @return Builder
		 */
		public Builder punctuation(CleanOptions.PUNCTUATION val){
			this.punctuation = val;
			return this;
		}
		
		/**
		 * Set numbers
		 */
		public Builder number(CleanOptions.NUMBER val){
			this.number = val;
			return this;
		}
		
		/**
		 * Set stemming
		 */
		public Builder stemming(CleanOptions.STEMMING val){
			this.stemming = val;
			return this;
		}
		
		/**
		 * Set stopwords
		 */
		public Builder stopwords(CleanOptions.STOPWORDS val){
			this.stopwords = val;
			return this;
		}
		
		/**
		 * Build
		 */
		public TweetCleanerConfiguration build(){
			return new TweetCleanerConfiguration(this);
		}
	}
	
	/**
	 * Main constructor
	 */
	@Autowired
	private TweetCleanerConfiguration(Builder builder){
		this.options = new HashMap<String,CleanOptions>();
		
		this.options.put("url", builder.url);
		this.options.put("changeCase", builder.changeCase);
		this.options.put("emoticon", builder.emoticon);
		this.options.put("hash", builder.hash);
		this.options.put("punctuation", builder.punctuation);
		this.options.put("number", builder.number);
		this.options.put("stemming", builder.stemming);
		this.options.put("stopwords", builder.stopwords);
	}
	
	/**
	 * Get option list
	 */
	public HashMap<String, CleanOptions> getOptionList(){
		return this.options;
	}
	
	/**
	 * Get a single option
	 */
	public CleanOptions getOption(String name){
		return this.options.get(name);
	}
}
