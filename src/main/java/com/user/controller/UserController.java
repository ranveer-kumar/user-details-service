package com.user.controller;


import com.user.api.UserApi;
import com.user.model.PageResponse;
import com.user.model.UserDetailsPatchRequest;
import com.user.model.UserDetailsResponse;
import com.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;


    /**
     * GET /api/userdetails/empID
     *
     * @param userId Numeric ID of the user to get (required)
     * @return ResponseEntity<UserDetailsResponse>
     * @throws Exception
     */
    @Override
    public ResponseEntity<UserDetailsResponse> getUserDetails(Integer userId) throws Exception {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    /**
     * GET /api/userdetails/firstname/lastname
     *
     * @param firstName First name of the user
     * @param lastName Last name of the user
     * @return ResponseEntity<List<UserDetailsResponse>>
     * @throws Exception
     */
    @Override
    public ResponseEntity<List<UserDetailsResponse>> getUserDetailsByFirstNameLastName(String firstName, String lastName) throws Exception {
        return ResponseEntity.ok(userService.getUserByFirstNameAndLastName(firstName.trim().toUpperCase(), lastName.trim().toUpperCase()));
    }

    /**
     * PATCH /api/userdetails/empId
     *
     * @param empId Numeric ID of the user to update (required)
     * @param userDetailsPatchRequest Updated user details (required)
     * @return ResponseEntity<Void>
     * @throws Exception
     */
    @Override
    public ResponseEntity<Void> patchUserDetails(Integer empId, UserDetailsPatchRequest userDetailsPatchRequest) throws Exception {
        userService.updateUserDetails(empId, userDetailsPatchRequest);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/userdetails
     *
     * @param pageNo Page number (optional, default to 0)
     * @param offset Offset of the page (optional, default to 10)
     * @return ResponseEntity<PageResponse>
     * @throws Exception
     */
    @Override
    public ResponseEntity<PageResponse> getAllUsersDetail(Integer pageNo, Integer offset) throws Exception {
        PageRequest pageRequest = PageRequest.of(pageNo, offset);
        return ResponseEntity.ok(userService.getAllUsersDetail(pageRequest));
    }
}
