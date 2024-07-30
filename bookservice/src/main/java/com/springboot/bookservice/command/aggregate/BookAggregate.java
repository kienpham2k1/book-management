package com.springboot.bookservice.command.aggregate;

import com.springboot.bookservice.command.command.CreateBookCommand;
import com.springboot.bookservice.command.command.DeleteBookCommand;
import com.springboot.bookservice.command.command.UpdateBookCommand;
import com.springboot.bookservice.command.event.BookCreatedEvent;
import com.springboot.bookservice.command.event.BookDeletedEvent;
import com.springboot.bookservice.command.event.BookUpdatedEvent;
import com.springboot.commonservice.command.RollBackStatusBookCommand;
import com.springboot.commonservice.command.UpdateStatusBookCommand;
import com.springboot.commonservice.event.BookRollBackStatusEvent;
import com.springboot.commonservice.event.BookUpdateStatusEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@Aggregate
public class BookAggregate {
    @AggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;

    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand) {
        BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
        BeanUtils.copyProperties(createBookCommand, bookCreatedEvent);
        AggregateLifecycle.apply(bookCreatedEvent);
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent event) {
        this.bookId = event.getBookId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @CommandHandler
    public void handleUpdateBook(UpdateBookCommand updateBookCommand) {
        BookUpdatedEvent bookUpdatedEvent = new BookUpdatedEvent();
        BeanUtils.copyProperties(updateBookCommand, bookUpdatedEvent);
        AggregateLifecycle.apply(bookUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(BookUpdatedEvent event) {
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
        this.name = event.getName();
    }

    @CommandHandler
    public void handle(RollBackStatusBookCommand command) {
        BookRollBackStatusEvent event = new BookRollBackStatusEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(BookRollBackStatusEvent event) {
        this.bookId = event.getBookId();
        this.isReady = event.getIsReady();
    }
    @CommandHandler
    public void handle(UpdateStatusBookCommand command) {
        BookUpdateStatusEvent event = new BookUpdateStatusEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(BookUpdateStatusEvent event) {
        this.bookId = event.getBookId();
        this.isReady = event.getIsReady();
    }

    @CommandHandler
    public void handleDeleteBook(DeleteBookCommand deleteBookCommand) {
        BookDeletedEvent bookDeletedEvent = new BookDeletedEvent();
        BeanUtils.copyProperties(deleteBookCommand, bookDeletedEvent);
        AggregateLifecycle.apply(bookDeletedEvent);
    }

    @EventSourcingHandler
    public void on(BookDeletedEvent event) {
        this.bookId = event.getBookId();
    }
}
