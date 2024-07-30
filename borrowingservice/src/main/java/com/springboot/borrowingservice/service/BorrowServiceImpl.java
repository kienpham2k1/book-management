package com.springboot.borrowingservice.service;

import com.springboot.borrowingservice.command.data.BorrowingRepository;
import com.springboot.borrowingservice.query.model.BorrowingResponseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Override
    public void createBorrowBook() {

    }

    public List<BorrowingResponseModel> getAll() {
        List<BorrowingResponseModel> list = new ArrayList<>();
        borrowingRepository.findAll().forEach(e -> {
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(e, model);
            list.add(model);
        });
        return list;
    }
}
