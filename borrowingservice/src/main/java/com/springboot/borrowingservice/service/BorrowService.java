package com.springboot.borrowingservice.service;

import com.springboot.borrowingservice.query.model.BorrowingResponseModel;

import java.util.List;

public interface BorrowService {
    void createBorrowBook();
    List<BorrowingResponseModel> getAll();
}
