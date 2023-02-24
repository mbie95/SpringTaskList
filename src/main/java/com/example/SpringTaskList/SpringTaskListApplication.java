package com.example.SpringTaskList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

//implementuje konfigurator, aby zmienic konfiguracje zarzadzania repozytorium
@SpringBootApplication
public class SpringTaskListApplication implements RepositoryRestConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(SpringTaskListApplication.class, args);
    }

    //bean, aby klasa byla znana springowi
    @Bean
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }
        
    @Override
    public void configureValidatingRepositoryEventListener(final ValidatingRepositoryEventListener validatingListener) {
        //przed stworzeniem i przed zapisem obiektu do repozytorium jest walidowany
        //dodaje odpowiednia wiadomosc przy wprowadzaniu blednego opisu zadania
        validatingListener.addValidator("beforeCreate", validator());
        validatingListener.addValidator("beforeSave", validator());
    }
    
}
