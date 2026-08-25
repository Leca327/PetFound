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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import starter.Icone;

public class ContServicoAtualizar extends javax.swing.JFrame {

    private Connection connection;
    Boolean maxc;
    String adm, audio, tipocont, nome, selcont;
    int tipo, tipo2;

    public ContServicoAtualizar(String admin, String au, String sel) {
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
            bupf();
            bupet();
            tamanho();
            buscontratou(selcont);
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
        textf_cod.setText("");
        tamanho();
        textf_userBuscar.setText("");
    }

    public Boolean maxperm() {
        maxc = !((textf_pf.getText().length() <= 50) && (textf_pet.getText().length() <= 50) && (textf_cod.getText().length() <= 50) && (textf_cmt.getText().length() <= 255));
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

        inputText = textf_cmt.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 255;
        L_caractercmt.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caractercmt.setForeground(new Color(255, 51, 51));
            L_caractercmt.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caractercmt.setForeground(new Color(0, 0, 0));
            L_caractercmt.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }
    }

    public void bus(String pet) {

        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM servico WHERE servcod = ?";
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

    public void buscontratou(String cont) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM contatoserv WHERE codconts = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, cont);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                Boolean tipo = res.getBoolean(4);

                if (tipo) {
                    textf_cmt.setEnabled(true);
                    textf_cmt.setToolTipText("Comente sobre o Serviço");
                    slider_av.setToolTipText("Avalie o Serviço");
                    slider_av.setEnabled(true);
                    slider_av.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    check_nav.setEnabled(true);
                } else {
                    textf_cmt.setEnabled(false);
                    slider_av.setEnabled(false);
                    textf_cmt.setToolTipText("É preciso a pessoa contratar.");
                    slider_av.setToolTipText("É preciso a pessoa contratar.");
                    slider_av.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                    check_nav.isSelected();
                    check_nav.setEnabled(false);

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
                comb_p.removeItem("Sem Versões Guardadas");
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
        String sql = "select * from servico";
        //cb_v.removeAllItems();
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            // Limpar itens existentes do ComboBox
            while (res.next()) {
                comb_pet.removeItem("Sem Serviço Encontrado");
                String tx1 = res.getString(6);

                // Verificar se o valor já existe no ComboBox
                if (!valorExisteNoComboBox2(tx1)) {
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

    private boolean valorExisteNoComboBox2(String valor) {
        for (int i = 0; i < comb_pet.getItemCount(); i++) {
            Object item = comb_pet.getItemAt(i);
            if (valor.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public String verificar(String cod) {
        String status = "";
        Boolean perm = maxperm();
        if (perm == false) {
            this.connection = new ConnectionFactory().getConnection();
            PreparedStatement ps;

            if ((adm == null) || (adm == "")) {

                alert al = new alert(adm, audio);
                al.setVisible(true);
                String msg = "Administrador não";
                String msg2 = "não está logado";
                String tit = "Admin nulo";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");

            } else {
                try {
                    PreparedStatement stm = connection.prepareStatement("select * from servico where servcod=?;");
                    stm.setString(1, textf_pet.getText());
                    ResultSet rs = stm.executeQuery();
                    if (rs.next()) {
                        String tx1 = rs.getString(7);
                        if (tx1.equals(textf_pf.getText())) {
                            alert al = new alert(null, audio);
                            al.setVisible(true);
                            String msg = "Não é possivel dono do";
                            String msg1 = "Anuncio criar contato";
                            String msg2 = "com ele mesmo";
                            String tit = "Contato Interrompido";
                            al.alertinput(tit, "erro", msg, msg1, msg2, "erro");
                        } else {

                            ps = connection.prepareStatement("select * from contatoserv where codconts=?;");
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
                                    String msg = "Código da Pessoa";
                                    String msg1 = "não existe";
                                    String tit = "Pessoa existente";
                                    al.alertinput(tit, "erro", msg, msg1, "", "erro");

                                } else {
                                    String pet = textf_pet.getText();
                                    ps = connection.prepareStatement("select * from servico where servcod=?;");
                                    ps.setString(1, pet);
                                    rs = ps.executeQuery();
                                    if (!rs.next()) {
                                        alert al = new alert(adm, audio);
                                        al.setVisible(true);
                                        String msg = "Código do Serviço não existe";
                                        String tit = "Serviço existente";
                                        al.alertinput(tit, "erro", "", msg, "", "erro");

                                    } else {

                                        if (tipo == 1) {
                                            if ((textf_pet.getText().isEmpty()) || (textf_pf.getText().isEmpty()) || (textf_cod.getText().isEmpty())) {
                                                alert al = new alert(adm, audio);
                                                al.setVisible(true);
                                                String msg = "Os campos não podem";
                                                String msg2 = "retornar vazios";
                                                String tit = "Campo(s) de Texto Vazio(s)";
                                                al.alertinput(tit, "erro", msg, msg2, "", "erro");

                                            } else {
                                                String cmt;
                                                Integer av;
                                                String codc = textf_cod.getText();
                                                String codpet = textf_pet.getText();
                                                String codpf = textf_pf.getText();

                                                cmt = textf_cmt.getText();
                                                av = slider_av.getValue();

                                                atualizarcont(cmt, codpet, codpf, codc, av);
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
                                String msg = "Código do Contato Serviço inexistente";
                                String tit = "Serviço inexistente";
                                al.alertinput(tit, "erro", "", msg, "", "erro");
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            alert al = new alert(adm, audio);
            al.setVisible(true);
            String msg = "Diminua a Quantidade ";
            String msg2 = "de Caracter Para";
            String msg3 = "o Cadastro.";
            String tit = "Excedeu o Limite de Caracter";
            al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
        }
        return status;

    }

    public static String HrAtual() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return now.format(formatter);
    }

    public static String DtAtual() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }

    public void atualizarcont(String cmt, String codpet, String codpf, String codc, int av) {
        this.connection = new ConnectionFactory().getConnection();
        String cod = textf_userBuscar.getText();
        //Connection con;
        PreparedStatement ps;
        String status = "";
        this.connection = new ConnectionFactory().getConnection();
        String sql = "";
        try {
            sql = "select * from contatoserv where codconts= '" + cod + "'";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                ps = connection.prepareStatement("update contatoserv set comentario=?, scodserv=?,pcodp=?,codconts=?,avaliacao=?,dtaval=?,hraval=? where codconts=?");

                if (check_nav.isSelected() || !check_nav.isEnabled()) {
                    ps.setNull(1, java.sql.Types.BOOLEAN);
                    ps.setString(2, codpet);
                    ps.setString(3, codpf);
                    ps.setString(4, codc);
                    ps.setNull(5, java.sql.Types.BOOLEAN);
                    ps.setNull(6, java.sql.Types.BOOLEAN);
                    ps.setNull(7, java.sql.Types.BOOLEAN);
                    ps.setString(8, cod);
                } else {
                    ps.setString(1, cmt);
                    ps.setString(2, codpet);
                    ps.setString(3, codpf);
                    ps.setString(4, codc);
                    ps.setInt(5, av);
                    ps.setString(6, DtAtual());
                      ps.setString(7, HrAtual());
                    ps.setString(8, cod);
                }

                int i = ps.executeUpdate();

                if (i != 0) {
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Serviço";
                    String msg2 = "atualizados com sucesso";
                    String tit = "Atualização de Serviço";
                    al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

                } else {
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Serviço";
                    String msg2 = "não atualizados";
                    String tit = "Atualização de Serviço";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                }
            } else {
                alert al = new alert(adm, audio);
                al.setVisible(true);
                String msg = "Código Vazio ou";
                String msg2 = "Contato Serviço Não existe";
                String tit = "Atualização de Serviço";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void buscarcont(String user) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM contatoserv WHERE codconts = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                String tx1 = res.getString(2);
                String tx2 = res.getString(1);
                String tx3 = res.getString(6);
                String tx4 = res.getString(3);
                int tx5 = res.getInt(5);

                textf_cod.setText(tx4);
                textf_pet.setText(tx1);
                textf_pf.setText(tx2);
                textf_cmt.setText(tx3);
                slider_av.setValue(tx5);

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
        L_caractercod = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        textf_cmt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        L_caractercmt = new javax.swing.JLabel();
        label_cmt = new javax.swing.JLabel();
        L_max = new javax.swing.JLabel();
        slider_av = new javax.swing.JSlider();
        jLabel11 = new javax.swing.JLabel();
        label_av = new javax.swing.JLabel();
        check_nav = new javax.swing.JCheckBox();
        textf_nobgd = new javax.swing.JLabel();
        textf_nobgd1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        label_userBuscar = new javax.swing.JLabel();
        textf_userBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btn_att = new javax.swing.JButton();
        Lbuscar = new javax.swing.JLabel();
        lfundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pet Found - Atualizar Contato Serviço");
        setMinimumSize(new java.awt.Dimension(660, 560));
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
        textf_pf.setToolTipText("");
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
        textf_pf1.setToolTipText("Nome da Pessoa que está sendo escolhida");
        textf_pf1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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
        textf_pet.setToolTipText("");
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
        textf_pe.setToolTipText("Nome do Serviço que está sendo escolhido");
        textf_pe.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label_pf1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_pf1.setText("COD SERVIÇO");

        btn_atualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/atualizar.png"))); // NOI18N
        btn_atualizar.setToolTipText("Atualize Um Contato Serviço");
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
        label_cod.setText("COD CONTATO SERVIÇO");

        textf_cod.setBackground(new java.awt.Color(255, 253, 243));
        textf_cod.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_cod.setToolTipText("");
        textf_cod.setBorder(null);
        textf_cod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_codKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_codKeyReleased(evt);
            }
        });

        L_caractercod.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercod.setText("0");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(363, 3));

        textf_cmt.setBackground(new java.awt.Color(255, 253, 243));
        textf_cmt.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_cmt.setToolTipText("");
        textf_cmt.setBorder(null);
        textf_cmt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_cmtKeyReleased(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caractercmt.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercmt.setText("0");

        label_cmt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_cmt.setText("COMENTÁRIO");

        L_max.setText("10");

        slider_av.setMaximum(10);
        slider_av.setToolTipText("<html>Avaliar de 1 a 5.\n<br>A cada 2 avaliação conta como 1<br>\n10=5 , 7=3.5</html>");
        slider_av.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slider_av.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_avStateChanged(evt);
            }
        });
        slider_av.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                slider_avKeyReleased(evt);
            }
        });

        jLabel11.setText("0");

        label_av.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_av.setText("AVALIAÇÃO");

        check_nav.setText("Não Avaliar");
        check_nav.setToolTipText("Deixar Avaliação e Comentário Como Nulo");
        check_nav.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        check_nav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_navActionPerformed(evt);
            }
        });

        textf_nobgd.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        textf_nobgd.setForeground(new java.awt.Color(204, 51, 0));
        textf_nobgd.setText("*");
        textf_nobgd.setToolTipText("Campo não Obrigatório");
        textf_nobgd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        textf_nobgd.setPreferredSize(new java.awt.Dimension(15, 20));

        textf_nobgd1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        textf_nobgd1.setForeground(new java.awt.Color(204, 51, 0));
        textf_nobgd1.setText("*");
        textf_nobgd1.setToolTipText("Campo não Obrigatório");
        textf_nobgd1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        textf_nobgd1.setPreferredSize(new java.awt.Dimension(15, 20));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(L_vlt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(L_caractercmt)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(textf_cmt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_cmt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_nobgd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(175, 175, 175)))
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
                .addGap(56, 56, 56)
                .addComponent(textf_nobgd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_av, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(label_pf1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(textf_pe, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(textf_pet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comb_pet, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(slider_av, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(65, 65, 65)
                            .addComponent(check_nav)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(L_max))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_cod)
                            .addComponent(label_av))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textf_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slider_av, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textf_nobgd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(L_caractercod)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addComponent(label_cmt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(textf_cmt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textf_nobgd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(L_caractercmt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(L_vlt))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(L_max)
                            .addComponent(check_nav))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_limpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        btn_atualizar.getAccessibleContext().setAccessibleDescription("Atualize Um Admin");

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 583, 350));

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
        btn_att.setToolTipText("<html>Sair da Tela E buscar<br> O Código do Contato Serviço</html>");
        btn_att.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_att.setPreferredSize(new java.awt.Dimension(146, 22));
        btn_att.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_attActionPerformed(evt);
            }
        });

        Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        Lbuscar.setToolTipText("Buscar Contato Serviço");
        Lbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
            String sql = "select * from servico where servcod like ?";

            try {
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setString(1, userInput + "%");
                ResultSet res = stm.executeQuery();

                if (res.next()) {
                    String tx1 = res.getString(5);
                    textf_pe.setText(tx1);

                } else {
                    textf_pe.setText("Serviço Não Encontrado");
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
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo atulizar o Contato Serviço " + textf_userBuscar.getText() + "?\nUma vez atulizado, a informação irá mudar para sempre.");
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

    private void textf_cmtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_cmtKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_cmtKeyReleased

    private void slider_avStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_avStateChanged
        int sliderValue = slider_av.getValue();
        L_max.setText(Integer.toString(sliderValue));

    }//GEN-LAST:event_slider_avStateChanged

    private void slider_avKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_slider_avKeyReleased
        int sliderValue = slider_av.getValue();
        L_max.setText("R$" + sliderValue + ",00");
    }//GEN-LAST:event_slider_avKeyReleased

    private void check_navActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_navActionPerformed
        if (check_nav.isSelected()) {
            textf_cmt.setEnabled(false);
            slider_av.setEnabled(false);
            textf_cmt.setToolTipText("É preciso Habilitara Avaliação.");
            slider_av.setToolTipText("É preciso Habilitar a Avaliação.");
            slider_av.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        } else {
            textf_cmt.setEnabled(true);
            textf_cmt.setToolTipText("Comente sobre o Serviço");
            slider_av.setToolTipText("Avalie o Serviço");
            slider_av.setEnabled(true);
            slider_av.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }


    }//GEN-LAST:event_check_navActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String au = "";
                String ad = "";
                String sel = "";
                new ContServicoAtualizar(ad, au, sel).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel L_caractercmt;
    private javax.swing.JLabel L_caractercod;
    private javax.swing.JLabel L_max;
    private javax.swing.JLabel L_vlt;
    private javax.swing.JLabel Lbuscar;
    private javax.swing.JButton btn_att;
    private SwingPerson.JbuttonArr btn_atualizar;
    private SwingPerson.JbuttonArr btn_limpar;
    private javax.swing.JCheckBox check_nav;
    private javax.swing.JComboBox<String> comb_p;
    private javax.swing.JComboBox<String> comb_pet;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label_av;
    private javax.swing.JLabel label_cmt;
    private javax.swing.JLabel label_cod;
    private javax.swing.JLabel label_pf;
    private javax.swing.JLabel label_pf1;
    private javax.swing.JLabel label_userBuscar;
    private javax.swing.JLabel lfundo;
    private javax.swing.JSlider slider_av;
    private javax.swing.JTextField textf_cmt;
    private javax.swing.JTextField textf_cod;
    private javax.swing.JLabel textf_nobgd;
    private javax.swing.JLabel textf_nobgd1;
    private javax.swing.JTextField textf_pe;
    private javax.swing.JTextField textf_pet;
    private javax.swing.JTextField textf_pf;
    private javax.swing.JTextField textf_pf1;
    private javax.swing.JTextField textf_userBuscar;
    // End of variables declaration//GEN-END:variables
}
