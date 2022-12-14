package com.example.onlinejudge.service;


import com.example.onlinejudge.mapper.JudgeMapper;
import com.example.onlinejudge.model.Judge;
import com.example.onlinejudge.util.ProcessRet;
import com.example.onlinejudge.util.ProcessRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class JudgeService {

    @Autowired
    private JudgeMapper dao;
    private AtomicInteger cnt;

    public JudgeService() {
        this.cnt = new AtomicInteger(0);
    }

    // 判断解答problemId号问题的code是否正确
    public JudgeRet judge(int problemId, String code) throws IOException, InterruptedException {
        String basePath = "./judge/cpp";
        if (!Files.exists(Path.of(basePath))) {
            Files.createDirectories(Path.of(basePath));
            Files.createDirectories(Path.of(basePath + "/ret"));
        }

        // 生成存储code的文件路径
        int id = cnt.getAndIncrement() % 10086;
        if (cnt.get() > 10086) {
            cnt.set(0);
        }
        String fileName = String.format(basePath + "/%d.cpp", id);

        // 存储code
        Files.writeString(Paths.get(fileName), new String(code.getBytes(StandardCharsets.UTF_8)));

        // 编译code
        var processRunner = new ProcessRunner();
        String executableName = String.format(basePath + "/ret/%d", id);
        ProcessRet ret = processRunner.runProcess("g++", fileName, "-o", executableName);

        // 编译失败, 返回编译错误
        if (ret.getExitStatus() != 0) {
            System.out.println(ret.getOutput().toString());
            return new JudgeRet(JudgeCode.COMPILE_ERROR, "编译错误");
        }

        // 如果编译成功, 再去数据库查询相关的problem信息
        Judge judge = dao.selectById(problemId);


        // 以给定的时间限制和输入运行程序
        ret = processRunner.runProcessWithTimelimitAndInput(judge.getTimeLimit(), judge.getInput(), executableName);

        if (ret.getExitStatus() == 0) {
            // 安稳的运行结束了, 判断一下答案是否正确

            if (ret.getOutput().toString().equals(judge.getOutput())) {
                return new JudgeRet(JudgeCode.ACCEPTED, "通过");
            }
            return new JudgeRet(JudgeCode.WRONG_ANSWER, "答案错误");
        } else if (ret.getExitStatus() - 128 == 15) {
            return new JudgeRet(JudgeCode.TIME_LIMIT_EXCEPTION, "超时");
        } else if (ret.getExitStatus() - 128 == 11) {
            return new JudgeRet(JudgeCode.SEGMENT_FAULT, "段错误");
        } else if (ret.getExitStatus() - 128 == 8) {
            return new JudgeRet(JudgeCode.ARITHMETIC_EXCEPTION, "算术异常");
        } else {
            return new JudgeRet(JudgeCode.UNKNOWN_ERROR, "未知错误");
        }
    }
}

