package com.sbrf.reboot.namePageService;

import com.sbrf.reboot.nameApi.NameController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc

public class NamePageServiceTest {
    @Autowired
    NameController controller;
    @Autowired
    MockMvc mvc;

    @Test
    public void getHelloOkTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/user")
                .param("name","Olya"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello, Olya"));
    }

    @Test
    public void getHelloNullTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/user"))
                .andExpect(status().is4xxClientError());
    }
}
