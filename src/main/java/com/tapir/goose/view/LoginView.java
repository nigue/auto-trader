package com.tapir.goose.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

@Named("login")
@RequestScoped
public class LoginView implements Serializable {

    private static final Logger logger = LogManager.getLogger(LoginView.class);

    private Integer loadStep;

    @PostConstruct
    public void init() {
        loadStep = 0;
    }

    public Integer getLoadStep() {
        return loadStep;
    }

    public Long getDateView() {
        return System.currentTimeMillis();
    }

    public void execute() throws InterruptedException {
        loadStep++;
        Thread.sleep(2000);
        loadStep++;
        Thread.sleep(2000);
        loadStep++;
        Thread.sleep(2000);
        loadStep++;
    }

}
