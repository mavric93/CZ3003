/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.util;

import core.controller.CrisisController;
import core.controller.TerrorismCrisisController;
import core.controller.TrainBreakDownCrisisController;
import core.model.Crisis;
import core.model.TerrorismCrisis;
import core.model.TrainBreakDownCrisis;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mavric
 */
public class CrisisFactory {
    public static CrisisController createController(String type) {
        CrisisController controller = null;
        
        String controllerString = "core.controller."+type+"CrisisController";
        Class<?> controllerClass = null;

        try {
            controllerClass = Class.forName(controllerString);
            return (CrisisController) controllerClass.newInstance();
        } catch (Exception ex) {
            return new CrisisController();
        }
        
            /*
            type = type.toUpperCase();
            switch (type) {
            case "TERRORISM":
            controller = new TerrorismCrisisController();
            break;
            case "TRAINBREAKDOWN":
            controller = new TrainBreakDownCrisisController();
            break;
            default:
            controller = new CrisisController();
            }*/
    }
    public Crisis createCrisis(String type){
        Crisis c = null;
        CrisisController controller = null;
        
        String modelString = "core.model."+type+"Crisis";
        Class<?> modelClass = null;

        try {
            modelClass = Class.forName(modelString);
            return (Crisis) modelClass.newInstance();
        } catch (Exception ex) {
            return new Crisis();
        }
        /*
        switch (type) {
            case "TERRORISM":
                c = new TerrorismCrisis();
                break;
            case "TRAINBREAKDOWN":
                c = new TrainBreakDownCrisis();
                break;
            default:
                c = new Crisis();
        }
        return c;*/
    }
}
