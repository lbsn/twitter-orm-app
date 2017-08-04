package lbsn.twitter_orm_app.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lbsn.twitter_orm_app.domain.TweetEntity;
import lbsn.twitter_orm_app.domain.TweetUserEntity;
import lbsn.twitter_orm_app.repository.TweetDao;
import lbsn.twitter_orm_app.repository.TweetUserDao;
import lbsn.twitter_orm_app.service.TweetSearch;

@Controller
public class IndexController{
	
	@RequestMapping(value={"/", "/home"})
	public String index(){
		return "index";
	}

}
	
