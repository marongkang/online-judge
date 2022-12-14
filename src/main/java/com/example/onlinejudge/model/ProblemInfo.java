package com.example.onlinejudge.model;


import lombok.Data;

@Data
public class ProblemInfo {
    private Integer id;
    private String description;
    private String sampleInput;
    private String sampleOutput;
}
