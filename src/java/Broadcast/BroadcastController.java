/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broadcast;

import Broadcast.util.Broadcastable;
import Broadcast.util.GroupPostable;
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String agent = request.getParameter("agent");
            String message = request.getParameter("message");
            String recipent = request.getParameter("recipient");
            System.out.println(recipent+"@@@@@@@");
            if (recipent.isEmpty()) {
                Broadcastable socialMedia = SocialMediaFactory.getBroadcastAgentInstance(agent);
                socialMedia.broadcast(message);
                response.getWriter().print(message + " has been broadcasted to " + agent);
            } else {
                GroupPostable targetedRecipents = SocialMediaFactory.getIndividualAgentInstance(agent);
                targetedRecipents.sendToGroup(message, recipent);
            }
            //response.getWriter().write(message + " has been posted to " + agent);
        } catch (Exception ex) {
            //return error page
            System.out.println(request.getContextPath());
            ex.printStackTrace();
            response.getWriter().write(ex.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // action type == broadcast
        try {
            String agent = request.getParameter("agent");
            String message = request.getParameter("message");
            String recipent = request.getParameter("recipient");
            if (recipent == null) {
                Broadcastable socialMedia = SocialMediaFactory.getBroadcastAgentInstance(agent);
                socialMedia.broadcast(message);
                response.getWriter().print(message + " has been broadcasted to " + agent);
            } else {
                GroupPostable targetedRecipents = SocialMediaFactory.getIndividualAgentInstance(agent);
                targetedRecipents.sendToGroup(message, recipent);
            }
            //response.getWriter().write(message + " has been posted to " + agent);
        } catch (Exception ex) {
            //return error page
            System.out.println(request.getContextPath());
            ex.printStackTrace();
            response.getWriter().write(ex.getMessage());
        }
    }
}
