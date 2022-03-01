package com.dreamtea.Boot.Service;

import com.dreamtea.Boot.Domain.User;

public interface UserService {

    User login(String userName, String password);
}
