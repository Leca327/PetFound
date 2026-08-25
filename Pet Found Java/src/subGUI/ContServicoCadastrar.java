package subGUI;

import alert.alert;
import dao.ContServDAO;
import factory.ConnectionFactory;
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
import modelo.ContServMOD;
import starter.Icone;

public class ContServicoCadastrar extends javax.swing.JFrame {

    private Connection connection;
    Boolean maxc;
    String adm, audio, nome;

    public ContServicoCadastrar(String admin, String au) {
        initComponents();
        adm = admin;
        audio = au;
        setIcon();
        buspessoa();
        buspet();
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
    }

    public Boolean maxperm() {
        maxc = !((textf_pf.getText().length() <= 50) && (textf_pet.getText().length() <= 50));
        return maxc;
    }

    public static String generateContpetCode(String name, String serv) {
        LocalDateTime now = LocalDateTime.now();

        // Remover os espaços das strings nick e name
        name = name.replaceAll("\\s", "");
        serv = serv.replaceAll("\\s", "");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        String dayOfMonth = String.format("%02d", now.getDayOfMonth());
        String monthValue = String.format("%02d", now.getMonthValue());

        String adminCode = "CONT"
                + serv.substring(0, 5)
                + now.getHour()
                + now.getMinute()
                + name.substring(0, 6)
                + dayOfMonth
                + monthValue
                + now.getYear();

        return adminCode;
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

    public void bus(String pet) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM servico WHERE servcod = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, pet);
            ResultSet res = stm.executeQuery();

            if (res.next()) { // Move o cursor para a primeira linha (se houver resultados)
                nome = res.getString("nomeserv"); // Substitua "1" pelo nome da coluna
            } else {
                // Não houve resultados para a consulta
                nome = null; // Ou qualquer outro tratamento que você queira fazer
            }

            // Feche a conexão, o PreparedStatement e o ResultSet quando terminar
            res.close();
            stm.close();
            connection.close();
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
                comb_pet.removeItem("Sem Serviços");
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

    public void cadastro() {
        Boolean perm = maxperm();
        if (perm == false) {
            String user = textf_pf.getText();
            String pet = textf_pet.getText();
            this.connection = new ConnectionFactory().getConnection();
            PreparedStatement ps;
            String status = "";

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

                        ps = connection.prepareStatement("select * from contatoserv where pcodp=? and scodserv=?;");
                        ps.setString(1, user);
                        ps.setString(2, pet);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            alert al = new alert(adm, audio);
                            al.setVisible(true);
                            String msg = "Contato para este Serviço";
                            String msg1 = "Dessa Pessoa já Existe";
                            String tit = "Contato existente";
                            al.alertinput(tit, "erro", msg, msg1, "", "erro");

                        } else {
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
                                    if ((textf_pf.getText().isEmpty()) || (textf_pet.getText().isEmpty())) {
                                        alert al = new alert(adm, audio);
                                        al.setVisible(true);
                                        String msg = "Os campos não podem";
                                        String msg2 = "retornar vazios";
                                        String tit = "Campo(s) de Texto Vazio";
                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");

                                    } else {
                                        bus(pet);
                                        String contpetcod = generateContpetCode(textf_pf.getText(), pet);
                                        ContServMOD cp = new ContServMOD();
                                        String date = DtAtual();
                                        String hr = HrAtual();
                                        cp.setCodcontp(contpetcod);
                                        cp.setServcod(textf_pet.getText());
                                        cp.setPcod(textf_pf.getText());
                                        cp.setDt(date);
                                        cp.setHr(hr);

                                        // instanciando a classe UsuarioDAO do pacote dao e criando seu objeto dao
                                        ContServDAO dao = new ContServDAO();
                                        dao.adiciona(cp);
                                        alert al = new alert(adm, audio);
                                        al.setVisible(true);
                                        String msg = "Contato do Serviço " + nome;
                                        String msg2 = " inserido(a) com sucesso";
                                        String tit = "Cadastro de Admin";
                                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                                        dispose();
                                        limpar();

                                    }
                                }
                                // }
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

            } catch (Exception e) {
                e.printStackTrace();
            }
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
        btn_cadastrar = new SwingPerson.JbuttonArr();
        btn_limpar = new SwingPerson.JbuttonArr();
        L_vlt = new javax.swing.JLabel();
        lfundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pet Found - Cadastrar Contato Serviço");
        setMinimumSize(new java.awt.Dimension(658, 338));
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

        textf_pf.setBackground(new java.awt.Color(204, 204, 204));
        textf_pf.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
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

        textf_pet.setBackground(new java.awt.Color(204, 204, 204));
        textf_pet.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
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

        btn_cadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cadadc.png"))); // NOI18N
        btn_cadastrar.setToolTipText("Cadastre Um Admin");
        btn_cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadastrarActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(label_pf)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textf_pf1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(textf_pf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comb_p, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(77, 77, 77)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(label_pf1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textf_pe, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(textf_pet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comb_pet, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(L_vlt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(btn_cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(227, 227, 227))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_pf)
                            .addComponent(textf_pf1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_pf, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comb_p, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_pf1)
                            .addComponent(textf_pe, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_pet, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comb_pet, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(L_vlt)
                        .addContainerGap())))
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
                    textf_pe.setText("Serviço Não Encontrada");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            textf_pf1.setText(""); // Define o campo como vazio se o texto estiver vazio
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

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        if (comb_p.getSelectedItem() == null || comb_p.getSelectedItem() == "") {

        } else {
            textf_pf.setText(comb_p.getSelectedItem().toString());
            bsp();
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void textf_pfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_pfKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_pfKeyPressed

    private void textf_pfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_pfKeyReleased
        bsp();
        String userInput = textf_pf.getText().toLowerCase();
        for (int i = 0; i < comb_p.getItemCount(); i++) {
            String item = comb_p.getItemAt(i).toLowerCase();
            if (item.startsWith(userInput)) {
                comb_p.setSelectedIndex(i);
                return;
            }
        }
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
            cadastro();
        }
    }//GEN-LAST:event_textf_petKeyPressed

    private void textf_petKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_petKeyReleased
        bspet();
        String userInput = textf_pf.getText().toLowerCase();
        for (int i = 0; i < comb_p.getItemCount(); i++) {
            String item = comb_p.getItemAt(i).toLowerCase();
            if (item.startsWith(userInput)) {
                comb_p.setSelectedIndex(i);
                return;
            }
        }
    }//GEN-LAST:event_textf_petKeyReleased

    private void btn_cadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadastrarActionPerformed
        cadastro();
    }//GEN-LAST:event_btn_cadastrarActionPerformed

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        audios("cl");
        lmp();
    }//GEN-LAST:event_btn_limparActionPerformed

    private void L_vltMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_vltMouseClicked
        vlt();
    }//GEN-LAST:event_L_vltMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String au = "";
                String ad = "";
                new ContServicoCadastrar(ad, au).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel L_vlt;
    private SwingPerson.JbuttonArr btn_cadastrar;
    private SwingPerson.JbuttonArr btn_limpar;
    private javax.swing.JComboBox<String> comb_p;
    private javax.swing.JComboBox<String> comb_pet;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label_pf;
    private javax.swing.JLabel label_pf1;
    private javax.swing.JLabel lfundo;
    private javax.swing.JTextField textf_pe;
    private javax.swing.JTextField textf_pet;
    private javax.swing.JTextField textf_pf;
    private javax.swing.JTextField textf_pf1;
    // End of variables declaration//GEN-END:variables
}
