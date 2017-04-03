/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONObject;

/**
 *
 * @author mavric
 */
public class CrisisUpdate {
    private final String pattern = "yyyy-MM-dd HH:mm:ss";
    private int crisisUpdateID;
    private int crisisID;
    private String update;
    private Calendar timeUpdated;

    public CrisisUpdate(int crisisID, String update) {
        this.crisisID = crisisID;
        this.update = update;
        this.timeUpdated = Calendar.getInstance();
    }

    public CrisisUpdate() {
        
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
    public void setTimeupdated(String timeupdated){
        try {
            Date timeOccured1 = new SimpleDateFormat(pattern).parse(timeupdated);
            this.timeUpdated = Calendar.getInstance();
            this.timeUpdated.setTime(timeOccured1);
        } catch (ParseException ex) {
            
        }
    }
    public String getTimeResolvedtoString() {
        if (timeUpdated == null) {
            return "null";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(timeUpdated.getTime());
    }

    public JSONObject toJSON() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("crisisUpdateID", this.getCrisisUpdateID());
        jsonObj.put("crisisID", this.getCrisisID());
        jsonObj.put("timeUpdated", this.getTimeResolvedtoString());
        jsonObj.put("update", this.getUpdate());
        return jsonObj;
    }
    
    
    
}
