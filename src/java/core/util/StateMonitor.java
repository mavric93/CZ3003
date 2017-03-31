/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.util;

import core.DAO.CrisisDAO;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import javax.swing.Timer;

/**
 *
 * @author mavric
 */
public class StateMonitor extends TimerTask {

    private boolean currentCrisisState;
    CrisisDAO crisisDAO = new CrisisDAO();

    public StateMonitor(boolean isCrisis) {
        this.currentCrisisState = isCrisis;
        System.out.println("started");
    }

    @Override
    public void run() {
        boolean newCrisisState = crisisDAO.checkComfirmedCrisis();
        boolean stateChanged = newCrisisState && this.currentCrisisState;

        if (currentCrisisState && !newCrisisState) {
            //send final report
            System.out.println("send last report " + System.nanoTime());
        }

        currentCrisisState = newCrisisState;
        if (currentCrisisState) {
            System.out.println("send email " + System.nanoTime());
        } else {
            System.out.println("no ongoing crisis new message " + System.nanoTime());

        }
    }
}
