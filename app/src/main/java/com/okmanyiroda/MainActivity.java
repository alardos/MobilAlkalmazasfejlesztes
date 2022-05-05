package com.okmanyiroda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
	private static final String LOG_TAG = MainActivity.class.getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
	}
	
	public void login(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
	
	public void register(View view) {
	}
}