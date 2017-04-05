package core.util;

import core.controller.ReportController;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReportGenerator {

    public static void main(String[] args) throws JRException, FileNotFoundException {
        ReportController con = new ReportController();
        JSONArray reportContent = con.listOngoing();
        System.out.println(reportContent.toString());

        for (int i = 0; i < reportContent.length(); i++) {
            JSONObject crisisJSON = reportContent.getJSONObject(i);

            String typeOfCrisis = crisisJSON.getString("crisisType");
            String crisisID = crisisJSON.getInt("crisisID") + "";
            ReportGenerator.generateReport(crisisID, typeOfCrisis, crisisJSON.toString());
        }
    }

    public static String generateReport(String crisisID, String crisisType, String jsonString) {

        try {
            String filePath = Thread.currentThread().getContextClassLoader().toString();
            System.out.println(filePath);
            System.out.println(System.getProperty("user.dir"));
            // Compile jrxml file.
            JasperReport jasperReport = JasperCompileManager.compileReport("./resource/" + crisisType + ".jrxml");

            // Parameters for report
            Map<String, Object> parameters = new HashMap<String, Object>();

            //have to do this in order to link the ".json" file
            InputStream iostream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));

            //have to do this in order to link the ".json" file
            parameters.put(JsonQueryExecuterFactory.JSON_INPUT_STREAM, iostream);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);

            // Make sure the output directory exists.
            File outDir = new File("jasperoutput");
            outDir.mkdirs();

            // Export to PDF.
            String outputPath = "jasperoutput/Crisis_" + crisisID + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

            System.out.println("Done!");
            return outputPath;
        } catch (JRException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
