/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.controller;

import javax.servlet.http.HttpServletRequest;

import core.DAO.CrisisDAO;
import core.DAO.TerrorismCrisisDAO;
import core.DAO.TrainBreakDownCrisisDAO;
import java.util.ArrayList;
import java.util.List;
import core.model.Crisis;
import core.model.TerrorismCrisis;
import core.model.TrainBreakDownCrisis;
import java.util.Calendar;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author mavric
 */
public class CrisisController {
    private CrisisDAO dao;
    private String errorStack;
    public CrisisController() {
        super();
        dao = new CrisisDAO();
    }
    public int create(HttpServletRequest request) throws Exception {
        int id = -1;
        try {
            System.out.println(request.getParameter("crisisType"));
            String type = request.getParameter("crisisType");
            String address = request.getParameter("address");
            double latitude = Double.parseDouble(request.getParameter("latitude"));
            double longitude = Double.parseDouble(request.getParameter("longitude"));
            String description = request.getParameter("description");
            int mobileNumber = Integer.parseInt(request.getParameter("mobilenumber"));
            Crisis newCrisis =  new Crisis(type, address, latitude, longitude, description,mobileNumber);
            id = dao.addCrisis(newCrisis);
        } catch (Exception ex) {
            throw ex;
        }
        return id;
    }

    public boolean update(HttpServletRequest request) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //able to update description, status and timeResolved
        boolean success = false;
	try {
            int crisisID = Integer.parseInt(request.getParameter("crisisID"));
            String description = request.getParameter("description");
            String status = request.getParameter("status");
            Calendar timeResolved = null;
            if(status.toUpperCase().equals("Resolved")){
                timeResolved = Calendar.getInstance();
            }
            Crisis crisis = new Crisis();
            crisis.setCrisisID(crisisID);
            crisis.setDescription(description);
            crisis.setStatus(status);
            crisis.setTimeResolved(timeResolved);
            success = dao.updateCrisis(crisis);
        } catch (Exception ex) {
            throw ex;
        }
	return success;
    }

    public Crisis read(HttpServletRequest request) {
	int crisisID = Integer.parseInt(request.getParameter("crisisID"));
        Crisis crisis  = dao.getCrisisById(crisisID);
        return crisis;
    }

    public List<Crisis> list() {
        ArrayList<Crisis> crisisList = new ArrayList<Crisis>();
        try{
            crisisList = dao.getAllCrisis();
            
            for(int i=0;i<crisisList.size();i++){
                Crisis crisis = crisisList.get(i);
                if(crisis instanceof TrainBreakDownCrisis){
                    TrainBreakDownCrisisDAO tbdDAO = new TrainBreakDownCrisisDAO();
                    crisisList.set(i, tbdDAO.getCrisisById(crisis.getCrisisID(), crisis));
                }else if(crisis instanceof TerrorismCrisis){
                    TerrorismCrisisDAO tDAO = new TerrorismCrisisDAO();
                    crisisList.set(i, tDAO.getCrisisById(crisis.getCrisisID(), crisis));
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return crisisList;
    }
    
    public JSONArray listJSON(){
        List<Crisis> crisisList = list();
        JSONArray JSONArr = new JSONArray();
        for(Crisis c : crisisList){
            JSONArr.put(c.toJSON());
        }
        return JSONArr;
    }
    
}
