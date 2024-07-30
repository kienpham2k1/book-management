package com.springboot.bookservice.query.controller;

import com.springboot.bookservice.query.model.BookResponseModel;
import com.springboot.bookservice.query.queries.GetAllBooksQuery;
import com.springboot.bookservice.query.queries.GetBookQuery;
import com.springboot.commonservice.model.BookResponseCommonModel;
import com.springboot.commonservice.query.GetDetailsBookQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
    @Autowired
    QueryGateway queryGateway;

//    @GetMapping("/{bookId}")
//    public BookResponseModel getBooks(@PathVariable String bookId) {
//        GetBookQuery getBooksQuery = new GetBookQuery();
//        getBooksQuery.setBookId(bookId);
//        BookResponseModel bookResponseModel =
//                queryGateway.query(getBooksQuery,
//                                ResponseTypes.instanceOf(BookResponseModel.class))
//                        .join();
//        return bookResponseModel;
//    }

    @GetMapping("/{bookId}")
    public BookResponseCommonModel getBooks(@PathVariable String bookId) {
        GetDetailsBookQuery getBooksQuery = new GetDetailsBookQuery(bookId);
        getBooksQuery.setBookId(bookId);
        BookResponseCommonModel bookResponseModel =
                queryGateway.query(getBooksQuery,
                                ResponseTypes.instanceOf(BookResponseCommonModel.class))
                        .join();
        return bookResponseModel;
    }

    @GetMapping()
    public List<BookResponseModel> getAllBooks() {
        GetAllBooksQuery getAllBooksQuery = new GetAllBooksQuery();
        List<BookResponseModel> bookResponseModel =
                queryGateway.query(getAllBooksQuery,
                                ResponseTypes.multipleInstancesOf(BookResponseModel.class))
                        .join();
        return bookResponseModel;
    }
}
