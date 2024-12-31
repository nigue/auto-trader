package com.tapir.goose.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named("site")
@RequestScoped
public class SiteView implements Serializable {

    private static final Logger logger = LogManager.getLogger(SiteView.class);
    
    private Boolean active;
    private String key;
    private String date;

    @PostConstruct
    public void init() {
        key = (String) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("key");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        date = dateFormat.format(Calendar.getInstance().getTime());
        logger.info("Navigate to site with key: {}", key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
