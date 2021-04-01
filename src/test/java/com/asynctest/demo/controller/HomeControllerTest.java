package com.asynctest.demo.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@AutoConfigureMockMvc
@SpringBootTest
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("测试callable正确执行返回字符串")
    void callableResult() throws Exception {
        /*
        MvcResult result = mockMvc.perform(get("/home/callable")) //执行请求 
           .andExpect(request().asyncStarted())
            //.andExpect(request().asyncResult(CoreMatchers.instanceOf(User.class))) //默认会等10秒超时 
            .andReturn(); 

            mockMvc.perform(asyncDispatch(result)) 
            .andExpect(status().isOk()) 
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
            .andExpect(jsonPath("$.id").value(1));
        */

        MvcResult mvcResult = mockMvc.perform(get("/home/callable"))
                .andExpect(request().asyncStarted())
            .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(content().string("this is callable"));
    }


    @Test
    @DisplayName("测试callable不正确执行返回字符串")
    void deferredResult() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/home/callable")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content, equalTo(""));
    }

    @Test
    @DisplayName("测试async返回字符串")
    void getAsynHello() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/home/async")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content, equalTo("this is async"));
    }

    @Test
    @DisplayName("测试sync返回字符串")
    void getSyncHello() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/home/sync")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content, equalTo("this is sync"));
    }
}