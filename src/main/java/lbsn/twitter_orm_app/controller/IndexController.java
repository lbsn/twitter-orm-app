package lbsn.twitter_orm_app.controller;


import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import model.TweetSearch;

@Controller
public class IndexController{
	
	@RequestMapping(value={"/", "/home"})
	public String index(){
		return "index";
	}
	
	@GetMapping("/search")
	public String tweetSearch(Model model){
		model.addAttribute("tweetSearch", new TweetSearch());
		return "search";
	}
	
	@PostMapping("/search")
	public String submit(@ModelAttribute TweetSearch tweetSearch){
		return "tweet";
	}
}
	
