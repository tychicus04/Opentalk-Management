package com.tychicus.opentalk.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.annotation.ApplicationScope;

public class BeanLifeCycleMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyConfigBeanLifeCycle.class);

        BeanLifeCycle myBeanForLifeCycle3 = context.getBean(BeanLifeCycle.class);

        myBeanForLifeCycle3.hello();

//        context.close();
    }

//         Bean Scope
//        System.out.println(myBeanForLifeCycle3);
//        System.out.println(myBeanForLifeCycle2);
//    }
}
