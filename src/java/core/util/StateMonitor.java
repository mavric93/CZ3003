/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.util;

import core.DAO.CrisisDAO;
import core.controller.ReportController;
import java.util.Calendar;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author mavric
 */
public class StateMonitor extends TimerTask {

    private boolean currentCrisisState;
    CrisisDAO crisisDAO;
    Calendar start, end;

    public StateMonitor(boolean isCrisis) {
        this.currentCrisisState = isCrisis;
        crisisDAO = new CrisisDAO();
        System.out.println("started");
    }

    @Override
    public void run() {
        boolean newCrisisState = crisisDAO.checkComfirmedCrisis();
        boolean stateChanged = newCrisisState && this.currentCrisisState;

        if (!currentCrisisState && newCrisisState) {//from non-crisisState to crisisState
            start = Calendar.getInstance();
        }
        if (currentCrisisState && !newCrisisState) {//from crisisState to non-crisisState
            //send final report
            end = Calendar.getInstance();
            System.out.println("send last report " + System.nanoTime());
            //ReportController con = new ReportController();
            //JSONArray reportContent = con.listResolved(start, end);
            //System.out.println("LAST JSON:" + reportContent.toString());
            //after sending final report
            start = null;
            end = null;
        }

        currentCrisisState = newCrisisState;
        if (currentCrisisState) {
            System.out.println("In Crisis State " + System.nanoTime());
        //    ReportController con = new ReportController();
        //    JSONArray reportContent = con.listOngoing();

//            for (int i = 0; i < reportContent.length(); i++) {
//                JSONObject crisisJSON = reportContent.getJSONObject(i);
//                String typeOfCrisis = crisisJSON.getString("crisisType");
//                String crisisID = crisisJSON.getInt("crisisID")+"";
//                ReportGenerator.generateReport(crisisID, typeOfCrisis,crisisJSON.toString());
//            }

        } else {
            System.out.println("no ongoing crisis new message " + System.nanoTime());

        }
    }
}
