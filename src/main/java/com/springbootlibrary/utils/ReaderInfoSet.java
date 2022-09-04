package com.springbootlibrary.utils;

import com.springbootlibrary.pojo.ReaderInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReaderInfoSet {
    private final ReaderInfo readerInfo;

    public ReaderInfoSet(ReaderInfo readerInfo) {
        this.readerInfo = readerInfo;
    }

    public ReaderInfo readerInfoSet(String readerId, String name, String sex, String birth, String address, String telcode) {
        if (readerId != null) {
            readerInfo.setReaderId(Integer.parseInt(readerId));
        }
        readerInfo.setName(name);
        readerInfo.setSex(sex);
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            readerInfo.setBirth(simpleDateFormat.parse(birth));
        } catch (ParseException e) {
            readerInfo.setBirth(null);
        }
        readerInfo.setAddress(address);
        readerInfo.setTelcode(telcode);
        return readerInfo;
    }
}
