package Logar;
import factory.ConnectionFactory;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginChefe extends JPanel {
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    String userchefe;
    String senhachefe;
    private Connection connection;

    public LoginChefe() {
        setLayout(new GridLayout(2, 2));
        setBackground(new Color(255, 253, 243)); // Cor de fundo clara

        JLabel rotuloUsuario = new JLabel("Usuário:");
        JLabel rotuloSenha = new JLabel("Senha:");

        campoUsuario = new JTextField();
        campoSenha = new JPasswordField();

        // Defina as cores de texto para corresponder à cor escura
        rotuloUsuario.setForeground(new Color(64, 33, 7));
        rotuloSenha.setForeground(new Color(64, 33, 7));

        add(rotuloUsuario);
        add(campoUsuario);
        add(rotuloSenha);
        add(campoSenha);
    }
    
    public String getUsuario() {
        return campoUsuario.getText();
    }

    public String getSenha() {
        return new String(campoSenha.getPassword());
    }
    
    
    public void buscarAdmin() {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM admin WHERE admcod = '10'";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                userchefe = res.getString(1);
                senhachefe = res.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Boolean verificalog(){
        Boolean Status=null;
        buscarAdmin();
        Status = (userchefe == null ? getUsuario() == null : userchefe.equals(getUsuario())) && (senhachefe == null ? getSenha() == null : senhachefe.equals(getSenha()));
        
        return Status;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crie e configure o JOptionPane
            JOptionPane optionPane = new JOptionPane("Mensagem de erro aqui!", JOptionPane.ERROR_MESSAGE);
            optionPane.setIcon(new ImageIcon("icone_exclamacao.png")); // Substitua com o caminho para o ícone de exclamação

            // Crie o diálogo e mostre-o
            JDialog dialog = optionPane.createDialog("Erro de Login");
            dialog.setVisible(true);
        });
    }
}
