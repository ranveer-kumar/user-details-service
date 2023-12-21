package com.user.datasource.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "USER_DETAIL")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "emp_id", columnDefinition = "INT")
    private Integer empId;
    @Column(name = "title", columnDefinition = "VARCHAR(100)")
    private String title;
    @Column(name = "first_name", columnDefinition = "VARCHAR(100)")
    private String firstName;
    @Column(name = "last_name", columnDefinition = "VARCHAR(100)")
    private String lastName;
    @Column(name = "gender", columnDefinition = "CHAR(10)")
    private String gender;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "emp_id")
    private List<Address> addresses = new ArrayList<>();


}

