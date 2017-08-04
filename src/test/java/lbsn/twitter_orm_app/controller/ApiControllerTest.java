package lbsn.twitter_orm_app.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import lbsn.twitter_orm_app.domain.SearchCriteria;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class ApiControllerTest {
	private MockMvc mockMvc;
	@MockBean
	private SearchCriteria search;
	
   @Before
   public void setup() {
       this.mockMvc = MockMvcBuilders.standaloneSetup(new ApiController()).build();
   }

   @Test
   public void testStartStreaming() throws Exception {
   	this.search.setKeyword("london");
   	this.mockMvc.perform(post("/api/search").
   			param("keyword", "london").
   			accept(MediaType.APPLICATION_JSON_UTF8)).
   			andExpect(status().isOk()).
				andExpect(content().contentType("application/json"));
   }
   
   @Test
   public void testUpdate() throws Exception {
   	this.search.setKeyword("london");
   	this.mockMvc.perform(post("/api/update", this.search).accept(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/json"));
   }
}
