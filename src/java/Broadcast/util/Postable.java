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
public interface Postable {

    //public abstract void init(String propertiesFile);

    public abstract void post(String message) throws Exception;
}
