package com.example.onlinejudge.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Judge {
    public Judge(Integer id, String input, String output, Integer timeLimit) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.timeLimit = timeLimit;
    }

    @TableId
    private Integer id;
    private String input;
    private String output;
    @TableField("timeLimit")
    private Integer timeLimit;
}
