/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broadcast.Agent;

import Broadcast.util.Postable;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author zhijie
 *
 * Twitter Account User Account: CMSBroadCast Password : cmsbroadcastpassword
 *
 * Can choose to use twitter4j.properties for account setup or use
 * configureTwitter function for init.
 */
public class TwitterAgent implements Postable{

    static String TWITTER_CONSUMER_KEY = "YJJesWSbVH1o83DbDUUN42sii";
    static String TWITTER_CONSUMER_SECRET = "zs6ZljjO7FK8fjDPzBhUFUClP98rRU5BXpXy0qXkqlHscaBubv";
    static String TWITTER_ACCESS_TOKEN = "842613433698672640-pzm6qjmcnavpXUt8UrDeNGl2UVKTrUt";
    static String TWITTER_ACCESS_TOKEN_SECRET = "W9ry2kJ3ua8aetuEZkvoQjT8b3MMLc2BQtNh9Av0VRRbo";

    ConfigurationBuilder cb;
    TwitterFactory tf;
    Twitter twitter;

    public void post(String statusMessage) {

        try {
            //twitter = TwitterFactory.getSingleton();            
            initTwitter();
            Status status = twitter.updateStatus(statusMessage);
            System.out.println("Status Message Updated");
        } catch (TwitterException ex) {
            Logger.getLogger(TwitterAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initTwitter() {
        // The factory instance is re-useable and thread safe.
        cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
                .setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET)
                .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);

        tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

    }

}
