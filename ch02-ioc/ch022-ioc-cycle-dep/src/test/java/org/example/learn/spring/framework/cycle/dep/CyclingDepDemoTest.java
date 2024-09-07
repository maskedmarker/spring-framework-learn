package org.example.learn.spring.framework.cycle.dep;

import org.junit.Test;

public class CyclingDepDemoTest {

    @Test
    public void testRun() {
        CyclingDepDemo application = new CyclingDepDemo();
        application.run();
    }
}