package lbsn.twitter_orm_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		return "tweet";
	}
	
	@PostMapping("/search")	
	public ResponseEntity<String> submit(@RequestBody String keyword) throws Exception{
		tweetStream.startStreaming();
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@PostMapping("/update")	
	public ResponseEntity<String> update(@RequestBody String keyword) throws Exception{
		tweetStream.startStreaming();
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
}
