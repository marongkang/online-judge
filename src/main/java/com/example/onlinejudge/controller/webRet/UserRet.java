package com.example.onlinejudge.controller.webRet;

import com.example.onlinejudge.model.User;
import lombok.Data;

@Data
public class UserRet extends CommonRet{
    private User user;

    public UserRet(Integer code, String msg, User user) {
        super(code, msg);
        this.user = user;
    }
}
