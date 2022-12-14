package com.example.onlinejudge.util;

import java.util.ArrayList;

public class ProcessRet {
    @Override
    public String toString() {
        return "ProcessRet{" +
                "exitStatus=" + exitStatus +
                ", output=" + output +
                '}';
    }

    private int exitStatus;
    private ArrayList<String> output;

    public ProcessRet(int exitStatus, ArrayList<String> output) {
        this.exitStatus = exitStatus;
        this.output = output;
    }

    public int getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(int exitStatus) {
        this.exitStatus = exitStatus;
    }

    public ArrayList<String> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<String> output) {
        this.output = output;
    }
}
