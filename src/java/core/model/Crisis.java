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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class Crisis {
    private final String pattern = "yyyy-MM-dd HH:mm:ss";
    private int crisisID;
    private String crisisType;
    private String description;
    private String address;
    private double latitude;
    private double longitude;
    private String status;
    private Calendar timeReported;
    private Calendar timeResolved;
    private String icon;
    

    public Crisis() {
        
    }

    public Crisis(String crisisType, String address, double latitude, double longitude, String description) {
        this.crisisType = crisisType;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = "Pending";
        this.timeReported = Calendar.getInstance();
        this.timeResolved = null;
        this.description = description;
        this.icon = "";
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Calendar getTimeOccured() {
        return timeReported;
    }

    public String getTimeReportedtoString() {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String formated = format.format(timeReported.getTime());
        return formated;
    }

    public void setTimeReported(Calendar timeOccured) {
        this.timeReported = timeOccured;
    }

    public void setTimeReported(String datetime) {
        try {
            Date timeOccured1 = new SimpleDateFormat(pattern).parse(datetime);
            timeReported = Calendar.getInstance();
            this.timeReported.setTime(timeOccured1);
        } catch (ParseException ex) {
            Logger.getLogger(Crisis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Calendar getTimeResolved() {
        return timeResolved;
    }

    public String getTimeResolvedtoString() {
        if (timeResolved == null) {
            return "null";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(timeResolved.getTime());
    }

    public void setTimeResolved(Calendar timeResolved) {
        this.timeResolved = timeResolved;
    }

    public void setTimeResolved(String datetime) {
        try {
            if (datetime == null) {
                timeResolved = null;
                return;
            }
            Date timeResolved = new SimpleDateFormat(pattern).parse(datetime);
            this.timeResolved = Calendar.getInstance();
            this.timeResolved.setTime(timeResolved);
        } catch (ParseException ex) {
            Logger.getLogger(Crisis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCrisisType() {
        return crisisType;
    }

    public void setCrisisType(String crisisType) {
        this.crisisType = crisisType;
    }
    
    public int getCrisisID() {
        return crisisID;
    }

    public void setCrisisID(int crisisID) {
        this.crisisID = crisisID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JSONObject toJSON() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject json = new JSONObject();
        json.put("crisisID", crisisID);
        json.put("crisistype", crisisType);
        json.put("address", address);
        json.put("latitude", latitude);
        json.put("longitude", longitude);
        json.put("status", status);
        json.put("timereported", getTimeReportedtoString());
        json.put("timeresolved", getTimeResolvedtoString());
        json.put("description", description);
        json.put("icon", icon);
        return json;
    }

}
