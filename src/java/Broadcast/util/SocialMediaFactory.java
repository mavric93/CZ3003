/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broadcast.util;

import Broadcast.Agent.EmailAgent;
import Broadcast.Agent.TwitterAgent;
import Broadcast.Agent.FacebookAgent;
import Broadcast.Agent.SMSAgent;

/**
 *
 * @author zhijie
 */
public class SocialMediaFactory {

    public static GroupPostable getInstance(String socialMediaAgent) throws Exception {

        if (socialMediaAgent == null) {
            throw new Exception("Agent parameter should not be null");
        }

        socialMediaAgent = socialMediaAgent.toLowerCase();

        switch (socialMediaAgent) {
            case "facebook":
                return new FacebookAgent();
            case "twitter":
                return new TwitterAgent();
            case "sms":
                return new SMSAgent();
            case "email":
                return new EmailAgent();
            default:
                throw new Exception("Unsupported social media agent");      //reflection
            }

    }
    /**
     * Get Broadcast Agent instance based on class name
     * @param agent 
     * Exact string of the broadcast agent class. 
     * @return GroupPostable 
     * @throws Exception 
     */
    public static GroupPostable getAgentInstance(String agent) throws Exception{
        String agentString = "Broadcast.Agent."+agent;
        System.out.println("Received agentString" + agentString);
        
        Class<?> agentClass = Class.forName(agentString);        
        return getAgentClass(agentClass);
        
    }
    
    public static GroupPostable getAgentClass(Class agentClass) throws Exception {
        return (GroupPostable) agentClass.newInstance();
    }
}
