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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import core.model.CrisisUpdate;
import core.util.DbUtil;

/**
 *
 * @author mavric
 */
public class CrisisUpdateDAO {

    //FUNCTIONS: CREATE,UPDATE,READ,LIST
    private Connection connection;

    public CrisisUpdateDAO() {
        connection = DbUtil.getConnection();
    }

    //DONE
    public int addCrisisUpdate(CrisisUpdate crisisUpdate) {
        int id = 1;
        try {
            SimpleDateFormat datetimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO `ssad`.`crisisupdate` "
                            + "(`CrisisID`, `Update`, `timeUpdated`) "
                            + "VALUES (?, ?, ?);");
            // Parameters start with 1
            preparedStatement.setInt(1, crisisUpdate.getCrisisID());
            preparedStatement.setString(2, crisisUpdate.getUpdate());
            preparedStatement.setString(3, datetimeformat.format(crisisUpdate.getTimeupdated().getTime()));
            preparedStatement.execute();
        } catch (SQLException e) {
            id=-1;
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
    public ArrayList<CrisisUpdate> getAllCrisisUpdate(int crisisID) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ArrayList<CrisisUpdate> crisisUpdateList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM ssad.crisisupdate where crisisID = ?;");
            // Parameters start with 1
            preparedStatement.setInt(1, crisisID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CrisisUpdate crisisUpdate = new CrisisUpdate();
                crisisUpdate.setCrisisID(rs.getInt("crisisID"));
                crisisUpdate.setCrisisUpdateID(rs.getInt("crisisupdateID"));
                crisisUpdate.setUpdate(rs.getString("update"));
                crisisUpdate.setTimeupdated(rs.getString("timeUpdated"));
                crisisUpdateList.add(crisisUpdate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crisisUpdateList;
    }
}
