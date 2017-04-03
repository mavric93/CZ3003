/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broadcast.BroadCastManager;

import Broadcast.util.GroupPostable;
import Broadcast.util.SocialMediaFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author zhijie This class is for testing purposes only
 */
public class BroadCastManager {

    public static void main(String[] args) {
        String[] message = new String[1];
        message[0] = "Message Test after updating interfaces!!! ";

        String[] subject = new String[1];
        subject[0] = "CMS Crisis -- MRT Breakdown!";

        String agent[] = new String[4];
        agent[0] = "EmailAgent";
        agent[1] = "FacebookAgent";
        agent[2] = "SMSAgent";
        agent[3] = "TwitterAgent";

        //String agent = "EmailAgent";
        Map<String, String[]> messageMap = new HashMap();
        messageMap.put("message", message);
        messageMap.put("subject", subject);
        
        GroupPostable socialMedia = null;
        try {

            //get instance from factory
            for (String eachAgent : agent) {
                socialMedia = SocialMediaFactory.getAgentGroupInstance(eachAgent);
                socialMedia.post(messageMap);
            }
            //polymorphic ability to send message
            

        } catch (Exception ex) {
            System.out.println("Expection");
            Logger.getLogger(BroadcastManagerSelvet.class.getName(), "Exception:" + ex);
            //return error page
        }
    }

}
