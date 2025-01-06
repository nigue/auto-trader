package com.tapir.goose.view;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("indexBean")
@SessionScoped
public class ProgressBean implements Serializable {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String action() throws InterruptedException {
        Thread.sleep(6000);
        System.out.println(message);
        return "site";
    }
}
