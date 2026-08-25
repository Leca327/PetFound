package InJframe;

import Logar.login;
import alert.alert;
import factory.ConnectionFactory;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

public class Notificacao extends javax.swing.JInternalFrame {

    private Connection connection;
    String admin, audio, selcod;
    int resul;
    Boolean permchefe;

    public Notificacao(String adm, String audio) {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        //
        selecttp();
        this.audio = audio;
        this.admin = adm;

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();

                audios("cl");
                try {
                    selcod = table.getValueAt(row, 0).toString();
                } catch (NullPointerException e) {
                    // Trate a exceção aqui, por exemplo, definindo um valor padrão
                    selcod = "Valor Nulo";
                }

            }
        });
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

    }

    public class CustomTableModel extends DefaultTableModel {

        public CustomTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Impede a edição dos dados na tabela
        }
    }

    public void buscartodosnot(String cod) {

        int cont = 0;
        resul = 0;
        this.connection = new ConnectionFactory().getConnection();

        String sql = "SELECT * FROM notificacao WHERE pessoa_codpessoa=?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, cod); // Defina o valor de cod aqui
            ResultSet res = stm.executeQuery();

            ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

            while (res.next()) {
                resul++;
                Object[] row = new Object[8];
                row[0] = res.getString(1);
                row[1] = res.getString(9);
                row[2] = res.getString(3);
                row[3] = res.getString(8);
                row[4] = res.getString(7);
                row[5] = res.getString(6);
                row[6] = res.getString(5);
                row[7] = res.getString(4);
                data.add(row);
            }

            // Define os nomes das colunas
            Object[] columnNames = {"Cód de Notificação", "Título", "Mensagem", "Vizualização", "Cód Serviço", "Cód Pet", "Cód Contato Pet", "Cód Contato Serviço"};

            // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
            Notificacao.CustomTableModel model = new Notificacao.CustomTableModel(data.toArray(new Object[0][0]), columnNames);

            // Configura o modelo da tabela
            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (resul == 1) {
            textf_result.setText(resul + " Resultado Encontrado");
        } else {
            textf_result.setText(resul + " Resultados Encontrados");
        }
    }

    public void buscarPF(String nick) {
        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps;

        try {
            ps = connection.prepareStatement("select * from pessoa  where nickname=?;");
            ps.setString(1, nick);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.connection = new ConnectionFactory().getConnection();

                try {
                    ps = connection.prepareStatement("SELECT * FROM pessoa WHERE nickname=?;");
                    ps.setString(1, nick);
                    ResultSet res = ps.executeQuery();

                    while (res.next()) {

                        alert al = new alert(admin, audio);
                        al.audios("ok");
                        String tx5 = (res.getString(7));
                        String tx3 = (res.getString(1));
                        String tx2 = (res.getString(11));

                        if ("PF".equals(tx5.substring(0, 2))) {
                            textf_tpp.setText("Pessoa Física");
                        } else if ("PJ".equals(tx5.substring(0, 2))) {
                            textf_tpp.setText("Pessoa Jurídica");
                        }
                        textf_tpp1.setText(tx3 + " " + tx2);
                        textf_codp.setText(tx5);
                        buscartodosnot(tx5);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Nickname não existe";
                String tit = "Pessoa inexistente";
                al.alertinput(tit, "erro", "", msg, "", "erro");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void limpar() {
        selcod = null;
        textf_codp.setText("");
        textarea_msg.setText("");
        textf_ass.setText("");
        textf_nickBuscar.setText("");
        textf_tpp.setText("");
        textf_tpp1.setText("");
        textf_result.setText("O Resultados Encontrados");
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
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

    public static String getCodnot(String msg, String cod, String msg1, String opc) {
        LocalDateTime now = LocalDateTime.now();

        // Remover os espaços das strings nick e name
        cod = cod.replaceAll("\\s", "");
        msg = msg.replaceAll("\\s", "");
        msg1 = msg.replaceAll("\\s", "");

        String dayOfMonth = String.format("%02d", now.getDayOfMonth());
        String monthValue = String.format("%02d", now.getMonthValue());
        String icod = "";

        if (null != opc) {
            switch (opc) {
                case "Comunicado Geral":
                    icod = "NOT";
                    break;
                case "Serviço":
                    icod = "NOTAPVSCV";
                    break;
                case "Pet":
                    icod = "NOTAPVPET";
                    break;
                case "Contato Pet Anunciante":
                    icod = "NOTCONTPET";
                    break;
                case "Contato Pet Contatante":
                    icod = "NOTCONTCTTTPET";
                    break;
                case "Contato Serviço Anunciante":
                    icod = "NOTCONTSVC";
                    break;
                case "Contato Serviço Contatante":
                    icod = "NOTCONTCTTTSVC";
                    break;
                default:
                    icod = "NOT";
                    break;
            }
        }

        String adminCode = icod
                + cod.substring(0, 2)
                + now.getHour()
                + now.getMinute()
                + msg.charAt(0)
                + msg.charAt(msg.length() / 2)
                + msg.charAt(msg.length() - 1)
                + msg1.charAt(0)
                + msg1.charAt(msg.length() / 2)
                + msg1.charAt(msg.length() - 1)
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
    
    public void enviarnot(String msgs, String Titulo, String em, String codent, String cod) {
        String sql;
        String selectedOption = (String) cb_tp.getSelectedItem();
        if (null != selectedOption) {
            switch (selectedOption) {
                case "Comunicado Geral":

                    sql = "INSERT INTO notificacao(pessoa_codpessoa, notifications_name, mensagem,notcod,active,dtnot,hrnot) VALUES(?,?,?,?,?,?,?);";

                    try {
                        PreparedStatement stmt = connection.prepareStatement(sql);

                        stmt.setString(1, em);
                        stmt.setString(2, Titulo);
                        stmt.setString(3, msgs);
                        stmt.setString(4, cod);
                        stmt.setBoolean(5, true);
                        stmt.setString(6, DtAtual());
                        stmt.setString(7, HrAtual());
                        stmt.execute();
                        stmt.close();

                    } catch (SQLException u) {
                        throw new RuntimeException(u);
                    }
                    break;
                case "Serviço":
                    sql = "INSERT INTO notificacao(pessoa_codpessoa, notifications_name, mensagem,notcod,s_codserv,active,dtnot,hrnot) VALUES(?,?,?,?,?,?,?,?);";

                    try {
                        PreparedStatement stmt = connection.prepareStatement(sql);

                        stmt.setString(1, em);
                        stmt.setString(2, Titulo);
                        stmt.setString(3, msgs);
                        stmt.setString(4, cod);
                        stmt.setString(5, codent);
                        stmt.setBoolean(6, true);
                        stmt.setString(7, DtAtual());
                        stmt.setString(8, HrAtual());
                        stmt.execute();
                        stmt.close();

                    } catch (SQLException u) {
                        throw new RuntimeException(u);
                    }
                    break;
                case "Pet":
                    sql = "INSERT INTO notificacao(pessoa_codpessoa, notifications_name, mensagem,notcod,p_codpet,active,dtnot,hrnot) VALUES(?,?,?,?,?,?,?,?);";

                    try {
                        PreparedStatement stmt = connection.prepareStatement(sql);

                        stmt.setString(1, em);
                        stmt.setString(2, Titulo);
                        stmt.setString(3, msgs);
                        stmt.setString(4, cod);
                        stmt.setString(5, codent);
                        stmt.setBoolean(6, true);
                        stmt.setString(7, DtAtual());
                        stmt.setString(8, HrAtual());
                        stmt.execute();
                        stmt.close();

                    } catch (SQLException u) {
                        throw new RuntimeException(u);
                    }
                    break;
                case "Contato Serviço Anunciante":
                    sql = "INSERT INTO notificacao(pessoa_codpessoa, notifications_name, mensagem,notcod,contcodcontatoserv,active,dtnot,hrnot) VALUES(?,?,?,?,?,?,?,?);";

                    try {
                        PreparedStatement stmt = connection.prepareStatement(sql);

                        stmt.setString(1, em);
                        stmt.setString(2, Titulo);
                        stmt.setString(3, msgs);
                        stmt.setString(4, cod);
                        stmt.setString(5, codent);
                        stmt.setBoolean(6, true);
                        stmt.setString(7, DtAtual());
                        stmt.setString(8, HrAtual());
                        stmt.execute();
                        stmt.close();

                    } catch (SQLException u) {
                        throw new RuntimeException(u);
                    }
                    break;
                case "Contato Pet Anunciante":
                    sql = "INSERT INTO notificacao(pessoa_codpessoa, notifications_name, mensagem,notcod,contcodcontatopet,active,dtnot,hrnot) VALUES(?,?,?,?,?,?,?,?);";

                    try {
                        PreparedStatement stmt = connection.prepareStatement(sql);

                        stmt.setString(1, em);
                        stmt.setString(2, Titulo);
                        stmt.setString(3, msgs);
                        stmt.setString(4, cod);
                        stmt.setString(5, codent);
                        stmt.setBoolean(6, true);
                        stmt.setString(7, DtAtual());
                        stmt.setString(8, HrAtual());
                        stmt.execute();
                        stmt.close();

                    } catch (SQLException u) {
                        throw new RuntimeException(u);
                    }
                    break;
                case "Contato Serviço Contatante":
                    sql = "INSERT INTO notificacao(pessoa_codpessoa, notifications_name, mensagem,notcod,contcodcontatoserv,active,dtnot,hrnot) VALUES(?,?,?,?,?,?,?,?);";

                    try {
                        PreparedStatement stmt = connection.prepareStatement(sql);

                        stmt.setString(1, em);
                        stmt.setString(2, Titulo);
                        stmt.setString(3, msgs);
                        stmt.setString(4, cod);
                        stmt.setString(5, codent);
                        stmt.setBoolean(6, true);
                        stmt.setString(7, DtAtual());
                        stmt.setString(8, HrAtual());
                        stmt.execute();
                        stmt.close();

                    } catch (SQLException u) {
                        throw new RuntimeException(u);
                    }
                    break;
                case "Contato Pet Contatante":
                    sql = "INSERT INTO notificacao(pessoa_codpessoa, notifications_name, mensagem,notcod,contcodcontatopet,active,dtnot,hrnot) VALUES(?,?,?,?,?,?,?,?);";

                    try {
                        PreparedStatement stmt = connection.prepareStatement(sql);

                        stmt.setString(1, em);
                        stmt.setString(2, Titulo);
                        stmt.setString(3, msgs);
                        stmt.setString(4, cod);
                        stmt.setString(5, codent);
                        stmt.setBoolean(6, true);
                        stmt.setString(7, DtAtual());
                        stmt.setString(8, HrAtual());
                        stmt.execute();
                        stmt.close();

                    } catch (SQLException u) {
                        throw new RuntimeException(u);
                    }
                    break;
                default:
                    break;
            }
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Notificação foi ";
            String msg2 = "Enviada com sucesso";
            String tit = "Notificação";
            al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
            String nick = textf_nickBuscar.getText();
            limpar();
            textf_nickBuscar.setText(nick);
            buscarPF(nick);

        }

    }

    public void deletar(String cod) {
        this.connection = new ConnectionFactory().getConnection();

        try {
            // Deletar admin
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM notificacao WHERE notcod = ?");
            deleteStatement.setString(1, cod);
            int i = deleteStatement.executeUpdate();

            if (i != 0) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Notificação ";
                String msg2 = "deletado do database";
                String tit = "Deleção de Notificação";
                al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                buscarPF(textf_nickBuscar.getText());
            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Erro ao deletar";
                String tit = "Deleção de Notificação";
                al.alertinput(tit, "erro", "", msg, "", "erro");
            }
        } catch (SQLException e) {
            // Outro erro SQL ocorreu, imprima o stack trace para depuração
            e.printStackTrace();

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        label_userBuscar = new javax.swing.JLabel();
        textf_nickBuscar = new javax.swing.JTextField();
        Lbuscar = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textf_tpp = new javax.swing.JTextField();
        labeltppes = new javax.swing.JLabel();
        labeltppes1 = new javax.swing.JLabel();
        textf_tpp1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        textarea_msg = new javax.swing.JTextArea();
        textf_ass = new javax.swing.JTextField();
        textf_codp = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jbuttonArr1 = new SwingPerson.JbuttonArr();
        btn_limpar2 = new SwingPerson.JbuttonArr();
        cb_tp = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        textf_codent = new javax.swing.JTextField();
        l_titp = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        textf_result = new javax.swing.JLabel();
        btn_del = new SwingPerson.JbuttonArr();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(64, 33, 7));
        jPanel1.setForeground(new java.awt.Color(64, 33, 7));
        jPanel1.setPreferredSize(new java.awt.Dimension(1284, 666));

        jPanel2.setBackground(new java.awt.Color(255, 253, 243));

        label_userBuscar.setBackground(new java.awt.Color(51, 51, 51));
        label_userBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_userBuscar.setForeground(new java.awt.Color(51, 51, 51));
        label_userBuscar.setText("NICKNAME");

        textf_nickBuscar.setBackground(new java.awt.Color(255, 253, 243));
        textf_nickBuscar.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_nickBuscar.setBorder(null);
        textf_nickBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_nickBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textf_nickBuscarMouseClicked(evt);
            }
        });
        textf_nickBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_nickBuscarKeyPressed(evt);
            }
        });

        Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        Lbuscar.setToolTipText("Buscar Email de Pessoa");
        Lbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Lbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LbuscarMouseClicked(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(363, 3));

        textf_tpp.setEditable(false);
        textf_tpp.setBackground(new java.awt.Color(204, 204, 204));

        labeltppes.setBackground(new java.awt.Color(51, 51, 51));
        labeltppes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labeltppes.setForeground(new java.awt.Color(51, 51, 51));
        labeltppes.setText("Tipo de Pessoa");

        labeltppes1.setBackground(new java.awt.Color(51, 51, 51));
        labeltppes1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labeltppes1.setForeground(new java.awt.Color(51, 51, 51));
        labeltppes1.setText("Nome");

        textf_tpp1.setEditable(false);
        textf_tpp1.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(404, 404, 404)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labeltppes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_tpp, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labeltppes1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_tpp1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(label_userBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textf_nickBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addComponent(Lbuscar)))
                .addContainerGap(404, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textf_nickBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_userBuscar))
                        .addGap(0, 0, 0)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Lbuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textf_tpp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labeltppes1))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textf_tpp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labeltppes)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        textarea_msg.setBackground(new java.awt.Color(255, 253, 243));
        textarea_msg.setColumns(20);
        textarea_msg.setLineWrap(true);
        textarea_msg.setRows(5);
        textarea_msg.setWrapStyleWord(true);
        jScrollPane1.setViewportView(textarea_msg);

        textf_ass.setBackground(new java.awt.Color(255, 253, 243));

        textf_codp.setEditable(false);
        textf_codp.setBackground(new java.awt.Color(255, 253, 243));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 253, 243));
        jLabel1.setText("Tipo de Notificação");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 253, 243));
        jLabel2.setText("Título");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 253, 243));
        jLabel3.setText("Mensagem:");

        jbuttonArr1.setText("Enviar Notificação");
        jbuttonArr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonArr1ActionPerformed(evt);
            }
        });

        btn_limpar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/apagar.png"))); // NOI18N
        btn_limpar2.setToolTipText("Limpar Todos os Campos");
        btn_limpar2.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_limpar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpar2ActionPerformed(evt);
            }
        });

        cb_tp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Comunicado Geral", "Serviço", "Pet", "Contato Pet Anunciante", "Contato Pet Contatante", "Contato Serviço Anunciante", "Contato Serviço Contatante" }));
        cb_tp.setPreferredSize(new java.awt.Dimension(134, 22));
        cb_tp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_tpActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 253, 243));
        jLabel5.setText("Código da Pessoa");

        textf_codent.setBackground(new java.awt.Color(255, 253, 243));

        l_titp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        l_titp.setForeground(new java.awt.Color(255, 253, 243));
        l_titp.setText("Código");

        table.setBackground(new java.awt.Color(204, 204, 204));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Cód de Notificação", "Título", "Mensagem", "Vizualização", "Cód Serviço", "Cód Pet", "Cód Contato Pet", "Cód Contato Serviço"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setGridColor(new java.awt.Color(255, 253, 243));
        jScrollPane2.setViewportView(table);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 253, 243));
        jLabel6.setText("Notificações Referente a Pessoa Buscada");

        textf_result.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        textf_result.setForeground(new java.awt.Color(255, 253, 243));
        textf_result.setText("0 Resultados Encontrados");
        textf_result.setToolTipText("Resultados Encontrados na Tabela de Admin");

        btn_del.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/deletar.png"))); // NOI18N
        btn_del.setToolTipText("Deletar Notificação");
        btn_del.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(l_titp)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(textf_ass)
                                .addComponent(jLabel2)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addComponent(textf_codent, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textf_codp, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(146, 146, 146)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(cb_tp, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(181, 181, 181)
                                .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textf_result, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_del, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_limpar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_tp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(l_titp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_codent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_codp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_ass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textf_result)
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_limpar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_del, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
public void verif() {
        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps;

        if (admin == null || admin.equals("")) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Você deve estar logado";
            String msg2 = "Para Efetuar isso";
            String tit = "Não Logado";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");

            login lg = new login(audio);
            lg.setVisible(true);
            dispose();
        } else {

            String msgs = textarea_msg.getText();
            String Titulo = textf_ass.getText();
            String em = textf_codp.getText();
            String coe = textf_codent.getText();
            String selectedOption = (String) cb_tp.getSelectedItem();
            if (textarea_msg.getText().isEmpty() || textf_codp.getText().isEmpty() || textf_ass.getText().isEmpty()) {

                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Preencha todos os campos";
                String msg2 = "Para enviar a notificação.";
                String tit = "Notificação não enviada";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            } else {
                String cod = getCodnot(Titulo, em, msgs, selectedOption);
                try {
                    ResultSet rs;
                    ps = connection.prepareStatement("select * from pessoa  where pcod=?;");
                    ps.setString(1, em);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        selectedOption = (String) cb_tp.getSelectedItem();
                        if (null != selectedOption) {
                            switch (selectedOption) {
                                case "Comunicado Geral":
                                    enviarnot(msgs, Titulo, em, null, cod);
                                    break;
                                case "Serviço":
                                    ps = connection.prepareStatement("select * from servico  where servcod=?;");
                                    ps.setString(1, coe);
                                    rs = ps.executeQuery();
                                    if (rs.next()) {
                                        enviarnot(msgs, Titulo, em, coe, cod);
                                    } else {
                                        alert al = new alert(admin, audio);
                                        al.setVisible(true);
                                        String msg = "Código de Serviço";
                                        String msg2 = "Não Encontrado.";
                                        String tit = "Código Incorreto";
                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                    }
                                    break;
                                case "Pet":
                                    ps = connection.prepareStatement("select * from pet  where petcod=?;");
                                    ps.setString(1, coe);
                                    rs = ps.executeQuery();
                                    if (rs.next()) {
                                        enviarnot(msgs, Titulo, em, coe, cod);
                                    } else {
                                        alert al = new alert(admin, audio);
                                        al.setVisible(true);
                                        String msg = "Código de Pet";
                                        String msg2 = "Não Encontrado.";
                                        String tit = "Código Incorreto";
                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                    }
                                    break;
                                case "Contato Serviço Anunciante":
                                    ps = connection.prepareStatement("select * from contatoserv  where codconts=?;");
                                    ps.setString(1, coe);
                                    rs = ps.executeQuery();
                                    if (rs.next()) {
                                        ps = connection.prepareStatement("select * from pessoa  where pcod=(select pessoa_codp from servico  where servcod=(select scodserv from contatoserv  where codconts=?) );");
                                        ps.setString(1, coe);
                                        rs = ps.executeQuery();
                                        if (rs.next()) {
                                            String codanun = rs.getString("pcod");
                                            if (codanun == null ? textf_codp.getText() == null : codanun.equals(textf_codp.getText())) {
                                                enviarnot(msgs, Titulo, em, coe, cod);
                                            } else {
                                                alert al = new alert(admin, audio);
                                                al.setVisible(true);
                                                String msg = "Pessoa de Contato";
                                                String msg2 = "indicada não ";
                                                String msg3 = "é o anunciante.";
                                                String tit = "Pessoa Incorreto";
                                                al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                                            }

                                        } else {
                                            alert al = new alert(admin, audio);
                                            al.setVisible(true);
                                            String msg = "Pessoa de Contato";
                                            String msg2 = "indicada não ";
                                            String msg3 = "encontrada.";
                                            String tit = "Pessoa Incorreto";
                                            al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                                        }
                                    } else {
                                        alert al = new alert(admin, audio);
                                        al.setVisible(true);
                                        String msg = "Código de Contato";
                                        String msg2 = "Serviço não";
                                        String msg3 = "Encontrado.";
                                        String tit = "Código Incorreto";
                                        al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                                    }
                                    break;
                                case "Contato Pet Anunciante":
                                    ps = connection.prepareStatement("select * from contatopet  where codcontp=?;");
                                    ps.setString(1, coe);
                                    rs = ps.executeQuery();
                                    if (rs.next()) {
                                        ps = connection.prepareStatement("select * from pessoa  where pcod=(select pessoa_codp from servico  where servcod=(select scodserv from contatopet  where codcontp=?) );");
                                        ps.setString(1, coe);
                                        rs = ps.executeQuery();
                                        if (rs.next()) {
                                            String codanun = rs.getString("pcod");
                                            if (codanun == null ? textf_codp.getText() == null : codanun.equals(textf_codp.getText())) {
                                                enviarnot(msgs, Titulo, em, coe, cod);
                                            } else {
                                                alert al = new alert(admin, audio);
                                                al.setVisible(true);
                                                String msg = "Pessoa de Contato";
                                                String msg2 = "indicada não ";
                                                String msg3 = "é o anunciante.";
                                                String tit = "Pessoa Incorreto";
                                                al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                                            }

                                        } else {
                                            alert al = new alert(admin, audio);
                                            al.setVisible(true);
                                            String msg = "Pessoa de Contato";
                                            String msg2 = "indicada não ";
                                            String msg3 = "encontrada.";
                                            String tit = "Pessoa Incorreto";
                                            al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                                        }
                                    } else {
                                        alert al = new alert(admin, audio);
                                        al.setVisible(true);
                                        String msg = "Código de Contato";
                                        String msg2 = "Pet não Encontrado";
                                        String tit = "Código Incorreto";
                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                    }
                                    break;
                                case "Contato Serviço Contatante":
                                    ps = connection.prepareStatement("select * from contatoserv  where codconts=?;");
                                    ps.setString(1, coe);
                                    rs = ps.executeQuery();
                                    if (rs.next()) {
                                        String codcttt = rs.getString("pcodp");

                                        if (codcttt.equals(textf_codp.getText())) {
                                            enviarnot(msgs, Titulo, em, coe, cod);
                                        } else {
                                            alert al = new alert(admin, audio);
                                            al.setVisible(true);
                                            String msg = "Pessoa indicada";
                                            String msg2 = "Não é a contatante";
                                            String msg3 = "do contato.";
                                            String tit = "Pessoa Incorreto";
                                            al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                                        }

                                    } else {
                                        alert al = new alert(admin, audio);
                                        al.setVisible(true);
                                        String msg = "Código de Contato";
                                        String msg2 = "Serviço não";
                                        String msg3 = "Encontrado.";
                                        String tit = "Código Incorreto";
                                        al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                                    }
                                    break;
                                case "Contato Pet Contatante":
                                    ps = connection.prepareStatement("select * from pessoa  where emailp=?;");
                                    ps.setString(1, coe);
                                    rs = ps.executeQuery();
                                    if (rs.next()) {
                                        String codcttt = rs.getString("pfcodp");
                                        if (codcttt.equals(textf_codp.getText())) {
                                            enviarnot(msgs, Titulo, em, coe, cod);
                                        } else {
                                            alert al = new alert(admin, audio);
                                            al.setVisible(true);
                                            String msg = "Pessoa indicada";
                                            String msg2 = "Não é a contatante";
                                            String msg3 = "do contato.";
                                            String tit = "Pessoa Incorreto";
                                            al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                                        }
                                    } else {
                                        alert al = new alert(admin, audio);
                                        al.setVisible(true);
                                        String msg = "Código de Contato";
                                        String msg2 = "Pet não Encontrado";
                                        String tit = "Código Incorreto";
                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }

                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Código não cadastrado";
                        String msg2 = "No Sistema.";
                        String tit = "Campos Vazios";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                        buscartodosnot(cod);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void selecttp() {
        String selectedOption = (String) cb_tp.getSelectedItem();
        if (null != selectedOption) {
            switch (selectedOption) {
                case "Comunicado Geral":
                    l_titp.setVisible(false);
                    textf_codent.setVisible(false);
                    break;
                case "Serviço":
                    l_titp.setVisible(true);
                    textf_codent.setVisible(true);
                    l_titp.setText("Código de Serviço");
                    break;
                case "Pet":
                    l_titp.setVisible(true);
                    textf_codent.setVisible(true);
                    l_titp.setText("Código de Pet");
                    break;
                case "Contato Serviço Anunciante":
                    l_titp.setVisible(true);
                    textf_codent.setVisible(true);
                    l_titp.setText("Código de Contato Serviço");
                    break;
                case "Contato Pet Anunciante":
                    l_titp.setVisible(true);
                    textf_codent.setVisible(true);
                    l_titp.setText("Código de Contato Pet");
                    break;
                case "Contato Serviço Contatante":
                    l_titp.setVisible(true);
                    textf_codent.setVisible(true);
                    l_titp.setText("Código de Contato Serviço");
                    break;
                case "Contato Pet Contatante":
                    l_titp.setVisible(true);
                    textf_codent.setVisible(true);
                    l_titp.setText("Código de Contato Pet");
                    break;
                default:
                    break;
            }
        }
    }

    public void del() {
        if (admin == null || admin.equals("")) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Você deve estar logado";
            String msg2 = "Para Efetuar isso";
            String tit = "Não Logado";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");

            login lg = new login(audio);
            lg.setVisible(true);
            dispose();
        } else {
            if (permchefe == true) {

                if (selcod == null || selcod == "") {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Selecione a Notificação";
                    String msg2 = "Para Deletar.";
                    String tit = "Deletar";
                    al.alertinput(tit, "info", msg, msg2, "", "info");
                } else {
                    deletar(selcod);
                }

            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg2 = "Você não tem ";
                String msg3 = "Permissão suficiente";
                String tit = "Sem Permissão";
                al.alertinput(tit, "erro", msg2, msg3, "", "erro");
            }
        }
    }

    private void textf_nickBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_nickBuscarMouseClicked
        textf_nickBuscar.setText("");
    }//GEN-LAST:event_textf_nickBuscarMouseClicked

    private void textf_nickBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nickBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscarPF(textf_nickBuscar.getText());
        }
    }//GEN-LAST:event_textf_nickBuscarKeyPressed

    private void LbuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LbuscarMouseClicked
        buscarPF(textf_nickBuscar.getText());
    }//GEN-LAST:event_LbuscarMouseClicked

    private void jbuttonArr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonArr1ActionPerformed
        verif();

    }//GEN-LAST:event_jbuttonArr1ActionPerformed

    private void btn_limpar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpar2ActionPerformed
        audios("cl");
        limpar();
    }//GEN-LAST:event_btn_limpar2ActionPerformed

    private void cb_tpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_tpActionPerformed
        selecttp();
    }//GEN-LAST:event_cb_tpActionPerformed

    private void btn_delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delActionPerformed
        del();
    }//GEN-LAST:event_btn_delActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Lbuscar;
    private SwingPerson.JbuttonArr btn_del;
    private SwingPerson.JbuttonArr btn_limpar2;
    private javax.swing.JComboBox<String> cb_tp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private SwingPerson.JbuttonArr jbuttonArr1;
    private javax.swing.JLabel l_titp;
    private javax.swing.JLabel label_userBuscar;
    private javax.swing.JLabel labeltppes;
    private javax.swing.JLabel labeltppes1;
    private javax.swing.JTable table;
    private javax.swing.JTextArea textarea_msg;
    private javax.swing.JTextField textf_ass;
    private javax.swing.JTextField textf_codent;
    private javax.swing.JTextField textf_codp;
    private javax.swing.JTextField textf_nickBuscar;
    private javax.swing.JLabel textf_result;
    private javax.swing.JTextField textf_tpp;
    private javax.swing.JTextField textf_tpp1;
    // End of variables declaration//GEN-END:variables
}
