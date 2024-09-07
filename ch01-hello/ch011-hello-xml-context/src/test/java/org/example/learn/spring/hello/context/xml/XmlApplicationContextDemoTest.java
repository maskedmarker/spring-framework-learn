package org.example.learn.spring.hello.context.xml;

import org.junit.Test;


/**
 * A bean definition essentially is a recipe for creating one or more objects.
 * The container looks at the recipe for a named bean when asked,
 * and uses the configuration metadata encapsulated by that bean definition to create (or acquire) an actual object.
 */
public class XmlApplicationContextDemoTest {

    @Test
    public void testRun() {
        XmlApplicationContextDemo application = new XmlApplicationContextDemo();
        application.run();
    }
}