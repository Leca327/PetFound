package InJframe;

import GUI.TelaPrin;
import Logar.login;
import factory.ConnectionFactory;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import subGUI.AdminAtualizar;

public class Home extends javax.swing.JInternalFrame {

    private Connection connection;
    String admin, audio;
    private TelaPrin telaPrin;

    public Home(String ad, String au, TelaPrin telaPrin) {
        admin = ad;
        audio = au;
        this.telaPrin=telaPrin;
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        //
        bus(admin);
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

                Lusern.setText(tx1);
                // Obtenha as dimensões da label
                int labelWidth = Limg.getWidth();
                int labelHeight = Limg.getHeight();

                // Verifica se há imagem
                if (imageBytes != null && imageBytes.length > 0) {
                    ImageIcon imageIcon = new ImageIcon(imageBytes);
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(299, 299, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);

                    if (Limg != null) {
                        Limg.setIcon(scaledIcon);
                    } else {
                        Limg.setText("Sem foto");
                    }
                } else {
                    // Obtenha a imagem original
                    ImageIcon icon = new ImageIcon(getClass().getResource("/img/semimg.png"));
                    Image image = icon.getImage();

                    // Redimensione a imagem de acordo com as dimensões da label
                    Image scaledImage = image.getScaledInstance(299, 299, Image.SCALE_SMOOTH);

                    // Crie um novo ícone com a imagem redimensionada
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);

                    // Atribua o novo ícone à label
                    Limg.setIcon(scaledIcon);
                }

                Boolean tx5 = res.getBoolean(7);//chefe
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel_opc = new javax.swing.JPanel();
        label_ogo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jbuttonArr1 = new SwingPerson.JbuttonArr();
        Lusern = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Limg = new javax.swing.JLabel();
        LAdminLog = new javax.swing.JLabel();

        setBorder(null);
        setPreferredSize(new java.awt.Dimension(1284, 666));

        Panel_opc.setBackground(new java.awt.Color(64, 33, 7));
        Panel_opc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Panel_opc.setPreferredSize(new java.awt.Dimension(1284, 666));

        label_ogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_ogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbuttonArr1.setText("Editar Informações");
        jbuttonArr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonArr1ActionPerformed(evt);
            }
        });
        jPanel4.add(jbuttonArr1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 480, 130, -1));

        Lusern.setBackground(new java.awt.Color(64, 33, 7));
        Lusern.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        Lusern.setForeground(new java.awt.Color(64, 33, 7));
        Lusern.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lusern.setText("Adm");
        jPanel4.add(Lusern, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 349, 60));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fundohome.png"))); // NOI18N
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        Limg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Limg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 243)));
        Limg.setMaximumSize(new java.awt.Dimension(299, 299));
        Limg.setMinimumSize(new java.awt.Dimension(299, 299));
        Limg.setPreferredSize(new java.awt.Dimension(299, 299));
        Limg.setVerifyInputWhenFocusTarget(false);
        jPanel4.add(Limg, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, -1, -1));

        LAdminLog.setBackground(new java.awt.Color(255, 148, 44));
        LAdminLog.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        LAdminLog.setForeground(new java.awt.Color(255, 148, 44));
        LAdminLog.setText("Bem Vindo(a)");

        javax.swing.GroupLayout Panel_opcLayout = new javax.swing.GroupLayout(Panel_opc);
        Panel_opc.setLayout(Panel_opcLayout);
        Panel_opcLayout.setHorizontalGroup(
            Panel_opcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_opcLayout.createSequentialGroup()
                .addGroup(Panel_opcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_opcLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(LAdminLog))
                    .addGroup(Panel_opcLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(label_ogo, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        Panel_opcLayout.setVerticalGroup(
            Panel_opcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_opcLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LAdminLog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label_ogo, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1284, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Panel_opc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 666, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Panel_opc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbuttonArr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonArr1ActionPerformed
        audios("cl");
        AdminAtualizar aa = new AdminAtualizar(admin, audio, admin, telaPrin);
        aa.setVisible(true);

    }//GEN-LAST:event_jbuttonArr1ActionPerformed
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LAdminLog;
    private javax.swing.JLabel Limg;
    private javax.swing.JLabel Lusern;
    private javax.swing.JPanel Panel_opc;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel4;
    private SwingPerson.JbuttonArr jbuttonArr1;
    private javax.swing.JLabel label_ogo;
    // End of variables declaration//GEN-END:variables
}
