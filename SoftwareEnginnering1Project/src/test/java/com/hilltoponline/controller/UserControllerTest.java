package com.hilltoponline.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void getLoginPage() throws Exception {
        MockHttpServletRequestBuilder loginRequest = MockMvcRequestBuilders.get("/user/login")
                .accept(MediaType.TEXT_XML);
        mvc.perform(loginRequest)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                		"<input type=\"text\" name=\"username\" class=\"form-control\" placeholder=\"username\" aria-describedby=\"username-addon\" autofocus=\"true\" />"
                		)))
                .andExpect(content().string(containsString(
                		"<input type=\"password\" name=\"password\" class=\"form-control\" placeholder=\"password\" aria-describedby=\"password-addon\" />"
                		)));
    }
    
    @Test
    public void getLoginPageWithError() throws Exception {
        MockHttpServletRequestBuilder loginRequestWithError = MockMvcRequestBuilders.get("/user/login")
        		.param("error", "true")
                .accept(MediaType.TEXT_XML);
        mvc.perform(loginRequestWithError)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Wrong user or password")));
    }
    
    @Test
    public void getLoginPageWithLogoutMessage() throws Exception {
        MockHttpServletRequestBuilder loginRequestWithLogoutMessage = MockMvcRequestBuilders.get("/user/login")
        		.param("logout", "true")
                .accept(MediaType.TEXT_XML);
        mvc.perform(loginRequestWithLogoutMessage)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("You&#39;ve been logged out.")));
    }
}
