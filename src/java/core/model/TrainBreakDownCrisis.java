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
public class TrainBreakDownCrisis extends Crisis {
    private String secondMRTAddress;
    private double secondMRTLat;
    private double secondMRTLng;
	
    public TrainBreakDownCrisis(String crisisType, String address, double latitude, double longitude, String description,String secondMRTAddress,double secondMRTLng,double secondMRTLat,int mobileNumber) {
        super(crisisType, address, latitude, longitude, description,mobileNumber);
        this.secondMRTAddress = secondMRTAddress;
        this.secondMRTLat = secondMRTLat;
		this.secondMRTLng = secondMRTLng;
    }
	
    public TrainBreakDownCrisis(){
    
    }
    
	public String getSecondMRTAddress() {
		return secondMRTAddress;
	}
	public void setSecondMRTAddress(String secondMRTAddress) {
		this.secondMRTAddress = secondMRTAddress;
	}
	public double getSecondMRTLat() {
		return secondMRTLat;
	}
	public void setSecondMRTLat(double secondMRTLat) {
		this.secondMRTLat = secondMRTLat;
	}
	public double getSecondMRTLng() {
		return secondMRTLng;
	}
	public void setSecondMRTLng(double secondMRTLng) {
		this.secondMRTLng = secondMRTLng;
	}
	
	
    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put("secondMRTAddress", secondMRTAddress);
        json.put("secondMRTLat", secondMRTLat);
		json.put("secondMRTLng", secondMRTLng);
        return json;
    }

}
