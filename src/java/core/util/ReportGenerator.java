package core.util;

import core.controller.ReportController;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReportGenerator {

    public static void main(String[] args) throws JRException, FileNotFoundException {
        ReportController con = new ReportController();
        JSONArray reportContent = con.listOngoing();

        for (int i = 0; i < reportContent.length(); i++) {
            JSONObject jsonobj = reportContent.getJSONObject(i);
            JSONObject crisisJSON = jsonobj.getJSONObject("data");
            String typeOfCrisis = crisisJSON.getString("crisisType");
            String crisisID = crisisJSON.getInt("crisisID") + "";
            ReportGenerator.generateReport(crisisID, typeOfCrisis, jsonobj.toString());
        }
    }

    public static String generateReport(String crisisID, String crisisType, String jsonString) {

        try {

            // Compile jrxml file. ../etc/contacts.csv
            JasperReport jasperReport = JasperCompileManager.compileReport("./resource/" + crisisType + ".jrxml");

            Map<String, Object> parameters = new HashMap<String, Object>();

            System.out.println(jsonString);
            InputStream iostream = new ByteArrayInputStream(jsonString.getBytes());
            parameters.put(JsonQueryExecuterFactory.JSON_INPUT_STREAM, iostream);

            // Parameters for report
            //File jsonFile = new File("C:\\Users\\mavric\\GlassFish_Server\\glassfish\\domains\\SSAD\\config\\resource/1.json");
            //Map<String, Object> parameters = new HashMap();
            //parameters.put(JsonQueryExecuterFactory.JSON_INPUT_STREAM, new FileInputStream(jsonFile));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);

            // Make sure the output directory exists.
            File outDir = new File("C:\\Users\\mavric\\Documents\\NetBeansProjects\\SSAD\\web\\JasperReport");
            outDir.mkdirs();

            // Export to PDF.
            String outputPath = outDir.toString()+"/Crisis_" + crisisID + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

            return "C:\\Users\\mavric\\Documents\\NetBeansProjects\\SSAD\\web\\JasperReport\\"+"Crisis_" + crisisID + ".pdf";
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
