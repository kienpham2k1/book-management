package com.springboot.borrowingservice.query.controller;

import com.springboot.borrowingservice.query.model.BorrowingResponseModel;
import com.springboot.borrowingservice.query.queries.GetAllBorrowing;
import com.springboot.borrowingservice.query.queries.GetListBorrowingByEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/borrowings")
public class BorrowingBookQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<BorrowingResponseModel> getAll() {
        GetAllBorrowing getBorrowingsBook = new GetAllBorrowing();
        List<BorrowingResponseModel> list = queryGateway.query(getBorrowingsBook, ResponseTypes.multipleInstancesOf(BorrowingResponseModel.class)).join();
        return list;
    }

    @GetMapping("/getByEmployeeId")
    public List<BorrowingResponseModel> getAllByEmployeeId(@RequestParam String employeeId) {
        GetListBorrowingByEmployeeQuery getListBorrowingByEmployeeQuery = new GetListBorrowingByEmployeeQuery(employeeId);
        List<BorrowingResponseModel> list = queryGateway.query(getListBorrowingByEmployeeQuery, ResponseTypes.multipleInstancesOf(BorrowingResponseModel.class)).join();
        return list;
    }
}
