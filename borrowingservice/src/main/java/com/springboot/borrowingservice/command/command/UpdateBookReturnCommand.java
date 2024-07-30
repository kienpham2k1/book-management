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
public class UpdateBookReturnCommand {
    @TargetAggregateIdentifier
    private String id;
    private String bookId;
    private String employee;
    private Date returnDate;
}
