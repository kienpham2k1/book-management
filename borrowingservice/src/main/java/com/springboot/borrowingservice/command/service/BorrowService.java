package com.springboot.borrowingservice.command.service;

import com.springboot.borrowingservice.command.data.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowService {
    @Autowired
    private BorrowingRepository repository;

    public String findIdBorrowing(String employeeId, String bookId) {

        return repository.findByEmployeeIdAndBookIdAndReturnDateIsNull(employeeId, bookId).getId();
    }
}
