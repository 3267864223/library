package com.springbootlibrary.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderInfo {
    private Integer readerId;
    private String name;
    private String sex;
    private Date birth;
    private String address;
    private String telcode;

    public String getBirth() {
        if (this.birth == null){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(this.birth);
    }
}
