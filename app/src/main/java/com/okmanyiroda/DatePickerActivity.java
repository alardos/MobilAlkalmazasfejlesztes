package com.okmanyiroda;



import static java.lang.System.currentTimeMillis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.okmanyiroda.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;

public class DatePickerActivity extends AppCompatActivity {
	private static final String LOG_TAG = LoginActivity .class.getName();
	private static final int SECRET_KEY = 123456789;
	FirebaseUser user;
	FirebaseFirestore firestore;
	CollectionReference firebaseCollection;
	
	Long time;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_picker);
		
		user = FirebaseAuth.getInstance().getCurrentUser();
		TextView title = findViewById(R.id.textView_datePicker_title);
		Log.i(LOG_TAG, user.toString());
		title.setText(user.getEmail());
		
		
		firestore = FirebaseFirestore.getInstance();
		
		
		if (user.isAnonymous()) {
			title.setText((CharSequence) "Időpontfoglalás bejelentkezés nélkül");
		} else{
			Query q = firestore.collection("Users").whereEqualTo("email", user.getEmail());
			q.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
				@Override
				public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
					for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
						
						User user = snapshot.toObject(User.class);
						title.setText("Üdv " + user.getFirstname());
					}
				}
			});
			
		}
		
				
		((CalendarView) findViewById(R.id.calendarView)).setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
				
				Intent intent = new Intent(DatePickerActivity.this,AvalibeAppointmentsActivity.class);
				intent.putExtra("year",year).putExtra("month",month+1).putExtra("dayOfMonth",dayOfMonth);
				startActivity(intent);
			}
		});
		
		((Button) findViewById(R.id.button_datePicker_allAppointment)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(DatePickerActivity.this,AvalibeAppointmentsActivity.class);
				intent.putExtra("SECRET_KEY" , DatePickerActivity.SECRET_KEY);
				startActivity(intent);
			}
		});
		
	}
	
	
	public void noRestriction(View view) {
	
	
	}
	
	
	
	@Override
	protected void onDestroy() {
		FirebaseAuth.getInstance().signOut();
		startActivity(new Intent(this, MainActivity.class).putExtra("SECRET_KEY", SECRET_KEY));
		super.onDestroy();
		
	}
	
	@Override
	protected void onStop() {
		time = System.currentTimeMillis();
		super.onStop();
	}
	
	@Override
	protected void onStart() {
		// Ha a felhasználó be volt jelentkezve és 60 másodpercig nem használta az alkalmazást akkor kijelentkezteti
		if (!user.isAnonymous() && time != null && System.currentTimeMillis() - time > 60000) {
			Toast.makeText(this, "Az alkalmazás túl sokáig nem volt használatban, kijelentkezés", Toast.LENGTH_LONG).show();
			FirebaseAuth.getInstance().signOut();
			startActivity(new Intent(this, MainActivity.class).putExtra("SECRET_KEY", SECRET_KEY));
		}
		super.onStart();
	}
}