package com.springboot.borrowingservice.query.projection;

import com.springboot.borrowingservice.command.data.BorrowingRepository;
import com.springboot.borrowingservice.query.model.BorrowingResponseModel;
import com.springboot.borrowingservice.query.queries.GetAllBorrowing;
import com.springboot.borrowingservice.query.queries.GetListBorrowingByEmployeeQuery;
import com.springboot.borrowingservice.service.BorrowService;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BorrowingProjection {
    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<BorrowingResponseModel> handle(GetAllBorrowing getAllBorrowing) {
//        List<BorrowingResponseModel> list = new ArrayList<>();
//        borrowingRepository.findAll().forEach(e -> {
//            BorrowingResponseModel model = new BorrowingResponseModel();
//            BeanUtils.copyProperties(e, model);
//            list.add(model);
//        });
//        return list;
        return  borrowService.getAll();
    }

    @QueryHandler
    public List<BorrowingResponseModel> handle(GetListBorrowingByEmployeeQuery getListBorrowingByEmployeeQuery) {
        List<BorrowingResponseModel> list = new ArrayList<>();
        borrowingRepository.findAll().forEach(e -> {
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(e, model);
            list.add(model);
        });
        return list;
    }
}
