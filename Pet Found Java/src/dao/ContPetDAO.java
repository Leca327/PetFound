package dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.ContPetMOD;

public class ContPetDAO {

    private Connection connection;

    public ContPetDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(ContPetMOD cp) {
        String sql = "INSERT INTO contatopet(codcontp, petcodpet, pfcodp,dtcp,hrcp,tipocont) VALUES(?,?,?,?,?,?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, cp.getCodcontp());
            stmt.setString(2, cp.getPetcod());
            stmt.setString(3, cp.getPfcod());
            stmt.setString(4, cp.getDt());
            stmt.setString(5, cp.getHr());
            stmt.setString(6, cp.getTpcont());
            
            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }
}
