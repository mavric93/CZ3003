/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.DAO;

import core.model.Crisis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import core.model.TerrorismCrisis;
import core.util.DbUtil;
import java.sql.ResultSet;
import java.util.logging.Level;

/**
 *
* @author mavric
 */
public class TerrorismCrisisDAO {
	//FUNCTIONS: CREATE,READ
    private Connection connection;
    public TerrorismCrisisDAO() {
        connection = DbUtil.getConnection();
    }
	//create
    public void create(TerrorismCrisis terrorismCrisis) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO `ssad`.`terrorism` "
                            + "(`crisisID`, `typeOfAttack`, `radius`) "
                            + "VALUES (?, ?, ?);");

            // Parameters start with 1
            preparedStatement.setInt(1, terrorismCrisis.getCrisisID());
            preparedStatement.setString(2, terrorismCrisis.getTypeOfAttack());
            preparedStatement.setInt(3, terrorismCrisis.getRadius());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //read 1 crisis base on CrisisID
	public Crisis getCrisisById(int crisisID,Crisis c) {
		TerrorismCrisis crisis = (TerrorismCrisis)c;
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM ssad.Terrorism WHERE CrisisID=?;");
            preparedStatement.setInt(1, crisisID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
				//retrieve data
				crisis.setCrisisID(rs.getInt("CrisisID"));
                crisis.setRadius(rs.getInt("radius"));
				crisis.setTypeOfAttack(rs.getString("typeOfAttack"));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(CrisisDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return crisis;
    }
}
