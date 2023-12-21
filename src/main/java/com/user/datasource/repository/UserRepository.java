package com.user.datasource.repository;

import com.user.datasource.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT u " +
            "from User u " +
            "left join u.addresses as add " +
            "where u.empId = :empId ")
    User getUsers(@NonNull Integer empId);

    @Query(value = "SELECT u " +
            "from User u " +
            "left join u.addresses as add " +
            "where upper(u.firstName) = :firstName " +
            "and upper(u.lastName) = :lastName")
    List<User> getUsersByFirstNameLastName(@NonNull String firstName, @NonNull String lastName);

}
