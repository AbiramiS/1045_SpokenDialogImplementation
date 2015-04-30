package com.TicketBooking.logic;

import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity implements Tripinterface{

	EditText source,destination,seat;
	Button sub;
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.setting);
		source=(EditText)findViewById(R.id.source);
		destination=(EditText)findViewById(R.id.destination);
		seat=(EditText)findViewById(R.id.tseat);
		sub=(Button)findViewById(R.id.Submit);
		sub.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				try
				{
					boolean status=true;
					
					if(source.getText().toString().trim().equals(""))
					{
						status=false;
						Toast.makeText(getApplicationContext(), "Enter Source", Toast.LENGTH_SHORT).show();
					}
					else if(destination.getText().toString().trim().equals(""))
					{
						status=false;
						Toast.makeText(getApplicationContext(), "Enter Destination", Toast.LENGTH_SHORT).show();
					}
					else if(seat.getText().toString().trim().equals(""))
					{
						status=false;
						Toast.makeText(getApplicationContext(), "Enter Total number of seat", Toast.LENGTH_SHORT).show();
					}
					if(status)
					{
						String tseat=source.getText().toString().trim().toLowerCase()+"*"+destination.getText().toString().trim().toLowerCase();
						Tsource.put(source.getText().toString().trim(), destination.getText().toString().trim());
						Tseat.put(tseat,Integer.parseInt(seat.getText().toString().trim()));
						Toast.makeText(getApplicationContext(), ""+Tsource, Toast.LENGTH_SHORT).show();
						Toast.makeText(getApplicationContext(), ""+Tseat, Toast.LENGTH_SHORT).show();
						Intent i=new Intent(Settings.this,First_Activity.class);
						startActivity(i);
					}
				}catch (Exception e) {
					Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}
}
