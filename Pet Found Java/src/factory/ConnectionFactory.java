package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private Connection con;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/petfound";
    private String user = "root";
    private String password = "root";

   /* public void mudarsenha(String pass) {
        if (pass == "" || pass == null) {
            setPassword("root");
        } else {
            setPassword(pass);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/

    public Connection getConnection() {
       // String pass = getPassword();
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            return con;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    /*public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/petfound","root","root");
        }
        catch(SQLException excecao) {
            throw new RuntimeException(excecao);

        }
    }*/
}
