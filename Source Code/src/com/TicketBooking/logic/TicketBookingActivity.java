package com.TicketBooking.logic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TicketBookingActivity extends Activity implements Tripinterface{
    
	private TextView txtText;
	protected static final int RESULT_SPEECH = 1;
	private ImageButton btnSpeak;
	TextToSpeech ttobj;
//	ArrayList name=new ArrayList(); 
	String status="start";
	String source="",name="";
	int count=0;
//	LinkedHashMap Tsource=new LinkedHashMap();
//	LinkedHashMap Tseat=new LinkedHashMap();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        txtText = (TextView) findViewById(R.id.txtText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(new click());
        
//        Tsource.put("chennai","madurai");
//        Tsource.put("madurai", "tiruchy");
//        Tseat.put("chennai*madurai", "30");
//        Tseat.put("chennai*tiruchy", "30");
        
    	ttobj=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
		{

			@Override
			public void onInit(int status) {
				 if(status != TextToSpeech.ERROR){
		             ttobj.setLanguage(Locale.UK);
		            }	
				
			}
			
		});
    	
    	
    }
    public class click implements View.OnClickListener
    {

		@Override
		public void onClick(View v) {
			try
	        {
				if(name.equals(""))
				{
					ttobj.speak("Your Name please", TextToSpeech.QUEUE_FLUSH, null);
					Thread.sleep(1000);
					speech();
				}
				
				
				
	        }catch (Exception e) {
				Toast.makeText(getApplicationContext(), ""+e,Toast.LENGTH_SHORT).show();
			}
			
		}
    	
    }
    public void speech()
    {
    	try
        {
    		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

			try {
				startActivityForResult(intent, RESULT_SPEECH);
				txtText.setText("");
			} catch (ActivityNotFoundException a)
			{
				Toast.makeText(getApplicationContext(),"Ops! Your device doesn't support Speech to Text",Toast.LENGTH_SHORT).show();
			}
        }catch (Exception e) {
        	Toast.makeText(getApplicationContext(), ""+e,Toast.LENGTH_SHORT).show();
		}
    }
    
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data)
			{

				ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				txtText.setText(text.get(0));
				if(txtText.getText().toString().trim()!=null && txtText.getText().toString().trim()!="")
				{
					if(status.equals("start"))
					{
						name=txtText.getText().toString().trim();
						if(!name.equals(""))
						{
							ttobj.speak("Source Please", TextToSpeech.QUEUE_FLUSH, null);
							try {
								Thread.sleep(1000);
								speech();
								status="source";
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
						}
						
					}
					else if(status.equals("source"))
					{
						if(Tsource.containsKey(txtText.getText().toString().trim()))
						{
							source=txtText.getText().toString().trim();
							ttobj.speak("Destination Please", TextToSpeech.QUEUE_FLUSH, null);
							try 
							{
								Thread.sleep(1000);
								speech();
								status="Destination";
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else
						{
							ttobj.speak("Invalid Source", TextToSpeech.QUEUE_FLUSH, null);
							try 
							{
								Thread.sleep(1000);
								speech();
								status="source";
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					}
					else if(status.equals("Destination"))
					{
						String checksoutce=Tsource.get(source.toString().trim()).toString();
						if(checksoutce.equalsIgnoreCase(txtText.getText().toString().trim()))
						{
							String merge=source.toString().toLowerCase().trim()+"*"+checksoutce.toLowerCase().toString().trim();
							int getseat=Integer.parseInt(Tseat.get(merge).toString().trim());
							count++;
							if(count<=getseat)
							{
								String booked=source+"to"+txtText.getText().toString().trim()+"Ticket Booked for passanger"+name;
								ttobj.speak(booked, TextToSpeech.QUEUE_FLUSH, null);
								name="";
								
								try {
									Thread.sleep(1000);
									//speech();
									status="start";
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}else
							{
								ttobj.speak("Sorry Seat Not Available", TextToSpeech.QUEUE_FLUSH, null);
							}
							
						}else
						{
							ttobj.speak("Invalid Destination", TextToSpeech.QUEUE_FLUSH, null);
							try 
							{
								Thread.sleep(1000);
								speech();
								status="Destination";
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					}
					
					
					
					
				}
			}
			break;
		}

		}
	}
}