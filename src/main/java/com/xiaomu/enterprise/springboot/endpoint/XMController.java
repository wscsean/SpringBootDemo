package com.xiaomu.enterprise.springboot.endpoint;

import com.xiaomu.enterprise.springboot.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/xiaomu")
@RestController
public class XMController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(XMController.class);

    @Autowired
    private Environment env;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test/{name}")
    @ResponseBody
    public ResponseEntity<String> testUrl(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    @PathVariable final String name)
    {
        LOGGER.info("insert user url begin");
        final String sql = "insert into user(id,name) values(null, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int resRow = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, name);
                return ps;
            }
        },keyHolder);
        return new ResponseEntity<String>("你好，" + name + "！", HttpStatus.OK);
    }

    @GetMapping("/test/all/users")
    @ResponseBody
    public ResponseEntity<List<String>> getAllUsers(HttpServletRequest request,
                                          HttpServletResponse response)
    {
        LOGGER.info("get all users begin");
        String sql = "select name from user";
        List<User> users = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setName(resultSet.getString("name"));
                return user;
            }
        });

        List list = new ArrayList();
        for(User s : users){
            list.add(s.getName());
        }
        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }
}
