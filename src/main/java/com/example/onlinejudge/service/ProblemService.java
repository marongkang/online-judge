package com.example.onlinejudge.service;

import com.example.onlinejudge.mapper.ProblemInfoMapper;
import com.example.onlinejudge.mapper.ProblemMapper;
import com.example.onlinejudge.model.Problem;
import com.example.onlinejudge.model.ProblemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {
    @Autowired
    private ProblemMapper dao;

    @Autowired
    private ProblemInfoMapper infoDao;

    public List<Problem> getProblemList() {
        List<Problem> problems = dao.selectList(null);
        return problems;
    }

    public ProblemInfo getProblemInfoById(int id) {
        return infoDao.selectById(id);
    }
}

