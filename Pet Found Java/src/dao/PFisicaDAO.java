package dao;

import factory.ConnectionFactory;
import java.sql.*;
import modelo.PFisicaMOD;

public class PFisicaDAO {

    private Connection connection;

    String nomep;
    String contatop;
    String emailp;
    String enderecop;
    String nickname;
    String senha;
    String codp;
    String dt_nascimento;
    String sexo;
    String img;
    int tamanho;
    String hrcriacao;
    String dtcriacao;
    String codend;
    String cep;
    String uf;
    String bai;
    String end;
    String num;
    String cmpt;
    String cid;
    String snome;

    public PFisicaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(PFisicaMOD pf) {
        String sql;

        sql = "INSERT INTO endereco (endcod,cep,uf,bairro,endereco,numero,cmpt,cidade) VALUES (?, ?, ?,?, ?, ?,?, ?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, pf.getCodend());
            stmt.setString(2, pf.getCep());
            stmt.setString(3, pf.getUf());
            stmt.setString(4, pf.getBai());
            stmt.setString(5, pf.getEnd());
            stmt.setString(6, pf.getNum());
            stmt.setString(7, pf.getCmpt());
            stmt.setString(8, pf.getCid());

            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        if (pf.getImg() == null) {

            sql = "INSERT INTO pessoa (nomep, contatop, emailp, endcodend, nickname, senha, pcod,dtcriacao,hrcriacao,snomep)VALUES(?,?,?,?,?,?,?,?,?,?);";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, pf.getNomep());
                stmt.setString(2, pf.getContatop());
                stmt.setString(3, pf.getEmailp());
                stmt.setString(4, pf.getCodend());
                stmt.setString(5, pf.getNickname());
                stmt.setString(6, pf.getSenha());
                stmt.setString(7, pf.getCodp());
                stmt.setString(8, pf.getDtcriacao());
                stmt.setString(9, pf.getHrcriacao());
                stmt.setString(10, pf.getSnome());

                stmt.execute();
                stmt.close();

            } catch (SQLException u) {
                throw new RuntimeException(u);
            }

        } else {

            sql = "INSERT INTO pessoa (nomep, contatop, emailp, endcodend, nickname, senha, pcod,imgperfil,dtcriacao,hrcriacao,snomep)VALUES(?,?,?,?,?,?,?,?,?,?,?);";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, pf.getNomep());
                stmt.setString(2, pf.getContatop());
                stmt.setString(3, pf.getEmailp());
                stmt.setString(4, pf.getCodend());
                stmt.setString(5, pf.getNickname());
                stmt.setString(6, pf.getSenha());
                stmt.setString(7, pf.getCodp());
                stmt.setBlob(8, pf.getImg(), pf.getTamanho());
                stmt.setString(9, pf.getDtcriacao());
                stmt.setString(10, pf.getHrcriacao());
                stmt.setString(11, pf.getSnome());
                stmt.execute();
                stmt.close();

            } catch (SQLException u) {
                throw new RuntimeException(u);
            }

        }

        sql = "INSERT INTO fisica (dt_nascimento,sexo,codp) VALUES (?, ?, ?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, pf.getDt_nascimento());
            stmt.setString(2, pf.getSexo());
            stmt.setString(3, pf.getCodp());

            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

}
