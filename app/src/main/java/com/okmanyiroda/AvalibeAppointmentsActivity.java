package com.okmanyiroda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.type.DateTime;
import com.okmanyiroda.model.Appointment;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AvalibeAppointmentsActivity extends AppCompatActivity {
	
	private RecyclerView recyclerView;
	private ArrayList<Appointment> appointmentList;
	private AppointmentsAdapter adapter;
	
	private static final String LOG_TAG = AvalibeAppointmentsActivity.class.getName();
	private static final int SECRET_KEY = 123456789;
	
	private FirebaseFirestore firestore;
	private CollectionReference firebaseCollection;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avalibe_appointments);
		
		if (getIntent().getIntExtra("SECRET_KEY",0) != SECRET_KEY) finish();
		
//		LocalDate date = LocalDate.parse(getIntent().getStringExtra("date"));
		
		recyclerView = findViewById(R.id.recyclerview);
		recyclerView.setLayoutManager(new GridLayoutManager(this,1));
		appointmentList = new ArrayList<Appointment>();
		adapter = new AppointmentsAdapter(this,appointmentList);
		recyclerView.setAdapter(adapter);
		
		
		
		if (getIntent().getIntExtra("year",-1) != -1){
			LocalDateTime startdt = LocalDateTime.of(
					getIntent().getIntExtra("year",0),
					getIntent().getIntExtra("month",0),
					getIntent().getIntExtra("dayOfMonth",0),
					0,
				0);
			
			
			LocalDateTime enddt = LocalDateTime.of(
					getIntent().getIntExtra("year",0),
					getIntent().getIntExtra("month",0),
					getIntent().getIntExtra("dayOfMonth",0),
					23,
				59);
			queryData(startdt.toEpochSecond(ZoneOffset.of("+02:00"))*1000,enddt.toEpochSecond(ZoneOffset.of("+02:00"))*1000);
			return;
		}else{
			
			queryData();
		}
		
		
		

	}
	
	
	private void queryData(long start, long end){Log.i(LOG_TAG, "Querying between " + start + " and " + end);
		firestore = FirebaseFirestore.getInstance();
		firebaseCollection = firestore.collection("appointmentList");
	}
	
	
//	private void queryData(){
		
		


//		ArrayList<Appointment> list = new ArrayList<Appointment>();
//
//		firebaseCollection.whereGreaterThan("appointmentTime",start).whereLessThan("appointmentTime",end).get().addOnSuccessListener(queryDocumentSnapshots -> {
//			for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
//				Log.i(LOG_TAG, "Available time has been found");
//				Map<String,Object> map = snapshot.getData();
//				appointmentList.add(new Appointment(Instant.ofEpochMilli((long) map.get("appointmentTime")).atZone(ZoneId.systemDefault()).toLocalDateTime()));
//			}
//			adapter.notifyDataSetChanged();
//		}).addOnFailureListener(new OnFailureListener() {
//			@Override
//			public void onFailure(@NonNull Exception e) {
//				int i = 5;
//			}
//		});
//	}
	

	
	private void queryData(){
		firestore = FirebaseFirestore.getInstance();
		firebaseCollection = firestore.collection("appointmentList");
		
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		
		firebaseCollection.get().addOnSuccessListener(queryDocumentSnapshots -> {
			for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
				Log.i(LOG_TAG, "Available time has been found");
				Map<String,Object> map = snapshot.getData();
				appointmentList.add(new Appointment(Instant.ofEpochMilli((long) map.get("appointmentTime")).atZone(ZoneId.systemDefault()).toLocalDateTime()));
			}
			adapter.notifyDataSetChanged();
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				int i = 5;
			}
		});
	}
	
	
}