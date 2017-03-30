/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broadcast.Agent;

import Broadcast.util.Postable;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBuilder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zhijie
 */
public class FacebookAgent implements Postable {

    static String OAUTH_FACEBOOK_APP_ID = "428690590809586";
    static String OAUTH_FACEBOOK_APP_SECRET = "a27efed8b5d57ea1e671792a477d7d30";
    //cannot be final
    static String OAUTH_ACCESS_TOKEN = "EAAGF5E5iPfIBADV6oIpbF7fzpUZBSBDLZBo1bY3qzTPBxdUSkdATsWsTZCZAhWTUVdY8XhzVS852Rh5IjmdZCtKg8kJleCRSf3cQMlUUMQnJpTA3O4ocxZB8DMBZAAdc1nZAIuEVHBTLLhoOW0m82x9t";
    static String OAUTH_PERMISSIONS = "email,publish_stream,manage_pages";
    static String PAGE_ACCESS_TOKEN = "EAACEdEose0cBAAutDFBHiwxhfmGoFXObs7xZBuyeNjTUw3laXAfdvnOsweEGyWV0fAFvmlOkHsajBfS3aQraQJt2M6WOcnbepsTZAX2dSxOENeaoZCCIwpx1zZAbQ7OMKzysXm0smRusSJjbci75QTyGEby0mDgs4j7OVFzTGEb8LdnNBv48I4Q8DboOFfIZD";

    @Override
    public void post(String statusMessage) {

        //initFacebook();
        //TODO Need to get new page_access_token
        //check if session is still valid
        //retrieve token
      //  reviveToken();
        
               
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(OAUTH_FACEBOOK_APP_ID, OAUTH_FACEBOOK_APP_SECRET);
        facebook.setOAuthPermissions(OAUTH_PERMISSIONS);
        facebook.setOAuthAccessToken(new AccessToken(PAGE_ACCESS_TOKEN, null));

        try {
            facebook.postStatusMessage(statusMessage);
        } catch (FacebookException ex) {
            Logger.getLogger(FacebookAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
