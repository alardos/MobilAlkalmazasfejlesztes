package com.okmanyiroda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
	private static final int SECRET_KEY = 123456789;
	private static final String LOG_TAG = MainActivity.class.getName();
	FirebaseAuth auth;
	FirebaseFirestore firestore;
	CollectionReference firebaseCollection;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		auth = FirebaseAuth.getInstance();
		
		
		
//		firebaseCollection.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
//			for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//				MyClass item = documentSnapshot.toObject(MyClass.class);
//				list.add(item);
//			}
//		});

	}
	
	public void login(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra("SECRET_KEY",SECRET_KEY);
		startActivity(intent);
	}
	
	public void register(View view) {
		Intent intent = new Intent(this, RegistrationActivity.class);
		intent.putExtra("SECRET_KEY",SECRET_KEY);
		startActivity(intent);
	}
	
	public void anonim(View view) {
		auth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				if (task.isSuccessful()){
					Toast.makeText(MainActivity.this, "Időpontfoglalás bejelentkezés nélkül", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(MainActivity.this, AvalibeAppointmentsActivity.class);
					intent.putExtra("SECRET_KEY",SECRET_KEY);
					startActivity(intent);
				}else{
					Toast.makeText(MainActivity.this, "A bejelentkezés nélküli időpontfoglalás jelenleg nem működik", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
	}
}