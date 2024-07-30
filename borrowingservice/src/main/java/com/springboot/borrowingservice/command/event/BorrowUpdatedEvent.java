package com.springboot.borrowingservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowUpdatedEvent {
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowingDate;
}
