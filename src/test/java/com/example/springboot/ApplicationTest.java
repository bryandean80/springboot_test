package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {
    @Autowired
    private MockMvc mockmvc;
    @Test
    void testHistory() throws Exception{
        mockmvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=testing")).andReturn();
        mockmvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("testing")));
    }

    @Test
    void testDelete() throws Exception{
        mockmvc.perform(MockMvcRequestBuilders.post("/delete?post_text=stringToDelete")).andReturn();
        mockmvc.perform(MockMvcRequestBuilders.post("/delete?post_text=stringToDelete").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("does not exist")));
    }


    @Test
    void testCapsDelete() throws Exception{
        //post
        mockmvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=stringToDelete")).andReturn();
        // try to delete lowercase version of post
        mockmvc.perform(MockMvcRequestBuilders.post("/delete?post_text=stringtodelete")).andReturn();
        // check if post exists
        mockmvc.perform(MockMvcRequestBuilders.post("/history").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("stringToDelete")));
    }

    @Test
    void testDeleteHistory() throws Exception{
        //post
        mockmvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=stringToDelete")).andReturn();
        //remove post
        mockmvc.perform(MockMvcRequestBuilders.post("/delete?post_text=stringToDelete")).andReturn();
        //check history for post
        mockmvc.perform(MockMvcRequestBuilders.post("/history").contentType(MediaType.ALL))
                .andExpect(content().string(not(containsString("stringToDelete"))));
    }
}