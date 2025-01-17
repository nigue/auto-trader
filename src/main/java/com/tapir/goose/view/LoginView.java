package com.tapir.goose.view;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

@Named("login")
@ViewScoped
public class LoginView implements Serializable {

    private static final Logger logger = LogManager.getLogger(LoginView.class);

    private static final long serialVersionUID = 1L;

    private double progress = 0d;
    private String message = "ready";
    private Boolean condition = true;

    private String key;
    private String secret;

    public String longOperation() {
        if (!condition) {

            logger.error("Invalid credentials");

            addMessage(FacesMessage.SEVERITY_ERROR,
                    "Error Message",
                    "Message Content");

            return "";
        }
        for (int i = 0; i < 100; i++) {
            // simulate a heavy operation
            progress++;
            message = "processing [" + i + "]";
            logger.info(message);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        message = "completed";

        putValue("key", key);
        putValue("secret", secret);
        return "site";
    }

    private void putValue(String name, String value) {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .put(name, value);
    }


    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
