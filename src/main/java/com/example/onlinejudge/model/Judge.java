package com.example.onlinejudge.model;

import lombok.Data;

@Data
public class Judge {
    private Integer id;
    private String input;
    private String output;
    private Integer timeLimit;
}
