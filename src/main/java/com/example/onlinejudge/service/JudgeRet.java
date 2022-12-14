package com.example.onlinejudge.service;


public class JudgeRet {
    @Override
    public String toString() {
        return "JudgeRet{" +
                "judgeCode=" + judgeCode +
                ", msg='" + msg + '\'' +
                '}';
    }

    public JudgeRet(JudgeCode code, String msg) {
        this.judgeCode = code;
        this.msg = msg;
    }

    public JudgeCode getJudgeCode() {
        return judgeCode;
    }

    public void setJudgeCode(JudgeCode judgeCode) {
        this.judgeCode = judgeCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private JudgeCode judgeCode;
    private String msg;
}
