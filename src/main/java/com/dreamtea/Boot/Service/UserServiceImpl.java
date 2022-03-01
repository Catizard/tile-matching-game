package com.dreamtea.Boot.Service;

import com.dreamtea.Boot.Domain.User;
import com.dreamtea.Boot.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public User login(String userName, String password) {
        User user = userMapper.login(userName, password);
        if(user == null || user.getUserLogin() != 0) {
            return null;
        }

        //数据传输格式: token-{token}, name-{name}
        //TODO 为了防止多地同时登陆，这里对 name 和 token 记录了双射来校验，有没有更好的办法？
        String genToken = UUID.randomUUID().toString();
        String token = "token-" + genToken, name = "name-" + userName;
        redisService.set(token, name);
        redisService.set(name, token);
        return user;
    }

    //TODO 也许用户可能没有登录也按到注销,是否要添加一个提示信息表示尚未登陆? 或者只是简单拦截
    public void logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        redisService.del(token);
    }
}
