package InJframe;

import Logar.login;
import alert.alert;
import factory.ConnectionFactory;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class EnviarEmail extends javax.swing.JInternalFrame {

    private Connection connection;
    String admin, audio, nm;

    public EnviarEmail(String adm, String audio) {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        this.admin = adm;
        this.audio = audio;
        bus(adm);
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
                nm = tx3;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void enviar(String msgs, String Titulo, String gmail) {
        // Configurações do servidor SMTP e conta de e-mail
        String host = "smtp.office365.com"; // Endereço do servidor SMTP
        String porta = "587"; // Porta do servidor SMTP (587 é comum para TLS)
        String usuario = "petfound302@outlook.com"; // Seu endereço de e-mail
        String senha = "petFound1302#"; // Sua senha de e-mail

        // Configurações de propriedades
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", porta);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Cria uma sessão com autenticação
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, senha);
            }
        });

        try {
            // Cria um objeto Message
            Message message = new MimeMessage(session);

            // Define o remetente com nome
            InternetAddress remetente = new InternetAddress(usuario, "Administrador: " + nm);
            message.setFrom(remetente);

            // Define o destinatário
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(gmail));

            // Define o assunto e o conteúdo do e-mail
            message.setSubject(Titulo);
            message.setText(msgs);

            // Envia o e-mail
            Transport.send(message);

            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "E-email enviado com";
            String msg2 = "sucesso.";
            String tit = "Enviar E-mail";
            al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void buscarPF(String nick) {
        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps;

        try {
            ps = connection.prepareStatement("select * from pessoa  where nickname=?;");
            ps.setString(1, nick);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.connection = new ConnectionFactory().getConnection();

                try {
                    ps = connection.prepareStatement("SELECT * FROM pessoa WHERE nickname=?;");
                    ps.setString(1, nick);
                    ResultSet res = ps.executeQuery();

                    while (res.next()) {

                        alert al = new alert(admin, audio);
                        al.audios("ok");
                        String tx5 = (res.getString(7));
                        String tx4 = (res.getString(3));
                        String tx3 = (res.getString(1));
                        String tx2 = (res.getString(11));

                        if ("PF".equals(tx5.substring(0, 2))) {
                            textf_tpp.setText("Pessoa Física");
                        } else if ("PJ".equals(tx5.substring(0, 2))) {
                            textf_tpp.setText("Pessoa Jurídica");
                        }
                        textf_tpp1.setText(tx3 + " " + tx2);
                        textf_email.setText(tx4);

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Nickname não existe";
                String tit = "Pessoa inexistente";
                al.alertinput(tit, "erro", "", msg, "", "erro");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void limpar() {
        textf_email.setText("");
        textarea_msg.setText("");
        textf_ass.setText("");
        textf_nickBuscar.setText("");
        textf_tpp.setText("");
        textf_tpp1.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        label_userBuscar = new javax.swing.JLabel();
        textf_nickBuscar = new javax.swing.JTextField();
        Lbuscar = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textf_tpp = new javax.swing.JTextField();
        labeltppes = new javax.swing.JLabel();
        labeltppes1 = new javax.swing.JLabel();
        textf_tpp1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        textarea_msg = new javax.swing.JTextArea();
        textf_ass = new javax.swing.JTextField();
        textf_email = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jbuttonArr1 = new SwingPerson.JbuttonArr();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        btn_limpar2 = new SwingPerson.JbuttonArr();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();

        setBorder(null);
        setMinimumSize(new java.awt.Dimension(0, 0));

        jPanel1.setBackground(new java.awt.Color(64, 33, 7));
        jPanel1.setForeground(new java.awt.Color(64, 33, 7));
        jPanel1.setPreferredSize(new java.awt.Dimension(1284, 666));

        jPanel2.setBackground(new java.awt.Color(255, 253, 243));

        label_userBuscar.setBackground(new java.awt.Color(51, 51, 51));
        label_userBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_userBuscar.setForeground(new java.awt.Color(51, 51, 51));
        label_userBuscar.setText("NICKNAME");

        textf_nickBuscar.setBackground(new java.awt.Color(255, 253, 243));
        textf_nickBuscar.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_nickBuscar.setBorder(null);
        textf_nickBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_nickBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textf_nickBuscarMouseClicked(evt);
            }
        });
        textf_nickBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_nickBuscarKeyPressed(evt);
            }
        });

        Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        Lbuscar.setToolTipText("Buscar Email de Pessoa");
        Lbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Lbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LbuscarMouseClicked(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(363, 3));

        textf_tpp.setEditable(false);
        textf_tpp.setBackground(new java.awt.Color(204, 204, 204));

        labeltppes.setBackground(new java.awt.Color(51, 51, 51));
        labeltppes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labeltppes.setForeground(new java.awt.Color(51, 51, 51));
        labeltppes.setText("Tipo de Pessoa");

        labeltppes1.setBackground(new java.awt.Color(51, 51, 51));
        labeltppes1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labeltppes1.setForeground(new java.awt.Color(51, 51, 51));
        labeltppes1.setText("Nome");

        textf_tpp1.setEditable(false);
        textf_tpp1.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(404, 404, 404)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labeltppes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_tpp, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labeltppes1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_tpp1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(label_userBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textf_nickBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addComponent(Lbuscar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textf_nickBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_userBuscar))
                        .addGap(0, 0, 0)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Lbuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textf_tpp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labeltppes1))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textf_tpp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labeltppes)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        textarea_msg.setBackground(new java.awt.Color(255, 253, 243));
        textarea_msg.setColumns(20);
        textarea_msg.setLineWrap(true);
        textarea_msg.setRows(5);
        textarea_msg.setWrapStyleWord(true);
        jScrollPane1.setViewportView(textarea_msg);

        textf_ass.setBackground(new java.awt.Color(255, 253, 243));

        textf_email.setEditable(false);
        textf_email.setBackground(new java.awt.Color(255, 253, 243));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 253, 243));
        jLabel1.setText("E-mail do Destinatário");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 253, 243));
        jLabel2.setText("Assunto do E-mail:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 253, 243));
        jLabel3.setText("Mensagem:");

        jbuttonArr1.setText("Enviar E-mail");
        jbuttonArr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonArr1ActionPerformed(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Boa tarde/Boa noite/Bom dia.\n\nAtenciosamente,\n ____");
        jTextArea1.setToolTipText("Clique para colocar a mensagem no campo.");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTextArea1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea1);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setText("Prezado(a) ____,\n\nAtenciosamente,\n ____");
        jTextArea2.setToolTipText("Clique para colocar a mensagem no campo.");
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTextArea2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTextArea2);

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.setText("Caro ____,\n\nAtenciosamente,\n ____");
        jTextArea3.setToolTipText("Clique para colocar a mensagem no campo.");
        jTextArea3.setWrapStyleWord(true);
        jTextArea3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTextArea3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea3MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTextArea3);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 253, 243));
        jLabel5.setText("Mensagem Padrões");

        btn_limpar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/apagar.png"))); // NOI18N
        btn_limpar2.setToolTipText("Limpar Todos os Campos");
        btn_limpar2.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_limpar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpar2ActionPerformed(evt);
            }
        });

        jTextArea4.setEditable(false);
        jTextArea4.setColumns(20);
        jTextArea4.setLineWrap(true);
        jTextArea4.setRows(5);
        jTextArea4.setText("Olá ____,\n\nAtenciosamente,\n ____");
        jTextArea4.setToolTipText("Clique para colocar a mensagem no campo.");
        jTextArea4.setWrapStyleWord(true);
        jTextArea4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTextArea4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea4MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTextArea4);

        jTextArea5.setEditable(false);
        jTextArea5.setColumns(20);
        jTextArea5.setLineWrap(true);
        jTextArea5.setRows(5);
        jTextArea5.setText("Senhor(a) ____,\n\nAtenciosamente,\n ____");
        jTextArea5.setToolTipText("Clique para colocar a mensagem no campo.");
        jTextArea5.setWrapStyleWord(true);
        jTextArea5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTextArea5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea5MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTextArea5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 3, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(182, 182, 182)
                                        .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(textf_email, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(textf_ass)
                                            .addComponent(jLabel2)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3))))
                                .addGap(326, 326, 326)
                                .addComponent(btn_limpar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(51, 51, 51)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12)
                        .addComponent(textf_ass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_limpar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
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
                .addGap(0, 36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void verif() {
        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps;

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
            String msgs = textarea_msg.getText();
            String Titulo = textf_ass.getText();
            String em = textf_email.getText();
            if (textarea_msg.getText().isEmpty() || textf_email.getText().isEmpty() || textf_ass.getText().isEmpty()) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Preencha todos os campos";
                String msg2 = "Para enviar o E-mail.";
                String tit = "E-mail Inválido";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            } else {

                try {
                    ps = connection.prepareStatement("select * from pessoa  where emailp=?;");
                    ps.setString(1, em);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {

                        enviar(msgs, Titulo, em);

                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Email não cadastrado";
                        String msg2 = "No Sistema.";
                        String tit = "Campos Vazios";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void textf_nickBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_nickBuscarMouseClicked
        textf_nickBuscar.setText("");

    }//GEN-LAST:event_textf_nickBuscarMouseClicked

    private void textf_nickBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nickBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscarPF(textf_nickBuscar.getText());
        }
    }//GEN-LAST:event_textf_nickBuscarKeyPressed

    private void LbuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LbuscarMouseClicked
        buscarPF(textf_nickBuscar.getText());
    }//GEN-LAST:event_LbuscarMouseClicked

    private void jbuttonArr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonArr1ActionPerformed
        verif();
    }//GEN-LAST:event_jbuttonArr1ActionPerformed

    private void jTextArea1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea1MouseClicked
        textarea_msg.setText(jTextArea1.getText());
    }//GEN-LAST:event_jTextArea1MouseClicked

    private void jTextArea2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea2MouseClicked
        textarea_msg.setText(jTextArea2.getText());
    }//GEN-LAST:event_jTextArea2MouseClicked

    private void jTextArea3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea3MouseClicked
        textarea_msg.setText(jTextArea3.getText());
    }//GEN-LAST:event_jTextArea3MouseClicked

    private void btn_limpar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpar2ActionPerformed
        audios("cl");
        limpar();
    }//GEN-LAST:event_btn_limpar2ActionPerformed

    private void jTextArea4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea4MouseClicked
        textarea_msg.setText(jTextArea4.getText());
    }//GEN-LAST:event_jTextArea4MouseClicked

    private void jTextArea5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea5MouseClicked
        textarea_msg.setText(jTextArea5.getText());
    }//GEN-LAST:event_jTextArea5MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Lbuscar;
    private SwingPerson.JbuttonArr btn_limpar2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private SwingPerson.JbuttonArr jbuttonArr1;
    private javax.swing.JLabel label_userBuscar;
    private javax.swing.JLabel labeltppes;
    private javax.swing.JLabel labeltppes1;
    private javax.swing.JTextArea textarea_msg;
    private javax.swing.JTextField textf_ass;
    private javax.swing.JTextField textf_email;
    private javax.swing.JTextField textf_nickBuscar;
    private javax.swing.JTextField textf_tpp;
    private javax.swing.JTextField textf_tpp1;
    // End of variables declaration//GEN-END:variables
}
