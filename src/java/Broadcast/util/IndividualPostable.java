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
public interface IndividualPostable {

    public abstract void post(Object messageObj, String recipient) throws Exception;
}
