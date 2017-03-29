/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.controller;

import javax.servlet.http.HttpServletRequest;

import core.DAO.TrainBreakDownCrisisDAO;
import core.model.Crisis;
import core.model.TrainBreakDownCrisis;

/**
 *
 * @author mavric
 */
public class TrainBreakDownCrisisController extends CrisisController {

    private TrainBreakDownCrisisDAO dao;

    public TrainBreakDownCrisisController() {
        super();
        dao = new TrainBreakDownCrisisDAO();
    }
    @Override
    public int create(HttpServletRequest request) throws Exception {
        int id =-1;
        try {
            id = super.create(request);
            String type = request.getParameter("crisisType");
            String address = request.getParameter("address");
            double latitude = Double.parseDouble(request.getParameter("latitude"));
            double longitude = Double.parseDouble(request.getParameter("longitude"));
            String description = request.getParameter("description");

            String second_address = request.getParameter("secondMRTAddress");
            double second_latitude = Double.parseDouble(request.getParameter("secondMRTLat"));
            double second_longitude = Double.parseDouble(request.getParameter("secondMRTLng"));

            TrainBreakDownCrisis crisis = new TrainBreakDownCrisis(type, address, latitude, longitude, description,second_address,second_longitude,second_latitude);
            crisis.setCrisisID(id);
            dao.create(crisis);
        } catch (Exception ex) {
            throw ex;
        }
        return id;
    }
    @Override
    public Crisis read(HttpServletRequest request) {
        Crisis crisis  = super.read(request);
        int crisisID = Integer.parseInt(request.getParameter("crisisID"));
        crisis = dao.getCrisisById(crisisID,crisis);
        return crisis;
    }
    /*
    @Override
    protected TrainBreakDownCrisis createCrisisFromRequest(HttpServletRequest request) {
        
        String type = request.getParameter("crisisType");
        String address = request.getParameter("address");
        double latitude = Double.parseDouble(request.getParameter("latitude"));
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        String description = request.getParameter("description");
		
        String second_address = request.getParameter("secondMRTAddress");
        double second_latitude = Double.parseDouble(request.getParameter("secondMRTLat"));
	double second_longitude = Double.parseDouble(request.getParameter("secondMRTLng"));
	
        return new TrainBreakDownCrisis(type, address, latitude, longitude, description,second_address,second_longitude,second_latitude);
    }*/
}
