package com.example.onlinejudge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.onlinejudge.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
