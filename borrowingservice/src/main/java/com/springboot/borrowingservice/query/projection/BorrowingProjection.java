package com.springboot.borrowingservice.query.projection;

import com.springboot.borrowingservice.command.data.Borrowing;
import com.springboot.borrowingservice.command.data.BorrowingRepository;
import com.springboot.borrowingservice.query.model.BorrowingResponseModel;
import com.springboot.borrowingservice.query.queries.GetAllBorrowing;
import com.springboot.borrowingservice.query.queries.GetListBorrowingByEmployeeQuery;
import com.springboot.borrowingservice.service.BorrowService;
import com.springboot.commonservice.model.BookResponseCommonModel;
import com.springboot.commonservice.model.EmployeeResponseCommonModel;
import com.springboot.commonservice.query.GetDetailsBookQuery;
import com.springboot.commonservice.query.GetDetailsEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
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
    private BorrowingRepository borrowRepository;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<BorrowingResponseModel> handle(GetAllBorrowing getAllBorrowing) {
        List<BorrowingResponseModel> list = new ArrayList<>();
        borrowRepository.findAll().forEach(e -> {
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(e, model);
            GetDetailsBookQuery getDetailsBookQuery = new GetDetailsBookQuery(e.getBookId());
            BookResponseCommonModel bookResponseCommonModel = queryGateway.query(getDetailsBookQuery,
                    ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
            model.setNameBook(bookResponseCommonModel.getName());
            GetDetailsEmployeeQuery getDetailsEmployeeQuery = new GetDetailsEmployeeQuery(e.getEmployeeId());
            EmployeeResponseCommonModel employeeResponseCommonModel = queryGateway.query(getDetailsEmployeeQuery,
                    ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
            model.setNameEmployee(employeeResponseCommonModel.getFirstName().concat(" " + employeeResponseCommonModel.getLastName()));

            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<BorrowingResponseModel> handle(GetListBorrowingByEmployeeQuery query) {
        List<BorrowingResponseModel> list = new ArrayList<>();
        List<Borrowing> borrowingList = borrowRepository.findByEmployeeIdAndReturnDateIsNull(query.getEmployeeId());
        borrowingList.forEach(e -> {
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(e, model);
            GetDetailsBookQuery getDetailsBookQuery = new GetDetailsBookQuery(e.getBookId());
            BookResponseCommonModel bookResponseCommonModel = queryGateway.query(getDetailsBookQuery,
                    ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
            model.setNameBook(bookResponseCommonModel.getName());
            GetDetailsEmployeeQuery getDetailsEmployeeQuery = new GetDetailsEmployeeQuery(e.getEmployeeId());
            EmployeeResponseCommonModel employeeResponseCommonModel = queryGateway.query(getDetailsEmployeeQuery,
                    ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
            model.setNameEmployee(employeeResponseCommonModel.getFirstName().concat(" " + employeeResponseCommonModel.getLastName()));
            list.add(model);
        });
        return list;
    }
}
