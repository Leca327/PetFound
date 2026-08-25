package subGUI;

import alert.alert;
import factory.ConnectionFactory;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import starter.Icone;

public class PetReprovar extends javax.swing.JFrame {

    private Connection connection;
    String adm, audio, pet, codadm;

    public PetReprovar(String adm, String aud, String pet) {
        this.adm = adm;
        audio = aud;
        this.pet = pet;
        initComponents();
        bus(adm);
        setIcon();
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

    public void limpar() {
        texarea_mt.setText("");
    }

    public void vlt() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja sair?");
        if (escolha == 0) {
            audios("tc");
            dispose();
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
                codadm = tx4;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void enviar() {
        this.connection = new ConnectionFactory().getConnection();

        //Connection con;
        PreparedStatement ps;
        String status = "";
        try {
            ps = connection.prepareStatement("update pet set aprovacaopet=? ,admincodadmn=?,motivoreppet=? where petcod=?");
            ps.setBoolean(1, false);
            ps.setString(2, codadm);
            ps.setString(3, texarea_mt.getText());
            ps.setString(4, pet);
            int i = ps.executeUpdate();
            if (i != 0) {
                alert al = new alert(adm, audio);
                al.setVisible(true);
                String msg = "Detalhes do Pet";
                String msg2 = "atualizados com sucesso";
                String tit = "Atualização de Pet";
                al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                dispose();

            } else {
                alert al = new alert(adm, audio);
                al.setVisible(true);
                String msg = "Detalhes do Pet";
                String msg2 = "não atualizados";
                String tit = "Atualização de Pet";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn_cadastrar = new SwingPerson.JbuttonArr();
        btn_limpar = new SwingPerson.JbuttonArr();
        L_vlt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        texarea_mt = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        lfundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pet Found - Reprovar Pet");
        setPreferredSize(new java.awt.Dimension(658, 338));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 253, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_cadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_rep.png"))); // NOI18N
        btn_cadastrar.setToolTipText("Cadastre Um Admin");
        btn_cadastrar.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadastrarActionPerformed(evt);
            }
        });

        btn_limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_apagar.png"))); // NOI18N
        btn_limpar.setToolTipText("Limpar Todos os Campos");
        btn_limpar.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparActionPerformed(evt);
            }
        });

        L_vlt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_voltar.png"))); // NOI18N
        L_vlt.setToolTipText("Sair de Cadastrar");
        L_vlt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        L_vlt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                L_vltMouseClicked(evt);
            }
        });

        texarea_mt.setColumns(20);
        texarea_mt.setLineWrap(true);
        texarea_mt.setRows(5);
        texarea_mt.setWrapStyleWord(true);
        jScrollPane1.setViewportView(texarea_mt);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Motivo da Reprovação:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(L_vlt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_cadastrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L_vlt, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_limpar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 583, 240));

        lfundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fundo.jpg"))); // NOI18N
        getContentPane().add(lfundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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


    private void btn_cadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadastrarActionPerformed
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Reprovar O Pet?");

        if (escolha == 0) {
            enviar();
        }
    }//GEN-LAST:event_btn_cadastrarActionPerformed

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        audios("cl");
        lmp();
    }//GEN-LAST:event_btn_limparActionPerformed

    private void L_vltMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_vltMouseClicked
        vlt();
    }//GEN-LAST:event_L_vltMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PetReprovar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PetReprovar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PetReprovar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PetReprovar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String adm = "";
                String au = "";
                String pet = "";
                new PetReprovar(adm, au, pet).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel L_vlt;
    private SwingPerson.JbuttonArr btn_cadastrar;
    private SwingPerson.JbuttonArr btn_limpar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lfundo;
    private javax.swing.JTextArea texarea_mt;
    // End of variables declaration//GEN-END:variables
}
