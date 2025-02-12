package com.tapir.goose.view;

import com.tapir.goose.view.pojo.UserVDO;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;

@Named("login")
@ViewScoped
public class LoginView implements Serializable {

    private static final Logger logger = LogManager.getLogger(LoginView.class);

    private static final long serialVersionUID = 1L;

    private double progress = 0d;
    private Boolean condition = true;

    private String key;
    private String secret;

    /*
    Command execute
    Process with multiple steps
    - obtain secret and key
    - validate with binance, response boolean valid
        - if are invalid, throw error
    - obtain user (sell%, obj, symbols_interval_list)
        - if exist: get actual data of user
        - if not exist: get new data of user
    - get actual balance
    - calculate state (free, in_operation)
        - if free: symbol table with binance
        - if in_operation: active_order, last_enter_order
    */
    public String longOperation() {
        progress = 0;
        if (!condition) {

            logger.error("Invalid credentials");

            addMessage(FacesMessage.SEVERITY_ERROR,
                    "Error Message",
                    "Message Content");

            return "";
        }
        try {
            UserVDO userVDO = new UserVDO("ricardo",
                    "usdt",
                    BigDecimal.valueOf(0.5D),
                    BigDecimal.valueOf(35000D));
            putValue("user", userVDO);
            progress += 20;
            logger.info("progress {}", progress);
            Thread.sleep(2000);
            progress += 20;
            logger.info("progress {}", progress);
            Thread.sleep(2000);
            progress += 20;
            logger.info("progress {}", progress);
            Thread.sleep(2000);
            progress += 20;
            logger.info("progress {}", progress);
            Thread.sleep(2000);
            progress += 10;
            logger.info("progress {}", progress);
            Thread.sleep(2000);
            
            putValue("key", key);
            putValue("secret", secret);
            progress += 10;
            logger.info("progress {}", progress);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "site";
    }

    private void putValue(String name, Object value) {
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
