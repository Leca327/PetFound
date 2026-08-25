package InJframe;

import Logar.login;
import alert.alert;
import factory.ConnectionFactory;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import subGUI.AdminAtualizar;
import subGUI.AdminCadastrar;

public class AdminBuscar extends javax.swing.JInternalFrame {

    private Connection connection;
    String seluser;
    public String admin, audio;
    int tipo2, resul;
    int cont = 0;
    Boolean permchefe = false;
    Boolean permchefesel;
    int cont2 = 0;
    Boolean dst;

    public AdminBuscar(String adm, String au) {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        //
        admin = adm;
        audio = au;
        buscarTodosAdmin();
        if (admin != null || !"".equals(admin)) {
            bus(admin);
        }

        //textf_buscar.setCursor(lockedCursor);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();

                audios("cl");
                seluser = table.getValueAt(row, 0).toString();
                setImg(seluser);

                Object value = table.getValueAt(row, column);
                String campoSelecionado;
                if (value != null && !value.toString().isEmpty()) {
                    campoSelecionado = value.toString();
                    textf_copiado.setText(campoSelecionado);
                    bsenha();
                    btn_permchefe.setEnabled(true);
                    busadm(seluser);
                    if (permchefesel == false) {
                        btn_permchefe.setText("Dar Permissão de Chefe");
                    } else {
                        btn_permchefe.setText("Tirar Permissão de Chefe");
                    }
                } else {
                    campoSelecionado = "";
                    textf_copiado.setText("");
                    pass_versenha.setText("");

                }

                busdst(seluser);
                if ("nulo".equals(seluser) || "null".equals(seluser)) {
                    if ("nulo".equals(admin) || "null".equals(admin)) {

                    } else {
                        pass_versenha.setEchoChar('\u25CF');
                        cont2 = 0;
                        btn_vsenha
                                .setIcon(new ImageIcon(AdminBuscar.class
                                        .getResource("/img/icon_versenham.png")));
                        btn_vsenha.setToolTipText("Senha Oculta");
                        Font customFont = new Font("Segoe UI", Font.PLAIN, 10);
                        pass_versenha.setFont(customFont);
                    }

                } else {

                }
            }
        });
        if ("nulo".equals(admin)) {
            btn_permchefe.setVisible(true);
        } else {
            btn_permchefe.setVisible(false);
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
        if (admin != null || !"".equals(admin)) {
            if (permchefe == true) {
                btn_vsenha.setEnabled(true);
                btn_vsenha.setToolTipText("Ver Senha de Admin");
            } else {
                btn_vsenha.setEnabled(false);
                btn_vsenha.setToolTipText("Sem Permissão para Ver Senha de Admin");
            }
        }
    }

    public void limpar() {
        textf_buscar.setText("");
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        label_foto.setIcon(null);
        tipo2 = 0;
        textf_copiado.setText("");
        pass_versenha.setText("");
        seluser = "";
        textf_result.setText("0 Resultados Encontrados");
        limfiltro();
        dst = null;
        btn_permchefe.setEnabled(false);
    }

    public void limfiltro() {
        cb_dst.setSelectedItem("Todos os Admins");
        cb_ordem.setSelectedItem("Mais Recentes");

    }

    public void bloqbus() {
        Cb_adm.setSelectedItem(null);
        textf_buscar.setEditable(false);
        textf_buscar.setToolTipText("Selecione a A forma que deseja Pesquisar o Admin.");
    }

    public void setImg(String user) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM admin WHERE usera = ? ";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            if (res.next()) {

                byte[] imageBytes = res.getBytes(5);

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

    public void verificar(String cod) {
        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String bloq;
        String selectedOption = (String) cb_dst.getSelectedItem();
        if (null == selectedOption) {
            bloq = "";
        } else {
            switch (selectedOption) {
                case "Admins Desativados":
                    bloq = "and bloqueioadm=true";
                    break;
                case "Admins Ativos":
                    bloq = "and (bloqueioadm IS NULL OR bloqueioadm = False)";
                    break;
                default:
                    bloq = "";
                    break;
            }
        }

        switch (tipo2) {
            case 1: {
                try {
                    ps = connection.prepareStatement("select * from admin where usera=?" + bloq);
                    ps.setString(1, cod);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "User do Admin não existe";
                        String tit = "Admin inexistente";
                        al.alertinput(tit, "erro", "", msg, "", "erro");

                    } else {
                        alert al = new alert(admin, audio);
                        al.audios("ok");
                        buscarAdmin(cod);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {

                try {
                    ps = connection.prepareStatement("select * from admin where admcod=? " + bloq);
                    ps.setString(1, cod);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "ID do Admin não existe";
                        String tit = "Admin inexistente";
                        al.alertinput(tit, "erro", "", msg, "", "erro");

                    } else {
                        alert al = new alert(admin, audio);
                        al.audios("ok");
                        buscarAdmin(cod);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {

                deletar(cod);

                break;
            }
            case 4: {
                try {
                    ps = connection.prepareStatement("select * from admin where usera=? ");
                    ps.setString(1, cod);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "ID do Admin não existe";
                        String tit = "Admin inexistente";
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

    public void buscarAdmin(String user) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "";
        PreparedStatement stm;
        ResultSet res;
        resul = 0;
        String bloq;

        String selectedOption = (String) cb_dst.getSelectedItem();
        if (null == selectedOption) {
            bloq = "";
        } else {
            switch (selectedOption) {
                case "Admins Desativados":
                    bloq = "and bloqueioadm=true";
                    break;
                case "Admins Ativos":
                    bloq = "and (bloqueioadm IS NULL OR bloqueioadm = False)";
                    break;
                default:
                    bloq = "";
                    break;
            }
        }

        resul = 0;
        switch (tipo2) {
            case 1: {
                sql = "SELECT * FROM admin where usera=? " + bloq;
                try {
                    stm = connection.prepareStatement(sql);
                    stm.setString(1, user); // Define o valor do parâmetro
                    res = stm.executeQuery();

                    // Cria uma matriz bidimensional para armazenar os dados do SELECT
                    Object[][] tabeladm = new Object[1][3]; // Número de colunas é 3

                    while (res.next()) {
                        resul++;
                        tabeladm[0][0] = res.getString(1);
                        tabeladm[0][1] = res.getString(3);
                        tabeladm[0][2] = res.getString(4);

                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"User", "Nome", "Id"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    AdminBuscar.CustomTableModel model = new AdminBuscar.CustomTableModel(tabeladm, columnNames);

                    // Configura o modelo da tabela
                    table.setModel(model);
                    setImg(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            }
            case 2: {

                sql = "SELECT * FROM admin where admcod=? " + bloq;
                try {
                    stm = connection.prepareStatement(sql);
                    stm.setString(1, user); // Define o valor do parâmetro
                    res = stm.executeQuery();

                    // Cria uma matriz bidimensional para armazenar os dados do SELECT
                    Object[][] tabeladm = new Object[1][3]; // Número de colunas é 3

                    while (res.next()) {
                        resul++;
                        tabeladm[0][0] = res.getString(1);
                        tabeladm[0][1] = res.getString(3);
                        tabeladm[0][2] = res.getString(4);

                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"User", "Nome", "Id"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    AdminBuscar.CustomTableModel model = new AdminBuscar.CustomTableModel(tabeladm, columnNames);

                    // Configura o modelo da tabela
                    table.setModel(model);
                    sql = "SELECT * FROM admin WHERE admcod = ? " + bloq;

                    stm.setString(1, user);
                    res = stm.executeQuery();

                    if (res.next()) {
                        String Nick = res.getString(1);

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
        if ("Admins Desativados".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(AdminBuscar.class.getResource("/img/icon_atv.png")));
        } else {
            btn_dst.setIcon(new ImageIcon(AdminBuscar.class.getResource("/img/icon_dest.png")));
        }
    }

    public void bsenha() {

        String sql = "SELECT * FROM admin WHERE usera = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, seluser);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                String tx2 = res.getString(2);

                pass_versenha.setText(tx2);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void busdst(String user) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM admin WHERE usera = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                Boolean tx5 = res.getBoolean(6);//bloq
                if (tx5) {
                    btn_dst.setIcon(new ImageIcon(AdminBuscar.class.getResource("/img/icon_atv.png")));
                    dst = false;
                } else {
                    btn_dst.setIcon(new ImageIcon(AdminBuscar.class.getResource("/img/icon_dest.png")));
                    dst = true;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void busadm(String user) {

        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM admin WHERE usera = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                Boolean tx5 = res.getBoolean(7);//chefe

                permchefesel = tx5;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void BPersAdmin(String user) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "";
        PreparedStatement stm;
        ResultSet res;
        resul = 0;
        String bloq, ord;

        String selectedOption = (String) cb_dst.getSelectedItem();
        if (null == selectedOption) {
            bloq = "";
        } else {
            switch (selectedOption) {
                case "Admins Desativados":
                    bloq = "and bloqueioadm=true";
                    break;
                case "Admins Ativos":
                    bloq = "and (bloqueioadm IS NULL OR bloqueioadm = False)";
                    break;
                default:
                    bloq = "";
                    break;
            }
        }

        selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = "ORDER BY admcod DESC;";
        } else {
            ord = "ORDER BY admcod ASC;";
        }

        resul = 0;
        switch (tipo2) {
            case 1: {

                sql = "SELECT * FROM admin where usera like ? " + bloq + " " + ord;
                try {
                    stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {

                        while (result) {
                            resul++;
                            Object[] row = new Object[3];
                            row[0] = res.getString(1); // Nome
                            row[1] = res.getString(3); // Descrição
                            row[2] = res.getString(4);
                            data.add(row);
                            result = res.next();
                        }
                    }
                    // Define os nomes das colunas
                    Object[] columnNames = {"User", "Nome", "Id"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    table.setModel(model);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            }
            case 2: {

                sql = "SELECT * FROM admin where admcod like ? " + bloq + " " + ord;
                try {
                    stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    res = stm.executeQuery();

                    // Cria uma matriz bidimensional para armazenar os dados do SELECT
                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[3];
                            row[0] = res.getString(1); // Nome
                            row[1] = res.getString(3); // Descrição
                            row[2] = res.getString(4);
                            data.add(row);
                            result = res.next();
                        }
                    }
                    // Define os nomes das colunas
                    Object[] columnNames = {"User", "Nome", "Id"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

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
        if ("Admins Desativados".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(AdminBuscar.class.getResource("/img/icon_atv.png")));
        } else {
            btn_dst.setIcon(new ImageIcon(AdminBuscar.class.getResource("/img/icon_dest.png")));
        }
    }

    public void buscarTodosAdmin() {
        resul = 0;
        String bloq, ord;

        String selectedOption = (String) cb_dst.getSelectedItem();
        if (null == selectedOption) {
            bloq = "";
        } else {
            switch (selectedOption) {
                case "Admins Desativados":
                    bloq = "WHERE bloqueioadm=true";
                    break;
                case "Admins Ativos":
                    bloq = "WHERE (bloqueioadm IS NULL OR bloqueioadm = False)";
                    break;
                default:
                    bloq = "";
                    break;
            }
        }

        selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = "ORDER BY admcod DESC;";
        } else {
            ord = "ORDER BY admcod ASC;";
        }

        resul = 0;
        tipo2 = 0;
        this.connection = new ConnectionFactory().getConnection();

        String sql = "SELECT * FROM admin " + bloq + " " + ord;
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
                    Object[] row = new Object[3];
                    row[0] = res.getString(1); // Nome
                    row[1] = res.getString(3); // Descrição
                    row[2] = res.getString(4);
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
            Object[] columnNames = {"User", "Nome", "Id"};

            // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
            CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

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
        if ("Admins Desativados".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(AdminBuscar.class.getResource("/img/icon_atv.png")));
        } else {
            btn_dst.setIcon(new ImageIcon(AdminBuscar.class.getResource("/img/icon_dest.png")));
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

    public void deletar(String cod) {
        this.connection = new ConnectionFactory().getConnection();

        try {
            // Verificar se há dados atrelados ao admin na tabela pet
            PreparedStatement petStatement = connection.prepareStatement("SELECT petcod FROM pet WHERE admincodadmn = (SELECT admcod FROM admin WHERE usera = ?)");
            petStatement.setString(1, cod);
            ResultSet petResult = petStatement.executeQuery();
            if (petResult.next()) {
                // Existem dados atrelados ao admin na tabela pet, então substitua a chave por um valor nulo
                PreparedStatement nullPetStatement = connection.prepareStatement("UPDATE pet SET admincodadmn = '10' WHERE admincodadmn IN (SELECT admcod FROM admin WHERE usera = ?);");
                nullPetStatement.setString(1, cod);
                nullPetStatement.executeUpdate();
            }

            // Verificar se há dados atrelados ao admin na tabela servico
            PreparedStatement servicoStatement = connection.prepareStatement("SELECT servcod FROM servico WHERE admin_codadmn = (SELECT admcod FROM admin WHERE usera = ?)");
            servicoStatement.setString(1, cod);
            ResultSet servicoResult = servicoStatement.executeQuery();
            if (servicoResult.next()) {
                // Existem dados atrelados ao admin na tabela servico, então substitua a chave por um valor nulo
                PreparedStatement nullServicoStatement = connection.prepareStatement("UPDATE servico SET admin_codadmn = '10' WHERE admin_codadmn IN (SELECT admcod FROM admin WHERE usera = ?)");
                nullServicoStatement.setString(1, cod);
                nullServicoStatement.executeUpdate();
            }

            // Deletar admin
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM admin WHERE usera = ?");
            deleteStatement.setString(1, cod);
            int i = deleteStatement.executeUpdate();

            if (i != 0) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Admin " + cod;
                String msg2 = "deletado do database";
                String tit = "Deleção de Admin";
                al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                buscarTodosAdmin();
            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Erro ao deletar";
                String tit = "Deleção de Admin";
                al.alertinput(tit, "erro", "", msg, "", "erro");
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) {
                // A exceção de violação de chave estrangeira ocorreu, trate-a conforme necessário

                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Erro ao deletar: Existem dados";
                String msg2 = "relacionados ao admin na";
                String msg3 = "tabela pet ou servico";
                String tit = "Deleção de Admin";
                al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
            } else {
                // Outro erro SQL ocorreu, imprima o stack trace para depuração
                e.printStackTrace();
            }
        }
    }

    public void desativar(String cod) {
        this.connection = new ConnectionFactory().getConnection();

        String selectedOption = (String) cb_dst.getSelectedItem();
        //Connection con;
        PreparedStatement ps;
        String status = "";
        if (dst == false) {
            try {

                ps = connection.prepareStatement("update admin set bloqueioadm=? where usera=?");

                ps.setBoolean(1, false);
                ps.setString(2, cod);

                int i = ps.executeUpdate();
                if (i != 0) {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Admin";
                    String msg2 = "atualizado com sucesso";
                    String tit = "Atualização de Admin";
                    al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                    buscarTodosAdmin();
                } else {
                    alert al = new alert(admin, audio);
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

            try {

                ps = connection.prepareStatement("update admin set bloqueioadm=? where usera=?");

                ps.setBoolean(1, true);
                ps.setString(2, cod);

                int i = ps.executeUpdate();
                if (i != 0) {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Admin";
                    String msg2 = "atualizado com sucesso";
                    String tit = "Atualização de Admin";
                    al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                    buscarTodosAdmin();
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Admin";
                    String msg2 = "não atualizados";
                    String tit = "Atualização de Admin";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PFundo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        check_buperso = new javax.swing.JCheckBox();
        Cb_adm = new javax.swing.JComboBox<>();
        Latt = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textf_buscar = new javax.swing.JTextField();
        Lbuscar = new javax.swing.JLabel();
        btn_tirarsel = new SwingPerson.JbuttonArr();
        btn_limpar = new SwingPerson.JbuttonArr();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btn_cad = new javax.swing.JButton();
        btn_att = new javax.swing.JButton();
        label_selecionado = new javax.swing.JLabel();
        textf_copiado = new javax.swing.JTextField();
        label_copy = new javax.swing.JLabel();
        textf_result = new javax.swing.JLabel();
        btn_del = new SwingPerson.JbuttonArr();
        btn_dst = new SwingPerson.JbuttonArr();
        cb_ordem = new javax.swing.JComboBox<>();
        btn_vsenha = new SwingPerson.JbuttonArr();
        label_vsenha = new javax.swing.JLabel();
        pass_versenha = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        label_foto = new javax.swing.JLabel();
        cb_dst = new javax.swing.JComboBox<>();
        btn_lmpfil = new SwingPerson.JbuttonArr();
        btn_filpet = new SwingPerson.JbuttonArr();
        btn_permchefe = new SwingPerson.JbuttonArr();

        setBackground(new java.awt.Color(0, 0, 0));
        setBorder(null);
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1284, 666));

        PFundo.setBackground(new java.awt.Color(64, 33, 7));
        PFundo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PFundo.setPreferredSize(new java.awt.Dimension(1284, 666));
        PFundo.setRequestFocusEnabled(false);
        PFundo.setVerifyInputWhenFocusTarget(false);

        jPanel1.setBackground(new java.awt.Color(255, 253, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(397, 200));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Buscar Admin");

        check_buperso.setBackground(new java.awt.Color(255, 253, 243));
        check_buperso.setText("Busca Personalizada");
        check_buperso.setToolTipText("Pesquise conforme você escreve");
        check_buperso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Cb_adm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código", "User" }));
        Cb_adm.setSelectedIndex(-1);
        Cb_adm.setToolTipText("Selecione Por qual Informação Quer Pesquisar");
        Cb_adm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Cb_adm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cb_admActionPerformed(evt);
            }
        });

        Latt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_att.png"))); // NOI18N
        Latt.setToolTipText("Busque Todos e Atualize o Banco de Dados");
        Latt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Latt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LattMouseClicked(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(363, 3));

        textf_buscar.setEditable(false);
        textf_buscar.setBackground(new java.awt.Color(255, 253, 243));
        textf_buscar.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_buscar.setToolTipText("Selecione a forma que deseja Pesquisar o Admin");
        textf_buscar.setBorder(null);
        textf_buscar.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textf_buscarMouseClicked(evt);
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

        Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        Lbuscar.setToolTipText("Buscar Admin");
        Lbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Lbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LbuscarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LbuscarMouseEntered(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Cb_adm, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Latt))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(check_buperso)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_tirarsel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(textf_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Lbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Cb_adm, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Latt, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Lbuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_buperso)
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_tirarsel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_limpar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        table.setBackground(new java.awt.Color(204, 204, 204));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User", "Nome", "Id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table.setGridColor(new java.awt.Color(255, 253, 243));
        jScrollPane1.setViewportView(table);

        btn_cad.setBackground(new java.awt.Color(64, 33, 7));
        btn_cad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_cad.setForeground(new java.awt.Color(255, 255, 255));
        btn_cad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_cadbr.png"))); // NOI18N
        btn_cad.setToolTipText("Cadastrar Admin");
        btn_cad.setBorder(null);
        btn_cad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        btn_att.setToolTipText("Atualizar Admin Selecionado");
        btn_att.setBorder(null);
        btn_att.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_att.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_att.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_attActionPerformed(evt);
            }
        });

        label_selecionado.setBackground(new java.awt.Color(64, 33, 7));
        label_selecionado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_selecionado.setForeground(new java.awt.Color(255, 253, 243));
        label_selecionado.setText("Valor Selecionado");

        textf_copiado.setEditable(false);
        textf_copiado.setBackground(new java.awt.Color(204, 204, 204));
        textf_copiado.setToolTipText("Campo Selecionado na Tabela");
        textf_copiado.setBorder(null);
        textf_copiado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        textf_copiado.setMinimumSize(new java.awt.Dimension(500, 20));
        textf_copiado.setPreferredSize(new java.awt.Dimension(500, 20));

        label_copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_copy.png"))); // NOI18N
        label_copy.setToolTipText("Copiar Informação no Campo de Seleção");
        label_copy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_copy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_copyMouseClicked(evt);
            }
        });

        textf_result.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        textf_result.setForeground(new java.awt.Color(255, 253, 243));
        textf_result.setText("x Resultados Encontrados");
        textf_result.setToolTipText("Resultados Encontrados na Tabela de Admin");
        textf_result.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn_del.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/deletarm.png"))); // NOI18N
        btn_del.setToolTipText("Deletar Admin Selecionado");
        btn_del.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delActionPerformed(evt);
            }
        });

        btn_dst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_dest.png"))); // NOI18N
        btn_dst.setToolTipText("Desativar/Ativar Admin Selecionado");
        btn_dst.setPreferredSize(new java.awt.Dimension(35, 35));
        btn_dst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dstActionPerformed(evt);
            }
        });

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

        btn_vsenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_versenham.png"))); // NOI18N
        btn_vsenha.setToolTipText("Sem Permissão para Ver Senha de Admin");
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

        pass_versenha.setEditable(false);
        pass_versenha.setBackground(new java.awt.Color(204, 204, 204));
        pass_versenha.setToolTipText("Senha do Admin Selecionado");
        pass_versenha.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pass_versenha.setMinimumSize(new java.awt.Dimension(64, 27));
        pass_versenha.setPreferredSize(new java.awt.Dimension(64, 27));
        pass_versenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pass_versenhaActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 253, 243));

        label_foto.setBackground(new java.awt.Color(255, 253, 243));
        label_foto.setForeground(new java.awt.Color(255, 253, 243));
        label_foto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_foto.setToolTipText("Imagem do Admin Selecionado");
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
            .addComponent(label_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        cb_dst.setBackground(new java.awt.Color(255, 253, 243));
        cb_dst.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos os Admins", "Admins Ativos", "Admins Desativados" }));
        cb_dst.setToolTipText("Buscar Admin Desativados/Ativados");
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

        btn_filpet.setText("Filtrar Admin");
        btn_filpet.setToolTipText("Procurar Admin com os filtros");
        btn_filpet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filpetActionPerformed(evt);
            }
        });

        btn_permchefe.setText("Dar Permissão de Chefe");
        btn_permchefe.setToolTipText("Permitir que um Admin faça mais funcionalidades");
        btn_permchefe.setEnabled(false);
        btn_permchefe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_permchefeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PFundoLayout = new javax.swing.GroupLayout(PFundo);
        PFundo.setLayout(PFundoLayout);
        PFundoLayout.setHorizontalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PFundoLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addComponent(textf_copiado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_copy)
                                .addGap(12, 12, 12))
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PFundoLayout.createSequentialGroup()
                                        .addComponent(cb_ordem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cb_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_lmpfil, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_filpet, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label_selecionado)
                                    .addGroup(PFundoLayout.createSequentialGroup()
                                        .addComponent(pass_versenha, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_vsenha, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label_vsenha))
                                .addGap(0, 32, Short.MAX_VALUE))))
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textf_result, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                                        .addComponent(btn_cad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)
                                        .addComponent(btn_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_del, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addComponent(btn_permchefe, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(11, 11, 11))))
        );
        PFundoLayout.setVerticalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addComponent(label_selecionado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label_copy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textf_copiado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_vsenha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pass_versenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_vsenha, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_ordem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_lmpfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_filpet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addComponent(textf_result)
                        .addGap(18, 18, 18)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_cad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_del, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btn_permchefe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PFundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PFundo, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void lmp() {
        audios("cl");
        textf_copiado.setText("");
        limpar();
    }

    public void bus() {

        if (!check_buperso.isSelected()) {
            String selectedItem = Cb_adm.getSelectedItem().toString();

            switch (selectedItem) {
                case "Código":
                    tipo2 = 2;
                    String cod = textf_buscar.getText();
                    verificar(cod);
                    break;
                case "User":
                    tipo2 = 1;
                    String user = textf_buscar.getText();
                    verificar(user);
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

    public void bper(int a) {
        String user = textf_buscar.getText();

        if (check_buperso.isSelected()) {
            switch (a) {
                case 2:
                    tipo2 = 1;
                    if (!user.isEmpty()) {
                        BPersAdmin(user);
                    }
                    break;
                case 1:
                    tipo2 = 2;
                    String cod = textf_buscar.getText();
                    if (!cod.isEmpty()) {
                        BPersAdmin(cod);
                    }
                    break;
                default:
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Mais de um ou nenhum";
                    String msg2 = "campo de texto com valores";
                    String tit = "Preencha somente um dos campos";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                    break;
            }
        }
    }

    public void tsl() {
        if (tipo2 == 0) {
            table.clearSelection();
            textf_copiado.setText("");
            pass_versenha.setText("");
            label_foto.setIcon(null);
            seluser = "";
        } else {
            table.clearSelection();
            textf_copiado.setText("");
            pass_versenha.setText("");
            seluser = "";

        }
        dst = null;
        btn_permchefe.setEnabled(false);
    }

    public void att_banco() {
        limpar();
        buscarTodosAdmin();
        alert al = new alert(admin, audio);
        al.setVisible(true);
        String msg = "Banco Atualizado";
        String tit = "Atualização";
        al.alertinput(tit, "ok", "", msg, "", "sucesso");
    }

    public void att() {
        if (permchefe == true || seluser.equals(admin)) {
            if ("nulo".equals(admin) || !"nulo".equals(seluser)) {
                if ("null".equals(admin) || !"null".equals(seluser)) {
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

                        if (seluser == null || "".equals(seluser)) {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Selecione na Tabela";
                            String msg2 = "quem deseja Atualizar";
                            String tit = "Nenhum Admin selecionado";
                            al.alertinput(tit, "info", msg, msg2, "", "info");

                        } else {

                            if (seluser.equals(admin)) {
                                alert al = new alert(admin, audio);
                                al.setVisible(true);
                                String msg = "Utilize a Página Home";
                                String msg2 = "Para mudar suas";
                                String msg3 = "Informações pessoais.";
                                String tit = "Selecionou Você mesmo";
                                al.alertinput(tit, "info", msg, msg2, msg3, "info");
                            } else {
                                audios("tc");
                                AdminAtualizar adm = new AdminAtualizar(admin, audio, seluser, null);
                                adm.setVisible(true);
                            }

                        }
                    }
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String tit = "Erro";
                    String msg = "Impossível Alterar o reserva";
                    al.alertinput(tit, "erro", "", msg, "", "erro");

                }
            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String tit = "Erro";
                String msg = "Impossível Alterar o CHEFE";
                al.alertinput(tit, "erro", "", msg, "", "erro");

            }
        } else if (!seluser.equals(admin)) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Impossível Alterar";
            String msg2 = "Outro Membro";
            String tit = "Erro";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");
        } else {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Você não tem ";
            String msg3 = "permissão suficiente";
            String tit = "Sem Permissão";
            al.alertinput(tit, "erro", msg, msg3, "", "erro");
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
            if (permchefe == true) {
                audios("tc");
                AdminCadastrar adm = new AdminCadastrar(admin, audio);
                adm.setVisible(true);
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

    public void del() {
        if ("nulo".equals(seluser)) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String tit = "Erro";
            String msg = "Impossível Deletar o CHEFE";
            al.alertinput(tit, "erro", "", msg, "", "erro");

        } else if ("null".equals(seluser)) {

            alert al = new alert(admin, audio);
            al.setVisible(true);
            String tit = "Erro";
            String msg = "Impossível Deletar o reserva";
            al.alertinput(tit, "erro", "", msg, "", "erro");

        } else if (seluser == null ? admin == null : seluser.equals(admin)) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Impossível Deletar";
            String msg2 = "Você Mesmo(a)";
            String tit = "Erro";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");

        } else {
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
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Selecione na Tabela";
                        String msg2 = "quem deseja deletar";
                        String tit = "Nenhum Admin selecionado";
                        al.alertinput(tit, "info", msg, msg2, "", "info");

                    } else {
                        audios("aviso");
                        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Deletar o Admin " + seluser + "?\nUma vez deletado, essas informações sumirão do banco de dados");

                        if (escolha == 0) {
                            tipo2 = 3;
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
    }

    public void dst() {
        if ("nulo".equals(seluser)) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String tit = "Erro";
            String msg = "Impossível Desativar o CHEFE";
            al.alertinput(tit, "erro", "", msg, "", "erro");

        } else if ("null".equals(seluser)) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String tit = "Erro";
            String msg = "Impossível Desativar o reserva";
            al.alertinput(tit, "erro", "", msg, "", "erro");
        } else if (seluser == null ? admin == null : seluser.equals(admin)) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Impossível Desativar/Ativar";
            String msg2 = "Você Mesmo(a)";
            String tit = "Erro";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");

        } else {
            if (admin == null || admin.equals("")) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Você deve estar logado";
                String msg2 = "Para Efetuar isso";
                String tit = "Não Logado";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            } else {

                if (permchefe == true) {
                    if (seluser == null) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Selecione na Tabela";
                        String msg2 = "quem deseja Desativar";
                        String tit = "Nenhum Admin selecionado";
                        al.alertinput(tit, "info", msg, msg2, "", "info");

                    } else {

                        if (dst == false) {
                            audios("aviso");
                            int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Ativar o Admin " + seluser + "?");
                            if (escolha == 0) {
                                tipo2 = 4;
                                verificar(seluser);
                            }
                        } else {
                            audios("aviso");
                            int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Desativar o Admin " + seluser + "?");
                            if (escolha == 0) {
                                tipo2 = 4;
                                verificar(seluser);
                            }
                        }
                    }
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

    public void versenha() {
        if ("".equals(seluser) || seluser == null) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Selecione o Admin";
            String msg3 = "Para Ver a senha";
            String tit = "Sem seleção de Admin";
            al.alertinput(tit, "erro", msg, msg3, "", "erro");
            pass_versenha.setEchoChar('\u25CF');
            cont2 = 0;
            btn_vsenha
                    .setIcon(new ImageIcon(AdminBuscar.class
                            .getResource("/img/icon_versenham.png")));
            btn_vsenha.setToolTipText("Senha Oculta");
            Font customFont = new Font("Segoe UI", Font.PLAIN, 10);
            pass_versenha.setFont(customFont);
        } else {
            if ("nulo".equals(seluser) || "null".equals(seluser)) {

                if ("nulo".equals(admin) || "null".equals(admin)) {
                    audios("cl");
                    if (cont2 == 0) {
                        pass_versenha.setEchoChar((char) 0);
                        cont2 = 1;
                        btn_vsenha
                                .setIcon(new ImageIcon(AdminBuscar.class
                                        .getResource("/img/icon_desversenham.png")));
                        btn_vsenha.setToolTipText("Senha Visível");
                        Font customFont = new Font("Segoe UI", Font.PLAIN, 12);
                        pass_versenha.setFont(customFont);
                    } else {
                        pass_versenha.setEchoChar('\u25CF');
                        cont2 = 0;
                        btn_vsenha
                                .setIcon(new ImageIcon(AdminBuscar.class
                                        .getResource("/img/icon_versenham.png")));
                        btn_vsenha.setToolTipText("Senha Oculta");
                        Font customFont = new Font("Segoe UI", Font.PLAIN, 10);
                        pass_versenha.setFont(customFont);
                    }
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Você não tem ";
                    String msg3 = "permissão suficiente";
                    String tit = "Sem Permissão";
                    al.alertinput(tit, "erro", msg, msg3, "", "erro");

                    pass_versenha.setEchoChar('\u25CF');
                    cont2 = 0;
                    btn_vsenha
                            .setIcon(new ImageIcon(AdminBuscar.class
                                    .getResource("/img/icon_versenham.png")));
                    btn_vsenha.setToolTipText("Senha Oculta");
                    Font customFont = new Font("Segoe UI", Font.PLAIN, 10);
                    pass_versenha.setFont(customFont);
                }

            } else {
                audios("cl");
                if (cont2 == 0) {
                    pass_versenha.setEchoChar((char) 0);
                    cont2 = 1;
                    btn_vsenha
                            .setIcon(new ImageIcon(AdminBuscar.class
                                    .getResource("/img/icon_desversenham.png")));
                    btn_vsenha.setToolTipText("Senha Visível");
                    Font customFont = new Font("Segoe UI", Font.PLAIN, 12);
                    pass_versenha.setFont(customFont);
                } else {
                    pass_versenha.setEchoChar('\u25CF');
                    cont2 = 0;
                    btn_vsenha
                            .setIcon(new ImageIcon(AdminBuscar.class
                                    .getResource("/img/icon_versenham.png")));
                    btn_vsenha.setToolTipText("Senha Oculta");
                    Font customFont = new Font("Segoe UI", Font.PLAIN, 10);
                    pass_versenha.setFont(customFont);
                }
            }
        }

    }

    private void Cb_admActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cb_admActionPerformed
        if (Cb_adm.getSelectedItem() != null) {
            textf_buscar.setEditable(true);
            textf_buscar.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            textf_buscar.setToolTipText("Escreva por quem quer Procurar.");
            Lbuscar.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            textf_buscar.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        } else {
            textf_buscar.setEditable(false);
            textf_buscar.setToolTipText("Selecione a A forma que deseja Pesquisar o Admin.");
            Lbuscar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            textf_buscar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            //textf_buscar.setCursor(lockedCursor);
        }

    }//GEN-LAST:event_Cb_admActionPerformed

    private void LattMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LattMouseClicked
        att_banco();
    }//GEN-LAST:event_LattMouseClicked

    private void textf_buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_buscarMouseClicked
        if (textf_buscar.isEditable()) {
            if (textf_buscar.isEditable()) {
                limpar(); // Chama o método "limpar" se o campo estiver editável
            }
        }
    }//GEN-LAST:event_textf_buscarMouseClicked

    private void textf_buscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_buscarKeyPressed
        if (textf_buscar.isEditable()) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                bus();
            }
        }
    }//GEN-LAST:event_textf_buscarKeyPressed

    private void textf_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_buscarKeyReleased
        if (textf_buscar.isEditable()) {
            String selectedItem = Cb_adm.getSelectedItem().toString();

            if (selectedItem.equals("Código")) {
                bper(1);
            } else if (selectedItem.equals("User")) {
                bper(2);
            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "";
                String msg2 = "Nenhuma Opção Selecionada";
                String tit = "";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            }
        }
    }//GEN-LAST:event_textf_buscarKeyReleased

    private void LbuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LbuscarMouseClicked
        if (Cb_adm.getSelectedItem() != null) {
            bus();
            Lbuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            Lbuscar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }//GEN-LAST:event_LbuscarMouseClicked

    private void btn_tirarselActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tirarselActionPerformed
        audios("cl");
        tsl();
    }//GEN-LAST:event_btn_tirarselActionPerformed

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        audios("cl");
        bloqbus();
        lmp();
    }//GEN-LAST:event_btn_limparActionPerformed

    private void btn_cadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadActionPerformed
        tsl();
        cad();
    }//GEN-LAST:event_btn_cadActionPerformed

    private void btn_attActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_attActionPerformed
      att();
       tsl();
    }//GEN-LAST:event_btn_attActionPerformed

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

    private void btn_delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delActionPerformed
        del();
        tsl();
    }//GEN-LAST:event_btn_delActionPerformed

    private void btn_dstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dstActionPerformed
        dst();
        tsl();
    }//GEN-LAST:event_btn_dstActionPerformed

    private void cb_ordemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ordemActionPerformed

    }//GEN-LAST:event_cb_ordemActionPerformed

    private void btn_vsenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vsenhaActionPerformed
        versenha();
    }//GEN-LAST:event_btn_vsenhaActionPerformed

    private void pass_versenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pass_versenhaActionPerformed
        String selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            cb_ordem.setToolTipText("Buscar Admin Mais Recentes");
        } else {
            cb_ordem.setToolTipText("Buscar Admin Mais Antigos");
        }
    }//GEN-LAST:event_pass_versenhaActionPerformed

    private void btn_lmpfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lmpfilActionPerformed
        audios("cl");
        limfiltro();
        tsl();
    }//GEN-LAST:event_btn_lmpfilActionPerformed

    private void btn_filpetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filpetActionPerformed
        btn_permchefe.setEnabled(false);
        dst = null;
        buscarTodosAdmin();
        tsl();

    }//GEN-LAST:event_btn_filpetActionPerformed

    private void btn_permchefeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_permchefeActionPerformed
        if ("nulo".equals(seluser)) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String tit = "Erro";
            String msg = "Impossível Tirar a";
            String msg1 = "permissão de chefe do";
            String msg2 = "CHEFE";
            al.alertinput(tit, "erro", msg, msg1, msg2, "erro");

        } else if ("null".equals(seluser)) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String tit = "Erro";
            String msg = "Impossível Tirar a";
            String msg1 = "permissão de chefe do";
            String msg2 = "Reserva";
            al.alertinput(tit, "erro", msg, msg1, msg2, "erro");
        } else {
            this.connection = new ConnectionFactory().getConnection();
            PreparedStatement ps;
            if ("Dar Permissão de Chefe".equals(btn_permchefe.getText())) {
                try {
                    ps = connection.prepareStatement("update admin set chefe=? where usera=?");
                    ps.setBoolean(1, true);
                    ps.setString(2, seluser);
                    int i = ps.executeUpdate();
                    if (i != 0) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Admin";
                        String msg2 = "atualizados com sucesso";
                        String tit = "Atualização de Admin";
                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                        buscarTodosAdmin();
                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes Admin não atualizados";
                        String tit = "Atualização de Admin";
                        al.alertinput(tit, "erro", "", msg, "", "erro");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ps = connection.prepareStatement("update admin set chefe=? where usera=?");
                    ps.setBoolean(1, false);
                    ps.setString(2, seluser);
                    int i = ps.executeUpdate();
                    if (i != 0) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Admin";
                        String msg2 = "atualizados com sucesso";
                        String tit = "Atualização de Admin";
                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                        buscarTodosAdmin();
                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes Admin não atualizados";
                        String tit = "Atualização de Admin";
                        al.alertinput(tit, "erro", "", msg, "", "erro");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        tsl();
    }//GEN-LAST:event_btn_permchefeActionPerformed

    private void LbuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LbuscarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_LbuscarMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Cb_adm;
    private javax.swing.JLabel Latt;
    private javax.swing.JLabel Lbuscar;
    private javax.swing.JPanel PFundo;
    private javax.swing.JButton btn_att;
    private javax.swing.JButton btn_cad;
    private SwingPerson.JbuttonArr btn_del;
    private SwingPerson.JbuttonArr btn_dst;
    private SwingPerson.JbuttonArr btn_filpet;
    private SwingPerson.JbuttonArr btn_limpar;
    private SwingPerson.JbuttonArr btn_lmpfil;
    private SwingPerson.JbuttonArr btn_permchefe;
    private SwingPerson.JbuttonArr btn_tirarsel;
    private SwingPerson.JbuttonArr btn_vsenha;
    private javax.swing.JComboBox<String> cb_dst;
    private javax.swing.JComboBox<String> cb_ordem;
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
    private javax.swing.JTable table;
    private javax.swing.JTextField textf_buscar;
    private javax.swing.JTextField textf_copiado;
    private javax.swing.JLabel textf_result;
    // End of variables declaration//GEN-END:variables
}
