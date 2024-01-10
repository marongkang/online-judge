package com.example.onlinejudge.controller;

import com.example.onlinejudge.service.OjService;
import com.example.onlinejudge.controller.webRet.JudgeCode;
import com.example.onlinejudge.controller.webRet.JudgeRet;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/oj")
public class OjController {

    @Autowired
    OjService ojService;

    @PostMapping
    @ResponseBody
    public JudgeRet codeSubmit(@RequestBody String submit) throws IOException, InterruptedException {

        var obj = new JSONObject(submit);
        String submitCode = obj.getString("code");
        int problemId = obj.getInt("problemId");

        // 校参
        if (submitCode.length() == 0 || problemId < 0) {
            return new JudgeRet(-1, JudgeCode.UNKNOWN_ERROR, "非法题号或空代码");
        }

        return ojService.judge(problemId, submitCode);
    }
}
