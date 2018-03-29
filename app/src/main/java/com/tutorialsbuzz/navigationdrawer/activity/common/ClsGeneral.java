package com.tutorialsbuzz.navigationdrawer.activity.common;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClsGeneral {
	public static Context mContext;

	public static String NAME = "name";
	public static String EMAIL = "email";
	public static String CARLICENSENUMBER = "carLicenseNumber";
	public static String USERID = "userid";
	public static String SCANNEDUSER = "scannedUser";
	public static String ANDROIDID = "androidId";

	public static void setPreferences(Context context, String key, String value) {
		mContext = context;
		SharedPreferences.Editor editor = mContext.getSharedPreferences(
				"WED_APP", Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getPreferences(Context context, String key) {
		mContext = context;
		SharedPreferences prefs = mContext.getSharedPreferences("WED_APP",
				Context.MODE_PRIVATE);
		String text = prefs.getString(key, "");
		return text;
	}

	public static void showAlertActivity(String message, final Activity context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								// context.finish();
							}
						});
		// AlertDialog alert = builder.create();

		try {
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showAlertActivityWithTitle(String title, String message,
			final Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
				.setTitle(title)
				.setCancelable(false)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								// context.finish();
							}
						});
		// AlertDialog alert = builder.create();

		try {
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showAlertActivityWithTitleFinish(String title, String message,
			final Activity mActivity) {

		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setMessage(message)
				.setTitle(title)
				.setCancelable(false)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								 mActivity.finish();
							}
						});
		// AlertDialog alert = builder.create();

		try {
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showAlertandFinish(String message,
			final Activity activity) {

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								activity.finish();
							}
						});
		// AlertDialog alert = builder.create();

		try {
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public static boolean emailValidator(String email) {
		Pattern pattern;
		Matcher matcher;
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	// public boolean emailValidator(String email) {
	// boolean isValid = false;
	//
	// String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	// CharSequence inputStr = email;
	//
	// Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	// Matcher matcher = pattern.matcher(inputStr);
	// if (matcher.matches()) {
	// isValid = true;
	// }
	// return isValid;
	// }

	public void showAlertActivity(String message, Context mContext) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								// context.finish();
							}
						});
		// AlertDialog alert = builder.create();

		try {
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static String GetDateTimeDifference(Date startDate, Date endDate){

		//milliseconds
		long different = endDate.getTime() - startDate.getTime();

		System.out.println("startDate : " + startDate);
		System.out.println("endDate : "+ endDate);
		System.out.println("different : " + different);

		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;
		long monthInMilli = daysInMilli * 30;

		long elapsedMonths = different / monthInMilli;
		different = different % monthInMilli;

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;

		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;
		long totalSecond = (elapsedSeconds + (60*elapsedMinutes) + (3600*elapsedHours) + (86400*elapsedDays) + (2592000*elapsedMonths));

		Log.i("elapsedMonths",""+elapsedMonths);
		Log.i("elapsedDays",""+elapsedDays);
		Log.i("elapsedHours",""+elapsedHours);
		Log.i("elapsedMinutes",""+elapsedMinutes);
		Log.i("elapsedSeconds",""+elapsedSeconds);
		if (totalSecond<60){
			return "now";
		}
		if ((totalSecond>=60) && (!(totalSecond>=120))){
			return elapsedMinutes+" min ago";
		}
		if ((totalSecond>=120) && (!(totalSecond>=3600))){
			return elapsedMinutes+" mins ago";
		}
		if ((totalSecond>=3600) && (!(totalSecond>=7200))){
			return elapsedHours+" hour ago";
		}
		if ((totalSecond>=7200) && (!(totalSecond>=86400))){
			return elapsedHours+" hours ago";
		}
		if ((totalSecond>=86400) && (totalSecond<172800)){
			return elapsedDays+" day ago";
		}
		if ((totalSecond>=172800) && (!(totalSecond>=2592000))){
			return elapsedDays+" days ago";
		}
		if ((totalSecond>=2592000) && (!(totalSecond>=5184000))){
			return elapsedMonths+" month ago";
		}
		if ((totalSecond>=5184000) && (!(totalSecond<31104000))){
			return elapsedMonths+" months ago";
		}
		else {
			return ""+startDate;
		}


	}
	public static String GetTodaysDate() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(c.getTime());
	}

	public static void updatelanguage(String headerlanguage) {


		if (headerlanguage.equalsIgnoreCase("English")) {
			setLocale("en");
		}

		if (headerlanguage.equalsIgnoreCase("Hindi")) {

			setLocale("hi");


		} else if (headerlanguage.equalsIgnoreCase("Kannada")) {

			setLocale("kn");

		} else if (headerlanguage.equalsIgnoreCase("Telugu")) {

			setLocale("te");

		} else if (headerlanguage.equalsIgnoreCase("Tamil")) {

			setLocale("ta");

		}


	}

	private static void setLocale(String en) {
		Locale	myLocale = new Locale(en);
		Resources res = mContext.getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
	}



}
