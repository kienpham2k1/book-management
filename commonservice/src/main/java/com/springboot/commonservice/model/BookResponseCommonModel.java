package com.springboot.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseCommonModel {
    private String bookId;
    private String name;
    private String author;
    private boolean isReady;
}
