package com.tutorialsbuzz.navigationdrawer.activity.gcm;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.ucc.application.R;
import com.tutorialsbuzz.navigationdrawer.activity.MainActivity;

public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	Context c;

	public GcmIntentService() {
		super("GcmIntentService");
		//Log.e(" In GcmIntentService.....","In GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);
		if (!extras.isEmpty())
		{
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType))
			{
				sendNotification("Send error: " +intent.getExtras().toString());
				Log.e("error occured",intent.getExtras().toString());
			}
			else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType))
			{
				sendNotification("Deleted messages on server: " +intent.getExtras().toString());
				Log.e("error occured",intent.getExtras().toString());
			}
			else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))
			{

				Log.i("working", "Completed work @ " + SystemClock.elapsedRealtime());
				try {
                    String msg = null;
                    msg =	intent.getExtras().getString("message");
                    sendNotification(msg);
				} catch (Exception e) {

				}
			}

		}

		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}


	private void sendNotification(String msg)
	{
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		//builder.setSound(alarmSound);
		mNotificationManager = (NotificationManager)
				this.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class), 0);

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
						.setSmallIcon(R.drawable.logo)

		.setContentTitle("UCC")
		.setSound(alarmSound)
		.setStyle(new NotificationCompat.BigTextStyle()
				.bigText(msg))
						.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000 })
		.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mBuilder.setAutoCancel(true);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}
}