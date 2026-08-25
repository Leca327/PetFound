package dao;

import factory.ConnectionFactory;
import java.sql.*;
import modelo.ServicoMOD;

public class ServicoDAO {

    private Connection connection;

    String codserv;
    String nomeserv;
    String descserv;
    String preco;
    String loc;

    public ServicoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(ServicoMOD servico) {
        String sql = "INSERT INTO servico(servcod, nomeserv, descserv,preco,admin_codadmn,pessoa_codp,aprovacaoserv,dts,hrs,estados,cidades) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, servico.getCodserv());
            stmt.setString(2, servico.getNomeserv());
            stmt.setString(3, servico.getDescserv());
            stmt.setString(4, servico.getPreco());
            stmt.setString(5, servico.getAdmcad());
            stmt.setString(6, servico.getPf());
            stmt.setBoolean(7, true);
            stmt.setString(8, servico.getDtp());
            stmt.setString(9, servico.getHrp());
            stmt.setString(10, servico.getEst());
            stmt.setString(11, servico.getCid());

            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        if (servico.getImg() != null) {
            sql = "INSERT INTO imagem(codimg ,img,servicocodserv ) VALUES(?,?,?)";

            try {
                stmt = connection.prepareStatement(sql);

                stmt.setString(1, servico.getCodimg());
                stmt.setBlob(2, servico.getImg(), servico.getTamanho());
                stmt.setString(3, servico.getCodserv());
                stmt.execute();
                stmt.close();

            } catch (SQLException u) {
                throw new RuntimeException(u);
            }
        }
    }
}
