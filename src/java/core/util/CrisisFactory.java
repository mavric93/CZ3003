/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.util;

import core.controller.CrisisController;
import core.controller.TerrorismCrisisController;
import core.model.Crisis;
import core.model.TerrorismCrisis;

/**
 *
 * @author mavric
 */
public class CrisisFactory {
    public static CrisisController createController(String type){
        CrisisController controller = null;
        switch (type) {
            case "Terrorism":
                controller = new TerrorismCrisisController();
                break;
            case "TrainBreakdown":
                //controller = new MRTBreakdownController();
                break;
            default:
                controller = new CrisisController();
        }
        return controller;
    }
    public Crisis createCrisis(String type){
        Crisis c = null;
        switch (type) {
            case "Terrorism":
                c = new TerrorismCrisis();
                
                //initialise c;
                
                break;
            case "TrainBreakdown":
                //controller = new MRTBreakdownController();
                break;
            default:
                c = new Crisis();
        }
        return c;
    }
}
