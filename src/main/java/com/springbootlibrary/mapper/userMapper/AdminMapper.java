package com.springbootlibrary.mapper.userMapper;

import com.springbootlibrary.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AdminMapper {
    //根据id查询用户
    Admin selectById(Integer id);

    int updateAdminPassword(Admin admin);
}
