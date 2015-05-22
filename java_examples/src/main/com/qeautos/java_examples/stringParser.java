package com.qeautos.java_examples;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 1/5/15.
 */
public class stringParser {
    /*
    This function is only a programming exercise. Don't use this function for serious calculation.
    The goal is to translate a double number string into a double number without using the java
    String.doDouble() function.
    We use Long to process char number, therefore it has limitation on range and precision.
    We can consider using Long array to improve this range and precision limitation later.
    Function parse_double(String in) takes a number string as its input argument, and returns a double number
    of the intended value represented by the number string.
    The number string needs to be in the form of "+/-digits.digits" where:
        +/- is the sign before the number, it is optional;
        . is the decimal point, it is optional;
        Digits before the decimal point is optional if there is at least one digit after the decimal point.
        Digits after the decimal point is optional if there is at least one digit before the decimal point.
    It throws IllegalArgumentException if this number format is not followed.
    Digits before and after the decimal point are numbers up to the value of Long.MAX_VALUE. Beyond that it
    will throw ArithmeticException.
    Literally this function can parse a Double number string with Integer.MAX_VALUE digits before the decimal
    point, and same number of digits after the decimal point, plus the +/- sign. It throws
    IllegalArgumentException if this limitation is exceeded.
    Mathematically this function can parse a Double number string where its expected value is in the range of
    (+/-)(Long.MAX_VALUE - 512). It throws ArithmeticException if this limitation is exceeded.
    Mathematical precision wise it can not process digits number after the decimal point that is greater than
    (Long.MAX_VALUE - 512). You need to trade precision vs string length.
    The returned double number if successful will be literally correct compare with the input string. However
    when compare with the expected double you need to use precision delta of 1e-15.
    */
    public double parse_double(String in) throws IllegalArgumentException, ArithmeticException {
        //need to use long to process before and after decimal integer strings to guarantee accuracy
        long before = 0, after = 0;
        double result = 0;
        //ASCII of number 0 as offset
        int offset = (int)"0".charAt(0);
        // first we check of the in (input) has content
        if (in == null || in.isEmpty()) {
            throw new IllegalArgumentException();
        }
        //regex for the format of +/- digits-before . digits-after
        // +/- optional as group 1
        // digits-before optional as group 2
        // . (dot) optional
        // digits-after optional as group 3
        String double_regex = "([+-]?)([0-9]*)[.]?([0-9]*)$";
        Pattern p = Pattern.compile(double_regex);
        Matcher m = p.matcher(in);
        if (m.matches()) {
            //one of group 2 or group 3 should have number present, throw IllegalArgumentException
            if ((m.group(2).length() == 0) && (m.group(3).length() == 0)) {
                throw new IllegalArgumentException();
            }
            // our string length limitation is Integer.MAX_VALUE
            if ((m.group(2).length() > Integer.MAX_VALUE) || (m.group(3).length() > Integer.MAX_VALUE)) {
                throw new IllegalArgumentException();
            }
            //process group 2 (before dot)
            if (m.group(2).length() > 0) {
                before = (long) ((int) m.group(2).charAt(0) - offset);
                for (int i = 1; i < m.group(2).length(); i++) {
                    long max_check = before;
                    before = before * 10 + ((long) ((int) m.group(2).charAt(i) - offset));
                    //check for long overflow, throw ArithmeticException
                    if (((i + 1) < m.group(2).length()) && (max_check > before)) {
                        throw new ArithmeticException();
                    }
                }
            }
            //process group 3 (after dot)
            if (m.group(3).length() > 0) {
                after = ((long) ((int) m.group(3).charAt(0) - offset));
                for (int i = 1; i < m.group(3).length(); i++) {
                    long max_check = after;
                    after = after * 10 + ((long) ((int) m.group(3).charAt(i) - offset));
                    //check for long overflow, throw ArithmeticException
                    if (((i + 1) < m.group(3).length()) && (max_check > after)) {
                        throw new ArithmeticException();
                    }
                }
            }
            //process result sum
            result = (double) (before + after / Math.pow(10, m.group(3).length()));
            //don't trust out result if it exceeds the range limitation.
            //this should not happen mathematically in this function, this is a safety guard.
            if (Math.abs(before) > (Long.MAX_VALUE - 512)) {
                throw new ArithmeticException();
            }
            if (Math.abs(after) > (Long.MAX_VALUE - 512)) {
                throw new ArithmeticException();
            }
            //process sign
            if (m.group(1).contentEquals("-")) {
                result = -result;
            }
        }
        //throw exception because input format does not match double_regex
        else {
            throw new IllegalArgumentException();
        }
        return result;
    }
}