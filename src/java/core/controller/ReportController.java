/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.controller;

import core.DAO.CrisisDAO;
import core.DAO.CrisisUpdateDAO;
import core.DAO.TerrorismCrisisDAO;
import core.DAO.TrainBreakDownCrisisDAO;
import core.model.Crisis;
import core.model.CrisisUpdate;
import core.model.TerrorismCrisis;
import core.model.TrainBreakDownCrisis;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author mavric
 */
public class ReportController {
    private CrisisDAO cdao;
    private CrisisUpdateDAO udao;
    
    public ReportController(){
        cdao = new CrisisDAO();
        udao = new CrisisUpdateDAO();
    }
    public JSONArray listResolved(Calendar from,Calendar to){
        JSONArray ja = new JSONArray();
        try{
            ArrayList<Crisis> crisisList = cdao.getResolvedCrisis(convertCalendar(from), convertCalendar(to));
            for(int i=0;i<crisisList.size();i++){
                Crisis crisis = crisisList.get(i);
                if(crisis instanceof TrainBreakDownCrisis){
                    TrainBreakDownCrisisDAO tbdDAO = new TrainBreakDownCrisisDAO();
                    crisisList.set(i, tbdDAO.getCrisisById(crisis.getCrisisID(), crisis));
                }else if(crisis instanceof TerrorismCrisis){
                    TerrorismCrisisDAO tDAO = new TerrorismCrisisDAO();
                    crisisList.set(i, tDAO.getCrisisById(crisis.getCrisisID(), crisis));
                }
                List<CrisisUpdate> updates = udao.getAllCrisisUpdate(crisis.getCrisisID());
                JSONObject jo = crisisList.get(i).toJSON();
                JSONArray jaUpdate = new JSONArray();
                for(int j=0;j<updates.size();j++){
                    jaUpdate.put(updates.get(j).toJSON());
                }
                jo.put("updates",jaUpdate);
                ja.put(jo);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ja;
    }
    public JSONArray listOngoing(){
        JSONArray ja = new JSONArray();
        try{
            ArrayList<Crisis> crisisList = cdao.getAllOngoingCrisis();
            for(int i=0;i<crisisList.size();i++){
                Crisis crisis = crisisList.get(i);
                if(crisis instanceof TrainBreakDownCrisis){
                    TrainBreakDownCrisisDAO tbdDAO = new TrainBreakDownCrisisDAO();
                    crisisList.set(i, tbdDAO.getCrisisById(crisis.getCrisisID(), crisis));
                }else if(crisis instanceof TerrorismCrisis){
                    TerrorismCrisisDAO tDAO = new TerrorismCrisisDAO();
                    crisisList.set(i, tDAO.getCrisisById(crisis.getCrisisID(), crisis));
                }
                List<CrisisUpdate> updates = udao.getAllCrisisUpdate(crisis.getCrisisID());
                JSONObject jo = crisisList.get(i).toJSON();
                JSONArray jaUpdate = new JSONArray();
                for(int j=0;j<updates.size();j++){
                    jaUpdate.put(updates.get(j).toJSON());
                }
                jo.put("updates",jaUpdate);
                ja.put(jo);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ja;
    }
    private String convertCalendar(Calendar c){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        if (c == null) {
            return "null";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(c.getTime());
    }
}
