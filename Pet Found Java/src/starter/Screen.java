package starter;

import Logar.login;
import factory.ConnectionFactory;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class Screen extends javax.swing.JFrame {

    public String audio;
    private Connection connection;

    public Screen() {
        initComponents();
        setIcon();
        checkSaidaAudio();
        if (isConnectionOK()) {
            buscarVersao();
        }
        desingbar();
    }

    public boolean isConnectionOK() {

        this.connection = new ConnectionFactory().getConnection();

        if (connection == null) {
            Lversion.setText("Banco Off"); // Caso nenhuma versão seja encontrada
            return false;
        } else {
            String sql = "SELECT * FROM versao ORDER BY codv DESC LIMIT 1"; // Selecionar a versão mais recente
            try {
                PreparedStatement stm = connection.prepareStatement(sql);
                ResultSet res = stm.executeQuery();

                if (res.next()) {
                    String versaoMaisRecente = res.getString("codv"); // Substitua "versao_coluna" pelo nome real da coluna
                    Lversion.setText(versaoMaisRecente); // Atualizar a label com a versão mais recente
                } else {
                    Lversion.setText("v0.0.0"); // Caso nenhuma versão seja encontrada
                }

                stm.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        }
        return true;
    }

    public void desingbar() {
        BarraProgresso.setUI(new BasicProgressBarUI() {
            @Override
            protected void paintDeterminate(Graphics g, JComponent jc) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int larg = BarraProgresso.getWidth();
                int alt = BarraProgresso.getHeight();

                double percent = BarraProgresso.getPercentComplete();

                int espacolarg = (int) (larg * percent);
                int espacoalt = alt;

                // Preencha a parte que representa o carregamento com uma cor e cantos arredondados
                g2d.setColor(new Color(240, 145, 90)); // Cor do interior
                RoundRectangle2D.Double arre = new RoundRectangle2D.Double(0, 0, espacolarg, espacoalt, espacoalt, espacoalt);
                g2d.fill(arre);

                ImageIcon img = new ImageIcon(Screen.class.getResource("/img/patabar.png"));
                g2d.drawImage(img.getImage(), espacolarg - 26, -1, null);
            }
        });
    }

    public void buscarVersao() {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM versao ORDER BY codv DESC LIMIT 1"; // Selecionar a versão mais recente
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                String versaoMaisRecente = res.getString("codv"); // Substitua "versao_coluna" pelo nome real da coluna
                Lversion.setText(versaoMaisRecente); // Atualizar a label com a versão mais recente
            } else {
                Lversion.setText("v0.0.0"); // Caso nenhuma versão seja encontrada
            }

            stm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setIcon() {
        Icone ic = new Icone();
        String cm = ic.getIcon();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(cm)));
    }

    public static void desingtipo() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException e) {

            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();

        } catch (ClassNotFoundException e) {

            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();

        } catch (InstantiationException e) {

            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();

        } catch (IllegalAccessException e) {

            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void checkSaidaAudio() {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        if (mixers.length == 0) {

            audio = "off";
        } else {
            audio = "on";
            for (Mixer.Info mixerInfo : mixers) {
                // System.out.println(mixerInfo);
            }
        }
    }

    public String getAudio() {
        return audio;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Lnmprj = new javax.swing.JLabel();
        progresso = new javax.swing.JLabel();
        BarraProgresso = new javax.swing.JProgressBar();
        Lbemv = new javax.swing.JLabel();
        Lversion = new javax.swing.JLabel();
        LFundo1 = new javax.swing.JLabel();
        LFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(600, 300));
        setResizable(false);
        getContentPane().setLayout(null);

        Lnmprj.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Lnmprj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lnmprj.setText("Pet Found");
        getContentPane().add(Lnmprj);
        Lnmprj.setBounds(0, 120, 510, 32);

        progresso.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        progresso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        progresso.setText("0%");
        getContentPane().add(progresso);
        progresso.setBounds(0, 210, 510, 20);

        BarraProgresso.setBackground(new java.awt.Color(255, 255, 255));
        BarraProgresso.setForeground(new java.awt.Color(240, 145, 90));
        BarraProgresso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 0));
        getContentPane().add(BarraProgresso);
        BarraProgresso.setBounds(60, 180, 390, 23);

        Lbemv.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Lbemv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lbemv.setText("Bem Vindo(a)");
        getContentPane().add(Lbemv);
        Lbemv.setBounds(0, 70, 510, 60);

        Lversion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Lversion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lversion.setText("v0.0.0");
        getContentPane().add(Lversion);
        Lversion.setBounds(430, 210, 80, 60);

        LFundo1.setForeground(new java.awt.Color(153, 153, 153));
        LFundo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fundo.jpg"))); // NOI18N
        LFundo1.setText(".");
        LFundo1.setToolTipText("");
        LFundo1.setMinimumSize(new java.awt.Dimension(600, 600));
        LFundo1.setPreferredSize(new java.awt.Dimension(600, 300));
        getContentPane().add(LFundo1);
        LFundo1.setBounds(-170, 60, 690, 270);

        LFundo.setForeground(new java.awt.Color(153, 153, 153));
        LFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/pata branca.gif"))); // NOI18N
        LFundo.setText(".");
        LFundo.setToolTipText("");
        LFundo.setMinimumSize(new java.awt.Dimension(600, 600));
        LFundo.setPreferredSize(new java.awt.Dimension(600, 300));
        getContentPane().add(LFundo);
        LFundo.setBounds(-175, 0, 750, 60);

        setSize(new java.awt.Dimension(512, 254));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        desingtipo();
        //</editor-fold>
        Screen sc = new Screen();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                sc.setVisible(true);
            }
        });

        String audioStatus = sc.getAudio();
        login log = new login(audioStatus);
        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(40);
                sc.BarraProgresso.setValue(i);
                sc.progresso.setText(Integer.toString(i) + "%");
            }
        } catch (Exception e) {

        }

        new Screen().setVisible(false);
        log.setVisible(true);
        sc.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar BarraProgresso;
    private javax.swing.JLabel LFundo;
    private javax.swing.JLabel LFundo1;
    private javax.swing.JLabel Lbemv;
    private javax.swing.JLabel Lnmprj;
    private javax.swing.JLabel Lversion;
    private javax.swing.JLabel progresso;
    // End of variables declaration//GEN-END:variables
}
