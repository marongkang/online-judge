package com.example.onlinejudge;


import com.example.onlinejudge.controller.OnlineJudgeController;
import com.example.onlinejudge.service.JudgeCode;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;

@SpringBootTest
class OnlineJudgeApplicationTests {

    @Autowired
    private OnlineJudgeController controller;

    @Test
    public void basicAcceptedTest() throws IOException, InterruptedException {
        var map = new HashMap<String, String>();
        map.put("code",
                "#include <bits/stdc++.h>\n" +
                "using namespace std;\n" +
                "\n" +
                "\n" +
                "int main() {\n" +
                "    int a, b;\n" +
                "    cin >> a >> b;\n" +
                "    cout << a + b << endl;\n" +
                "}");
        map.put("problemId", "1");
        JSONObject obj = new JSONObject(map);
        var ret = controller.codeSubmit(obj.toString());
        assert ret.getJudgeCode() == JudgeCode.ACCEPTED : "it must be accepted";
    }

    @Test
    public void basicProblemInfoTest() {
        System.out.println(controller.getProblemInfo(1).toString());
    }

}
