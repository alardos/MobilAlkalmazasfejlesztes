package com.okmanyiroda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AvalibeAppointmentsActivity extends AppCompatActivity {
	
	private RecyclerView recyclerView;
	private ArrayList<Appointment> appointmentList;
	private AppointmentsAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avalibe_appointments);
	}
}