package com.dtover.blog.service;

import com.dtover.blog.po.User;

public interface UserService {

    User checkUser(String username, String password);
}
