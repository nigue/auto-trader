package com.tapir.goose.view;

import com.tapir.goose.view.pojo.BinanceVDO;
import com.tapir.goose.view.pojo.UserVDO;
import com.tapir.goose.view.pojo.WalletVDO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
    private WalletVDO wallet;
    private List<BinanceVDO> symbols;

    @PostConstruct
    public void init() {
        logger.info("Enter to Site");
        key = (String) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("key");
        secret = (String) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("secret");

        logger.info("Navigate to site with key: {}", key);
        user = new UserVDO("pancracio",
                "usdt", 
                BigDecimal.valueOf(0.5D), 
                BigDecimal.valueOf(35000D));
        wallet = new WalletVDO(true,
                "usdt",
                BigDecimal.valueOf(32000D),
                BigDecimal.valueOf(104000D),
                BigDecimal.valueOf(103000D),
                BigDecimal.valueOf(102000D),
                "29/12 00:42");
        var row = new BinanceVDO("BTC-USDT",
                "15m",
                5,
                BigDecimal.valueOf(-3.12345D));
        symbols = Arrays.asList(row, row);
    }

    public String enterOperation() {
        /*progress = 0;
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
        }*/
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

    public WalletVDO getWallet() {
        return wallet;
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
