package lbsn.twitter_orm_app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lbsn.twitter_orm_app.repository.TweetDao;
import lbsn.twitter_orm_app.service.TweetSearch;
import lbsn.twitter_orm_app.service.TweetStream;

@Controller
public class TweetController {
	@Autowired
	private TweetDao tweetDao;
	@Autowired
	private TaskExecutor taskExecutor;
	
	@RequestMapping("/tweet")
	public String tweet(@ModelAttribute TweetStream tweetStream){
		return "tweet";
	}
	
	@PostMapping("/search")
	public String submit(@RequestParam(value="keyword") String keyword,
			TweetStream tweetStream){
		tweetStream.run();
		return "tweet";
	}
}
