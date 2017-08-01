package lbsn.twitter_orm_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lbsn.twitter_orm_app.domain.AjaxResponse;
import lbsn.twitter_orm_app.domain.TweetEntity;
import lbsn.twitter_orm_app.repository.TweetDao;
import lbsn.twitter_orm_app.service.TweetStream;

@Controller
public class TweetController {
	@Autowired
	private TweetDao tweetDao;
	@Autowired
	private TweetStream tweetStream;
	
	@RequestMapping("/tweet")
	public String tweet(@ModelAttribute TweetStream tweetStream){
		System.out.println("-------------------/TWEET");
		return "tweet";
	}
}
