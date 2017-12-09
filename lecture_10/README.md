# 9. Spring I - intro

## Spring Framework

- open source project
- Apache 2.0 license, June 2003, Rod Johnson
- currently version 5.0.2
- originally designed to simplify development
- integrates well known frameworks
- DI,AOP
- versions are not 100% compatible

## Benefits of using Spring

- eliminate boilerplate
- organized into modules (no need to include whole stack)
- based on Java SE (application server not required)


## Spring modules

- about 20 modules of Spring framework + ecosystem
![](https://www.tutorialspoint.com/spring/images/spring_architecture.png)

## Spring modules - Data Access

- JDBC - abstraction above the plain JDBC
- ORM - integration with JPA,Hibernate, JDO, iBatis
- OXM - abstraction above Object/XML mapping
- Transaction - transaction management


## Spring modules - Web

- Web-MVC - implementation for web apps (MVC pattern)
- Web-Socket - support for web socket communication
- Web-Portlet - MVC implementation for portlets

## Spring modules - other

- AOP - aspect oriented programing
- Aspects - integration with AspectJ framework
- Test - support for JUnit and TestNG frameworks

## Spring - IoC container

- part of core
- container handles Spring Bean
- objects assembled according to configuration data
- XML,Annotation or Java code

## Spring - IoC - BeanFactory

- basic support for DI
- less resource consuming
- implements BeanFactory interface (org.springframework.beans.factory.BeanFactory)
- provides wiring, bean instantiation
- exists for backward compatibility

## Spring - IoC - ApplicationContext

- extends BeanFactory functionality
- bean post processor
- supports all spring features out of box
- use an ApplicationContext unless you have a really good reason for not doing so


## Bean Definitions

- POJO managed by container
- configuration metadata - definition,lifecycle details, dependencies
- defined in context config
- bean inheritance - reuse the properties configuration
- abstract bean to inherit from

```
    <!--Abstract bean as a template for all other-->
    <bean id="beanTemplate" abstract="true">
        <property name="message1" value="Hello World!"/>
        <property name="message2" value="Hello Second World!"/>
        <property name="message3" value="Namaste India!"/>
    </bean>
    
    <!-- Sample bean using inheritance -->
    <bean id="sampleBean"
          class="cz.sohlich.spring.bean.SampleBean"
          parent="beanTemplate">
    </bean>
    
    <bean id="dependent" class="cz.sohlich.spring.bean.DependentBean">
        <constructor-arg name="injectedBean"
                         ref="sampleBean"></constructor-arg>
    </bean>
```

## Bean Scopes

- singleton - only one in container
- prototype - new one each time it is needed
- request (web)
- session (web)
- global-session (web)

```
    <bean id="sampleBean"
          scope="singleton"
          class="cz.sohlich.spring.bean.SampleBean">
    </bean>
```

## Bean lifecycle

- init-method (org.springframework.beans.factory.InitializingBean)
- destroy-method (org.springframework.beans.factory.DisposableBean)
- default-init-method="init"  default-destroy-method="destroy"
- implementation of interface not widely used

```
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-3.0.xsd"
        default-init-method="init" 
        default-destroy-method="destroy">
        
      <bean id="sampleBean"
            scope="singleton"
            class="cz.sohlich.spring.bean.SampleBean"
            init-method="init"
            destroy-method="destroy" >
      </bean>
    </beans>
```

## Spring - Bean Post Processors

- callback on bean instantiation
- implement BeanPostProcessor
- ApplicationContext detects automatically

```
    @Component
    public class PostProcessor implements BeanPostProcessor {
    
        Logger log = LoggerFactory.getLogger(PostProcessor.class);
    
        public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
            log.info("Instantiating " + s);
            return o;
        }
    
        public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
            log.info("Instantiating " + s + " DONE");
            return o;
        }
    }
```

## Dependency Injection

- Setter based → <property>
- Constructor based → <constructor-arg>
- Autowiring - byName, byType, constructor, auto detect
- Autodetect → Spring first tries to wire using autowire by constructor, if it does not work, Spring tries to autowire by byType.

```   
    <bean id="outerBean" class="...">
          <property name="target">
             <bean id="innerBean" class="..."/>
          </property>
    </bean>
```

## Beans  - annotations

- `<context:annotation-config/>`
- @Required, @Autowired,@Qualifier
- @Resource, @PostConstruct and @PreDestroy
- annotations @Component, @Repository, @Service and @Controller
    @Service
    @Scope("prototype")
    public class PrototypeComponent {
    }

## Java Based Configuration

- @Configuration annotation
- @Bean
- composable with XML

```
    @Configuration
    @EnableWebMvc
    @EnableTransactionManagement
    @ComponentScan(basePackages = "eu.edhouse.spring.notes")
    public class ApplicationConfiguration {
      @Bean
      public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(H2).build();
      }
    }
```

