package com.tutorialsbuzz.navigationdrawer.activity.model;

import java.io.Serializable;

/**
 * Created by indglobal on 12/1/2015.
 */
public class QuestionDataBean implements Serializable
{
    String id;
    String date;
    String total_question;
    String question_category;
    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    String answer;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    String language;


    public QuestionDataBean(String id, String question_category, String question, String answer, String option1, String option2, String option3, String option4, String total_question, String date,String choosedlanguage,String description)
    {



        this.id=id;
        this.question_category=question_category;
        this.question=question;
        this.answer=answer;
        this.option1=option1;
        this.option2=option2;
        this.option3=option3;
        this.option4=option4;

        this.total_question=total_question;
        this.date=date;
        this.language=choosedlanguage;
        this.description=description;

    }


public QuestionDataBean(String question, String answer, String option1, String option2, String option3, String option4)
{
    this.question=question;
    this.option1=option1;
    this.option2=option2;
    this.option3=option3;
    this.option4=option4;
    this.answer=answer;
}


    public void setQuestion_category(String question_category) {
        this.question_category = question_category;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswer(String answer) {
        this.answer = answer;}
    public void setTotal_question(String total_question) {
        this.total_question = total_question;
    }

    public void setDate(String date) {
        this.date = date;
    }








    public String getQuestion_category() {
        return question_category;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getAnswer() {
        return answer;
    }

    public String getTotal_question() {
        return total_question;
    }

    public String getDate() {
        return date;
    }
   }
