package com.springboot.borrowingservice.command.event;

import com.springboot.borrowingservice.command.data.Borrowing;
import com.springboot.borrowingservice.command.data.BorrowingRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BorrowEventHandler {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @EventHandler
    void on(BorrowCreatedEvent event) {
        Borrowing model = new Borrowing();
        BeanUtils.copyProperties(event, model);
        borrowingRepository.save(model);
    }

    @EventHandler
    void on(BorrowUpdatedEvent event) {
        Borrowing model = new Borrowing();
        BeanUtils.copyProperties(event, model);
        borrowingRepository.save(model);
    }

    @EventHandler
    void on(BorrowDeletedEvent event) {
        borrowingRepository.deleteById(event.getId());
    }
}
