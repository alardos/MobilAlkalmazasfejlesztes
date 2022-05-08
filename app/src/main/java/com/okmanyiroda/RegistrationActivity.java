package com.okmanyiroda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.okmanyiroda.model.User;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {
	
	private static final String LOG_TAG = LoginActivity.class.getName();
	private static final int SECRET_KEY = 123456789;
	private FirebaseAuth auth;
	private FirebaseFirestore firestore;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		auth = FirebaseAuth.getInstance();
	}
	
	public void login(View view) {
		Intent intent = new Intent(this,LoginActivity.class);
		intent.putExtra("SECRET_KEY",SECRET_KEY);
		startActivity(intent);
	}
	
	public void register(View view) {
		
		// Creating user object
		String password2 = ((EditText) findViewById(R.id.editText_registration_password2)).getText().toString();
		User user = new User(
				((EditText) findViewById(R.id.editText_registration_firstName)).getText().toString(),
				((EditText) findViewById(R.id.editText_registration_lastName)).getText().toString(),
				((EditText) findViewById(R.id.editText_registration_email)).getText().toString(),
				((EditText) findViewById(R.id.editText_registration_password)).getText().toString(),
				((EditText) findViewById(R.id.editText_registration_personalID)).getText().toString()
		);
		
		
		
		// Checking if any fields are empty
		if (
				user.getFirstname().isEmpty() ||
				user.getLastname().isEmpty() ||
				user.getEmail().isEmpty() ||
				password2.isEmpty() ||
				user.getPassword().isEmpty() ||
				user.getPersonalid().isEmpty()
		)
		{
			Toast.makeText(this, "Minden mezőt ki kell tölteni", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// Checking if the two passwords are match
		if(!user.getPassword().equals(password2)){
			Toast.makeText(this, "A két jelszó nem egyezik meg", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// Checking if the length of the password is long enough for the firebase auth
		else if(user.getPassword().length() < 6){
			Toast.makeText(this, "A jelszó minimum 6 karakter hosszúságú kell legyen", Toast.LENGTH_SHORT).show();
			return;
		}
		

		auth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(this, task -> {
			if (!task.isSuccessful()) {
				try {
					throw Objects.requireNonNull(task.getException());
				} catch(FirebaseAuthInvalidCredentialsException e) {
					Toast.makeText(RegistrationActivity.this, "Az email cím vagy jelszó nem megfelelő formátumú", Toast.LENGTH_SHORT).show();
				} catch(FirebaseAuthUserCollisionException e) {
					Toast.makeText(RegistrationActivity.this, "Az email cím már regisztrálva van", Toast.LENGTH_SHORT).show();
				} catch(Exception e) {
					Log.e(LOG_TAG, e.getMessage());
				}
				return;
			}
			
			
			user.setId(auth.getUid());
			
			firestore = FirebaseFirestore.getInstance();
			firestore.collection("Users").add(user).addOnCompleteListener(task1 -> {
				Toast.makeText(RegistrationActivity.this, "A regisztráció sikeres", Toast.LENGTH_SHORT).show();
			});
			
			
			
			Intent intent = new Intent(RegistrationActivity.this, DatePickerActivity.class);
			intent.putExtra("SECRET_KEY",SECRET_KEY);
			startActivity(intent);
		});
		
		
		
		
	}
	
}