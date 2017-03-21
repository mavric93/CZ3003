/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Broadcast.Agent.FacebookAgent;
import Broadcast.Agent.SMSAgent;
import Broadcast.Agent.TwitterAgent;

/**
 *
 * @author zhijie
 */
public class BroadCastManager {

    public static void main(String[] args) {
        FacebookAgent facebook = new FacebookAgent();
        TwitterAgent twitter = new TwitterAgent();
        SMSAgent sms = new SMSAgent();
        
        String statusMessage = "Hello Mavric!!!!";
        
        //facebook.updateStatus(statusMessage);//TODO Facebook Need to get new access token     
        //twitter.updateStatus(statusMessage);
        //sms.send("", statusMessage);
        //sms.send("90094124", statusMessage);
    }

}
