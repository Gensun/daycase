package com.sun.server.impl;

import com.sun.domain.PageBean;
import com.sun.domain.User;
import com.sun.dto.UserDto;
import com.sun.dto.impl.UserDtoImpl;
import com.sun.server.UserService;

import java.util.List;
import java.util.Map;

/**
 * @program: daycase
 * @description:
 * @author: Genie Sun
 * @create: 2020-01-15 14:00
 **/
public class UserServiceImpl implements UserService {

    private UserDto userDto = new UserDtoImpl();

    @Override
    public User login(User user) {
        return userDto.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public List<User> findAll() {
        return userDto.findAll();
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {

        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);

        if (currentPage <= 0) {
            currentPage = 1;
        }

        PageBean<User> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setRows(rows);

        int totalCount = userDto.findTotalCount(condition);
        pageBean.setTotalCount(totalCount);

        int start = (currentPage - 1) * rows;
        List<User> list = userDto.findByPage(start, rows, condition);
        pageBean.setList(list);

        int totalPage = (totalCount % rows) == 0 ? totalCount / rows : (totalCount / rows) + 1;
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }

    @Override
    public void addUser(User user) {
        userDto.addUser(user);
    }

    @Override
    public void update(User user) {
        userDto.update(user);
    }

    @Override
    public User findUserById(String id) {
        return userDto.findUserById(id);
    }

    @Override
    public void delete(int id) {
        userDto.deleteUser(id);
    }

    @Override
    public void deletes(String[] ids) {
        if (ids.length <= 0 || ids == null) return;
        for (String id :
                ids) {
            userDto.deleteUser(Integer.parseInt(id));
        }
    }
}
