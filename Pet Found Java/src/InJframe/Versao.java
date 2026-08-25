package InJframe;

import Logar.LoginChefe;
import Logar.login;
import Logar.versao;
import alert.alert;
import factory.ConnectionFactory;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public final class Versao extends javax.swing.JInternalFrame {

    private Connection connection;
    String admin, audio;
    Boolean maxc;
    String selcod;
    Boolean permchefe = false;

    public Versao(String admin, String audio) {
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI a = (BasicInternalFrameUI) this.getUI();
        a.setNorthPane(null);
        //
        this.admin = admin;
        this.audio = audio;
        initComponents();
        buscarVersao();
        opc();
        bus(this.admin);
    }

    public void bus(String user) {

        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM admin WHERE usera = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                String tx1 = res.getString(1);//User
                String tx2 = res.getString(2);//Senha
                String tx3 = res.getString(3);//Nome
                String tx4 = res.getString(4);//Cod
                byte[] imageBytes = res.getBytes(5);//Imagem
                Boolean tx5 = res.getBoolean(7);//chefe

                permchefe = tx5;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void limpar() {
        label_info01.setText("-");
        label_info02.setText("-");
        label_info03.setText("-");
        label_info04.setText("-");
        label_info05.setText("-");
        label_info06.setText("-");
        label_info07.setText("-");
        label_info08.setText("-");
        label_info09.setText("-");
        label_info10.setText("-");
        cb_v.removeAllItems();
        textf_vs.setText("");
        textf_nm.setText("");
        textf_additem01.setText("");
        textf_additem02.setText("");
        textf_additem03.setText("");
        textf_additem04.setText("");
        textf_additem05.setText("");
        textf_additem10.setText("");
        textf_additem06.setText("");
        textf_additem07.setText("");
        textf_additem08.setText("");
        textf_additem09.setText("");

    }

    public void buscarVersao() {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "select * from versao";
        //cb_v.removeAllItems();
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            // Limpar itens existentes do ComboBox
            while (res.next()) {
                cb_v.removeItem("Sem Versões Guardadas");
                String tx1 = res.getString(1);

                L_vsatual.setText(tx1);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Boolean maxperm() {
        maxc = !(textf_vs.getText().length() <= 50 && textf_nm.getText().length() <= 50 && (textf_additem01.getText().length() + textf_additem02.getText().length() + textf_additem03.getText().length() + textf_additem04.getText().length() + textf_additem05.getText().length() + textf_additem06.getText().length() + textf_additem07.getText().length() + textf_additem08.getText().length() + textf_additem09.getText().length() + textf_additem10.getText().length() <= 480));
        return maxc;
    }

    public void cadastro() {
        Boolean perm = maxperm();
        if (perm == false) {
            if (!"v . . ".equals(textf_vs.getText()) && !textf_nm.getText().isEmpty()) {
                this.connection = new ConnectionFactory().getConnection();
                PreparedStatement ps;
                String v = textf_vs.getText();

                try {
                    ps = connection.prepareStatement("select * from versao where codv=?;");
                    ps.setString(1, v);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Versão já está inserida";
                        String msg1 = "no Banco";
                        String tit = "Versão existente";
                        al.alertinput(tit, "erro", msg, msg1, "", "erro");

                    } else {
                        if ((!textf_additem01.getText().isEmpty() || !textf_additem02.getText().isEmpty() || !textf_additem03.getText().isEmpty() || !textf_additem04.getText().isEmpty() || !textf_additem05.getText().isEmpty() || !textf_additem10.getText().isEmpty() || !textf_additem06.getText().isEmpty() || !textf_additem07.getText().isEmpty() || !textf_additem08.getText().isEmpty() || !textf_additem09.getText().isEmpty())) {
                            StringBuilder descvBuilder = new StringBuilder();

                            // Verificar cada campo de texto e adicionar apenas valores não vazios à string descv
                            for (int i = 0; i < 10; i++) {
                                String fieldValue = getTextFieldValue(i);
                                if (!fieldValue.isEmpty()) {
                                    if (descvBuilder.length() > 0) {
                                        descvBuilder.append(";");
                                    }
                                    descvBuilder.append("-").append(fieldValue);
                                }
                            }

                            // Adicionar um ponto-e-vírgula no final, mesmo que descvBuilder esteja vazio
                            descvBuilder.append(";");

                            String sql = "INSERT INTO versao (codv, nome, descv) VALUES(?,?,?);";

                            try {
                                PreparedStatement stmt = connection.prepareStatement(sql);

                                stmt.setString(1, textf_vs.getText());
                                stmt.setString(2, textf_nm.getText());
                                stmt.setString(3, descvBuilder.toString());
                                stmt.execute();
                                stmt.close();

                                alert al = new alert(admin, audio);
                                al.setVisible(true);
                                String msg = "Versão Cadastrada";
                                String msg1 = "Com Sucesso";
                                String tit = "Cadastro Versão";
                                al.alertinput(tit, "ok", msg, msg1, "", "sucesso");
                                limpar();
                                buscarVersao();
                            } catch (SQLException u) {
                                throw new RuntimeException(u);
                            }
                        } else {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Ao menos 1 campo";
                            String msg1 = "Precisa ser preenchido";
                            String tit = "Cadastro Versão";
                            al.alertinput(tit, "erro", msg, msg1, "", "erro");
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Os campos de Número e Título";
                String msg1 = "Precisam ser preenchidos";
                String tit = "Cadastro Versão";
                al.alertinput(tit, "erro", msg, msg1, "", "erro");
            }
        } else {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Diminua a Quantidade ";
            String msg2 = "de Caracter Para";
            String msg3 = "o Cadastro.";
            String tit = "Excedeu o Limite de Caracter";
            al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
        }

    }

    private String getTextFieldValue(int index) {
        switch (index) {
            case 0:
                return textf_additem01.getText();
            case 1:
                return textf_additem02.getText();
            case 2:
                return textf_additem03.getText();
            case 3:
                return textf_additem04.getText();
            case 4:
                return textf_additem05.getText();
            case 5:
                return textf_additem06.getText();
            case 6:
                return textf_additem07.getText();
            case 7:
                return textf_additem08.getText();
            case 8:
                return textf_additem09.getText();
            case 9:
                return textf_additem10.getText();
            default:
                return "";
        }
    }

    public void delet() {
        LoginChefe panel = new LoginChefe();
        int result = JOptionPane.showConfirmDialog(this, panel, "Login do Chefe", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {

            Boolean status = panel.verificalog();

            if (status) {
                String cod = textf_vs.getText();
                try {
                    PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM versao WHERE codv = ?");
                    deleteStatement.setString(1, cod);
                    int i = deleteStatement.executeUpdate();

                    if (i != 0) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Versão " + cod;
                        String msg2 = "deletado do database";
                        String tit = "Deleção de Versão";
                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                        limpar();
                        buscarVersao();
                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Erro ao deletar";
                        String tit = "Deleção de Versão";
                        al.alertinput(tit, "erro", "", msg, "", "erro");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                alert al = new alert("", audio);
                al.setVisible(true);
                String msg = "Usuario ou Senha";
                String msg1 = "Incorreta";
                String tit = "Informação Errada";
                al.alertinput(tit, "erro", msg, msg1, "", "erro");
            }
        }
    }

    public void buscar() {
        String cod = textf_vs.getText();
        this.connection = new ConnectionFactory().getConnection();
        String sql = "select * from versao where codv=?";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, cod);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                String tx1 = res.getString(3);
                String[] items = tx1.split(";-");

                textf_vs.setText(res.getString(1));
                textf_nm.setText(res.getString(2));

                JTextField[] textFields = {
                    textf_additem01, textf_additem02, textf_additem03, textf_additem04,
                    textf_additem05, textf_additem06, textf_additem07, textf_additem08,
                    textf_additem09, textf_additem10
                };

                for (int i = 0; i < Math.min(10, items.length); i++) {
                    String item = items[i].trim().replace("-", "").replace(";", ""); // Remove "-" e ";" e espaços em branco no início e no final
                    textFields[i].setText(item);
                }

            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Erro ao Buscar";
                String msg1 = "Versão não existe";
                String tit = "Buscar de Versão";
                al.alertinput(tit, "erro", msg, msg1, "", "erro");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tamanho();
        realesed();
    }

    public void atualizar() {
        Boolean perm = maxperm();
        if (perm == false) {
            if (!"v . . ".equals(textf_vs.getText()) && !textf_nm.getText().isEmpty()) {
                this.connection = new ConnectionFactory().getConnection();
                PreparedStatement ps;
                String v = textf_vs.getText();

                try {
                    // Check if the version (codv) exists in the database
                    ps = connection.prepareStatement("SELECT * FROM versao WHERE codv = ?;");
                    ps.setString(1, v);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        if ((!textf_additem01.getText().isEmpty() || !textf_additem02.getText().isEmpty() || !textf_additem03.getText().isEmpty() || !textf_additem04.getText().isEmpty() || !textf_additem05.getText().isEmpty() || !textf_additem10.getText().isEmpty() || !textf_additem06.getText().isEmpty() || !textf_additem07.getText().isEmpty() || !textf_additem08.getText().isEmpty() || !textf_additem09.getText().isEmpty())) {
                            // Version exists; perform the update
                            StringBuilder descvBuilder = new StringBuilder();

                            // Verificar cada campo de texto e adicionar apenas valores não vazios à string descv
                            for (int i = 0; i < 10; i++) {
                                String fieldValue = getTextFieldValue(i);
                                if (!fieldValue.isEmpty()) {
                                    if (descvBuilder.length() > 0) {
                                        descvBuilder.append(";");
                                    }
                                    descvBuilder.append("-").append(fieldValue);
                                }
                            }

                            // Adicionar um ponto-e-vírgula no final, mesmo que descvBuilder esteja vazio
                            descvBuilder.append(";");

                            String sql = "UPDATE versao SET nome = ?, descv = ? WHERE codv = ?;";

                            try {
                                PreparedStatement stmt = connection.prepareStatement(sql);

                                stmt.setString(1, textf_nm.getText());
                                stmt.setString(2, descvBuilder.toString());
                                stmt.setString(3, v);
                                stmt.executeUpdate();
                                stmt.close();

                                alert al = new alert(admin, audio);
                                al.setVisible(true);
                                String msg = "Versão Atualizada";
                                String msg1 = "Com Sucesso";
                                String tit = "Atualização de Versão";
                                al.alertinput(tit, "ok", msg, msg1, "", "sucesso");
                                limpar();
                                buscarVersao();
                            } catch (SQLException u) {
                                throw new RuntimeException(u);
                            }
                        } else {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Ao menos 1 campo";
                            String msg1 = "Precisa ser preenchido";
                            String tit = "Cadastro Versão";
                            al.alertinput(tit, "erro", msg, msg1, "", "erro");
                        }

                    } else {
                        // Version doesn't exist in the database
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Versão não encontrada";
                        String msg1 = "no Banco de Dados";
                        String tit = "Versão não existente";
                        al.alertinput(tit, "erro", msg, msg1, "", "erro");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Os campos de Versão e Nome";
                String msg1 = "Precisam ser preenchidos";
                String tit = "Cadastro Versão";
                al.alertinput(tit, "erro", msg, msg1, "", "erro");
            }
        } else {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Diminua a Quantidade ";
            String msg2 = "de Caracter Para";
            String msg3 = "a Atualização.";
            String tit = "Excedeu o Limite de Caracter";
            al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
        }
    }

    public void tamanho() {

        //User
        String inputText = textf_nm.getText(); // Obtém o texto do campo de texto
        int numCaracteres1 = inputText.length();
        int maxperm = 48;
        L_nm.setText(Integer.toString(numCaracteres1));
        if (numCaracteres1 > maxperm) {
            L_nm.setForeground(new Color(255, 51, 51));
            L_nm.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_nm.setForeground(new Color(0, 0, 0));
            L_nm.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        //Senha
        inputText = textf_additem01.getText(); // Obtém o texto do campo de texto
        int numCaracteres2 = inputText.length();
        maxperm = 48;
        L_desc01.setText(Integer.toString(numCaracteres2));
        if (numCaracteres2 > maxperm) {
            L_desc01.setForeground(new Color(255, 51, 51));
            L_desc01.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_desc01.setForeground(new Color(0, 0, 0));
            L_desc01.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_additem02.getText(); // Obtém o texto do campo de texto
        int numCaracteres3 = inputText.length();
        maxperm = 48;
        L_desc02.setText(Integer.toString(numCaracteres3));
        if (numCaracteres3 > maxperm) {
            L_desc02.setForeground(new Color(255, 51, 51));
            L_desc02.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_desc02.setForeground(new Color(0, 0, 0));
            L_desc02.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_additem03.getText(); // Obtém o texto do campo de texto
        int numCaracteres4 = inputText.length();
        maxperm = 48;
        L_desc03.setText(Integer.toString(numCaracteres4));
        if (numCaracteres4 > maxperm) {
            L_desc03.setForeground(new Color(255, 51, 51));
            L_desc03.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_desc03.setForeground(new Color(0, 0, 0));
            L_desc03.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_additem04.getText(); // Obtém o texto do campo de texto
        int numCaracteres5 = inputText.length();
        maxperm = 48;
        L_desc04.setText(Integer.toString(numCaracteres5));
        if (numCaracteres5 > maxperm) {
            L_desc04.setForeground(new Color(255, 51, 51));
            L_desc04.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_desc04.setForeground(new Color(0, 0, 0));
            L_desc04.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_additem05.getText(); // Obtém o texto do campo de texto
        int numCaracteres6 = inputText.length();
        maxperm = 48;
        L_desc05.setText(Integer.toString(numCaracteres6));
        if (numCaracteres6 > maxperm) {
            L_desc05.setForeground(new Color(255, 51, 51));
            L_desc05.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_desc05.setForeground(new Color(0, 0, 0));
            L_desc05.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_additem06.getText(); // Obtém o texto do campo de texto
        int numCaracteres7 = inputText.length();
        maxperm = 48;
        L_desc06.setText(Integer.toString(numCaracteres7));
        if (numCaracteres7 > maxperm) {
            L_desc06.setForeground(new Color(255, 51, 51));
            L_desc06.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_desc06.setForeground(new Color(0, 0, 0));
            L_desc06.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_additem07.getText(); // Obtém o texto do campo de texto
        int numCaracteres8 = inputText.length();
        maxperm = 48;
        L_desc07.setText(Integer.toString(numCaracteres8));
        if (numCaracteres8 > maxperm) {
            L_desc07.setForeground(new Color(255, 51, 51));
            L_desc07.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_desc07.setForeground(new Color(0, 0, 0));
            L_desc07.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_additem08.getText(); // Obtém o texto do campo de texto
        int numCaracteres9 = inputText.length();
        maxperm = 48;
        L_desc08.setText(Integer.toString(numCaracteres9));
        if (numCaracteres9 > maxperm) {
            L_desc08.setForeground(new Color(255, 51, 51));
            L_desc08.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_desc08.setForeground(new Color(0, 0, 0));
            L_desc08.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_additem09.getText(); // Obtém o texto do campo de texto
        int numCaracteres10 = inputText.length();
        maxperm = 48;
        L_desc09.setText(Integer.toString(numCaracteres10));
        if (numCaracteres10 > maxperm) {
            L_desc09.setForeground(new Color(255, 51, 51));
            L_desc09.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_desc09.setForeground(new Color(0, 0, 0));
            L_desc09.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_additem10.getText(); // Obtém o texto do campo de texto
        int numCaracteres11 = inputText.length();
        maxperm = 48;
        L_desc10.setText(Integer.toString(numCaracteres11));
        if (numCaracteres11 > maxperm) {
            L_desc10.setForeground(new Color(255, 51, 51));
            L_desc10.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_desc10.setForeground(new Color(0, 0, 0));
            L_desc10.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }
        int tot = 0;
        tot = numCaracteres11 + numCaracteres10 + numCaracteres9 + numCaracteres8 + numCaracteres7 + numCaracteres6 + numCaracteres5 + numCaracteres4 + numCaracteres3 + numCaracteres2;
        maxperm = 480;
        L_tot.setText(Integer.toString(tot));
        if (tot > maxperm) {
            L_tot.setForeground(new Color(255, 51, 51));
            L_tot.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_tot.setForeground(new Color(0, 0, 0));
            L_tot.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

    }

    public void audios(String tpa) {
        switch (audio) {
            case "on":
                AudioInputStream audioInputStream;
                if (null != tpa) {
                    switch (tpa) {

                        case "tc"://voltar
                try {
                            // Carrega o arquivo de áudio
                            audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/audio/transicaovolta.wav"));

                            // Cria um objeto Clip para reproduzir o áudio
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInputStream);

                            // Inicia a reprodução do áudio
                            clip.start();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;

                        case "cl"://click
                try {
                            // Carrega o arquivo de áudio
                            audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/audio/click.wav"));

                            // Cria um objeto Clip para reproduzir o áudio
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInputStream);

                            // Inicia a reprodução do áudio
                            clip.start();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;

                        case "tci"://msg de volta
                try {
                            // Carrega o arquivo de áudio
                            audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/audio/transicaoida.wav"));

                            // Cria um objeto Clip para reproduzir o áudio
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInputStream);

                            // Inicia a reprodução do áudio
                            clip.start();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;

                        default:
                            break;
                    }
                }
            case "off":
                //sem audio
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        textf_nm = new javax.swing.JTextField();
        textf_additem01 = new javax.swing.JTextField();
        textf_additem02 = new javax.swing.JTextField();
        textf_additem03 = new javax.swing.JTextField();
        textf_additem04 = new javax.swing.JTextField();
        textf_additem05 = new javax.swing.JTextField();
        textf_additem06 = new javax.swing.JTextField();
        textf_additem07 = new javax.swing.JTextField();
        textf_additem08 = new javax.swing.JTextField();
        textf_additem09 = new javax.swing.JTextField();
        textf_additem10 = new javax.swing.JTextField();
        L_nm = new javax.swing.JLabel();
        L_desc01 = new javax.swing.JLabel();
        L_desc02 = new javax.swing.JLabel();
        L_desc03 = new javax.swing.JLabel();
        L_desc04 = new javax.swing.JLabel();
        L_desc05 = new javax.swing.JLabel();
        L_desc06 = new javax.swing.JLabel();
        L_desc07 = new javax.swing.JLabel();
        L_desc08 = new javax.swing.JLabel();
        L_desc09 = new javax.swing.JLabel();
        L_desc10 = new javax.swing.JLabel();
        L_tot = new javax.swing.JLabel();
        L_nv = new javax.swing.JLabel();
        L_nv1 = new javax.swing.JLabel();
        L_nv2 = new javax.swing.JLabel();
        Lbuscar = new javax.swing.JLabel();
        textf_vs = new javax.swing.JFormattedTextField();
        jbuttonArr3 = new SwingPerson.JbuttonArr();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        label_info01 = new javax.swing.JLabel();
        label_info02 = new javax.swing.JLabel();
        label_info03 = new javax.swing.JLabel();
        label_info04 = new javax.swing.JLabel();
        label_info05 = new javax.swing.JLabel();
        label_info06 = new javax.swing.JLabel();
        label_info07 = new javax.swing.JLabel();
        label_info08 = new javax.swing.JLabel();
        label_info09 = new javax.swing.JLabel();
        label_info10 = new javax.swing.JLabel();
        cb_v = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btn_atualizar = new SwingPerson.JbuttonArr();
        btn_limpar = new SwingPerson.JbuttonArr();
        btn_cadastrar = new SwingPerson.JbuttonArr();
        btn_del = new SwingPerson.JbuttonArr();
        L_vsatual = new javax.swing.JLabel();
        comb_opc = new javax.swing.JComboBox<>();

        setBorder(null);
        setToolTipText("");

        jPanel1.setBackground(new java.awt.Color(64, 33, 7));
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(1284, 666));

        jPanel3.setBackground(new java.awt.Color(255, 253, 243));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        textf_nm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_nmKeyReleased(evt);
            }
        });

        textf_additem01.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_additem01KeyReleased(evt);
            }
        });

        textf_additem02.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_additem02KeyReleased(evt);
            }
        });

        textf_additem03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textf_additem03ActionPerformed(evt);
            }
        });
        textf_additem03.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_additem03KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_additem03KeyReleased(evt);
            }
        });

        textf_additem04.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_additem04KeyReleased(evt);
            }
        });

        textf_additem05.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_additem05KeyReleased(evt);
            }
        });

        textf_additem06.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_additem06KeyReleased(evt);
            }
        });

        textf_additem07.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_additem07KeyReleased(evt);
            }
        });

        textf_additem08.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_additem08KeyReleased(evt);
            }
        });

        textf_additem09.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_additem09KeyReleased(evt);
            }
        });

        textf_additem10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_additem10KeyReleased(evt);
            }
        });

        L_nm.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_nm.setText("0");

        L_desc01.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_desc01.setText("0");

        L_desc02.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_desc02.setText("0");

        L_desc03.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_desc03.setText("0");

        L_desc04.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_desc04.setText("0");

        L_desc05.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_desc05.setText("0");

        L_desc06.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_desc06.setText("0");

        L_desc07.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_desc07.setText("0");

        L_desc08.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_desc08.setText("0");

        L_desc09.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_desc09.setText("0");

        L_desc10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_desc10.setText("0");

        L_tot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        L_tot.setText("0");
        L_tot.setToolTipText("Total de Caracteres na Descrição");

        L_nv.setText("Número Versão");

        L_nv1.setText("Novas Atualizações da Versão:");

        L_nv2.setText("Titulo da Versão");

        Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        Lbuscar.setToolTipText("Buscar Versão Anteriores");
        Lbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Lbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LbuscarMouseClicked(evt);
            }
        });

        try {
            textf_vs.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("v#.#.#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        textf_vs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_vsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_vsKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(L_nm)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(L_nv2)
                            .addComponent(textf_nm, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(L_nv)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(textf_vs, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Lbuscar)))
                .addContainerGap(77, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(L_tot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(L_nv1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(textf_additem01, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textf_additem02, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textf_additem03, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textf_additem04, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textf_additem05, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textf_additem06, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textf_additem07, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textf_additem08, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textf_additem09, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textf_additem10, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(L_desc02, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(L_desc03, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(L_desc04, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(L_desc05, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(L_desc06, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(L_desc07, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(L_desc08, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(L_desc09, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(L_desc10, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(L_desc01, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(Lbuscar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(L_nv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textf_vs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(L_nv2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textf_nm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_nm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(L_nv1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textf_additem01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_desc01)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textf_additem02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_desc02)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textf_additem03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_desc03)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textf_additem04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_desc04)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textf_additem05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_desc05)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textf_additem06, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_desc06)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textf_additem07, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_desc07)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textf_additem08, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_desc08)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textf_additem09, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_desc09)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textf_additem10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_desc10)
                .addGap(12, 12, 12)
                .addComponent(L_tot)
                .addContainerGap())
        );

        jbuttonArr3.setText("Ver Versões Do Projeto");
        jbuttonArr3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonArr3ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(64, 33, 7));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel4.setBackground(new java.awt.Color(255, 253, 243));
        jPanel4.setPreferredSize(new java.awt.Dimension(12, 165));

        label_info01.setText("-");

        label_info02.setText("-");

        label_info03.setText("-");

        label_info04.setText("-");

        label_info05.setText("-");

        label_info06.setText("-");

        label_info07.setText("-");

        label_info08.setText("-");

        label_info09.setText("-");

        label_info10.setText("-");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_info01, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_info03, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_info04, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_info05, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_info06, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_info07, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_info08, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_info09, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_info10, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_info02, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_info01)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info02)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info03)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info04)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info05)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info06)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info07)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info08)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info09)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info10)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        cb_v.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cb_v.setSelectedItem(null);
        cb_v.setToolTipText("");
        cb_v.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb_v.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_vActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(cb_v, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(cb_v, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 253, 243));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Visualização Da Versão");

        btn_atualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/atualizar.png"))); // NOI18N
        btn_atualizar.setToolTipText("Atualizar Versão");
        btn_atualizar.setMaximumSize(new java.awt.Dimension(0, 0));
        btn_atualizar.setMinimumSize(new java.awt.Dimension(60, 60));
        btn_atualizar.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizarActionPerformed(evt);
            }
        });

        btn_limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/apagar.png"))); // NOI18N
        btn_limpar.setToolTipText("Limpar Todos os Campos");
        btn_limpar.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparActionPerformed(evt);
            }
        });

        btn_cadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cadadc.png"))); // NOI18N
        btn_cadastrar.setToolTipText("Cadastre Versão");
        btn_cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadastrarActionPerformed(evt);
            }
        });

        btn_del.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/deletar.png"))); // NOI18N
        btn_del.setToolTipText("Deletar Versão");
        btn_del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delActionPerformed(evt);
            }
        });

        L_vsatual.setBackground(new java.awt.Color(255, 253, 243));
        L_vsatual.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        L_vsatual.setForeground(new java.awt.Color(255, 253, 243));
        L_vsatual.setText("v0.0.0");
        L_vsatual.setToolTipText("Versão Atual do Projeto");

        comb_opc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cadastrar", "Atualizar", "Buscar", "Deletar" }));
        comb_opc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comb_opcActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 272, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jbuttonArr3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(201, 201, 201))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_del, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(comb_opc, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(315, 315, 315)
                        .addComponent(L_vsatual)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comb_opc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(L_vsatual))
                        .addGap(103, 103, 103)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbuttonArr3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_del, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void realesed() {
        label_info10.setText("-" + textf_additem10.getText());
        label_info09.setText("-" + textf_additem09.getText());
        label_info08.setText("-" + textf_additem08.getText());
        label_info07.setText("-" + textf_additem07.getText());
        label_info06.setText("-" + textf_additem06.getText());
        label_info05.setText("-" + textf_additem05.getText());
        label_info04.setText("-" + textf_additem04.getText());
        label_info03.setText("-" + textf_additem03.getText());
        label_info02.setText("-" + textf_additem02.getText());
        label_info01.setText("-" + textf_additem01.getText());
    }

    public void opc() {
        String selectedOption = (String) comb_opc.getSelectedItem();
        if (null != selectedOption) {
            switch (selectedOption) {
                case "Buscar":
                    Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png")));
                    break;
                case "Deletar":
                    Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png")));
                    break;
                case "Cadastrar":
                    Lbuscar.setIcon(null);
                    break;
                case "Atualizar":
                    Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png")));
                    break;
                default:
                    break;
            }
        }
    }

    public void cad() {

        if (admin == null || admin.equals("")) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Você deve estar logado";
            String msg2 = "Para Efetuar isso";
            String tit = "Não Logado";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");

            login lg = new login(audio);
            lg.setVisible(true);
            dispose();
        } else {
            String selectedOption = (String) comb_opc.getSelectedItem();
            if ("Cadastrar".equals(selectedOption)) {
                cadastro();
            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Selecione a operação";
                String msg1 = "de Cadastrar";
                String tit = "Cadastro de Versão";
                al.alertinput(tit, "erro", msg, msg1, "", "erro");
            }
        }

    }

    public void att() {
        if (permchefe == true || selcod.equals(admin)) {
            if ("nulo".equals(admin) || !"nulo".equals(selcod)) {
                if ("null".equals(admin) || !"null".equals(selcod)) {
                    if (admin == null || admin.equals("")) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Você deve estar logado";
                        String msg2 = "Para Efetuar isso";
                        String tit = "Não Logado";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        login lg = new login(audio);
                        lg.setVisible(true);
                        dispose();
                    } else {

                        String selectedOption = (String) comb_opc.getSelectedItem();
                        if ("Atualizar".equals(selectedOption)) {
                            atualizar();
                        } else {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Selecione a operação";
                            String msg1 = "de Atualizar";
                            String tit = "Atulização de Versão";
                            al.alertinput(tit, "erro", msg, msg1, "", "erro");
                        }
                    }
                }
            }
        } else {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg2 = "Você não tem ";
            String msg3 = "Permissão suficiente";
            String tit = "Sem Permissão";
            al.alertinput(tit, "erro", msg2, msg3, "", "erro");
        }
    }

    public void del() {
        if (admin == null || admin.equals("")) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Você deve estar logado";
            String msg2 = "Para Efetuar isso";
            String tit = "Não Logado";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");

            login lg = new login(audio);
            lg.setVisible(true);
            dispose();
        } else {

            if (permchefe == true) {
                String selectedOption = (String) comb_opc.getSelectedItem();
                if ("Deletar".equals(selectedOption)) {
                    delet();
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Selecione a operação";
                    String msg1 = "de Deletar";
                    String tit = "Deletar de Versão";
                    al.alertinput(tit, "erro", msg, msg1, "", "erro");
                }
            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg2 = "Você não tem ";
                String msg3 = "Permissão suficiente";
                String tit = "Sem Permissão";
                al.alertinput(tit, "erro", msg2, msg3, "", "erro");

            }
        }
    }

    private void textf_additem01KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_additem01KeyReleased
        realesed();
        tamanho();
    }//GEN-LAST:event_textf_additem01KeyReleased

    private void jbuttonArr3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonArr3ActionPerformed
        versao vs = new versao();
        vs.setVisible(true);
    }//GEN-LAST:event_jbuttonArr3ActionPerformed

    private void textf_additem02KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_additem02KeyReleased
        realesed();
        tamanho();
    }//GEN-LAST:event_textf_additem02KeyReleased

    private void textf_additem03KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_additem03KeyReleased
        realesed();
        tamanho();
    }//GEN-LAST:event_textf_additem03KeyReleased

    private void textf_additem04KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_additem04KeyReleased
        realesed();
        tamanho();
    }//GEN-LAST:event_textf_additem04KeyReleased

    private void textf_additem05KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_additem05KeyReleased
        realesed();
        tamanho();
    }//GEN-LAST:event_textf_additem05KeyReleased

    private void textf_additem06KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_additem06KeyReleased
        realesed();
        tamanho();
    }//GEN-LAST:event_textf_additem06KeyReleased

    private void textf_additem07KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_additem07KeyReleased
        realesed();
        tamanho();
    }//GEN-LAST:event_textf_additem07KeyReleased

    private void textf_additem08KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_additem08KeyReleased
        realesed();
        tamanho();
    }//GEN-LAST:event_textf_additem08KeyReleased

    private void textf_additem09KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_additem09KeyReleased
        realesed();
        tamanho();
    }//GEN-LAST:event_textf_additem09KeyReleased

    private void textf_additem10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_additem10KeyReleased
        realesed();
        tamanho();
    }//GEN-LAST:event_textf_additem10KeyReleased

    private void cb_vActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_vActionPerformed

    }//GEN-LAST:event_cb_vActionPerformed

    private void textf_nmKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nmKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_nmKeyReleased

    private void btn_atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizarActionPerformed
        att();
    }//GEN-LAST:event_btn_atualizarActionPerformed

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Limpar todos os campos?");
        if (escolha == 0) {
            limpar();

        }
    }//GEN-LAST:event_btn_limparActionPerformed

    private void btn_delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delActionPerformed
        del();
    }//GEN-LAST:event_btn_delActionPerformed

    private void LbuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LbuscarMouseClicked
        String selectedOption = (String) comb_opc.getSelectedItem();
        if (!"Cadastro".equals(selectedOption)) {
            buscar();

        } else {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Selecione a operação";
            String msg1 = "Correta";
            String tit = "Buscar Versão";
            al.alertinput(tit, "erro", msg, msg1, "", "erro");
        }
    }//GEN-LAST:event_LbuscarMouseClicked

    private void textf_vsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_vsKeyReleased
        cb_v.removeAllItems();
        cb_v.addItem(textf_vs.getText());
    }//GEN-LAST:event_textf_vsKeyReleased

    private void comb_opcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comb_opcActionPerformed
        opc();
    }//GEN-LAST:event_comb_opcActionPerformed

    private void textf_additem03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textf_additem03ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textf_additem03ActionPerformed

    private void textf_additem03KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_additem03KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_textf_additem03KeyPressed

    private void btn_cadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadastrarActionPerformed
        cad();
    }//GEN-LAST:event_btn_cadastrarActionPerformed

    private void textf_vsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_vsKeyPressed
        String selectedOption = (String) comb_opc.getSelectedItem();
        if (!"Cadastro".equals(selectedOption)) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                buscar();
            }

        } else {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Selecione a operação";
            String msg1 = "Correta";
            String tit = "Buscar Versão";
            al.alertinput(tit, "erro", msg, msg1, "", "erro");
        }
    }//GEN-LAST:event_textf_vsKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel L_desc01;
    private javax.swing.JLabel L_desc02;
    private javax.swing.JLabel L_desc03;
    private javax.swing.JLabel L_desc04;
    private javax.swing.JLabel L_desc05;
    private javax.swing.JLabel L_desc06;
    private javax.swing.JLabel L_desc07;
    private javax.swing.JLabel L_desc08;
    private javax.swing.JLabel L_desc09;
    private javax.swing.JLabel L_desc10;
    private javax.swing.JLabel L_nm;
    private javax.swing.JLabel L_nv;
    private javax.swing.JLabel L_nv1;
    private javax.swing.JLabel L_nv2;
    private javax.swing.JLabel L_tot;
    private javax.swing.JLabel L_vsatual;
    private javax.swing.JLabel Lbuscar;
    private SwingPerson.JbuttonArr btn_atualizar;
    private SwingPerson.JbuttonArr btn_cadastrar;
    private SwingPerson.JbuttonArr btn_del;
    private SwingPerson.JbuttonArr btn_limpar;
    private javax.swing.JComboBox<String> cb_v;
    private javax.swing.JComboBox<String> comb_opc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private SwingPerson.JbuttonArr jbuttonArr3;
    private javax.swing.JLabel label_info01;
    private javax.swing.JLabel label_info02;
    private javax.swing.JLabel label_info03;
    private javax.swing.JLabel label_info04;
    private javax.swing.JLabel label_info05;
    private javax.swing.JLabel label_info06;
    private javax.swing.JLabel label_info07;
    private javax.swing.JLabel label_info08;
    private javax.swing.JLabel label_info09;
    private javax.swing.JLabel label_info10;
    private javax.swing.JTextField textf_additem01;
    private javax.swing.JTextField textf_additem02;
    private javax.swing.JTextField textf_additem03;
    private javax.swing.JTextField textf_additem04;
    private javax.swing.JTextField textf_additem05;
    private javax.swing.JTextField textf_additem06;
    private javax.swing.JTextField textf_additem07;
    private javax.swing.JTextField textf_additem08;
    private javax.swing.JTextField textf_additem09;
    private javax.swing.JTextField textf_additem10;
    private javax.swing.JTextField textf_nm;
    private javax.swing.JFormattedTextField textf_vs;
    // End of variables declaration//GEN-END:variables
}
