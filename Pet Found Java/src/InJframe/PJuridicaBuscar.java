package InJframe;

import GUI.TelaPrin;
import Logar.login;
import alert.alert;
import factory.ConnectionFactory;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import subGUI.PJuridicaAtualizar;
import subGUI.PJuridicaCadastrar;

public class PJuridicaBuscar extends javax.swing.JInternalFrame {

    private Connection connection;

    String seluser, selcod;
    String adm, audio, codend;
    int tipo2, resul, tipoveri;
    int cont = 0;
    int cont2 = 0;
    Boolean dst;
    Boolean permchefe = false;
    private TelaPrin telaPrin;

    public PJuridicaBuscar(String admin, String au, TelaPrin telaPrin) {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        //
        adm = admin;
        audio = au;
        resul = 0;
        buscarTodospj();
        bus(adm);
        this.telaPrin = telaPrin;
        tablepj.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tablepj.getSelectedRow();
                int column = tablepj.getSelectedColumn();

                audios("cl");

                seluser = tablepj.getValueAt(row, 3).toString();
                selcod = tablepj.getValueAt(row, 7).toString();
                setImg(seluser);
                busdst(seluser);
                Object value = tablepj.getValueAt(row, column);
                String campoSelecionado;
                if (value != null && !value.toString().isEmpty()) {
                    campoSelecionado = value.toString();
                    textf_copiado.setText(campoSelecionado);
                    bsenha();
                    btn_pet.setEnabled(true);
                    btn_pet1.setEnabled(true);
                } else {
                    campoSelecionado = "";
                    textf_copiado.setText("");
                }

            }
        });
    }

    public void busdst(String user) {

        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM pessoa WHERE nickname = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                Boolean tx5 = res.getBoolean(12);//bloq
                if (tx5) {
                    btn_dst.setIcon(new ImageIcon(PFisicaBuscar.class.getResource("/img/icon_atv.png")));
                    dst = false;
                } else {
                    btn_dst.setIcon(new ImageIcon(PFisicaBuscar.class.getResource("/img/icon_dest.png")));
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
        if (adm != null || adm != "") {
            if (permchefe == true) {
                btn_vsenha.setEnabled(true);
                btn_vsenha.setToolTipText("Ver Senha de Pessoa Jurídica");
            } else {
                btn_vsenha.setEnabled(false);
                btn_vsenha.setToolTipText("Sem Permissão para Ver Senha de Pessoa Jurídica");
            }
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
        dst = null;
        tipo2 = 0;
        textf_copiado.setText("");
        seluser = "";
        DefaultTableModel model = (DefaultTableModel) tablepj.getModel();
        model.setRowCount(0);
        label_foto.setIcon(null);
        textf_result.setText("0 Resultados Encontrados");
        textf_buscar.setText("");
        textf_buscar.setText("");
        model.setRowCount(0);
        label_foto.setIcon(null);
        tipo2 = 0;
        textf_copiado.setText("");
        selcod = "";
        limfiltro();
        btn_pet.setEnabled(false);
        btn_pet1.setEnabled(false);
    }

    public void limfiltro() {
        cb_dst.setSelectedItem("Todas Pessoas Jurídicas At/Dst");
        cb_ordem.setSelectedItem("Mais Recentes");
        cb_tp.setSelectedItem("Todos Tipos de Pessoas Jurídicas");

    }

    public void bloqbus() {
        Cb_pf.setSelectedItem(null);
        textf_buscar.setEditable(false);
        textf_buscar.setToolTipText("Selecione a A forma que deseja Pesquisar o Pessoa Jurídica.");
    }

    public static String formatcont(String cont) {
        String dd = cont.substring(1, 3);
        String num = cont.substring(4);
        cont = dd + num.substring(0, 5) + num.substring(6);

        return cont;
    }

    public String verificar(String cod) {
        String status = "";
        PreparedStatement ps = null;
        switch (tipoveri) {
            case 1: {

                this.connection = new ConnectionFactory().getConnection();

                status = "";
                try {
                    switch (tipo2) {
                        case 1:
                            ps = connection.prepareStatement("select * from juridica where cod_p=?;");
                            break;
                        case 3:
                            ps = connection.prepareStatement("SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where emailp=?;");
                            break;
                        case 4:
                            ps = connection.prepareStatement("SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where contatop=?;");
                            break;
                        case 5:
                            ps = connection.prepareStatement("SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where cnpj=?;");
                            break;
                        case 6:
                            ps = connection.prepareStatement("SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where ramo_ativ=?;");
                            break;
                        default:
                            ps = connection.prepareStatement("SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where nickname=?;");
                            break;
                    }
                 
                    ps.setString(1, cod);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        resul = 0;
                        buscarpj(cod);
                    } else {
                        alert al = new alert(adm, audio);
                        al.setVisible(true);
                        String msg = "Informação não existe";
                        String tit = "Pessoa Jurídicas inexistente";
                        al.alertinput(tit, "erro", "", msg, "", "erro");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }

            case 6: {
                delet(cod);
                break;
            }
            case 4: {
                desativar(cod);
                break;
            }
            default:
                break;

        }
        return status;
    }

    public void desativar(String cod) {

        String selectedOption = (String) cb_dst.getSelectedItem();
        if (dst == false) {
            this.connection = new ConnectionFactory().getConnection();

            //Connection con;
            PreparedStatement ps;
            String status = "";
            try {

                ps = connection.prepareStatement("update pessoa set bloqueiop=? where pcod=?");

                ps.setBoolean(1, false);
                ps.setString(2, cod);

                int i = ps.executeUpdate();
                if (i != 0) {
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Detalhes da Pessoa Jurídica";
                    String msg2 = "atualizado com sucesso";
                    String tit = "Atualização de Pessoa";
                    al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                    buscarTodospj();
                } else {
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Detalhes da Pessoa Jurídica";
                    String msg2 = "não atualizados";
                    String tit = "Atualização de Pessoa";
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

                ps = connection.prepareStatement("update pessoa set bloqueiop=? where pcod=?");

                ps.setBoolean(1, true);
                ps.setString(2, cod);

                int i = ps.executeUpdate();
                if (i != 0) {
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Detalhes da Pessoa Jurídica";
                    String msg2 = "atualizado com sucesso";
                    String tit = "Atualização de Pessoa";
                    al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                    buscarTodospj();
                } else {
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Detalhes da Pessoa Jurídica";
                    String msg2 = "não atualizados";
                    String tit = "Atualização de Pessoa Jurídica";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setImg(String user) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where nickname=?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            if (res.next()) {

                byte[] imageBytes = res.getBytes(8);

                // Verifica se há imagem
                if (imageBytes != null && imageBytes.length > 0) {
                    ImageIcon imageIcon = new ImageIcon(imageBytes);
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(label_foto.getWidth(), label_foto.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    label_foto.setIcon(scaledIcon);
                } else {
                    label_foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/semimg.png")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void buscarpjdesp(String user) {
        String bloq, ord, sx;

        // Inicialize min com um valor padrão
        String selectedOption = (String) cb_dst.getSelectedItem();
        switch (selectedOption) {
            case "Pessoas Jurídicas Desativadas":
                bloq = " AND pessoa.bloqueiop=true";
                break;
            case "Pessoas Jurídicas Ativas":
                bloq = " AND (pessoa.bloqueiop IS NULL OR pessoa.bloqueiop = False)";
                break;
            default:
                bloq = "";
                break;
        }

        selectedOption = (String) cb_tp.getSelectedItem();
        sx = "";
        if (null != selectedOption) {
            switch (selectedOption) {
                case "Todos Tipos de Pessoas Jurídicas":
                    sx = "";
                    break;
                case ("ONG"):
                    sx = " and tipoj='" + selectedOption + "'";
                    break;
                case ("Empresa"):
                    sx = " and tipoj='" + selectedOption + "'";
                    break;
                default:
                    break;
            }
        }

        selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = " ORDER BY CONCAT(pessoa.dtcriacao, ' ', pessoa.hrcriacao) DESC";
        } else {
            ord = " ORDER BY CONCAT(pessoa.dtcriacao, ' ', pessoa.hrcriacao) ASC;";
        }

        switch (tipo2) {
            case 6: {
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where juridica.ramo_ativ= ?" + bloq + sx + ord;

                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[18];
                            row[0] = res.getString(1);
                            row[1] = res.getString(2);
                            row[2] = res.getString(3);
                            row[3] = res.getString(5);
                            row[4] = res.getString("cnpj");
                            row[5] = res.getString("ramo_ativ");
                            row[6] = res.getString("tipoj");
                            row[7] = res.getString(7);
                            row[8] = res.getString(9);
                            row[9] = res.getString(10);
                            row[10] = res.getString(4);
                            row[11] = res.getString("cep");
                            row[12] = res.getString("uf");
                            row[13] = res.getString("cidade");
                            row[14] = res.getString("bairro");
                            row[15] = res.getString("endereco");
                            row[16] = res.getString("numero");
                            row[17] = res.getString("cmpt");
                            data.add(row);
                            result = res.next();
                        }
                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    tablepj.setModel(model);
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
        if ("Pessoas Jurídicas Desativadas".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(PJuridicaBuscar.class.getResource("/img/icon_atv.png")));
        } else {
            btn_dst.setIcon(new ImageIcon(PJuridicaBuscar.class.getResource("/img/icon_dest.png")));
        }
    }

    public void buscarpj(String user) {
        resul = 0;

        String bloq, ord, sx;

        // Inicialize min com um valor padrão
        String selectedOption = (String) cb_dst.getSelectedItem();
        switch (selectedOption) {
            case "Pessoas Jurídicas Desativadas":
                bloq = " AND pessoa.bloqueiop=true";
                break;
            case "Pessoas Jurídicas Ativas":
                bloq = " AND (pessoa.bloqueiop IS NULL OR pessoa.bloqueiop = False)";
                break;
            default:
                bloq = "";
                break;
        }

        selectedOption = (String) cb_tp.getSelectedItem();
        sx = "";
        if (null != selectedOption) {
            switch (selectedOption) {
                case "Todos Tipos de Pessoas Jurídicas":
                    sx = "";
                    break;
                case ("ONG"):
                    sx = " and tipoj='" + selectedOption + "'";
                    break;
                case ("Empresa"):
                    sx = " and tipoj='" + selectedOption + "'";
                    break;
                default:
                    break;
            }
        }

        selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = " ORDER BY CONCAT(pessoa.dtcriacao, ' ', pessoa.hrcriacao) DESC";
        } else {
            ord = " ORDER BY CONCAT(pessoa.dtcriacao, ' ', pessoa.hrcriacao) ASC;";
        }

        switch (tipo2) {
            case 1: {
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where cod_p=?" + bloq + sx + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user); // Define o valor do parâmetro
                    ResultSet res = stm.executeQuery();

                    // Cria uma matriz bidimensional para armazenar os dados do SELECT
                    Object[][] tabelpj = new Object[1][18]; // Número de colunas é 19

                    while (res.next()) {
                        resul++;
                        tabelpj[0][0] = res.getString(1);
                        tabelpj[0][1] = res.getString(2);
                        tabelpj[0][2] = res.getString(3);
                        tabelpj[0][3] = res.getString(5);
                        tabelpj[0][4] = res.getString("cnpj");
                        tabelpj[0][5] = res.getString("ramo_ativ");
                        tabelpj[0][6] = res.getString("tipoj");
                        tabelpj[0][7] = res.getString(7);
                        tabelpj[0][8] = res.getString(9);
                        tabelpj[0][9] = res.getString(10);
                        tabelpj[0][10] = res.getString(4);
                        tabelpj[0][11] = res.getString("cep");
                        tabelpj[0][12] = res.getString("uf");
                        tabelpj[0][13] = res.getString("cidade");
                        tabelpj[0][14] = res.getString("bairro");
                        tabelpj[0][15] = res.getString("endereco");
                        tabelpj[0][16] = res.getString("numero");
                        tabelpj[0][17] = res.getString("cmpt");
                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    PJuridicaBuscar.CustomTableModel model = new PJuridicaBuscar.CustomTableModel(tabelpj, columnNames);

                    // Configura o modelo da tabela
                    tablepj.setModel(model);
                    sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where codp=?";

                    stm.setString(1, user);
                    res = stm.executeQuery();

                    if (res.next()) {
                        String Nick = res.getString(5);

                        setImg(Nick);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where emailp=?" + bloq + sx + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user); // Define o valor do parâmetro
                    ResultSet res = stm.executeQuery();

                    // Cria uma matriz bidimensional para armazenar os dados do SELECT
                    Object[][] tabelpj = new Object[1][18]; // Número de colunas é 19

                    while (res.next()) {
                        resul++;
                        tabelpj[0][0] = res.getString(1);
                        tabelpj[0][1] = res.getString(2);
                        tabelpj[0][2] = res.getString(3);
                        tabelpj[0][3] = res.getString(5);
                        tabelpj[0][4] = res.getString("cnpj");
                        tabelpj[0][5] = res.getString("ramo_ativ");
                        tabelpj[0][6] = res.getString("tipoj");
                        tabelpj[0][7] = res.getString(7);
                        tabelpj[0][8] = res.getString(9);
                        tabelpj[0][9] = res.getString(10);
                        tabelpj[0][10] = res.getString(4);
                        tabelpj[0][11] = res.getString("cep");
                        tabelpj[0][12] = res.getString("uf");
                        tabelpj[0][13] = res.getString("cidade");
                        tabelpj[0][14] = res.getString("bairro");
                        tabelpj[0][15] = res.getString("endereco");
                        tabelpj[0][16] = res.getString("numero");
                        tabelpj[0][17] = res.getString("cmpt");

                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    PJuridicaBuscar.CustomTableModel model = new PJuridicaBuscar.CustomTableModel(tabelpj, columnNames);

                    // Configura o modelo da tabela
                    tablepj.setModel(model);
                    sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where codp=?";

                    stm.setString(1, user);
                    res = stm.executeQuery();

                    if (res.next()) {
                        String Nick = res.getString(5);

                        setImg(Nick);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 4: {
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where contatop=?" + bloq + sx + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user); // Define o valor do parâmetro
                    ResultSet res = stm.executeQuery();

                    // Cria uma matriz bidimensional para armazenar os dados do SELECT
                    Object[][] tabelpj = new Object[1][18]; // Número de colunas é 19

                    while (res.next()) {
                        resul++;
                        tabelpj[0][0] = res.getString(1);
                        tabelpj[0][1] = res.getString(2);
                        tabelpj[0][2] = res.getString(3);
                        tabelpj[0][3] = res.getString(5);
                        tabelpj[0][4] = res.getString("cnpj");
                        tabelpj[0][5] = res.getString("ramo_ativ");
                        tabelpj[0][6] = res.getString("tipoj");
                        tabelpj[0][7] = res.getString(7);
                        tabelpj[0][8] = res.getString(9);
                        tabelpj[0][9] = res.getString(10);
                        tabelpj[0][10] = res.getString(4);
                        tabelpj[0][11] = res.getString("cep");
                        tabelpj[0][12] = res.getString("uf");
                        tabelpj[0][13] = res.getString("cidade");
                        tabelpj[0][14] = res.getString("bairro");
                        tabelpj[0][15] = res.getString("endereco");
                        tabelpj[0][16] = res.getString("numero");
                        tabelpj[0][17] = res.getString("cmpt");

                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    PJuridicaBuscar.CustomTableModel model = new PJuridicaBuscar.CustomTableModel(tabelpj, columnNames);

                    // Configura o modelo da tabela
                    tablepj.setModel(model);
                    sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where codp=?";

                    stm.setString(1, user);
                    res = stm.executeQuery();

                    if (res.next()) {
                        String Nick = res.getString(5);

                        setImg(Nick);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 5: {
                resul++;
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where cnpj=?" + bloq + sx + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user); // Define o valor do parâmetro
                    ResultSet res = stm.executeQuery();

                    // Cria uma matriz bidimensional para armazenar os dados do SELECT
                    Object[][] tabelpj = new Object[1][18]; // Número de colunas é 19

                    while (res.next()) {
                        resul++;
                        tabelpj[0][0] = res.getString(1);
                        tabelpj[0][1] = res.getString(2);
                        tabelpj[0][2] = res.getString(3);
                        tabelpj[0][3] = res.getString(5);
                        tabelpj[0][4] = res.getString("cnpj");
                        tabelpj[0][5] = res.getString("ramo_ativ");
                        tabelpj[0][6] = res.getString("tipoj");
                        tabelpj[0][7] = res.getString(7);
                        tabelpj[0][8] = res.getString(9);
                        tabelpj[0][9] = res.getString(10);
                        tabelpj[0][10] = res.getString(4);
                        tabelpj[0][11] = res.getString("cep");
                        tabelpj[0][12] = res.getString("uf");
                        tabelpj[0][13] = res.getString("cidade");
                        tabelpj[0][14] = res.getString("bairro");
                        tabelpj[0][15] = res.getString("endereco");
                        tabelpj[0][16] = res.getString("numero");
                        tabelpj[0][17] = res.getString("cmpt");

                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    PJuridicaBuscar.CustomTableModel model = new PJuridicaBuscar.CustomTableModel(tabelpj, columnNames);

                    // Configura o modelo da tabela
                    tablepj.setModel(model);
                    sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where nickname=?";

                    stm.setString(1, user);
                    res = stm.executeQuery();

                    if (res.next()) {
                        String Nick = res.getString(5);

                        setImg(Nick);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 6: {
                buscarpjdesp(user);
                break;
            }
            case 7: {
                resul++;
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where nickname=?" + bloq + sx + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user); // Define o valor do parâmetro
                    ResultSet res = stm.executeQuery();

                    // Cria uma matriz bidimensional para armazenar os dados do SELECT
                    Object[][] tabelpj = new Object[1][18]; // Número de colunas é 19

                    while (res.next()) {
                        resul++;
                        tabelpj[0][0] = res.getString(1);
                        tabelpj[0][1] = res.getString(2);
                        tabelpj[0][2] = res.getString(3);
                        tabelpj[0][3] = res.getString(5);
                        tabelpj[0][4] = res.getString("cnpj");
                        tabelpj[0][5] = res.getString("ramo_ativ");
                        tabelpj[0][6] = res.getString("tipoj");
                        tabelpj[0][7] = res.getString(7);
                        tabelpj[0][8] = res.getString(9);
                        tabelpj[0][9] = res.getString(10);
                        tabelpj[0][10] = res.getString(4);
                        tabelpj[0][11] = res.getString("cep");
                        tabelpj[0][12] = res.getString("uf");
                        tabelpj[0][13] = res.getString("cidade");
                        tabelpj[0][14] = res.getString("bairro");
                        tabelpj[0][15] = res.getString("endereco");
                        tabelpj[0][16] = res.getString("numero");
                        tabelpj[0][17] = res.getString("cmpt");

                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    PJuridicaBuscar.CustomTableModel model = new PJuridicaBuscar.CustomTableModel(tabelpj, columnNames);

                    // Configura o modelo da tabela
                    tablepj.setModel(model);
                    sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where nickname=?";

                    stm.setString(1, user);
                    res = stm.executeQuery();

                    if (res.next()) {
                        String Nick = res.getString(5);

                        setImg(Nick);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            default:
                break;
        }
        if (resul == 1) {
            textf_result.setText(resul + " Resultado Encontrado");
        } else {
            textf_result.setText(resul + " Resultados Encontrados");
        }

        selectedOption = (String) cb_dst.getSelectedItem();
        if ("Pessoas Jurídicas Desativadas".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(PJuridicaBuscar.class.getResource("/img/icon_atv.png")));
        } else {
            btn_dst.setIcon(new ImageIcon(PJuridicaBuscar.class.getResource("/img/icon_dest.png")));
        }

    }

    public void BPersPj(String user) {
        resul = 0;

        String bloq, ord, sx;

        // Inicialize min com um valor padrão
        String selectedOption = (String) cb_dst.getSelectedItem();
        switch (selectedOption) {
            case "Pessoas Jurídicas Desativadas":
                bloq = " AND pessoa.bloqueiop=true";
                break;
            case "Pessoas Jurídicas Ativas":
                bloq = " AND (pessoa.bloqueiop IS NULL OR pessoa.bloqueiop = False)";
                break;
            default:
                bloq = "";
                break;
        }

        selectedOption = (String) cb_tp.getSelectedItem();
        sx = "";
        if (null != selectedOption) {
            switch (selectedOption) {
                case "Todos Tipos de Pessoas Jurídicas":
                    sx = "";
                    break;
                case ("ONG"):
                    sx = " and tipoj='" + selectedOption + "'";
                    break;
                case ("Empresa"):
                    sx = " and tipoj='" + selectedOption + "'";
                    break;
                default:
                    break;
            }
        }

        selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = " ORDER BY CONCAT(pessoa.dtcriacao, ' ', pessoa.hrcriacao) DESC";
        } else {
            ord = " ORDER BY CONCAT(pessoa.dtcriacao, ' ', pessoa.hrcriacao) ASC;";
        }

        switch (tipo2) {
            case 1: {
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where juridica.cod_p like ?" + bloq + sx + ord;

                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[18];
                            row[0] = res.getString(1);
                            row[1] = res.getString(2);
                            row[2] = res.getString(3);
                            row[3] = res.getString(5);
                            row[4] = res.getString("cnpj");
                            row[5] = res.getString("ramo_ativ");
                            row[6] = res.getString("tipoj");
                            row[7] = res.getString(7);
                            row[8] = res.getString(9);
                            row[9] = res.getString(10);
                            row[10] = res.getString(4);
                            row[11] = res.getString("cep");
                            row[12] = res.getString("uf");
                            row[13] = res.getString("cidade");
                            row[14] = res.getString("bairro");
                            row[15] = res.getString("endereco");
                            row[16] = res.getString("numero");
                            row[17] = res.getString("cmpt");
                            data.add(row);
                            result = res.next();
                        }
                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    tablepj.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            }
            case 5: {
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where juridica.cnpj like ?" + bloq + sx + ord;

                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[18];
                            row[0] = res.getString(1);
                            row[1] = res.getString(2);
                            row[2] = res.getString(3);
                            row[3] = res.getString(5);
                            row[4] = res.getString("cnpj");
                            row[5] = res.getString("ramo_ativ");
                            row[6] = res.getString("tipoj");
                            row[7] = res.getString(7);
                            row[8] = res.getString(9);
                            row[9] = res.getString(10);
                            row[10] = res.getString(4);
                            row[11] = res.getString("cep");
                            row[12] = res.getString("uf");
                            row[13] = res.getString("cidade");
                            row[14] = res.getString("bairro");
                            row[15] = res.getString("endereco");
                            row[16] = res.getString("numero");
                            row[17] = res.getString("cmpt");
                            data.add(row);
                            result = res.next();
                        }
                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    tablepj.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            }
            case 6: {
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where juridica.ramo_ativ like ?" + bloq + sx + ord;

                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[18];
                            row[0] = res.getString(1);
                            row[1] = res.getString(2);
                            row[2] = res.getString(3);
                            row[3] = res.getString(5);
                            row[4] = res.getString("cnpj");
                            row[5] = res.getString("ramo_ativ");
                            row[6] = res.getString("tipoj");
                            row[7] = res.getString(7);
                            row[8] = res.getString(9);
                            row[9] = res.getString(10);
                            row[10] = res.getString(4);
                            row[11] = res.getString("cep");
                            row[12] = res.getString("uf");
                            row[13] = res.getString("cidade");
                            row[14] = res.getString("bairro");
                            row[15] = res.getString("endereco");
                            row[16] = res.getString("numero");
                            row[17] = res.getString("cmpt");
                            data.add(row);
                            result = res.next();
                        }
                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    tablepj.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            }
            case 7: {
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where pessoa.nickname like ?" + bloq + sx + ord;

                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[18];
                            row[0] = res.getString(1);
                            row[1] = res.getString(2);
                            row[2] = res.getString(3);
                            row[3] = res.getString(5);
                            row[4] = res.getString("cnpj");
                            row[5] = res.getString("ramo_ativ");
                            row[6] = res.getString("tipoj");
                            row[7] = res.getString(7);
                            row[8] = res.getString(9);
                            row[9] = res.getString(10);
                            row[10] = res.getString(4);
                            row[11] = res.getString("cep");
                            row[12] = res.getString("uf");
                            row[13] = res.getString("cidade");
                            row[14] = res.getString("bairro");
                            row[15] = res.getString("endereco");
                            row[16] = res.getString("numero");
                            row[17] = res.getString("cmpt");
                            data.add(row);
                            result = res.next();
                        }
                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    tablepj.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 4: {
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where pessoa.contatop like ?" + bloq + sx + ord;

                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[18];
                            row[0] = res.getString(1);
                            row[1] = res.getString(2);
                            row[2] = res.getString(3);
                            row[3] = res.getString(5);
                            row[4] = res.getString("cnpj");
                            row[5] = res.getString("ramo_ativ");
                            row[6] = res.getString("tipoj");
                            row[7] = res.getString(7);
                            row[8] = res.getString(9);
                            row[9] = res.getString(10);
                            row[10] = res.getString(4);
                            row[11] = res.getString("cep");
                            row[12] = res.getString("uf");
                            row[13] = res.getString("cidade");
                            row[14] = res.getString("bairro");
                            row[15] = res.getString("endereco");
                            row[16] = res.getString("numero");
                            row[17] = res.getString("cmpt");
                            data.add(row);
                            result = res.next();
                        }
                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    tablepj.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                this.connection = new ConnectionFactory().getConnection();
                String sql = "";
                sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend where pessoa.emailp like ?" + bloq + sx + ord;

                try {

                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[18];
                            row[0] = res.getString(1);
                            row[1] = res.getString(2);
                            row[2] = res.getString(3);
                            row[3] = res.getString(5);
                            row[4] = res.getString("cnpj");
                            row[5] = res.getString("ramo_ativ");
                            row[6] = res.getString("tipoj");
                            row[7] = res.getString(7);
                            row[8] = res.getString(9);
                            row[9] = res.getString(10);
                            row[10] = res.getString(4);
                            row[11] = res.getString("cep");
                            row[12] = res.getString("uf");
                            row[13] = res.getString("cidade");
                            row[14] = res.getString("bairro");
                            row[15] = res.getString("endereco");
                            row[16] = res.getString("numero");
                            row[17] = res.getString("cmpt");
                            data.add(row);
                            result = res.next();
                        }
                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    tablepj.setModel(model);
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
        if ("Pessoas Jurídicas Desativadas".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(PJuridicaBuscar.class.getResource("/img/icon_atv.png")));
        } else {
            btn_dst.setIcon(new ImageIcon(PJuridicaBuscar.class.getResource("/img/icon_dest.png")));
        }
    }

    public void buscarTodospj() {
        resul = 0;
        String bloq, ord, sx;

        // Inicialize min com um valor padrão
        String selectedOption = (String) cb_dst.getSelectedItem();
        if (null == selectedOption) {
            bloq = "";
        } else {
            switch (selectedOption) {
                case "Pessoas Jurídicas Desativadas":
                    bloq = " WHERE pessoa.bloqueiop=true";
                    break;
                case "Pessoas Jurídicas Ativas":
                    bloq = " WHERE (pessoa.bloqueiop IS NULL OR pessoa.bloqueiop = False)";
                    break;
                default:
                    bloq = " WHERE";
                    break;
            }
        }

        selectedOption = (String) cb_tp.getSelectedItem();
        sx = "";
        if (" WHERE".equals(bloq)) {
            if (null != selectedOption) {
                switch (selectedOption) {
                    case "Todos Tipos de Pessoas Jurídicas":
                        sx = "";
                        break;
                    case ("ONG"):
                        sx = " tipoj='" + selectedOption + "'";
                        break;
                    case ("Empresa"):
                        sx = " tipoj='" + selectedOption + "'";
                        break;
                    default:
                        break;
                }
            }
        } else {
            if (null != selectedOption) {
                switch (selectedOption) {
                    case "Todos Tipos de Pessoas Jurídicas":
                        sx = "";
                        break;
                    case ("ONG"):
                        sx = " and tipoj='" + selectedOption + "'";
                        break;
                    case ("Empresa"):
                        sx = " and tipoj='" + selectedOption + "'";
                        break;
                    default:
                        break;
                }
            }
        }

        selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = " ORDER BY CONCAT(pessoa.dtcriacao, ' ', pessoa.hrcriacao) DESC";
        } else {
            ord = " ORDER BY CONCAT(pessoa.dtcriacao, ' ', pessoa.hrcriacao) ASC;";
        }

        if (" WHERE".equals(bloq) && "".equals(sx)) {
            bloq = "";
        }

        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend " + bloq + sx + ord;

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

            boolean result = res.next();
            if (result) {
                alert al = new alert(adm, audio);
                al.audios("ok");
                while (result) {
                    resul++;
                    Object[] row = new Object[18];
                    row[0] = res.getString(1);
                    row[1] = res.getString(2);
                    row[2] = res.getString(3);
                    row[3] = res.getString(5);
                    row[4] = res.getString("cnpj");
                    row[5] = res.getString("ramo_ativ");
                    row[6] = res.getString("tipoj");
                    row[7] = res.getString(7);
                    row[8] = res.getString(9);
                    row[9] = res.getString(10);
                    row[10] = res.getString(4);
                    row[11] = res.getString("cep");
                    row[12] = res.getString("uf");
                    row[13] = res.getString("cidade");
                    row[14] = res.getString("bairro");
                    row[15] = res.getString("endereco");
                    row[16] = res.getString("numero");
                    row[17] = res.getString("cmpt");
                    data.add(row);
                    result = res.next();
                }
                cont++;
            } else {
                if (cont == 0) {
                    cont++;
                } else {
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Nenhum registro encontrado";
                    String msg2 = "no Banco de Dados";
                    String tit = "Informação";
                    al.alertinput(tit, "info", msg, msg2, "", "info");
                }
            }

            // Define os nomes das colunas
            Object[] columnNames = {"Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"};

            // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
            PJuridicaBuscar.CustomTableModel model = new PJuridicaBuscar.CustomTableModel(data.toArray(new Object[0][0]), columnNames);

            // Configura o modelo da tabela
            tablepj.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (resul == 1) {
            textf_result.setText(resul + " Resultado Encontrado");
        } else {
            textf_result.setText(resul + " Resultados Encontrados");
        }

        selectedOption = (String) cb_dst.getSelectedItem();
        if ("Pessoas Jurídicas Desativadas".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(PJuridicaBuscar.class.getResource("/img/icon_atv.png")));
        } else {
            btn_dst.setIcon(new ImageIcon(PJuridicaBuscar.class.getResource("/img/icon_dest.png")));
        }
    }

    public void delet(String cod) {
        PreparedStatement ps;
        String status;
        try {
            // Excluir contatoserv relacionados à pessoa jurídica
            ps = connection.prepareStatement("DELETE FROM contatoserv WHERE pcodp IN (SELECT pcod FROM pessoa WHERE pcod IN (SELECT cod_p FROM juridica WHERE cod_p = ?));");
            ps.setString(1, cod);
            int i = ps.executeUpdate();

            // Excluir imagem relacionada à pessoa jurídica
            ps = connection.prepareStatement("DELETE FROM imagem WHERE petcodpet IN (SELECT petcod FROM pet WHERE pessoacodp IN (SELECT pcod FROM pessoa WHERE pcod IN (SELECT cod_p FROM juridica WHERE cod_p = ?)));");
            ps.setString(1, cod);
            i = ps.executeUpdate();

            // Excluir imagem relacionada à pessoa física
            ps = connection.prepareStatement("DELETE FROM imagem WHERE servicocodserv IN (SELECT servcod FROM servico WHERE pessoa_codp IN (SELECT pcod FROM pessoa WHERE pcod IN (SELECT cod_p FROM juridica WHERE cod_p = ?)));");
            ps.setString(1, cod);
            i = ps.executeUpdate();

            // Excluir pets relacionados à pessoa jurídica
            ps = connection.prepareStatement("DELETE FROM pet WHERE pessoacodp IN (SELECT pcod FROM pessoa WHERE pcod IN (SELECT cod_p FROM juridica WHERE cod_p = ?));");
            ps.setString(1, cod);
            i = ps.executeUpdate();

            // Excluir serviços relacionados à pessoa jurídica
            ps = connection.prepareStatement("DELETE FROM servico WHERE pessoa_codp IN (SELECT pcod FROM pessoa WHERE pcod IN (SELECT cod_p FROM juridica WHERE cod_p = ?));");
            ps.setString(1, cod);
            i = ps.executeUpdate();

            String sql = "SELECT * FROM endereco WHERE endcod = (SELECT endcodend FROM pessoa WHERE pcod = ?);";
            try {

                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setString(1, cod);
                ResultSet res = stm.executeQuery();

                if (res.next()) {
                    codend = res.getString(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Excluir pessoa jurídica
            ps = connection.prepareStatement("DELETE pessoa, juridica FROM pessoa INNER JOIN juridica ON pessoa.pcod = juridica.cod_p WHERE pessoa.pcod = ?;");
            ps.setString(1, cod);
            i = ps.executeUpdate();

            sql = "SELECT * FROM pessoa WHERE endcodend =? ;";
            try {

                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setString(1, codend);
                ResultSet res = stm.executeQuery();

                if (res.next()) {
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Endereço em uso";
                    String msg2 = "por outras pessoas";
                    String tit = "Endereço não deletado";
                    al.alertinput(tit, "info", msg, msg2, "", "info");
                } else {
                    // Excluir endereco
                    ps = connection.prepareStatement("DELETE FROM endereco WHERE endcod = ?;");
                    ps.setString(1, codend);
                    i = ps.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (i != 0) {
                alert al = new alert(adm, audio);
                al.setVisible(true);
                String msg = "Pessoa Jurídica " + seluser;
                String msg2 = "deletado do database";
                String tit = "Deleção de Pessoa Jurídica";
                al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                buscarTodospj();
            } else {
                alert al = new alert(adm, audio);
                al.setVisible(true);
                String msg = "Pessoa Jurídica " + seluser;
                String msg2 = "não deletado do database";
                String tit = "Deleção de Pessoa Jurídica";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            }
        } catch (SQLException e) {
            // Tratar exceção
            e.printStackTrace();
        }

    }

    public void bsenha() {

        String sql = "SELECT * FROM pessoa JOIN juridica ON juridica.cod_p = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend WHERE nickname = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, seluser);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                String tx2 = res.getString(6);
                pass_versenha.setText(tx2);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PFundo = new javax.swing.JPanel();
        label_selecionado = new javax.swing.JLabel();
        textf_copiado = new javax.swing.JTextField();
        label_copy = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        label_foto = new javax.swing.JLabel();
        btn_cad = new javax.swing.JButton();
        btn_att = new javax.swing.JButton();
        textf_result = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Latt = new javax.swing.JLabel();
        Lbuscar = new javax.swing.JLabel();
        Cb_pf = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        textf_buscar = new javax.swing.JTextField();
        btn_limpar1 = new SwingPerson.JbuttonArr();
        btn_tirarsel1 = new SwingPerson.JbuttonArr();
        check_buperso = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        btn_dst = new SwingPerson.JbuttonArr();
        btn_deletar = new SwingPerson.JbuttonArr();
        cb_ordem = new javax.swing.JComboBox<>();
        cb_dst = new javax.swing.JComboBox<>();
        cb_tp = new javax.swing.JComboBox<>();
        btn_lmpfil = new SwingPerson.JbuttonArr();
        btn_filpet = new SwingPerson.JbuttonArr();
        pass_versenha = new javax.swing.JPasswordField();
        btn_vsenha = new SwingPerson.JbuttonArr();
        label_vsenha = new javax.swing.JLabel();
        btn_pet = new SwingPerson.JbuttonArr();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablepj = new javax.swing.JTable();
        btn_pet1 = new SwingPerson.JbuttonArr();

        setBorder(null);
        setMinimumSize(new java.awt.Dimension(1284, 672));
        setPreferredSize(new java.awt.Dimension(1284, 666));

        PFundo.setBackground(new java.awt.Color(64, 33, 7));
        PFundo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PFundo.setPreferredSize(new java.awt.Dimension(1284, 666));

        label_selecionado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_selecionado.setForeground(new java.awt.Color(242, 242, 242));
        label_selecionado.setText("Valor Selecionado");

        textf_copiado.setEditable(false);
        textf_copiado.setBackground(new java.awt.Color(204, 204, 204));
        textf_copiado.setToolTipText("Campo Selecionado na Tabela");
        textf_copiado.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_copiado.setMinimumSize(new java.awt.Dimension(500, 20));
        textf_copiado.setName(""); // NOI18N
        textf_copiado.setPreferredSize(new java.awt.Dimension(500, 20));
        textf_copiado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textf_copiadoActionPerformed(evt);
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

        jPanel2.setBackground(new java.awt.Color(255, 253, 243));

        label_foto.setBackground(new java.awt.Color(255, 253, 243));
        label_foto.setForeground(new java.awt.Color(255, 253, 243));
        label_foto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_foto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(label_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btn_cad.setBackground(new java.awt.Color(64, 33, 7));
        btn_cad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_cad.setForeground(new java.awt.Color(255, 255, 255));
        btn_cad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_cadbr.png"))); // NOI18N
        btn_cad.setToolTipText("Cadastrar Pessoa Jurídica");
        btn_cad.setBorder(null);
        btn_cad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cad.setMinimumSize(new java.awt.Dimension(35, 35));
        btn_cad.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_cad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadActionPerformed(evt);
            }
        });

        btn_att.setBackground(new java.awt.Color(64, 33, 7));
        btn_att.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_att.setForeground(new java.awt.Color(255, 255, 255));
        btn_att.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/atualizarbr.png"))); // NOI18N
        btn_att.setToolTipText("Atualizar Pessoa Selecionada");
        btn_att.setBorder(null);
        btn_att.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_att.setMinimumSize(new java.awt.Dimension(35, 35));
        btn_att.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_att.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_attActionPerformed(evt);
            }
        });

        textf_result.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        textf_result.setForeground(new java.awt.Color(255, 253, 243));
        textf_result.setText("x Resultados Encontrados");
        textf_result.setToolTipText("Resultados Encontrados na Tabela de Pessoa Jurídica");

        jPanel1.setBackground(new java.awt.Color(255, 253, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(397, 200));

        Latt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_att.png"))); // NOI18N
        Latt.setToolTipText("Busque Todos e Atualize o Banco");
        Latt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Latt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LattMouseClicked(evt);
            }
        });

        Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        Lbuscar.setToolTipText("Buscar Pessoa Jurídica");
        Lbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Lbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LbuscarMouseClicked(evt);
            }
        });

        Cb_pf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código Pessoa Jurídica", "CNPJ", "Ramo/Atividade", "E-mail", "Contato", "Nome Fantasia" }));
        Cb_pf.setSelectedIndex(-1);
        Cb_pf.setToolTipText("Selecione Por qual Informação Quer Pesquisar");
        Cb_pf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Cb_pf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cb_pfActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(363, 3));

        textf_buscar.setEditable(false);
        textf_buscar.setBackground(new java.awt.Color(255, 253, 243));
        textf_buscar.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_buscar.setToolTipText("Selecione a forma que deseja Pesquisar a Pessoa Jurídica");
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
        check_buperso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_bupersoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Buscar Pessoa Jurídica");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Cb_pf, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Latt))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(textf_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Lbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(check_buperso)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_limpar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_tirarsel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 50, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Latt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Cb_pf, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
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

        btn_dst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_dest.png"))); // NOI18N
        btn_dst.setToolTipText("Desativar/Ativar Pessoa Selecionada");
        btn_dst.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_dst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dstActionPerformed(evt);
            }
        });

        btn_deletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/deletarm.png"))); // NOI18N
        btn_deletar.setToolTipText("Deletar Pessoa Selecionada");
        btn_deletar.setMaximumSize(new java.awt.Dimension(35, 35));
        btn_deletar.setMinimumSize(new java.awt.Dimension(35, 35));
        btn_deletar.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_deletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deletarActionPerformed(evt);
            }
        });

        cb_ordem.setBackground(new java.awt.Color(255, 253, 243));
        cb_ordem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mais Recentes", "Mais Antigos" }));
        cb_ordem.setToolTipText("Pessoa Jurídica em Ordem de data Crescente/Decrescente");
        cb_ordem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cb_dst.setBackground(new java.awt.Color(255, 253, 243));
        cb_dst.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas Pessoas Jurídicas At/Dst", "Pessoas Jurídicas Ativas", "Pessoas Jurídicas Desativadas" }));
        cb_dst.setToolTipText("Pets Aivos(At)/Desativados(Dst)");
        cb_dst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb_dst.setMinimumSize(new java.awt.Dimension(202, 22));
        cb_dst.setPreferredSize(new java.awt.Dimension(202, 22));

        cb_tp.setBackground(new java.awt.Color(255, 253, 243));
        cb_tp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos Tipos de Pessoas Jurídicas", "ONG", "Empresa", " " }));
        cb_tp.setToolTipText("");
        cb_tp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb_tp.setMinimumSize(new java.awt.Dimension(202, 22));
        cb_tp.setPreferredSize(new java.awt.Dimension(202, 22));

        btn_lmpfil.setText("Limpar Filtros");
        btn_lmpfil.setToolTipText("Limpar todos os filtros");
        btn_lmpfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lmpfilActionPerformed(evt);
            }
        });

        btn_filpet.setText("Filtrar Pessoas");
        btn_filpet.setToolTipText("Procurar Pessoas com os filtros");
        btn_filpet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filpetActionPerformed(evt);
            }
        });

        pass_versenha.setEditable(false);
        pass_versenha.setBackground(new java.awt.Color(204, 204, 204));
        pass_versenha.setToolTipText("Senha da Pessoa Jurídica Selecionado");
        pass_versenha.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pass_versenha.setMinimumSize(new java.awt.Dimension(64, 27));
        pass_versenha.setPreferredSize(new java.awt.Dimension(64, 27));
        pass_versenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pass_versenhaActionPerformed(evt);
            }
        });

        btn_vsenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_versenham.png"))); // NOI18N
        btn_vsenha.setToolTipText("Sem Permissão para Ver Senha de Pessoa Jurídica\n");
        btn_vsenha.setPreferredSize(new java.awt.Dimension(20, 20));
        btn_vsenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vsenhaActionPerformed(evt);
            }
        });

        label_vsenha.setBackground(new java.awt.Color(64, 33, 7));
        label_vsenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_vsenha.setForeground(new java.awt.Color(255, 253, 243));
        label_vsenha.setText("Senha");

        btn_pet.setText("Ver Pet da Pessoa Jurídica");
        btn_pet.setToolTipText("Ver Pets que Essa Pessoa Possui");
        btn_pet.setEnabled(false);
        btn_pet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_petActionPerformed(evt);
            }
        });

        tablepj.setBackground(new java.awt.Color(204, 204, 204));
        tablepj.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Contato", "Email", "Nome Fantasia", "CNPJ", "Ramo/Atividade", "Tipo", "Código", "Data de Criação", "Hora de Criação", "Cód Endereço", "CEP", "UF", "Cidade", "Bairro", "Endereço", "Número", "Complemento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablepj.setGridColor(new java.awt.Color(255, 253, 243));
        jScrollPane1.setViewportView(tablepj);

        btn_pet1.setText("Ver Serviço da Pessoa Jurídica");
        btn_pet1.setToolTipText("Ver Serviços que Essa Pessoa Possui");
        btn_pet1.setEnabled(false);
        btn_pet1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pet1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PFundoLayout = new javax.swing.GroupLayout(PFundo);
        PFundo.setLayout(PFundoLayout);
        PFundoLayout.setHorizontalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addComponent(textf_copiado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_copy))
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_selecionado)
                                    .addGroup(PFundoLayout.createSequentialGroup()
                                        .addComponent(cb_tp, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(152, 152, 152)
                                        .addComponent(btn_lmpfil, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_filpet, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PFundoLayout.createSequentialGroup()
                                        .addComponent(cb_ordem, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cb_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PFundoLayout.createSequentialGroup()
                                        .addComponent(pass_versenha, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_vsenha, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label_vsenha))
                                .addGap(0, 23, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                        .addComponent(btn_pet, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_pet1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_cad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_deletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(textf_result)))
                .addContainerGap())
        );
        PFundoLayout.setVerticalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addComponent(label_selecionado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textf_copiado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_copy, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_vsenha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pass_versenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_vsenha, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_ordem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btn_lmpfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_filpet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cb_tp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textf_result)
                .addGap(10, 10, 10)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_cad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_deletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_pet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_pet1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PFundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PFundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void versenha() {
        audios("cl");
        if (cont2 == 0) {
            pass_versenha.setEchoChar((char) 0);
            cont2 = 1;
            btn_vsenha
                    .setIcon(new ImageIcon(PJuridicaBuscar.class
                            .getResource("/img/icon_desversenham.png")));
            btn_vsenha.setToolTipText("Senha Visível");
            Font customFont = new Font("Segoe UI", Font.PLAIN, 12);
            pass_versenha.setFont(customFont);
        } else {
            pass_versenha.setEchoChar('\u25CF');
            cont2 = 0;
            btn_vsenha
                    .setIcon(new ImageIcon(PJuridicaBuscar.class
                            .getResource("/img/icon_versenham.png")));
            btn_vsenha.setToolTipText("Senha Oculta");
            Font customFont = new Font("Segoe UI", Font.PLAIN, 10);
            pass_versenha.setFont(customFont);
        }
    }

    public void inm() {
        alert al = new alert(adm, audio);
        al.setVisible(true);
        String msg = "Indisponível no momento";
        String tit = "Página em manutenção";
        al.alertinput(tit, "info", "", msg, "", "info");
    }

    public void lmp() {
        audios("cl");
        textf_copiado.setText("");
        pass_versenha.setText("");
        limpar();
    }

    public void del() {
        if (adm == null || adm.equals("")) {
            alert al = new alert(adm, audio);
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
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Selecione na Tabela";
                    String msg2 = "quem deseja deletar";
                    String tit = "Nenhuma Pessoa Física selecionada";
                    al.alertinput(tit, "info", msg, msg2, "", "info");

                } else {
                    audios("aviso");
                    int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Deletar a Pessoa Física " + seluser + "?\nUma vez deletado, essas informações sumirão do banco de dados");

                    if (escolha == 0) {
                        tipoveri = 6;

                        verificar(selcod);
                    }
                }
            } else {
                alert al = new alert(adm, audio);
                al.setVisible(true);
                String msg2 = "Você não tem ";
                String msg3 = "Permissão suficiente";
                String tit = "Sem Permissão";
                al.alertinput(tit, "erro", msg2, msg3, "", "erro");
            }
        }
    }

    public void bus() {

        if (!check_buperso.isSelected()) {
            String selectedItem = Cb_pf.getSelectedItem().toString();

            switch (selectedItem) {
                case "Código Pessoa Jurídica":
                    tipo2 = 1;
                    tipoveri = 1;
                    String cod = textf_buscar.getText();
                    verificar(cod);
                    break;
                case "Nome Fantasia":
                    tipo2 = 7;
                    tipoveri = 1;
                    String cp = textf_buscar.getText();
                    verificar(cp);
                    break;
                case "E-mail": {
                    tipo2 = 3;
                    tipoveri = 1;
                    String cadm = textf_buscar.getText();
                    verificar(cadm);
                    break;
                }
                case "Contato": {
                    tipo2 = 4;
                    tipoveri = 1;
                    String cadm = textf_buscar.getText();
                    verificar(cadm);
                    break;
                }
                case "CNPJ": {
                    tipo2 = 5;
                    tipoveri = 1;
                    String cadm = textf_buscar.getText();
                    verificar(cadm);
                    break;
                }
                case "Ramo/Atividade": {
                    tipo2 = 6;
                    tipoveri = 1;
                    String cadm = textf_buscar.getText();
                    verificar(cadm);
                    break;
                }
                default:
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "";
                    String msg2 = "Nenhuma Opção Selecionada";
                    String tit = "";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    break;
            }

        } else {
            alert al = new alert(adm, audio);
            al.setVisible(true);
            String msg = "Desmarque a busca";
            String msg2 = "personalizada para";
            String msg3 = "Buscar especifíco.";
            String tit = "";
            al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
        }
    }

    public void bper(int a) {
        resul = 0;
        String campo = textf_buscar.getText();
        if (check_buperso.isSelected()) {
            switch (a) {
                case 7:
                    tipo2 = 7;

                    if (!campo.isEmpty()) {
                        BPersPj(campo);
                    }
                    break;

                case 1:
                    tipo2 = 1;
                    if (!campo.isEmpty()) {
                        BPersPj(campo);
                    }
                    break;

                case 4:
                    tipo2 = 4;
                    String contato = textf_buscar.getText().replaceAll("[^0-9]", "");

                    if (!contato.isEmpty()) {

                        BPersPj(contato);
                    }
                    break;

                case 3:
                    tipo2 = 3;
                    if (!campo.isEmpty()) {
                        BPersPj(campo);
                    }
                    break;
                case 5:
                    tipo2 = 5;
                    if (!campo.isEmpty()) {
                        BPersPj(campo);
                    }
                    break;
                case 6:
                    tipo2 = 6;
                    if (!campo.isEmpty()) {
                        BPersPj(campo);
                    }
                    break;
                default:

                    break;
            }
        }
    }

    public void tsl() {
        dst = null;
        if (tipo2 == 0) {
            label_foto.setIcon(null);
        }
        btn_pet.setEnabled(false);
        btn_pet1.setEnabled(false);
        seluser = "";
        tablepj.clearSelection();
        textf_copiado.setText("");
        pass_versenha.setText("");
    }

    public void att_banco() {
        limpar();
        resul = 0;
        buscarTodospj();
        alert al = new alert(adm, audio);
        al.setVisible(true);
        String msg = "Banco Atualizado";
        String tit = "Atualização";
        al.alertinput(tit, "ok", "", msg, "", "sucesso");
    }

    public void dst() {

        if (adm == null || adm.equals("")) {
            alert al = new alert(adm, audio);
            al.setVisible(true);
            String msg = "Você deve estar logado";
            String msg2 = "Para Efetuar isso";
            String tit = "Não Logado";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");
        } else {

            if (seluser == null) {
                alert al = new alert(adm, audio);
                al.setVisible(true);
                String msg = "Selecione na Tabela";
                String msg2 = "quem deseja Desativar";
                String tit = "Nenhuma Pessoa Jurídica selecionada";
                al.alertinput(tit, "info", msg, msg2, "", "info");

            } else {

                String selectedOption = (String) cb_dst.getSelectedItem();
                if (dst == false) {
                    audios("aviso");
                    int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Ativar a Pessoa Jurídica " + seluser + "?");

                    if (escolha == 0) {
                        tipoveri = 4;
                        verificar(selcod);
                    }
                } else {
                    audios("aviso");
                    int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Desativar a Pessoa Jurídica " + seluser + "?");

                    if (escolha == 0) {
                        tipoveri = 4;
                        verificar(selcod);
                    }
                }
            }

        }
    }

    public void att() {
        if (permchefe == true || selcod.equals(adm)) {
            if ("nulo".equals(adm) || !"nulo".equals(selcod)) {
                if ("null".equals(adm) || !"null".equals(selcod)) {
                    if (adm == null || adm.equals("")) {
                        alert al = new alert(adm, audio);
                        al.setVisible(true);
                        String msg = "Você deve estar logado";
                        String msg2 = "Para Efetuar isso";
                        String tit = "Não Logado";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        login lg = new login(audio);
                        lg.setVisible(true);
                        dispose();
                    } else {

                        if (selcod == null) {
                            String cod = textf_buscar.getText();
                            alert al = new alert(adm, audio);
                            al.setVisible(true);
                            String msg = "Selecione na Tabela";
                            String msg2 = "quem deseja Atualizar";
                            String tit = "Nenhum Pessoa Jurídica selecionado";
                            al.alertinput(tit, "info", msg, msg2, "", "info");

                        } else {
                            audios("tc");
                            PJuridicaAtualizar pj = new PJuridicaAtualizar(adm, audio, seluser);
                            pj.setVisible(true);
                        }
                    }
                }
            }
        }
    }

    private void textf_copiadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textf_copiadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textf_copiadoActionPerformed

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
        audios("tc");
        PJuridicaCadastrar pf = new PJuridicaCadastrar(adm, audio);
        pf.setVisible(true);
        tsl();
    }//GEN-LAST:event_btn_cadActionPerformed

    private void btn_attActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_attActionPerformed
        att();
        tsl();
    }//GEN-LAST:event_btn_attActionPerformed

    private void LattMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LattMouseClicked
        btn_pet.setEnabled(false);
        btn_pet1.setEnabled(false);
        att_banco();
    }//GEN-LAST:event_LattMouseClicked

    private void LbuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LbuscarMouseClicked
        btn_pet.setEnabled(false);
        btn_pet1.setEnabled(false);
        if (Cb_pf.getSelectedItem() != null) {
            bus();
            Lbuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            Lbuscar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_LbuscarMouseClicked

    private void Cb_pfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cb_pfActionPerformed
        if (Cb_pf.getSelectedItem() != null) {
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
            textf_buscar.setToolTipText("Selecione a A forma que deseja Pesquisar a Pessoa Jurídica.");
            Lbuscar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            textf_buscar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            //textf_buscar.setCursor(lockedCursor);
        }
    }//GEN-LAST:event_Cb_pfActionPerformed

    private void textf_buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_buscarMouseClicked
        if (textf_buscar.isEditable()) {
            limpar(); // Chama o método "limpar" se o campo estiver editável
        }
    }//GEN-LAST:event_textf_buscarMouseClicked

    private void textf_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textf_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textf_buscarActionPerformed

    private void textf_buscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_buscarKeyPressed
        if (textf_buscar.isEditable()) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

                bus();
            }
        }
    }//GEN-LAST:event_textf_buscarKeyPressed

    private void textf_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_buscarKeyReleased
        resul = 0;
        if (textf_buscar.isEditable() && check_buperso.isSelected()) {
            String selectedItem = Cb_pf.getSelectedItem().toString();

            switch (selectedItem) {
                case "Código Pessoa Jurídica":
                    tipo2 = 1;
                    tipoveri = 1;
                    bper(1);
                    break;
                case "Nome Fantasia":
                    tipo2 = 7;
                    tipoveri = 1;
                    bper(7);
                    break;
                case "E-mail": {
                    tipo2 = 3;
                    tipoveri = 1;
                    bper(3);
                    break;
                }
                case "Contato": {
                    tipo2 = 4;
                    tipoveri = 1;
                    bper(4);
                    break;
                }
                case "CNPJ": {
                    tipo2 = 5;
                    tipoveri = 1;
                    bper(5);
                    break;
                }
                case "Ramo/Atividade": {
                    tipo2 = 6;
                    tipoveri = 1;
                    bper(6);
                    break;
                }
                default:
                    alert al = new alert(adm, audio);
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

    private void btn_lmpfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lmpfilActionPerformed
        audios("cl");
        btn_pet.setEnabled(false);
        btn_pet1.setEnabled(false);
        limfiltro();
        tsl();
    }//GEN-LAST:event_btn_lmpfilActionPerformed

    private void btn_filpetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filpetActionPerformed
        dst = null;
        buscarTodospj();
        tsl();
    }//GEN-LAST:event_btn_filpetActionPerformed

    private void pass_versenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pass_versenhaActionPerformed
        String selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            cb_ordem.setToolTipText("Buscar Pessoa Jurídica Mais Recentes");
        } else {
            cb_ordem.setToolTipText("Buscar Pessoa Jurídica Mais Antigos");
        }
    }//GEN-LAST:event_pass_versenhaActionPerformed

    private void btn_vsenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vsenhaActionPerformed
        versenha();
    }//GEN-LAST:event_btn_vsenhaActionPerformed

    private void btn_petActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_petActionPerformed
        audios("cl");
        if (telaPrin != null) {
            // Crie um evento personalizado para indicar que o botão foi clicado
            ActionEvent closeEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "fecharJFrame");

            // Dispare o evento para a TelaPrin
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeEvent);
        }

        if (selcod != null || !"".equals(selcod)) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    TelaPrin aa = new TelaPrin(adm, audio);
                    aa.setVisible(true);
                    aa.selectpet(selcod);
                }
            });
        }

    }//GEN-LAST:event_btn_petActionPerformed

    private void btn_pet1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pet1ActionPerformed
        audios("cl");
        if (telaPrin != null) {
            // Crie um evento personalizado para indicar que o botão foi clicado
            ActionEvent closeEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "fecharJFrame");

            // Dispare o evento para a TelaPrin
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeEvent);
        }

        if (selcod != null || !"".equals(selcod)) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    TelaPrin aa = new TelaPrin(adm, audio);
                    aa.setVisible(true);
                    aa.selectserv(selcod);
                }
            });
        }
    }//GEN-LAST:event_btn_pet1ActionPerformed

    private void check_bupersoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_bupersoActionPerformed
        btn_pet.setEnabled(false);
        btn_pet1.setEnabled(false);
    }//GEN-LAST:event_check_bupersoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Cb_pf;
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
    private SwingPerson.JbuttonArr btn_pet;
    private SwingPerson.JbuttonArr btn_pet1;
    private SwingPerson.JbuttonArr btn_tirarsel1;
    private SwingPerson.JbuttonArr btn_vsenha;
    private javax.swing.JComboBox<String> cb_dst;
    private javax.swing.JComboBox<String> cb_ordem;
    private javax.swing.JComboBox<String> cb_tp;
    private javax.swing.JCheckBox check_buperso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_copy;
    private javax.swing.JLabel label_foto;
    private javax.swing.JLabel label_selecionado;
    private javax.swing.JLabel label_vsenha;
    private javax.swing.JPasswordField pass_versenha;
    private javax.swing.JTable tablepj;
    private javax.swing.JTextField textf_buscar;
    private javax.swing.JTextField textf_copiado;
    private javax.swing.JLabel textf_result;
    // End of variables declaration//GEN-END:variables
}
