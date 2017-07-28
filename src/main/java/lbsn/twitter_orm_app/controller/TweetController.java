package lbsn.twitter_orm_app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lbsn.twitter_orm_app.repository.TweetDao;
import lbsn.twitter_orm_app.service.TweetSearch;

@Controller
public class TweetController {
	@Autowired
	private TweetDao tweetDao;
	
	@RequestMapping("/tweet")
	public String tweet(@ModelAttribute TweetSearch tweetSearch){
		return "tweet";
	}
	
	@PostMapping("/search")
	public String submit(@RequestParam(value="keyword") String keyword,
			TweetSearch tweetSearch){
		List<Tweet> tweets = tweetSearch.search();
		for(Tweet t : tweets){
			tweetDao.save(t);
		}
		return "tweet";
	}
}
