package com.qeautos.java_scala_examples;

import org.testng.annotations.Test;

public class JavaServiceTest {

    @Test
    public void testJavaServiceHello() throws Exception {
        new JavaService().hello();
    }
}