package Logar;

import alert.alert;
import factory.ConnectionFactory;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.sql.*;
import java.util.concurrent.CountDownLatch;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import starter.Icone;

public class login extends javax.swing.JFrame {

    final CountDownLatch latch = new CountDownLatch(1);
    public int err, temp;
    public int cont = 0;
    ConnectionFactory daoc = new ConnectionFactory();
    private Connection con;
    private PreparedStatement pst;
    public String audio;
    //instanciar objeto para fluxo de bytes.
    private FileInputStream fis;

    // variável global para armazenar tamanho da imagem em bytes.
    private int tamanho;

    public login(String au) {
        initComponents();
        setIcon();
        audio = au;
        Status();
        Psenha.setEchoChar('\u25CF'); // Define o caractere de substituição como um círculo
        //Psenha.setEchoChar('\u1F43E');
        placeholder();
    }

    public void placeholder() {

        CTuser.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (CTuser.getText().equals("Usuario123")) {
                    CTuser.setText("");
                    CTuser.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (CTuser.getText().isEmpty()) {
                    CTuser.setText("Usuario123");
                    CTuser.setForeground(Color.GRAY);
                }
            }
        });

        // Adiciona um FocusListener para o campo Psenha
        Psenha.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(Psenha.getPassword()).equals("Digite sua senha")) {
                    Psenha.setText("");
                    Psenha.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(Psenha.getPassword()).isEmpty()) {
                    Psenha.setText("Digite sua senha");
                    Psenha.setForeground(Color.GRAY);
                }
            }
        });
    }

    public void audios(String tpa) {
        switch (audio) {

            case "on":
                AudioInputStream audioInputStream;
                if (null != tpa) {
                    switch (tpa) {
                        case "tc":
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

                        case "cl":
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

                        case "tci":
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

    public void setIcon() {
        Icone ic = new Icone();
        String cm = ic.getIcon();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(cm)));
    }

    private void Status() {
        try {
            con = daoc.getConnection();
            if (con == null) {
                Lconnect.setIcon(new ImageIcon(login.class.getResource("/img/bdnencontrado.png")));
                Lconnect.setToolTipText("Sistema Não Conectado ao Banco de Dados");
            } else {
                Lconnect.setIcon(new ImageIcon(login.class.getResource("/img/bdencontrado.png")));
                Lconnect.setToolTipText("Sistema Conectado ao Banco de Dados");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        switch (audio) {
            case "off":

                Laudio.setIcon(new ImageIcon(login.class.getResource("/img/saudio.png")));
                Rdsta.setSelected(true);
                Laudio.setToolTipText("Áudio Desligado");
                break;
            case "on":

                Laudio.setIcon(new ImageIcon(login.class.getResource("/img/audio.png")));
                Ratva.setSelected(true);
                Laudio.setToolTipText("Áudio Ligado");
                break;
        }
    }

    Thread bloqueioThread = new Thread(() -> {
        try {
            Thread.sleep(20000); // Bloqueia por 20 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown(); // Libera a contagem para desbloquear
    });

    public void timeout() {

        String user = CTuser.getText();
        alert al = new alert(user, audio);
        al.setVisible(true);
        String msg = "Pela diversas tentativas";
        String msg2 = "De erro você esperou";
        String msg3 = "20 segundos";
        String tit = "Senha Incorreta";
        al.alertinput(tit, "erro", msg, msg2, msg3, "timeout");

        bloqueioThread.start();
        try {
            latch.await(); // Aguarda até que a contagem chegue a zero
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void log() {
        String senha = String.valueOf(Psenha.getPassword());
        String user = CTuser.getText();
        if (("Usuario123".equals(CTuser.getText()) || CTuser.getText().isEmpty() || CTuser.getText() == null)
                && (String.valueOf(Psenha.getPassword()).equals("Digite sua senha") || String.valueOf(Psenha.getPassword()).isEmpty() || String.valueOf(Psenha.getPassword()) == null)) {
            alert al = new alert(user, audio);
            al.setVisible(true);
            String msg = "Os campos de Login";
            String msg2 = "Não podem estar vazios";
            String tit = "Campos Não Preenchidos";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");
        } else {
            try {
                con = daoc.getConnection();
                String sql = "select * from admin where BINARY usera=? and BINARY senhaa=? and (bloqueioadm IS NULL OR bloqueioadm = false);";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, user);
                stm.setString(2, senha);
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    if (user.equals(user) && (senha.equals(senha))) {
                        alert al = new alert(user, audio);
                        al.setVisible(true);
                        String msg = "Bem vindo(a) " + CTuser.getText();
                        String tit = "Logado com sucesso";
                        al.alertinput(tit, "ok", "", msg, "", "telaprinlog");
                        dispose();
                    }

                } else {
                    sql = "select * from admin where usera=? and senhaa=? and bloqueioadm=true";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, user);
                    stm.setString(2, senha);
                    rs = stm.executeQuery();
                    if (rs.next()) {
                        alert al = new alert(user, audio);
                        al.setVisible(true);
                        String msg = "Admin Desativado.";
                        String msg2 = "Fale com Um Superior";
                        String msg3 = "Para entender o motivo";
                        String tit = "Desativado";
                        al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                    } else {
                        err++;
                        if (err == 3) {
                            err();
                        } else if (err <= 5) {
                            alert al = new alert(user, audio);
                            al.setVisible(true);
                            String msg = "Usuário ou senha incorretos.";
                            String msg2 = "Tente novamente.";
                            String tit = "Dados incorretos";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        } else {
                            err = 0;
                            timeout();
                        }
                    }
                }
            } catch (Exception CommunicationsException) {
                alert al = new alert(user, audio);
                al.setVisible(true);
                String msg = "Erro ao se comunicar";
                String msg2 = "com o banco de dados";
                String tit = "Erro";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BGaudio = new javax.swing.ButtonGroup();
        LTitulo = new javax.swing.JLabel();
        panel_bd = new javax.swing.JPanel();
        label_bd = new javax.swing.JLabel();
        PFundo = new javax.swing.JPanel();
        Plogin = new javax.swing.JPanel();
        Luser = new javax.swing.JLabel();
        Lsenha = new javax.swing.JLabel();
        CTuser = new javax.swing.JTextField();
        Psenha = new javax.swing.JPasswordField();
        LTitulo1 = new javax.swing.JLabel();
        Luser1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        label_exibirsenha = new javax.swing.JLabel();
        jbuttonArr1 = new SwingPerson.JbuttonArr();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        LLogo = new javax.swing.JLabel();
        Lconnect = new javax.swing.JLabel();
        Laudio = new javax.swing.JLabel();
        LFundo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        menu_sobre = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menu_voz = new javax.swing.JMenuItem();
        menu_texto = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menu_esqueci = new javax.swing.JMenuItem();
        menu_cmlog = new javax.swing.JMenuItem();
        Mopcoes = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        Ratva = new javax.swing.JRadioButtonMenuItem();
        Rdsta = new javax.swing.JRadioButtonMenuItem();
        menu_voltar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pet Found - Login");
        setMinimumSize(new java.awt.Dimension(1366, 795));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(null);

        LTitulo.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        LTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LTitulo.setText("PET FOUND");
        getContentPane().add(LTitulo);
        LTitulo.setBounds(0, 60, 1320, 48);

        panel_bd.setBackground(new java.awt.Color(255, 253, 243));
        panel_bd.setOpaque(false);

        label_bd.setBackground(new java.awt.Color(255, 253, 243));
        label_bd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_bd.setToolTipText("");

        javax.swing.GroupLayout panel_bdLayout = new javax.swing.GroupLayout(panel_bd);
        panel_bd.setLayout(panel_bdLayout);
        panel_bdLayout.setHorizontalGroup(
            panel_bdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_bdLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(label_bd, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_bdLayout.setVerticalGroup(
            panel_bdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_bdLayout.createSequentialGroup()
                .addComponent(label_bd, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(panel_bd);
        panel_bd.setBounds(1020, 650, 230, 30);

        PFundo.setBackground(new java.awt.Color(64, 33, 7));
        PFundo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PFundo.setPreferredSize(new java.awt.Dimension(970, 450));

        Plogin.setBackground(new java.awt.Color(255, 253, 243));

        Luser.setText("Esqueci Minha Senha");
        Luser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Luser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LuserMouseClicked(evt);
            }
        });

        Lsenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Lsenha.setText("Senha");

        CTuser.setBackground(new java.awt.Color(255, 253, 243));
        CTuser.setForeground(new java.awt.Color(102, 102, 102));
        CTuser.setText("Usuario123");
        CTuser.setToolTipText("Coloque seu Nick");
        CTuser.setBorder(null);
        CTuser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CTuserMouseClicked(evt);
            }
        });
        CTuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTuserActionPerformed(evt);
            }
        });
        CTuser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTuserKeyPressed(evt);
            }
        });

        Psenha.setBackground(new java.awt.Color(255, 253, 243));
        Psenha.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        Psenha.setForeground(new java.awt.Color(102, 102, 102));
        Psenha.setText("Digite sua senha");
        Psenha.setToolTipText("Coloque sua Senha");
        Psenha.setBorder(null);
        Psenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PsenhaMouseClicked(evt);
            }
        });
        Psenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PsenhaKeyPressed(evt);
            }
        });

        LTitulo1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        LTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LTitulo1.setText("LOGIN");

        Luser1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Luser1.setText("Usuário");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cadeado_1.png"))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user_1.png"))); // NOI18N

        label_exibirsenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ocultar.png"))); // NOI18N
        label_exibirsenha.setToolTipText("Senha Oculta");
        label_exibirsenha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_exibirsenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_exibirsenhaMouseClicked(evt);
            }
        });

        jbuttonArr1.setText("Entrar");
        jbuttonArr1.setToolTipText("Logar No Sistema");
        jbuttonArr1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbuttonArr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonArr1ActionPerformed(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(363, 3));

        javax.swing.GroupLayout PloginLayout = new javax.swing.GroupLayout(Plogin);
        Plogin.setLayout(PloginLayout);
        PloginLayout.setHorizontalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PloginLayout.createSequentialGroup()
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CTuser, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Lsenha)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jbuttonArr1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Psenha, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_exibirsenha))
                            .addComponent(Luser, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Luser1))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        PloginLayout.setVerticalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PloginLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(LTitulo1)
                .addGap(60, 60, 60)
                .addComponent(Luser1)
                .addGap(6, 6, 6)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addComponent(CTuser, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(Lsenha)
                .addGap(6, 6, 6)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addComponent(Psenha, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label_exibirsenha, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(29, 29, 29)
                .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(Luser))
        );

        CTuser.getAccessibleContext().setAccessibleName("");
        CTuser.getAccessibleContext().setAccessibleDescription("");

        LLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N

        javax.swing.GroupLayout PFundoLayout = new javax.swing.GroupLayout(PFundo);
        PFundo.setLayout(PFundoLayout);
        PFundoLayout.setHorizontalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(LLogo)
                .addGap(61, 61, 61)
                .addComponent(Plogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        PFundoLayout.setVerticalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Plogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PFundoLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(LLogo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(PFundo);
        PFundo.setBounds(200, 150, 970, 450);

        Lconnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bdcarregando.png"))); // NOI18N
        Lconnect.setToolTipText("Procurando Banco de Dados");
        Lconnect.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Lconnect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LconnectMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LconnectMouseExited(evt);
            }
        });
        getContentPane().add(Lconnect);
        Lconnect.setBounds(1260, 630, 60, 60);

        Laudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/audio.png"))); // NOI18N
        Laudio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Laudio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaudioMouseClicked(evt);
            }
        });
        getContentPane().add(Laudio);
        Laudio.setBounds(1290, 20, 30, 30);

        LFundo.setForeground(new java.awt.Color(153, 153, 153));
        LFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fundo.jpg"))); // NOI18N
        LFundo.setText(".");
        LFundo.setToolTipText("");
        getContentPane().add(LFundo);
        LFundo.setBounds(0, 0, 1366, 800);

        jMenuBar1.setBackground(new java.awt.Color(255, 253, 243));
        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuBar1.setPreferredSize(new java.awt.Dimension(286, 35));

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/optionm.png"))); // NOI18N
        jMenu3.setToolTipText("Aba de Opções");
        jMenu3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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

        jMenu3.add(menu_sobre);

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

        jMenu3.add(jMenu5);

        jMenu2.setText("Ajuda");
        jMenu2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menu_esqueci.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_esqueci.setText("Esqueci minha senha");
        menu_esqueci.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_esqueci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_esqueciActionPerformed(evt);
            }
        });
        jMenu2.add(menu_esqueci);

        menu_cmlog.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_cmlog.setText("Como logar?");
        menu_cmlog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_cmlog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_cmlogActionPerformed(evt);
            }
        });
        jMenu2.add(menu_cmlog);

        jMenu3.add(jMenu2);

        Mopcoes.setText("Opções");
        Mopcoes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenu4.setText("Audio");
        jMenu4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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
        jMenu4.add(Ratva);

        Rdsta.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        BGaudio.add(Rdsta);
        Rdsta.setText("Desativar Audio");
        Rdsta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Rdsta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RdstaActionPerformed(evt);
            }
        });
        jMenu4.add(Rdsta);

        Mopcoes.add(jMenu4);

        menu_voltar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        menu_voltar.setText("Sair");
        menu_voltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_voltarActionPerformed(evt);
            }
        });
        Mopcoes.add(menu_voltar);

        jMenu3.add(Mopcoes);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    public void inm() {
        String user = CTuser.getText();
        alert al = new alert(user, audio);
        al.setVisible(true);
        String msg = "Indisponível no momento";
        String tit = "Página em manutenção";
        al.alertinput(tit, "info", msg, "", "", "info");
    }

    public void esquecisenha() {
        String user = CTuser.getText();
        alert al = new alert(user, audio);
        al.setVisible(true);
        String msg = "Caso tenha esquecido";
        String msg2 = " a senha, procurar por";
        String msg3 = "outro administrador";
        String tit = "Recuperação.";
        al.alertinput(tit, "info", msg, msg2, msg3, "info");
    }
    private void CTuserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CTuserMouseClicked

    }//GEN-LAST:event_CTuserMouseClicked

    private void PsenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PsenhaMouseClicked

    }//GEN-LAST:event_PsenhaMouseClicked

    private void PsenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PsenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            CTuser.setText("");
            CTuser.setForeground(Color.black);
            Psenha.setText("");
            Psenha.setForeground(Color.black);
        }

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            log();
        }
    }//GEN-LAST:event_PsenhaKeyPressed

    private void LuserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LuserMouseClicked
        //esquecisenha();
        esquecisenha sc = new esquecisenha(audio);
        sc.setVisible(true);

    }//GEN-LAST:event_LuserMouseClicked

    private void CTuserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTuserKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            Psenha.setText("");
            Psenha.setForeground(Color.black);
            CTuser.setText("");
            CTuser.setForeground(Color.black);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            log();
        }
    }//GEN-LAST:event_CTuserKeyPressed

    private void menu_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_voltarActionPerformed
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja sair?");
        if (escolha == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_menu_voltarActionPerformed

    private void menu_vozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_vozActionPerformed
        inm();
    }//GEN-LAST:event_menu_vozActionPerformed

    private void menu_textoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_textoActionPerformed
        inm();
    }//GEN-LAST:event_menu_textoActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        Status();
    }//GEN-LAST:event_formWindowActivated

    private void LconnectMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LconnectMouseEntered
        panel_bd.setOpaque(rootPaneCheckingEnabled);
        try {
            con = daoc.getConnection();

            if (con == null) {
                Lconnect.setIcon(new ImageIcon(login.class
                        .getResource("/img/bdnencontrado.png")));
                label_bd.setText("Erro de Conexão");

            } else {
                Lconnect.setIcon(new ImageIcon(login.class
                        .getResource("/img/bdencontrado.png")));
                label_bd.setText("Banco de dados conectado");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_LconnectMouseEntered

    private void LconnectMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LconnectMouseExited
        label_bd.setText("");
        panel_bd.setOpaque(false);
    }//GEN-LAST:event_LconnectMouseExited

    private void label_exibirsenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_exibirsenhaMouseClicked
        audios("cl");
        if (cont == 0) {
            Psenha.setEchoChar((char) 0);
            cont = 1;
            label_exibirsenha
                    .setIcon(new ImageIcon(login.class
                            .getResource("/img/mostrar.png")));
            label_exibirsenha.setToolTipText("Senha Visível");

        } else {
            Psenha.setEchoChar('\u25CF');
            cont = 0;
            label_exibirsenha
                    .setIcon(new ImageIcon(login.class
                            .getResource("/img/ocultar.png")));
            label_exibirsenha.setToolTipText("Senha Oculta");
        }
    }//GEN-LAST:event_label_exibirsenhaMouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        versao v = new versao();
        v.buscarVersao(); // Buscar versões antes de exibir a janela
        v.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void LaudioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaudioMouseClicked
        audios("cl");
        if (audio == "off") {
            Mixer.Info[] mixers = AudioSystem.getMixerInfo();

            if (mixers.length == 0) {
                audio = "off";
                String user = CTuser.getText();
                alert al = new alert(user, audio);
                al.setVisible(true);
                String msg = "Nenhum dispositivo de";
                String msg2 = "saída de audio encontrado";
                String tit = "Áudio indisponível";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");

            } else {

                audio = "on";
                for (Mixer.Info mixerInfo : mixers) {
                    System.out.println(mixerInfo);
                }
            }
            Status();
        } else {

            audio = "off";
            Status();
        }
    }//GEN-LAST:event_LaudioMouseClicked

    private void RatvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RatvaActionPerformed
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        if (mixers.length == 0) {
            audio = "off";
            String user = CTuser.getText();
            alert al = new alert(user, audio);
            al.setVisible(true);
            String msg = "Nenhum dispositivo de";
            String msg2 = "saída de audio encontrado";
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

    private void menu_esqueciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_esqueciActionPerformed
        esquecisenha();
    }//GEN-LAST:event_menu_esqueciActionPerformed

    private void menu_cmlogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cmlogActionPerformed
        inm();
    }//GEN-LAST:event_menu_cmlogActionPerformed

    private void jbuttonArr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonArr1ActionPerformed
        log();
    }//GEN-LAST:event_jbuttonArr1ActionPerformed

    private void CTuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTuserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTuserActionPerformed

    public void err() {
        String user = CTuser.getText();
        alert al = new alert(user, audio);
        al.setVisible(true);
        String msg = "Caso tenha esquecido";
        String msg2 = " a senha, procurar por";
        String msg3 = "outro administrador";
        String tit = "Senha Incorreta";
        al.alertinput(tit, "erro", msg, msg2, msg3, "erro");

    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String au = "off";
                new login(au).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGaudio;
    private javax.swing.JTextField CTuser;
    private javax.swing.JLabel LFundo;
    private javax.swing.JLabel LLogo;
    private javax.swing.JLabel LTitulo;
    private javax.swing.JLabel LTitulo1;
    private javax.swing.JLabel Laudio;
    private javax.swing.JLabel Lconnect;
    private javax.swing.JLabel Lsenha;
    private javax.swing.JLabel Luser;
    private javax.swing.JLabel Luser1;
    private javax.swing.JMenu Mopcoes;
    private javax.swing.JPanel PFundo;
    private javax.swing.JPanel Plogin;
    private javax.swing.JPasswordField Psenha;
    private javax.swing.JRadioButtonMenuItem Ratva;
    private javax.swing.JRadioButtonMenuItem Rdsta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private SwingPerson.JbuttonArr jbuttonArr1;
    private javax.swing.JLabel label_bd;
    private javax.swing.JLabel label_exibirsenha;
    private javax.swing.JMenuItem menu_cmlog;
    private javax.swing.JMenuItem menu_esqueci;
    private javax.swing.JMenu menu_sobre;
    private javax.swing.JMenuItem menu_texto;
    private javax.swing.JMenuItem menu_voltar;
    private javax.swing.JMenuItem menu_voz;
    private javax.swing.JPanel panel_bd;
    // End of variables declaration//GEN-END:variables
}
