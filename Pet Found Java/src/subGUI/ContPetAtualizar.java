package subGUI;

import alert.alert;
import factory.ConnectionFactory;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import starter.Icone;

public class ContPetAtualizar extends javax.swing.JFrame {

    private Connection connection;
    Boolean maxc;
    String adm, audio, tipocont, nome, selcont;
    int tipo, tipo2;

    public ContPetAtualizar(String admin, String au, String sel) {
        initComponents();
        adm = admin;
        audio = au;
        selcont = sel;
        setIcon();
        buspessoa();
        buspet();
        if (selcont != "" || selcont != null) {
            textf_userBuscar.setText(selcont);
            buscarcont(selcont);
            tamanho();
            bupf();
            bupet();
        }
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
        textf_pf.setText("");
        textf_pet.setText("");
        textf_pe.setText("");
        textf_pf1.setText("");
        comb_p.setSelectedItem(null);
        comb_pet.setSelectedItem(null);
        comb_tp.setSelectedItem(null);
        textf_cod.setText("");
        textf_userBuscar.setText("");
    }

    public Boolean maxperm() {
        maxc = !((textf_pf.getText().length() <= 50) && (textf_pet.getText().length() <= 50) && (textf_cod.getText().length() <= 50));
        return maxc;
    }

    public void tamanho() {

        //Código
        String inputText = textf_cod.getText(); // Obtém o texto do campo de texto
        int numCaracteres = inputText.length();
        int maxperm = 50;
        L_caractercod.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caractercod.setForeground(new Color(255, 51, 51));
            L_caractercod.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caractercod.setForeground(new Color(0, 0, 0));
            L_caractercod.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }
    }

    public void bus(String pet) {

        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM pet WHERE petcod = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, pet);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                String tipo = res.getString(14);
                nome = res.getString(1);
                switch (tipo) {
                    case "Adocao":
                        tipocont = "adt";
                        break;
                    case "Padrinho":
                        tipocont = "apd";
                        break;
                    case "Pad_Ado":
                        tipocont = "adt_apd";
                        break;
                    default:
                        tipocont = "";
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void buspessoa() {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "select * from pessoa";
        //cb_v.removeAllItems();
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            // Limpar itens existentes do ComboBox
            while (res.next()) {
                comb_p.removeItem("Ssem Pessoa Encontrada");
                String tx1 = res.getString(7);

                // Verificar se o valor já existe no ComboBox
                if (!valorExisteNoComboBox(tx1)) {
                    comb_p.addItem(tx1); // Adicionar valor ao ComboBox
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buspet() {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "select * from pet";
        //cb_v.removeAllItems();
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            // Limpar itens existentes do ComboBox
            while (res.next()) {
                comb_pet.removeItem("Sem Pet Encontrado");
                String tx1 = res.getString(9);

                // Verificar se o valor já existe no ComboBox
                if (!valorExisteNoComboBox(tx1)) {
                    comb_pet.addItem(tx1); // Adicionar valor ao ComboBox
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean valorExisteNoComboBox(String valor) {
        for (int i = 0; i < comb_p.getItemCount(); i++) {
            Object item = comb_p.getItemAt(i);
            if (valor.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public String verificar(String cod) {
        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps;
        String status = "";

        if ((adm == null) || (adm == "")) {

            alert al = new alert(adm, audio);
            al.setVisible(true);
            String msg = "Administrador não";
            String msg2 = "não está logado";
            String tit = "Admin nulo";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");

        } else {
            try {
                PreparedStatement stm = connection.prepareStatement("select * from pet where petcod=?;");
                stm.setString(1, textf_pet.getText());
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    String tx1 = rs.getString(10);
                    if (tx1.equals(textf_pf.getText())) {
                        alert al = new alert(null, audio);
                        al.setVisible(true);
                        String msg = "Não é possivel dono do";
                        String msg1 = "Anuncio criar contato";
                        String msg2 = "com ele mesmo";
                        String tit = "Contato Interrompido";
                        al.alertinput(tit, "erro", msg, msg1, msg2, "erro");
                    } else {

                        ps = connection.prepareStatement("select * from contatopet where codcontp=?;");
                        ps.setString(1, cod);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            String user = textf_pf.getText();
                            ps = connection.prepareStatement("select * from pessoa where pcod=?;");
                            ps.setString(1, user);
                            rs = ps.executeQuery();
                            if (!rs.next()) {
                                alert al = new alert(adm, audio);
                                al.setVisible(true);
                                String msg = "Código da Pessoa Física";
                                String msg1 = "não existe";
                                String tit = "Pessoa existente";
                                al.alertinput(tit, "erro", msg, msg1, "", "erro");

                            } else {

                                if ("PJ".equals(user.substring(0, 2))) {
                                    alert al = new alert(adm, audio);
                                    al.setVisible(true);
                                    String msg = "Pessoa Jurídica não pode";
                                    String msg1 = "Ter um contato de pet";
                                    String tit = "Pessoa existente";
                                    al.alertinput(tit, "erro", msg, msg1, "", "erro");

                                } else {
                                    stm = connection.prepareStatement("select * from pet where petcod=?;");
                                    stm.setString(1, textf_pet.getText());
                                    rs = stm.executeQuery();
                                    if (rs.next()) {
                                        String tx2 = rs.getString(10);
                                         String tx3="";
                                        stm = connection.prepareStatement("SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod where pcod= ?;");
                                        stm.setString(1, tx2);
                                        rs = stm.executeQuery();
                                        if (rs.next()) {
                                            tx3 = rs.getString(16);
                                        }

                                        String tp = (String) comb_tp.getSelectedItem();
                                        if (( ("PJ".equals(tx2.substring(0, 2))&& "Empresa".equals(tx3)  ) ||"PF".equals(tx2.substring(0, 2))) && !"Adoção".equals(tp)) {
                                            alert al = new alert(adm, audio);
                                            al.setVisible(true);
                                            String msg = "Dono Temporario do pet";
                                            String msg1 = "Só pode ter contato de";
                                            String msg2 = "Adoção.";
                                            String tit = "Atualização";
                                            al.alertinput(tit, "erro", msg, msg1, msg2, "erro");
                                        } else {
                                            String pet = textf_pet.getText();
                                            ps = connection.prepareStatement("select * from pet where petcod=?;");
                                            ps.setString(1, pet);
                                            rs = ps.executeQuery();
                                            if (!rs.next()) {
                                                alert al = new alert(adm, audio);
                                                al.setVisible(true);
                                                String msg = "Código do Pet não existe";
                                                String tit = "Pet existente";
                                                al.alertinput(tit, "erro", "", msg, "", "erro");

                                            } else {

                                                if (tipo == 1) {
                                                    if ((textf_pet.getText().isEmpty()) || (textf_pf.getText().isEmpty()) || (comb_tp.getSelectedItem() == null) || (textf_cod.getText().isEmpty())) {
                                                        alert al = new alert(adm, audio);
                                                        al.setVisible(true);
                                                        String msg = "Os campos não podem";
                                                        String msg2 = "retornar vazios";
                                                        String tit = "Campo(s) de Texto Vazio(s)";
                                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");

                                                    } else {

                                                        switch (tp) {
                                                            case "Adoção":
                                                                tp = "adt";
                                                                break;
                                                            case "Padrinho":
                                                                tp = "apd";
                                                                break;
                                                            case "Padrinho e Adoção":
                                                                tp = "adt_apd";
                                                                break;
                                                            default:
                                                                tp = "";
                                                                break;
                                                        }
                                                        String codc = textf_cod.getText();
                                                        String codpet = textf_pet.getText();
                                                        String codpf = textf_pf.getText();
                                                        atualizarcont(tp, codpet, codpf, codc);
                                                        limpar();

                                                    }

                                                } else {

                                                    buscarcont(cod);
                                                }
                                            }
                                        }

                                    } else {
                                        alert al = new alert(adm, audio);
                                        al.setVisible(true);
                                        String msg = "Pessoa não pode";
                                        String msg1 = "Ser Encontrada";
                                        String tit = "Pessoa existente";
                                        al.alertinput(tit, "erro", msg, msg1, "", "erro");
                                    }

                                }
                            }
                        } else {

                            alert al = new alert(null, audio);
                            al.setVisible(true);
                            String msg = "Código de Pet";
                            String msg1 = "Não encontrada.";
                            String tit = "Pet Inexistente";
                            al.alertinput(tit, "erro", msg, msg1, "", "erro");
                        }
                    }
                } else {
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Código do Contato Pet inexistente";
                    String tit = "Pet inexistente";
                    al.alertinput(tit, "erro", "", msg, "", "erro");
                } 
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 

        return status;

    }

    public void atualizarcont(String tp, String codpet, String codpf, String codc) {
        this.connection = new ConnectionFactory().getConnection();
        String cod = textf_userBuscar.getText();
        //Connection con;
        PreparedStatement ps;
        String status = "";
        this.connection = new ConnectionFactory().getConnection();
        String sql = "";
        try {
            sql = "select * from contatopet where codcontp= '" + cod + "'";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                ps = connection.prepareStatement("update contatopet set tipocont=?, petcodpet=?,pfcodp=?,codcontp=? where codcontp=?");

                ps.setString(1, tp);
                ps.setString(2, codpet);
                ps.setString(3, codpf);
                ps.setString(4, codc);
                ps.setString(5, cod);

                int i = ps.executeUpdate();

                if (i != 0) {
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Pet";
                    String msg2 = "atualizados com sucesso";
                    String tit = "Atualização de Contato Pet";
                    al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                    //dispose();

                } else {
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Pet";
                    String msg2 = "não atualizados";
                    String tit = "Atualização de Pet";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                }
            } else {
                alert al = new alert(adm, audio);
                al.setVisible(true);
                String msg = "Código Vazio ou";
                String msg2 = "Contato Pet Não existe";
                String tit = "Atualização de Pet";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void buscarcont(String user) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM contatopet WHERE codcontp = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                String tx1 = res.getString(2);
                String tx2 = res.getString(1);
                String tx3 = res.getString(3);
                String tx4 = res.getString(4);

                textf_cod.setText(tx4);
                textf_pet.setText(tx1);
                textf_pf.setText(tx2);

                String cb = tx3;
                switch (cb) {
                    case "apd":
                        comb_tp.setSelectedItem("Padrinho");
                        break;
                    case "adt":
                        comb_tp.setSelectedItem("Adoção");
                        break;
                    case "adt_apd":
                        comb_tp.setSelectedItem("Padrinho e Adoção");
                        break;
                    default:
                        break;
                }

            } else {

                alert al = new alert(adm, audio);
                al.setVisible(true);
                String msg = "Informação Trazida";
                String msg2 = "Incorreta Ou Desatulizada.";
                String msg3 = "Atualize o Banco de Dados.";
                String tit = "Busca de Admin";
                al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                al.setAlwaysOnTop(true);
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        comb_p = new javax.swing.JComboBox<>();
        textf_pf = new javax.swing.JTextField();
        textf_pf1 = new javax.swing.JTextField();
        label_pf = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comb_pet = new javax.swing.JComboBox<>();
        textf_pet = new javax.swing.JTextField();
        textf_pe = new javax.swing.JTextField();
        label_pf1 = new javax.swing.JLabel();
        btn_atualizar = new SwingPerson.JbuttonArr();
        btn_limpar = new SwingPerson.JbuttonArr();
        L_vlt = new javax.swing.JLabel();
        label_cod = new javax.swing.JLabel();
        textf_cod = new javax.swing.JTextField();
        comb_tp = new javax.swing.JComboBox<>();
        label_anun = new javax.swing.JLabel();
        L_caractercod = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        label_userBuscar = new javax.swing.JLabel();
        textf_userBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btn_att = new javax.swing.JButton();
        Lbuscar = new javax.swing.JLabel();
        lfundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pet Found - Atualizer Contato Pet");
        setPreferredSize(new java.awt.Dimension(658, 469));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 253, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("Selecionar Código");
        jLabel2.setToolTipText("<html> Selecionar Código Na Comb Box <br> E colocar No Campo de Texto</html>");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        comb_p.setBackground(new java.awt.Color(204, 204, 204));
        comb_p.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        comb_p.setSelectedItem(null);
        comb_p.setToolTipText("");
        comb_p.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        textf_pf.setBackground(new java.awt.Color(255, 253, 243));
        textf_pf.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_pf.setBorder(null);
        textf_pf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_pfKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_pfKeyReleased(evt);
            }
        });

        textf_pf1.setEditable(false);
        textf_pf1.setBackground(new java.awt.Color(204, 204, 204));
        textf_pf1.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        textf_pf1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        label_pf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_pf.setText("COD PESSOA");

        jLabel3.setText("Selecionar Código");
        jLabel3.setToolTipText("<html> Selecionar Código Na Comb Box <br> E colocar No Campo de Texto</html>");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        comb_pet.setBackground(new java.awt.Color(204, 204, 204));
        comb_pet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        comb_pet.setSelectedItem(null);
        comb_pet.setToolTipText("");
        comb_pet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        textf_pet.setBackground(new java.awt.Color(255, 253, 243));
        textf_pet.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_pet.setBorder(null);
        textf_pet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_petKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_petKeyReleased(evt);
            }
        });

        textf_pe.setEditable(false);
        textf_pe.setBackground(new java.awt.Color(204, 204, 204));
        textf_pe.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        textf_pe.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        label_pf1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_pf1.setText("COD PET");

        btn_atualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/atualizar.png"))); // NOI18N
        btn_atualizar.setToolTipText("Cadastre Um Admin");
        btn_atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizarActionPerformed(evt);
            }
        });

        btn_limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/apagar.png"))); // NOI18N
        btn_limpar.setToolTipText("Limpar Todos os Campos");
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

        label_cod.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_cod.setText("COD CONTATO PET");

        textf_cod.setBackground(new java.awt.Color(255, 253, 243));
        textf_cod.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_cod.setBorder(null);
        textf_cod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_codKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_codKeyReleased(evt);
            }
        });

        comb_tp.setBackground(new java.awt.Color(255, 253, 243));
        comb_tp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Padrinho", "Adoção", "Padrinho e Adoção" }));
        comb_tp.setSelectedIndex(-1);
        comb_tp.setSelectedItem(null);
        comb_tp.setBorder(null);
        comb_tp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        label_anun.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_anun.setText("TIPO DE CONTATO");

        L_caractercod.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercod.setText("0");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(363, 3));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(L_vlt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(L_caractercod)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(textf_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(label_pf)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textf_pf1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textf_pf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comb_p, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel2)
                        .addComponent(label_cod, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(77, 77, 77)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(label_pf1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(textf_pe, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(textf_pet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comb_pet, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel3)
                                .addComponent(comb_tp, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label_anun))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_pf)
                            .addComponent(textf_pf1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_pf, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_pf1)
                            .addComponent(textf_pe, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_pet, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(comb_p, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(comb_pet, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label_cod)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label_anun)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(comb_tp)))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(L_caractercod)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(L_vlt))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_limpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 583, 260));

        jPanel2.setBackground(new java.awt.Color(255, 253, 243));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        label_userBuscar.setBackground(new java.awt.Color(51, 51, 51));
        label_userBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_userBuscar.setForeground(new java.awt.Color(51, 51, 51));
        label_userBuscar.setText("Código");

        textf_userBuscar.setBackground(new java.awt.Color(255, 253, 243));
        textf_userBuscar.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_userBuscar.setToolTipText("");
        textf_userBuscar.setBorder(null);
        textf_userBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textf_userBuscarMouseClicked(evt);
            }
        });
        textf_userBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_userBuscarKeyPressed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(363, 3));

        btn_att.setBackground(new java.awt.Color(255, 253, 243));
        btn_att.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_att.setForeground(new java.awt.Color(51, 51, 51));
        btn_att.setText("Não sei o Código");
        btn_att.setToolTipText("");
        btn_att.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_att.setPreferredSize(new java.awt.Dimension(146, 22));
        btn_att.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_attActionPerformed(evt);
            }
        });

        Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        Lbuscar.setToolTipText("Buscar Admin");
        Lbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Lbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LbuscarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(label_userBuscar)
                        .addGap(5, 5, 5)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textf_userBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(Lbuscar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_userBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textf_userBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lbuscar))
                .addGap(5, 5, 5)
                .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 583, -1));

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

    public void bsp() {
        String userInput = textf_pf.getText();
        if (!userInput.isEmpty()) {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "select * from pessoa where pcod like ?";

            try {
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setString(1, userInput + "%");
                ResultSet res = stm.executeQuery();

                if (res.next()) {
                    String tx1 = res.getString(5);
                    textf_pf1.setText(tx1);

                } else {
                    textf_pf1.setText("Pessoa Não Encontrada");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            textf_pf1.setText(""); // Define o campo como vazio se o texto estiver vazio
        }
    }

    public void bspet() {
        String userInput = textf_pet.getText();
        if (!userInput.isEmpty()) {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "select * from pet where petcod like ?";

            try {
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setString(1, userInput + "%");
                ResultSet res = stm.executeQuery();

                if (res.next()) {
                    String tx1 = res.getString(5);
                    textf_pe.setText(tx1);

                } else {
                    textf_pe.setText("Pessoa Não Encontrada");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            textf_pe.setText(""); // Define o campo como vazio se o texto estiver vazio
        }
    }

    public void vlt() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Sair?");
        if (escolha == 0) {
            audios("tc");
            dispose();
        }
    }

    public void att() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo atulizar o Contato Pet " + textf_userBuscar.getText() + "?\nUma vez atulizado, a informação irá mudar para sempre.");
        if (escolha == 0) {
            String cod = textf_userBuscar.getText();
            tipo = 1;
            verificar(cod);
        }
    }

    public void bus() {
        String user = textf_userBuscar.getText();
        tipo = 2;
        verificar(user);
    }

    public void bupf() {
        bsp();
        String userInput = textf_pf.getText().toLowerCase();
        for (int i = 0; i < comb_p.getItemCount(); i++) {
            String item = comb_p.getItemAt(i).toLowerCase();
            if (item.startsWith(userInput)) {
                comb_p.setSelectedIndex(i);
                return;
            }
        }
    }

    public void bupet() {
        bspet();
        String userInput = textf_pet.getText().toLowerCase();
        for (int i = 0; i < comb_pet.getItemCount(); i++) {
            String item = comb_pet.getItemAt(i).toLowerCase();
            if (item.startsWith(userInput)) {
                comb_pet.setSelectedIndex(i);
                return;
            }
        }
    }
    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        if (comb_p.getSelectedItem() == null || comb_p.getSelectedItem() == "") {

        } else {
            textf_pf.setText(comb_p.getSelectedItem().toString());
            bsp();
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void textf_pfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_pfKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_pfKeyPressed

    private void textf_pfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_pfKeyReleased
        bupf();
    }//GEN-LAST:event_textf_pfKeyReleased

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        if (comb_pet.getSelectedItem() == null || comb_pet.getSelectedItem() == "") {

        } else {
            textf_pet.setText(comb_pet.getSelectedItem().toString());
            bspet();
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void textf_petKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_petKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_petKeyPressed

    private void textf_petKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_petKeyReleased
        bupet();
    }//GEN-LAST:event_textf_petKeyReleased

    private void btn_atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizarActionPerformed
        att();
    }//GEN-LAST:event_btn_atualizarActionPerformed

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        audios("cl");
        lmp();
    }//GEN-LAST:event_btn_limparActionPerformed

    private void L_vltMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_vltMouseClicked
        vlt();
    }//GEN-LAST:event_L_vltMouseClicked

    private void textf_userBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_userBuscarMouseClicked
        limpar();
    }//GEN-LAST:event_textf_userBuscarMouseClicked

    private void textf_userBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_userBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            bus();
        }
    }//GEN-LAST:event_textf_userBuscarKeyPressed

    private void btn_attActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_attActionPerformed
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja sair para a tela de Buscar Contato Pet?");
        if (escolha == 0) {
            audios("tc");
            // AdminBuscar pf = new AdminBuscar(admin, audio);
            //pf.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_btn_attActionPerformed

    private void LbuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LbuscarMouseClicked
        bus();
    }//GEN-LAST:event_LbuscarMouseClicked

    private void textf_codKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_codKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            bus();
        }
    }//GEN-LAST:event_textf_codKeyPressed

    private void textf_codKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_codKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_codKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String au = "";
                String ad = "";
                String sel = "";
                new ContPetAtualizar(ad, au, sel).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel L_caractercod;
    private javax.swing.JLabel L_vlt;
    private javax.swing.JLabel Lbuscar;
    private javax.swing.JButton btn_att;
    private SwingPerson.JbuttonArr btn_atualizar;
    private SwingPerson.JbuttonArr btn_limpar;
    private javax.swing.JComboBox<String> comb_p;
    private javax.swing.JComboBox<String> comb_pet;
    private javax.swing.JComboBox<String> comb_tp;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label_anun;
    private javax.swing.JLabel label_cod;
    private javax.swing.JLabel label_pf;
    private javax.swing.JLabel label_pf1;
    private javax.swing.JLabel label_userBuscar;
    private javax.swing.JLabel lfundo;
    private javax.swing.JTextField textf_cod;
    private javax.swing.JTextField textf_pe;
    private javax.swing.JTextField textf_pet;
    private javax.swing.JTextField textf_pf;
    private javax.swing.JTextField textf_pf1;
    private javax.swing.JTextField textf_userBuscar;
    // End of variables declaration//GEN-END:variables
}
