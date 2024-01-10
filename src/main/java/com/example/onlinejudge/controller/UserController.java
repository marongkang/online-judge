package com.example.onlinejudge.controller;

import com.example.onlinejudge.controller.webRet.UserRet;
import com.example.onlinejudge.model.User;
import com.example.onlinejudge.service.UserService;
import com.example.onlinejudge.controller.webRet.CommonRet;
import com.example.onlinejudge.controller.webRet.CommonIDRet;
import org.apache.tomcat.util.security.MD5Encoder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public CommonRet login(@RequestBody JSONObject obj) throws NoSuchAlgorithmException {
        String name = obj.getString("name");
        String pwd = obj.getString("pwd");

        // 校参
        if (name.length() == 0 || pwd.length() == 0 || name.length() > 50 || pwd.length() > 50) {
            return new CommonRet(-1, "用户名或密码格式不合法");
        }

        MessageDigest md = MessageDigest.getInstance("MD5");
        String encodedPwd = MD5Encoder.encode(md.digest(pwd.getBytes()));
        User user = userService.getUserByName(name);
        if (user == null) {
            return new CommonRet(-1, "用户不存在");
        }
        if (user.getEncodedPwd().equals(encodedPwd)) {
            return new CommonRet(0, "登陆成功");
        }

        return new CommonRet(-1, "密码错误");
    }

    @PostMapping("/register")
    public CommonIDRet register(@RequestBody Map<String, Object> map) throws NoSuchAlgorithmException {
        String name = (String) map.get("name");
        String pwd = (String) map.get("pwd");

        // 校参
        if (name.length() == 0 || pwd.length() == 0 || name.length() > 50 || pwd.length() > 50) {
            return new CommonIDRet(-1, "用户名或密码格式不合法", -1);
        }

        MessageDigest md = MessageDigest.getInstance("MD5");
        String encodedPwd = MD5Encoder.encode(md.digest(pwd.getBytes()));
        int id = userService.addUser(name, encodedPwd);
        if (id < 0) {
            return new CommonIDRet(-1, "添加用户失败", -1);
        }

        return new CommonIDRet(0, "添加用户成功", id);
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public UserRet getProblemByName(@PathVariable(name = "id") int id) {
        // 校参
        if (id <= 0) {
            return new UserRet(-1, "用户ID不合法", null);
        }

        User user = userService.getUserByID(id);
        if (user == null) {
            return new UserRet(-1, "用户不存在", null);
        }

        return new UserRet(0, "成功查询到用户", user);
    }
}
