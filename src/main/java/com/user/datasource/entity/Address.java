package com.user.datasource.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id", columnDefinition = "INT")
    private Integer addressId;

    @Column(name = "emp_id", columnDefinition = "INT")
    private Integer empId;

    @Column(name = "street", columnDefinition = "VARCHAR(100)")
    private String street;

    @Column(name = "state", columnDefinition = "VARCHAR(10)")
    private String state;
    @Column(name = "city", columnDefinition = "VARCHAR(100)")
    private String city;
    @Column(name = "postcode", columnDefinition = "INT")
    private Integer postcode;
}


