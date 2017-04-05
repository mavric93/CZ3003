/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broadcast.Agent;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import Broadcast.util.GroupPostable;
import facebook4j.FacebookException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zhijie
 */
public class FacebookAgent implements GroupPostable {

    static String OAUTH_FACEBOOK_APP_ID = "428690590809586";
    static String OAUTH_FACEBOOK_APP_SECRET = "a27efed8b5d57ea1e671792a477d7d30";
    static String OAUTH_ACCESS_TOKEN = "EAAGF5E5iPfIBAHNSEvEbDkySZAnXX2cCofaz56k4IBjQT1UwShZBXPQdAPK1mNxocl81ZABgMtY6ap0Sm8EjOAnzcAQ6uccCI12nZCsTeYqq8zGb6rMLSnrBwzmpsYx69YMkfwE9yzQj0oicTcGmFnvkNI7NLz8ZBZCZAZCvERiuLFQBrNoxSf24";
    static String PAGE_ACCESS_TOKEN = "EAAGF5E5iPfIBALXGrayg3tYlfeyDclw1yAG7fy3zOxF7kJZBwCTetB9u5TiUwR2iZAO0XtPNjtZBIlvbNzaoGOE9QWmsWtmGsDaKnPpIt0GpYf9iQ6TQkvXnm3ZBkYlZA56mv9KGJNxCkPAF76QK1J9Q8BkxvAg0ZD";

    @Override
    public void post(Object messageObj) {
        
        Map<String,String[]> parametersMap = (HashMap)messageObj;
        
        String message = parametersMap.get("message")[0];     

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(OAUTH_FACEBOOK_APP_ID, OAUTH_FACEBOOK_APP_SECRET);
        facebook.setOAuthAccessToken(new AccessToken(PAGE_ACCESS_TOKEN, null)); //Set page access token to update page

        try {
            facebook.postStatusMessage(message);
            
        } catch (FacebookException ex) {
            Logger.getLogger(FacebookAgent.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("Facebook Status post Completed!");
        }
    }
    @Override
    public void post(Object messageObj, String recipent) throws Exception {
        
    }
}
