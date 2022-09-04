package com.springbootlibrary.utils;

import com.springbootlibrary.pojo.BookInfo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookInfoSet {

    private final BookInfo bookInfo;

    public BookInfoSet(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    //封装BookInfo
    public BookInfo bookInfoSet(String name, String author, String publish, String isbn, String introduction, String language, String price, String pubdate, String classId, String pressmark, String state) {
        bookInfo.setName(name);
        bookInfo.setAuthor(author);
        bookInfo.setPublish(publish);
        bookInfo.setIsbn(isbn);
        bookInfo.setIntroduction(introduction);
        bookInfo.setLanguage(language);
        BigDecimal bigDecimal_price = new BigDecimal(price);
        bookInfo.setPrice(bigDecimal_price);
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = simpleDateFormat.parse(pubdate);
            bookInfo.setPubdate(parse);
        } catch (ParseException e) {
            //日期转换失败，将pubdate设置成null
            bookInfo.setPubdate(null);
        }
        bookInfo.setClassId(Integer.parseInt(classId));
        bookInfo.setPressmark(Integer.parseInt(pressmark));
        bookInfo.setState(Integer.parseInt(state));
        return bookInfo;
    }
}
