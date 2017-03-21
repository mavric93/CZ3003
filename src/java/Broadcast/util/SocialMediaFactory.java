/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broadcast.util;

import Broadcast.Agent.TwitterAgent;
import Broadcast.Agent.FacebookAgent;
import Broadcast.Agent.SMSAgent;
import Broadcast.Agent.EmailAgent;

/**
 *
 * @author zhijie
 */
public class SocialMediaFactory {

    public static Postable getInstance(String socialMediaAgent) throws Exception {

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
}
