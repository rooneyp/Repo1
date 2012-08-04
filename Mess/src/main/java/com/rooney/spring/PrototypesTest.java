package com.rooney.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:com/rooney/spring/MyApplicationContext1.xml" })
public class PrototypesTest {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) throws Exception {
		//MyApplicationContext.xml
        ClassPathXmlApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[] { "com/rooney/spring/MyApplicationContext1.xml" });
        // getBeans(appContext);


        Thread.sleep(1000 * 30);
    }

    @Test
    public void testPrototypes() throws Exception {
        getBeans(applicationContext);
    }

    private static void getBeans(ApplicationContext appContext) {
        Parent parent1 = (Parent) appContext.getBean("parent");
        System.err.println(parent1);
        System.err.println(parent1.getChild());

        Parent parent2 = (Parent) appContext.getBean("parent");
        System.err.println(parent2);
        System.err.println(parent2.getChild());

        System.out.println("String is set to " + parent1.getChild().getString());
    }

}
