package InJframe;

import Logar.login;
import alert.alert;
import factory.ConnectionFactory;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
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
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

public class Ticket extends javax.swing.JInternalFrame {

    private Connection connection;
    String admin, audio, selcod, admcod;
    int resul, tipo2, cont;
    Boolean peguei;

    public Ticket(String adm, String audio) {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        //sss
        this.audio = audio;
        this.admin = adm;
        buscarTodostic();
        bus(admin);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();

                audios("cl");
                selcod = table.getValueAt(row, 5).toString();
                Buscatt(selcod);
                Object value = table.getValueAt(row, column);
                String campoSelecionado;
                if (value != null && !value.toString().isEmpty()) {
                    campoSelecionado = value.toString();
                    textf_copiado.setText(campoSelecionado);
                } else {
                    campoSelecionado = "";
                    textf_copiado.setText("");

                }

                if (selcod != null) {

                    radio_ouadm.setEnabled(false);
                    if (radio_ouadm.isSelected()) {
                        radio_ninguempegou.setEnabled(false);
                        radio_meuadm.setEnabled(false);
                    } else {
                        radio_ninguempegou.setEnabled(true);
                        radio_meuadm.setEnabled(true);
                    }

                    if (peguei == true) {
                        radio_resol.setEnabled(true);
                        radio_aberto.setEnabled(true);
                        radio_fechar.setEnabled(true);
                        jButton2.setEnabled(true);
                    } else {
                        radio_resol.setEnabled(false);
                        radio_aberto.setEnabled(false);
                        radio_fechar.setEnabled(false);
                        texarea_mt.setEnabled(false);
                        jButton2.setEnabled(false);
                    }
                } else {
                    radio_ninguempegou.setEnabled(false);
                    radio_meuadm.setEnabled(false);
                    radio_ouadm.setEnabled(false);
                    radio_resol.setEnabled(false);
                    radio_aberto.setEnabled(false);
                    radio_fechar.setEnabled(false);
                    texarea_mt.setEnabled(false);
                    jButton2.setEnabled(false);
                }
            }
        });

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
                admcod = tx4;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void limfiltro() {
        cb_dst.setSelectedItem("Todos os Tickets");
        cb_ordem.setSelectedItem("Mais Recentes");

    }

    public void tsl() {
        radio_ninguempegou.setEnabled(false);
        radio_meuadm.setEnabled(false);
        radio_ouadm.setEnabled(false);
        radio_resol.setEnabled(false);
        radio_aberto.setEnabled(false);
        radio_fechar.setEnabled(false);
        texarea_mt.setEnabled(false);
        jButton2.setEnabled(false);
        peguei = null;
        table.clearSelection();
        textf_copiado.setText("");
        texarea_mt.setText("");
        bgp_final.clearSelection();
        bgp_pegar.clearSelection();
    }

    public void Buscatt(String cod) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "";

        sql = "select * from faleconosco where fccod= '" + cod + "'";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            while (res.next()) {
                String pegar = res.getString("adminfc");

                if (res.wasNull()) {
                    radio_ninguempegou.setSelected(true);
                    peguei = false;
                } else if (pegar.equals(admcod)) {
                    radio_meuadm.setSelected(true);
                    peguei = true;
                } else {
                    peguei = false;
                    radio_ouadm.setSelected(true);
                    radio_meuadm.setSelected(false);
                    radio_ninguempegou.setSelected(false);

                }

                boolean fina = res.getBoolean("statusfc");
                if (fina) {
                    radio_resol.setSelected(true);
                    texarea_mt.setEnabled(true);

                } else if (res.wasNull()) {
                    radio_aberto.setSelected(true);

                } else {
                    radio_fechar.setSelected(true);
                    texarea_mt.setEnabled(true);

                }
                String situ = res.getString("situfinal");
                texarea_mt.setText(situ);
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

    public void limpar() {
        radio_ninguempegou.setEnabled(false);
        radio_meuadm.setEnabled(false);
        radio_ouadm.setEnabled(false);
        radio_resol.setEnabled(false);
        radio_aberto.setEnabled(false);
        radio_fechar.setEnabled(false);
        texarea_mt.setEnabled(false);
        jButton2.setEnabled(false);
        peguei = null;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        tipo2 = 0;
        textf_copiado.setText("");
        selcod = "";
        textf_result.setText("0 Resultados Encontrados");
        limfiltro();
        texarea_mt.setText("");
        bgp_final.clearSelection();
        bgp_pegar.clearSelection();
    }

    public void buscarTodostic() {
        resul = 0;
        String bloq, ord;

        String selectedOption = (String) cb_dst.getSelectedItem();
        if (null == selectedOption) {
            bloq = "";
        } else {
            switch (selectedOption) {
                case "Tickets Fechados":
                    bloq = "WHERE statusfc=false";
                    break;
                case "Tickets Abertos":
                    bloq = "WHERE statusfc is NULL";
                    break;
                case "Tickets Resolvidos":
                    bloq = "WHERE statusfc = true";
                    break;
                default:
                    bloq = "";
                    break;
            }
        }

        selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = "ORDER BY dtfc DESC, hrfc DESC;";
        } else {
            ord = "ORDER BY dtfc ASC, hrfc ASC;";
        }

        resul = 0;
        tipo2 = 0;
        this.connection = new ConnectionFactory().getConnection();

        String sql = "SELECT * FROM faleconosco " + bloq + " " + ord;
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

            boolean result = res.next();
            if (result) {
                audios("ok");

                while (result) {
                    resul++;
                    Object[] row = new Object[10];
                    row[0] = res.getString("assunto");
                    row[1] = res.getString("titulofc");
                    row[2] = res.getString("descfc");
                    row[3] = res.getString("nomepes");
                    row[4] = res.getString("emailfc");
                    row[5] = res.getString("fccod");
                    row[6] = res.getString("dtfc");
                    row[7] = res.getString("hrfc");
                    row[8] = res.getString("adminfc");
                    row[9] = res.getString("situfinal");
                    data.add(row);
                    result = res.next();
                }
                cont++;
            } else {
                if (cont == 0) {
                    cont++;
                } else {
                    resul = 0;
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Nenhum registro encontrado";
                    String msg2 = "no Banco de Dados";
                    String tit = "Informação";
                    al.alertinput(tit, "info", msg, msg2, "", "info");
                }
            }
            // Define os nomes das colunas
            Object[] columnNames = {"Assunto", "Título", "Descrição", "Nome", "E-mail", "Código", "Data de Criação", "Hora de Criação", "Admin no Ticket", "Situação final"};

            // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
            Ticket.CustomTableModel model = new Ticket.CustomTableModel(data.toArray(new Object[0][0]), columnNames);

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

    public class CustomTableModel extends DefaultTableModel {

        public CustomTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Impede a edição dos dados na tabela
        }
    }

    public void atualizarlig(String Cod) {

        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps;
        String status = "";

        if ((admin == null) || (admin == "")) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Admin não logado";
            String tit = "Detalhes do Contato não atualizados";
            al.alertinput(tit, "erro", "", msg, "", "erro");

            login lg = new login(audio);
            lg.setVisible(true);
            dispose();
        } else {

            if (selcod == null || "".equals(selcod)) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Selecione na Tabela";
                String msg2 = "quem deseja Atualizar";
                String tit = "Nenhum Contato selecionado";
                al.alertinput(tit, "info", msg, msg2, "", "info");

            } else if (radio_meuadm.isSelected()) {

                try {
                    ps = connection.prepareStatement("update faleconosco set adminfc=? ,situfinal=?, statusfc=?,dtfc=?,hrfc=? where fccod=?");
                    ps.setString(1, admcod);
                    ps.setNull(2, java.sql.Types.BOOLEAN);
                    ps.setNull(3, java.sql.Types.BOOLEAN);
                    ps.setNull(4, java.sql.Types.BOOLEAN);
                    ps.setNull(5, java.sql.Types.BOOLEAN);
                    ps.setString(6, selcod);
                    int i = ps.executeUpdate();
                    if (i != 0) {

                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Ticket";
                        String msg2 = "atualizado com sucesso";
                        String tit = "Atualização de Ticket";
                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                        buscarTodostic();
                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Ticket";
                        String msg2 = "não atualizados";
                        String tit = "Atualização de Ticket";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    }
                    buscarTodostic();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (radio_ninguempegou.isSelected()) {

                try {
                    ps = connection.prepareStatement("update faleconosco set adminfc=? ,situfinal=?, statusfc=?,dtfc=?,hrfc=? where fccod=?");
                    ps.setNull(1, java.sql.Types.BOOLEAN);
                    ps.setNull(2, java.sql.Types.BOOLEAN);
                    ps.setNull(3, java.sql.Types.BOOLEAN);
                    ps.setNull(4, java.sql.Types.BOOLEAN);
                    ps.setNull(5, java.sql.Types.BOOLEAN);
                    ps.setString(6, selcod);
                    int i = ps.executeUpdate();
                    if (i != 0) {
                        radio_resol.setEnabled(false);
                        radio_aberto.setEnabled(false);
                        radio_fechar.setEnabled(false);
                        texarea_mt.setEnabled(false);
                        jButton2.setEnabled(false);

                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Ticket";
                        String msg2 = "atualizado com sucesso";
                        String tit = "Atualização de Contato";
                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Ticket";
                        String msg2 = "não atualizados";
                        String tit = "Atualização de Ticket";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    }
                    buscarTodostic();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Nenhum botão de";
                String msg2 = "rádio selecionado";
                String tit = "Seleção do Radio";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            }
            Buscatt(selcod);
        }
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

    public void atualizar(String Cod) {
        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps;
        String status = "";

        if ((admin == null) || (admin == "")) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Admin não logado";
            String tit = "Detalhes do Contato não atualizados";
            al.alertinput(tit, "erro", "", msg, "", "erro");

            login lg = new login(audio);
            lg.setVisible(true);
            dispose();
        } else {

            if (selcod == null || "".equals(selcod)) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Selecione na Tabela";
                String msg2 = "quem deseja Atualizar";
                String tit = "Nenhum Contato selecionado";
                al.alertinput(tit, "info", msg, msg2, "", "info");

            } else if (radio_resol.isSelected()) {
                if (!texarea_mt.getText().isEmpty()) {
                    try {
                        ps = connection.prepareStatement("update faleconosco set situfinal=?, statusfc=?,dtfc=?,hrfc=? where fccod=?");
                        ps.setString(1, texarea_mt.getText());
                        ps.setBoolean(2, true);
                        ps.setString(3, DtAtual());
                        ps.setString(4, HrAtual());
                        ps.setString(5, selcod);
                        int i = ps.executeUpdate();

                        if (i != 0) {

                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Ticket";
                            String msg2 = "atualizado com sucesso";
                            String tit = "Atualização de Contato";
                            al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

                        } else {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Ticket";
                            String msg2 = "não atualizados";
                            String tit = "Atualização de Ticket";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");
                        }
                        buscarTodostic();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    buscarTodostic();
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Preencha a situação";
                    String msg2 = "final do ticket";
                    String tit = "Atualização de Ticket";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                }
            } else if (radio_aberto.isSelected()) {
                try {
                    ps = connection.prepareStatement("update faleconosco set situfinal=?, statusfc=?,dtfc=?,hrfc=? where fccod=?");
                    ps.setNull(1, java.sql.Types.BOOLEAN);
                    ps.setNull(2, java.sql.Types.BOOLEAN);
                    ps.setNull(3, java.sql.Types.BOOLEAN);
                    ps.setNull(4, java.sql.Types.BOOLEAN);
                    ps.setString(5, selcod);
                    int i = ps.executeUpdate();

                    if (i != 0) {

                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Ticket";
                        String msg2 = "atualizado com sucesso";
                        String tit = "Atualização de Contato";
                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Ticket";
                        String msg2 = "não atualizados";
                        String tit = "Atualização de Ticket";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    }
                    buscarTodostic();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                buscarTodostic();
            } else if (radio_fechar.isSelected()) {
                if (!texarea_mt.getText().isEmpty()) {
                    try {
                        ps = connection.prepareStatement("update faleconosco set situfinal=?, statusfc=?,dtfc=?,hrfc=? where fccod=?");
                        ps.setString(1, texarea_mt.getText());
                        ps.setBoolean(2, false);
                        ps.setString(3, DtAtual());
                        ps.setString(4, HrAtual());
                        ps.setString(5, selcod);
                        int i = ps.executeUpdate();

                        if (i != 0) {

                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Ticket";
                            String msg2 = "atualizado com sucesso";
                            String tit = "Atualização de Contato";
                            al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

                        } else {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Ticket";
                            String msg2 = "não atualizados";
                            String tit = "Atualização de Ticket";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");
                        }
                        buscarTodostic();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    buscarTodostic();
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Preencha a situação";
                    String msg2 = "final do ticket";
                    String tit = "Atualização de Ticket";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                }
            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Nenhum botão de";
                String msg2 = "rádio selecionado";
                String tit = "Seleção do Radio";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgp_final = new javax.swing.ButtonGroup();
        bgp_pegar = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cb_ordem = new javax.swing.JComboBox<>();
        cb_dst = new javax.swing.JComboBox<>();
        btn_lmpfil = new SwingPerson.JbuttonArr();
        btn_filpet = new SwingPerson.JbuttonArr();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btn_tirarsel = new SwingPerson.JbuttonArr();
        btn_limpar = new SwingPerson.JbuttonArr();
        label_copy = new javax.swing.JLabel();
        label_selecionado = new javax.swing.JLabel();
        textf_copiado = new javax.swing.JTextField();
        Latt = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        radio_meuadm = new javax.swing.JRadioButton();
        radio_ouadm = new javax.swing.JRadioButton();
        label_ajuda3 = new javax.swing.JLabel();
        radio_ninguempegou = new javax.swing.JRadioButton();
        jbuttonArr1 = new SwingPerson.JbuttonArr();
        textf_result = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        radio_fechar = new javax.swing.JRadioButton();
        radio_aberto = new javax.swing.JRadioButton();
        label_ajuda1 = new javax.swing.JLabel();
        radio_resol = new javax.swing.JRadioButton();
        jButton2 = new SwingPerson.JbuttonArr();
        jScrollPane2 = new javax.swing.JScrollPane();
        texarea_mt = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(64, 33, 7));
        jPanel1.setPreferredSize(new java.awt.Dimension(1284, 666));

        jPanel2.setBackground(new java.awt.Color(255, 253, 243));

        cb_ordem.setBackground(new java.awt.Color(255, 253, 243));
        cb_ordem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mais Recentes", "Mais Antigos" }));
        cb_ordem.setToolTipText("Buscar Admin Mais Recente/Antigos\n");
        cb_ordem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb_ordem.setPreferredSize(new java.awt.Dimension(202, 22));
        cb_ordem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_ordemActionPerformed(evt);
            }
        });

        cb_dst.setBackground(new java.awt.Color(255, 253, 243));
        cb_dst.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos os Tickets", "Tickets Abertos", "Tickets Fechados", "Tickets Resolvidos" }));
        cb_dst.setToolTipText("Buscar Tickets abertos ou fechados");
        cb_dst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb_dst.setMinimumSize(new java.awt.Dimension(202, 22));
        cb_dst.setPreferredSize(new java.awt.Dimension(202, 22));

        btn_lmpfil.setText("Limpar Filtros");
        btn_lmpfil.setToolTipText("Limpar todos os filtros");
        btn_lmpfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lmpfilActionPerformed(evt);
            }
        });

        btn_filpet.setText("Filtrar Ticket");
        btn_filpet.setToolTipText("Procurar Admin com os filtros");
        btn_filpet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filpetActionPerformed(evt);
            }
        });

        table.setBackground(new java.awt.Color(204, 204, 204));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Assunto", "Título", "Descrição", "Nome", "E-mail", "Código", "Data de Criação", "Hora de Criação", "Admin no Ticket", "Situação final"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table.setGridColor(new java.awt.Color(255, 253, 243));
        jScrollPane1.setViewportView(table);

        btn_tirarsel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_tselect.png"))); // NOI18N
        btn_tirarsel.setToolTipText("Tirar Seleção da Tabela");
        btn_tirarsel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tirarselActionPerformed(evt);
            }
        });

        btn_limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_apagar.png"))); // NOI18N
        btn_limpar.setToolTipText("Limpar Infomações");
        btn_limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparActionPerformed(evt);
            }
        });

        label_copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_copy.png"))); // NOI18N
        label_copy.setToolTipText("Copiar Informação no Campo de Seleção");
        label_copy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_copy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_copyMouseClicked(evt);
            }
        });

        label_selecionado.setBackground(new java.awt.Color(64, 33, 7));
        label_selecionado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_selecionado.setForeground(new java.awt.Color(64, 33, 7));
        label_selecionado.setText("Valor Selecionado");

        textf_copiado.setEditable(false);
        textf_copiado.setBackground(new java.awt.Color(204, 204, 204));
        textf_copiado.setToolTipText("Campo Selecionado na Tabela");
        textf_copiado.setBorder(null);
        textf_copiado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        textf_copiado.setMinimumSize(new java.awt.Dimension(500, 20));
        textf_copiado.setPreferredSize(new java.awt.Dimension(500, 20));

        Latt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_att.png"))); // NOI18N
        Latt.setToolTipText("Busque Todos e Atualize o Banco de Dados");
        Latt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Latt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LattMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(label_selecionado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_copiado, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_copy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_ordem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_lmpfil, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_filpet, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(Latt))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_tirarsel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cb_ordem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(label_copy, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textf_copiado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_selecionado)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cb_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_lmpfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_filpet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Latt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_tirarsel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_limpar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 253, 243));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Atualizar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel6.setPreferredSize(new java.awt.Dimension(322, 60));

        radio_meuadm.setBackground(new java.awt.Color(255, 253, 243));
        bgp_pegar.add(radio_meuadm);
        radio_meuadm.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_meuadm.setText("Aceitar");
        radio_meuadm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_meuadm.setEnabled(false);
        radio_meuadm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_meuadmActionPerformed(evt);
            }
        });

        radio_ouadm.setBackground(new java.awt.Color(255, 253, 243));
        bgp_pegar.add(radio_ouadm);
        radio_ouadm.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_ouadm.setText("Aceito por outro");
        radio_ouadm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_ouadm.setEnabled(false);
        radio_ouadm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_ouadmActionPerformed(evt);
            }
        });

        label_ajuda3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_ajuda3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajuda3.setToolTipText("<html>\n<p>\nAceitar um ticket para ser resolvido.<br>\n</p>\n</html>");
        label_ajuda3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label_ajuda3.setMinimumSize(new java.awt.Dimension(0, 0));
        label_ajuda3.setPreferredSize(new java.awt.Dimension(15, 22));

        radio_ninguempegou.setBackground(new java.awt.Color(255, 253, 243));
        bgp_pegar.add(radio_ninguempegou);
        radio_ninguempegou.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_ninguempegou.setText("Nulo");
        radio_ninguempegou.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_ninguempegou.setEnabled(false);
        radio_ninguempegou.setFocusable(false);
        radio_ninguempegou.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_ninguempegouActionPerformed(evt);
            }
        });

        jbuttonArr1.setText("Enviar");
        jbuttonArr1.setToolTipText("Enviar Novas Informações");
        jbuttonArr1.setPreferredSize(new java.awt.Dimension(32, 22));
        jbuttonArr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonArr1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_ajuda3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radio_meuadm)
                    .addComponent(radio_ouadm)
                    .addComponent(radio_ninguempegou))
                .addGap(24, 24, 24))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(radio_meuadm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_ouadm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_ninguempegou)
                .addGap(17, 17, 17)
                .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_ajuda3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        textf_result.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        textf_result.setForeground(new java.awt.Color(255, 253, 243));
        textf_result.setText("x Resultados Encontrados");
        textf_result.setToolTipText("Resultados Encontrados na Tabela de Contato Pet");
        textf_result.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel4.setBackground(new java.awt.Color(255, 253, 243));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Atualizar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(429, 60));

        radio_fechar.setBackground(new java.awt.Color(255, 253, 243));
        bgp_final.add(radio_fechar);
        radio_fechar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_fechar.setText("Fechar Ticket");
        radio_fechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_fechar.setEnabled(false);
        radio_fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_fecharActionPerformed(evt);
            }
        });

        radio_aberto.setBackground(new java.awt.Color(255, 253, 243));
        bgp_final.add(radio_aberto);
        radio_aberto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_aberto.setText("Ticket em Aberto");
        radio_aberto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_aberto.setEnabled(false);
        radio_aberto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_abertoActionPerformed(evt);
            }
        });

        label_ajuda1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajuda1.setToolTipText("<html>\n<p>\nFinalizar um ticket.\n\n</p>\n</html>");
        label_ajuda1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label_ajuda1.setMinimumSize(new java.awt.Dimension(15, 22));
        label_ajuda1.setPreferredSize(new java.awt.Dimension(15, 22));

        radio_resol.setBackground(new java.awt.Color(255, 253, 243));
        bgp_final.add(radio_resol);
        radio_resol.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_resol.setText("Resolvido");
        radio_resol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_resol.setEnabled(false);
        radio_resol.setFocusable(false);
        radio_resol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_resolActionPerformed(evt);
            }
        });

        jButton2.setText("Enviar");
        jButton2.setToolTipText("Enviar Novas Informações");
        jButton2.setEnabled(false);
        jButton2.setPreferredSize(new java.awt.Dimension(61, 22));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane2.setEnabled(false);

        texarea_mt.setColumns(20);
        texarea_mt.setLineWrap(true);
        texarea_mt.setRows(5);
        texarea_mt.setWrapStyleWord(true);
        jScrollPane2.setViewportView(texarea_mt);

        jLabel1.setText("Situação final da Solução/Problema");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(radio_resol)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radio_aberto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radio_fechar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_ajuda1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_ajuda1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radio_fechar)
                        .addComponent(radio_aberto)
                        .addComponent(radio_resol)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1072, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(textf_result)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textf_result)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
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
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void lmp() {
        audios("cl");
        textf_copiado.setText("");
        limpar();
    }

    public void enviar_attlig() {
        atualizarlig(selcod);

    }

    public void enviar_att() {

        atualizar(selcod);
    }

    public void att_banco() {
        limpar();
        tsl();
        buscarTodostic();
        alert al = new alert(admin, audio);
        al.setVisible(true);
        String msg = "Banco Atualizado";
        String tit = "Atualização";
        al.alertinput(tit, "ok", "", msg, "", "sucesso");
    }
    private void cb_ordemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ordemActionPerformed

    }//GEN-LAST:event_cb_ordemActionPerformed

    private void btn_lmpfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lmpfilActionPerformed
        audios("cl");
        limfiltro();
        tsl();
    }//GEN-LAST:event_btn_lmpfilActionPerformed

    private void btn_filpetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filpetActionPerformed
        buscarTodostic();
        tsl();
    }//GEN-LAST:event_btn_filpetActionPerformed

    private void radio_meuadmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_meuadmActionPerformed

    }//GEN-LAST:event_radio_meuadmActionPerformed

    private void radio_ouadmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_ouadmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_ouadmActionPerformed

    private void jbuttonArr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonArr1ActionPerformed
        enviar_attlig();
        tsl();
    }//GEN-LAST:event_jbuttonArr1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        enviar_att();
        tsl();


    }//GEN-LAST:event_jButton2ActionPerformed

    private void radio_resolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_resolActionPerformed
        texarea_mt.setEnabled(true);
    }//GEN-LAST:event_radio_resolActionPerformed

    private void btn_tirarselActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tirarselActionPerformed
        audios("cl");
        tsl();
    }//GEN-LAST:event_btn_tirarselActionPerformed

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        audios("cl");
        lmp();
    }//GEN-LAST:event_btn_limparActionPerformed

    private void label_copyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_copyMouseClicked
        Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard();
        ClipboardOwner selecao = new StringSelection(textf_copiado.getText());
        board.setContents((Transferable) selecao, selecao);
        label_copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_copyokk.png")));
        audios("cl");
        int delay = 5000; // 10 segundos em milissegundos
        Timer timer = new Timer(delay, e -> {
            label_copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_copy.png")));
        });
        timer.setRepeats(false); // Definir para não repetir a tarefa

        timer.start();
    }//GEN-LAST:event_label_copyMouseClicked

    private void radio_ninguempegouActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_ninguempegouActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_ninguempegouActionPerformed

    private void radio_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_fecharActionPerformed
        texarea_mt.setEnabled(true);
    }//GEN-LAST:event_radio_fecharActionPerformed

    private void radio_abertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_abertoActionPerformed
        texarea_mt.setEnabled(false);
    }//GEN-LAST:event_radio_abertoActionPerformed

    private void LattMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LattMouseClicked
        att_banco();
    }//GEN-LAST:event_LattMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Latt;
    private javax.swing.ButtonGroup bgp_final;
    private javax.swing.ButtonGroup bgp_pegar;
    private SwingPerson.JbuttonArr btn_filpet;
    private SwingPerson.JbuttonArr btn_limpar;
    private SwingPerson.JbuttonArr btn_lmpfil;
    private SwingPerson.JbuttonArr btn_tirarsel;
    private javax.swing.JComboBox<String> cb_dst;
    private javax.swing.JComboBox<String> cb_ordem;
    private SwingPerson.JbuttonArr jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private SwingPerson.JbuttonArr jbuttonArr1;
    private javax.swing.JLabel label_ajuda1;
    private javax.swing.JLabel label_ajuda3;
    private javax.swing.JLabel label_copy;
    private javax.swing.JLabel label_selecionado;
    private javax.swing.JRadioButton radio_aberto;
    private javax.swing.JRadioButton radio_fechar;
    private javax.swing.JRadioButton radio_meuadm;
    private javax.swing.JRadioButton radio_ninguempegou;
    private javax.swing.JRadioButton radio_ouadm;
    private javax.swing.JRadioButton radio_resol;
    private javax.swing.JTable table;
    private javax.swing.JTextArea texarea_mt;
    private javax.swing.JTextField textf_copiado;
    private javax.swing.JLabel textf_result;
    // End of variables declaration//GEN-END:variables
}
