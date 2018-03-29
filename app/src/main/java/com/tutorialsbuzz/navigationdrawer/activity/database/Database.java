package com.tutorialsbuzz.navigationdrawer.activity.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tutorialsbuzz.navigationdrawer.activity.model.QuestionDataBean;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

	private static final String KEY_QUESTION_CATEGORY_ID = "_question_category_id";
	private static final String KEY_QUESTION_CATEGORY = "_question_category";
	private static final String KEY_QUESTION = "_question";
	private static final String KEY_ANSWER = "_answer";
	private static final String KEY_OPTION1 = "_option1";
	private static final String KEY_OPTION2 = "_option2";
	private static final String KEY_OPTION3 = "_option3";
	private static final String KEY_OPTION4 = "_option4";
	private static final String KEY_TOTAL_QUESTION = "_total_question";
	private static final String KEY_DATE = "_date";
	private static final String KEY_LANGUAGE = "_language";
	private static final String KEY_DESCRIPTION = "_description";


	private static final int VERSION = 1;
	private static final String DATABASE = "questionListDatabase";

	private static final String QUESTION_TABLE = "book_table";

	public Database(Context context) {
		super(context, DATABASE, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String chat_table_query = "create table " + QUESTION_TABLE + " (" + KEY_QUESTION_CATEGORY_ID + " text, "+ KEY_QUESTION_CATEGORY + " text, " +KEY_QUESTION
				+ " text, " + KEY_ANSWER + " text, "+ KEY_OPTION1 + " text,  "+ KEY_OPTION2 + " text," + KEY_OPTION3 +
				" text, "+ KEY_OPTION4 +" text, "+ KEY_TOTAL_QUESTION + " text, "+ KEY_DATE +" text, " + KEY_LANGUAGE +" text, " + KEY_DESCRIPTION +" text "+")";

		db.execSQL(chat_table_query);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + QUESTION_TABLE);
		onCreate(db);
	}

	public void onUpdate(String date,String language) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(QUESTION_TABLE, KEY_DATE + " = ? AND " + KEY_LANGUAGE + " = ? ",
				new String[]{String.valueOf(date), String.valueOf(language)});
	//	db.delete(QUESTION_TABLE, KEY_DATE + "=" + date, null);
		showChatData();


	}
	
	
	public void showChatData() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.query(QUESTION_TABLE, null, null,
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
				Log.d("show", (c.getString(9)));
				Log.d("show", (c.getString(10)));

			} while (c.moveToNext());
		}
		c.close();
		db.close();
	}

	

	public void insertQuestionData(QuestionDataBean questionDataBean) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_QUESTION_CATEGORY_ID, questionDataBean.getId());
		values.put(KEY_QUESTION_CATEGORY, questionDataBean.getQuestion_category());
		values.put(KEY_QUESTION, questionDataBean.getQuestion());
		values.put(KEY_ANSWER, questionDataBean.getAnswer());
		values.put(KEY_OPTION1, questionDataBean.getOption1());
		values.put(KEY_OPTION2,  questionDataBean.getOption2());
		values.put(KEY_OPTION3,  questionDataBean.getOption3());
		values.put(KEY_OPTION4, questionDataBean.getOption4());
		values.put(KEY_TOTAL_QUESTION, questionDataBean.getTotal_question());
		values.put(KEY_DATE, questionDataBean.getDate());
		values.put(KEY_LANGUAGE, questionDataBean.getLanguage());
		values.put(KEY_DESCRIPTION, questionDataBean.getDescription());

		// Inserting Row
		try{
		db.insert(QUESTION_TABLE, null, values);
		}catch(Exception e){
			e.printStackTrace();
		}
		db.close(); // Closing database connection
	}

	public ArrayList<QuestionDataBean> getQuestionList(String date, String language) {
		ArrayList<QuestionDataBean> msgModel = new ArrayList<QuestionDataBean>();
		SQLiteDatabase db = this.getReadableDatabase();

		String s = "Select * from "+ QUESTION_TABLE + " where ( "+ KEY_DATE+ " = '"+date+"' AND  "+ KEY_LANGUAGE+ " = '"+language+"' ) " ;


		/*db.delete(BOOK_TABLE, userid + " = ? AND "+bookid+" = ? " ,
				new String[] { String.valueOf(userId),  String.valueOf(bookId) });*/

		msgModel.clear();
		Cursor c = db.rawQuery(s, null);

		if (c.moveToFirst()) {
			do {
				QuestionDataBean item = new QuestionDataBean(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8),c.getString(9),c.getString(10),c.getString(11));
				msgModel.add(item);
			}
			while (c.moveToNext());
		}
		c.close();
		db.close();

		return msgModel;
	}
	public ArrayList<QuestionDataBean> getAllQuestionList() {
		ArrayList<QuestionDataBean> msgModel = new ArrayList<QuestionDataBean>();
		SQLiteDatabase db = this.getReadableDatabase();

		String s = "Select * from "+ QUESTION_TABLE +" ) " ;

		msgModel.clear();
		Cursor c = db.rawQuery(s, null);

		if (c.moveToFirst()) {
			do {
				QuestionDataBean item = new QuestionDataBean(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8),c.getString(9),c.getString(10),c.getString(10));
				msgModel.add(item);
			}
			while (c.moveToNext());
		}
		c.close();
		db.close();

		return msgModel;
	}


	public int isThisIdAvailable(String date) {
		boolean returnValue;
		String s = "Select * from " + QUESTION_TABLE +" where "+KEY_DATE+" = '"+date+"'";
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(s, null);
		int count = c.getCount();
		c.close();
		db.close();
		if(count<1){
			returnValue = false;
		}else{
			returnValue = true;
		}
		return count;
	}

	public int getQuestionCount() {
		boolean returnValue;
		String s = "Select * from " + QUESTION_TABLE ;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(s, null);
		int count = c.getCount();
		c.close();
		db.close();
		if(count<1){
			returnValue = false;
		}else{
			returnValue = true;
		}
		return count;
	}
/*	public void updateCount(String book_id, String book_name, String book_cat_id,String book_isbn14, String quantity1, String book_scat_id, String book_sscat_id1,String book_sub_title,String book_code,String book_pages,String book_edition,String book_discount,String book_oinr,String book_inr,String book_usd,String book_qty,String book_image,String selected,String user_id)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues values = new ContentValues();
	        values.put(bookid, book_id); 
			values.put(bookname, book_name); 
			values.put(bookcat_id, book_cat_id);
			values.put(book_isbn13, book_isbn14);
			values.put(quantity, quantity1);
			values.put(bookscat_id, book_scat_id); 
			values.put(book_sscat_id, book_sscat_id1);
			values.put(booksub_title, book_sub_title);
			values.put(bookcode, book_code); 
			values.put(bookpages, book_pages); 
			values.put(bookedition, book_edition); 
			values.put(bookdiscount, book_discount); 
			values.put(bookoinr, book_oinr);
			values.put(bookinr, book_inr);
			values.put(bookusd, book_usd); 
			values.put(bookqty, book_qty); 
			values.put(bookimage, book_image);
			values.put(selectedd, selected);
			values.put(userid, user_id);
	      db.update(BOOK_TABLE, values,  userid + " = ? AND "+bookid+" = ? ", new String[] { user_id , book_id} );
	   }*/



	

		}

	

	
	

