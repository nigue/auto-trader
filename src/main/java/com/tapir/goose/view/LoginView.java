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

    public String longOperation() {
        if (!condition) {

            logger.error("Invalid credentials");

            addMessage(FacesMessage.SEVERITY_ERROR,
                    "Error Message",
                    "Message Content");

            return "";
        }
        for(int i = 0; i < 100; i++)
        {
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

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .put("key", message);
        return "site";
    }


    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public double getProgress()
    {
        return progress;
    }

    public void setProgress(double progress)
    {
        this.progress = progress;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
