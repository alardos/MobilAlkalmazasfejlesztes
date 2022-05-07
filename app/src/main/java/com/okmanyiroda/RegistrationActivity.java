package com.okmanyiroda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {
	
	private static final String LOG_TAG = LoginActivity .class.getName();
	private static final int SECRET_KEY = 123456789;
	private FirebaseAuth mAuth;

	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String personalID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		mAuth = FirebaseAuth.getInstance();
	}
	
	public void login(View view) {
		Intent intent = new Intent(this,LoginActivity.class);
		intent.putExtra("SECRET_KEY",SECRET_KEY);
		startActivity(intent);
	}
	
	public void register(View view) {
		
		firstName = ((EditText) findViewById(R.id.editText_registration_firstName)).getText().toString();
		lastName = ((EditText) findViewById(R.id.editText_registration_lastName)).getText().toString();
		email = ((EditText) findViewById(R.id.editText_registration_email)).getText().toString();
		password = ((EditText) findViewById(R.id.editText_registration_password)).getText().toString();
		personalID = ((EditText) findViewById(R.id.editText_registration_personalID)).getText().toString();
		
		String password2 = ((EditText) findViewById(R.id.editText_registration_password2)).getText().toString();
		
		// TODO: all fields are requered
		if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password2.isEmpty() || password.isEmpty() || personalID.isEmpty())
			Toast.makeText(this, "Minden mezőt ki kell tölteni", Toast.LENGTH_SHORT).show();
		
		else if(!password.equals(password2))
			Toast.makeText(this, "A két jelszó nem egyezik meg", Toast.LENGTH_SHORT).show();
		
		else if(password.length() < 6)
			Toast.makeText(this, "A jelszó minimum 6 karakter hosszúságú kell legyen", Toast.LENGTH_SHORT).show();
		
		else {
			mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if (task.isSuccessful()) {
						
						Log.i(LOG_TAG,"User created successfully");
						Toast.makeText(RegistrationActivity.this, "A regisztráció sikeres", Toast.LENGTH_SHORT).show();
						
						
						Intent intent = new Intent(RegistrationActivity.this, DatePickerActivity.class);
						intent.putExtra("SECRET_KEY",SECRET_KEY);
						startActivity(intent);
						
						
						
					} else{
						try {
							throw Objects.requireNonNull(task.getException());
						} catch(FirebaseAuthInvalidCredentialsException e) {
							Toast.makeText(RegistrationActivity.this, "Az email cím vagy jelszó nem megfelelő formátumú", Toast.LENGTH_SHORT).show();
						} catch(FirebaseAuthUserCollisionException e) {
							Toast.makeText(RegistrationActivity.this, "Az email cím már regisztrálva van", Toast.LENGTH_SHORT).show();
						} catch(Exception e) {
							Log.e(LOG_TAG, e.getMessage());
						}
					}
				}
			});
		}
		
		
		
	}
	
}