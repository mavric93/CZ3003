/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.servlet;

import core.DAO.CrisisDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author mavric
 */
public class CrisisStateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CrisisDAO dao;

    public CrisisStateServlet() {
        super();
        dao = new CrisisDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        boolean crisisState = dao.checkComfirmedCrisis();
		JSONObject jo = new JSONObject();
		jo.put("crisisState",crisisState);
        response.getWriter().write(jo.toString());
    }
}
