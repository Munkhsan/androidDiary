package com.andriod;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


public class App extends Activity {	
private DatePicker dp;
private EditText edtDiary;
private Button btnWrite;
private String  fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        edtDiary = (EditText) findViewById(R.id.edtDiary);
       
        dp = (DatePicker) findViewById(R.id.datePicker1);
        
        btnWrite = (Button) findViewById(R.id.button);
        Calendar cal =Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);
        
        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				fileName = Integer.toString(year)+ "-"
        				+ Integer.toString(monthOfYear+1)+ "_"
        				+ Integer.toString(dayOfMonth)+ ".txt";
        				
        				String str = readDairy(fileName);
        		        edtDiary.setText(str);
        		        if(fileName!= null)
        		        btnWrite.setEnabled(true);
				
			}
        });
        
		 btnWrite.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
							try {
								FileOutputStream outFs  = openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE);
								String note=  edtDiary.getText().toString();
								outFs.write(note.getBytes());
								outFs.close();
								Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG).show();
							} catch (IOException e){
								
							
							}
						}
				});
        
    }
    
    public  String readDairy(String fName) {
    	
    	String  diaryStr = null;
    	FileInputStream inFs=null;
    	try {
	    		inFs = openFileInput(fName);
	    		byte[] txt = new byte [500];
	    		inFs.read(txt);
	    		inFs.close();
	    		diaryStr = (new String(txt)).trim();
	    		btnWrite.setText("");
		    } catch (IOException e ) {
		    	edtDiary.setHint(" ");
		    	btnWrite.setText(" ");
		    }
    	return diaryStr;
    	
    	}
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
