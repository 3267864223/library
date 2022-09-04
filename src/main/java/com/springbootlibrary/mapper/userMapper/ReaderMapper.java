package com.springbootlibrary.mapper.userMapper;

import com.springbootlibrary.pojo.ReaderCard;
import com.springbootlibrary.pojo.ReaderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ReaderMapper {

    ReaderCard selectByReaderCardById(Integer readerCardId);

    List<ReaderInfo> selectAllByReaderInfo();

    ReaderInfo selectByReaderInfoById(Integer readerInfoId);

    int  updateReaderInfo(ReaderInfo readerInfo);

    int deleteReaderInfo(Integer readerInfoId);

    void deleteReaderCard(Integer readerCardId);

    int insertReaderInfo(ReaderInfo readerInfo);

    void insertReaderCard(ReaderCard readerCard);

    void updateReaderCard(ReaderCard readerCard);
}
