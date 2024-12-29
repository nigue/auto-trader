package com.tapir.goose.view;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named("index")
@SessionScoped
public class IndexView implements Serializable {

    private static final Logger logger = LogManager.getLogger(IndexView.class);

    public String login() {
        logger.info("Login at {}", new Date());
        return "site";
    }
}
