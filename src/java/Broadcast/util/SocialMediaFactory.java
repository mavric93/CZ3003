/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broadcast.util;

/**
 *
 * @author zhijie
 */
public class SocialMediaFactory {

    /**
     * Get Broadcast Agent instance based on class name
     * @param agent 
     * Exact string of the broadcast agent class. 
     * @return GroupPostable 
     * @throws Exception 
     */
    public static Broadcastable getBroadcastAgentInstance(String agent) throws Exception{
        String agentString = "Broadcast.Agent."+agent;
        System.out.println("Received agentString" + agentString);
        
        Class<?> agentClass = Class.forName(agentString);        
        return getBroadcastAgent(agentClass);
        
    }
    
    public static GroupPostable getIndividualAgentInstance(String agent) throws Exception{
        String agentString = "Broadcast.Agent."+agent;
        System.out.println("Received agentString" + agentString);
        
        Class<?> agentClass = Class.forName(agentString);        
        return getIndividualAgent(agentClass);
        
    }
    
    private static Broadcastable getBroadcastAgent(Class agentClass) throws Exception {
        return (Broadcastable) agentClass.newInstance();
    }
    
    private static GroupPostable getIndividualAgent(Class agentClass) throws Exception {
        return (GroupPostable) agentClass.newInstance();
    }
}
