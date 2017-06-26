package lbsn.twitter_orm_app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController{
	
	@RequestMapping(value={"/", "/home"})
	public String index(){
		return "index";
	}
	
}
