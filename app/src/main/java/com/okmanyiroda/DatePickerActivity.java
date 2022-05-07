package com.okmanyiroda;



import static java.lang.System.currentTimeMillis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.GregorianCalendar;
import java.util.LinkedList;

public class DatePickerActivity extends AppCompatActivity {
	private static final String LOG_TAG = LoginActivity .class.getName();
	private static final int SECRET_KEY = 123456789;
	FirebaseUser user;
	FirebaseFirestore firestore;
	CollectionReference firebaseCollection;
	
	Long time;
	
	class MyClass{
		private String name;
		private int age;
		
		public MyClass(String name, int age){
			this.name = name;
			this.age = age;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public int getAge() {
			return age;
		}
		
		public void setAge(int age) {
			this.age = age;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_picker);
		
		user = FirebaseAuth.getInstance().getCurrentUser();
		TextView title = findViewById(R.id.textView_datePicker_title);
		Log.i(LOG_TAG, user.toString());
		title.setText(user.getEmail());
		if (!user.isAnonymous()) {
			title.setText((CharSequence) "Időpontfoglalás bejelentkezés nélkül");
		}
		
		
		
		
		
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