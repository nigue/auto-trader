package com.tapir.goose.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named("index")
@SessionScoped
public class IndexView implements Serializable {

    private static final Logger logger = LogManager.getLogger(IndexView.class);
    
    private Boolean active;
    private String key;

    @PostConstruct
    public void init() {
        active = false;
    }
    
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String login() {
        logger.info("Login at {}", new Date());
        logger.info("Login with key: {}", key);
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("key", key);
        
        return "site";
    }
}
