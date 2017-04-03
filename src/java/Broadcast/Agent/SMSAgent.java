package Broadcast.Agent;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import facebook4j.internal.org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Broadcast.util.GroupPostable;
import Broadcast.util.IndividualPostable;
import java.util.HashMap;
import java.util.Map;

public class SMSAgent implements GroupPostable, IndividualPostable {

    final private static String FCM_URL = "https://fcm.googleapis.com/fcm/send";
    final private static String AUTHORIZATION_KEY = "AAAADG3DRnE:APA91bHa0pj4VagEU0QEa-iGO9H03E6DCNKGpUS1lvO9GNJq03BG0g4dUvuH7GffYzlzioQeZQzIY79COGnSIrNbr_8pQEE7WRE9QiPtsEFt7_TY1J9pEvR1wYVWp423JZ2QNPjiw5CS";
    final private String token = "dbeudyBiG50:APA91bFZrnFMCtphEWAbpFVD3Ni9dTZBwc-MMA6PaGwGmS8l2eHTWJuFJBUsyLQG4W_hOE9u1ouKviZatkxES1cCMJvBNwXBCrGzy6UuPuLAg8MhhTEl9ui_c73Bbe_oVQ_OA5PPBwSs";
    final private static String CONTACTSFILE = "../etc/contacts.csv";//"src/Broadcast/etc/contacts.csv";

    public ArrayList<Integer> readContactList() throws Exception {
        BufferedReader br = null;
        try {
            ArrayList<Integer> recipents = new ArrayList();
            File f = new File(CONTACTSFILE);
            System.out.println(f.getAbsoluteFile());
            FileReader fr = new FileReader(f);
            br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String lineArr[] = line.split(",");
                int recipentNum = Integer.parseInt(lineArr[1].trim());
                recipents.add(recipentNum);
            }
            return recipents;
        } catch (FileNotFoundException ex) {
            System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
            System.out.println("File not found");
            throw new Exception("SMS contact file missing");
        } catch (NumberFormatException nfex) {
            throw new Exception("Erronous input in SMS contact File");
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(SMSAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void post(Object messageObj) throws Exception {

        
        ArrayList<Integer> contactList = readContactList();
        for (int recipent : contactList) {
            post(messageObj, Integer.toString(recipent));
        }
    }

    @Override
    public void post(Object messageObj, String recipent) throws Exception {
                
        Map <String,String[]> pMap = (HashMap)messageObj;
        String message = pMap.get("message")[0];
        
        // codes to sent to android
        URL url;
        HttpURLConnection urlConnection = null;
        String resp = null;
        try {

            url = new URL(FCM_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "key=" + AUTHORIZATION_KEY);

            JSONObject inputData = new JSONObject();

            inputData.put("phoneNumber", recipent);
            inputData.put("Msg", message);

            JSONObject jo = new JSONObject();
            jo.put("to", token);
            jo.put("data", inputData);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            String parameters = jo.toString();
            wr.write(parameters.getBytes());

            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);
            int data = isw.read();
            StringBuilder sb = new StringBuilder();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                sb.append(current);
            }
            resp = sb.toString();

            System.out.println(resp);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
