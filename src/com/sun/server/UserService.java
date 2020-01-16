package com.sun.server;

import com.sun.domain.PageBean;
import com.sun.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User login(User user);

    List<User> findAll();

    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);

    void addUser(User user);

    void update(User user);

    User findUserById(String id);

    void delete(int id);

    void deletes(String[] ids);
}
