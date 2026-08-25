package GUI;

import InJframe.AdminBuscar;
import InJframe.ContPetBuscar;
import InJframe.ContServicoBuscar;
import InJframe.EnviarEmail;
import InJframe.Home;
import InJframe.Notificacao;
import InJframe.PFisicaBuscar;
import InJframe.PJuridicaBuscar;
import InJframe.PetBuscar;
import InJframe.ServicoBuscar;
import InJframe.Ticket;
import InJframe.Versao;
import Logar.login;
import Logar.versao;
import alert.alert;
import factory.ConnectionFactory;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import starter.Icone;

public class TelaPrin extends javax.swing.JFrame {

    private Connection connection;
    Boolean permchefe = false;
    String admin, audio;

    public TelaPrin(String ad, String au) {
        admin = ad;
        audio = au;
        initComponents();
        Status();
        selecthome();
        setIcon();
        bus(ad);
        if (permchefe == true) {
            btn_versao.setEnabled(true);
            btn_versao.setToolTipText("Campo da Versão");
            btn_versao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btn_vs.setEnabled(true);
            btn_vs.setToolTipText("Campo da Versão");
            btn_vs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            btn_email.setEnabled(true);
            btn_email.setToolTipText("Campo de Mandar E-mail");
            btn_email.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btn_em.setEnabled(true);
            btn_em.setToolTipText("Campo de Mandar E-mail");
            btn_em.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        } else {
            btn_versao.setEnabled(false);
            btn_versao.setToolTipText("Sem Permissão Para o CRUD de Versão");
            btn_versao.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            btn_vs.setEnabled(false);
            btn_vs.setToolTipText("Sem Permissão Para o CRUD de Versão");
            btn_vs.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

            btn_email.setEnabled(false);
            btn_email.setToolTipText("Campo de Mandar E-mail");
            btn_email.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btn_em.setEnabled(false);
            btn_em.setToolTipText("Campo de Mandar E-mail");
            btn_em.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        selectcorbtn();
        // Adicione um ouvinte para o evento personalizado "fecharJFrame"
        Toolkit.getDefaultToolkit().getSystemEventQueue().push(new EventQueue() {
            @Override
            protected void dispatchEvent(AWTEvent event) {
                if (event instanceof ActionEvent && "fecharJFrame".equals(((ActionEvent) event).getActionCommand())) { // Armazena o evento da JInternalFrame
                    // Armazena o evento da JInternalFrame
                    dispose(); // Fecha a JFrame pai
                }
                super.dispatchEvent(event);
            }
        });

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

                Laudio.setIcon(new ImageIcon(TelaPrin.class.getResource("/img/saudio.png")));
                Rdsta.setSelected(true);
                Laudio.setToolTipText("Áudio Desligado");
                break;
            case "on":

                Laudio.setIcon(new ImageIcon(TelaPrin.class.getResource("/img/audio.png")));
                Ratva.setSelected(true);
                Laudio.setToolTipText("Áudio Ligado");
                break;
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

    }

    public void fecharJanela() {
        dispose(); // Fecha a janela da TelaPrin
    }

    public void menuvertical() {
        int x = Panel_buscar.getWidth();

        if (x == 180) {
            Panel_buscar.setSize(x, 730);
            Thread th = new Thread() {
                @Override
                public void run() {
                    try {
                        // The loop should decrement i instead of incrementing it.
                        for (int i = 180; i >= 0; i--) {
                            Thread.sleep(1);
                            Panel_buscar.setSize(i, 730);  // Animate the width by decrementing i.
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            };
            th.start();
        } else if (x == 0) {
            Panel_buscar.show();
            Panel_buscar.setSize(x, 730);
            Thread th = new Thread() {
                @Override
                public void run() {
                    try {
                        // The loop should decrement i instead of incrementing it.
                        for (int i = 0; i <= 180; i++) {
                            Thread.sleep(1);
                            Panel_buscar.setSize(i, 730);  // Animate the width by incrementing i.
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            };
            th.start();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngroupaudio = new javax.swing.ButtonGroup();
        Panel_menu = new javax.swing.JPanel();
        Laudio = new javax.swing.JLabel();
        L_home = new javax.swing.JLabel();
        Lnmmenu = new javax.swing.JLabel();
        L_tela = new javax.swing.JLabel();
        Panel_buscar = new javax.swing.JPanel();
        Btn_adm = new javax.swing.JButton();
        Btn_pf = new javax.swing.JButton();
        Btn_pj = new javax.swing.JButton();
        Btn_servico = new javax.swing.JButton();
        Btn_pet = new javax.swing.JButton();
        Btn_contserv = new javax.swing.JButton();
        Btn_contpet = new javax.swing.JButton();
        btn_notificacao = new javax.swing.JButton();
        btn_email = new javax.swing.JButton();
        btn_versao = new javax.swing.JButton();
        btn_tic = new javax.swing.JButton();
        Panel_opc = new javax.swing.JPanel();
        Panel_mvertical = new javax.swing.JPanel();
        btn_adm = new SwingPerson.JbuttonArr();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_pf = new SwingPerson.JbuttonArr();
        btn_pj = new SwingPerson.JbuttonArr();
        btn_serv = new SwingPerson.JbuttonArr();
        btn_pet = new SwingPerson.JbuttonArr();
        btn_contserv = new SwingPerson.JbuttonArr();
        btn_contpet = new SwingPerson.JbuttonArr();
        btn_not = new SwingPerson.JbuttonArr();
        btn_em = new SwingPerson.JbuttonArr();
        btn_vs = new SwingPerson.JbuttonArr();
        btn_tick = new SwingPerson.JbuttonArr();
        menu_tp = new javax.swing.JMenuBar();
        menu_opc = new javax.swing.JMenu();
        menu_audio = new javax.swing.JMenu();
        Ratva = new javax.swing.JRadioButtonMenuItem();
        Rdsta = new javax.swing.JRadioButtonMenuItem();
        menu_vertical = new javax.swing.JMenuItem();
        menu_desl = new javax.swing.JMenuItem();
        menu_areaadm = new javax.swing.JMenu();
        menu_admin = new javax.swing.JMenuItem();
        menu_pets = new javax.swing.JMenuItem();
        menu_servicos = new javax.swing.JMenuItem();
        menu_pf = new javax.swing.JMenuItem();
        menu_pj = new javax.swing.JMenuItem();
        menu_not = new javax.swing.JMenuItem();
        menu_email = new javax.swing.JMenuItem();
        menu_versao = new javax.swing.JMenuItem();
        menu_sobre = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        menu_acess = new javax.swing.JMenu();
        menu_voz = new javax.swing.JMenuItem();
        menu_texto = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pet Found");
        setMinimumSize(new java.awt.Dimension(1360, 768));
        setPreferredSize(new java.awt.Dimension(1360, 768));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Panel_menu.setBackground(new java.awt.Color(255, 253, 243));
        Panel_menu.setPreferredSize(new java.awt.Dimension(0, 40));

        Laudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/audio.png"))); // NOI18N
        Laudio.setToolTipText("Saída de Audio");
        Laudio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Laudio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaudioMouseClicked(evt);
            }
        });

        L_home.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        L_home.setText("Pet Found");
        L_home.setToolTipText("Ir Para Home");
        L_home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        L_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                L_homeMouseClicked(evt);
            }
        });

        Lnmmenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Lnmmenu.setText("Home");
        Lnmmenu.setToolTipText("Localização do App");

        L_tela.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        L_tela.setText("Tela Principal/");

        javax.swing.GroupLayout Panel_menuLayout = new javax.swing.GroupLayout(Panel_menu);
        Panel_menu.setLayout(Panel_menuLayout);
        Panel_menuLayout.setHorizontalGroup(
            Panel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(L_home)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(L_tela)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Lnmmenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1046, Short.MAX_VALUE)
                .addComponent(Laudio)
                .addGap(23, 23, 23))
        );
        Panel_menuLayout.setVerticalGroup(
            Panel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_menuLayout.createSequentialGroup()
                .addGroup(Panel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Panel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(Panel_menuLayout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(Laudio))
                        .addComponent(L_home, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Panel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(Lnmmenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(L_tela, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(Panel_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 40));

        Panel_buscar.setBackground(new java.awt.Color(64, 33, 7));
        Panel_buscar.setPreferredSize(new java.awt.Dimension(0, 730));

        Btn_adm.setBackground(new java.awt.Color(64, 33, 7));
        Btn_adm.setForeground(new java.awt.Color(255, 255, 255));
        Btn_adm.setText("  Administrador");
        Btn_adm.setToolTipText("Campo do Administrador");
        Btn_adm.setBorder(null);
        Btn_adm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_adm.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        Btn_adm.setPreferredSize(new java.awt.Dimension(180, 42));
        Btn_adm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_admActionPerformed(evt);
            }
        });

        Btn_pf.setBackground(new java.awt.Color(64, 33, 7));
        Btn_pf.setForeground(new java.awt.Color(255, 255, 255));
        Btn_pf.setText("  Pessoa Física");
        Btn_pf.setBorder(null);
        Btn_pf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_pf.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        Btn_pf.setPreferredSize(new java.awt.Dimension(180, 42));
        Btn_pf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_pfActionPerformed(evt);
            }
        });

        Btn_pj.setBackground(new java.awt.Color(64, 33, 7));
        Btn_pj.setForeground(new java.awt.Color(255, 255, 255));
        Btn_pj.setText("  Pessoa Jurídica");
        Btn_pj.setToolTipText("Campo da Pessoa Jurídica");
        Btn_pj.setBorder(null);
        Btn_pj.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_pj.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        Btn_pj.setPreferredSize(new java.awt.Dimension(180, 42));
        Btn_pj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_pjActionPerformed(evt);
            }
        });

        Btn_servico.setBackground(new java.awt.Color(64, 33, 7));
        Btn_servico.setForeground(new java.awt.Color(255, 255, 255));
        Btn_servico.setText("  Serviço");
        Btn_servico.setToolTipText("Campo do Serviço");
        Btn_servico.setBorder(null);
        Btn_servico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_servico.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        Btn_servico.setPreferredSize(new java.awt.Dimension(180, 42));
        Btn_servico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_servicoActionPerformed(evt);
            }
        });

        Btn_pet.setBackground(new java.awt.Color(64, 33, 7));
        Btn_pet.setForeground(new java.awt.Color(255, 255, 255));
        Btn_pet.setText("  Pet");
        Btn_pet.setToolTipText("Campo do Pet");
        Btn_pet.setBorder(null);
        Btn_pet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_pet.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        Btn_pet.setPreferredSize(new java.awt.Dimension(180, 42));
        Btn_pet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_petActionPerformed(evt);
            }
        });

        Btn_contserv.setBackground(new java.awt.Color(64, 33, 7));
        Btn_contserv.setForeground(new java.awt.Color(255, 255, 255));
        Btn_contserv.setText("  Contato Serviço");
        Btn_contserv.setToolTipText("Campo do Contato de Serviço");
        Btn_contserv.setBorder(null);
        Btn_contserv.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_contserv.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        Btn_contserv.setPreferredSize(new java.awt.Dimension(180, 42));
        Btn_contserv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_contservActionPerformed(evt);
            }
        });

        Btn_contpet.setBackground(new java.awt.Color(64, 33, 7));
        Btn_contpet.setForeground(new java.awt.Color(255, 255, 255));
        Btn_contpet.setText("  Contato Pet");
        Btn_contpet.setToolTipText("Campo do Contato de Pet");
        Btn_contpet.setBorder(null);
        Btn_contpet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_contpet.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        Btn_contpet.setPreferredSize(new java.awt.Dimension(180, 42));
        Btn_contpet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_contpetActionPerformed(evt);
            }
        });

        btn_notificacao.setBackground(new java.awt.Color(64, 33, 7));
        btn_notificacao.setForeground(new java.awt.Color(255, 255, 255));
        btn_notificacao.setText("  Notificação");
        btn_notificacao.setToolTipText("Campo do Contato de Notificação");
        btn_notificacao.setBorder(null);
        btn_notificacao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_notificacao.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_notificacao.setPreferredSize(new java.awt.Dimension(180, 42));
        btn_notificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_notificacaoActionPerformed(evt);
            }
        });

        btn_email.setBackground(new java.awt.Color(64, 33, 7));
        btn_email.setForeground(new java.awt.Color(255, 255, 255));
        btn_email.setText("  Mandar E-mail");
        btn_email.setToolTipText("Campo de Mandar E-mail");
        btn_email.setBorder(null);
        btn_email.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_email.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_email.setPreferredSize(new java.awt.Dimension(180, 42));
        btn_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_emailActionPerformed(evt);
            }
        });

        btn_versao.setBackground(new java.awt.Color(64, 33, 7));
        btn_versao.setForeground(new java.awt.Color(255, 255, 255));
        btn_versao.setText("  Versão");
        btn_versao.setToolTipText("Campo da Versão");
        btn_versao.setBorder(null);
        btn_versao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_versao.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_versao.setPreferredSize(new java.awt.Dimension(180, 42));
        btn_versao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_versaoActionPerformed(evt);
            }
        });

        btn_tic.setBackground(new java.awt.Color(64, 33, 7));
        btn_tic.setForeground(new java.awt.Color(255, 255, 255));
        btn_tic.setText("  Ticket");
        btn_tic.setToolTipText("Campo de Ticket");
        btn_tic.setBorder(null);
        btn_tic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_tic.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_tic.setPreferredSize(new java.awt.Dimension(180, 42));
        btn_tic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ticActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_buscarLayout = new javax.swing.GroupLayout(Panel_buscar);
        Panel_buscar.setLayout(Panel_buscarLayout);
        Panel_buscarLayout.setHorizontalGroup(
            Panel_buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_buscarLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(Panel_buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_tic, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Panel_buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Btn_pet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Btn_servico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Btn_pj, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Btn_pf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Btn_adm, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Btn_contpet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Btn_contserv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_notificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_email, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_versao, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        Panel_buscarLayout.setVerticalGroup(
            Panel_buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_buscarLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(Btn_adm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_pf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_pj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_servico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_pet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_contserv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_contpet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_notificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_versao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_tic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
        );

        getContentPane().add(Panel_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 0, -1));

        Panel_opc.setBackground(new java.awt.Color(64, 33, 7));
        Panel_opc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Panel_opc.setPreferredSize(new java.awt.Dimension(1284, 666));

        javax.swing.GroupLayout Panel_opcLayout = new javax.swing.GroupLayout(Panel_opc);
        Panel_opc.setLayout(Panel_opcLayout);
        Panel_opcLayout.setHorizontalGroup(
            Panel_opcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
        );
        Panel_opcLayout.setVerticalGroup(
            Panel_opcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 662, Short.MAX_VALUE)
        );

        getContentPane().add(Panel_opc, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        Panel_mvertical.setBackground(new java.awt.Color(255, 148, 44));
        Panel_mvertical.setPreferredSize(new java.awt.Dimension(60, 740));
        Panel_mvertical.setRequestFocusEnabled(false);

        btn_adm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_admin2.png"))); // NOI18N
        btn_adm.setToolTipText("Campo do Administrador");
        btn_adm.setMaximumSize(new java.awt.Dimension(60, 42));
        btn_adm.setMinimumSize(new java.awt.Dimension(60, 42));
        btn_adm.setPreferredSize(new java.awt.Dimension(60, 42));
        btn_adm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_admActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/option.png"))); // NOI18N
        jLabel1.setToolTipText("Menu Vertical");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_sair2.png"))); // NOI18N
        jLabel3.setToolTipText("Deslogar do Sistem e Retornar a tela de Login");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        btn_pf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_user.png"))); // NOI18N
        btn_pf.setToolTipText("Campo da Pessoa Física");
        btn_pf.setMaximumSize(new java.awt.Dimension(60, 42));
        btn_pf.setMinimumSize(new java.awt.Dimension(60, 42));
        btn_pf.setPreferredSize(new java.awt.Dimension(60, 42));
        btn_pf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pfActionPerformed(evt);
            }
        });

        btn_pj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_ong.png"))); // NOI18N
        btn_pj.setToolTipText("Campo da Pessoa Jurídica");
        btn_pj.setMaximumSize(new java.awt.Dimension(60, 42));
        btn_pj.setMinimumSize(new java.awt.Dimension(60, 42));
        btn_pj.setPreferredSize(new java.awt.Dimension(60, 42));
        btn_pj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pjActionPerformed(evt);
            }
        });

        btn_serv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_serviços.png"))); // NOI18N
        btn_serv.setToolTipText("Campo do Serviço");
        btn_serv.setMaximumSize(new java.awt.Dimension(60, 42));
        btn_serv.setMinimumSize(new java.awt.Dimension(60, 42));
        btn_serv.setPreferredSize(new java.awt.Dimension(60, 42));
        btn_serv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_servActionPerformed(evt);
            }
        });

        btn_pet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_pets.png"))); // NOI18N
        btn_pet.setToolTipText("Campo do Pet");
        btn_pet.setMaximumSize(new java.awt.Dimension(60, 42));
        btn_pet.setMinimumSize(new java.awt.Dimension(60, 42));
        btn_pet.setPreferredSize(new java.awt.Dimension(60, 42));
        btn_pet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_petActionPerformed(evt);
            }
        });

        btn_contserv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_contserv.png"))); // NOI18N
        btn_contserv.setToolTipText("Campo do Contato de Serviço");
        btn_contserv.setMaximumSize(new java.awt.Dimension(60, 42));
        btn_contserv.setMinimumSize(new java.awt.Dimension(60, 42));
        btn_contserv.setPreferredSize(new java.awt.Dimension(60, 42));
        btn_contserv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_contservActionPerformed(evt);
            }
        });

        btn_contpet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_contapet.png"))); // NOI18N
        btn_contpet.setToolTipText("Campo do Contato de Pet");
        btn_contpet.setMaximumSize(new java.awt.Dimension(60, 42));
        btn_contpet.setMinimumSize(new java.awt.Dimension(60, 42));
        btn_contpet.setPreferredSize(new java.awt.Dimension(60, 42));
        btn_contpet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_contpetActionPerformed(evt);
            }
        });

        btn_not.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_not.png"))); // NOI18N
        btn_not.setToolTipText("Campo de enviar Notificação");
        btn_not.setMaximumSize(new java.awt.Dimension(60, 42));
        btn_not.setMinimumSize(new java.awt.Dimension(60, 42));
        btn_not.setPreferredSize(new java.awt.Dimension(60, 42));
        btn_not.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_notActionPerformed(evt);
            }
        });

        btn_em.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_email.png"))); // NOI18N
        btn_em.setToolTipText("Campo de Mandar E-mail");
        btn_em.setMaximumSize(new java.awt.Dimension(60, 42));
        btn_em.setMinimumSize(new java.awt.Dimension(60, 42));
        btn_em.setPreferredSize(new java.awt.Dimension(60, 42));
        btn_em.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_emActionPerformed(evt);
            }
        });

        btn_vs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_versao.png"))); // NOI18N
        btn_vs.setToolTipText("Campo da Versão");
        btn_vs.setMaximumSize(new java.awt.Dimension(60, 42));
        btn_vs.setMinimumSize(new java.awt.Dimension(60, 42));
        btn_vs.setPreferredSize(new java.awt.Dimension(60, 42));
        btn_vs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vsActionPerformed(evt);
            }
        });

        btn_tick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_ticket.png"))); // NOI18N
        btn_tick.setToolTipText("Campo da Versão");
        btn_tick.setMaximumSize(new java.awt.Dimension(60, 42));
        btn_tick.setMinimumSize(new java.awt.Dimension(60, 42));
        btn_tick.setPreferredSize(new java.awt.Dimension(60, 42));
        btn_tick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tickActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_mverticalLayout = new javax.swing.GroupLayout(Panel_mvertical);
        Panel_mvertical.setLayout(Panel_mverticalLayout);
        Panel_mverticalLayout.setHorizontalGroup(
            Panel_mverticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_mverticalLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(4, 4, 4))
            .addGroup(Panel_mverticalLayout.createSequentialGroup()
                .addComponent(btn_adm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_mverticalLayout.createSequentialGroup()
                .addGroup(Panel_mverticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_serv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_pet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_pf, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_pj, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_contserv, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_contpet, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_not, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_em, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_vs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_mverticalLayout.createSequentialGroup()
                .addComponent(btn_tick, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Panel_mverticalLayout.setVerticalGroup(
            Panel_mverticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_mverticalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(btn_adm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_pf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_pj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_serv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_pet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_contserv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_contpet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_not, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_em, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_vs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_tick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(59, 59, 59))
        );

        getContentPane().add(Panel_mvertical, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 60, 710));

        menu_tp.setBackground(new java.awt.Color(255, 253, 243));
        menu_tp.setBorder(null);
        menu_tp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        menu_opc.setText("Opções");
        menu_opc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menu_audio.setText("Audio");
        menu_audio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Ratva.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        btngroupaudio.add(Ratva);
        Ratva.setSelected(true);
        Ratva.setText("Ativar Audio");
        Ratva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Ratva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RatvaActionPerformed(evt);
            }
        });
        menu_audio.add(Ratva);

        Rdsta.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        btngroupaudio.add(Rdsta);
        Rdsta.setText("Desativar Audio");
        Rdsta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Rdsta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RdstaActionPerformed(evt);
            }
        });
        menu_audio.add(Rdsta);

        menu_opc.add(menu_audio);

        menu_vertical.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menu_vertical.setText("Abrir Menu Vertical");
        menu_vertical.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_vertical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_verticalActionPerformed(evt);
            }
        });
        menu_opc.add(menu_vertical);

        menu_desl.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        menu_desl.setText("Deslogar");
        menu_desl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_desl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_deslActionPerformed(evt);
            }
        });
        menu_opc.add(menu_desl);

        menu_tp.add(menu_opc);

        menu_areaadm.setText("Área do Administrador");
        menu_areaadm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menu_admin.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menu_admin.setText("Administradores");
        menu_admin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_adminActionPerformed(evt);
            }
        });
        menu_areaadm.add(menu_admin);

        menu_pets.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menu_pets.setText("Pets");
        menu_pets.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_pets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_petsActionPerformed(evt);
            }
        });
        menu_areaadm.add(menu_pets);

        menu_servicos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menu_servicos.setText("Serviços");
        menu_servicos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_servicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_servicosActionPerformed(evt);
            }
        });
        menu_areaadm.add(menu_servicos);

        menu_pf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menu_pf.setText("Pessoa Física");
        menu_pf.setToolTipText("");
        menu_pf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_pf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_pfActionPerformed(evt);
            }
        });
        menu_areaadm.add(menu_pf);

        menu_pj.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menu_pj.setText("Pessoa Jurídica");
        menu_pj.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_pj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_pjActionPerformed(evt);
            }
        });
        menu_areaadm.add(menu_pj);

        menu_not.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menu_not.setText("Enviar Notificação");
        menu_not.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_not.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_notActionPerformed(evt);
            }
        });
        menu_areaadm.add(menu_not);

        menu_email.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menu_email.setText("Enviar E-mail");
        menu_email.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_emailActionPerformed(evt);
            }
        });
        menu_areaadm.add(menu_email);

        menu_versao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menu_versao.setText("Versão");
        menu_versao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_versao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_versaoActionPerformed(evt);
            }
        });
        menu_areaadm.add(menu_versao);

        menu_tp.add(menu_areaadm);

        menu_sobre.setText("Sobre");
        menu_sobre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem1.setText("Versão");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menu_sobre.add(jMenuItem1);

        menu_tp.add(menu_sobre);

        menu_acess.setText("Acessibilidade");
        menu_acess.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menu_voz.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menu_voz.setText("Ativar comando por voz");
        menu_voz.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_voz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_vozActionPerformed(evt);
            }
        });
        menu_acess.add(menu_voz);

        menu_texto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        menu_texto.setText("Ativar leitura de texto");
        menu_texto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_texto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_textoActionPerformed(evt);
            }
        });
        menu_acess.add(menu_texto);

        menu_tp.add(menu_acess);

        setJMenuBar(menu_tp);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void inm() {
        alert al = new alert(admin, audio);
        al.setVisible(true);
        String msg = "Indisponível no momento";
        String tit = "Página em manutenção";
        al.alertinput(tit, "info", "", msg, "", "info");
    }

    public void vlt() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?");
        if (escolha == 0) {
            audios("tc");
            login te = new login(audio);
            te.setVisible(true);
            dispose();
        }
    }

    public void selectpet(String pf) {
        PetBuscar op1 = new PetBuscar(admin, audio, pf);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }

    public void selectpet() {
        PetBuscar op1 = new PetBuscar(admin, audio, null);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }

    public void selectadm() {
        AdminBuscar op1 = new AdminBuscar(admin, audio);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }

    public void selectpf() {
        PFisicaBuscar op1 = new PFisicaBuscar(admin, audio, this); // Passa a referência de TelaPrin
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate();
        Panel_opc.repaint();
        selectcorbtn();
    }

    public void selecthome() {
        Home op1 = new Home(admin, audio, this);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }

    public void selectcontpet() {
        ContPetBuscar op1 = new ContPetBuscar(admin, audio);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }

    public void selectpj() {
        PJuridicaBuscar op1 = new PJuridicaBuscar(admin, audio, this);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }

    public void selectserv() {
        ServicoBuscar op1 = new ServicoBuscar(admin, audio, null);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }

    public void selectserv(String p) {
        ServicoBuscar op1 = new ServicoBuscar(admin, audio, p);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }

    public void selectcontserv() {
        ContServicoBuscar op1 = new ContServicoBuscar(admin, audio);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }

    public void selecversao() {
        Versao op1 = new Versao(admin, audio);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }

    public void selectem() {
        EnviarEmail op1 = new EnviarEmail(admin, audio);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }

    public void selectnot() {
        Notificacao op1 = new Notificacao(admin, audio);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }
    
    public void selecttic() {
        Ticket op1 = new Ticket(admin, audio);
        Panel_opc.removeAll();
        Panel_opc.add(op1).setVisible(true);
        Panel_opc.revalidate(); // Revalidate the container after making changes
        Panel_opc.repaint();
        selectcorbtn();
    }
    
    public void selectcorbtn() {
        Color clickedcolor = new Color(255, 148, 44);
        Color defaultcolor = new Color(64, 33, 7);
        //LineBorder bordadefault = new LineBorder(new Color(255, 148, 44), 2);
        LineBorder bordaclicke = new LineBorder(Color.BLACK, 3);

        Btn_adm.setBackground(defaultcolor);
        Btn_pf.setBackground(defaultcolor);
        Btn_pj.setBackground(defaultcolor);
        Btn_servico.setBackground(defaultcolor);
        Btn_pet.setBackground(defaultcolor);
        Btn_contserv.setBackground(defaultcolor);
        Btn_contpet.setBackground(defaultcolor);
        btn_adm.setBorder(null);
        btn_pf.setBorder(null);
        btn_pj.setBorder(null);
        btn_serv.setBorder(null);
        btn_pet.setBorder(null);
        btn_contserv.setBorder(null);
        btn_contpet.setBorder(null);
        btn_em.setBorder(null);
        btn_email.setBackground(defaultcolor);
        btn_vs.setBorder(null);
        btn_versao.setBackground(defaultcolor);
        btn_not.setBorder(null);
        btn_notificacao.setBackground(defaultcolor);
        btn_tic.setBackground(defaultcolor);
        btn_tick.setBorder(null);

        if (null != Lnmmenu.getText()) {
            switch (Lnmmenu.getText()) {
                case "Buscar Administrador":
                    Btn_adm.setBackground(clickedcolor);
                    btn_adm.setBorder(bordaclicke);
                    break;
                case "Buscar Pessoa Física":
                    Btn_pf.setBackground(clickedcolor);
                    btn_pf.setBorder(bordaclicke);
                    break;
                case "Buscar Contato Pet":
                    btn_contpet.setBorder(bordaclicke);
                    Btn_contpet.setBackground(clickedcolor);
                    break;
                case "Buscar Pessoa Jurídica":
                    Btn_pj.setBackground(clickedcolor);
                    btn_pj.setBorder(bordaclicke);
                    break;
                case "Buscar Contato Serviço":
                    btn_contserv.setBorder(bordaclicke);
                    Btn_contserv.setBackground(clickedcolor);
                    break;
                case "Buscar Serviço":
                    btn_serv.setBorder(bordaclicke);
                    Btn_servico.setBackground(clickedcolor);
                    break;
                case "Buscar Pet":
                    btn_pet.setBorder(bordaclicke);
                    Btn_pet.setBackground(clickedcolor);
                    break;
                case "Enviar E-mail":
                    btn_em.setBorder(bordaclicke);
                    btn_email.setBackground(clickedcolor);
                    break;
                case "Notificação":
                    btn_not.setBorder(bordaclicke);
                    btn_notificacao.setBackground(clickedcolor);
                    break;
                case "Versão":
                    btn_vs.setBorder(bordaclicke);
                    btn_versao.setBackground(clickedcolor);
                    break;
                case "Ticket":
                    btn_tic.setBorder(bordaclicke);
                    btn_tick.setBackground(clickedcolor);
                    break;
                default:
                    break;
            }
        }
    }
    
    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        audios("cl");
        menuvertical();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void LaudioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaudioMouseClicked
        audios("cl");
        if (audio == "off") {
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
        } else {

            audio = "off";
            Status();
        }
    }//GEN-LAST:event_LaudioMouseClicked

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

    private void menu_deslActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_deslActionPerformed
        vlt();
    }//GEN-LAST:event_menu_deslActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        audios("tci");
        versao v = new versao();
        v.buscarVersao(); // Buscar versões antes de exibir a janela
        v.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void menu_vozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_vozActionPerformed
        inm();
    }//GEN-LAST:event_menu_vozActionPerformed

    private void menu_textoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_textoActionPerformed
        inm();
    }//GEN-LAST:event_menu_textoActionPerformed

    private void menu_petsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_petsActionPerformed
        Lnmmenu.setText("Buscar Pet");
        selectpet();
    }//GEN-LAST:event_menu_petsActionPerformed

    private void menu_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_adminActionPerformed
        Lnmmenu.setText("Buscar Administrador");
        selectadm();
    }//GEN-LAST:event_menu_adminActionPerformed

    private void menu_servicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_servicosActionPerformed
        Lnmmenu.setText("Buscar Serviço");
        selectserv();
    }//GEN-LAST:event_menu_servicosActionPerformed

    private void btn_admActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_admActionPerformed
        audios("tci");
        Lnmmenu.setText("Buscar Administrador");
        selectadm();
    }//GEN-LAST:event_btn_admActionPerformed

    private void L_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_homeMouseClicked
        audios("cl");
        selecthome();
    }//GEN-LAST:event_L_homeMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        vlt();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void Btn_pfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_pfActionPerformed
        Lnmmenu.setText("Buscar Pessoa Física");
        selectpet();
        menuvertical();
    }//GEN-LAST:event_Btn_pfActionPerformed

    private void Btn_contpetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_contpetActionPerformed
        Lnmmenu.setText("Buscar Contato Pet");
        selectcontpet();
        menuvertical();
    }//GEN-LAST:event_Btn_contpetActionPerformed

    private void btn_pfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pfActionPerformed
        audios("tci");
        Lnmmenu.setText("Buscar Pessoa Física");
        selectpf();
    }//GEN-LAST:event_btn_pfActionPerformed

    private void btn_pjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pjActionPerformed
        audios("tci");
        Lnmmenu.setText("Buscar Pessoa Jurídica");
        selectpj();
    }//GEN-LAST:event_btn_pjActionPerformed

    private void btn_servActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_servActionPerformed
        audios("tci");
        Lnmmenu.setText("Buscar Serviço");
        selectserv();
    }//GEN-LAST:event_btn_servActionPerformed

    private void btn_petActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_petActionPerformed
        audios("tci");
        Lnmmenu.setText("Buscar Pet");
        selectpet();
    }//GEN-LAST:event_btn_petActionPerformed

    private void btn_contservActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_contservActionPerformed
        audios("tci");
        Lnmmenu.setText("Buscar Contato Serviço");
        selectcontserv();
    }//GEN-LAST:event_btn_contservActionPerformed

    private void btn_contpetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_contpetActionPerformed
        audios("tci");
        Lnmmenu.setText("Buscar Contato Pet");
        selectcontpet();
    }//GEN-LAST:event_btn_contpetActionPerformed

    private void btn_notActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_notActionPerformed
        audios("tci");
        Lnmmenu.setText("Notificação");
        selectnot();
    }//GEN-LAST:event_btn_notActionPerformed

    private void btn_emActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_emActionPerformed
        audios("tci");
        Lnmmenu.setText("Enviar E-mail");
        selectem();
    }//GEN-LAST:event_btn_emActionPerformed

    private void btn_vsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vsActionPerformed
        audios("tci");
        Lnmmenu.setText("Versão");
        selecversao();
    }//GEN-LAST:event_btn_vsActionPerformed

    private void Btn_petActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_petActionPerformed
        Lnmmenu.setText("Buscar Pet");
        selectpet();
        menuvertical();
    }//GEN-LAST:event_Btn_petActionPerformed

    private void Btn_admActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_admActionPerformed
        Lnmmenu.setText("Buscar Administrador");
        selectadm();
        menuvertical();
    }//GEN-LAST:event_Btn_admActionPerformed

    private void Btn_servicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_servicoActionPerformed
        Lnmmenu.setText("Buscar Serviço");
        selectserv();
        menuvertical();
    }//GEN-LAST:event_Btn_servicoActionPerformed

    private void Btn_pjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_pjActionPerformed
        Lnmmenu.setText("Buscar Pessoa Jurídica");
        selectpj();
        menuvertical();
    }//GEN-LAST:event_Btn_pjActionPerformed

    private void Btn_contservActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_contservActionPerformed
        Lnmmenu.setText("Buscar Contato Serviço");
        selectcontserv();
        menuvertical();
    }//GEN-LAST:event_Btn_contservActionPerformed

    private void btn_versaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_versaoActionPerformed
        Lnmmenu.setText("Versão");
        selecversao();
        menuvertical();
    }//GEN-LAST:event_btn_versaoActionPerformed

    private void btn_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_emailActionPerformed
        Lnmmenu.setText("Enviar E-mail");
        selectem();
        menuvertical();
    }//GEN-LAST:event_btn_emailActionPerformed

    private void btn_notificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_notificacaoActionPerformed
        Lnmmenu.setText("Notificação");
        selectnot();
        menuvertical();
    }//GEN-LAST:event_btn_notificacaoActionPerformed

    private void menu_pfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_pfActionPerformed
        Lnmmenu.setText("Buscar Pessoa Física");
        selectpf();
    }//GEN-LAST:event_menu_pfActionPerformed

    private void menu_pjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_pjActionPerformed
        Lnmmenu.setText("Buscar Pessoa Jurídica");
        selectpj();
    }//GEN-LAST:event_menu_pjActionPerformed

    private void menu_versaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_versaoActionPerformed
        Lnmmenu.setText("Versão");
        selecversao();
    }//GEN-LAST:event_menu_versaoActionPerformed

    private void menu_notActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_notActionPerformed
        Lnmmenu.setText("Notificação");
        selectnot();
    }//GEN-LAST:event_menu_notActionPerformed

    private void menu_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_emailActionPerformed
        Lnmmenu.setText("Enviar E-mail");
        selectem();
    }//GEN-LAST:event_menu_emailActionPerformed

    private void menu_verticalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_verticalActionPerformed
        menuvertical();
    }//GEN-LAST:event_menu_verticalActionPerformed

    private void btn_tickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tickActionPerformed
        audios("tci");
        Lnmmenu.setText("Ticket");
        selecttic();
    }//GEN-LAST:event_btn_tickActionPerformed

    private void btn_ticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ticActionPerformed
        Lnmmenu.setText("Ticket");
        selecttic();
        menuvertical();
    }//GEN-LAST:event_btn_ticActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String au = "";
                String adm = "";
                new TelaPrin(adm, au).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_adm;
    private javax.swing.JButton Btn_contpet;
    private javax.swing.JButton Btn_contserv;
    private javax.swing.JButton Btn_pet;
    private javax.swing.JButton Btn_pf;
    private javax.swing.JButton Btn_pj;
    private javax.swing.JButton Btn_servico;
    private javax.swing.JLabel L_home;
    private javax.swing.JLabel L_tela;
    private javax.swing.JLabel Laudio;
    private javax.swing.JLabel Lnmmenu;
    private javax.swing.JPanel Panel_buscar;
    private javax.swing.JPanel Panel_menu;
    private javax.swing.JPanel Panel_mvertical;
    private javax.swing.JPanel Panel_opc;
    private javax.swing.JRadioButtonMenuItem Ratva;
    private javax.swing.JRadioButtonMenuItem Rdsta;
    private SwingPerson.JbuttonArr btn_adm;
    private SwingPerson.JbuttonArr btn_contpet;
    private SwingPerson.JbuttonArr btn_contserv;
    private SwingPerson.JbuttonArr btn_em;
    private javax.swing.JButton btn_email;
    private SwingPerson.JbuttonArr btn_not;
    private javax.swing.JButton btn_notificacao;
    private SwingPerson.JbuttonArr btn_pet;
    private SwingPerson.JbuttonArr btn_pf;
    private SwingPerson.JbuttonArr btn_pj;
    private SwingPerson.JbuttonArr btn_serv;
    private javax.swing.JButton btn_tic;
    private SwingPerson.JbuttonArr btn_tick;
    private javax.swing.JButton btn_versao;
    private SwingPerson.JbuttonArr btn_vs;
    private javax.swing.ButtonGroup btngroupaudio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenu menu_acess;
    private javax.swing.JMenuItem menu_admin;
    private javax.swing.JMenu menu_areaadm;
    private javax.swing.JMenu menu_audio;
    private javax.swing.JMenuItem menu_desl;
    private javax.swing.JMenuItem menu_email;
    private javax.swing.JMenuItem menu_not;
    private javax.swing.JMenu menu_opc;
    private javax.swing.JMenuItem menu_pets;
    private javax.swing.JMenuItem menu_pf;
    private javax.swing.JMenuItem menu_pj;
    private javax.swing.JMenuItem menu_servicos;
    private javax.swing.JMenu menu_sobre;
    private javax.swing.JMenuItem menu_texto;
    private javax.swing.JMenuBar menu_tp;
    private javax.swing.JMenuItem menu_versao;
    private javax.swing.JMenuItem menu_vertical;
    private javax.swing.JMenuItem menu_voz;
    // End of variables declaration//GEN-END:variables
}
