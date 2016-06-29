package com.scut.easyfe.entity.test;

import android.content.res.Resources;

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

    public static ArrayList<Problem> getProblems() {
        Resources resources = App.get().getResources();
        ArrayList<Problem> problems = new ArrayList<>();
        problems.add(new Problem(resources.getString(R.string.question_1),resources.getString(R.string.answer_1)));
        problems.add(new Problem(resources.getString(R.string.question_2),resources.getString(R.string.answer_2)));
        problems.add(new Problem(resources.getString(R.string.question_3),resources.getString(R.string.answer_3)));
        problems.add(new Problem(resources.getString(R.string.question_4),resources.getString(R.string.answer_4)));
        problems.add(new Problem(resources.getString(R.string.question_5),resources.getString(R.string.answer_5)));
        problems.add(new Problem(resources.getString(R.string.question_6),resources.getString(R.string.answer_6)));
        problems.add(new Problem(resources.getString(R.string.question_7),resources.getString(R.string.answer_7)));
        problems.add(new Problem(resources.getString(R.string.question_8),resources.getString(R.string.answer_8)));
        problems.add(new Problem(resources.getString(R.string.question_9),resources.getString(R.string.answer_9)));
        problems.add(new Problem(resources.getString(R.string.question_10),resources.getString(R.string.answer_10)));

        return problems;
    }

    public static String getOtherProblemsText(){
        Resources resources = App.get().getResources();
        return resources.getString(R.string.answer_other);
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
