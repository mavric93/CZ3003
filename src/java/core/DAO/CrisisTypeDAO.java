/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import core.model.Crisis;
import core.model.CrisisType;
import core.util.DbUtil;

/**
 *
 * @author mavric
 */
public class CrisisTypeDAO {
    
    private Connection connection;

    public CrisisTypeDAO() {
        connection = DbUtil.getConnection();
    }
    
     public List<CrisisType> getAllCrisisType() {
        List<CrisisType> crisisTypeList = new ArrayList<CrisisType>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from crisistype");
            while (rs.next()) {
                CrisisType crisisType = new CrisisType();
                crisisType.setCrisisType(rs.getString("CType"));
                crisisType.setIcon(rs.getString("Icon"));
                crisisTypeList.add(crisisType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return crisisTypeList;
    }
}
