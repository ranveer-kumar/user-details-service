package com.user.service.impl;

import com.user.datasource.entity.Address;
import com.user.datasource.entity.User;
import com.user.datasource.repository.AddressRepository;
import com.user.datasource.repository.UserRepository;
import com.user.exception.OperationErrorException;
import com.user.exception.UserNotFoundException;
import com.user.model.PageResponse;
import com.user.model.UserAddressResponse;
import com.user.model.UserDetailsPatchRequest;
import com.user.model.UserDetailsResponse;
import com.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    /**
     * Fall back method defined for update operation exception
     * @param throwable
     * @throws OperationErrorException
     */
    public void fallbackUpdateUser(Throwable throwable) throws OperationErrorException {
        throw new OperationErrorException("Error while updating user details");
    }

    @Override
    public UserDetailsResponse getUser(int employeeId) throws UserNotFoundException {
        User userDetails = userRepository.getUsers(employeeId);
        if (Objects.isNull(userDetails)) {
            log.error("User not found for employeeId: {}", employeeId);
            throw new UserNotFoundException("User Details Not Found.");
        }
        log.info("User Details found for employeeId: {}", employeeId);

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        BeanUtils.copyProperties(userDetails, userDetailsResponse);
        List<UserAddressResponse> addressResponses = getUserAddressResponses(userDetails);
        userDetailsResponse.setAddress(addressResponses);
        return userDetailsResponse;
    }

    private static List<UserAddressResponse> getUserAddressResponses(User userDetails) {
        List<UserAddressResponse> addressResponses = new ArrayList<>();
        userDetails.getAddresses().forEach(address -> {
            UserAddressResponse userAddressResponse = new UserAddressResponse();
            BeanUtils.copyProperties(address, userAddressResponse);
            addressResponses.add(userAddressResponse);
        });
        return addressResponses;
    }

    @Override
    public List<UserDetailsResponse> getUserByFirstNameAndLastName(String firstName, @NonNull String lastName) throws UserNotFoundException {
        List<User> userDetails = userRepository.getUsersByFirstNameLastName(firstName, lastName);
        if (CollectionUtils.isEmpty(userDetails)) {
            log.error("User not found for firstName: {}, lastName: {}", firstName, lastName);
            throw new UserNotFoundException("User Details Not Found.");
        }
        log.info("User Details found for for firstName: {}, lastName: {}", firstName, lastName);
        List<UserDetailsResponse> userDetailsResponses = new ArrayList<UserDetailsResponse>();

        userDetails.forEach(user -> {
            UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
            BeanUtils.copyProperties(user, userDetailsResponse);
            List<UserAddressResponse> addressResponses = getUserAddressResponses(user);
            userDetailsResponse.setAddress(addressResponses);
            userDetailsResponses.add(userDetailsResponse);
        });
        return userDetailsResponses;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @CircuitBreaker(name = "updateUserDetails", fallbackMethod = "fallbackUpdateUser")
    public void updateUserDetails(Integer empId, UserDetailsPatchRequest userDetailsPatchRequest) throws UserNotFoundException {
        User user = userRepository.getUsers(empId);
        if (Objects.isNull(user)) {
            log.error("User not found for empId: {}", empId);
            throw new UserNotFoundException("User Details Not Found.");
        }

        setUserFields(userDetailsPatchRequest, user);
        userRepository.save(user);

        userDetailsPatchRequest.getAddresses().forEach(address -> {
            Optional<Address> add = addressRepository.findById(address.getAddressId());
            if(add.isPresent()) {
                Address addressObj = add.get();
                if (!StringUtils.isEmpty(address.getCity())) {
                    addressObj.setCity(address.getCity());
                }
                if (!StringUtils.isEmpty(address.getStreet())) {
                    addressObj.setStreet(address.getStreet());
                }
                if (!StringUtils.isEmpty(address.getState())) {
                    addressObj.setState(address.getState());
                }
                if (!StringUtils.isEmpty(address.getPostcode())) {
                    addressObj.setPostcode(address.getPostcode());
                }
                addressRepository.save(addressObj);
            }
        });
    }

    private static void setUserFields(UserDetailsPatchRequest userDetailsPatchRequest, User user) {
        if (Objects.nonNull(userDetailsPatchRequest.getFirstName()) || !StringUtils.isEmpty(userDetailsPatchRequest.getFirstName())) {
            user.setFirstName(userDetailsPatchRequest.getFirstName());
        }
        if (Objects.nonNull(userDetailsPatchRequest.getLastName()) || !StringUtils.isEmpty(userDetailsPatchRequest.getLastName())) {
            user.setLastName(userDetailsPatchRequest.getLastName());
        }
        if (Objects.nonNull(userDetailsPatchRequest.getGender()) || !StringUtils.isEmpty(userDetailsPatchRequest.getGender())) {
            user.setGender(userDetailsPatchRequest.getGender());
        }
        if (Objects.nonNull(userDetailsPatchRequest.getTitle()) || !StringUtils.isEmpty(userDetailsPatchRequest.getTitle())) {
            user.setTitle(userDetailsPatchRequest.getTitle());
        }
    }

    @Override
    public PageResponse getAllUsersDetail(PageRequest pageRequest) {
        Page<User> userPage = userRepository.findAll(pageRequest);
        List<UserDetailsResponse> userDetailsResponses = userPage.getContent()
                .stream()
                .map(this::convertToUserDetailsResponse)
                .collect(Collectors.toList());

        PageResponse pageResponse = new PageResponse();
        pageResponse.setContent(userDetailsResponses);
        pageResponse.setTotalPages(userPage.getTotalPages());
        pageResponse.setTotalElements(userPage.getTotalElements());

        return pageResponse;
    }


    private UserDetailsResponse convertToUserDetailsResponse(User user) {
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        BeanUtils.copyProperties(user, userDetailsResponse);
        userDetailsResponse.setAddress(convertToAddressResponses(user.getAddresses()));

        return userDetailsResponse;
    }

    private List<UserAddressResponse> convertToAddressResponses(List<Address> addresses) {
        return addresses.stream()
                .map(this::convertToAddressResponse)
                .collect(Collectors.toList());
    }

    private UserAddressResponse convertToAddressResponse(Address address) {
        UserAddressResponse addressResponse = new UserAddressResponse();
        BeanUtils.copyProperties(address, addressResponse);
        return addressResponse;
    }
}
