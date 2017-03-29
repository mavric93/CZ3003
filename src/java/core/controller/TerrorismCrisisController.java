/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.controller;

import javax.servlet.http.HttpServletRequest;

import core.DAO.TerrorismCrisisDAO;
import core.model.Crisis;
import core.model.TerrorismCrisis;

/**
 *
 * @author mavric
 */
public class TerrorismCrisisController extends CrisisController {
    private TerrorismCrisisDAO dao;
    public TerrorismCrisisController() {
        super();
        dao = new TerrorismCrisisDAO();
    }
    @Override
    public int create(HttpServletRequest request) throws Exception {
        int id =-1;
        try {
            id = super.create(request);
            int radius = Integer.parseInt(request.getParameter("radius"));
            String type = request.getParameter("crisistype");
            String address = request.getParameter("address");
            double latitude = Double.parseDouble(request.getParameter("latitude"));
            double longitude = Double.parseDouble(request.getParameter("longitude"));
            String description = request.getParameter("description");
            String typeOfAttack = request.getParameter("typeOfAttack");
            
            TerrorismCrisis crisis = new TerrorismCrisis(typeOfAttack, radius, type, address, latitude, longitude, description);
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
    
    /*@Override
    protected TerrorismCrisis createCrisisFromRequest(HttpServletRequest request) {
        int radius = Integer.parseInt(request.getParameter("radius"));
        String type = request.getParameter("crisistype");
        String address = request.getParameter("address");
        double latitude = Double.parseDouble(request.getParameter("latitude"));
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        String description = request.getParameter("description");
        String typeOfAttack = request.getParameter("typeOfAttack");

        return new TerrorismCrisis(typeOfAttack, radius, type, address, latitude, longitude, description);
    }*/
}
