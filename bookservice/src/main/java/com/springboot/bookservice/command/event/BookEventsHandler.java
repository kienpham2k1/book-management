package com.springboot.bookservice.command.event;

import com.springboot.bookservice.command.data.Book;
import com.springboot.bookservice.command.data.BookRepository;
import com.springboot.commonservice.event.BookRollBackStatusEvent;
import com.springboot.commonservice.event.BookUpdateStatusEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookEventsHandler {
    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void on(BookCreatedEvent event) {
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdatedEvent event) {
        Book book = bookRepository.getById(event.getBookId());
        book.setAuthor(event.getAuthor());
        book.setName(event.getName());
        book.setReady(event.getIsReady());
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdateStatusEvent event) {
        Book book = bookRepository.getById(event.getBookId());
        book.setReady(event.getIsReady());
        bookRepository.save(book);
    }
    @EventHandler
    public void on(BookRollBackStatusEvent event) {
        Book book = bookRepository.getById(event.getBookId());
        book.setReady(event.getIsReady());
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookDeletedEvent event) {
        bookRepository.deleteById(event.getBookId());
    }
}
