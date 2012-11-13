package com.example.calculator;

import  com.example.calculator.R;

import java.text.ParseException;
import java.lang.ArithmeticException;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	// Поле для ввода
	EditText input;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        View sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);
        
        input = (EditText) findViewById(R.id.input_edit_text);
    }
    
    public void onClick(View v)
    {
    	Log.d("onClick", "sendButton clicked!");
    	
    	try {
    		input.setText( "" + Parser.eval( input.getText().toString() ) );
    		
    	} catch (ParseException e) {
    		Log.e("ParseError", e.toString());
    		input.setText("Некорректное выражение");
    		
    	} catch (ArithmeticException e) {
    		Log.e("ComputationError", e.toString());
    		input.setText("Деление на ноль или другая арифметическая ошибка");
    		
    	}
    }
    
}
