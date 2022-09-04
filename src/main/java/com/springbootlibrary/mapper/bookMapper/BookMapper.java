package com.springbootlibrary.mapper.bookMapper;

import com.springbootlibrary.pojo.BookInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BookMapper {
    List<BookInfo> selectAllBook();

    BookInfo selectByBookId(Integer bookId);

    void updateBook(BookInfo bookInfo);

    void deleteBook(Integer bookId);

    //根据书名模糊查询
    List<BookInfo> selectBookByName(String bookName);

    void  insertBook(BookInfo bookInfo);
}
