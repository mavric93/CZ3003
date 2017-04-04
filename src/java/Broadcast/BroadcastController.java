/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broadcast;

import Broadcast.util.GroupPostable;
import Broadcast.util.IndividualPostable;
import Broadcast.util.SocialMediaFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhijie
 */
public class BroadcastController extends HttpServlet {

    public BroadcastController() {
        super();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String agent = request.getParameter("agent");
            String message = request.getParameter("message");
            String recipient = request.getParameter("recipient");

            GroupPostable socialMedia = SocialMediaFactory.getAgentGroupInstance(agent);
            socialMedia.post(request.getParameterMap(),recipient);

            //response.
            response.getWriter().print(message + " has been posted to " + agent);
            //response.getWriter().write(message + " has been posted to " + agent);
        } catch (Exception ex) {
            //return error page
            System.out.println(request.getContextPath());
            ex.printStackTrace();
            response.getWriter().write(ex.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // action type == broadcast
        try {
            String agent = request.getParameter("agent");
            String message = request.getParameter("message");

            GroupPostable socialMedia = SocialMediaFactory.getAgentGroupInstance(agent);
            socialMedia.post(request.getParameterMap());

            //response.
            response.getWriter().print(message + " has been posted to " + agent);
            //response.getWriter().write(message + " has been posted to " + agent);
        } catch (Exception ex) {
            //return error page
            System.out.println(request.getContextPath());
            ex.printStackTrace();
            response.getWriter().write(ex.getMessage());
        }

        //action type = contact&agency = POLICE&message =

    }
}
