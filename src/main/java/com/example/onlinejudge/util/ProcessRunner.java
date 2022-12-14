package com.example.onlinejudge.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ProcessRunner {
    public ProcessRet runProcess(String... cmd) throws IOException, InterruptedException {
        // 创建进程
        var builder = new ProcessBuilder(cmd);

        // 重定向标准错误(和标准输出合并)
        builder.redirectErrorStream(true);

        // 运行
        var process = builder.start();

        // 读取输出
        String line;
        ArrayList<String> output = new ArrayList<>();
        var bufferReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((line = bufferReader.readLine()) != null) {
            output.add(line);
        }

        return new ProcessRet(process.waitFor(), output);
    }

    // 单位毫秒
    public ProcessRet runProcessWithTimelimitAndInput(int tl, String input, String... cmd) throws IOException, InterruptedException {
        // 创建进程
        var builder = new ProcessBuilder(cmd);

        // 重定向标准错误(和标准输出合并)
        builder.redirectErrorStream(true);

        // 运行
        var process = builder.start();

        // 指定时间后杀进程
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                process.destroy();
                timer.cancel();
            }
        }, tl);// 设定指定的时间time, 单位毫秒

        // input内容
        var out =  process.getOutputStream();
        out.write(input.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();

        // 读取输出
        String line;
        ArrayList<String> output = new ArrayList<>();
        var bufferReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((line = bufferReader.readLine()) != null) {
            output.add(line);
        }

        int ret = process.waitFor();
        timer.cancel();
        return new ProcessRet(ret, output);
    }
}


