package com.okmanyiroda.model;

import android.os.Parcel;



import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import com.google.type.DateTime;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Appointment {
	private LocalDateTime appointmentTime;
	
	public Appointment(LocalDateTime appointmentTime){
		this.appointmentTime = appointmentTime;
	}
	
	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}
	
	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
}
