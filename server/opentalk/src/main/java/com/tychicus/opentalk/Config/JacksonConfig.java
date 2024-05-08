package com.tychicus.opentalk.Config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

//    @Bean
//    @Primary
//    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
//        System.out.println("Config is starting.");
//        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        return objectMapper;
//    }
//    @Bean
//    public Module jsonMapperJava8DateTimeModule() {
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
//        module.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
//        module.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
//        module.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
//        return module;
//    }
}
