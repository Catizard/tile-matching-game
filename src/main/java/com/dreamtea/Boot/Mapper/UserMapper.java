package com.dreamtea.Boot.Mapper;

import com.dreamtea.Boot.Domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User login(@Param("user_name") String userName, @Param("password") String password);
}
