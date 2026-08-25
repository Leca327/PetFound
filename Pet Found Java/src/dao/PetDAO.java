package dao;

import factory.ConnectionFactory;
import java.sql.*;
import modelo.PetMOD;

public class PetDAO {

    private Connection connection;

    String codpet;
    String nomepet;
    String fai_ida;
    String historia;
    String raca;
    String cor_pel;
    String sexo;
    String porte;
    String descpet;
    String loc;
    String tipoa;
    String pf;
    String dtp;
    String hrp;
    String aprovacao;

    public PetDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(PetMOD pet) {
        String sql = "INSERT INTO pet(petcod, nomepet, fai_ida, historia, raca, cor_pel, sexo, porte, descpet,admincodadmn,estadop,finalidade,pessoacodp,aprovacaopet,dtp,hrp,tipop,cidadep) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, pet.getCodpet());
            stmt.setString(2, pet.getNomepet());
            stmt.setString(3, pet.getFai_ida());
            stmt.setString(4, pet.getHistoria());
            stmt.setString(5, pet.getRaca());
            stmt.setString(6, pet.getCor_pel());
            stmt.setString(7, pet.getSexo());
            stmt.setString(8, pet.getPorte());
            stmt.setString(9, pet.getDescpet());
            stmt.setString(10, pet.getAdmcad());
            stmt.setString(11, pet.getEst());
            stmt.setString(12, pet.getTipoa());
            stmt.setString(13, pet.getPf());
            stmt.setBoolean(14, true);
            stmt.setString(15, pet.getDtp());
            stmt.setString(16, pet.getHrp());
            stmt.setString(17, pet.getTipo());
            stmt.setString(18, pet.getCid());
            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        if (pet.getImg() != null) {
            sql = "INSERT INTO imagem(codimg ,img,petcodpet ) VALUES(?,?,?)";

            try {
                stmt = connection.prepareStatement(sql);

                stmt.setString(1, pet.getCodimg());
                stmt.setBlob(2, pet.getImg(), pet.getTamanho());
                stmt.setString(3, pet.getCodpet());
                stmt.execute();
                stmt.close();

            } catch (SQLException u) {
                throw new RuntimeException(u);
            }
        }
    }
}
