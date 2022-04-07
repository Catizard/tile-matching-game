package com.dreamtea.Boot.Service;

import com.dreamtea.Boot.Domain.User;
import com.dreamtea.Boot.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static com.dreamtea.Boot.Configurer.WebConfigurer.REDIS_PLAYERNAME_PREFIX;
import static com.dreamtea.Boot.Configurer.WebConfigurer.REDIS_REMOTETOKEN_PREFIX;

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
        String genToken = UUID.randomUUID().toString();
        String token = REDIS_REMOTETOKEN_PREFIX + genToken, name = REDIS_PLAYERNAME_PREFIX + userName;
        redisService.set(token, name);
        redisService.set(name, token);
        return user;
    }

    public void logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        redisService.del(token);
    }
}
