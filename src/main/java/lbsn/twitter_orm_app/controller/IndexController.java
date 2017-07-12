package lbsn.twitter_orm_app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lbsn.twitter_orm_app.sentiment.SentimentClassifier;
import lbsn.twitter_orm_app.tweetsearch.TweetSearch;
import twitter4j.TwitterException;

@Controller
public class IndexController{
	
	@RequestMapping(value={"/", "/home"})
	public String index(){
		return "index";
	}
	
	@GetMapping("/search")
	public String tweetSearch(@ModelAttribute TweetSearch tweetSearch){
		return "search";
	}
	
	@PostMapping("/search")
	public ModelAndView submit(@RequestParam(value="keyword") String keyword,
			TweetSearch tweetSearch) throws TwitterException{
		ModelAndView model = new ModelAndView("tweet", "tweets", tweetSearch.search());
		return model;
	}
	
	@RequestMapping("/test")
	public ModelAndView score(SentimentClassifier sc){
		String text = "I'm very happy to be here!";
		String sentiment = sc.score(text);
		return new ModelAndView("test", "model", sentiment);
	}
}
	
