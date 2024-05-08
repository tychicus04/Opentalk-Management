package com.tychicus.opentalk.example;

import jakarta.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanLifeCycle {


    @PostConstruct
    public void init() {
        System.out.println("From Post Construct method !!!");
        System.out.println("Create some database...");
    }

    public void hello() {
        System.out.println("Hello !!!");
    }

    @PreDestroy
    public void destroy () {
        System.out.println("From Pre Destroy method !!!");
        System.out.println("Release some resources ... closing database connection");
    }
}




/*
Bean life cycle is managed by the spring container.
Container started => Bean Instantiated => Dependencies Injected
=> Custom init() method => Custom utility method
=> Custom destroy() method
 */

/* @PostConstruct
Spring calls the methods annotated with @PostConstruct only once,
just after the initialization of bean properties. Keep in mind that
these methods will run even if there's nothing to initialize.
The method annotated with @PostConstruct can have any access level,
but it can't be static.

One possible use of @PostConstruct is populating a database.
 */

/* @PreDestroy
A method annotated with @PreDestroy runs only once, just before Spring
removes our bean from the application context.
Same as with @PostConstruct, the methods annotated with @PreDestroy
can have any access level, but can't be static.

The purpose of this method should be to release resources or perform
other cleanup tasks, such as closing a database connection, before the
bean gets destroyed.
 */
