package subGUI;

import GUI.TelaPrin;
import Logar.versao;
import alert.alert;
import factory.ConnectionFactory;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import starter.Icone;

public class AdminAtualizar extends javax.swing.JFrame {

    private Connection connection;
    private int tamanho;
    public int tipo, tipo2;
    public String admin, ts, audio, selnick;
    Boolean maxc;
    //instanciar objeto para fluxo de bytes.
    private FileInputStream fis;
    byte[] imageBytes;
    Boolean permchefe = false;
    private TelaPrin telaPrin;

    // variável global para armazenar tamanho da imagem em bytes.
    public AdminAtualizar(String adm, String au, String user, TelaPrin telaPrin) {
        initComponents();
        admin = adm;
        audio = au;
        setIcon();
        Status();
        this.telaPrin=telaPrin;
        selnick = user;
        if (selnick != "" || selnick != null) {
            textf_userBuscar.setText(selnick);
            buscarAdmin(selnick);
            bus(admin);
            tamanho();
        }

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
       
        if (admin != null || !"".equals(admin)) {
            if (permchefe == true && !"nulo".equals(selnick) && !"null".equals(selnick)) {
                textf_cod.setEnabled(true);
                textf_cod.setEditable(true);
                textf_cod.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
                textf_cod.setToolTipText("");
            } else {
                textf_cod.setEnabled(false);
                textf_cod.setEditable(false);
                textf_cod.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                textf_cod.setToolTipText("Sem Permissão para Modificar código de Admin");
            }
        }
    }

    public void setIcon() {
        Icone ic = new Icone();
        String cm = ic.getIcon();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(cm)));
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

    private void Status() {

        switch (audio) {
            case "off":

                Rdsta.setSelected(true);
                break;
            case "on":

                Ratva.setSelected(true);
                break;
        }
    }

    public void limpar() {
        textf_userBuscar.setText("");
        textf_user.setText("");
        textf_nome.setText("");
        pass_senha.setText("");
        pass_senhaconfirm.setText("");
        label_foto.setText("");
        label_foto.setIcon(null);
        tamanho();
        textf_cod.setText("");

    }

    private void CarregarFoto() {

        // vai ser responsável por carregar foto do computador local para a interface java.
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Selecionar Arquivo Desejado");
        jfc.setFileFilter(new FileNameExtensionFilter("Arquivos de Imagens(*.PNG,"
                + "*.JPG, *.JPEG)", "png", "jpg", "jpeg"));

        int resultado = jfc.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                fis = new FileInputStream(jfc.getSelectedFile());
                tamanho = (int) jfc.getSelectedFile().length();
                Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(label_foto.getWidth(),
                        label_foto.getHeight(), Image.SCALE_SMOOTH);
                label_foto.setIcon(new ImageIcon(foto));
                label_foto.updateUI();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void TirarFoto() {
        audios("cl");
        ts = "sim";
        label_foto.setIcon(null);
        label_foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/semimg.png")));
    }

    public String getCod(String user) {

        this.connection = new ConnectionFactory().getConnection();
        String sql = "";
        String cod = "";
        sql = "select * from admin where usera= '" + user + "'";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            if (res.next()) {

                String tx1 = (res.getString(4));

                cod = tx1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cod;
    }

    public Boolean maxperm() {
        maxc = !(textf_user.getText().length() <= 25 && textf_nome.getText().length() <= 25 && pass_senha.getPassword().length <= 25 && pass_senhaconfirm.getPassword().length <= 25 && textf_cod.getText().length() <= 25);
        return maxc;
    }

    public String verificar(String user) {
        String status = "";
        Boolean perm = maxperm();
        if (perm == false) {
            this.connection = new ConnectionFactory().getConnection();
            PreparedStatement ps;

            try {

                ps = connection.prepareStatement("select * from admin where usera=?");
                ps.setString(1, user);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    if (tipo == 1) {

                        String cod = getCod(user);

                        String usern = textf_user.getText();
                        if ((textf_cod.getText().isEmpty()) && (textf_user.getText().isEmpty()) || (pass_senha.getText().isEmpty()) || (textf_nome.getText().isEmpty()) || (pass_senhaconfirm.getText().isEmpty())) {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Os campos não podem";
                            String msg2 = "retornar vazios";
                            String tit = "Campo(s) de Texto Vazio";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        } else {
                            ps = connection.prepareStatement("SELECT * FROM admin where usera=? AND admcod <> ?");
                            ps.setString(1, usern);
                            ps.setString(2, cod);

                            rs = ps.executeQuery();
                            if (rs.next()) {
                                alert al = new alert(admin, audio);
                                al.setVisible(true);
                                String msg = "User do Admin já existe";
                                String tit = "Admin existente";
                                al.alertinput(tit, "erro", "", msg, "", "erro");

                            } else {
                                String cd = textf_cod.getText();
                                ps = connection.prepareStatement("SELECT * FROM admin where admcod=? AND usera <> ?");
                                ps.setString(1, cd);
                                ps.setString(2, user);

                                rs = ps.executeQuery();
                                if (rs.next()) {
                                    alert al = new alert(admin, audio);
                                    al.setVisible(true);
                                    String msg = "Código do Admin já existe";
                                    String tit = "Código existente";
                                    al.alertinput(tit, "erro", "", msg, "", "erro");
                                } else {

                                    if (pass_senha.getText().equals(pass_senhaconfirm.getText())) {

                                        // instanciando a classe UsuarioDAO do pacote dao e criando seu objeto dao
                                        String User = textf_userBuscar.getText();
                                        String senha = pass_senha.getText();
                                        String nome = textf_nome.getText();
                                        usern = textf_user.getText();
                                        String cs = textf_cod.getText();
                                        atualizarAdmin(User, senha, nome, usern, cs, cod);
                                        limpar();
                                        fis = null;
                                        ts = null;

                                    } else {
                                        alert al = new alert(admin, audio);
                                        al.setVisible(true);
                                        String msg = "As senhas não estão iguais";
                                        String tit = "Senhas desiquais";
                                        al.alertinput(tit, "erro", "", msg, "", "erro");
                                    }
                                }
                            }
                        }
                    } else {

                        alert al = new alert(admin, audio);
                        al.audios("ok");

                        buscarAdmin(user);

                    }
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "User do Admin não existe";
                    String tit = "Admin inexistente";
                    al.alertinput(tit, "erro", "", msg, "", "erro");
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        return status;

    }

    public void buscarAdmin(String user) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM admin WHERE usera = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                String tx1 = res.getString(1);
                String tx2 = res.getString(2);
                String tx3 = res.getString(3);
                imageBytes = res.getBytes(5);
                String tx4 = res.getString(4);

                textf_cod.setText(tx4);
                textf_user.setText(tx1);
                pass_senha.setText(tx2);
                pass_senhaconfirm.setText(tx2);
                textf_nome.setText(tx3);

                // Verifica se há imagem
                if (imageBytes != null && imageBytes.length > 0) {
                    ImageIcon imageIcon = new ImageIcon(imageBytes);
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(label_foto.getWidth(), label_foto.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);

                    if (label_foto != null) {
                        label_foto.setIcon(scaledIcon);
                    } else {
                        label_foto.setText("Sem foto");
                    }
                } else {
                    label_foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/semimg.png")));
                }
            } else {

                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Informação Trazida";
                String msg2 = "Incorreta Ou Desatulizada.";
                String msg3 = "Atualize o Banco de Dados.";
                String tit = "Busca de Admin";
                al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                al.setAlwaysOnTop(true);
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String atualizarAdmin(String user, String senha, String nome, String usern, String cod, String codp) {
        this.connection = new ConnectionFactory().getConnection();
        String status = "";
        if (cod == null ? codp == null : cod.equals(codp)) {
            //Connection con;
            PreparedStatement ps;
            try {

                ps = connection.prepareStatement("update admin set usera=?, senhaa=?, nomea=?,imgadm=?,admcod=? where usera=?");

                ps.setString(1, usern);
                ps.setString(2, senha);
                ps.setString(3, nome);
                ps.setString(5, cod);
                ps.setString(6, user);

                if (fis == null && ts == null && imageBytes != null) {
                    ps.setBlob(4, new ByteArrayInputStream(imageBytes), imageBytes.length);

                } else {
                    ps.setBlob(4, fis, tamanho);
                }

                int i = ps.executeUpdate();
                if (i != 0) {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Admin";
                    String msg2 = "atualizados com sucesso";
                    String tit = "Atualização de Admin";
                    al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                    al.setAlwaysOnTop(true);
                    if (admin.equals(selnick) && !usern.equals(user) || fis!=null) {
                        dispose(); // Fecha a AdminAtualizar
                        telaPrin.dispose();
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                TelaPrin aa = new TelaPrin(usern, audio);
                                aa.setVisible(true);
                            }
                        });
                    }

                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Admin";
                    String msg2 = "não atualizados";
                    String tit = "Atualização de Admin";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                // Verificar se há dados atrelados ao admin na tabela pet
                PreparedStatement petStatement = connection.prepareStatement("SELECT petcod FROM pet WHERE admincodadmn = ?");
                petStatement.setString(1, codp);
                ResultSet petResult = petStatement.executeQuery();
                if (petResult.next()) {
                    // Existem dados atrelados ao admin na tabela pet, então substitua a chave por um valor nulo
                    PreparedStatement nullPetStatement = connection.prepareStatement("UPDATE pet SET admincodadmn = '1' WHERE admincodadmn  = ?;");
                    nullPetStatement.setString(1, codp);
                    nullPetStatement.executeUpdate();
                }

                // Verificar se há dados atrelados ao admin na tabela servico
                PreparedStatement servicoStatement = connection.prepareStatement("SELECT servcod FROM servico WHERE admin_codadmn = ?");
                servicoStatement.setString(1, codp);
                ResultSet servicoResult = servicoStatement.executeQuery();
                if (servicoResult.next()) {
                    // Existem dados atrelados ao admin na tabela servico, então substitua a chave por um valor nulo
                    PreparedStatement nullServicoStatement = connection.prepareStatement("UPDATE servico SET admin_codadmn = '1' WHERE admin_codadmn  = ?");
                    nullServicoStatement.setString(1, codp);
                    nullServicoStatement.executeUpdate();
                }

                PreparedStatement ps;
                try {

                    ps = connection.prepareStatement("update admin set usera=?, senhaa=?, nomea=?,imgadm=?,admcod=? where usera=?");

                    ps.setString(1, usern);
                    ps.setString(2, senha);
                    ps.setString(3, nome);
                    ps.setString(5, cod);
                    ps.setString(6, user);

                    if (fis == null && ts == null && imageBytes != null) {
                        ps.setBlob(4, new ByteArrayInputStream(imageBytes), imageBytes.length);

                    } else {
                        ps.setBlob(4, fis, tamanho);
                    }

                    int i = ps.executeUpdate();
                    if (i != 0) {
                        // Verificar se há dados atrelados ao admin na tabela pet
                        petStatement = connection.prepareStatement("SELECT petcod FROM pet WHERE admincodadmn = '1'");
                        petResult = petStatement.executeQuery();
                        if (petResult.next()) {
                            // Existem dados atrelados ao admin na tabela pet, então substitua a chave por um valor nulo
                            PreparedStatement nullPetStatement = connection.prepareStatement("UPDATE pet SET admincodadmn = ? WHERE admincodadmn='1';");
                            nullPetStatement.setString(1, cod);
                            nullPetStatement.executeUpdate();
                        }

                        // Verificar se há dados atrelados ao admin na tabela servico
                        servicoStatement = connection.prepareStatement("SELECT servcod FROM servico WHERE admin_codadmn ='1';");
                        servicoResult = servicoStatement.executeQuery();
                        if (servicoResult.next()) {
                            // Existem dados atrelados ao admin na tabela servico, então substitua a chave por um valor nulo
                            PreparedStatement nullServicoStatement = connection.prepareStatement("UPDATE servico SET admin_codadmn = ? WHERE admin_codadmn='1';");
                            nullServicoStatement.setString(1, cod);
                            nullServicoStatement.executeUpdate();
                        }

                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Admin";
                        String msg2 = "atualizados com sucesso";
                        String tit = "Atualização de Admin";
                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                        dispose();
                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Admin";
                        String msg2 = "não atualizados";
                        String tit = "Atualização de Admin";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {

                // Outro erro SQL ocorreu, imprima o stack trace para depuração
                e.printStackTrace();

            }
        }
        return status;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BGaudio = new javax.swing.ButtonGroup();
        PFundo = new javax.swing.JPanel();
        Plogin = new javax.swing.JPanel();
        pass_senhaconfirm = new javax.swing.JPasswordField();
        textf_nome = new javax.swing.JTextField();
        label_nome = new javax.swing.JLabel();
        label_senhaconfirm = new javax.swing.JLabel();
        btn_atualizar = new SwingPerson.JbuttonArr();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        L_caracternm = new javax.swing.JLabel();
        L_caractercse = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textf_user = new javax.swing.JTextField();
        label_user = new javax.swing.JLabel();
        L_caracterus = new javax.swing.JLabel();
        label_senha = new javax.swing.JLabel();
        pass_senha = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        L_caracterse = new javax.swing.JLabel();
        btn_limpar = new SwingPerson.JbuttonArr();
        jLabel9 = new javax.swing.JLabel();
        L_caractercod = new javax.swing.JLabel();
        label_cod = new javax.swing.JLabel();
        textf_cod = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        label_foto = new javax.swing.JLabel();
        btnCARREGAR = new SwingPerson.JbuttonArr();
        BtnSemimg = new SwingPerson.JbuttonArr();
        L_vlt = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        label_userBuscar = new javax.swing.JLabel();
        textf_userBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btn_att = new javax.swing.JButton();
        Lbuscar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar3 = new javax.swing.JMenuBar();
        Mopc = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        menu_bus = new javax.swing.JMenuItem();
        menu_atu = new javax.swing.JMenuItem();
        menu_cf = new javax.swing.JMenuItem();
        menu_tf = new javax.swing.JMenuItem();
        menu_cad2 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menu_voz = new javax.swing.JMenuItem();
        menu_texto = new javax.swing.JMenuItem();
        menu_sobre = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        Ratva = new javax.swing.JRadioButtonMenuItem();
        Rdsta = new javax.swing.JRadioButtonMenuItem();
        menu_voltar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pet Found - Atualizar Admin");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PFundo.setBackground(new java.awt.Color(64, 33, 7));
        PFundo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PFundo.setPreferredSize(new java.awt.Dimension(776, 370));

        Plogin.setBackground(new java.awt.Color(255, 253, 243));
        Plogin.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Plogin.setPreferredSize(new java.awt.Dimension(546, 366));

        pass_senhaconfirm.setBackground(new java.awt.Color(255, 253, 243));
        pass_senhaconfirm.setBorder(null);
        pass_senhaconfirm.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pass_senhaconfirm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pass_senhaconfirmKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pass_senhaconfirmKeyReleased(evt);
            }
        });

        textf_nome.setBackground(new java.awt.Color(255, 253, 243));
        textf_nome.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_nome.setBorder(null);
        textf_nome.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_nome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_nomeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_nomeKeyReleased(evt);
            }
        });

        label_nome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_nome.setText("NOME");

        label_senhaconfirm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_senhaconfirm.setText("CONFIRMAR SENHA");

        btn_atualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/atualizar.png"))); // NOI18N
        btn_atualizar.setToolTipText("Atualizar Informações de Admin");
        btn_atualizar.setMaximumSize(new java.awt.Dimension(0, 0));
        btn_atualizar.setMinimumSize(new java.awt.Dimension(60, 60));
        btn_atualizar.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizarActionPerformed(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caracternm.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracternm.setText("0");

        L_caractercse.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercse.setText("0");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(363, 3));

        textf_user.setBackground(new java.awt.Color(255, 253, 243));
        textf_user.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_user.setBorder(null);
        textf_user.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_user.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_userKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_userKeyReleased(evt);
            }
        });

        label_user.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_user.setText("USER");

        L_caracterus.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterus.setText("0");

        label_senha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_senha.setText("SENHA");

        pass_senha.setBackground(new java.awt.Color(255, 253, 243));
        pass_senha.setBorder(null);
        pass_senha.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pass_senha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pass_senhaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pass_senhaKeyReleased(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caracterse.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterse.setText("0");

        btn_limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/apagar.png"))); // NOI18N
        btn_limpar.setToolTipText("Limpar Todos os Campos");
        btn_limpar.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparActionPerformed(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caractercod.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercod.setText("0");

        label_cod.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_cod.setText("CÓDIGO");

        textf_cod.setEditable(false);
        textf_cod.setBackground(new java.awt.Color(255, 253, 243));
        textf_cod.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_cod.setToolTipText("");
        textf_cod.setBorder(null);
        textf_cod.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_cod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_codKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_codKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout PloginLayout = new javax.swing.GroupLayout(Plogin);
        Plogin.setLayout(PloginLayout);
        PloginLayout.setHorizontalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PloginLayout.createSequentialGroup()
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addComponent(L_caracterus))
                        .addGroup(PloginLayout.createSequentialGroup()
                            .addGap(296, 296, 296)
                            .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(L_caractercse))
                                .addComponent(textf_user, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_senhaconfirm)
                                .addComponent(pass_senhaconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_user))))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_nome)
                            .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(L_caracternm)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label_senha)
                            .addComponent(pass_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(L_caracterse)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_cod)
                                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(L_caractercod)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textf_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(0, 46, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PloginLayout.setVerticalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PloginLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_user)
                    .addComponent(label_nome))
                .addGap(7, 7, 7)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addComponent(textf_user, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addComponent(textf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(L_caracterus)
                    .addComponent(L_caracternm))
                .addGap(18, 18, 18)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_senhaconfirm)
                    .addComponent(label_senha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addComponent(pass_senhaconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addComponent(pass_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(L_caractercse)
                    .addComponent(L_caracterse))
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_cod)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(L_caractercod)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        label_foto.setForeground(new java.awt.Color(255, 255, 255));
        label_foto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(label_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnCARREGAR.setText("Carregar");
        btnCARREGAR.setToolTipText("Carregar Uma Imagem");
        btnCARREGAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCARREGARActionPerformed(evt);
            }
        });

        BtnSemimg.setText("Remover");
        BtnSemimg.setToolTipText("Remover imagem do Admin");
        BtnSemimg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSemimgActionPerformed(evt);
            }
        });

        L_vlt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_voltarbr.png"))); // NOI18N
        L_vlt.setToolTipText("Sair de Atualizar");
        L_vlt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        L_vlt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                L_vltMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 253, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        label_userBuscar.setBackground(new java.awt.Color(51, 51, 51));
        label_userBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_userBuscar.setForeground(new java.awt.Color(51, 51, 51));
        label_userBuscar.setText("USER");

        textf_userBuscar.setBackground(new java.awt.Color(255, 253, 243));
        textf_userBuscar.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_userBuscar.setToolTipText("");
        textf_userBuscar.setBorder(null);
        textf_userBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_userBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textf_userBuscarMouseClicked(evt);
            }
        });
        textf_userBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_userBuscarKeyPressed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(363, 3));

        btn_att.setBackground(new java.awt.Color(255, 253, 243));
        btn_att.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_att.setForeground(new java.awt.Color(51, 51, 51));
        btn_att.setText("Não sei o User");
        btn_att.setToolTipText("<html>Sair da Tela E buscar<br>\nO user do Admin</html>");
        btn_att.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_att.setPreferredSize(new java.awt.Dimension(146, 22));
        btn_att.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_attActionPerformed(evt);
            }
        });

        Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        Lbuscar.setToolTipText("Buscar Admin");
        Lbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Lbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LbuscarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label_userBuscar)
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textf_userBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addComponent(Lbuscar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_userBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textf_userBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lbuscar))
                .addGap(5, 5, 5)
                .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Atualizar Admin");

        javax.swing.GroupLayout PFundoLayout = new javax.swing.GroupLayout(PFundo);
        PFundo.setLayout(PFundoLayout);
        PFundoLayout.setHorizontalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PFundoLayout.createSequentialGroup()
                            .addComponent(btnCARREGAR, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BtnSemimg, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(L_vlt)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Plogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 7, Short.MAX_VALUE))
        );
        PFundoLayout.setVerticalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(25, 25, 25)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCARREGAR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnSemimg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(L_vlt)
                .addContainerGap())
            .addGroup(PFundoLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Plogin, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
        );

        getContentPane().add(PFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jMenuBar3.setBackground(new java.awt.Color(255, 253, 243));
        jMenuBar3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuBar3.setMinimumSize(new java.awt.Dimension(210, 20));
        jMenuBar3.setPreferredSize(new java.awt.Dimension(210, 35));

        Mopc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/option.png"))); // NOI18N
        Mopc.setToolTipText("Aba de Opções");
        Mopc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenu4.setText("Comandos Rápidos");

        menu_bus.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_bus.setText("Atualizar");
        menu_bus.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_bus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_busActionPerformed(evt);
            }
        });
        jMenu4.add(menu_bus);

        menu_atu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_atu.setText("Buscar");
        menu_atu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_atu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_atuActionPerformed(evt);
            }
        });
        jMenu4.add(menu_atu);

        menu_cf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_cf.setText("Carregar Foto");
        menu_cf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_cf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_cfActionPerformed(evt);
            }
        });
        jMenu4.add(menu_cf);

        menu_tf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_tf.setText("Tirar Foto");
        menu_tf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_tfActionPerformed(evt);
            }
        });
        jMenu4.add(menu_tf);

        menu_cad2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_cad2.setText("Limpar");
        menu_cad2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_cad2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_cad2ActionPerformed(evt);
            }
        });
        jMenu4.add(menu_cad2);

        Mopc.add(jMenu4);

        jMenu5.setText("Acessibilidade");

        menu_voz.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_voz.setText("Ativar comando por voz");
        menu_voz.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_voz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_vozActionPerformed(evt);
            }
        });
        jMenu5.add(menu_voz);

        menu_texto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_texto.setText("Ativar leitura de texto");
        menu_texto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_texto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_textoActionPerformed(evt);
            }
        });
        jMenu5.add(menu_texto);

        Mopc.add(jMenu5);

        menu_sobre.setText("Sobre");
        menu_sobre.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jMenuItem1.setText("Versão");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menu_sobre.add(jMenuItem1);

        Mopc.add(menu_sobre);

        jMenu3.setText("Opções");
        jMenu3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jMenu6.setText("Audio");
        jMenu6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Ratva.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        BGaudio.add(Ratva);
        Ratva.setSelected(true);
        Ratva.setText("Ativar Audio");
        Ratva.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Ratva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RatvaActionPerformed(evt);
            }
        });
        jMenu6.add(Ratva);

        Rdsta.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        BGaudio.add(Rdsta);
        Rdsta.setText("Desativar Audio");
        Rdsta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Rdsta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RdstaActionPerformed(evt);
            }
        });
        jMenu6.add(Rdsta);

        jMenu3.add(jMenu6);

        menu_voltar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        menu_voltar.setText("Voltar");
        menu_voltar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_voltarActionPerformed(evt);
            }
        });
        jMenu3.add(menu_voltar);

        Mopc.add(jMenu3);

        jMenuBar3.add(Mopc);

        setJMenuBar(jMenuBar3);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    public void att() {

        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo atulizar o Admin " + textf_userBuscar.getText() + "?\nUma vez atulizado, a informação irá mudar para sempre.");
        if (escolha == 0) {
            String cod = textf_userBuscar.getText();
            tipo = 1;
            verificar(cod);
        }
    }

    public void bus() {
        String user = textf_userBuscar.getText();
        tipo = 2;
        verificar(user);
    }

    public void vlt() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja sair?");
        if (escolha == 0) {
            audios("tc");
            //     AdminBuscar admn = new AdminBuscar(admin, audio);
            //     admn.setVisible(true);
            dispose();
        }
    }

    public void lmp() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Limpar todos os campos?");
        if (escolha == 0) {
            limpar();

        }
    }

    public void inm() {
        alert al = new alert(admin, audio);
        al.setVisible(true);
        String msg = "Indisponível no momento";
        String tit = "Página em manutenção";
        al.alertinput(tit, "info", "", msg, "", "info");
    }

    public void tamanho() {
        //Nome
        String inputText = textf_nome.getText(); // Obtém o texto do campo de texto
        int numCaracteres = inputText.length();
        int maxperm = 50;
        L_caracternm.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracternm.setForeground(new Color(255, 51, 51));
            L_caracternm.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracternm.setForeground(new Color(0, 0, 0));
            L_caracternm.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        //User
        inputText = textf_user.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 25;
        L_caracterus.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracterus.setForeground(new Color(255, 51, 51));
            L_caracterus.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracterus.setForeground(new Color(0, 0, 0));
            L_caracterus.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        //Senha
        inputText = pass_senha.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 25;
        L_caracterse.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracterse.setForeground(new Color(255, 51, 51));
            L_caracterse.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracterse.setForeground(new Color(0, 0, 0));
            L_caracterse.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        //senha confirma
        inputText = pass_senhaconfirm.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 25;
        L_caractercse.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caractercse.setForeground(new Color(255, 51, 51));
            L_caractercse.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caractercse.setForeground(new Color(0, 0, 0));
            L_caractercse.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        //Código
        inputText = textf_cod.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 25;
        L_caractercod.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caractercod.setForeground(new Color(255, 51, 51));
            L_caractercod.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caractercod.setForeground(new Color(0, 0, 0));
            L_caractercod.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }
    }

    private void textf_userBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_userBuscarMouseClicked
        limpar();
    }//GEN-LAST:event_textf_userBuscarMouseClicked

    private void textf_userBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_userBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            bus();
        }
    }//GEN-LAST:event_textf_userBuscarKeyPressed

    private void textf_userKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_userKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_userKeyPressed

    private void pass_senhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_pass_senhaKeyPressed

    private void textf_nomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_nomeKeyPressed

    private void pass_senhaconfirmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaconfirmKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_pass_senhaconfirmKeyPressed

    private void menu_cad2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cad2ActionPerformed
        lmp();
    }//GEN-LAST:event_menu_cad2ActionPerformed

    private void menu_atuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_atuActionPerformed
        bus();
    }//GEN-LAST:event_menu_atuActionPerformed

    private void menu_busActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_busActionPerformed
        att();
    }//GEN-LAST:event_menu_busActionPerformed

    private void menu_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_voltarActionPerformed
        vlt();
    }//GEN-LAST:event_menu_voltarActionPerformed

    private void menu_vozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_vozActionPerformed
        inm();
    }//GEN-LAST:event_menu_vozActionPerformed

    private void menu_textoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_textoActionPerformed
        inm();
    }//GEN-LAST:event_menu_textoActionPerformed

    private void menu_cfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cfActionPerformed
        audios("cl");
        CarregarFoto();
    }//GEN-LAST:event_menu_cfActionPerformed

    private void menu_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tfActionPerformed
        TirarFoto();
    }//GEN-LAST:event_menu_tfActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        audios("tci");
        versao v = new versao();
        v.buscarVersao(); // Buscar versões antes de exibir a janela
        v.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void RatvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RatvaActionPerformed
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        if (mixers.length == 0) {
            audio = "off";
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Nenhum dispositivo de";
            String msg2 = "saída de áudio encontrado";
            String tit = "Áudio indisponível";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");

        } else {

            audio = "on";
            for (Mixer.Info mixerInfo : mixers) {
                System.out.println(mixerInfo);
            }
        }
        Status();
        Rdsta.setSelected(true);
    }//GEN-LAST:event_RatvaActionPerformed

    private void RdstaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RdstaActionPerformed
        audio = "off";
        Status();
        Rdsta.setSelected(true);
    }//GEN-LAST:event_RdstaActionPerformed

    private void btn_attActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_attActionPerformed
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja sair para a tela de Buscar Administrador?");
        if (escolha == 0) {
            audios("tc");
            // AdminBuscar pf = new AdminBuscar(admin, audio);
            //pf.setVisible(true);
            dispose();
        }

    }//GEN-LAST:event_btn_attActionPerformed

    private void btnCARREGARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCARREGARActionPerformed
        audios("cl");
        CarregarFoto();
    }//GEN-LAST:event_btnCARREGARActionPerformed

    private void BtnSemimgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSemimgActionPerformed
        TirarFoto();
    }//GEN-LAST:event_BtnSemimgActionPerformed

    private void btn_atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizarActionPerformed
        att();
    }//GEN-LAST:event_btn_atualizarActionPerformed

    private void L_vltMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_vltMouseClicked
        vlt();
    }//GEN-LAST:event_L_vltMouseClicked

    private void LbuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LbuscarMouseClicked
        bus();
    }//GEN-LAST:event_LbuscarMouseClicked

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        audios("cl");
        lmp();
    }//GEN-LAST:event_btn_limparActionPerformed

    private void textf_nomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nomeKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_nomeKeyReleased

    private void textf_userKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_userKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_userKeyReleased

    private void pass_senhaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaKeyReleased
        tamanho();
    }//GEN-LAST:event_pass_senhaKeyReleased

    private void pass_senhaconfirmKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaconfirmKeyReleased
        tamanho();
    }//GEN-LAST:event_pass_senhaconfirmKeyReleased

    private void textf_codKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_codKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_codKeyPressed

    private void textf_codKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_codKeyReleased
        String inputText = textf_cod.getText(); // Obtém o texto do campo de texto
        int numCaracteres = inputText.length();
        int maxperm = 25;
        L_caractercod.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caractercod.setForeground(new Color(255, 51, 51));
            L_caractercod.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caractercod.setForeground(new Color(0, 0, 0));
            L_caractercod.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }
    }//GEN-LAST:event_textf_codKeyReleased

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String ad = "";
                String au = "off";
                String sel = "";
                TelaPrin tp = null;
                new AdminAtualizar(ad, au, sel, tp).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGaudio;
    private SwingPerson.JbuttonArr BtnSemimg;
    private javax.swing.JLabel L_caractercod;
    private javax.swing.JLabel L_caractercse;
    private javax.swing.JLabel L_caracternm;
    private javax.swing.JLabel L_caracterse;
    private javax.swing.JLabel L_caracterus;
    private javax.swing.JLabel L_vlt;
    private javax.swing.JLabel Lbuscar;
    private javax.swing.JMenu Mopc;
    private javax.swing.JPanel PFundo;
    private javax.swing.JPanel Plogin;
    private javax.swing.JRadioButtonMenuItem Ratva;
    private javax.swing.JRadioButtonMenuItem Rdsta;
    private SwingPerson.JbuttonArr btnCARREGAR;
    private javax.swing.JButton btn_att;
    private SwingPerson.JbuttonArr btn_atualizar;
    private SwingPerson.JbuttonArr btn_limpar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label_cod;
    private javax.swing.JLabel label_foto;
    private javax.swing.JLabel label_nome;
    private javax.swing.JLabel label_senha;
    private javax.swing.JLabel label_senhaconfirm;
    private javax.swing.JLabel label_user;
    private javax.swing.JLabel label_userBuscar;
    private javax.swing.JMenuItem menu_atu;
    private javax.swing.JMenuItem menu_bus;
    private javax.swing.JMenuItem menu_cad2;
    private javax.swing.JMenuItem menu_cf;
    private javax.swing.JMenu menu_sobre;
    private javax.swing.JMenuItem menu_texto;
    private javax.swing.JMenuItem menu_tf;
    private javax.swing.JMenuItem menu_voltar;
    private javax.swing.JMenuItem menu_voz;
    private javax.swing.JPasswordField pass_senha;
    private javax.swing.JPasswordField pass_senhaconfirm;
    private javax.swing.JTextField textf_cod;
    private javax.swing.JTextField textf_nome;
    private javax.swing.JTextField textf_user;
    private javax.swing.JTextField textf_userBuscar;
    // End of variables declaration//GEN-END:variables
}
