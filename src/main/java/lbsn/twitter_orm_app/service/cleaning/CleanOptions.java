package lbsn.twitter_orm_app.service.cleaning;

/**
 * Stores possible values for TweetCleanerConfiguration options.
 * @author Luca Bisin (lbsn) - 2017
 *
 */
public interface CleanOptions{
	/**
	 * Url options
	 */
	public static enum URL implements CleanOptions{
		REMOVE,TAG,RETAIN;
	}
	
	/**
	 * Hashtag options
	 */
	public static enum HASH implements CleanOptions{
		REMOVE,TAG,RETAIN;
	}
	
	/**
	 * Case options
	 */
	public static enum CHANGE_CASE implements CleanOptions{
		LOWER,RETAIN;
	}
	
	/**
	 * Emoticons options
	 */
	public static enum EMOTICON implements CleanOptions{
		REMOVE,TAG,RETAIN;
	}
	
	/**
	 * Punctuation options
	 */
	public static enum PUNCTUATION implements CleanOptions{
		KEEP_APOSTROPHE,KEEP_ALL;
	}
	
	/**
	 * Numbers options
	 */
	public static enum NUMBER implements CleanOptions{
		REMOVE,RETAIN;
	}
	
	/**
	 * Stemming options
	 */
	public static enum STEMMING implements CleanOptions{
		TRUE,FALSE;
	}
	
	/**
	 * Stopwords remove
	 */
	public static enum STOPWORDS implements CleanOptions{
		TRUE,FALSE;
	}
}