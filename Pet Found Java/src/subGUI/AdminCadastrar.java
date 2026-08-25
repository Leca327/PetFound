package subGUI;

import Logar.versao;
import alert.alert;
import dao.AdminDAO;
import modelo.AdminMOD;
import factory.ConnectionFactory;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import starter.Icone;

public class AdminCadastrar extends javax.swing.JFrame {

    private Connection connection;
    //instanciar objeto para fluxo de bytes.
    private FileInputStream fis;
    public String admn, audio;
    // variável global para armazenar tamanho da imagem em bytes.
    private int tamanho;
    Boolean maxc;

    public AdminCadastrar(String adm, String au) {
        initComponents();
        setIcon();
        admn = adm;
        audio = au;
        Status();
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
        textf_user.setText("");
        textf_nome.setText("");
        pass_senha.setText("");
        pass_senhaconfirm.setText("");
        label_foto.setIcon(null);
        L_caracternm.setText("0");
        L_caracterus.setText("0");
        L_caracterse.setText("0");
        L_caractercse.setText("0");

    }

    public static String generateAdminCode(String nick, String name) {
        LocalDateTime now = LocalDateTime.now();

        // Remover os espaços das strings nick e name
        nick = nick.replaceAll("\\s", "");
        name = name.replaceAll("\\s", "");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        String dayOfMonth = String.format("%02d", now.getDayOfMonth());
        String monthValue = String.format("%02d", now.getMonthValue());

        String adminCode = "Adm"
                + nick.charAt(0)
                + nick.charAt(nick.length() / 2)
                + nick.charAt(nick.length() - 1)
                + now.getHour()
                + now.getMinute()
                + name.charAt(0)
                + name.charAt(name.length() / 2)
                + name.charAt(name.length() - 1)
                + dayOfMonth
                + monthValue
                + now.getYear();

        return adminCode;
    }

    private void CarregarFoto() {

        // vai ser responsável por carregar foto do computador local para a interface java.
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Selecionar Arquivo Desejado");
        jfc.setFileFilter(new FileNameExtensionFilter("Arquivos de Imagens(*.PNG,"
                + "*.JPG, *.JPEG)", "png", "jpg", "jpeg"));
        //jfc.showOpenDialog(this);
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
        label_foto.setIcon(null);
    }

    public Boolean maxperm() {
        maxc = !(textf_user.getText().length() <= 25 && textf_nome.getText().length() <= 25 && pass_senha.getPassword().length <= 25 && pass_senhaconfirm.getPassword().length <= 25);
        return maxc;
    }

    public void cadastro() {
        Boolean perm = maxperm();
        if (perm == false) {
            String user = textf_user.getText();
            this.connection = new ConnectionFactory().getConnection();
            PreparedStatement ps;
            String status = "";

            try {
                ps = connection.prepareStatement("select * from admin where usera=?;");
                ps.setString(1, user);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    alert al = new alert(admn, audio);
                    al.setVisible(true);
                    String msg = "User do Admin já existe";
                    String tit = "Admin existente";
                    al.alertinput(tit, "erro", "", msg, "", "erro");

                } else {
                    ps = connection.prepareStatement("select * from pessoa where nickname=?;");
                    ps.setString(1, user);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        alert al = new alert(admn, audio);
                        al.setVisible(true);
                        String msg = "Nick do Admin já existe";
                        String tit = "Admin existente";
                        al.alertinput(tit, "erro", "", msg, "", "erro");

                    } else {
                        if ((textf_user.getText().isEmpty()) || (textf_nome.getText().isEmpty()) || (pass_senha.getText().isEmpty()) || (pass_senhaconfirm.getText().isEmpty())) {
                            alert al = new alert(admn, audio);
                            al.setVisible(true);
                            String msg = "Os campos não podem";
                            String msg2 = "retornar vazios";
                            String tit = "Campo(s) de Texto Vazio";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        } else {
                            String nome = textf_nome.getText();
                            String adminCode = generateAdminCode(user, nome);
                            AdminMOD admin = new AdminMOD();

                            admin.setUser(textf_user.getText());
                            admin.setSenha(pass_senha.getText());
                            admin.setNome(textf_nome.getText());
                            admin.setCod(adminCode);
                            admin.setImg(fis);
                            admin.setTamanho(tamanho);

                            if (pass_senha.getText().equals(pass_senhaconfirm.getText())) {

                                // instanciando a classe UsuarioDAO do pacote dao e criando seu objeto dao
                                AdminDAO dao = new AdminDAO();
                                dao.adiciona(admin);
                                alert al = new alert(admn, audio);
                                al.setVisible(true);
                                String msg = "Admin " + textf_user.getText();
                                String msg2 = " inserido(a) com sucesso";
                                String tit = "Cadastro de Admin";
                                al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                                dispose();
                                limpar();

                            } else {
                                alert al = new alert(admn, audio);
                                al.setVisible(true);
                                String msg = "Senha e Confirmar Senha";
                                String msg2 = "Precisam ser iguais";
                                String tit = "Senhas desiquais";
                                al.alertinput(tit, "erro", msg, msg2, "", "erro");
                            }

                        }
                    }
                    // }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            alert al = new alert(admn, audio);
            al.setVisible(true);
            String msg = "Diminua a Quantidade ";
            String msg2 = "de Caracter Para";
            String msg3 = "o Cadastro.";
            String tit = "Excedeu o Limite de Caracter";
            al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BGaudio = new javax.swing.ButtonGroup();
        PFundo = new javax.swing.JPanel();
        Plogin = new javax.swing.JPanel();
        pass_senha = new javax.swing.JPasswordField();
        textf_nome = new javax.swing.JTextField();
        label_nome = new javax.swing.JLabel();
        textf_user = new javax.swing.JTextField();
        label_user = new javax.swing.JLabel();
        label_senha1 = new javax.swing.JLabel();
        pass_senhaconfirm = new javax.swing.JPasswordField();
        label_senha = new javax.swing.JLabel();
        btn_cadastrar = new SwingPerson.JbuttonArr();
        btn_limpar = new SwingPerson.JbuttonArr();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        L_caracternm = new javax.swing.JLabel();
        L_caracterus = new javax.swing.JLabel();
        L_caractercse = new javax.swing.JLabel();
        L_caracterse = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        label_foto = new javax.swing.JLabel();
        BtnSemimg1 = new SwingPerson.JbuttonArr();
        btnCARREGAR1 = new SwingPerson.JbuttonArr();
        L_vlt = new javax.swing.JLabel();
        jMenuBar3 = new javax.swing.JMenuBar();
        Mopc = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        menu_cad = new javax.swing.JMenuItem();
        menu_tf = new javax.swing.JMenuItem();
        menu_cf = new javax.swing.JMenuItem();
        menu_lmp = new javax.swing.JMenuItem();
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
        setTitle("Pet Found - Cadastrar Admin");
        setPreferredSize(new java.awt.Dimension(826, 445));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PFundo.setBackground(new java.awt.Color(64, 33, 7));
        PFundo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PFundo.setPreferredSize(new java.awt.Dimension(810, 373));

        Plogin.setBackground(new java.awt.Color(255, 253, 243));
        Plogin.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Plogin.setPreferredSize(new java.awt.Dimension(553, 367));

        pass_senha.setBackground(new java.awt.Color(255, 253, 243));
        pass_senha.setBorder(null);
        pass_senha.setPreferredSize(new java.awt.Dimension(64, 20));
        pass_senha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pass_senhaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pass_senhaKeyReleased(evt);
            }
        });

        textf_nome.setBackground(new java.awt.Color(255, 253, 243));
        textf_nome.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_nome.setBorder(null);
        textf_nome.setPreferredSize(new java.awt.Dimension(64, 20));
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

        textf_user.setBackground(new java.awt.Color(255, 253, 243));
        textf_user.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_user.setBorder(null);
        textf_user.setPreferredSize(new java.awt.Dimension(64, 20));
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

        label_senha1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_senha1.setText("CONFIRMAR SENHA");

        pass_senhaconfirm.setBackground(new java.awt.Color(255, 253, 243));
        pass_senhaconfirm.setBorder(null);
        pass_senhaconfirm.setPreferredSize(new java.awt.Dimension(64, 20));
        pass_senhaconfirm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pass_senhaconfirmKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pass_senhaconfirmKeyReleased(evt);
            }
        });

        label_senha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_senha.setText("SENHA");

        btn_cadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cadadc.png"))); // NOI18N
        btn_cadastrar.setToolTipText("Cadastre Um Admin");
        btn_cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadastrarActionPerformed(evt);
            }
        });

        btn_limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/apagar.png"))); // NOI18N
        btn_limpar.setToolTipText("Limpar Todos os Campos");
        btn_limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caracternm.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracternm.setText("0");

        L_caracterus.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterus.setText("0");

        L_caractercse.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercse.setText("0");

        L_caracterse.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterse.setText("0");

        javax.swing.GroupLayout PloginLayout = new javax.swing.GroupLayout(Plogin);
        Plogin.setLayout(PloginLayout);
        PloginLayout.setHorizontalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(PloginLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(L_caracterus)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(L_caracternm)
                            .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(textf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_nome)
                                .addComponent(pass_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_senha)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(L_caracterse))
                        .addGap(61, 61, 61)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_user)
                            .addComponent(textf_user, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pass_senhaconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_senha1)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(L_caractercse))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        PloginLayout.setVerticalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                        .addComponent(label_nome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                        .addComponent(label_user)
                        .addGap(7, 7, 7)
                        .addComponent(textf_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(L_caracternm)
                    .addComponent(L_caracterus))
                .addGap(32, 32, 32)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                        .addComponent(label_senha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pass_senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                        .addComponent(label_senha1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pass_senhaconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(L_caractercse)
                    .addComponent(L_caracterse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cadastrar Admin");

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        label_foto.setBackground(new java.awt.Color(255, 253, 243));
        label_foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        BtnSemimg1.setText("Remover");
        BtnSemimg1.setToolTipText("Remover imagem do Admin");
        BtnSemimg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSemimg1ActionPerformed(evt);
            }
        });

        btnCARREGAR1.setText("Carregar");
        btnCARREGAR1.setToolTipText("Carregar Uma Foto");
        btnCARREGAR1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCARREGAR1ActionPerformed(evt);
            }
        });

        L_vlt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_voltarbr.png"))); // NOI18N
        L_vlt.setToolTipText("Sair de Cadastrar");
        L_vlt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        L_vlt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                L_vltMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PFundoLayout = new javax.swing.GroupLayout(PFundo);
        PFundo.setLayout(PFundoLayout);
        PFundoLayout.setHorizontalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(L_vlt)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addComponent(btnCARREGAR1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BtnSemimg1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addComponent(Plogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        PFundoLayout.setVerticalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnSemimg1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCARREGAR1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(L_vlt)
                .addContainerGap())
            .addGroup(PFundoLayout.createSequentialGroup()
                .addComponent(Plogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        getContentPane().add(PFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jMenuBar3.setBackground(new java.awt.Color(255, 253, 243));
        jMenuBar3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuBar3.setMinimumSize(new java.awt.Dimension(210, 20));
        jMenuBar3.setPreferredSize(new java.awt.Dimension(210, 35));

        Mopc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/option.png"))); // NOI18N
        Mopc.setToolTipText("Aba de Opções");

        jMenu4.setText("Comandos Rápidos");
        jMenu4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menu_cad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_cad.setText("Cadastrar");
        menu_cad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_cad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_cadActionPerformed(evt);
            }
        });
        jMenu4.add(menu_cad);

        menu_tf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_tf.setText("Tirar Foto");
        menu_tf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_tfActionPerformed(evt);
            }
        });
        jMenu4.add(menu_tf);

        menu_cf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_cf.setText("Carregar Foto");
        menu_cf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_cf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_cfActionPerformed(evt);
            }
        });
        jMenu4.add(menu_cf);

        menu_lmp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_lmp.setText("Limpar");
        menu_lmp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_lmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_lmpActionPerformed(evt);
            }
        });
        jMenu4.add(menu_lmp);

        Mopc.add(jMenu4);

        jMenu5.setText("Acessibilidade");
        jMenu5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menu_voz.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_voz.setText("Ativar comando por voz");
        menu_voz.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_voz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_vozActionPerformed(evt);
            }
        });
        jMenu5.add(menu_voz);

        menu_texto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_texto.setText("Ativar leitura de texto");
        menu_texto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_texto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_textoActionPerformed(evt);
            }
        });
        jMenu5.add(menu_texto);

        Mopc.add(jMenu5);

        menu_sobre.setText("Sobre");
        menu_sobre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem1.setText("Versão");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menu_sobre.add(jMenuItem1);

        Mopc.add(menu_sobre);

        jMenu3.setText("Opções");
        jMenu3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenu6.setText("Audio");
        jMenu6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Ratva.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        BGaudio.add(Ratva);
        Ratva.setSelected(true);
        Ratva.setText("Ativar Audio");
        Ratva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Ratva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RatvaActionPerformed(evt);
            }
        });
        jMenu6.add(Ratva);

        Rdsta.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        BGaudio.add(Rdsta);
        Rdsta.setText("Desativar Audio");
        Rdsta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Rdsta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RdstaActionPerformed(evt);
            }
        });
        jMenu6.add(Rdsta);

        jMenu3.add(jMenu6);

        menu_voltar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        menu_voltar.setText("Voltar");
        menu_voltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
    public void lmp() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Limpar todos os campos?");

        if (escolha == 0) {
            limpar();
        }
    }

    public void vlt() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Sair?");
        if (escolha == 0) {
            audios("tc");

            //AdminBuscar ad = new AdminBuscar(admn, audio);
            //ad.setVisible(true);
            dispose();
        }
    }

    public void inm() {
        alert al = new alert(admn, audio);
        al.setVisible(true);
        String msg = "Indisponível no momento";
        String tit = "Página em manutenção";
        al.alertinput(tit, "info", "", msg, "", "info");
    }

    private void menu_lmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_lmpActionPerformed
        lmp();
    }//GEN-LAST:event_menu_lmpActionPerformed

    private void menu_cadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cadActionPerformed
        cadastro();
    }//GEN-LAST:event_menu_cadActionPerformed

    private void menu_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_voltarActionPerformed
        vlt();
    }//GEN-LAST:event_menu_voltarActionPerformed

    private void menu_vozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_vozActionPerformed
        inm();
    }//GEN-LAST:event_menu_vozActionPerformed

    private void menu_textoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_textoActionPerformed
        inm();
    }//GEN-LAST:event_menu_textoActionPerformed

    private void menu_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tfActionPerformed
        TirarFoto();
    }//GEN-LAST:event_menu_tfActionPerformed

    private void menu_cfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cfActionPerformed
        CarregarFoto();
    }//GEN-LAST:event_menu_cfActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        audios("tci");
        versao v = new versao();
        v.buscarVersao(); // Buscar versões antes de exibir a janela
        v.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void textf_userKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_userKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_userKeyPressed

    private void pass_senhaconfirmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaconfirmKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_pass_senhaconfirmKeyPressed

    private void pass_senhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_pass_senhaKeyPressed

    private void textf_nomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_nomeKeyPressed

    private void RatvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RatvaActionPerformed
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        if (mixers.length == 0) {
            alert al = new alert(admn, audio);
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

    private void BtnSemimg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSemimg1ActionPerformed
        TirarFoto();
    }//GEN-LAST:event_BtnSemimg1ActionPerformed

    private void btnCARREGAR1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCARREGAR1ActionPerformed
        audios("cl");
        CarregarFoto();
    }//GEN-LAST:event_btnCARREGAR1ActionPerformed

    private void L_vltMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_vltMouseClicked
        vlt();
    }//GEN-LAST:event_L_vltMouseClicked

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        audios("cl");
        lmp();
    }//GEN-LAST:event_btn_limparActionPerformed

    private void btn_cadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadastrarActionPerformed
        cadastro();
    }//GEN-LAST:event_btn_cadastrarActionPerformed

    private void textf_nomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nomeKeyReleased
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
    }//GEN-LAST:event_textf_nomeKeyReleased

    private void textf_userKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_userKeyReleased
        String inputText = textf_user.getText(); // Obtém o texto do campo de texto
        int numCaracteres = inputText.length();
        int maxperm = 25;
        L_caracterus.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracterus.setForeground(new Color(255, 51, 51));
            L_caracterus.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracterus.setForeground(new Color(0, 0, 0));
            L_caracterus.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }
    }//GEN-LAST:event_textf_userKeyReleased

    private void pass_senhaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaKeyReleased
        String inputText = pass_senha.getText(); // Obtém o texto do campo de texto
        int numCaracteres = inputText.length();
        int maxperm = 25;
        L_caracterse.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracterse.setForeground(new Color(255, 51, 51));
            L_caracterse.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracterse.setForeground(new Color(0, 0, 0));
            L_caracterse.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }
    }//GEN-LAST:event_pass_senhaKeyReleased

    private void pass_senhaconfirmKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaconfirmKeyReleased
        String inputText = pass_senhaconfirm.getText(); // Obtém o texto do campo de texto
        int numCaracteres = inputText.length();
        int maxperm = 25;
        L_caractercse.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caractercse.setForeground(new Color(255, 51, 51));
            L_caractercse.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caractercse.setForeground(new Color(0, 0, 0));
            L_caractercse.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }
    }//GEN-LAST:event_pass_senhaconfirmKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String ad = "";
                String au = "off";
                new AdminCadastrar(ad, au).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGaudio;
    private SwingPerson.JbuttonArr BtnSemimg1;
    private javax.swing.JLabel L_caractercse;
    private javax.swing.JLabel L_caracternm;
    private javax.swing.JLabel L_caracterse;
    private javax.swing.JLabel L_caracterus;
    private javax.swing.JLabel L_vlt;
    private javax.swing.JMenu Mopc;
    private javax.swing.JPanel PFundo;
    private javax.swing.JPanel Plogin;
    private javax.swing.JRadioButtonMenuItem Ratva;
    private javax.swing.JRadioButtonMenuItem Rdsta;
    private SwingPerson.JbuttonArr btnCARREGAR1;
    private SwingPerson.JbuttonArr btn_cadastrar;
    private SwingPerson.JbuttonArr btn_limpar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel label_foto;
    private javax.swing.JLabel label_nome;
    private javax.swing.JLabel label_senha;
    private javax.swing.JLabel label_senha1;
    private javax.swing.JLabel label_user;
    private javax.swing.JMenuItem menu_cad;
    private javax.swing.JMenuItem menu_cf;
    private javax.swing.JMenuItem menu_lmp;
    private javax.swing.JMenu menu_sobre;
    private javax.swing.JMenuItem menu_texto;
    private javax.swing.JMenuItem menu_tf;
    private javax.swing.JMenuItem menu_voltar;
    private javax.swing.JMenuItem menu_voz;
    private javax.swing.JPasswordField pass_senha;
    private javax.swing.JPasswordField pass_senhaconfirm;
    private javax.swing.JTextField textf_nome;
    private javax.swing.JTextField textf_user;
    // End of variables declaration//GEN-END:variables
}
