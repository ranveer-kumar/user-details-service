package com.user.controller;

import com.user.model.PageResponse;
import com.user.model.UserDetailsPatchRequest;
import com.user.model.UserDetailsResponse;
import com.user.service.UserService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void test_getUserDetails() throws Exception {

        //set
        Integer empId = 1;
        UserDetailsResponse userDetailsResponse = Instancio.create(UserDetailsResponse.class);
        Mockito.when(userService.getUser(Mockito.anyInt())).thenReturn(userDetailsResponse);

        //act
        ResponseEntity<UserDetailsResponse> response = userController.getUserDetails(empId);

        //assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody() instanceof UserDetailsResponse);
    }

    @Test
    void test_getUserDetailsByFirstNameLastName() throws Exception {

        //set
        String firstName = "Test";
        String lastName = "Last";
        UserDetailsResponse userDetailsResponse = Instancio.create(UserDetailsResponse.class);
        Mockito.when(userService.getUserByFirstNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(List.of(userDetailsResponse));

        //act
        ResponseEntity<List<UserDetailsResponse>> response = userController.getUserDetailsByFirstNameLastName(firstName, lastName);

        //assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().get(0) instanceof UserDetailsResponse);
    }

    @Test
    void test_getAllUsersDetail() throws Exception {

        //set
        Integer pageNo = 1;
        Integer offset = 1;
        PageResponse pageResponse = Instancio.create(PageResponse.class);
        Mockito.when(userService.getAllUsersDetail(Mockito.any())).thenReturn(pageResponse);

        //act
        ResponseEntity<PageResponse> response = userController.getAllUsersDetail(pageNo, offset);

        //assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody() instanceof PageResponse);
    }

    @Test
    void test_patchUserDetails() throws Exception {

        //set
        Integer empId = 1;
        UserDetailsPatchRequest userDetailsPatchRequest = Instancio.create(UserDetailsPatchRequest.class);
        Mockito.doNothing().when(userService).updateUserDetails(Mockito.anyInt(), Mockito.any());

        //act
        ResponseEntity<Void> response = userController.patchUserDetails(empId, userDetailsPatchRequest);

        //assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }
}
