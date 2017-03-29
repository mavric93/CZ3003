/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.controller;

import javax.servlet.http.HttpServletRequest;

import core.DAO.CrisisDAO;
import java.util.ArrayList;
import java.util.List;
import core.model.Crisis;
import java.util.Calendar;

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
            Crisis newCrisis = createCrisisFromRequest(request);
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

    public List<Crisis> list(HttpServletRequest request) {
        ArrayList<Crisis> crisisList = new ArrayList<Crisis>();
        try{
            crisisList = dao.getAllCrisis();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return crisisList;
    }
    
    protected Crisis createCrisisFromRequest(HttpServletRequest request) throws Exception {
        try {
            //CrisisID, CType, Description, Address, Lat, Lng, Status, TimeReported, TimeResolved
            String type = request.getParameter("crisisType");
            String address = request.getParameter("address");
            double latitude = Double.parseDouble(request.getParameter("latitude"));
            double longitude = Double.parseDouble(request.getParameter("longitude"));
            String description = request.getParameter("description");
            return new Crisis(type, address, latitude, longitude, description);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
