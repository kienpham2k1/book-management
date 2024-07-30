package com.springboot.borrowingservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBorrowCommand {
    @TargetAggregateIdentifier
    private String id;

    private String bookId;
    private String employeeId;
    private Date borrowingDate;

    public CreateBorrowCommand(String bookId, String employeeId, Date borrowingDate, String id) {
        super();
        this.bookId = bookId;
        this.employeeId = employeeId;
        this.borrowingDate = borrowingDate;
        this.id = id;
    }
}
