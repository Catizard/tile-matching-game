package com.dreamtea.Service;

import com.dreamtea.Domain.User;

public interface UserService {

    User login(String userName, String password);
}
