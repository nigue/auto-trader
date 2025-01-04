package com.tapir.goose.view;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("percent")
@SessionScoped
public class PercentBean implements Serializable {

private int progress;
    
    public void doProgress(){
        class ProgressThread extends Thread{

            @Override
            public void run() {
                while(progress<100){
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        
                    }
                    progress=progress+5;
                }
            }
            
        }
        
        ProgressThread pt=new ProgressThread();
        pt.start();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    
}
