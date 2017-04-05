/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.util;

import java.util.Timer;
import javax.servlet.ServletContextEvent;

/**
 *
 * @author mavric
 */
public class CrisisStateListener implements javax.servlet.ServletContextListener {

    private static StateMonitor timerTask;
    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timerTask = new StateMonitor(false);
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 10000,10000);//1000*60*30, 1000*60*30);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        timer.cancel();
        timerTask.cancel();
        System.out.println("State monitor ended ");
    }
}
