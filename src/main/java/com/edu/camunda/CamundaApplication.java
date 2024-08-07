package com.edu.camunda;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableZeebeClient  //проверить позже без неё
@Deployment(resources = "classpath:/process/*.bpmn")
@SpringBootApplication
public class CamundaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamundaApplication.class, args);
    }


}
