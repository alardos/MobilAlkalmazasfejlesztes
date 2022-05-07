package com.okmanyiroda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {
	
	private ArrayList<Appointment> appointmentList;
	private ArrayList<Appointment> appointmentListAll;
	private Context context;
	private int lastPosition = -1;
	
	AppointmentsAdapter(Context content, ArrayList<Appointment> appointmentList){
		this.appointmentList = appointmentList;
		this.appointmentListAll = appointmentList;
		this.context = content;
	}
	
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder((LayoutInflater.from(context).inflate(R.layout.appointment, parent,false)));
	}
	
	@Override
	public void onBindViewHolder(@NonNull AppointmentsAdapter.ViewHolder holder, int position) {
		Appointment appointment = appointmentList.get(position);
		
		holder.bindTo(appointment);
	}
	
	@Override
	public int getItemCount() {
		return 0;
	}
	
	class ViewHolder extends RecyclerView.ViewHolder{
		
		private TextView dateTextView;
		private TextView timeTextView;
		private Button applyButton;
		
		public ViewHolder(@NonNull View itemView) {
			
			super(itemView);
			dateTextView = itemView.findViewById(R.id.textView_appointment_date);
			timeTextView = itemView.findViewById(R.id.textView_appointment_time);
			applyButton = itemView.findViewById(R.id.button_appointment_apply);  
		}
		
		public void bindTo(Appointment appointment) {
			dateTextView.setText(appointment.getDate());
			dateTextView.setText(appointment.getTime());
		}
	}
	
}

