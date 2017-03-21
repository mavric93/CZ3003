/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import org.json.JSONObject;

/**
 *
 * @author mavric
 */
public class CrisisType {
    //CType, Icon
    private String crisisType;
    private String Icon;

    public String getCrisisType() {
        return crisisType;
    }

    public void setCrisisType(String crisisType) {
        this.crisisType = crisisType;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String Icon) {
        this.Icon = Icon;
    }
    
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("crisisType", crisisType);
        json.put("Icon", Icon);
        return json;
    }
}
