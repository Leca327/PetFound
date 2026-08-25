package alert;

import GUI.TelaPrin;
import GUI.TelaPrin;
import java.awt.Toolkit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.util.concurrent.CountDownLatch;
import starter.Icone;

public class alert extends javax.swing.JFrame {

    private boolean but = false;
    String id, user, audio;

    public alert(String us, String au) {
        initComponents();
        user = us;
        audio = au;
        id = "a";
        setIcon();
    }

    public void setIcon() {
        Icone ic= new Icone();
        String cm =ic.getIcon();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(cm)));
    }

    public void alertinput(String tit, String tp, String txt, String txt1, String txt2, String ida) {
        Label_title.setText(tit);
        label_msg.setText(txt);
        label_msg1.setText(txt1);
        label_msg2.setText(txt2);
        switch (tp) {
            case "ok":
                label_imagem.setIcon(new ImageIcon(alert.class.getResource("/img/ok.png")));
                audios("ok");
                break;
            case "erro":
                label_imagem.setIcon(new ImageIcon(alert.class.getResource("/img/erro.png")));
                audios("erro");
                break;
            case "info":
                label_imagem.setIcon(new ImageIcon(alert.class.getResource("/img/info.png")));
                audios("info");
                break;
            default:
                break;
        }

        id = ida;
        tit();
    }

    // Thread que vai bloquear a tela
    public void tit() {
        switch (id) {
            case "timeout":
                setTitle("Espere 20 Segundos");
                //try {
                //    Thread.sleep(20000); // Espera x segundos
                //} catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
                break;
            case "erro":
                setTitle("Error");
                break;

            case "info":
                setTitle("Informes");
                break;

            case "sucesso":
                setTitle("Sucesso");
                break;
            default:

                break;
        }
    }

    public void audios(String tpa) {
        switch (audio) {

            case "on":
                AudioInputStream audioInputStream;
                if (null != tpa) {
                    switch (tpa) {
                        case "erro":
                            Toolkit.getDefaultToolkit().beep();
                            break;
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
                        case "info":
                        try {
                            // Carrega o arquivo de áudio
                            audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/audio/info.wav"));

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
                        case "ok":
                        try {
                            // Carrega o arquivo de áudio
                            audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/audio/realizado.wav"));

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

    public void btnOK() {
        switch (id) {
            case "telaprinlog":

                audios("tc");
                TelaPrin tela = new TelaPrin(user, audio);
                tela.setVisible(true);
                dispose();
                String au = "off";
                break;

            case "erro":
                dispose();
                break;

            case "info":
                dispose();
                break;

            case "sucesso":
                dispose();
                break;

            default:
                dispose();
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        Label_title = new javax.swing.JLabel();
        label_imagem = new javax.swing.JLabel();
        label_msg = new javax.swing.JLabel();
        label_msg1 = new javax.swing.JLabel();
        label_msg2 = new javax.swing.JLabel();
        button_oka = new SwingPerson.JbuttonArr();
        lfundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mensagem");
        setPreferredSize(new java.awt.Dimension(356, 228));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 253, 243));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(64, 33, 7)));

        Label_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Label_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_title.setText("Titulo");

        label_imagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ok.png"))); // NOI18N

        label_msg.setText("Mensagem");

        label_msg1.setText("Mensagem");

        label_msg2.setText("Mensagem");

        button_oka.setText("OK");
        button_oka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_okaActionPerformed(evt);
            }
        });
        button_oka.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                button_okaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Label_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(label_imagem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(label_msg1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_msg2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label_msg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(button_oka, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Label_title)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(label_msg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_msg1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_msg2))
                    .addComponent(label_imagem, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_oka, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 300, 150));

        lfundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fundo.jpg"))); // NOI18N
        getContentPane().add(lfundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button_okaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_okaActionPerformed
        btnOK();
    }//GEN-LAST:event_button_okaActionPerformed

    private void button_okaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_button_okaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnOK();
        }
    }//GEN-LAST:event_button_okaKeyPressed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String us = "";
                String au = "";
                new alert(us, au).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Label_title;
    private SwingPerson.JbuttonArr button_oka;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label_imagem;
    private javax.swing.JLabel label_msg;
    private javax.swing.JLabel label_msg1;
    private javax.swing.JLabel label_msg2;
    private javax.swing.JLabel lfundo;
    // End of variables declaration//GEN-END:variables
}
