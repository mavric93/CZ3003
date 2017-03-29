/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import core.model.TrainBreakDownCrisis;
import core.util.DbUtil;

/**
 *
* @author mavric
 */
public class TrainBreakDownCrisisDAO {

    private Connection connection;

    public TrainBreakDownCrisisDAO() {
        connection = DbUtil.getConnection();
    }
	//create
    public void create(TrainBreakDownCrisis TrainBreakDownCrisis) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO `ssad`.`TrainBreakDown` "
                            + "(`crisisID`, `Second_mrt_address`, `Second_mrt_lat`, `Second_mrt_lng`) "
                            + "VALUES (?, ?, ?);");

            // Parameters start with 1
            preparedStatement.setInt(1, TrainBreakDownCrisis.getCrisisID());
            preparedStatement.setString(2, TrainBreakDownCrisis.getSecondMRTAddress());
            preparedStatement.setInt(3, TrainBreakDownCrisis.getSecondMRTLat());
			preparedStatement.setInt(4, TrainBreakDownCrisis.getSecondMRTLng());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	//read 1 crisis base on CrisisID
	public Crisis getCrisisById(int crisisID,Crisis c) {
		TrainBreakDownCrisis crisis = (TrainBreakDownCrisis)c;
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM ssad.TrainBreakDown WHERE CrisisID=?;");
            preparedStatement.setInt(1, crisisID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
				//retrieve data
				crisis.setCrisisID(rs.getInt("CrisisID"));
                crisis.setRadius(rs.getInt("radius"));
				crisis.setTypeOfAttack(rs.getString("typeOfAttack"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CrisisDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return crisis;
    }
}
