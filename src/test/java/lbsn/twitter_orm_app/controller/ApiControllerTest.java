package lbsn.twitter_orm_app.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lbsn.twitter_orm_app.domain.SearchCriteria;
import lbsn.twitter_orm_app.repository.TweetDao;
import lbsn.twitter_orm_app.service.ClusteringService;
import lbsn.twitter_orm_app.service.TweetStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiControllerTest {
	@Mock
	private TweetStream tweetStream;
	@Mock
	private TweetDao tweetDao;
	@Mock
	private ClusteringService clusteringService;
	@InjectMocks
	private ApiController apiController = new ApiController();
	
	private MockMvc mockMvc;
	private ObjectMapper mapper;
	
   @Before
   public void setup() {
   	this.mockMvc = MockMvcBuilders.
      		 standaloneSetup(this.apiController).
      		 build();
   	
   	this.mapper = new ObjectMapper();
   	this.mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
   	this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
   }
   
   /**
    * Test search
    */
   @Test
   public void testSearch(){
   	
   	SearchCriteria search = new SearchCriteria();
   	search.setKeyword("london");
   	
   	try {
			this.mockMvc.perform(post("/api/search").
					contentType(MediaType.APPLICATION_JSON).
					content(this.mapper.writeValueAsString(search)).
					accept(MediaType.APPLICATION_JSON)).					
					andExpect(status().isOk()).
					andExpect(content().contentType("application/json;charset=ISO-8859-1"));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
   }
   
   /**
    * Test update
    */
   @Test
   public void testUpdate(){
   	
   	SearchCriteria search = new SearchCriteria();
   	search.setKeyword("london");
   	
   	try {
			this.mockMvc.perform(post("/api/update").
					contentType(MediaType.APPLICATION_JSON).
					content(this.mapper.writeValueAsString(search)).
					accept(MediaType.APPLICATION_JSON)).					
					andExpect(status().isOk()).
					andExpect(content().contentType("application/json;charset=ISO-8859-1"));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
   }
   
   /**
    * Test cluster
    */
   @Test
   public void testCluster(){
   	SearchCriteria search = new SearchCriteria();
   	search.setKeyword("london");
   	
   	try {
			this.mockMvc.perform(post("/api/cluster").
					contentType(MediaType.APPLICATION_JSON).
					content(this.mapper.writeValueAsString(search)).
					accept(MediaType.APPLICATION_JSON)).					
					andExpect(status().isOk()).
					andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
   }
   
}
