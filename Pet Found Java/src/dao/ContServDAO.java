
package dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.ContServMOD;

public class ContServDAO {
     private Connection connection;

    public ContServDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(ContServMOD cv) {
        String sql = "INSERT INTO contatoserv(codconts, scodserv, pcodp,dtcs,hrcs) VALUES(?,?,?,?,?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, cv.getCodcontp());
            stmt.setString(2, cv.getServcod());
            stmt.setString(3, cv.getPcod());
            stmt.setString(4, cv.getDt());
            stmt.setString(5, cv.getHr());
            
            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }
}
