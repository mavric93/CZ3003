/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import core.model.TerrorismCrisis;
import core.util.DbUtil;

/**
 *
 * @author mavric
 */
public class TerrorismCrisisDAO {

    private Connection connection;

    public TerrorismCrisisDAO() {
        connection = DbUtil.getConnection();
    }

    public void addTerrorismCrisis(TerrorismCrisis terrorismCrisis) {
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

    public void modifyTerrorismCrisis(TerrorismCrisis terrorismCrsis) {
        /*try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update crisis set crisisname=?,crisi where crisisid =?");
            // Parameters start with 1
            preparedStatement.setString(1, terrorismCrsis.getCrisisName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}
