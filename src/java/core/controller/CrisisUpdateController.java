/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.controller;

import javax.servlet.http.HttpServletRequest;

import core.DAO.CrisisUpdateDAO;
import core.DAO.TerrorismCrisisDAO;
import core.DAO.TrainBreakDownCrisisDAO;
import java.util.ArrayList;
import java.util.List;
import core.model.Crisis;
import core.model.CrisisUpdate;
import core.model.TerrorismCrisis;
import core.model.TrainBreakDownCrisis;

/**
 *
 * @author mavric
 */
public class CrisisUpdateController {
    private CrisisUpdateDAO dao;
    public CrisisUpdateController() {
        super();
        dao = new CrisisUpdateDAO();
    }
    public int create(HttpServletRequest request) throws Exception {
        int id = -1;
        try {
            int crisisID = Integer.parseInt(request.getParameter("crisisID"));
            String update = request.getParameter("update");
            
            CrisisUpdate newCrisisUpdate =  new CrisisUpdate(crisisID,update);
            id = dao.addCrisisUpdate(newCrisisUpdate);
        } catch (Exception ex) {
            throw ex;
        }
        return id;
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
}
