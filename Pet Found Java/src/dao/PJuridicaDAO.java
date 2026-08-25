package dao;

import factory.ConnectionFactory;
import java.sql.*;
import modelo.PJuridicaMOD;

public class PJuridicaDAO {

    private Connection connection;

    String nomep;
    String contatop;
    String emailp;
    String enderecop;
    String nickname;
    String senha;
    String codp;
    String cnpj;
    String img;
    int tamanho;

    public PJuridicaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(PJuridicaMOD pj) {
        String sql;

        sql = "INSERT INTO endereco (endcod,cep,uf,bairro,endereco,numero,cmpt,cidade) VALUES (?, ?, ?,?, ?, ?,?, ?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, pj.getCodend());
            stmt.setString(2, pj.getCep());
            stmt.setString(3, pj.getUf());
            stmt.setString(4, pj.getBai());
            stmt.setString(5, pj.getEnd());
            stmt.setString(6, pj.getNum());
            stmt.setString(7, pj.getCmpt());
            stmt.setString(8, pj.getCid());

            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        if (pj.getImg() == null) {

            sql = "INSERT INTO pessoa (nomep, contatop, emailp, endcodend, nickname, senha, pcod,dtcriacao,hrcriacao)VALUES(?,?,?,?,?,?,?,?,?);";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, pj.getNomep());
                stmt.setString(2, pj.getContatop());
                stmt.setString(3, pj.getEmailp());
                stmt.setString(4, pj.getCodend());
                stmt.setString(5, pj.getNickname());
                stmt.setString(6, pj.getSenha());
                stmt.setString(7, pj.getCodp());
                stmt.setString(8, pj.getDtcriacao());
                stmt.setString(9, pj.getHrcriacao());

                stmt.execute();
                stmt.close();

            } catch (SQLException u) {
                throw new RuntimeException(u);
            }

        } else {

            sql = "INSERT INTO pessoa (nomep, contatop, emailp, endcodend, nickname, senha, pcod,imgperfil,dtcriacao,hrcriacao)VALUES(?,?,?,?,?,?,?,?,?,?);";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, pj.getNomep());
                stmt.setString(2, pj.getContatop());
                stmt.setString(3, pj.getEmailp());
                stmt.setString(4, pj.getCodend());
                stmt.setString(5, pj.getNickname());
                stmt.setString(6, pj.getSenha());
                stmt.setString(7, pj.getCodp());
                stmt.setBlob(8, pj.getImg(), pj.getTamanho());
                stmt.setString(9, pj.getDtcriacao());
                stmt.setString(10, pj.getHrcriacao());
                stmt.execute();
                stmt.close();

            } catch (SQLException u) {
                throw new RuntimeException(u);
            }

        }

        sql = "INSERT INTO juridica (cnpj,ramo_ativ,tipoj, cod_p) VALUES(?,?,?,?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, pj.getCnpj());
            stmt.setString(2, pj.getRamoativ());
            stmt.setString(3, pj.getTipo());
            stmt.setString(4, pj.getCodp());
            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

}
