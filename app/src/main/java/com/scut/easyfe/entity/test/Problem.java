package com.scut.easyfe.entity.test;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.entity.BaseEntity;

import java.util.ArrayList;

/**
 * 问题及帮助类问题实体类
 * Created by jay on 16/3/29.
 */
public class Problem extends BaseEntity {
    private String question = "";
    private String answer = "";

    public Problem(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public static ArrayList<Problem> getTestProblems() {
        ArrayList<Problem> problems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            problems.add(new Problem("我是问题 " + i, App.get().getResources().getString(R.string.answer_example)));
        }
        return problems;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
