package com.example.onlinejudge.controller;


import com.example.onlinejudge.model.Problem;
import com.example.onlinejudge.model.ProblemInfo;
import com.example.onlinejudge.service.JudgeRet;
import com.example.onlinejudge.service.JudgeService;
import com.example.onlinejudge.service.ProblemService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/oj")
public class OnlineJudgeController {

    @Autowired
    private JudgeService judgeService;

    @Autowired
    private ProblemService problemService;

    @PostMapping
    @ResponseBody
    public JudgeRet codeSubmit(@RequestBody String submit) throws IOException, InterruptedException {
        var obj = new JSONObject(submit);
        String submitCode = obj.getString("code");
        int problemId = obj.getInt("problemId");
        var ret = judgeService.judge(problemId, submitCode);

        return ret;
    }

    @GetMapping
    @ResponseBody
    @RequestMapping("/prob")
    public List<Problem> getProblemList() {
        return problemService.getProblemList();
    }

    @GetMapping
    @ResponseBody
    @RequestMapping("/probInfo")
    public ProblemInfo getProblemInfo(int id) {
        return problemService.getProblemInfoById(id);
    }
}
