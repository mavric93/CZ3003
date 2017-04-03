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
public class TerrorismCrisis extends Crisis {
    private String typeOfAttack;
    private int radius;
    public TerrorismCrisis(){
    
    }
    public TerrorismCrisis(String typeOfAttack, int radius, String crisisType, String address, double latitude, double longitude, String description,int mobileNumber) {
        super(crisisType, address, latitude, longitude, description,mobileNumber);
        this.typeOfAttack = typeOfAttack;
        this.radius = radius;
    }

    public String getTypeOfAttack() {
        return typeOfAttack;
    }

    public void setTypeOfAttack(String typeOfAttack) {
        this.typeOfAttack = typeOfAttack;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put("typeOfAttack", typeOfAttack);
        json.put("radius", radius);
        return json;
    }

}
