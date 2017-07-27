package lbsn.twitter_orm_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lbsn.twitter_orm_app.service.TweetSearch;

@Controller
public class TweetController {
	
	@RequestMapping("/tweet")
	public String tweet(@ModelAttribute TweetSearch tweetSearch){
		return "tweet";
	}
}
