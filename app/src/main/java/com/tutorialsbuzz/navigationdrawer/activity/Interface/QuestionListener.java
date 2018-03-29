package com.tutorialsbuzz.navigationdrawer.activity.Interface;

import com.tutorialsbuzz.navigationdrawer.activity.model.QuestionDataBean;

import java.util.ArrayList;

/**
 * Created by indglobal on 12/1/2015.
 */
public interface QuestionListener
{
    public void addQuestion(QuestionDataBean question);

    public ArrayList<QuestionDataBean> getAllQuestions(String date);

    public int getQuestionCount();
    public void updateRecord(QuestionDataBean question);
    public void showChatData() ;
}
