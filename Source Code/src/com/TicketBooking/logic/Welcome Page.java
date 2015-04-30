package com.TicketBooking.logic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class First_Activity extends Activity implements Tripinterface{

	TextView lay;
	ImageView setting,clear;
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.first);
		
		lay=(TextView)findViewById(R.id.lay);
		setting=(ImageView)findViewById(R.id.settings);
		clear=(ImageView)findViewById(R.id.clear);
		 if(android.os.Build.VERSION.SDK_INT > 9)
	     {
	    	 StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    	 StrictMode.setThreadPolicy(policy);
	     }
		lay.setOnClickListener(new submit());
		setting.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Intent i=new Intent(First_Activity.this,Settings.class);
			startActivity(i);
			}
		});
		clear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Tsource.clear();
				Tseat.clear();
				Toast.makeText(getApplicationContext(), "Data Clear",Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	public class submit implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
		
			Intent i=new Intent(First_Activity.this,TicketBookingActivity.class);
			startActivity(i);
		}
		
	}
}
