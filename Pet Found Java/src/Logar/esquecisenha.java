package Logar;

import alert.alert;
import factory.ConnectionFactory;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import starter.Icone;

public class esquecisenha extends javax.swing.JFrame {

    Boolean maxc;
    private Connection connection;
    String audio;

    private String userchefe, senhachefe;

    public esquecisenha(String audio) {
        this.audio = audio;
        initComponents();
        setIcon();
    }

    public void setIcon() {
        Icone ic= new Icone();
        String cm =ic.getIcon();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(cm)));
    }
    public Boolean maxperm() {
        maxc = !(textf_user.getText().length() <= 25 && pass_senha.getPassword().length <= 25 && pass_senhaconfirm.getPassword().length <= 25);
        return maxc;
    }

    public void limpar() {
        textf_user.setText("");
        pass_senha.setText("");
        pass_senhaconfirm.setText("");
    }

    public void mudarsenha() {
        this.connection = new ConnectionFactory().getConnection();
        Boolean perm = maxperm();
        if (perm == false) {

            String sql = "SELECT * FROM admin WHERE usera = ?";
            try {
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setString(1, textf_user.getText());
                ResultSet res = stm.executeQuery();

                if (res.next()) {

                    if (pass_senha.getText().equals(pass_senhaconfirm.getText())) {
                        stm = connection.prepareStatement("select * from admin where senhaa=? and usera=?;");
                        stm.setString(1, pass_senhaconfirm.getText());
                        stm.setString(2, textf_user.getText());
                        ResultSet rs = stm.executeQuery();
                        if (rs.next()) {
                            alert al = new alert(null, audio);
                            al.setVisible(true);
                            String msg = "Não é possível mudar";
                            String msg1 = "Para a mesma senha.";
                            String tit = "Mudar Senha";
                            al.alertinput(tit, "erro", msg, msg1, "", "erro");

                        } else {
                            LoginChefe panel = new LoginChefe();
                            int result = JOptionPane.showConfirmDialog(this, panel, "Login do Chefe", JOptionPane.OK_CANCEL_OPTION);

                            if (result == JOptionPane.OK_OPTION) {

                                Boolean status = panel.verificalog();

                                if (status) {
                                    //Connection con;
                                    PreparedStatement ps;
                                    try {
                                        ps = connection.prepareStatement("update admin set senhaa=? where usera=?");

                                        ps.setString(1, pass_senhaconfirm.getText());
                                        ps.setString(2, textf_user.getText());

                                        int i = ps.executeUpdate();
                                        if (i != 0) {
                                            alert al = new alert("", audio);
                                            al.setVisible(true);
                                            String msg = "Detalhes do Admin";
                                            String msg2 = "atualizados com sucesso";
                                            String tit = "Atualização de Admin";
                                            al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                                            limpar();
                                            dispose();
                                        } else {
                                            alert al = new alert("", audio);
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
                                    alert al = new alert("", audio);
                                    al.setVisible(true);
                                    String msg = "Usuario ou Senha";
                                    String msg1 = "Incorreta";
                                    String tit = "Informação Errada";
                                    al.alertinput(tit, "erro", msg, msg1, "", "erro");
                                }
                            }
                        }
                    } else {
                        alert al = new alert("", audio);
                        al.setVisible(true);
                        String msg = "As senhas não estão iguais";
                        String tit = "Senhas desiquais";
                        al.alertinput(tit, "erro", "", msg, "", "erro");
                    }

                } else {
                    alert al = new alert("", audio);
                    al.setVisible(true);
                    String msg = "Usuario não existe no";
                    String msg1 = "Banco de Dados";
                    String tit = "User errado";
                    al.alertinput(tit, "erro", msg, msg1, "", "erro");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            alert al = new alert("", audio);
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

        jPanel1 = new javax.swing.JPanel();
        textf_user = new javax.swing.JTextField();
        label_user = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pass_senha = new javax.swing.JPasswordField();
        label_senha = new javax.swing.JLabel();
        L_caracterse = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pass_senhaconfirm = new javax.swing.JPasswordField();
        label_senha1 = new javax.swing.JLabel();
        L_caractercse = new javax.swing.JLabel();
        jbuttonArr1 = new SwingPerson.JbuttonArr();
        lfundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pet Found - Mudar Senha Admin");
        setPreferredSize(new java.awt.Dimension(446, 396));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 253, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(363, 3));

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

        label_senha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_senha.setText("NOVA SENHA");

        L_caracterse.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterse.setText("0");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(363, 3));

        pass_senhaconfirm.setBackground(new java.awt.Color(255, 253, 243));
        pass_senhaconfirm.setBorder(null);
        pass_senhaconfirm.setPreferredSize(new java.awt.Dimension(64, 20));
        pass_senhaconfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pass_senhaconfirmActionPerformed(evt);
            }
        });
        pass_senhaconfirm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pass_senhaconfirmKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pass_senhaconfirmKeyReleased(evt);
            }
        });

        label_senha1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_senha1.setText("CONFIRMAR SENHA");

        L_caractercse.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercse.setText("0");

        jbuttonArr1.setText("Mudar Senha");
        jbuttonArr1.setToolTipText("");
        jbuttonArr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonArr1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pass_senhaconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_senha1)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(L_caractercse)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pass_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_senha)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(L_caracterse)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(label_user)
                        .addComponent(textf_user, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(84, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(label_user)
                .addGap(7, 7, 7)
                .addComponent(textf_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(label_senha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pass_senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_caracterse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_senha1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pass_senhaconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_caractercse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 370, 300));

        lfundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fundo.jpg"))); // NOI18N
        getContentPane().add(lfundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textf_userKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_userKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mudarsenha();
        }
    }//GEN-LAST:event_textf_userKeyPressed

    private void textf_userKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_userKeyReleased

    }//GEN-LAST:event_textf_userKeyReleased

    private void pass_senhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mudarsenha();
        }
    }//GEN-LAST:event_pass_senhaKeyPressed

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

    private void pass_senhaconfirmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaconfirmKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            mudarsenha();
        }
    }//GEN-LAST:event_pass_senhaconfirmKeyPressed

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

    private void jbuttonArr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonArr1ActionPerformed
        mudarsenha();
    }//GEN-LAST:event_jbuttonArr1ActionPerformed

    private void pass_senhaconfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pass_senhaconfirmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pass_senhaconfirmActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String au = "";
                new esquecisenha(au).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel L_caractercse;
    private javax.swing.JLabel L_caracterse;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private SwingPerson.JbuttonArr jbuttonArr1;
    private javax.swing.JLabel label_senha;
    private javax.swing.JLabel label_senha1;
    private javax.swing.JLabel label_user;
    private javax.swing.JLabel lfundo;
    private javax.swing.JPasswordField pass_senha;
    private javax.swing.JPasswordField pass_senhaconfirm;
    private javax.swing.JTextField textf_user;
    // End of variables declaration//GEN-END:variables
}
