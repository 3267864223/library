package com.springbootlibrary.mapper.LendMapper;

import com.springbootlibrary.pojo.LendList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LendMapper {

    List<LendList> selectAllLend();

    //借还日志中同一本书可以有多个借还记录
    List<LendList> selectByBookId(Integer bookId);

    void insertLend(LendList lendList);

    //借还日志中同一个读者可以有多个借还记录
    List<LendList> selectByReaderId(Integer readerId);

    void updateLendList(LendList lendList);

    int deleteLendListBySernum(Integer sernum);

}
