package com.springboot.employeeservice.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestModel {
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;
}