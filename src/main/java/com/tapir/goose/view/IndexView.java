package com.tapir.goose.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named("index")
@SessionScoped
public class IndexView implements Serializable {

    private static final Logger logger = LogManager.getLogger(IndexView.class);
    
    private Boolean active;
    private String key;
    private Integer step;
    private Boolean running = false; // Para controlar la simulación

    @PostConstruct
    public void init() {
        active = false;
        step = 0;
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

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public String login() {
        logger.info("Login at {}", new Date());
        logger.info("Login with key: {}", key);
        
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .put("key", key);


        return "site";
    }

    public void enter() throws InterruptedException {
        if (!running) {
            running = true;
            // Inicia un nuevo hilo para actualizar step en segundo plano
            new Thread(() -> {
                for (int i = 0; i <= 5; i++) {
                    try {
                        step = i; // Actualiza el valor
                        Thread.sleep(3000); // Espera 3 segundos
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                running = false; // Finaliza la ejecución al llegar a 5
            }).start();
        }
    }
}
