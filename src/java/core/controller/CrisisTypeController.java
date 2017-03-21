/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.DAO.CrisisTypeDAO;
import core.model.CrisisType;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author mavric
 */
public class CrisisTypeController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String LIST_USER = "/listCrisis.jsp";
    private CrisisTypeDAO dao;

    public CrisisTypeController() {
        super();
        dao = new CrisisTypeDAO();
    }

    //GET for Read and List
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("list")) {
            JSONObject json = new JSONObject();
            JSONArray ja = new JSONArray();
            for (CrisisType c : dao.getAllCrisisType()) {
                ja.put(c.toJSON());
            }
            json.put("data", ja);
            response.getWriter().println(json.toString());
        }
    }
}
