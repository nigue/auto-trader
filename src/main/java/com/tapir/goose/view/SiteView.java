package com.tapir.goose.view;

import com.tapir.goose.view.pojo.BinanceVDO;
import com.tapir.goose.view.pojo.UserVDO;
import com.tapir.goose.view.pojo.OperationVDO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.List;

import static com.tapir.goose.view.ViewConstants.*;

@Named("site")
@SessionScoped
public class SiteView implements Serializable {

    private static final Logger logger = LogManager.getLogger(SiteView.class);

    private double progress = 0d;
    private Boolean condition = true;
    private String key;
    private String secret;
    private String selectedSymbol;
    private UserVDO user;
    private OperationVDO operation;
    private List<BinanceVDO> symbols;

    @PostConstruct
    public void init() {
        logger.info("Enter to Site");
        progress = 0d;
        key = (String) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("key");
        secret = (String) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("secret");

        logger.info("Navigate to site with key: {}", key);
        user = getFlashAttribute(USER, UserVDO.class);
        operation = getFlashAttribute(OPERATION, OperationVDO.class);
        symbols = getFlashAttributeList(SYMBOLS, BinanceVDO.class);
    }

    private <T> T getFlashAttribute(String key, Class<T> type) {
        Object value = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get(key);
        return type.isInstance(value) ? type.cast(value) : null;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getFlashAttributeList(String key, Class<T> type) {
        Object value = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get(key);

        if (value instanceof List<?>) {
            List<?> list = (List<?>) value;
            if (!list.isEmpty() && type.isInstance(list.get(0))) {
                return (List<T>) list;
            }
        }
        return null;
    }

    public String enterOperation() {
        progress = 0;
        if (!condition) {

            logger.error("Invalid credentials");

            addMessage(FacesMessage.SEVERITY_ERROR,
                    "Error Message",
                    "Message Content");

            return "";
        }
        try {
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
        logger.info("navigate to login");
        return "login";
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

    public UserVDO getUser() {
        return user;
    }

    public OperationVDO getOperation() {
        return operation;
    }

    public List<BinanceVDO> getSymbols() {
        return symbols;
    }

    public String getSelectedSymbol() {
        return selectedSymbol;
    }

    public void setSelectedSymbol(String selectedSymbol) {
        this.selectedSymbol = selectedSymbol;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
