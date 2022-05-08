package com.okmanyiroda;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {
	private static final int SECRET_KEY = 123456789;
	private NotificationHandler notificationHandler;
	private static final String LOG_TAG = LoginActivity .class.getName();
	FirebaseAuth auth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		notificationHandler = new NotificationHandler(this);
		
		if (getIntent().getIntExtra("SECRET_KEY",0) != SECRET_KEY) finish();
		auth = FirebaseAuth.getInstance();
	}
	
	public void login(View view) {
		String email = ((EditText) findViewById(R.id.editText_login_email)).getText().toString();
		String password = ((EditText) findViewById(R.id.editText_login_password)).getText().toString();
		
		auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, task -> {
			if (task.isSuccessful()){
				Log.i(LOG_TAG, "Autenthication successful");
				Toast.makeText(LoginActivity.this, "A bejelentkezés sikeres", Toast.LENGTH_SHORT).show();
				
				notificationHandler.send("Be van jelentkezve az okmányiroda alkalmazásva\nKoppintson a kijelentkezéshez");
				
				Intent intent = new Intent(LoginActivity.this, DatePickerActivity.class);
				intent.putExtra("SECRET_KEY",SECRET_KEY);
				startActivity(intent);
				
				
			}else{
				try {
					throw task.getException();
				} catch(FirebaseAuthInvalidCredentialsException e) {
					Toast.makeText(LoginActivity.this, "Az email cím vagy a jelszó hibás", Toast.LENGTH_SHORT).show();
				} catch(Exception e) {
					Log.e(LOG_TAG, e.getMessage());
				}
			}
		});
		
	}
	
	public void register(View view) {
		Intent intent = new Intent(this,RegistrationActivity.class);
		intent.putExtra("SECRET_KEY",SECRET_KEY);
		startActivity(intent);
	}
}