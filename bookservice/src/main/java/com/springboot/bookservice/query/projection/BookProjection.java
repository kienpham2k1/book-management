package com.springboot.bookservice.query.projection;

import com.springboot.bookservice.command.data.Book;
import com.springboot.bookservice.command.data.BookRepository;
import com.springboot.bookservice.query.model.BookResponseModel;
import com.springboot.bookservice.query.queries.GetAllBooksQuery;
import com.springboot.bookservice.query.queries.GetBookQuery;
import com.springboot.commonservice.model.BookResponseCommonModel;
import com.springboot.commonservice.query.GetDetailsBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler

    public BookResponseModel handle(GetBookQuery getBooksQuery) {
        BookResponseModel model = new BookResponseModel();
        Book book = bookRepository.getById(getBooksQuery.getBookId());
        BeanUtils.copyProperties(book, model);
        return model;
    }

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBooksQuery getAllBooksQuery) {
        List<Book> listEntity = bookRepository.findAll();
        List<BookResponseModel> listbook = new ArrayList<>();
        listEntity.forEach(s -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(s, model);
            listbook.add(model);
        });
        return listbook;
    }

    @QueryHandler
    public BookResponseCommonModel handle(GetDetailsBookQuery getDetailsBookQuery) {
        BookResponseCommonModel model = new BookResponseCommonModel();
        Book book = bookRepository.getById(getDetailsBookQuery.getBookId());
        BeanUtils.copyProperties(book, model);
        return model;
    }

}
