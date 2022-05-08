package com.okmanyiroda;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;


public class NotificationHandler {
	private static final String LOG_TAG = NotificationHandler.class.getName();
	private Context context;
	private NotificationManager manager;
	private static final String CHANNEL_ID = "default_notification_channel";
	private final int NOTIFICATION_ID = 0;
	private FirebaseAuth auth;
	public NotificationHandler(Context context){
		this.context = context;
		this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Log.i(LOG_TAG,"Notification handler successfuly created");
		createChannel();
	}
	
	private void createChannel(){
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
			return;
		
		NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
				"Default Notifications",
				NotificationManager.IMPORTANCE_LOW);
		
		channel.enableLights(true);
		channel.enableVibration(true);
		channel.setDescription("Az alapértelmezett csatorna az Okmányiroda alkalmazás számára");
		this.manager.createNotificationChannel(channel);
		Log.i(LOG_TAG, "Notification channel successfuly created");
	}
	
	
	public void send(String message) {
		auth = FirebaseAuth.getInstance();
		Log.d(LOG_TAG, "Autentication service recieved");
		
		Intent intent = new Intent(context, MainActivity.class);
		Log.d(LOG_TAG, "Intent created");
		
		PendingIntent pendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);
		Log.d(LOG_TAG, "PendingIntent created");
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
				.setContentTitle("Okmányiroda")
				.setContentText(message)
				.setSmallIcon(R.drawable.ic_baseline_alternate_email_24)
				.setContentIntent(pendingIntent);
				
		Log.d(LOG_TAG, "Notification built");
		this.manager.notify(NOTIFICATION_ID, builder.build());
		Log.i(LOG_TAG, "Notification sent");
	}
}
