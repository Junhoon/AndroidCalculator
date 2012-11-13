package com.example.calculator;

import java.text.ParseException;
import java.util.LinkedList;

public class Parser {
	
	static final String validChars = "0123456789.+-*/^( )";
	
    static boolean isSpace(char c) {
        return c == ' ';
    }

    static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    static int priority(char op) throws ParseException {
        switch (op) {
        case '+':
        case '-':
            return 1;
            
        case '*':
        case '/':
            return 2;
            
        case '^':
        	return 3;
            
        default:
            return -1; // в частности для "("
        }
    }

    static void processOperator(LinkedList<Double> st, char op) {
        double r = st.removeLast();
        double l = st.removeLast();
        switch (op) {
        case '+':
            st.add(l + r);
            break;
        case '-':
            st.add(l - r);
            break;
        case '*':
            st.add(l * r);
            break;
        case '/':
            st.add(l / r);
            break;
        case '^':
            st.add( java.lang.Math.pow(l, r) );
            break;
        }
    }

    protected static void checkInvalidChars(String s) throws ParseException {
    	
    	found:
    	for (int i = 0; i < s.length(); ++i) {
    		
    		for (int j = 0; j < validChars.length(); ++j)
    			if ( s.charAt(i) == validChars.charAt(j) )
    				continue found;
    		
    		// Не нашли соответствия, в строке есть неизвестный символ
    		throw new ParseException(s, i);
    	}
    	
    	// Каждый символ в строке содержится также и в строке допустимы
    }
    
    public static double eval(String s) throws ParseException {
    	checkInvalidChars(s);
    		
    	// Стек чисел и стек операторов
        LinkedList<Double> st = new LinkedList<Double>();
        LinkedList<Character> op = new LinkedList<Character>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isSpace(c))
                continue;
            if (c == '(')
                op.add('(');
            else if (c == ')') {
                while (op.getLast() != '(')
                    processOperator(st, op.removeLast());
                op.removeLast();
            } else if (isOperator(c)) {
            	if (c != '^') // решаем проблему с a^b^c
	                while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
	                    processOperator(st, op.removeLast());
                op.add(c);
            } else {
                String operand = "";
                while (i < s.length() && (Character.isDigit(s.charAt(i)) || (s.charAt(i) == '.')))
                    operand += s.charAt(i++);
                --i;
                st.add(Double.parseDouble(operand));
            }
        }
        while (!op.isEmpty())
            processOperator(st, op.removeLast());
        return st.get(0);
    }
}
