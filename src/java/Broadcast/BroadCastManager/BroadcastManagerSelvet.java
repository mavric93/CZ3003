/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broadcast.BroadCastManager;

import Broadcast.util.SocialMediaFactory;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Broadcast.util.GroupPostable;

/**
 *
 * @author zhijie
 */
public class BroadcastManagerSelvet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

    }
/**This method handles HTTP post recieved by the web service
 *  * 
 * @param req
 *      request should contain fields such as agent and message
 *      for emailing subject should be included
 *      
 * @param res
 * @throws ServletException
 *      occurs hwhen
 * @throws IOException 
 */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        //Receive true to send, receive false to not send
        //broadcast?agent=facebook&
        String agent = req.getParameter("agent");
        //String message = req.getParameter("message");
        
        //req.getParameterMap().get("agent");

        try {
            //get instance from factory
            //GroupPostable socialMedia = SocialMediaFactory.getInstance(agent);
            //Get instance from factory using reflection
            GroupPostable socialMedia = SocialMediaFactory.getAgentInstance(agent);
            
            //polymorphic ability to send message
            socialMedia.post(req.getParameterMap());

        } catch (Exception ex) {
            System.out.println("Expection");
            Logger.getLogger(BroadcastManagerSelvet.class.getName(), "Exception:" + ex);
            //return error page
        }

    }
}
