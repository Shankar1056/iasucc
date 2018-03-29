package com.tutorialsbuzz.navigationdrawer.activity.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tutorialsbuzz.navigationdrawer.activity.Interface.QuestionListener;
import com.tutorialsbuzz.navigationdrawer.activity.model.QuestionDataBean;

import java.util.ArrayList;

/**
 * Created by indglobal on 12/1/2015.
 */
    public class DBHelper extends SQLiteOpenHelper implements QuestionListener {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "UccQuestionDatabase1.db";
    private static final String TABLE_NAME = "question_table1";
    private static final String KEY_ID = "_id";

    private static final String KEY_QUESTION_CATEGORY = "_question_category";
    private static final String KEY_QUESTION = "_question";
    private static final String KEY_ANSWER = "_answer";
    private static final String KEY_OPTION1 = "_option1";
    private static final String KEY_OPTION2 = "_option2";
    private static final String KEY_OPTION3 = "_option3";
    private static final String KEY_OPTION4 = "_option4";
    private static final String KEY_TOTAL_QUESTION = "_total_question";
    private static final String KEY_DATE = "_date";
    ArrayList<String> options = new ArrayList<String>();
    ArrayList<String> getOptions = new ArrayList<String>();
    String option[]=new String[10];
    String CREATE_TABLE = " create table if not exists question_table1 (_question_category varchar, _question varchar, _option1 varchar, _option2 varchar, _option3 varchar, _option4 varchar, _answer varchar, _total_question varchar, _date varchar)";
    String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE_NAME;
    SQLiteDatabase db;
    int num = 0;
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void addQuestion(QuestionDataBean question) {

        db = this.getWritableDatabase();
        try {


      db.execSQL("insert into " + TABLE_NAME + " values('" + question.getQuestion_category()+ "','"  + question.getQuestion() + "','" + question.getOption1() + "','" + question.getOption2() + "','" +  question.getOption3() + "','" + question.getOption4()  + "','" + question.getAnswer()+ "','" + question.getTotal_question()+ "','"+ question.getDate()+"')");
//db.execSQL("update "+ TABLE_NAME+" set "+KEY_ID+"='"+question.getId()+ "'," +KEY_QUESTION_CATEGORY+"='"+question.getQuestion_category()+ "'," +"' WHERE id=6 ");
            }
      catch (Exception e) {
            Log.e("problem", e + "");
        }

    }

    @Override
    public void updateRecord(QuestionDataBean question)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_QUESTION_CATEGORY, question.getQuestion_category());
        values.put(KEY_QUESTION, question.getQuestion());
        values.put(KEY_OPTION1, question.getOption1());
        values.put(KEY_OPTION2, question.getOption2());
        values.put(KEY_OPTION3, question.getOption3());
        values.put(KEY_OPTION4, question.getOption4());
        values.put(KEY_ANSWER, question.getAnswer());

        values.put(KEY_TOTAL_QUESTION, question.getTotal_question());
        values.put(KEY_DATE, question.getDate());
        db.update(TABLE_NAME, values,  KEY_DATE + " = ?", new String[] { question.getDate() } );
    }

    public int isThisIdAvailable(String date) {
//+" where "+KEY_DATE+" like '%"+date+"%'"
        String s = "Select * from " + TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
         Cursor cursor = db.rawQuery(s, null);
                num = cursor.getCount();
cursor.moveToNext();


        }
        catch (Exception e){
            Log.e("error",e+"");
        }
        db.close();
        return num;
    }



    @Override
    public void showChatData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, null, null,
                null, null, null, null);

        if (c.moveToFirst()) {
            do {
                Log.e("show", "Line Separated");
                Log.d("show", (c.getString(0)));
                Log.d("show", (c.getString(1)));
                Log.d("show", (c.getString(2)));
                Log.d("show", (c.getString(3)));
                Log.d("show", (c.getString(4)));
                Log.d("show", (c.getString(5)));
                Log.d("show", (c.getString(6)));
                Log.d("show", (c.getString(7)));
                Log.d("show", (c.getString(8)));


            } while (c.moveToNext());
        }
        c.close();
        db.close();
    }




    @Override
    public ArrayList<QuestionDataBean> getAllQuestions(String date_) {
        ArrayList<QuestionDataBean> questionList = null;
        SQLiteDatabase db ;

        db= this.getReadableDatabase();
        // questionList = null;

        questionList = new ArrayList<QuestionDataBean>();

        String QUERY = "Select * from " + TABLE_NAME ;
        final Cursor cursor = db.rawQuery(QUERY, null);

        if(cursor.moveToFirst()) {
            do {
                String str=cursor.getString(cursor.getColumnIndex("_date"));

                QuestionDataBean item = new QuestionDataBean(cursor.getString(cursor.getColumnIndex("_question")), cursor.getString(cursor.getColumnIndex("_option1")), cursor.getString(cursor.getColumnIndex("_option2")), cursor.getString(cursor.getColumnIndex("_option3")), cursor.getString(cursor.getColumnIndex("_option4")), cursor.getString(cursor.getColumnIndex("_answer")));

                questionList.add(item);
            }
            while (cursor.moveToNext());
        }
//            cursor.close();
//            db.close();
        return questionList;
        }


    @Override
    public int getQuestionCount() {

        SQLiteDatabase db = this.getReadableDatabase();
        try{

            String QUERY = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if(cursor != null && cursor.moveToFirst()) {
                do {

                    String str=cursor.getString(cursor.getColumnIndex("_date"));


                    num = cursor.getCount();
                    }
                while (cursor.moveToNext());
            }


            db.close();
            cursor.close();
            return num;
        }catch (Exception e){
            Log.e("error",e+"");
        }

        return 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
