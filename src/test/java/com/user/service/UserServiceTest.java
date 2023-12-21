package com.user.service;

import com.user.datasource.entity.Address;
import com.user.datasource.entity.User;
import com.user.datasource.repository.AddressRepository;
import com.user.datasource.repository.UserRepository;
import com.user.exception.UserNotFoundException;
import com.user.model.PageResponse;
import com.user.model.UserAddressRequest;
import com.user.model.UserDetailsPatchRequest;
import com.user.model.UserDetailsResponse;
import com.user.service.impl.UserServiceImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Test
    void test_getUser_should_return_success_response () throws Exception {
        //set
        Integer empId = 1;
        User user = Instancio.create(User.class);
        Mockito.when(userRepository.getUsers(empId)).thenReturn(user);

        //act
        UserDetailsResponse userDetailsResponse = userService.getUser(empId);

        //assert
        Assertions.assertNotNull(userDetailsResponse);
        Assertions.assertNotNull(userDetailsResponse.getAddress());
    }

    @Test
    void test_getUser_when_not_found_should_throw_exception () throws Exception {
        //set
        Integer empId = 1;
        User user = null;
        Mockito.when(userRepository.getUsers(empId)).thenReturn(user);

        //act
        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class,
                () -> {
                    userService.getUser(empId);
                });

        //assert
        Assertions.assertNotNull(exception);
    }

    @Test
    void test_getUserByFirstNameAndLastName_should_return_success_response () throws Exception {
        //set
        String firstName = "first";
        String lastName = "last";
        User user = Instancio.create(User.class);
        Mockito.when(userRepository.getUsersByFirstNameLastName(firstName, lastName)).thenReturn(List.of(user));

        //act
        List<UserDetailsResponse> userDetailsResponse = userService.getUserByFirstNameAndLastName(firstName, lastName);

        //assert
        Assertions.assertNotNull(userDetailsResponse);
        Assertions.assertEquals(1, userDetailsResponse.size());
    }

    @Test
    void test_getUserByFirstNameAndLastName_when_not_found_should_throw_exception () throws Exception {
        //set
        String firstName = "first";
        String lastName = "last";
        Mockito.when(userRepository.getUsersByFirstNameLastName(firstName, lastName)).thenReturn(Collections.EMPTY_LIST);

        //act
        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class,
                () -> {
                    userService.getUserByFirstNameAndLastName(firstName, lastName);
                });

        //assert
        Assertions.assertNotNull(exception);
    }

    @Test
    void test_updateUserDetails_should_return_success_response () throws Exception {
        //set
        Integer empId = 1;
        UserDetailsPatchRequest userDetailsPatchRequest = Instancio.create(UserDetailsPatchRequest.class);
        UserAddressRequest userAddressRequest = Instancio.create(UserAddressRequest.class);
        userAddressRequest.setAddressId(1);
        userDetailsPatchRequest.setAddresses(List.of(userAddressRequest));

        User user = Instancio.create(User.class);
        Address address = Instancio.create(Address.class);
        Mockito.when(userRepository.getUsers(empId)).thenReturn(user);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        Mockito.when(addressRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(address));
        Mockito.when(addressRepository.save(Mockito.any())).thenReturn(address);

        //act
        userService.updateUserDetails(empId, userDetailsPatchRequest);

        //assert
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void test_updateUserDetails_when_not_found_should_throw_exception () throws Exception {
        //set
        Integer empId = 1;
        UserDetailsPatchRequest userDetailsPatchRequest = Instancio.create(UserDetailsPatchRequest.class);
        User user = Instancio.create(User.class);
        Mockito.when(userRepository.getUsers(empId)).thenReturn(null);

        //act
        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class,
                () -> {
                    userService.updateUserDetails(empId, userDetailsPatchRequest);
                });

        //assert
        Assertions.assertNotNull(exception);
    }

    @Test
    void test_getAllUsersDetail_should_return_success_response () throws Exception {
        //set
        PageRequest pageRequest = PageRequest.of(0, 10);
        User user = Instancio.create(User.class);
        Page<User> page = new PageImpl<>(List.of(user), pageRequest, 1);
        Mockito.when(userRepository.findAll(pageRequest)).thenReturn(page);

        //act
        PageResponse pageResponse = userService.getAllUsersDetail(pageRequest);

        //assert
        Assertions.assertNotNull(pageResponse);
        assertEquals(1, pageResponse.getTotalPages());
        assertEquals(1, pageResponse.getTotalElements());
        assertEquals(1, pageResponse.getContent().size());
    }

}
