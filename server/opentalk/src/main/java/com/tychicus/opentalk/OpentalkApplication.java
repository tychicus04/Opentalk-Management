package com.tychicus.opentalk;

import com.tychicus.opentalk.config_profile_demo.*;
import com.tychicus.opentalk.example.BeanLifeCycle;
//import com.tychicus.opentalk.example.MyClassForInjectValueTest;
import com.tychicus.opentalk.example.MyConfigBeanLifeCycle;
import com.tychicus.opentalk.example.PrototypeBean;
import com.tychicus.opentalk.example.SingletonBean;
import com.tychicus.opentalk.model.Employee;
import com.tychicus.opentalk.repository.EmployeeRepository;
import com.tychicus.opentalk.service.impl.EmailService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;
import java.util.Optional;


// @SpringBootApplication:  auto-configuration and component scanning.
// A single @SpringBootApplication annotation can be used to enable @ComponentScan:
// @ComponentScanspringboot-restful-webservices: enable @Component scan on the package where the application is located
@SpringBootApplication
@EnableScheduling
@EnableCaching
@PropertySources({
		@PropertySource(value = "classpath:info1.properties"),
		@PropertySource(value = "classpath:info2.properties")

})
@ConfigurationPropertiesScan("com.tychicus.opentalk.config_profile_demo")
public class OpentalkApplication implements CommandLineRunner{
	// ====IOC: CREATE, AUTOWIRE, CONFIG AND MANAGE CYCLE. USING DI MANAGE COMPONENT BUILD APP -> SPRING BEAN
	// ====DI: CLASS NOT DIRECT INTERACT PARENT CALL CHILD: INTERFACE. REDUCE DEPENDENCIES MODULES
	public static void main(String[] args) {
		SpringApplication.run(OpentalkApplication.class, args);

//		SpringApplication application = new SpringApplication(OpentalkApplication.class);
//		ConfigurableEnvironment environment = new StandardEnvironment();
//		environment.setActiveProfiles("local");
//		application.setEnvironment(environment);
//		application.run(args);
	}

	@Value("${my.property.name}")
	private String priority;

	@Value("${myCompany.name1}") // get value with @PropertySource
	public String name1;

	@Value("${myCompany.name2}") // get value with @PropertySource
	public String name2;

	@Autowired //Environment Abstraction
	private Environment env;


	@Autowired // get value with prefix = mail1
	private ConfigProperties configProperties;

	@Autowired
	private ConfigPropertiesWithScan configPropertiesWithScan;

	@Autowired // get value with prefix = mail3
	private ConfigNestedProperties configNestedProperties;

	@Autowired // multiple file
	private YmlConfig ymlConfig;

	@Autowired// list object
	private ConfigListObject configListObject;

	////	=========== EXAMPLE SINGLETON AND PROTOTYPE ================
	@Autowired
	private PrototypeBean prototype1;
	@Autowired
	private PrototypeBean prototype2;

	@Autowired
	private SingletonBean singleTon1;
	@Autowired
	private SingletonBean singleTon2;



	@Override
	public void run(String... args) throws Exception {
//
		//========Spring Boot Usage==========
//		@PropertySources
		System.out.println("====== GET VALUE WITH PROPERTY SOURCES=======");
		System.out.println(name1);
		System.out.println(name2 + "\n");
		// Environment Abstraction
		System.out.println("====== GET VALUE WITH ENV ABS=======");
		System.out.println(env.getProperty("myCompany.name1") + "\n");
		// @ConfigurationProperties
		System.out.println("====== GET VALUE WITH PREFIX=======");
		System.out.println(configProperties.getHostName());
		System.out.println(configProperties.getFrom());
		System.out.println(configProperties.getPort() + "\n");
		// config with scan
		System.out.println("====== GET VALUE WITH PREFIX +  SCAN=======");
		System.out.println(configPropertiesWithScan.getHostName());
		System.out.println(configPropertiesWithScan.getFrom());
		System.out.println(configPropertiesWithScan.getPort() + "\n");

//         config nested class
		System.out.println("====== GET VALUE WITH PREFIX =======");
		System.out.println("====== GET VALUE WITH NESTED PROPERTIES=======");
		System.out.println("Default Recipients:");
		for (String recipient : configNestedProperties.getDefaultRecipients()) {
			System.out.println("  - " + recipient);
		}

		System.out.println("Additional Headers:");
		for (Map.Entry<String, String> entry : configNestedProperties.getAdditionalHeaders().entrySet()) {
			System.out.println("  " + entry.getKey() + ": " + entry.getValue());
		}

		User user = configNestedProperties.getUser();
		if (user != null) {
			System.out.println("Credentials:");
			System.out.println("  Username: " + user.getUsername());
			System.out.println("  Password: " + user.getPassword());
			System.out.println("  Auth Method: " + user.getAuthMethod());
		}
		System.out.println();


		// Config multiple profile
		System.out.println("====== GET VALUE WITH MULTIPLE FILE =======");
		System.out.println("using environment: " + ymlConfig.getEnvironment());
		System.out.println("name: " + ymlConfig.getName());
		System.out.println("enabled:" + ymlConfig.isEnabled());
		System.out.println("servers: " + ymlConfig.getServers() + "\n");

		// Config with ListObject
		System.out.println("====== GET VALUE WITH LIST OBJECT =======");
		for (Object o : configListObject.getProfiles()) {
			System.out.println(o);
		}

		// Test with priority of application.yml and active file
		System.out.println("=============TEST WITH PRIORITY OF APPLICATION.YML AND ACTIVE FILE USING IDE");
		System.out.println(priority);


		// =========== EXAMPLE BEAN LIFE CYCLE ======================
		System.out.println("=========== EXAMPLE SINGLETON AND PROTOTYPE ================");
		System.out.println(prototype1 == prototype2); //false
		System.out.println(singleTon1 == singleTon2);//true

		// =========== EXAMPLE Fetch type ======================
		System.out.println("=========== EXAMPLE FETCH LAZY AND EAGER ======================");
//		findUserById();

	}



	@Autowired
	private EmployeeRepository employeeRepository;



	private void findUserById() {

		Long theId = 1l;
		Optional<Employee> employee = employeeRepository.findById(theId);

		System.out.println("employee: " + employee.get());
		System.out.println("the associated companyBranch: " + employee.get().getCompanyBranch());

		System.out.println("Done!");
	}

}


