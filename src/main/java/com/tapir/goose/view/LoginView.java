package com.tapir.goose.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

@Named("login")
@RequestScoped
public class LoginView implements Serializable {

    private static final Logger logger = LogManager.getLogger(LoginView.class);

    private String message;
    private Boolean condition;

    @PostConstruct
    public void init() {
        logger.info("Init Login");
        condition = true;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getCondition() {
        return condition;
    }

    public String action() throws InterruptedException {
        if (!condition) {
            message = "error from message";
            logger.error("Invalid credentials");
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Authentication failed error ...",
                    "error");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
            return null;
        }
        logger.error("step 1: validate key-secret in binance");
        logger.error("step 1.1: if key-secret are invalid show error");
        logger.error("step 1.2: if key-secret are invalid delete user (thread)");
        Thread.sleep(3000);
        logger.error("step 2: get or create user");
        Thread.sleep(3000);
        logger.info("message: {}", message);
        return "site";
    }

}
