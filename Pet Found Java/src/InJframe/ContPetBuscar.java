package InJframe;

import Logar.login;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import alert.alert;
import factory.ConnectionFactory;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.table.DefaultTableModel;
import subGUI.ContPetAtualizar;
import subGUI.ContPetCadastrar;

//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import javax.sound.sampled.Mixer;
public class ContPetBuscar extends javax.swing.JInternalFrame {

    private Connection connection;

    int tipo2, resul, tipoveri;
    String seluser = null;
    String campoSelecionado, selcont;
    String admin, audio;
    int cont = 0;
    Boolean permchefe, sellig, dst;

    public ContPetBuscar(String ad, String au) {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        //
        bus(ad);
        admin = ad;
        audio = au;
        buscarTodosCP();
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                audios("cl");

                Object value = table.getValueAt(row, column);
                String campoSelecionado;
                if (value != null && !value.toString().isEmpty()) {
                    campoSelecionado = value.toString();
                    textf_copiado.setText(campoSelecionado);

                } else {
                    campoSelecionado = "";
                    textf_copiado.setText("");
                }

                seluser = table.getValueAt(row, 0).toString();

                selcont = table.getValueAt(row, 3).toString();
                Buscaatt(seluser);
                String selectedOption = (String) cb_dst.getSelectedItem();

                busdst(seluser);

                if (selcont != null) {

                    radio_null2.setEnabled(true);
                    radio_lg.setEnabled(true);
                    radio_nlg.setEnabled(true);

                    if (sellig == true && radio_lg.isSelected()) {
                        if (selcont.equals("adt")) {
                            radio_adt.setEnabled(true);
                            radio_null.setEnabled(true);
                            radio_nfez.setEnabled(true);
                        } else if (selcont.equals("apd")) {
                            radio_apd.setEnabled(true);
                            radio_null.setEnabled(true);
                            radio_nfez.setEnabled(true);
                        } else if (selcont.equals("adt_apd")) {
                            radio_apd.setEnabled(true);
                            radio_adt.setEnabled(true);
                            radio_null.setEnabled(true);
                            radio_nfez.setEnabled(true);
                        } else {
                            radio_null.setEnabled(true);
                        }
                    } else {
                        radio_apd.setEnabled(false);
                        radio_adt.setEnabled(false);
                        radio_null.setEnabled(false);
                        radio_nfez.setEnabled(false);
                    }
                } else {
                    radio_null2.setEnabled(false);
                    radio_lg.setEnabled(false);
                    radio_nlg.setEnabled(false);
                    radio_apd.setEnabled(false);
                    radio_adt.setEnabled(false);
                    radio_null.setEnabled(false);
                    radio_nfez.setEnabled(false);
                }
            }
        });
    }

    public void busdst(String user) {

        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM contatopet WHERE codcontp = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                Boolean tx5 = res.getBoolean(12);//bloq
                if (tx5) {
                    btn_dst.setIcon(new ImageIcon(ContPetBuscar.class.getResource("/img/icon_atv.png")));
                    dst = false;
                } else {
                    btn_dst.setIcon(new ImageIcon(ContPetBuscar.class.getResource("/img/icon_dest.png")));
                    dst = true;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
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

                permchefe = tx5;
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

    public void limfiltro() {
        cb_ordem.setSelectedItem("Mais Recentes");
        cb_con.setSelectedItem("Todos os Contatos");
        cb_dst.setSelectedItem("Todos os Contatos Pet Aqr/Narq");
        cb_tipc.setSelectedItem("Todos os Tipos de Contatos");
        cb_lig.setSelectedItem("Todos os Contatos que tiveram ligação.");
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

    public void desativar(String cod) {
        String selectedOption = (String) cb_dst.getSelectedItem();
        if (dst == false) {
            this.connection = new ConnectionFactory().getConnection();

            //Connection con;
            PreparedStatement ps;
            String status = "";
            try {

                ps = connection.prepareStatement("update contatopet set arquivarpet=? where codcontp=?");

                ps.setBoolean(1, false);
                ps.setString(2, cod);

                int i = ps.executeUpdate();
                if (i != 0) {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Contato Pet";
                    String msg2 = "atualizado com sucesso";
                    String tit = "Atualização do Contato Pet";
                    al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                    buscarTodosCP();
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Contato Pet";
                    String msg2 = "não atualizados";
                    String tit = "Atualização do Contato Pet";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.connection = new ConnectionFactory().getConnection();

            //Connection con;
            PreparedStatement ps;
            String status = "";
            try {

                ps = connection.prepareStatement("update contatopet set arquivarpet=? where codcontp=?");

                ps.setBoolean(1, true);
                ps.setString(2, cod);

                int i = ps.executeUpdate();
                if (i != 0) {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Contato Pet";
                    String msg2 = "atualizado com sucesso";
                    String tit = "Atualização do Contato Pet";
                    al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                    buscarTodosCP();
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Contato Pet";
                    String msg2 = "não atualizados";
                    String tit = "Atualização do Contato Pet";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void buscarcptdesp(String user) {
        resul = 0;
        this.connection = new ConnectionFactory().getConnection();

        String bloq, ord;

        String selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = " ORDER BY CONCAT(dtcp, ' ', hrcp) DESC;";
        } else {
            ord = " ORDER BY CONCAT(dtcp, ' ', hrcp) ASC;";
        }

        selectedOption = (String) cb_dst.getSelectedItem();
        switch (selectedOption) {
            case "Contatos Pets Arquivados":
                bloq = " and arquivarpet=true";
                break;
            case "Contatos Pets Não Arquivados":
                bloq = " and (arquivarpet IS NULL OR arquivarpet = False)";
                break;
            default:
                bloq = "";
                break;
        }

        String anunc;
        selectedOption = (String) cb_tipc.getSelectedItem();
        switch (selectedOption) {
            case "Todos os Tipos de Contatos":
                anunc = "";
                break;
            case "Contato de Adoção":
                anunc = " and tipocont = 'adt'";
                break;
            case "Contato de Padrinho":
                anunc = " and tipocont = 'apd'";
                break;
            default:
                anunc = " and tipocont='adt_apd'";
                break;
        }

        String con;
        selectedOption = (String) cb_con.getSelectedItem();

        switch (selectedOption) {
            case "Todos os Contatos":
                con = "";
                break;
            case "Todos os Contatos Concluidos":
                con = " and (adotou=true or apadrinhou=true)";
                break;
            case "Pet Adotados":
                con = " and adotou = true";
                break;
            case "Todos os Contatos esperando Conclusão":
                con = " and adotou IS NULL and apadrinhou IS NULL";
                break;
            case "Todos os Contatos não Concluidos":
                con = " and adotou = false and apadrinhou = false";
                break;
            default:
                con = " and apadrinhou=true";
                break;
        }

        String lig;
        selectedOption = (String) cb_lig.getSelectedItem();
        switch (selectedOption) {
            case "Todos os Contatos que tiveram ligação.":
                lig = "";
                break;
            case "Contatos que tiveram ligação.":
                lig = " and ligoupet = true";
                break;
            case "Contatos esperando uma ligação.":
                lig = " and ligoupet is null";
                break;
            default:
                lig = " and ligoupet=false";
                break;
        }
        String sql;
        switch (tipo2) {
            case 1:
                sql = "SELECT * FROM contatopet where pfcodp= '" + user + "'" + anunc + con + lig + bloq + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        alert al = new alert(admin, audio);
                        al.audios("ok");
                        while (result) {
                            resul++;
                            Object[] row = new Object[6];

                            row[0] = res.getString(4);
                            row[1] = res.getString(1);
                            row[2] = res.getString(2);
                            row[3] = res.getString(3);
                            row[4] = res.getString(7);
                            row[5] = res.getString(8);
                            data.add(row);
                            result = res.next();
                        }
                        cont++;
                    } else {
                        if (cont == 0) {
                            cont++;
                        } else {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Nenhum registro encontrado";
                            String msg2 = "no Banco de Dados";
                            String tit = "Informação";
                            al.alertinput(tit, "info", msg, msg2, "", "info");
                        }
                    }
                    // Define os nomes das colunas
                    Object[] columnNames = {"Cod Contato", "Cod PF", "Cod Pet", "Tipo de Contato", "Data", "Hora"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    ContPetBuscar.CustomTableModel model = new ContPetBuscar.CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    table.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                sql = "SELECT * FROM contatopet where petcodpet= '" + user + "'" + anunc + con + lig + bloq + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        alert al = new alert(admin, audio);
                        al.audios("ok");
                        while (result) {
                            resul++;
                            Object[] row = new Object[6];

                            row[0] = res.getString(4);
                            row[1] = res.getString(1);
                            row[2] = res.getString(2);
                            row[3] = res.getString(3);
                            row[4] = res.getString(7);
                            row[5] = res.getString(8);
                            data.add(row);
                            result = res.next();
                        }
                        cont++;
                    } else {
                        if (cont == 0) {
                            cont++;
                        } else {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Nenhum registro encontrado";
                            String msg2 = "no Banco de Dados";
                            String tit = "Informação";
                            al.alertinput(tit, "info", msg, msg2, "", "info");
                        }
                    }
                    // Define os nomes das colunas
                    Object[] columnNames = {"Cod Contato", "Cod PF", "Cod Pet", "Tipo de Contato", "Data", "Hora"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    ContPetBuscar.CustomTableModel model = new ContPetBuscar.CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    table.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;

        }

        if (resul == 1) {
            textf_result.setText(resul + " Resultado Encontrado");
        } else {
            textf_result.setText(resul + " Resultados Encontrados");
        }

        selectedOption = (String) cb_dst.getSelectedItem();
        if ("Contatos Pets Arquivados".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(ContPetBuscar.class.getResource("/img/icon_atv.png")));
        } else {
            btn_dst.setIcon(new ImageIcon(ContPetBuscar.class.getResource("/img/icon_dest.png")));
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

    public void limpar() {
        textf_buscar.setText("");
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        radio_apd.setEnabled(false);
        radio_adt.setEnabled(false);
        radio_null.setEnabled(false);
        radio_nfez.setEnabled(false);
        seluser = null;
        groupatt.clearSelection();
        textf_copiado.setText("");
        textf_result.setText("0 Resultados Encontrados");
        sellig = false;
        radio_null2.setEnabled(false);
        radio_lg.setEnabled(false);
        radio_nlg.setEnabled(false);
        Blig.clearSelection();
        dst = null;
    }

    public void verificar(String cod) {
        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps = null;
        String status = "";
        switch (tipoveri) {
            case 2: {
                try {
                    String selectedItem = Cb_pet.getSelectedItem().toString();
                    if (selectedItem.equals("Código de Contato Pet")) {
                        ps = connection.prepareStatement("select * from contatopet where codcontp=?;");
                    } else if (selectedItem.equals("Código de Pessoa")) {
                        ps = connection.prepareStatement("select * from contatopet where pfcodp=?;");
                    } else if (selectedItem.equals("Código de Pet")) {
                        ps = connection.prepareStatement("select * from contatopet where petcodpet=?;");
                    }

                    ps.setString(1, cod);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Informação não existe";
                        String tit = "Contato inexistente";
                        al.alertinput(tit, "erro", "", msg, "", "erro");

                    } else {
                        alert al = new alert(admin, audio);
                        al.audios("ok");
                        buscarCP(cod);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
            case 3: {

                try {
                    ps = connection.prepareStatement("delete from contatopet where codcontp=?;");
                    ps.setString(1, cod);
                    int i = ps.executeUpdate();
                    if (i != 0) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Contato do Pet: ";
                        String msg2 = cod;
                        String msg3 = "deletado do database";
                        String tit = "Deleção do Contato";
                        al.alertinput(tit, "ok", msg, msg2, msg3, "sucesso");
                        buscarTodosCP();
                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Erro ao deletar";
                        String tit = "Deleção do Contato";
                        al.alertinput(tit, "erro", "", msg, "", "erro");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 4: {
                this.connection = new ConnectionFactory().getConnection();

                status = "";
                try {
                    ps = connection.prepareStatement("select * from contatopet where codcontp=? ");
                    ps.setString(1, cod);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "ID do Contato Pet não existe";
                        String tit = "Contato inexistente";
                        al.alertinput(tit, "erro", "", msg, "", "erro");

                    } else {

                        desativar(cod);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            default:
                break;
        }
    }

    public void buscarCP(String user) {
        resul = 0;
        String bloq, ord;

        String selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = " ORDER BY CONCAT(dtcp, ' ', hrcp) DESC;";
        } else {
            ord = " ORDER BY CONCAT(dtcp, ' ', hrcp) ASC;";
        }

        selectedOption = (String) cb_dst.getSelectedItem();
        switch (selectedOption) {
            case "Contatos Pets Arquivados":
                bloq = " and arquivarpet=true";
                break;
            case "Contatos Pets Não Arquivados":
                bloq = " and (arquivarpet IS NULL OR arquivarpet = False)";
                break;
            default:
                bloq = "";
                break;
        }

        String anunc;
        selectedOption = (String) cb_tipc.getSelectedItem();
        switch (selectedOption) {
            case "Todos os Tipos de Contatos":
                anunc = "";
                break;
            case "Contato de Adoção":
                anunc = " and tipocont = 'adt'";
                break;
            case "Contato de Padrinho":
                anunc = " and tipocont = 'apd'";
                break;
            default:
                anunc = " and tipocont='adt_apd'";
                break;
        }

        String con;
        selectedOption = (String) cb_con.getSelectedItem();

        switch (selectedOption) {
            case "Todos os Contatos":
                con = "";
                break;
            case "Todos os Contatos Concluidos":
                con = " and (adotou=true or apadrinhou=true)";
                break;
            case "Todos os Contatos esperando Conclusão":
                con = " and adotou IS NULL and apadrinhou IS NULL";
                break;
            case "Pet Adotados":
                con = " and adotou = true";
                break;
            case "Todos os Contatos não Concluidos":
                con = " and adotou = false and apadrinhou = false";
                break;
            default:
                con = " and apadrinhou=true";
                break;
        }

        String lig;
        selectedOption = (String) cb_lig.getSelectedItem();
        switch (selectedOption) {
            case "Todos os Contatos que tiveram ligação.":
                lig = "";
                break;
            case "Contatos que tiveram ligação.":
                lig = " and ligoupet = true";
                break;
            case "Contatos esperando uma ligação.":
                lig = " and ligoupet is null";
                break;
            default:
                lig = " and ligoupet=false";
                break;
        }

        switch (tipo2) {
            case 2: {
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "select * from contatopet where codcontp=?" + anunc + con + lig + bloq + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user); // Define o valor do parâmetro
                    ResultSet res = stm.executeQuery();

                    // Cria uma matriz bidimensional para armazenar os dados do SELECT
                    Object[][] tabelacp = new Object[1][6]; // Número de colunas é 3

                    while (res.next()) {
                        resul++;
                        tabelacp[0][0] = res.getString(4);
                        tabelacp[0][1] = res.getString(1);
                        tabelacp[0][2] = res.getString(2);
                        tabelacp[0][3] = res.getString(3);
                        tabelacp[0][4] = res.getString(7);
                        tabelacp[0][5] = res.getString(8);
                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Cod Contato", "Cod PF", "Cod Pet", "Tipo de Contato", "Data", "Hora"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    ContPetBuscar.CustomTableModel model = new ContPetBuscar.CustomTableModel(tabelacp, columnNames);

                    // Configura o modelo da tabela
                    table.setModel(model);
                    radio_apd.setEnabled(true);
                    radio_adt.setEnabled(true);
                    radio_null.setEnabled(true);
                    radio_nfez.setEnabled(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                buscarcptdesp(user);
                break;
            }
            case 3: {

                buscarcptdesp(user);
                break;
            }
            default:
                break;
        }

        Buscaatt(user);

        if (resul == 1) {
            textf_result.setText(resul + " Resultado Encontrado");
        } else {
            textf_result.setText(resul + " Resultados Encontrados");
        }

        selectedOption = (String) cb_dst.getSelectedItem();
        if ("Contatos Pets Arquivados".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(ContPetBuscar.class.getResource("/img/icon_atv.png")));
        } else {
            btn_dst.setIcon(new ImageIcon(ContPetBuscar.class.getResource("/img/icon_dest.png")));
        }

    }

    public void BPersCP(String user) {
        resul = 0;

        String bloq, ord;

        String selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = " ORDER BY CONCAT(dtcp, ' ', hrcp) DESC;";
        } else {
            ord = " ORDER BY CONCAT(dtcp, ' ', hrcp) ASC;";
        }

        selectedOption = (String) cb_dst.getSelectedItem();
        switch (selectedOption) {
            case "Contatos Pets Arquivados":
                bloq = " and arquivarpet=true";
                break;
            case "Contatos Pets Não Arquivados":
                bloq = " and (arquivarpet IS NULL OR arquivarpet = False)";
                break;
            default:
                bloq = "";
                break;
        }

        String anunc;
        selectedOption = (String) cb_tipc.getSelectedItem();
        switch (selectedOption) {
            case "Todos os Tipos de Contatos":
                anunc = "";
                break;
            case "Contato de Adoção":
                anunc = " and tipocont = 'adt'";
                break;
            case "Contato de Padrinho":
                anunc = " and tipocont = 'apd'";
                break;
            default:
                anunc = " and tipocont='adt_apd'";
                break;
        }

        String con;
        selectedOption = (String) cb_con.getSelectedItem();

        switch (selectedOption) {
            case "Todos os Contatos":
                con = "";
                break;
            case "Todos os Contatos Concluidos":
                con = " and (adotou=true or apadrinhou=true)";
                break;
            case "Todos os Contatos esperando Conclusão":
                con = " and adotou IS NULL and apadrinhou IS NULL";
                break;
            case "Pet Adotados":
                con = " and adotou = true";
                break;
            case "Todos os Contatos não Concluidos":
                con = " adotou = false and apadrinhou = false";
                break;
            default:
                con = " and apadrinhou=true";
                break;
        }

        String lig;
        selectedOption = (String) cb_lig.getSelectedItem();
        switch (selectedOption) {
            case "Todos os Contatos que tiveram ligação.":
                lig = "";
                break;
            case "Contatos que tiveram ligação.":
                lig = " and ligoupet = true";
                break;
            case "Contatos esperando uma ligação.":
                lig = " and ligoupet is null";
                break;
            default:
                lig = " and ligoupet=false";
                break;
        }

        switch (tipo2) {
            case 1: {

                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM contatopet where codcontp like ?" + anunc + con + lig + bloq + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {

                        while (result) {
                            resul++;
                            Object[] row = new Object[6];

                            row[0] = res.getString(4);
                            row[1] = res.getString(1);
                            row[2] = res.getString(2);
                            row[3] = res.getString(3);
                            row[4] = res.getString(7);
                            row[5] = res.getString(8);
                            data.add(row);
                            result = res.next();
                        }
                    }
                    // Define os nomes das colunas
                    Object[] columnNames = {"Cod Contato", "Cod PF", "Cod Pet", "Tipo de Contato", "Data", "Hora"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    ContPetBuscar.CustomTableModel model = new ContPetBuscar.CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    table.setModel(model);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            }
            case 2: {

                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM contatopet where pfcodp like ?" + anunc + con + lig + bloq + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {

                        while (result) {
                            resul++;
                            Object[] row = new Object[6];

                            row[0] = res.getString(4);
                            row[1] = res.getString(1);
                            row[2] = res.getString(2);
                            row[3] = res.getString(3);
                            row[4] = res.getString(7);
                            row[5] = res.getString(8);
                            data.add(row);
                            result = res.next();
                        }
                    }
                    // Define os nomes das colunas
                    Object[] columnNames = {"Cod Contato", "Cod PF", "Cod Pet", "Tipo de Contato", "Data", "Hora"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    ContPetBuscar.CustomTableModel model = new ContPetBuscar.CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    table.setModel(model);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            }
            case 3: {

                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM contatopet where petcodpet like ?" + anunc + con + lig + bloq + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {

                        while (result) {
                            resul++;
                            Object[] row = new Object[6];

                            row[0] = res.getString(4);
                            row[1] = res.getString(1);
                            row[2] = res.getString(2);
                            row[3] = res.getString(3);
                            row[4] = res.getString(7);
                            row[5] = res.getString(8);
                            data.add(row);
                            result = res.next();
                        }
                    }
                    // Define os nomes das colunas
                    Object[] columnNames = {"Cod Contato", "Cod PF", "Cod Pet", "Tipo de Contato", "Data", "Hora"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    ContPetBuscar.CustomTableModel model = new ContPetBuscar.CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    table.setModel(model);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            }
            default:
                break;
        }
        tipo2 = 0;

        if (resul == 1) {
            textf_result.setText(resul + " Resultado Encontrado");
        } else {
            textf_result.setText(resul + " Resultados Encontrados");
        }

        selectedOption = (String) cb_dst.getSelectedItem();
        if ("Contatos Pets Arquivados".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(ContPetBuscar.class.getResource("/img/icon_atv.png")));
        } else {
            btn_dst.setIcon(new ImageIcon(ContPetBuscar.class.getResource("/img/icon_dest.png")));
        }
    }

    public void buscarTodosCP() {
        resul = 0;
        this.connection = new ConnectionFactory().getConnection();

        String bloq, ord;

        String selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = " ORDER BY CONCAT(dtcp, ' ', hrcp) DESC;";
        } else {
            ord = " ORDER BY CONCAT(dtcp, ' ', hrcp) ASC;";
        }

        selectedOption = (String) cb_dst.getSelectedItem();
        if (null == selectedOption) {
            bloq = "";
        } else {
            switch (selectedOption) {
                case "Contatos Pets Arquivados":
                    bloq = " WHERE arquivarpet=true";
                    break;
                case "Contatos Pets Não Arquivados":
                    bloq = " WHERE (arquivarpet IS NULL OR arquivarpet = False)";
                    break;
                default:
                    bloq = " WHERE";
                    break;
            }
        }

        String anunc;
        selectedOption = (String) cb_tipc.getSelectedItem();
        if (bloq == " WHERE") {
            switch (selectedOption) {
                case "Todos os Tipos de Contatos":
                    anunc = "";
                    break;
                case "Contato de Adoção":
                    anunc = " tipocont = 'adt'";
                    break;
                case "Contato de Padrinho":
                    anunc = " tipocont = 'apd'";
                    break;
                default:
                    anunc = " tipocont='adt_apd'";
                    break;
            }
        } else {
            switch (selectedOption) {
                case "Todos os Tipos de Contatos":
                    anunc = "";
                    break;
                case "Contato de Adoção":
                    anunc = " and tipocont = 'adt'";
                    break;
                case "Contato de Padrinho":
                    anunc = " and tipocont = 'apd'";
                    break;
                default:
                    anunc = " and tipocont='adt_apd'";
                    break;
            }
        }

        String con;
        selectedOption = (String) cb_con.getSelectedItem();
        if (bloq == " WHERE" && anunc == "") {

            switch (selectedOption) {
                case "Todos os Contatos":
                    con = "";
                    break;
                case "Todos os Contatos Concluidos":
                    con = " (adotou=true or apadrinhou=true)";
                    break;
                case "Todos os Contatos esperando Conclusão":
                    con = " adotou IS NULL and apadrinhou IS NULL";
                    break;
                case "Pet Adotados":
                    con = " adotou = true";
                    break;
                case "Todos os Contatos não Concluidos":
                    con = " adotou = false and apadrinhou = false";
                    break;
                default:
                    con = " apadrinhou=true";
                    break;
            }
        } else {

            switch (selectedOption) {
                case "Todos os Contatos":
                    con = "";
                    break;
                case "Todos os Contatos Concluidos":
                    con = " and (adotou=true or apadrinhou=true)";
                    break;
                case "Todos os Contatos esperando Conclusão":
                    con = " and adotou IS NULL and apadrinhou IS NULL";
                    break;
                case "Pet Adotados":
                    con = " and adotou = true";
                    break;
                case "Todos os Contatos não Concluidos":
                    con = " and adotou = false and apadrinhou = false";
                    break;
                default:
                    con = " and apadrinhou=true";
                    break;
            }
        }

        String lig;
        selectedOption = (String) cb_lig.getSelectedItem();
        if (bloq == " WHERE" && (anunc == "" & con == "")) {
            switch (selectedOption) {
                case "Todos os Contatos que tiveram ligação.":
                    lig = "";
                    break;
                case "Contatos que tiveram ligação.":
                    lig = " ligoupet = true";
                    break;
                case "Contatos esperando uma ligação.":
                    lig = " ligoupet is null";
                    break;
                default:
                    lig = " ligoupet=false";
                    break;
            }
        } else {
            switch (selectedOption) {
                case "Todos os Contatos que tiveram ligação.":
                    lig = "";
                    break;
                case "Contatos que tiveram ligação.":
                    lig = " and ligoupet = true";
                    break;
                case "Contatos esperando uma ligação.":
                    lig = " and ligoupet is null";
                    break;
                default:
                    lig = " and ligoupet=false";
                    break;
            }
        }
        if (bloq == " WHERE" && (anunc == "" && con == "" && lig == "")) {
            bloq = "";
        }

        String sql = "SELECT * FROM contatopet" + bloq + anunc + con + lig + ord;
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

            boolean result = res.next();
            if (result) {
                alert al = new alert(admin, audio);
                al.audios("ok");
                while (result) {
                    resul++;
                    Object[] row = new Object[6];

                    row[0] = res.getString(4);
                    row[1] = res.getString(1);
                    row[2] = res.getString(2);
                    row[3] = res.getString(3);
                    row[4] = res.getString(7);
                    row[5] = res.getString(8);
                    data.add(row);
                    result = res.next();
                }
                cont++;
            } else {
                if (cont == 0) {
                    cont++;
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Nenhum registro encontrado";
                    String msg2 = "no Banco de Dados";
                    String tit = "Informação";
                    al.alertinput(tit, "info", msg, msg2, "", "info");
                }
            }
            // Define os nomes das colunas
            Object[] columnNames = {"Cod Contato", "Cod PF", "Cod Pet", "Tipo de Contato", "Data", "Hora"};

            // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
            ContPetBuscar.CustomTableModel model = new ContPetBuscar.CustomTableModel(data.toArray(new Object[0][0]), columnNames);

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

        selectedOption = (String) cb_dst.getSelectedItem();
        if ("Contatos Pets Arquivados".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(ContPetBuscar.class.getResource("/img/icon_atv.png")));
        } else {
            btn_dst.setIcon(new ImageIcon(ContPetBuscar.class.getResource("/img/icon_dest.png")));
        }

    }

    public void Buscaatt(String user) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "";

        sql = "select * from contatopet where codcontp= '" + user + "'";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            while (res.next()) {
                boolean tx1 = res.getBoolean(5);
                boolean tx2 = res.getBoolean(6);
                /*if (tx1) {
                    radio_apd.setSelected(true);
                } else if (!res.wasNull()) {
                    radio_nfez.setSelected(true);
                } else if (tx1 == false && tx2 == false) {
                    radio_null.setSelected(true);
                }*/
                if (tx2) {
                    radio_adt.setSelected(true);
                } else if (tx1) {
                    radio_apd.setSelected(true);
                } else if (res.wasNull()) {
                    radio_null.setSelected(true);
                } else {
                    radio_nfez.setSelected(true);
                }
                boolean tx3 = res.getBoolean(9);
                if (tx3) {
                    radio_lg.setSelected(true);
                    sellig = true;
                } else if (!res.wasNull()) {
                    radio_nlg.setSelected(true);
                    sellig = false;
                } else {
                    radio_null2.setSelected(true);
                    sellig = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            String sql = "SELECT * FROM contatopet Where codcontp='" + Cod + "'";
            try {
                PreparedStatement stm = connection.prepareStatement(sql);
                ResultSet res = stm.executeQuery();

                boolean result = res.next();
                if (result) {
                    String codpet = res.getString(2);
                    sql = "SELECT * FROM contatopet WHERE adotou=true and petcodpet='" + codpet + "'";

                    try {
                        stm = connection.prepareStatement(sql);
                        res = stm.executeQuery();

                        result = res.next();
                        if (result) {
                            String codadt = res.getString(4);

                            if (codadt == null ? Cod == null : codadt.equals(Cod)) {
                                sql = "SELECT * FROM contatopet WHERE codcontp<>'" + codadt + "' and petcodpet='" + codpet + "' and adotou=true";
                            } else {
                                sql = "SELECT * FROM contatopet Where petcodpet='" + codpet + "' and adotou=true";
                            }

                        } else {
                            sql = "SELECT * FROM contatopet Where petcodpet='" + codpet + "' and adotou=true";
                        }

                        try {
                            stm = connection.prepareStatement(sql);
                            res = stm.executeQuery();

                            result = res.next();
                            if (result) {
                                alert al = new alert(admin, audio);
                                al.setVisible(true);
                                String msg = "Pet não pode ser atualizado";
                                String msg2 = "pois já possui um dono";
                                String tit = "Erro na Atualização";
                                al.alertinput(tit, "erro", msg, msg2, "", "erro");
                            } else {
                                if (selcont == null || "".equals(selcont)) {
                                    alert al = new alert(admin, audio);
                                    al.setVisible(true);
                                    String msg = "Selecione na Tabela";
                                    String msg2 = "quem deseja Atualizar";
                                    String tit = "Nenhum Contato selecionado";
                                    al.alertinput(tit, "info", msg, msg2, "", "info");

                                } else if (radio_apd.isSelected()) {

                                    try {
                                        ps = connection.prepareStatement("update contatopet set apadrinhou=?, adotou=?,dtfinalcp=?,hrfinalcp=?,arquivarpet=? where codcontp=?");
                                        ps.setBoolean(1, true);
                                        ps.setBoolean(2, false);
                                        ps.setString(3, DtAtual());
                                        ps.setString(4, HrAtual());
                                        ps.setNull(5, java.sql.Types.BOOLEAN);
                                        ps.setString(6, Cod);
                                        int i = ps.executeUpdate();
                                        if (i != 0) {
                                            ps = connection.prepareStatement("update pet set bloqueiopet=? where petcod=?");
                                            ps.setNull(1, java.sql.Types.BOOLEAN);
                                            ps.setString(2, codpet);
                                            i = ps.executeUpdate();
                                            if (i != 0) {
                                                ps = connection.prepareStatement("SELECT * FROM pet WHERE petcod = ?");
                                                ps.setString(1, codpet);

                                                ResultSet resultSet = ps.executeQuery();

                                                while (resultSet.next()) {
                                                    // Para cada linha encontrada, faça o UPDATE
                                                    String petcod = resultSet.getString("petcod");

                                                    ps = connection.prepareStatement("UPDATE contatopet SET arquivarpet=? WHERE petcodpet = ?");
                                                    ps.setNull(1, java.sql.Types.BOOLEAN);
                                                    ps.setString(2, petcod);
                                                    i = ps.executeUpdate();

                                                    if (i != 0) {
                                                        // Se o UPDATE for bem-sucedido, execute o código de sucesso aqui
                                                        alert al = new alert(admin, audio);
                                                        al.setVisible(true);
                                                        String msg = "Detalhes do Contato do";
                                                        String msg2 = "Pet atualizado";
                                                        String msg3 = "Com sucesso";
                                                        String tit = "Atualização de Contato";
                                                        al.alertinput(tit, "ok", msg, msg2, msg3, "sucesso");
                                                    } else {
                                                        // Se o UPDATE falhar, execute o código de erro aqui
                                                        alert al = new alert(admin, audio);
                                                        al.setVisible(true);
                                                        String msg = "Detalhes do Contato do";
                                                        String msg2 = "Pet não atualizados";
                                                        String tit = "Atualização de Contato";
                                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                                    }
                                                }
                                                buscarTodosCP();
                                            } else {
                                                alert al = new alert(admin, audio);
                                                al.setVisible(true);
                                                String msg = "Detalhes do Contato do";
                                                String msg2 = "Pet não atualizados";
                                                String tit = "Atualização de Contato";
                                                al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                            }
                                        } else {
                                            alert al = new alert(admin, audio);
                                            al.setVisible(true);
                                            String msg = "Detalhes do Contato do";
                                            String msg2 = "Pet não atualizados";
                                            String tit = "Atualização de Contato";
                                            al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } else if (radio_adt.isSelected()) {

                                    try {
                                        ps = connection.prepareStatement("update contatopet set adotou=?,apadrinhou=?,dtfinalcp=?,hrfinalcp=?,arquivarpet=? where codcontp=?");
                                        ps.setBoolean(1, true);
                                        ps.setBoolean(2, false);
                                        ps.setString(3, DtAtual());
                                        ps.setString(4, HrAtual());
                                        ps.setBoolean(5, true);
                                        ps.setString(6, Cod);
                                        int i = ps.executeUpdate();
                                        if (i != 0) {
                                            ps = connection.prepareStatement("update pet set bloqueiopet=? where petcod=?");
                                            ps.setBoolean(1, true);
                                            ps.setString(2, codpet);
                                            i = ps.executeUpdate();
                                            if (i != 0) {
                                                ps = connection.prepareStatement("SELECT * FROM pet WHERE petcod = ?");
                                                ps.setString(1, codpet);

                                                ResultSet resultSet = ps.executeQuery();

                                                while (resultSet.next()) {
                                                    // Para cada linha encontrada, faça o UPDATE
                                                    String petcod = resultSet.getString("petcod");

                                                    ps = connection.prepareStatement("UPDATE contatopet SET arquivarpet=? WHERE petcodpet = ?");
                                                    ps.setBoolean(1, true);
                                                    ps.setString(2, petcod);
                                                    i = ps.executeUpdate();

                                                    if (i != 0) {
                                                        // Se o UPDATE for bem-sucedido, execute o código de sucesso aqui
                                                        alert al = new alert(admin, audio);
                                                        al.setVisible(true);
                                                        String msg = "Detalhes do Contato do";
                                                        String msg2 = "Pet atualizado";
                                                        String msg3 = "Com sucesso";
                                                        String tit = "Atualização de Contato";
                                                        al.alertinput(tit, "ok", msg, msg2, msg3, "sucesso");
                                                    } else {
                                                        // Se o UPDATE falhar, execute o código de erro aqui
                                                        alert al = new alert(admin, audio);
                                                        al.setVisible(true);
                                                        String msg = "Detalhes do Contato do";
                                                        String msg2 = "Pet não atualizados";
                                                        String tit = "Atualização de Contato";
                                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                                    }
                                                }

                                                buscarTodosCP();

                                            } else {
                                                alert al = new alert(admin, audio);
                                                al.setVisible(true);
                                                String msg = "Detalhes do Contato do";
                                                String msg2 = "Pet não atualizados";
                                                String tit = "Atualização de Contato";
                                                al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                            }
                                        } else {
                                            alert al = new alert(admin, audio);
                                            al.setVisible(true);
                                            String msg = "Detalhes do Contato do";
                                            String msg2 = "Pet não atualizados";
                                            String tit = "Atualização de Contato";
                                            al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } else if (radio_null.isSelected()) {

                                    try {

                                        ps = connection.prepareStatement("update contatopet set apadrinhou=?,adotou=?,dtfinalcp=?,hrfinalcp=?,arquivarpet=? where codcontp=?");

                                        ps.setNull(1, java.sql.Types.BOOLEAN); // Definindo valor nulo
                                        ps.setNull(2, java.sql.Types.BOOLEAN);
                                        ps.setNull(3, java.sql.Types.BOOLEAN); // Definindo valor nulo
                                        ps.setNull(4, java.sql.Types.BOOLEAN);
                                        ps.setNull(5, java.sql.Types.BOOLEAN);
                                        ps.setString(6, Cod);

                                        int i = ps.executeUpdate();
                                        if (i != 0) {
                                            ps = connection.prepareStatement("update pet set bloqueiopet=? where petcod=?");
                                            ps.setNull(1, java.sql.Types.BOOLEAN);
                                            ps.setString(2, codpet);
                                            i = ps.executeUpdate();
                                            if (i != 0) {
                                                ps = connection.prepareStatement("SELECT * FROM pet WHERE petcod = ?");
                                                ps.setString(1, codpet);

                                                ResultSet resultSet = ps.executeQuery();

                                                while (resultSet.next()) {
                                                    // Para cada linha encontrada, faça o UPDATE
                                                    String petcod = resultSet.getString("petcod");

                                                    ps = connection.prepareStatement("UPDATE contatopet SET arquivarpet=? WHERE petcodpet = ?");
                                                    ps.setNull(1, java.sql.Types.BOOLEAN);
                                                    ps.setString(2, petcod);
                                                    i = ps.executeUpdate();

                                                    if (i != 0) {
                                                        // Se o UPDATE for bem-sucedido, execute o código de sucesso aqui
                                                        alert al = new alert(admin, audio);
                                                        al.setVisible(true);
                                                        String msg = "Detalhes do Contato do";
                                                        String msg2 = "Pet atualizado";
                                                        String msg3 = "Com sucesso";
                                                        String tit = "Atualização de Contato";
                                                        al.alertinput(tit, "ok", msg, msg2, msg3, "sucesso");
                                                    } else {
                                                        // Se o UPDATE falhar, execute o código de erro aqui
                                                        alert al = new alert(admin, audio);
                                                        al.setVisible(true);
                                                        String msg = "Detalhes do Contato do";
                                                        String msg2 = "Pet não atualizados";
                                                        String tit = "Atualização de Contato";
                                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                                    }
                                                }
                                                buscarTodosCP();
                                            } else {
                                                alert al = new alert(admin, audio);
                                                al.setVisible(true);
                                                String msg = "Detalhes do Contato do";
                                                String msg2 = "Pet não atualizados";
                                                String tit = "Atualização de Contato";
                                                al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                            }
                                        } else {
                                            alert al = new alert(admin, audio);
                                            al.setVisible(true);
                                            String msg = "Detalhes do Contato do";
                                            String msg2 = "Pet não atualizados";
                                            String tit = "Atualização de Contato";
                                            al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } else if (radio_nfez.isSelected()) {
                                    try {

                                        ps = connection.prepareStatement("update contatopet set apadrinhou=?,adotou=?,dtfinalcp=?,hrfinalcp=?,arquivarpet=? where codcontp=?");

                                        ps.setBoolean(1, false); // Definindo valor nulo
                                        ps.setBoolean(2, false);
                                        ps.setNull(3, java.sql.Types.BOOLEAN); // Definindo valor nulo
                                        ps.setNull(4, java.sql.Types.BOOLEAN);
                                        ps.setNull(5, java.sql.Types.BOOLEAN);
                                        ps.setString(6, Cod);

                                        int i = ps.executeUpdate();
                                        if (i != 0) {
                                            ps = connection.prepareStatement("update pet set bloqueiopet=? where petcod=?");
                                            ps.setNull(1, java.sql.Types.BOOLEAN);
                                            ps.setString(2, codpet);
                                            i = ps.executeUpdate();
                                            if (i != 0) {
                                                ps = connection.prepareStatement("SELECT * FROM pet WHERE petcod = ?");
                                                ps.setString(1, codpet);

                                                ResultSet resultSet = ps.executeQuery();

                                                while (resultSet.next()) {
                                                    // Para cada linha encontrada, faça o UPDATE
                                                    String petcod = resultSet.getString("petcod");

                                                    ps = connection.prepareStatement("UPDATE contatopet SET arquivarpet=? WHERE petcodpet = ?");
                                                    ps.setNull(1, java.sql.Types.BOOLEAN);
                                                    ps.setString(2, petcod);
                                                    i = ps.executeUpdate();

                                                    if (i != 0) {
                                                        // Se o UPDATE for bem-sucedido, execute o código de sucesso aqui
                                                        alert al = new alert(admin, audio);
                                                        al.setVisible(true);
                                                        String msg = "Detalhes do Contato do";
                                                        String msg2 = "Pet atualizado";
                                                        String msg3 = "Com sucesso";
                                                        String tit = "Atualização de Contato";
                                                        al.alertinput(tit, "ok", msg, msg2, msg3, "sucesso");
                                                    } else {
                                                        // Se o UPDATE falhar, execute o código de erro aqui
                                                        alert al = new alert(admin, audio);
                                                        al.setVisible(true);
                                                        String msg = "Detalhes do Contato do";
                                                        String msg2 = "Pet não atualizados";
                                                        String tit = "Atualização de Contato";
                                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                                    }
                                                }
                                                buscarTodosCP();
                                            } else {
                                                alert al = new alert(admin, audio);
                                                al.setVisible(true);
                                                String msg = "Detalhes do Contato do";
                                                String msg2 = "Pet não atualizados";
                                                String tit = "Atualização de Contato";
                                                al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                            }
                                        } else {
                                            alert al = new alert(admin, audio);
                                            al.setVisible(true);
                                            String msg = "Detalhes do Contato do";
                                            String msg2 = "Pet não atualizados";
                                            String tit = "Atualização de Contato";
                                            al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                        }
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
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

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

            if (selcont == null || "".equals(selcont)) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Selecione na Tabela";
                String msg2 = "quem deseja Atualizar";
                String tit = "Nenhum Contato selecionado";
                al.alertinput(tit, "info", msg, msg2, "", "info");

            } else if (radio_lg.isSelected()) {

                try {
                    ps = connection.prepareStatement("update contatopet set ligoupet=?, confirmpet=?, confirmpe=?,apadrinhou=?,adotou=? where codcontp=?");
                    ps.setBoolean(1, true);
                    ps.setBoolean(2, true);
                    ps.setBoolean(3, true);
                    ps.setNull(4, java.sql.Types.BOOLEAN);
                    ps.setNull(5, java.sql.Types.BOOLEAN);
                    ps.setString(6, Cod);
                    int i = ps.executeUpdate();
                    if (i != 0) {

                        if (selcont.equals("adt")) {
                            radio_adt.setEnabled(true);
                            radio_null.setEnabled(true);
                            radio_nfez.setEnabled(true);
                        } else if (selcont.equals("apd")) {
                            radio_apd.setEnabled(true);
                            radio_null.setEnabled(true);
                            radio_nfez.setEnabled(true);
                        } else if (selcont.equals("adt_apd")) {
                            radio_apd.setEnabled(true);
                            radio_adt.setEnabled(true);
                            radio_null.setEnabled(true);
                            radio_nfez.setEnabled(true);
                        } else {
                            radio_null.setEnabled(true);
                        }

                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Contato do";
                        String msg2 = "Pet atualizado com sucesso";
                        String tit = "Atualização de Contato";
                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                        buscarTodosCP();
                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Contato do";
                        String msg2 = "Pet não atualizados";
                        String tit = "Atualização de Contato";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (radio_nlg.isSelected()) {

                try {
                    ps = connection.prepareStatement("update contatopet set ligoupet=?, confirmpet=?, confirmpe=?,apadrinhou=?,adotou=? where codcontp=?");
                    ps.setBoolean(1, false);
                    ps.setBoolean(2, false);
                    ps.setBoolean(3, false);
                    ps.setBoolean(4, false);
                    ps.setBoolean(5, false);
                    ps.setString(6, Cod);
                    int i = ps.executeUpdate();
                    if (i != 0) {
                        radio_apd.setEnabled(false);
                        radio_adt.setEnabled(false);
                        radio_null.setEnabled(false);
                        radio_nfez.setEnabled(false);
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Contato do";
                        String msg2 = "Pet atualizado com sucesso";
                        String tit = "Atualização de Contato";
                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                        buscarTodosCP();
                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Contato do";
                        String msg2 = "Pet não atualizados";
                        String tit = "Atualização de Contato";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (radio_null2.isSelected()) {

                try {

                    ps = connection.prepareStatement("update contatopet set ligoupet=?, confirmpet=?, confirmpe=?,apadrinhou=?,adotou=?  where codcontp=?");

                    ps.setNull(1, java.sql.Types.BOOLEAN); // Definindo valor nulo
                    ps.setNull(2, java.sql.Types.BOOLEAN);
                    ps.setNull(3, java.sql.Types.BOOLEAN);
                    ps.setNull(4, java.sql.Types.BOOLEAN);
                    ps.setNull(5, java.sql.Types.BOOLEAN);
                    ps.setString(6, Cod);

                    int i = ps.executeUpdate();
                    if (i != 0) {
                        radio_apd.setEnabled(false);
                        radio_adt.setEnabled(false);
                        radio_null.setEnabled(false);
                        radio_nfez.setEnabled(false);
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Contato do";
                        String msg2 = "Pet atualizado com sucesso";
                        String tit = "Atualização de Contato";
                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                        buscarTodosCP();
                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Contato do";
                        String msg2 = "Pet não atualizados";
                        String tit = "Atualização de Contato";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    }
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
            Buscaatt(seluser);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupatt = new javax.swing.ButtonGroup();
        BGaudio = new javax.swing.ButtonGroup();
        Blig = new javax.swing.ButtonGroup();
        PFundo = new javax.swing.JPanel();
        label_selecionado = new javax.swing.JLabel();
        textf_copiado = new javax.swing.JTextField();
        label_copy = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Latt = new javax.swing.JLabel();
        Lbuscar = new javax.swing.JLabel();
        Cb_pet = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        textf_buscar = new javax.swing.JTextField();
        btn_limpar1 = new SwingPerson.JbuttonArr();
        btn_tirarsel1 = new SwingPerson.JbuttonArr();
        check_buperso = new javax.swing.JCheckBox();
        cb_ordem = new javax.swing.JComboBox<>();
        btn_lmpfil = new SwingPerson.JbuttonArr();
        btn_filpet = new SwingPerson.JbuttonArr();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        radio_adt = new javax.swing.JRadioButton();
        radio_apd = new javax.swing.JRadioButton();
        label_ajuda1 = new javax.swing.JLabel();
        radio_null = new javax.swing.JRadioButton();
        radio_nfez = new javax.swing.JRadioButton();
        jButton2 = new SwingPerson.JbuttonArr();
        cb_dst = new javax.swing.JComboBox<>();
        cb_tipc = new javax.swing.JComboBox<>();
        cb_con = new javax.swing.JComboBox<>();
        cb_lig = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        radio_lg = new javax.swing.JRadioButton();
        radio_nlg = new javax.swing.JRadioButton();
        label_ajuda3 = new javax.swing.JLabel();
        radio_null2 = new javax.swing.JRadioButton();
        jbuttonArr1 = new SwingPerson.JbuttonArr();
        btn_deletar = new SwingPerson.JbuttonArr();
        btn_dst = new SwingPerson.JbuttonArr();
        btn_att = new javax.swing.JButton();
        btn_cad = new javax.swing.JButton();
        textf_result = new javax.swing.JLabel();

        setBorder(null);
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1284, 666));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PFundo.setBackground(new java.awt.Color(64, 33, 7));
        PFundo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PFundo.setPreferredSize(new java.awt.Dimension(1284, 666));

        label_selecionado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_selecionado.setForeground(new java.awt.Color(242, 242, 242));
        label_selecionado.setText("Valor Selecionado");

        textf_copiado.setEditable(false);
        textf_copiado.setBackground(new java.awt.Color(204, 204, 204));
        textf_copiado.setToolTipText("Campo Selecionado na Tabela");
        textf_copiado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        textf_copiado.setMinimumSize(new java.awt.Dimension(500, 20));
        textf_copiado.setName(""); // NOI18N
        textf_copiado.setPreferredSize(new java.awt.Dimension(500, 20));

        label_copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_copy.png"))); // NOI18N
        label_copy.setToolTipText("Copiar Informação no Campo de Seleção");
        label_copy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_copy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_copyMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 253, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(397, 200));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Buscar Contato Pet");

        Latt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_att.png"))); // NOI18N
        Latt.setToolTipText("Busque Todos e Atualize o Banco de Dados");
        Latt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Latt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LattMouseClicked(evt);
            }
        });

        Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        Lbuscar.setToolTipText("Buscar Contato Pet");
        Lbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Lbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LbuscarMouseClicked(evt);
            }
        });

        Cb_pet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código de Contato Pet", "Código de Pessoa", "Código de Pet" }));
        Cb_pet.setSelectedIndex(-1);
        Cb_pet.setToolTipText("Selecione Por qual Informação Quer Pesquisar");
        Cb_pet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Cb_pet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cb_petActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(363, 3));

        textf_buscar.setEditable(false);
        textf_buscar.setBackground(new java.awt.Color(255, 253, 243));
        textf_buscar.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_buscar.setToolTipText("Selecione a forma que deseja Pesquisar o Pet");
        textf_buscar.setBorder(null);
        textf_buscar.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textf_buscarMouseClicked(evt);
            }
        });
        textf_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textf_buscarActionPerformed(evt);
            }
        });
        textf_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_buscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_buscarKeyReleased(evt);
            }
        });

        btn_limpar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_apagar.png"))); // NOI18N
        btn_limpar1.setToolTipText("Limpar Infomações");
        btn_limpar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpar1ActionPerformed(evt);
            }
        });

        btn_tirarsel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_tselect.png"))); // NOI18N
        btn_tirarsel1.setToolTipText("Tirar Seleção da Tabela");
        btn_tirarsel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tirarsel1ActionPerformed(evt);
            }
        });

        check_buperso.setBackground(new java.awt.Color(255, 253, 243));
        check_buperso.setText("Busca Personalizada");
        check_buperso.setToolTipText("Pesquise conforme você escreve");
        check_buperso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(591, 591, 591))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(Cb_pet, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(Latt))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(textf_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(Lbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(check_buperso))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_limpar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_tirarsel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Latt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Cb_pet, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textf_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Lbuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_buperso)
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_tirarsel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_limpar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        cb_ordem.setBackground(new java.awt.Color(255, 253, 243));
        cb_ordem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mais Recentes", "Mais Antigos" }));
        cb_ordem.setToolTipText("Contatos Pets em Ordem de data Crescente/Decrescente");
        cb_ordem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btn_lmpfil.setText("Limpar Filtros");
        btn_lmpfil.setToolTipText("Limpar todos os filtros");
        btn_lmpfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lmpfilActionPerformed(evt);
            }
        });

        btn_filpet.setText("Filtrar Contatos Pets");
        btn_filpet.setToolTipText("Procurar Contatos Pets com os filtros");
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
                "Cod Contato", "Cod de PF", "Cod de Pet", "Tipo de Contato", "Data", "Hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table.setGridColor(new java.awt.Color(255, 253, 243));
        jScrollPane1.setViewportView(table);

        jPanel4.setBackground(new java.awt.Color(255, 253, 243));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Atualizar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(429, 60));

        radio_adt.setBackground(new java.awt.Color(255, 253, 243));
        groupatt.add(radio_adt);
        radio_adt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_adt.setText("Adotou");
        radio_adt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_adt.setEnabled(false);

        radio_apd.setBackground(new java.awt.Color(255, 253, 243));
        groupatt.add(radio_apd);
        radio_apd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_apd.setText("Apadrinhou");
        radio_apd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_apd.setEnabled(false);

        label_ajuda1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajuda1.setToolTipText("<html>\n<p><b>Com Buscar Todos</b><br>\nPara acrescentar a Pessoa Física como tutora<br>\nou madrinha. Selecione na tabela quem deseja<br>\nmodificar.\n\n</p>\n</html>");
        label_ajuda1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label_ajuda1.setMinimumSize(new java.awt.Dimension(15, 22));
        label_ajuda1.setPreferredSize(new java.awt.Dimension(15, 22));

        radio_null.setBackground(new java.awt.Color(255, 253, 243));
        groupatt.add(radio_null);
        radio_null.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_null.setText("Nulo");
        radio_null.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_null.setEnabled(false);
        radio_null.setFocusable(false);

        radio_nfez.setBackground(new java.awt.Color(255, 253, 243));
        groupatt.add(radio_nfez);
        radio_nfez.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_nfez.setText("Não Quis");
        radio_nfez.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_nfez.setEnabled(false);
        radio_nfez.setFocusable(false);

        jButton2.setText("Enviar");
        jButton2.setToolTipText("Enviar Novas Informações");
        jButton2.setPreferredSize(new java.awt.Dimension(61, 22));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(radio_adt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_apd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_nfez)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_null)
                .addGap(9, 9, 9)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_ajuda1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_ajuda1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radio_adt)
                        .addComponent(radio_apd)
                        .addComponent(radio_null)
                        .addComponent(radio_nfez)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        cb_dst.setBackground(new java.awt.Color(255, 253, 243));
        cb_dst.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos os Contatos Pet Aqr/Narq", "Contatos Pets Não Arquivados", "Contatos Pets Arquivados" }));
        cb_dst.setToolTipText("Contatos Pets Arquivados(Arq)/Contatos Pets Não Arquivados(Narq)");
        cb_dst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb_dst.setMinimumSize(new java.awt.Dimension(202, 22));
        cb_dst.setPreferredSize(new java.awt.Dimension(202, 22));

        cb_tipc.setBackground(new java.awt.Color(255, 253, 243));
        cb_tipc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos os Tipos de Contatos", "Contato de Adoção", "Contato de Padrinho", "Contato de Adoção e de Padrinho" }));
        cb_tipc.setToolTipText("Contatos para adoção, apadrinhamento ou os dois.");
        cb_tipc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb_tipc.setMinimumSize(new java.awt.Dimension(202, 22));
        cb_tipc.setPreferredSize(new java.awt.Dimension(202, 22));

        cb_con.setBackground(new java.awt.Color(255, 253, 243));
        cb_con.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos os Contatos", "Todos os Contatos Concluidos", "Todos os Contatos não Concluidos", "Todos os Contatos esperando Conclusão", "Pet Adotados", "Pet Apadrinhado", " " }));
        cb_con.setToolTipText("Contatos que finalizaram com uma adoção ou apadrinhamento.");
        cb_con.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb_con.setMinimumSize(new java.awt.Dimension(202, 22));
        cb_con.setPreferredSize(new java.awt.Dimension(202, 22));

        cb_lig.setBackground(new java.awt.Color(255, 253, 243));
        cb_lig.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos os Contatos que tiveram ligação.", "Contatos que tiveram ligação.", "Contatos que não tiveram ligação.", "Contatos esperando uma ligação." }));
        cb_lig.setToolTipText("Contatos com Ligação ou sem.");
        cb_lig.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb_lig.setMinimumSize(new java.awt.Dimension(202, 22));
        cb_lig.setPreferredSize(new java.awt.Dimension(202, 22));

        jPanel6.setBackground(new java.awt.Color(255, 253, 243));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Atualizar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel6.setPreferredSize(new java.awt.Dimension(322, 60));

        radio_lg.setBackground(new java.awt.Color(255, 253, 243));
        Blig.add(radio_lg);
        radio_lg.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_lg.setText("Ligou");
        radio_lg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_lg.setEnabled(false);
        radio_lg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_lgActionPerformed(evt);
            }
        });

        radio_nlg.setBackground(new java.awt.Color(255, 253, 243));
        Blig.add(radio_nlg);
        radio_nlg.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_nlg.setText("Não Ligou");
        radio_nlg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_nlg.setEnabled(false);
        radio_nlg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_nlgActionPerformed(evt);
            }
        });

        label_ajuda3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajuda3.setToolTipText("<html>\n<p><b>Com Buscar Todos</b><br>\nPara adicionar se um o anunciante recebeu<br>\numa ligação e se o contatante ligou.\n\n</p>\n</html>");
        label_ajuda3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label_ajuda3.setMinimumSize(new java.awt.Dimension(0, 0));
        label_ajuda3.setPreferredSize(new java.awt.Dimension(15, 22));

        radio_null2.setBackground(new java.awt.Color(255, 253, 243));
        Blig.add(radio_null2);
        radio_null2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_null2.setText("Nulo");
        radio_null2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_null2.setEnabled(false);
        radio_null2.setFocusable(false);

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
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(radio_lg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_nlg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_null2)
                .addGap(8, 8, 8)
                .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_ajuda3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_ajuda3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radio_lg)
                        .addComponent(radio_nlg)
                        .addComponent(radio_null2)
                        .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        btn_deletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/deletarm.png"))); // NOI18N
        btn_deletar.setToolTipText("Deletar o Contato Pet Selecionado");
        btn_deletar.setMaximumSize(new java.awt.Dimension(35, 35));
        btn_deletar.setMinimumSize(new java.awt.Dimension(35, 35));
        btn_deletar.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_deletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deletarActionPerformed(evt);
            }
        });

        btn_dst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_dest.png"))); // NOI18N
        btn_dst.setToolTipText("Arquivar o Contato Selecionado");
        btn_dst.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_dst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dstActionPerformed(evt);
            }
        });

        btn_att.setBackground(new java.awt.Color(64, 33, 7));
        btn_att.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_att.setForeground(new java.awt.Color(255, 255, 255));
        btn_att.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/atualizarbr.png"))); // NOI18N
        btn_att.setToolTipText("Atualizar Contato Pet Selecionado");
        btn_att.setBorder(null);
        btn_att.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_att.setMinimumSize(new java.awt.Dimension(35, 35));
        btn_att.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_att.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_attActionPerformed(evt);
            }
        });

        btn_cad.setBackground(new java.awt.Color(64, 33, 7));
        btn_cad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_cad.setForeground(new java.awt.Color(255, 255, 255));
        btn_cad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_cadbr.png"))); // NOI18N
        btn_cad.setToolTipText("Cadastrar Contato Pet");
        btn_cad.setBorder(null);
        btn_cad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cad.setMinimumSize(new java.awt.Dimension(35, 35));
        btn_cad.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_cad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadActionPerformed(evt);
            }
        });

        textf_result.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        textf_result.setForeground(new java.awt.Color(255, 253, 243));
        textf_result.setText("x Resultados Encontrados");
        textf_result.setToolTipText("Resultados Encontrados na Tabela de Contato Pet");
        textf_result.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout PFundoLayout = new javax.swing.GroupLayout(PFundo);
        PFundo.setLayout(PFundoLayout);
        PFundoLayout.setHorizontalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addComponent(textf_copiado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_copy))
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PFundoLayout.createSequentialGroup()
                                        .addComponent(cb_ordem, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cb_dst, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cb_tipc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label_selecionado)
                                    .addGroup(PFundoLayout.createSequentialGroup()
                                        .addComponent(cb_con, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cb_lig, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 164, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btn_lmpfil, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_filpet, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                                .addComponent(btn_cad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_deletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textf_result, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(12, 12, 12))
        );
        PFundoLayout.setVerticalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addComponent(label_selecionado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textf_copiado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_copy, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_ordem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_tipc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_con, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_lig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_lmpfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_filpet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addComponent(textf_result)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_cad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_deletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        getContentPane().add(PFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void bloqbus() {
        Cb_pet.setSelectedItem(null);
        textf_buscar.setEditable(false);
        textf_buscar.setToolTipText("Selecione a A forma que deseja Pesquisar o Pet.");
    }

    public void dst() {
        if (admin == null || admin.equals("")) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Você deve estar logado";
            String msg2 = "Para Efetuar isso";
            String tit = "Não Logado";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");
        } else {

            if (seluser == null) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Selecione na Tabela";
                String msg2 = "quem deseja Desativar";
                String tit = "Nenhum Contato de Pet selecionado";
                al.alertinput(tit, "info", msg, msg2, "", "info");

            } else {

                String selectedOption = (String) cb_dst.getSelectedItem();
                if (dst == false) {
                    audios("aviso");
                    int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Desarquivar o Contato Pet " + seluser + "?");

                    if (escolha == 0) {
                        tipo2 = 4;
                        tipoveri = 4;
                        verificar(seluser);
                    }
                } else {
                    audios("aviso");
                    int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Arquivar o Contato Pet " + seluser + "?");

                    if (escolha == 0) {
                        tipo2 = 4;
                        tipoveri = 4;
                        verificar(seluser);
                    }
                }
            }
        }

    }

    public void bus() {
        if (!check_buperso.isSelected()) {
            String selectedItem = Cb_pet.getSelectedItem().toString();
            switch (selectedItem) {
                case "Código de Contato Pet":
                    tipo2 = 2;
                    tipoveri = 2;
                    String cod = textf_buscar.getText();
                    verificar(cod);
                    break;
                case "Código de Pessoa":
                    tipo2 = 1;
                    tipoveri = 2;
                    String cp = textf_buscar.getText();
                    verificar(cp);
                    break;
                case "Código de Pet":
                    tipo2 = 3;
                    tipoveri = 2;
                    String cpet = textf_buscar.getText();
                    verificar(cpet);
                    break;
                default:
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "";
                    String msg2 = "Nenhuma Opção Selecionada";
                    String tit = "";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    break;
            }
        } else {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Desmarque a busca";
            String msg2 = "personalizada para";
            String msg3 = "Buscar especifíco.";
            String tit = "";
            al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
        }
    }

    public void inm() {
        String nick = textf_buscar.getText();
        alert al = new alert(admin, audio);
        al.setVisible(true);
        String msg = "Indisponível no momento";
        String tit = "Página em manutenção";
        al.alertinput(tit, "info", "", msg, "", "info");
    }

    public void tsl() {
        dst = null;
        sellig = null;
        table.clearSelection();
        textf_copiado.setText("");
        groupatt.clearSelection();
        radio_apd.setEnabled(false);
        radio_adt.setEnabled(false);
        radio_null.setEnabled(false);
        radio_nfez.setEnabled(false);
        seluser = null;
        radio_null2.setEnabled(false);
        radio_lg.setEnabled(false);
        radio_nlg.setEnabled(false);

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
                if (seluser == null) {
                    String cod = textf_buscar.getText();
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Selecione na Tabela";
                    String msg2 = "quem deseja deletar";
                    String tit = "Nenhum Contato do Pet selecionado";
                    al.alertinput(tit, "info", msg, msg2, "", "info");

                } else {
                    audios("aviso");
                    int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Deletar o Contato Pet " + seluser + "?\nUma vez deletado, essas informações sumirão do banco de dados");

                    if (escolha == 0) {
                        tipoveri = 3;
                        verificar(seluser);
                    }
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

    public void bper(int a) {
        String cod = textf_buscar.getText();

        if (check_buperso.isSelected()) {
            switch (a) {
                case 1:
                    tipo2 = 1;

                    if (!cod.isEmpty()) {
                        BPersCP(cod);
                    }
                    break;
                case 2:
                    tipo2 = 2;

                    if (!cod.isEmpty()) {
                        BPersCP(cod);
                    }
                    break;
                case 3:
                    tipo2 = 3;

                    if (!cod.isEmpty()) {
                        BPersCP(cod);
                    }

                    break;
                default:
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Mais de um ou nenhum campo";
                    String msg2 = "de texto com valores";
                    String tit = "Preencha somente um dos campos";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    break;
            }
        }
    }

    public void lmp() {
        audios("cl");
        textf_copiado.setText("");
        limpar();
    }

    public void att_banco() {
        limpar();
        buscarTodosCP();
        tsl();
        alert al = new alert(admin, audio);
        al.setVisible(true);
        String msg = "Banco Atualizado";
        String tit = "Atualização";
        al.alertinput(tit, "ok", "", msg, "", "sucesso");
    }

    public void enviar_att() {
        if (seluser == null) {
            String cod = textf_buscar.getText();
            atualizar(cod);
        } else {
            atualizar(seluser);
        }
    }

    public void enviar_attlig() {
        if (seluser == null) {
            String cod = textf_buscar.getText();
            atualizarlig(cod);
        } else {
            atualizarlig(seluser);
        }
    }

    public void cad() {

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
            audios("tc");
            ContPetCadastrar cpet = new ContPetCadastrar(admin, audio);
            cpet.setVisible(true);

        }
    }

    public void att() {
        if (seluser == null) {
            String cod = textf_buscar.getText();
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Selecione na Tabela";
            String msg2 = "quem deseja Atualizar";
            String tit = "Nenhum Contato Pet selecionado";
            al.alertinput(tit, "info", msg, msg2, "", "info");

        } else {
            if (admin == null || admin.equals("")) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Você deve estar logado";
                String msg2 = "Para Efetuar isso";
                String tit = "Não Logado";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            } else {
                if (permchefe == true || seluser.equals(admin)) {
                    audios("tc");
                    ContPetAtualizar cp = new ContPetAtualizar(admin, audio, seluser);
                    cp.setVisible(true);
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Você não tem ";
                    String msg3 = "permissão suficiente";
                    String tit = "Sem Permissão";
                    al.alertinput(tit, "erro", msg, msg3, "", "erro");
                }

            }

        }
    }

    private void LattMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LattMouseClicked
        att_banco();
    }//GEN-LAST:event_LattMouseClicked

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

    private void btn_cadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadActionPerformed

        cad();
        tsl();
    }//GEN-LAST:event_btn_cadActionPerformed

    private void btn_attActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_attActionPerformed

        att();
        tsl();
    }//GEN-LAST:event_btn_attActionPerformed

    private void LbuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LbuscarMouseClicked
        if (Cb_pet.getSelectedItem() != null) {
            bus();
            Lbuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            Lbuscar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }//GEN-LAST:event_LbuscarMouseClicked

    private void Cb_petActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cb_petActionPerformed
        if (Cb_pet.getSelectedItem() != null) {
            textf_buscar.setEditable(true);
            textf_buscar.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            textf_buscar.setToolTipText("Escreva por quem quer Procurar.");
            Lbuscar.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            textf_buscar.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            // if (Cb_pet.getSelectedItem() == "Código de Admin Que Aprovou") {
            //     check_anunrep.setSelected(false);
            // }
        } else {
            textf_buscar.setEditable(false);
            textf_buscar.setToolTipText("Selecione a A forma que deseja Pesquisar o Pet.");
            Lbuscar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            textf_buscar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            //textf_buscar.setCursor(lockedCursor);
        }

    }//GEN-LAST:event_Cb_petActionPerformed

    private void textf_buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_buscarMouseClicked
        if (textf_buscar.isEditable()) {
            limpar(); // Chama o método "limpar" se o campo estiver editável
        }
    }//GEN-LAST:event_textf_buscarMouseClicked

    private void textf_buscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_buscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (textf_buscar.isEditable()) {
                bus();
            }
        }
    }//GEN-LAST:event_textf_buscarKeyPressed

    private void textf_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_buscarKeyReleased

        if (textf_buscar.isEditable()) {
            String selectedItem = Cb_pet.getSelectedItem().toString();

            switch (selectedItem) {
                case "Código de Contato Pet":
                    tipo2 = 1;
                    bper(1);
                    break;
                case "Código de Pessoa":
                    tipo2 = 2;
                    bper(2);
                    break;
                case "Código de Pet":
                    tipo2 = 3;
                    bper(3);
                    break;
                default:
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "";
                    String msg2 = "Nenhuma Opção Selecionada";
                    String tit = "";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    break;
            }
        }
    }//GEN-LAST:event_textf_buscarKeyReleased

    private void btn_limpar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpar1ActionPerformed
        audios("cl");
        bloqbus();
        lmp();
    }//GEN-LAST:event_btn_limpar1ActionPerformed

    private void btn_tirarsel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tirarsel1ActionPerformed
        audios("cl");
        tsl();
    }//GEN-LAST:event_btn_tirarsel1ActionPerformed

    private void btn_dstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dstActionPerformed
        dst();
        tsl();
    }//GEN-LAST:event_btn_dstActionPerformed

    private void btn_deletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deletarActionPerformed
        del();
        tsl();
    }//GEN-LAST:event_btn_deletarActionPerformed

    private void btn_filpetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filpetActionPerformed
        dst = null;
        radio_null2.setEnabled(false);
        radio_lg.setEnabled(false);
        radio_nlg.setEnabled(false);
        radio_apd.setEnabled(false);
        radio_adt.setEnabled(false);
        radio_null.setEnabled(false);
        radio_nfez.setEnabled(false);
        Blig.clearSelection();
        groupatt.clearSelection();
        buscarTodosCP();
        tsl();
    }//GEN-LAST:event_btn_filpetActionPerformed

    private void btn_lmpfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lmpfilActionPerformed
        limfiltro();
        tsl();
    }//GEN-LAST:event_btn_lmpfilActionPerformed

    private void radio_lgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_lgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_lgActionPerformed

    private void radio_nlgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_nlgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_nlgActionPerformed

    private void textf_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textf_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textf_buscarActionPerformed

    private void jbuttonArr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonArr1ActionPerformed
        enviar_attlig();
        tsl();
    }//GEN-LAST:event_jbuttonArr1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        enviar_att();
        tsl();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGaudio;
    private javax.swing.ButtonGroup Blig;
    private javax.swing.JComboBox<String> Cb_pet;
    private javax.swing.JLabel Latt;
    private javax.swing.JLabel Lbuscar;
    private javax.swing.JPanel PFundo;
    private javax.swing.JButton btn_att;
    private javax.swing.JButton btn_cad;
    private SwingPerson.JbuttonArr btn_deletar;
    private SwingPerson.JbuttonArr btn_dst;
    private SwingPerson.JbuttonArr btn_filpet;
    private SwingPerson.JbuttonArr btn_limpar1;
    private SwingPerson.JbuttonArr btn_lmpfil;
    private SwingPerson.JbuttonArr btn_tirarsel1;
    private javax.swing.JComboBox<String> cb_con;
    private javax.swing.JComboBox<String> cb_dst;
    private javax.swing.JComboBox<String> cb_lig;
    private javax.swing.JComboBox<String> cb_ordem;
    private javax.swing.JComboBox<String> cb_tipc;
    private javax.swing.JCheckBox check_buperso;
    private javax.swing.ButtonGroup groupatt;
    private SwingPerson.JbuttonArr jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private SwingPerson.JbuttonArr jbuttonArr1;
    private javax.swing.JLabel label_ajuda1;
    private javax.swing.JLabel label_ajuda3;
    private javax.swing.JLabel label_copy;
    private javax.swing.JLabel label_selecionado;
    private javax.swing.JRadioButton radio_adt;
    private javax.swing.JRadioButton radio_apd;
    private javax.swing.JRadioButton radio_lg;
    private javax.swing.JRadioButton radio_nfez;
    private javax.swing.JRadioButton radio_nlg;
    private javax.swing.JRadioButton radio_null;
    private javax.swing.JRadioButton radio_null2;
    private javax.swing.JTable table;
    private javax.swing.JTextField textf_buscar;
    private javax.swing.JTextField textf_copiado;
    private javax.swing.JLabel textf_result;
    // End of variables declaration//GEN-END:variables
}
