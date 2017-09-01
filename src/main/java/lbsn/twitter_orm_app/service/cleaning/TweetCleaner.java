package lbsn.twitter_orm_app.service.cleaning;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import weka.core.stemmers.SnowballStemmer;
import weka.core.stopwords.AbstractFileBasedStopwords;
import weka.core.stopwords.WordsFromFile;

public class TweetCleaner {
	private TweetCleanerConfiguration options;
	private String text;
	private SnowballStemmer stemmer;
	private AbstractFileBasedStopwords stopwordHandler;
	private final String STOPWORDS_FILE;
	
	
	public TweetCleaner(TweetCleanerConfiguration options){
		this.options = options;
		this.stemmer = new SnowballStemmer();
		this.stopwordHandler = new WordsFromFile();
		this.STOPWORDS_FILE = this.getClass().
				getResource("/data/stopwords/stopwords_en.txt").
				getPath();
		try {
			String[] params = weka.core.Utils.splitOptions("-stopwords " + this.STOPWORDS_FILE);
			stopwordHandler.setOptions(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String clean(String text){
		this.text = text;
		
		this.cleanUrl();
		this.cleanHash();
		this.cleanPunctuation();
		this.cleanNumber();
		this.cleanSpaces();
		this.changeCase();
		this.cleanStopwords();
		this.applyStemming();
		return this.text;
	}
	
	/**
	 * Clean url
	 */
	private void cleanUrl(){
		switch((CleanOptions.URL) this.options.getOption("url")){
		
		case REMOVE:
			this.text = this.text.replaceAll("(http|https):\\S+", "");
			this.text = this.text.replaceAll("pic.twitter.com\\S+", "");
			break;
		
		case RETAIN:
			break;
		
		case TAG:
			// TODO: implement tag
			break;		
		}
	}
	
	/**
	 * Clean hashtags
	 */
	private void cleanHash(){
		switch((CleanOptions.HASH) this.options.getOption("hash")){
		
		case REMOVE:
			this.text = this.text.replaceAll("#", "");
			break;
			
		case RETAIN:
			break;
			
		case TAG:
			// TODO: implement tag
			break;
		}
	}
	
	/**
	 * Clean emoticons
	 */
	public void cleanEmoticon(){
		switch((CleanOptions.EMOTICON) this.options.getOption("emoticon")){
		
		case REMOVE:
			break;
		
		case RETAIN:
			break;
		
		case TAG:
			break;
		}
	}
	
	/**
	 * Clean punctuation
	 */
	private void cleanPunctuation(){
		switch((CleanOptions.PUNCTUATION) this.options.getOption("punctuation")){
		
		case KEEP_ALL:
			break;
		
		case KEEP_APOSTROPHE:
			this.text = this.text.replaceAll("[^\\w\\s\']", "");
			break;
		}
	}
	
	/**
	 * Clean numbers
	 */
	private void cleanNumber(){
		switch((CleanOptions.NUMBER) this.options.getOption("number")){
		
		case REMOVE:
			this.text = this.text.replaceAll("\\d", "");
			break;
		
		case RETAIN:
			break;
		}
	}
	
	/**
	 * Clean spaces
	 */
	private void cleanSpaces(){
		this.text = this.text.replaceAll("\\s+", " ");
	}
	
	/**
	 * Change case
	 */
	private void changeCase(){
		switch((CleanOptions.CHANGE_CASE) this.options.getOption("changeCase")){
		
		case LOWER:
			this.text = this.text.toLowerCase();
			break;
		case RETAIN:
			break;
		}
	}
	
	/**
	 * Stemming
	 */
	private void applyStemming(){
		switch((CleanOptions.STEMMING) this.options.getOption("stemming")){

		case TRUE:
			List<String> words = Arrays.asList(text.split(" "));
			this.text = words.stream().map(s -> {return stemmer.stem(s);}).collect(Collectors.joining(" "));
			break;
		case FALSE:
			break;
		}
	}
	
	/**
	 * Remove stopwords
	 */
	private void cleanStopwords(){
		switch((CleanOptions.STOPWORDS) this.options.getOption("stopwords")){

		case TRUE:
			List<String> words = Arrays.asList(text.split(" "));
//			this.text = words.stream().filter(w -> !Stopwords.isStopword(w)).collect(Collectors.joining(" "));
			this.text = words.stream().filter(w -> !this.stopwordHandler.isStopword(w)).collect(Collectors.joining(" "));
			break;
		case FALSE:
			break;
		}
	}
}
