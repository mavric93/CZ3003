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

    public boolean update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Crisis read(int crisisID) {
        Crisis crisis  = dao.getCrisisById(crisisID);
        return crisis;
    }

    public List<Crisis> list(HttpServletRequest request) {
        //is there a need to get different types of crisis?
        //need to add where clause
        String where = "";
        ArrayList<Crisis> crisisList = new ArrayList<Crisis>();
        
        try{
            //instanceOf is the specialized version
            crisisList = dao.getAllCrisis();
            
            //check instanceOf then get data from specialized table
            for(int i=0;i<crisisList.size();i++){
                
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return crisisList;
    }
    
    protected Crisis createCrisisFromRequest(HttpServletRequest request) throws Exception {
        try {
            //CrisisID, CType, Description, Address, Lat, Lng, Status, TimeReported, TimeResolved
            String type = request.getParameter("crisistype");
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
