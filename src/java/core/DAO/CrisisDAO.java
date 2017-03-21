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

/**
 *
 * @author mavric
 */
public class CrisisDAO {

    private Connection connection;

    public CrisisDAO() {
        connection = DbUtil.getConnection();
    }

    public int addCrisis(Crisis crisis) {
        int id = -1;
        try {
            SimpleDateFormat datetimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO `ssad`.`crisis` "
                            + "(`CType`, `Description`, `Address`, `Lat`, `Lng`, `Status`, `TimeReported`) "
                            + "VALUES ( ?, ?, ?, ?, ?, ?, ?);");
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

    public void updateCrisis(Crisis crisis) {
        /*try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update crisis set crisisname=? where crisisid =?");
            // Parameters start with 1
            preparedStatement.setString(1, crisis.getCrisisName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    public ArrayList<Crisis> getAllCrisis() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ArrayList<Crisis> crisisList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM ssad.crisis join ssad.crisistype "
                    + "on ssad.crisis.CType = ssad.crisistype.CType;");
            while (rs.next()) {
                //CrisisID, CType, Description, Address, Lat, Lng, Status, TimeReported, TimeResolved
                
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

    public Crisis getCrisisById(int crisisID) {
        Crisis crisis = new Crisis();
        /*try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from crisis where crisisid=?");
            preparedStatement.setInt(1, crisisID);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                crisis.setCrisisID(rs.getInt("crisisID"));
                crisis.setCrisisName(rs.getString("crisisName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        return crisis;
    }

}
