/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import core.model.Crisis;
import core.model.TerrorismCrisis;
import core.util.CrisisFactory;
import core.util.DbUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mavric
 */
public class CrisisDAO {

    //FUNCTIONS: CREATE,UPDATE,READ,LIST

    private Connection connection;

    public CrisisDAO() {
        connection = DbUtil.getConnection();
    }

    //DONE

    public int addCrisis(Crisis crisis) {
        int id = -1;
        try {
            SimpleDateFormat datetimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO `ssad`.`crisis` "
                            + "(`CType`, `Description`, `Address`, `Lat`, `Lng`, `Status`, `TimeReported`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?);");
            // Parameters start with 1
            preparedStatement.setString(1, crisis.getCrisisType());
            preparedStatement.setString(2, crisis.getDescription());
            preparedStatement.setString(3, crisis.getAddress());
            preparedStatement.setDouble(4, crisis.getLatitude());
            preparedStatement.setDouble(5, crisis.getLongitude());
            preparedStatement.setString(6, crisis.getStatus());
            preparedStatement.setString(7, datetimeformat.format(crisis.getTimeOccured().getTime()));
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement2 = connection.
                    prepareStatement("SELECT CrisisID FROM ssad.crisis order by CrisisID desc LIMIT 1;");
            ResultSet rs = preparedStatement2.executeQuery();
            if (rs.next()) {
                id = rs.getInt("CrisisID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    //NOT SURE NEEDED OR NOT

    public void deleteCrisis(String crisisID) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from crisis where crisisid=?");
            // Parameters start with 1
            preparedStatement.setString(1, crisisID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //DONE

    public boolean updateCrisis(Crisis crisis) {
        SimpleDateFormat datetimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sqlStatement_resolved = "update ssad.crisis set Description=?,Status=?,TimeResolved=NOW() where crisisid =?";
        String sqlStatement_not_resolved = "update ssad.crisis set Description=?,Status=? where crisisid =?";
        boolean success = false;
        try {
            //CrisisID, CType, Description, Address, Lat, Lng, Status, TimeReported, TimeResolved
            PreparedStatement preparedStatement = null;
            if (crisis.getStatus().equals("Resolved")) {
                preparedStatement = connection.prepareStatement(sqlStatement_resolved);
            } else {
                preparedStatement = connection.prepareStatement(sqlStatement_not_resolved);
            }
            // Parameters start with 1
            preparedStatement.setString(1, crisis.getDescription());
            preparedStatement.setString(2, crisis.getStatus());
            preparedStatement.setInt(3, crisis.getCrisisID());
            if (preparedStatement.executeUpdate() > -1) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    //DONE

    public ArrayList<Crisis> getAllCrisis() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ArrayList<Crisis> crisisList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM ssad.crisis join ssad.crisistype "
                    + "on ssad.crisis.CType = ssad.crisistype.CType;");
            while (rs.next()) {
                //CrisisID, CType, Description, Address, Lat, Lng, Status, TimeReported, TimeResolved
                //initialise general fields
                //Crisis crisis = new Crisis();
                CrisisFactory fact = new CrisisFactory();
                Crisis crisis = fact.createCrisis(rs.getString("CType"));
                crisis.setCrisisID(rs.getInt("CrisisID"));
                crisis.setCrisisType(rs.getString("CType"));
                crisis.setAddress(rs.getString("Address"));
                crisis.setLatitude(rs.getDouble("Lat"));
                crisis.setLongitude(rs.getDouble("Lng"));
                crisis.setStatus(rs.getString("Status"));
                crisis.setTimeReported(rs.getString("TimeReported"));
                crisis.setTimeResolved(rs.getString("TimeResolved"));
                crisis.setDescription(rs.getString("Description"));
                crisis.setIcon(rs.getString("Icon"));
                crisisList.add(crisis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return crisisList;
    }

    //DONE
    public Crisis getCrisisById(int crisisID) {
        Crisis crisis = new Crisis();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM ssad.crisis join ssad.crisistype "
                            + "on ssad.crisis.CType = ssad.crisistype.CType WHERE CrisisID=?;");
            preparedStatement.setInt(1, crisisID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                //create specialized class
                CrisisFactory fact = new CrisisFactory();
                crisis = fact.createCrisis(rs.getString("CType"));
                //retrieve data
                crisis.setCrisisID(rs.getInt("CrisisID"));
                crisis.setCrisisType(rs.getString("CType"));
                crisis.setAddress(rs.getString("Address"));
                crisis.setLatitude(rs.getDouble("Lat"));
                crisis.setLongitude(rs.getDouble("Lng"));
                crisis.setStatus(rs.getString("Status"));
                crisis.setTimeReported(rs.getString("TimeReported"));
                crisis.setTimeResolved(rs.getString("TimeResolved"));
                crisis.setDescription(rs.getString("Description"));
                crisis.setIcon(rs.getString("Icon"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CrisisDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return crisis;
    }
	//to check if any crisis is in comfirmed status
	public boolean checkComfirmedCrisis() {
		boolean crisis = false;
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT CrisisID FROM ssad.crisis  WHERE Status=? LIMIT 1");
            preparedStatement.setString(1, "Comfirmed");
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
				crisis = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CrisisDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return crisis;

    }
}
