package lbsn.twitter_orm_app.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lbsn.twitter_orm_app.domain.TweetEntity;
import lbsn.twitter_orm_app.repository.TweetDao;

@Controller
public class IndexController{
	@Autowired
	private TweetDao tweetDao;
	
	@RequestMapping(value={"/", "/home"})
	public String index(){
		return "index";
	}

}
	
