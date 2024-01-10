package com.example.onlinejudge.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onlinejudge.mapper.UserMapper;
import com.example.onlinejudge.model.Problem;
import com.example.onlinejudge.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public int addUser(String name, String encodedPwd) {

        if (userMapper.insert(new User(name, encodedPwd)) == 0) {
            return -1;
        }

        // 获取自动生成的ID
        User user = this.getUserByName(name);
        if (user == null) {
            return -1;
        }
        int ID = user.getId();
        return ID;
    }

    public User getUserByID(int id) {
        return userMapper.selectById(id);
    }

    public User getUserByName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        return userMapper.selectOne(queryWrapper.eq("name", name));
    }
}
