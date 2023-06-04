package org.example.learn.spring.framework.context.annotation;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnnotationApplicationContextDemoTest {

    @Test
    public void testRun() {
        AnnotationApplicationContextDemo demo = new AnnotationApplicationContextDemo();
        demo.run();
    }
}