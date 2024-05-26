package org.scimat_plus.bibliometricwe.workflowmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })

public class WorkflowManagerApplication {



    public static void main(String[] args) {
        SpringApplication.run(WorkflowManagerApplication.class, args);
    }

}
