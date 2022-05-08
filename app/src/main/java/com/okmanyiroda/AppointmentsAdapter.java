package com.okmanyiroda;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.okmanyiroda.model.Appointment;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {
	private static final String LOG_TAG = AppointmentsAdapter.class.getName();
	private ArrayList<Appointment> appointmentList;
	private ArrayList<Appointment> appointmentListAll;
	private Context context;
	private int lastPosition = -1;
	
	AppointmentsAdapter(Context content, ArrayList<Appointment> appointmentList){
		this.appointmentList = appointmentList;
		this.appointmentListAll = appointmentList;
		this.context = content;
		Log.i(LOG_TAG, "AppotintmentsAdapter successfuly created");
	}
	

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		AppointmentsAdapter.ViewHolder viewHolder = new ViewHolder((LayoutInflater.from(context).inflate(R.layout.appointment, parent,false)));
		Log.i(LOG_TAG, "ViewHolder: "+viewHolder +" created");
		return viewHolder;
	}
	
	@Override
	public void onBindViewHolder(@NonNull AppointmentsAdapter.ViewHolder holder, int position) {
		Appointment appointment = appointmentList.get(position);
		Log.i(LOG_TAG, "Appointment: " + appointment.toString() + " on " + position +" position");
		
		holder.bindTo(appointment);
	}
	
	
	
	@Override
	public int getItemCount() {
		return appointmentList.size();
	}
	
	class ViewHolder extends RecyclerView.ViewHolder{
		
		private TextView dateTextView;
		private TextView timeTextView;
		private Button applyButton;
		
		public ViewHolder(@NonNull View itemView) {
			
			super(itemView);
			dateTextView = itemView.findViewById(R.id.textView_appointment_date);
			
			applyButton = itemView.findViewById(R.id.button_appointment_apply);
			
			if (dateTextView != null && timeTextView != null && applyButton != null)
				Log.i(LOG_TAG, "ViewHolder's views all reached");
		}
		
		public void bindTo(Appointment appointment) {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			
			String time = appointment.getAppointmentTime().format(formatter);
			
			

			dateTextView.setText(time);
			if (!dateTextView.getText().equals("TextView")) {
				Log.i(LOG_TAG, "Time View set");
			}
		}
	}
	
}

