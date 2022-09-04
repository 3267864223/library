package com.springbootlibrary.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderCard {
    private Integer readerId;
    private String name;
    private String passwd;
    private int cardState;

    public ReaderCard(Integer readerId, String name) {
        this.readerId = readerId;
        this.name = name;
    }
}
