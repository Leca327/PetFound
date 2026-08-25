package Logar;

import factory.ConnectionFactory;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import starter.Icone;

public class versao extends javax.swing.JFrame {

    private Connection connection;

    public versao() {

        initComponents();
        setIcon();
        ((JLabel) cb_v.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        //designComboBox();
        // Selecionar a última opção do JComboBox
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int lastOptionIndex = cb_v.getItemCount() - 1;
                if (lastOptionIndex >= 0) {
                    cb_v.setSelectedIndex(lastOptionIndex);
                }
            }
        });
    }

    public void setIcon() {
        Icone ic = new Icone();
        String cm = ic.getIcon();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(cm)));
    }

    public static void designComboBox() {
        //   cb_v.setUI(new BasicComboBoxUI() {

        // });
    }

    public void limpar() {
        label_info1.setText("");
        label_info2.setText("");
        label_info3.setText("");
        label_info4.setText("");
        label_info5.setText("");
        label_info6.setText("");
        label_info7.setText("");
        label_info8.setText("");
        label_info9.setText("");
        label_info10.setText("");

    }

    public void buscarVersao() {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "select * from versao";
        //cb_v.removeAllItems();
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            // Limpar itens existentes do ComboBox
            while (res.next()) {
                cb_v.removeItem("Sem Versões Guardadas");
                String tx1 = res.getString(1);

                // Verificar se o valor já existe no ComboBox
                if (!valorExisteNoComboBox(tx1)) {
                    cb_v.addItem(tx1); // Adicionar valor ao ComboBox
                    cb_v.setSelectedItem(tx1); // Selecionar o valor adicionado

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private boolean valorExisteNoComboBox(String valor) {
        for (int i = 0; i < cb_v.getItemCount(); i++) {
            Object item = cb_v.getItemAt(i);
            if (valor.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public void conteudo(String cod) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "";

        sql = "select * from versao where codv= '" + cod + "'";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            int labelIndex = 1; // Índice inicial da primeira label (label_info1)

            while (res.next()) {
                String tx1 = (res.getString(3));

                // Obter os valores separados por ponto e vírgula
                String[] values = tx1.split(";");

                // Preencher as labels com os valores obtidos
                for (int i = 0; i < values.length && labelIndex <= 10; i++) {
                    String value = values[i].trim(); // Remover espaços em branco
                    JLabel label = getLabelByIndex(labelIndex);
                    label.setText(value);
                    labelIndex++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JLabel getLabelByIndex(int index) {
        switch (index) {
            case 1:
                return label_info1;
            case 2:
                return label_info2;
            case 3:
                return label_info3;
            case 4:
                return label_info4;
            case 5:
                return label_info5;
            case 6:
                return label_info6;
            case 7:
                return label_info7;
            case 8:
                return label_info8;
            case 9:
                return label_info9;
            case 10:
                return label_info10;
            default:
                throw new IllegalArgumentException("Invalid label index: " + index);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        label_info1 = new javax.swing.JLabel();
        label_info2 = new javax.swing.JLabel();
        label_info3 = new javax.swing.JLabel();
        label_info4 = new javax.swing.JLabel();
        label_info5 = new javax.swing.JLabel();
        label_info6 = new javax.swing.JLabel();
        label_info7 = new javax.swing.JLabel();
        label_info8 = new javax.swing.JLabel();
        label_info9 = new javax.swing.JLabel();
        label_info10 = new javax.swing.JLabel();
        cb_v = new javax.swing.JComboBox<>();
        B_sair = new SwingPerson.JbuttonArr();
        label_fundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pet Found - Versão");
        setMinimumSize(new java.awt.Dimension(400, 350));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(64, 33, 7));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBackground(new java.awt.Color(255, 253, 243));
        jPanel2.setPreferredSize(new java.awt.Dimension(12, 165));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_info1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_info2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_info3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_info4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_info5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_info6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_info7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_info8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_info9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_info10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_info1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_info10)
                .addContainerGap(173, Short.MAX_VALUE))
        );

        cb_v.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cb_v.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sem Versões Guardadas" }));
        cb_v.setSelectedItem(null);
        cb_v.setToolTipText("");
        cb_v.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb_v.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_vActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(cb_v, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(cb_v, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 360, 310));

        B_sair.setText("Sair");
        B_sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_sairActionPerformed(evt);
            }
        });
        getContentPane().add(B_sair, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 360, 20));

        label_fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fundo.jpg"))); // NOI18N
        label_fundo.setMaximumSize(new java.awt.Dimension(400, 350));
        label_fundo.setMinimumSize(new java.awt.Dimension(400, 350));
        label_fundo.setPreferredSize(new java.awt.Dimension(400, 350));
        getContentPane().add(label_fundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 380));
        label_fundo.getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cb_vActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_vActionPerformed
        limpar();
        buscarVersao();
        String cod = (String) cb_v.getSelectedItem();
        if (cod != null) {
            conteudo(cod);
        }
    }//GEN-LAST:event_cb_vActionPerformed

    private void B_sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_sairActionPerformed
        dispose();
    }//GEN-LAST:event_B_sairActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                versao frame = new versao();
                frame.buscarVersao(); // Buscar versões antes de exibir a janela
                frame.setVisible(true);

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private SwingPerson.JbuttonArr B_sair;
    private javax.swing.JComboBox<String> cb_v;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label_fundo;
    private javax.swing.JLabel label_info1;
    private javax.swing.JLabel label_info10;
    private javax.swing.JLabel label_info2;
    private javax.swing.JLabel label_info3;
    private javax.swing.JLabel label_info4;
    private javax.swing.JLabel label_info5;
    private javax.swing.JLabel label_info6;
    private javax.swing.JLabel label_info7;
    private javax.swing.JLabel label_info8;
    private javax.swing.JLabel label_info9;
    // End of variables declaration//GEN-END:variables
}
