package com.user.service;

import com.user.exception.UserNotFoundException;
import com.user.model.PageResponse;
import com.user.model.UserDetailsPatchRequest;
import com.user.model.UserDetailsResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserService {

    /**
     * Method defined to get user details by employeeId
     * @param employeeId
     * @return UserDetailsResponse
     *
     * @throws UserNotFoundException
     */
    public UserDetailsResponse getUser(@NotNull int employeeId ) throws UserNotFoundException;

    /**
     * Method defined to get user details by first name and last name
     * @param firstName
     * @param lastName
     * @return UserDetailsResponse
     *
     * @throws UserNotFoundException
     */
    public List<UserDetailsResponse> getUserByFirstNameAndLastName(@NotNull String firstName, @NonNull String lastName) throws UserNotFoundException;

    /**
     * Method defined to partial update user details
     * @param empId
     * @param userDetailsPatchRequest
     *
     * @throws UserNotFoundException
     */
    void updateUserDetails(Integer empId, UserDetailsPatchRequest userDetailsPatchRequest) throws UserNotFoundException;


    /**
     * Method defined to get paginated user details
     * @param pageRequest
     * @return PageResponse
     */
    public PageResponse getAllUsersDetail(PageRequest pageRequest);
}
