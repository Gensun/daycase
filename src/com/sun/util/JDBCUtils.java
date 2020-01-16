package com.sun.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @program: daycase
 * @description:
 * @author: Genie Sun
 * @create: 2020-01-15 13:53
 **/
public class JDBCUtils {

    private static DataSource ds;

    static {
        Properties properties = new Properties();
        InputStream inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(inputStream);
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
