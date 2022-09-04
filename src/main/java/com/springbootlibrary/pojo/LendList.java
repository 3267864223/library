package com.springbootlibrary.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class LendList {
    private Integer sernum;
    private Integer bookId;
    private Integer readerId;
    private Date lendDate;
    private Date backDate;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public LendList(Integer bookId, Integer readerId, Date lendDate, Date backDate) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.lendDate = lendDate;
        this.backDate = backDate;
    }

    public String getLendDate() {
        return simpleDateFormat.format(this.lendDate);
    }

    public String getBackDate() {
        if (this.backDate==null){ //格式化日期的时候如果日期为null，会报空指针异常
            return null;
        }
        return simpleDateFormat.format(this.backDate);
    }
}
