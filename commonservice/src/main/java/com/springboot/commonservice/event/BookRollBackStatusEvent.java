package com.springboot.commonservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRollBackStatusEvent {private String bookId;
    private Boolean isReady;
    private String employeeId;
    private String borrowId;
}
