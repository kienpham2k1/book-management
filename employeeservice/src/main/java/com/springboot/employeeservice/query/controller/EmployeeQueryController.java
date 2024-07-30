package com.springboot.employeeservice.query.controller;

import com.springboot.employeeservice.query.model.EmployeeResponseModel;
import com.springboot.employeeservice.query.queries.GetAllEmployeesQuery;
import com.springboot.employeeservice.query.queries.GetEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{employeeId}")
    public EmployeeResponseModel getEmployee(@PathVariable String employeeId) {
        GetEmployeeQuery getEmployeeQuery = new GetEmployeeQuery(employeeId);
        EmployeeResponseModel employeeResponseModel = queryGateway.query(getEmployeeQuery, ResponseTypes.instanceOf(EmployeeResponseModel.class))
                .join();
        return employeeResponseModel;
    }

    @GetMapping
    public List<EmployeeResponseModel> getEmployee() {
        GetAllEmployeesQuery getAllEmployeesQuery = new GetAllEmployeesQuery();
        List<EmployeeResponseModel> employeeResponseModel = queryGateway.query(getAllEmployeesQuery, ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class))
                .join();
        return employeeResponseModel;
    }
}
