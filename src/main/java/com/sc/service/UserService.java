package com.sc.service;

import com.sc.po.User;


public interface UserService {

    User checkUser(String username, String password);
}
