package com.sun.dto;

import com.sun.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDto {

    public List<User> findAll();

    int findTotalCount(Map<String, String[]> condition);

    User findUserByUsernameAndPassword(String username, String password);

    List<User> findByPage(int start, int rows, Map<String,String[]> condition);

    void addUser(User user);

    void update(User user);

    User findUserById(String id);

    int deleteUser(int id);
}
