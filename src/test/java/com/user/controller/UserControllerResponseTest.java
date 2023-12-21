package com.user.controller;

import com.user.model.UserDetailsResponse;
import com.user.service.UserService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
public class UserControllerResponseTest {

    private final static String GET_USER_DETAILS_BY_ID = "/api/userdetails/{empId}";

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void test_getUserDetails_should_return_success() throws Exception {
        //set
        UserDetailsResponse userDetailsResponse = Instancio.create(UserDetailsResponse.class);
        Mockito.when(userService.getUser(Mockito.anyInt())).thenReturn(userDetailsResponse);

        //act
        mvc.perform(MockMvcRequestBuilders.get(GET_USER_DETAILS_BY_ID, 1)
                .contentType(MediaType.APPLICATION_JSON))

                //assert
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empId").exists());

    }
}
