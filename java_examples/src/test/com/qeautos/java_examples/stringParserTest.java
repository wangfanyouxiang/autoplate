package com.qeautos.java_examples;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class stringParserTest {
    stringParser myParser = new stringParser();

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test //no sign
    public void testParse_double_no_sign() throws Exception {
        String test_string = "123.45";
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        } catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //+ sign
    public void testParse_double_plus_sign() throws Exception {
        String test_string = "+123.45";
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        } catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //- sign
    public void testParse_double_minus_sign() throws Exception {
        String test_string = "-123.45";
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //int range
    //Here we can deal with Integer.MIN_VALUE because r is Long.
    public void testParse_double_int_range() throws Exception {
        long r;
        String test_string;
        double java_result, my_result;

        for (r = Integer.MIN_VALUE; r <= Integer.MAX_VALUE; r += (Integer.MAX_VALUE / 100)) {
            test_string = Long.toString(r);
            java_result = r;
            try {
                my_result = myParser.parse_double(test_string);
                assertEquals(my_result, java_result);
            } catch (IllegalArgumentException e) {
                assert false;
            }
        }
        for (r = Integer.MAX_VALUE; r >= Integer.MIN_VALUE; r -= (Integer.MAX_VALUE / 100)) {
            test_string = Long.toString(r);
            java_result = r;
            try {
                my_result = myParser.parse_double(test_string);
                assertEquals(my_result, java_result);
            } catch (IllegalArgumentException e) {
                assert false;
            }
        }
    }

    @Test //long range
    //caught long border issue. We can only support the range of +/- Long.MAX_VALUE - 512)
    public void testParse_double_long_range() throws Exception {
        double r;
        String test_string;
        double java_result, my_result;
        double long_border = Long.MAX_VALUE - 512;

        for (r = -long_border; r <= long_border; r += (long_border / 5)) {
            test_string = Long.toString((new Double(r)).longValue());
            java_result = r;
            try {
                my_result = myParser.parse_double(test_string);
                assertEquals(my_result, java_result);
            } catch (IllegalArgumentException e) {
                assert false;
            }
        }
        for (r = long_border; r >= long_border; r -= (long_border / 5)) {
            test_string = Long.toString((new Double(r)).longValue());
            java_result = r;
            try {
                my_result = myParser.parse_double(test_string);
                assertEquals(my_result, java_result);
            } catch (IllegalArgumentException e) {
                assert false;
            }
        }
    }

    @Test //start with dot
    public void testParse_double_start_with_dot() throws Exception {
        String test_string = ".123";
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //end with dot
    public void testParse_double_end_with_dot() throws Exception {
        String test_string = "123.";
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //zero dot number
    public void testParse_double_zero_dot_number() throws Exception {
        String test_string = "0.123";
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //no dot
    public void testParse_double_no_dot() throws Exception {
        String test_string = "123";
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //zero only
    public void testParse_double_zero_only() throws Exception {
        String test_string = "0";
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //zero dot
    public void testParse_double_zero_dot() throws Exception {
        String test_string = "0.";
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //dot zero
    public void testParse_double_dot_zero() throws Exception {
        String test_string = ".0";
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //sign_zero
    public void testParse_double_sign_zero() throws Exception {
        String test_string = "-0";
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //zero zero
    public void testParse_double_zero_zero() throws Exception {
        String test_string = "-0.0";
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //pi number
    public void testParse_double_pi_number() throws Exception {
        String test_string = Double.toString(Math.PI);
        double java_result = Math.PI;
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //e number
    public void testParse_double_e_number() throws Exception {
        String test_string = Double.toString(Math.E);
        double java_result = Math.E;
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //pi string
    //caught precision bug, main program to use long to process int
    public void testParse_double_pi_string() throws Exception {
        String test_string = Double.toString(Math.PI);
        //substitute pi string dot to 0
        test_string = test_string.replace(".", "0");
        double java_result = Double.parseDouble(test_string);
        double my_result;
        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //e string
    public void testParse_double_e_string() throws Exception {
        String test_string = Double.toString(Math.E);
        //substitute e string dot to 0
        test_string = test_string.replace(".", "0");
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //max int
    public void testParse_double_max_int() throws Exception {
        String test_string = '-' + Long.toString(Integer.MAX_VALUE) + "." + Long.toString(Integer.MAX_VALUE);
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result);
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
    }

    @Test //max long
    public void testParse_double_max_long() throws Exception {
        String test_string = '-' + Long.toString(Long.MAX_VALUE) + "." + Long.toString(Long.MAX_VALUE);
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (ArithmeticException e) {
            assert true;
        }
    }

    @Test //dot max long
    public void testParse_double_dot_max_long() throws Exception {
        String test_string = "." + Long.toString(Long.MAX_VALUE);
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (ArithmeticException e) {
            assert true;
        }
    }


    @Test //dot max range
    //use double precision 1e-15
    public void testParse_double_dot_max_range() throws Exception {
        String test_string = "0." + Long.toString(Long.MAX_VALUE - 512);
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assertEquals(my_result, java_result, 1e-15);
        }
        catch (ArithmeticException e) {
            assert false;
        }
    }

    @Test //extra long
    //caught long overflow bug
    public void testParse_double_extra_long() throws Exception {
        String test_string = '-' + '1' + Long.toString(Long.MAX_VALUE) + "." + Long.toString(Long.MAX_VALUE) + '0';
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (ArithmeticException e) {
            assert true;
        }
    }

    @Test //max double
    //should throw IllegalArgumentException because it contains E notation
    public void testParse_double_max_double() throws Exception {
        String test_string = '-' + Double.toString(Double.MAX_VALUE);
        double java_result = Double.parseDouble(test_string);
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test //random string
    public void testParse_double_random_string() throws Exception {
        String test_string = "iouwefhrcp9438u";
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test //extra dots
    public void testParse_double_extra_dots() throws Exception {
        String test_string = "12.3.45";
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test //extra signs
    public void testParse_double_extra_signs() throws Exception {
        String test_string = "-+123.45";
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test //leading space
    public void testParse_double_leading_space() throws Exception {
        String test_string = " 123.45";
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test //extra space
    public void testParse_double_extra_space() throws Exception {
        String test_string = "123.45 ";
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test //dot only
    //caught input detection bug, main program added an if statement
    public void testParse_double_dot_only() throws Exception {
        String test_string = ".";
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test //sign only
    public void testParse_double_sign_only() throws Exception {
        String test_string = "-";
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test //sign dot
    public void testParse_double_sign_dot() throws Exception {
        String test_string = "+.";
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test //empty string
    public void testParse_double_empty_string() throws Exception {
        String test_string = "";
        double my_result;

        try {
            my_result = myParser.parse_double(test_string);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test //null string
    public void testParse_double_null_string() throws Exception {
        double my_result;

        try {
            my_result = myParser.parse_double(null);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }
}