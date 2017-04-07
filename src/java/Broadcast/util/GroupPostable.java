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
public interface GroupPostable {
    public abstract void sendToGroup(String messageContent, String recipientGroup) throws Exception;
}
