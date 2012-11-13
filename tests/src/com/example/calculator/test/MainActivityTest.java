package com.example.calculator.test;

import java.text.ParseException;
import java.util.Random;

import com.example.calculator.MainActivity;
import com.example.calculator.Parser;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import junit.framework.Assert;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.some.app.HelloActivityTest \
 * com.some.app.tests/android.test.InstrumentationTestRunner
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testTwoByFour() throws ParseException {
        
    	// Don't go looking for snakes^W bugs - you might find them
    	double d = Parser.eval("2 * 4");
    	
    	Log.d("Test", "2*4: " + d);
        Assert.assertTrue(d == 8.0);
    }
    
    public void testExpression() throws ParseException {
        
    	double d = Parser.eval("5.24 * (42.8 - 400.57)");
    	
    	Log.d("Test", "Expression: " + d);
        Assert.assertTrue(d == (5.24 * (42.8 - 400.57)));
    }

    public void testPower() throws ParseException {
        
    	//    3
    	//   2      8
    	//  2   =  2  = 256
    	
    	double d = Parser.eval("2^2^3");
    	
    	Log.d("Test", "Power: " + d);
        Assert.assertTrue(d == 256.0);
    }
    
    public void testStressAddition() throws ParseException {
    	Random r = new Random();
    	for (int i = 0; i < 10; ++i) {
    		
    		final int amount = 100;
    		
    		// Массив случайных слагаемых
    		double[] summand = new double[amount];
    		for (int j = 0; j < amount; ++j)
    			summand[j] = -1000 + 2000 * r.nextDouble();
    		
    		summand[0] = java.lang.Math.abs( summand[0] );
    		String s = "" + summand[0];
    		double sum = summand[0];
    		for (int j = 1; j < amount; ++j) {
    			sum += summand[j];
    			
    			if (summand[j] >= 0)
    				s = s + "+" + summand[j];
    			else
					s = s + summand[j];
    		}
    		
    		Log.d("Test", "Stress s = " + s);
    		Assert.assertTrue(Parser.eval(s) == sum);
    	}
    }

    public void testDivByZero() throws ParseException {
        
    	// Проверяем что eval выкинет эксепшен от деления на ноль а не от чего-нибудь еще
    	try {
    		Parser.eval("42 / 0");
    	} catch (ArithmeticException e) {
    		// Поймал!
    		
    	}
    	
    }


}