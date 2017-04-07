/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.controller;

import javax.servlet.http.HttpServletRequest;

import core.DAO.CrisisUpdateDAO;
import java.util.ArrayList;
import core.model.CrisisUpdate;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author mavric
 */
public class CrisisUpdateController extends HttpServlet {

    private CrisisUpdateDAO dao;

    public CrisisUpdateController() {
        super();
        dao = new CrisisUpdateDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String action = request.getParameter("action");
            switch (action) {
                case "add":
                    JSONObject jo = new JSONObject();
                    jo.put("success",create(request));
                    response.getWriter().println(jo.toString());
                    break;
                case "list":
                    String jsonObj = list(request);
                    response.getWriter().write(jsonObj);
                    break;
            }

    }

    public boolean create(HttpServletRequest request) {
        boolean success = false;
        try {
            int crisisID = Integer.parseInt(request.getParameter("crisisID"));
            String update = request.getParameter("update");

            CrisisUpdate newCrisisUpdate = new CrisisUpdate(crisisID, update);
            int id= dao.addCrisisUpdate(newCrisisUpdate);
            if(id>-1){
                success = true;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    public String list(HttpServletRequest request) {
        ArrayList<CrisisUpdate> crisisUpdateList = new ArrayList();
        JSONArray jsonArr = new JSONArray();
        try {
            crisisUpdateList = dao.getAllCrisisUpdate(Integer.parseInt(request.getParameter("crisisID")));

            JSONObject jsonObj = new JSONObject();
            for (int i = 0; i < crisisUpdateList.size(); i++) {
                CrisisUpdate crisisupdate = crisisUpdateList.get(i);
                jsonArr.put(crisisupdate.toJSON());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonArr.toString();
    }
}
