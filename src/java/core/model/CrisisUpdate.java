/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import java.util.Calendar;

/**
 *
 * @author mavric
 */
public class CrisisUpdate {
    private int crisisUpdateID;
    private int crisisID;
    private String update;
    private Calendar timeUpdated;

    public CrisisUpdate(int crisisUpdateID, String update) {
        this.crisisUpdateID = crisisUpdateID;
        this.update = update;
        this.timeUpdated = Calendar.getInstance();
    }

    public int getCrisisUpdateID() {
        return crisisUpdateID;
    }

    public void setCrisisUpdateID(int crisisUpdateID) {
        this.crisisUpdateID = crisisUpdateID;
    }

    public int getCrisisID() {
        return crisisID;
    }

    public void setCrisisID(int crisisID) {
        this.crisisID = crisisID;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public Calendar getTimeupdated() {
        return timeUpdated;
    }

    public void setTimeupdated(Calendar timeupdated) {
        this.timeUpdated = timeupdated;
    }
    
    
    
}
