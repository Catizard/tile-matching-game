package com.dreamtea.Service;

import com.dreamtea.Domain.User;
import com.dreamtea.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public User login(String userName, String password) {
        User user = userMapper.login(userName, password);
        if(user == null || user.getUserLogin() != 0) {
            return null;
        }
        System.out.println(user);
        return  user;
    }
}
