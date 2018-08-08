package com.hilltoponline.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import javax.servlet.RequestDispatcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ErrorControllerTest {
	
	@Autowired
    private MockMvc mvc;
    

    @Test
    @WithUserDetails("registrar") //use a user to get past login page.
    public void getErrorPage() throws Exception {
    	MockHttpServletRequestBuilder errorTemplateRequest = MockMvcRequestBuilders.get("/error")
    			.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 404)
    	        .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/pathNotAvailable")
    	        .requestAttr(RequestDispatcher.ERROR_MESSAGE, "No message available")
				.accept(MediaType.TEXT_XML);
    	
    	mvc.perform(errorTemplateRequest)
    	.andExpect(content().string(containsString("404")))
    	.andExpect(content().string(containsString("Not Found")))
    	.andExpect(content().string(containsString("No message available")));
    }
}
