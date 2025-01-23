package com.tapir.goose.view;

import com.tapir.goose.view.pojo.BinanceVDO;
import com.tapir.goose.view.pojo.WalletVDO;
import com.tapir.goose.view.pojo.UserVDO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named("site")
@RequestScoped
public class SiteView implements Serializable {

    private static final Logger logger = LogManager.getLogger(SiteView.class);
    
    private String key;
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

    public UserVDO getUser() {
        return user;
    }

    public WalletVDO getWallet() {
        return wallet;
    }

    public List<BinanceVDO> getSymbols() {
        return symbols;
    }

}
