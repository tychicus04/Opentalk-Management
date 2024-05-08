package com.tychicus.opentalk.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Properties;

@Configuration
public class EmailConfig {

//    // Create a JavaMailSender bean to send emails. We'll use Gmail's SMTP server for this example
//    @Bean
//    public JavaMailSender getJavaMailSender(){
//        return new JavaMailSenderImpl();
//    }

    // create the factory method for the Thymeleaf engine. We'll need to tell the engine which TemplateResolver we've chosen,
    // which we can inject via a parameter to the bean factory method
    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine (ITemplateResolver templateResolver) {
        // SpringTemplateEngine automatically applies SpringStandardDialect and
        // enables Spring's own MessageSource message resolution mechanisms.
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        // Set the template resolver for the template engine
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    // Provide a template resolver to locate the template files directory.
    @Bean
    @Primary
    public ITemplateResolver thymeleafTemplateResolver () {
        // We'll use a ClassLoaderTemplateResolver to locate the template files in the classpath
        ClassLoaderTemplateResolver templateResolver
                = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("mail-templates/");
        templateResolver.setSuffix(".html");
        // HTML is the default value, added here for the sake of clarity.
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    // In order to manage translations with Thymeleaf, we can specify a MessageSource instance to the engine
    // Then, we'd create resource bundles for each locale we support: src/main/resources/mailMessages_xx_YY.properties
    @Bean
    public ResourceBundleMessageSource emailMessageSource () {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("mailMessages");
        return messageSource;
    }
}
