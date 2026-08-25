
package dao;

import factory.ConnectionFactory;
import java.sql.*;
import javax.swing.JOptionPane;
import modelo.AdminMOD;

public class AdminDAO {
    
    private Connection connection;
    
    String user;
    String senha;
    String nome;
    String img;
    int tamanho;

    public AdminDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adiciona(AdminMOD admin) {
        String sql = "INSERT INTO admin(usera, senhaa, nomea,admcod,imgadm,chefe) VALUES(?,?,?,?,?,?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, admin.getUser());
            stmt.setString(2, admin.getSenha());
            stmt.setString(3, admin.getNome());
            stmt.setString(4,admin.getCod());
            stmt.setBlob(5, admin.getImg(), admin.getTamanho());
            stmt.setBoolean(6,false);
            stmt.execute();
            stmt.close();
            
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

}
