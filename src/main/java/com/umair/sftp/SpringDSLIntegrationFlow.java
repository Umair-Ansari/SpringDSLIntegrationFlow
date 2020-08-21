package com.umair.sftp;

import com.umair.sftp.sessions.SessionManager;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.dsl.context.IntegrationFlowContext;

@SpringBootApplication
@IntegrationComponentScan
@EnableScheduling
@EnableIntegration
public class SpringDSLIntegrationFlow implements ApplicationListener{


    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private IntegrationFlowContext context;

    public static void main(String[] args) {
        SpringApplication.run(SpringDSLIntegrationFlow.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent e) {
        File jarPath = new File(SpringDSLIntegrationFlow.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        jarPath = jarPath.getParentFile().getParentFile().getParentFile();
        
        
        StringBuilder sb = new StringBuilder(jarPath.toString());
        
        sb.append("\\");
        sb.append("keys");
        sb.substring(0,6);
        File directory = new File(sb.toString().substring(6));
        
        
        if (!directory.exists()) {
            directory.mkdir();
        }
        
        StringBuilder sbFile = new StringBuilder(jarPath.toString());
        
        sbFile.append("\\");
        sbFile.append("temp");
        sbFile.substring(0,6);
        directory = new File(sbFile.toString().substring(6));
        
        
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
