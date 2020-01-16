package com.sun.dto.impl;

import com.sun.domain.User;
import com.sun.dto.UserDto;
import com.sun.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: daycase
 * @description:
 * @author: Genie Sun
 * @create: 2020-01-15 11:57
 **/
public class UserDtoImpl implements UserDto {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        try {
            String sql = "select * from user where username = ? and password = ?";
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from user";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from user where 1 = 1";
        StringBuilder builder = new StringBuilder(sql);

        Set<String> keySet = condition.keySet();
        List<Object> params = new ArrayList<>();

        for (String key :
                keySet) {
            if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;
            }

            String value = condition.get(key)[0];
            if (value != null && !"".equals(value)) {
                builder.append(" and " + key + " like ? ");
                params.add("%" + value + "%");
            }
        }

        builder.append(" limit ?,? ");
        params.add(start);
        params.add(rows);
        sql = builder.toString();
        System.out.println(sql);
        System.out.println(params);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class), params.toArray());
    }

    public int findTotalCount(Map<String, String[]> condition) {
        //1.定义模板初始化sql
        String sql = "select count(*) from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {

            //排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)) {
                //有值
                sb.append(" and " + key + " like ? ");
                params.add("%" + value + "%");//？条件的值
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);

        return jdbcTemplate.queryForObject(sb.toString(), Integer.class, params.toArray());
    }

    @Override
    public void addUser(User user) {
        String sql = "insert into user value(null,?,?,?,?,?,?,null,null)";
        jdbcTemplate.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void update(User user) {
        String sql = "update user set name = ?,gender = ? ,age = ? , address = ? , qq = ?, email = ? where id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(), user.getId());    }

    @Override
    public User findUserById(String id) {
        String sql = "select * from user where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),Integer.parseInt(id));
    }

    @Override
    public int deleteUser(int id) {
        String sql = "delete from user where id = ?";
        return jdbcTemplate.update(sql,id);
    }
}
