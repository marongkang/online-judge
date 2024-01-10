package com.example.onlinejudge.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onlinejudge.mapper.JudgeMapper;
import com.example.onlinejudge.mapper.ProblemMapper;
import com.example.onlinejudge.model.Judge;
import com.example.onlinejudge.model.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {
    @Autowired
    private ProblemMapper probDao;

    @Autowired
    private JudgeMapper judgeDao;

    public List<Problem> getProblemList() {
        List<Problem> problems = probDao.selectList(null);
        return problems;
    }

    public Problem getproblemById(int id) {
        return probDao.selectById(id);
    }
    public Problem getproblemByName(String name) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        return probDao.selectOne(queryWrapper.eq("name", name));
    }

    /**
     * @return 成功时返回ID, 失败返回-1
     */
    public int addProblem(String name, String description, String sampleInput, String sampleOutput, String[] inputs, String[] outputs, Integer[] timeLimits) {
        if (inputs.length != outputs.length || inputs.length != timeLimits.length) {
            return -1;
        }
        // 添加problem信息
        if (probDao.insert(new Problem(name, description, sampleInput, sampleOutput)) == 0) {
            return -1;
        }

        // 获取自动生成的ID
        Problem prob = this.getproblemByName(name);
        if (prob == null) {
            return -1;
        }
        int ID = prob.getId();

        // 添加多条评测信息
        for (int i = 0; i < inputs.length; i++) {
            if (judgeDao.insert(new Judge(ID, inputs[i], outputs[i], timeLimits[i])) == 0) {
                return -1;
            }
        }

        return ID;
    }
}

