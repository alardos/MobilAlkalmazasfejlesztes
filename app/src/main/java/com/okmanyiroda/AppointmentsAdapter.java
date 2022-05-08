package com.okmanyiroda;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.okmanyiroda.model.Appointment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {
	private static final String LOG_TAG = AppointmentsAdapter.class.getName();
	private ArrayList<Appointment> appointmentList;
	private ArrayList<Appointment> appointmentListAll;
	Context context;
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
		private LocalDateTime time;
		
		public ViewHolder(@NonNull View itemView) {
			
			super(itemView);
			dateTextView = itemView.findViewById(R.id.textView_appointment_date);
			
			applyButton = itemView.findViewById(R.id.button_appointment_apply);
			
			if (dateTextView != null && timeTextView != null && applyButton != null)
				Log.i(LOG_TAG, "ViewHolder's views all reached");
		}
		
		public void bindTo(Appointment appointment) {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			time = appointment.getAppointmentTime();
			String timestr = time.format(formatter);
			
			

			dateTextView.setText(timestr);
			if (!dateTextView.getText().equals("TextView")) {
				Log.i(LOG_TAG, "Time View set");
			}
			
			applyButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					
					// TODO: debug the appointment apply query
					// appointmentList.add(new Appointment(Instant.ofEpochMilli((long) map.get("appointmentTime")).atZone(ZoneId.systemDefault()).toLocalDateTime()));
					CollectionReference collection = FirebaseFirestore.getInstance().collection("Users");
					
					collection.whereEqualTo("id", FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
						@Override
						public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
							collection.document().update(
									"appotintmentTime",
									time.toEpochSecond(ZoneOffset.of("+02:00"))*1000);
						}
					});
					
					
				}
				
				
			});
		}
	}
	
}

