/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.servlet;

import core.DAO.CrisisTypeDAO;
import core.controller.CrisisController;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import core.model.Crisis;
import org.json.JSONArray;
import core.util.CrisisFactory;
import org.json.JSONObject;

/**
 *
 * @author mavric
 */
public class CrisisServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String LIST_USER = "/listCrisis.jsp";
    private CrisisTypeDAO dao;

    public CrisisServlet() {
        super();
        dao = new CrisisTypeDAO();
    }
    
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve all information from post
        String type = "";
        type = request.getParameter("crisisType");
        String action = "";
        action = request.getParameter("action");
        CrisisController controller = CrisisFactory.createController(type);
        
        JSONObject output = new JSONObject();
        try {
            switch (action) {
                case "create":
                    int id = controller.create(request);
                    if(id!=-1){
			output.put("status", "success");
                    }else{
			output.put("status", "fail");
                    }
                    break;
                case "update":
                    if(controller.update(request)){
			output.put("status", "success");
                    }else{
			output.put("status", "fail");
                    }
                    break;
                case "read":
                    Crisis crisis = controller.read(request);
                    output.put("data",crisis.toJSON());
                    break;
                case "list":
                    List<Crisis> list = controller.list(request);
                    JSONArray ja = new JSONArray();
                    for(int i=0;i<list.size();i++){
                        ja.put(list.get(i).toJSON());
                    }
                    output = output.put("data",ja.toString());
                default:
            }
        } catch (Exception ex) {
            ex.printStackTrace(response.getWriter());
        }
        response.setContentType("application/json");
        response.getWriter().print(output.toString());
    }
    
    //GET for Read and List
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve all information from get
        String type = "";
        if(request.getParameter("crisistype")!=null){
            type = request.getParameter("crisistype");
        }
        
        String action = "";
        action = request.getParameter("action");
        CrisisController controller = CrisisFactory.createController(type);
        
        String output = "";
        try {
            switch (action) {
                case "create":
                    int id = controller.create(request);
                    if(id!=-1){
                        output = "success";
                    }else{
                        output = "fail";
                    }
                    break;
                case "update":
                    controller.update(request);
                    break;
                case "read":
                    Crisis crisis = controller.read(request);
                    output = crisis.toJSON().toString();
                    break;
                case "list":
                    List<Crisis> list = controller.list(request);
                    JSONArray ja = new JSONArray();
                    for(int i=0;i<list.size();i++){
                        ja.put(list.get(i).toJSON());
                    }
                    output = ja.toString();
                default:
            }
        } catch (Exception ex) {
            ex.printStackTrace(response.getWriter());
        }
        response.getWriter().write(output);
    }
}
