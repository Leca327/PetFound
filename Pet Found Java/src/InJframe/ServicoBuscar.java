package InJframe;

import javax.swing.plaf.basic.BasicInternalFrameUI;
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
import subGUI.ServicoAtualizar;
import subGUI.ServicoCadastrar;
import subGUI.ServicoReprovar;

public class ServicoBuscar extends javax.swing.JInternalFrame {

    private Connection connection;

    String selcod;
    public String admin, apv, codadm, audio, pf;
    int tipo2, resul, tipoveri;
    int cont = 0;
    Boolean permchefe, dst;

    public ServicoBuscar(String adm, String au, String p) {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        //
        tmslider();
        admin = adm;
        audio = au;
        L_mt.setVisible(false);
        textarea_mt.setVisible(false);
        textarea_mt.setPreferredSize(new java.awt.Dimension(0, 0));
        pf = p;
        if (!"".equals(pf) && pf != null) {
            tipo2 = 1;
            buscarpettdesp(pf);

        } else {
            buscarTodospet();
        }

        if (admin != null || admin != "") {
            bus(admin);
        }

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.getSelectedRow();
                int column = jTable1.getSelectedColumn();

                audios("cl");

                Object value = jTable1.getValueAt(row, column);
                String campoSelecionado;
                if (value != null && !value.toString().isEmpty()) {
                    campoSelecionado = value.toString();
                    textf_copiado.setText(campoSelecionado);

                } else {
                    campoSelecionado = "";
                    textf_copiado.setText("");
                }

                // Verifica se o campo em branco é da coluna 15 (índice 14)
                if (jTable1.getValueAt(jTable1.getSelectedRow(), 6) == null) {
                    apv = null;
                } else {
                    apv = "achou";
                }
                selcod = jTable1.getValueAt(row, 3).toString();
                setImg(selcod);
                Buscaatt(selcod);
                busdst(selcod);
                if (selcod != null) {
                    radio_apv.setEnabled(true);
                    radio_rep.setEnabled(true);
                    radio_nulo.setEnabled(true);
                }
            }
        });

    }

    public void tmslider() {
        try {
            this.connection = new ConnectionFactory().getConnection();

            // Consulta SQL para obter o maior preço da tabela "servico"
            String sql = "SELECT MAX(preco) AS maior_preco FROM servico";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            double maiorPreco = 0;

            if (resultSet.next()) {
                maiorPreco = resultSet.getDouble("maior_preco");
            }

            // Agora, você pode usar o valor de "maiorPreco" para configurar o tamanho do slider
            // Suponha que você tenha um objeto Slider chamado "slider"
            // Defina o valor máximo do slider como o maior preço
            slider_preco.setMaximum((int) maiorPreco);
            slider_preco.setValue((int) maiorPreco);
            // Feche a conexão com o banco de dados
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void busdst(String user) {

        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM servico WHERE servcod = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                Boolean tx5 = res.getBoolean(13);//bloq
                if (tx5) {
                    btn_dst.setIcon(new ImageIcon(ServicoBuscar.class.getResource("/img/icon_atv.png")));
                    dst = false;
                } else {
                    btn_dst.setIcon(new ImageIcon(ServicoBuscar.class.getResource("/img/icon_dest.png")));
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
                codadm = tx4;
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
        textf_buscar.setText("");
        textf_buscar.setText("");
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        label_foto.setIcon(null);
        tipo2 = 0;
        textf_copiado.setText("");
        selcod = "";
        radio_apv.setEnabled(false);
        radio_rep.setEnabled(false);
        radio_nulo.setEnabled(false);
        groupatt.clearSelection();
        textf_result.setText("0 Resultados Encontrados");
        limfiltro();
        dst = null;
        L_mt.setVisible(false);
        textarea_mt.setVisible(false);
        tmslider();
    }

    public void limfiltro() {
        cb_anunc.setSelectedItem("Todos Tipos de Aprovações");
        cb_dst.setSelectedItem("Todos os Tipos de Serviços At/Dst");
        cb_ordem.setSelectedItem("Mais Recentes");
        slider_preco.setValue(2000);
        textf_preco.setText("00000000");
    }

    public static String formatpreco(String input) {
        // Remova todos os caracteres que não sejam números, vírgulas ou pontos.
        input = input.replaceAll("[^0-9,.]", "");

        // Substitua a vírgula por um ponto.
        input = input.replace(",", ".");

        // Remova os zeros à esquerda até encontrar o primeiro número diferente de zero ou até encontrar um ponto.
        int index = 0;
        while (index < input.length() && (input.charAt(index) == '0' || input.charAt(index) == '.')) {
            index++;
        }

        // Se o primeiro número diferente de zero for após um ponto, adicione um zero à esquerda.
        if (index < input.length() && input.charAt(index) == '.') {
            input = "0" + input;
        }

        // Retorne o valor formatado.
        return input;
    }

    public void delet(String cod) {
        PreparedStatement ps;
        String status;

        try {
            // Excluir contatopet relacionados à pessoa física
            ps = connection.prepareStatement("DELETE FROM contatoserv WHERE scodserv =?;");
            ps.setString(1, cod);
            int i = ps.executeUpdate();

            // Excluir imagem relacionada à pessoa física
            ps = connection.prepareStatement("DELETE FROM imagem WHERE servicocodserv =?;");
            ps.setString(1, cod);
            i = ps.executeUpdate();

            // Excluir pets relacionados à pessoa física
            ps = connection.prepareStatement("DELETE FROM servico WHERE servcod = ?;");
            ps.setString(1, cod);
            i = ps.executeUpdate();

            if (i != 0) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Serviço " + selcod;
                String msg2 = "deletado do database";
                String tit = "Deleção de Serviço";
                al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                buscarTodospet();
            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Erro ao deletar.";
                String msg2 = "Serviço inexistente";
                String tit = "Deleção de Serviço";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            }
        } catch (SQLException e) {
            // Tratar exceção
            e.printStackTrace();
        }
    }

    public String verificar(String cod) {

        PreparedStatement ps;
        String status = "";
        switch (tipoveri) {
            case 2: {

                this.connection = new ConnectionFactory().getConnection();

                status = "";
                try {
                    String selectedOption = (String) Cb_pet.getSelectedItem();
                    switch (selectedOption) {
                        case "Código de Serviço":
                            ps = connection.prepareStatement("select * from servico where servcod=?;");
                            break;
                        case "Código de Pessoa":
                            ps = connection.prepareStatement("select * from servico where pessoa_codp=?;");
                            break;
                        default:
                            ps = connection.prepareStatement("select * from servico where admin_codadmn=?;");
                            break;
                    }

                    ps.setString(1, cod);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Valor inexistente no banco";
                        String tit = "Serviço inexistente";
                        al.alertinput(tit, "erro", "", msg, "", "erro");

                    } else {
                        alert al = new alert(admin, audio);
                        al.audios("ok");
                        buscarPet(cod);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }

            case 3: {
                delet(cod);
                break;
            }
            case 4: {
                this.connection = new ConnectionFactory().getConnection();

                status = "";
                try {
                    ps = connection.prepareStatement("select * from servico where servcod=? ");
                    ps.setString(1, cod);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "ID do Serviço não existe";
                        String tit = "Serviço inexistente";
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
        return status;
    }

    public void setImg(String user) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM imagem WHERE servicocodserv = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                // Registro encontrado, obtém a imagem
                byte[] imageBytes = res.getBytes(2);

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
            } else {
                // Registro não encontrado, define a imagem padrão
                label_foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/semimg.png")));
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

    public void desativar(String cod) {

        String selectedOption = (String) cb_anunc.getSelectedItem();
        if (dst == false) {
            this.connection = new ConnectionFactory().getConnection();

            //Connection con;
            PreparedStatement ps;
            String status = "";
            try {

                ps = connection.prepareStatement("update servico set bloqueioserv=? where servcod=?");

                ps.setBoolean(1, false);
                ps.setString(2, cod);

                int i = ps.executeUpdate();
                if (i != 0) {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Serviço";
                    String msg2 = "atualizado com sucesso";
                    String tit = "Atualização de Serviço";
                    al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                    buscarTodospet();
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Serviço";
                    String msg2 = "não atualizados";
                    String tit = "Atualização de Serviço";
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

                ps = connection.prepareStatement("update servico set bloqueioserv=? where servcod=?");

                ps.setBoolean(1, true);
                ps.setString(2, cod);

                int i = ps.executeUpdate();
                if (i != 0) {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Serviço";
                    String msg2 = "atualizado com sucesso";
                    String tit = "Atualização de Serviço";
                    al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                    buscarTodospet();
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Detalhes do Serviço";
                    String msg2 = "não atualizados";
                    String tit = "Atualização de Serviço";
                    al.alertinput(tit, "erro", msg, msg2, "", "erro");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void buscarpettdesp(String user) {

        resul = 0;
        this.connection = new ConnectionFactory().getConnection();

        String bloq, ord;

        String selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = "ORDER BY servico.dts ASC, servico.hrs ASC;";
        } else {
            ord = "ORDER BY servico.dts DESC, servico.hrs DESC;";
        }

        selectedOption = (String) cb_dst.getSelectedItem();
        if (null == selectedOption) {
            bloq = "";
        } else {
            switch (selectedOption) {
                case "Serviços Desativados":
                    bloq = " and bloqueioserv=true";
                    break;
                case "Serviços Ativos":
                    bloq = " and (bloqueioserv IS NULL OR bloqueioserv = False)";
                    break;
                default:
                    bloq = "";
                    break;
            }
        }
        String anunc;

        selectedOption = (String) cb_anunc.getSelectedItem();
        switch (selectedOption) {
            case "Anuncio Aprovado":
                anunc = " and aprovacaoserv = true";
                break;
            case "Anuncio Reprovado":
                anunc = " and aprovacaoserv = false";
                break;
            case "Todos Tipos de Aprovações":
                anunc = "";
                break;
            default:
                anunc = " and aprovacaoserv IS NULL";
                break;
        }

        String preco;
        if (check_preco.isSelected()) {
            preco = " and preco <= " + formatpreco(textf_preco.getText());
        } else {
            int valor = slider_preco.getValue()+1;
            preco = " and preco <= " + valor;

        }

        switch (tipo2) {

            case 1: {
                String sql = "SELECT * FROM servico where pessoa_codp= '" + user + "'" + bloq + anunc + preco + " " + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    //stm.setString(1, user ); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[11];
                            row[0] = res.getString(1); // Nome
                            row[1] = res.getString(2); // Descrição
                            row[2] = res.getString(5); // Preço
                            row[3] = res.getString(6); // Cod
                            row[4] = res.getString(8);//Código adm
                            row[5] = res.getString(7);//Código user
                            row[6] = res.getString(9);//apv
                            row[7] = res.getString(10);//dt
                            row[8] = res.getString(11);//hr
                            row[9] = res.getString(3);//est
                            row[10] = res.getString(4);//cid
                            data.add(row);
                            result = res.next();
                        }
                    } else if (pf != null) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Nenhum Serviço foi Encontrado";
                        String msg2 = "Com essa pessoa";
                        String tit = "Buscar Serviços";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                        al.setAlwaysOnTop(true);
                    }
                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Descrição", "Preço", "Código", "Código de Adm", "Código de User", "Aprovação do Serviço", "Data de Criação", "Hora de Criação", "Estado do Serviço", "Cidade do Serviço"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    jTable1.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                tipo2 = 0;
                if (resul == 1) {
                    textf_result.setText(resul + " Resultado Encontrado");
                } else {
                    textf_result.setText(resul + " Resultados Encontrados");
                }
                break;
            }
            case 4: {
                String sql = "SELECT * FROM servico where admin_codadmn= '" + user + "'" + bloq + anunc + preco + " " + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    //stm.setString(1, user ); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[11];
                            row[0] = res.getString(1); // Nome
                            row[1] = res.getString(2); // Descrição
                            row[2] = res.getString(5); // Preço
                            row[3] = res.getString(6); // Cod
                            row[4] = res.getString(8);//Código adm
                            row[5] = res.getString(7);//Código user
                            row[6] = res.getString(9);//apv
                            row[7] = res.getString(10);//dt
                            row[8] = res.getString(11);//hr
                            row[9] = res.getString(3);//est
                            row[10] = res.getString(4);//cid
                            data.add(row);
                            result = res.next();
                        }
                    }
                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Descrição", "Preço", "Código", "Código de Adm", "Código de User", "Aprovação do Serviço", "Data de Criação", "Hora de Criação", "Estado do Serviço", "Cidade do Serviço"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    jTable1.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                tipo2 = 0;
                if (resul == 1) {
                    textf_result.setText(resul + " Resultado Encontrado");
                } else {
                    textf_result.setText(resul + " Resultados Encontrados");
                }

                break;
            }
            default:
                break;
        }

        if ("Serviços Desativados".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(ServicoBuscar.class.getResource("/img/icon_atv.png")));
        } else {

            btn_dst.setIcon(new ImageIcon(ServicoBuscar.class.getResource("/img/icon_dest.png")));

        }
    }

    public void buscarPet(String user) {

        resul = 0;
        String bloq;
        String selectedOption = (String) cb_dst.getSelectedItem();
        if (null == selectedOption) {
            bloq = "";
        } else {
            switch (selectedOption) {
                case "Serviços Desativados":
                    bloq = " and bloqueioserv=true";
                    break;
                case "Serviços Ativos":
                    bloq = " and (bloqueioserv IS NULL OR bloqueioserv = False)";
                    break;
                default:
                    bloq = "";
                    break;
            }
        }

        String anunc;
        selectedOption = (String) cb_anunc.getSelectedItem();
        switch (selectedOption) {
            case "Anuncio Aprovado":
                anunc = " and aprovacaoserv = true";
                break;
            case "Anuncio Reprovado":
                anunc = " and aprovacaoserv = false";
                break;
            case "Todos Tipos de Aprovações":
                anunc = "";
                break;
            default:
                anunc = " and aprovacaoserv IS NULL";
                break;
        }

        String preco;
        if (check_preco.isSelected()) {
            preco = " and preco <= " + formatpreco(textf_preco.getText());
        } else {
            int valor = slider_preco.getValue()+1;
            preco = " and preco <= " + valor;

        }

        resul = 0;
        this.connection = new ConnectionFactory().getConnection();
        String sql = "";
        switch (tipo2) {
            case 2:
                sql = "select * from servico where servcod= '" + user + "'" + bloq + preco + anunc;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    ResultSet res = stm.executeQuery();

                    // Cria uma matriz bidimensional para armazenar os dados do SELECT
                    Object[][] tabelserv = new Object[1][11]; // Número de colunas é 4

                    while (res.next()) {
                        resul++;
                        tabelserv[0][0] = res.getString(1); // Nome
                        tabelserv[0][1] = res.getString(2); // Descrição
                        tabelserv[0][2] = res.getString(5); // Preço
                        tabelserv[0][3] = res.getString(6); // Cod
                        tabelserv[0][4] = res.getString(8);//Código adm
                        tabelserv[0][5] = res.getString(7);//Código user
                        tabelserv[0][6] = res.getString(9);//apv
                        tabelserv[0][7] = res.getString(10);//dt
                        tabelserv[0][8] = res.getString(11);//hr
                        tabelserv[0][9] = res.getString(3);//est
                        tabelserv[0][10] = res.getString(4);//cid
                    }

                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Descrição", "Preço", "Código", "Código de Adm", "Código de User", "Aprovação do Serviço", "Data de Criação", "Hora de Criação", "Estado do Serviço", "Cidade do Serviço"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(tabelserv, columnNames);

                    // Configura o modelo da tabela
                    jTable1.setModel(model);
                    setImg(user);
                    radio_apv.setEnabled(true);
                    radio_rep.setEnabled(true);
                    radio_nulo.setEnabled(true);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (resul == 1) {
                    textf_result.setText(resul + " Resultado Encontrado");
                } else {
                    textf_result.setText(resul + " Resultados Encontrados");
                }
                break;
            case 1:
                buscarpettdesp(user);
            case 4:
                buscarpettdesp(user);
            default:
                break;
        }

        if ("Serviços Desativados".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(ServicoBuscar.class.getResource("/img/icon_atv.png")));
        } else {

            btn_dst.setIcon(new ImageIcon(ServicoBuscar.class.getResource("/img/icon_dest.png")));

        }
    }

    public void BPersPet(String user) {

        resul = 0;
        this.connection = new ConnectionFactory().getConnection();

        String bloq, ord;

        String selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = "ORDER BY servico.dts ASC, servico.hrs ASC;";
        } else {
            ord = "ORDER BY servico.dts DESC, servico.hrs DESC;";
        }

        selectedOption = (String) cb_dst.getSelectedItem();
        if (null == selectedOption) {
            bloq = "";
        } else {
            switch (selectedOption) {
                case "Serviços Desativados":
                    bloq = " and bloqueioserv=true";
                    break;
                case "Serviços Ativos":
                    bloq = " and (bloqueioserv IS NULL OR bloqueioserv = False)";
                    break;
                default:
                    bloq = "";
                    break;
            }
        }
        String anunc;

        selectedOption = (String) cb_anunc.getSelectedItem();
        switch (selectedOption) {
            case "Anuncio Aprovado":
                anunc = " and aprovacaoserv = true";
                break;
            case "Anuncio Reprovado":
                anunc = " and aprovacaoserv = false";
                break;
            case "Todos Tipos de Aprovações":
                anunc = "";
                break;
            default:
                anunc = " and aprovacaoserv IS NULL";
                break;
        }

        String preco;
        if (check_preco.isSelected()) {
            preco = " and preco <= " + formatpreco(textf_preco.getText());
        } else {
            int valor = slider_preco.getValue()+1;
            preco = " and preco <= " + valor;

        }

        switch (tipo2) {
            case 2: {
                String sql = "SELECT * FROM servico where servcod like ?" + bloq + anunc + preco + " " + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[11];
                            row[0] = res.getString(1); // Nome
                            row[1] = res.getString(2); // Descrição
                            row[2] = res.getString(5); // Preço
                            row[3] = res.getString(6); // Cod
                            row[4] = res.getString(8);//Código adm
                            row[5] = res.getString(7);//Código user
                            row[6] = res.getString(9);//apv
                            row[7] = res.getString(10);//dt
                            row[8] = res.getString(11);//hr
                            row[9] = res.getString(3);//est
                            row[10] = res.getString(4);//cid
                            data.add(row);
                            result = res.next();
                        }
                    }
                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Descrição", "Preço", "Código", "Código de Adm", "Código de User", "Aprovação do Serviço", "Data de Criação", "Hora de Criação", "Estado do Serviço", "Cidade do Serviço"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    jTable1.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                tipo2 = 0;
                if (resul == 1) {
                    textf_result.setText(resul + " Resultado Encontrado");
                } else {
                    textf_result.setText(resul + " Resultados Encontrados");
                }
                break;
            }
            case 1: {
                String sql = "SELECT * FROM servico where pessoa_codp like ?" + bloq + anunc + preco + " " + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[11];
                            row[0] = res.getString(1); // Nome
                            row[1] = res.getString(2); // Descrição
                            row[2] = res.getString(5); // Preço
                            row[3] = res.getString(6); // Cod
                            row[4] = res.getString(8);//Código adm
                            row[5] = res.getString(7);//Código user
                            row[6] = res.getString(9);//apv
                            row[7] = res.getString(10);//dt
                            row[8] = res.getString(11);//hr
                            row[9] = res.getString(3);//est
                            row[10] = res.getString(4);//cid
                            data.add(row);
                            result = res.next();
                        }
                    }
                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Descrição", "Preço", "Código", "Código de Adm", "Código de User", "Aprovação do Serviço", "Data de Criação", "Hora de Criação", "Estado do Serviço", "Cidade do Serviço"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    jTable1.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                tipo2 = 0;
                if (resul == 1) {
                    textf_result.setText(resul + " Resultado Encontrado");
                } else {
                    textf_result.setText(resul + " Resultados Encontrados");
                }
                break;
            }
            case 3: {
                String sql = "SELECT * FROM servico where admin_codadmn like ?" + bloq + anunc + preco + " " + ord;
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, user + "%"); // Define o valor do parâmetro com o operador %
                    ResultSet res = stm.executeQuery();

                    ArrayList<Object> data = new ArrayList<>(); // Lista para armazenar os registros

                    boolean result = res.next();
                    if (result) {
                        while (result) {
                            resul++;
                            Object[] row = new Object[11];
                            row[0] = res.getString(1); // Nome
                            row[1] = res.getString(2); // Descrição
                            row[2] = res.getString(5); // Preço
                            row[3] = res.getString(6); // Cod
                            row[4] = res.getString(8);//Código adm
                            row[5] = res.getString(7);//Código user
                            row[6] = res.getString(9);//apv
                            row[7] = res.getString(10);//dt
                            row[8] = res.getString(11);//hr
                            row[9] = res.getString(3);//est
                            row[10] = res.getString(4);//cid
                            data.add(row);
                            result = res.next();
                        }
                    }
                    // Define os nomes das colunas
                    Object[] columnNames = {"Nome", "Descrição", "Preço", "Código", "Código de Adm", "Código de User", "Aprovação do Serviço", "Data de Criação", "Hora de Criação", "Estado do Serviço", "Cidade do Serviço"};

                    // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
                    CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

                    // Configura o modelo da tabela
                    jTable1.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                tipo2 = 0;
                if (resul == 1) {
                    textf_result.setText(resul + " Resultado Encontrado");
                } else {
                    textf_result.setText(resul + " Resultados Encontrados");
                }
                break;
            }
            default:
                break;
        }
        if ("Serviços Desativados".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(ServicoBuscar.class.getResource("/img/icon_atv.png")));
        } else {

            btn_dst.setIcon(new ImageIcon(ServicoBuscar.class.getResource("/img/icon_dest.png")));

        }

    }

    public void buscarTodospet() {

        resul = 0;
        this.connection = new ConnectionFactory().getConnection();
        String bloq, ord;

        /*if (check_bloq.isSelected()) {
            bloq = "WHERE bloqueiopet=true";
        } else {
            bloq = "WHERE (bloqueiopet IS NULL OR bloqueiopet = false) ";
        }*/
        String selectedOption = (String) cb_ordem.getSelectedItem();
        if ("Mais Recentes".equals(selectedOption)) {
            ord = "ORDER BY servico.dts ASC, servico.hrs ASC;";
        } else {
            ord = "ORDER BY servico.dts DESC, servico.hrs DESC;";
        }

        selectedOption = (String) cb_dst.getSelectedItem();
        if (null == selectedOption) {
            bloq = "";
        } else {
            switch (selectedOption) {
                case "Serviços Desativados":
                    bloq = " WHERE bloqueioserv=true";
                    break;
                case "Serviços Ativos":
                    bloq = " WHERE (bloqueioserv IS NULL OR bloqueioserv = False)";
                    break;
                default:
                    bloq = " WHERE";
                    break;
            }
        }

        String anunc;
        selectedOption = (String) cb_anunc.getSelectedItem();
        if (bloq == " WHERE") {
            switch (selectedOption) {
                case "Anuncio Aprovado":
                    anunc = " aprovacaoserv = true";
                    break;
                case "Anuncio Reprovado":
                    anunc = " aprovacaoserv = false";
                    break;
                case "Todos Tipos de Aprovações":
                    anunc = "";
                    break;
                default:
                    anunc = " aprovacaoserv IS NULL";
                    break;
            }
        } else {
            switch (selectedOption) {
                case "Anuncio Aprovado":
                    anunc = " and aprovacaoserv = true";
                    break;
                case "Anuncio Reprovado":
                    anunc = " and aprovacaoserv = false";
                    break;
                case "Todos Tipos de Aprovações":
                    anunc = "";
                    break;
                default:
                    anunc = " and aprovacaoserv IS NULL";
                    break;
            }

        }

        String preco;

        if (check_preco.isSelected()) {
            if (" WHERE".equals(bloq) && "".equals(anunc)) {
                preco = " preco <= " + formatpreco(textf_preco.getText());
            } else {
                preco = " and preco <= " + formatpreco(textf_preco.getText());
            }
        } else {

            int valor = slider_preco.getValue()+1;
            if (" WHERE".equals(bloq) && "".equals(anunc)) {
                preco = " preco <= " + valor;
            } else {
                preco = " and preco <= " + valor;
            }
        }

        String sql = "SELECT * FROM servico" + bloq + anunc + preco + " " + ord;
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
                    Object[] row = new Object[11];
                    row[0] = res.getString(1); // Nome
                    row[1] = res.getString(2); // Descrição
                    row[2] = res.getString(5); // Preço
                    row[3] = res.getString(6); // Cod
                    row[4] = res.getString(8);//Código adm
                    row[5] = res.getString(7);//Código user
                    row[6] = res.getString(9);//apv
                    row[7] = res.getString(10);//dt
                    row[8] = res.getString(11);//hr
                    row[9] = res.getString(3);//est
                    row[10] = res.getString(4);//cid
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
            Object[] columnNames = {"Nome", "Descrição", "Preço", "Código", "Código de Adm", "Código de User", "Aprovação do Serviço", "Data de Criação", "Hora de Criação", "Estado do Serviço", "Cidade do Serviço"};

            // Cria um modelo de tabela personalizado usando os dados e nomes das colunas
            CustomTableModel model = new CustomTableModel(data.toArray(new Object[0][0]), columnNames);

            // Configura o modelo da tabela
            jTable1.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (resul == 1) {
            textf_result.setText(resul + " Resultado Encontrado");
        } else {
            textf_result.setText(resul + " Resultados Encontrados");
        }

        if ("Serviços Desativados".equals(selectedOption)) {
            btn_dst.setIcon(new ImageIcon(ServicoBuscar.class.getResource("/img/icon_atv.png")));
        } else {

            btn_dst.setIcon(new ImageIcon(ServicoBuscar.class.getResource("/img/icon_dest.png")));

        }

    }

    public void Buscaatt(String user) {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "select * from servico where servcod = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet res = stm.executeQuery();

            while (res.next()) {
                Boolean tx1 = res.getBoolean(9);
                if (apv != null) {
                    if (tx1) {
                        radio_apv.setSelected(true);
                        textarea_mt.setVisible(false);
                        L_mt.setVisible(false);
                        textarea_mt.setPreferredSize(new java.awt.Dimension(0, 0));
                    } else if (res.wasNull()) {
                        radio_nulo.setSelected(true);
                        textarea_mt.setVisible(false);
                        L_mt.setVisible(false);
                        textarea_mt.setPreferredSize(new java.awt.Dimension(0, 0));
                    } else {
                        radio_rep.setSelected(true);
                        String tx2 = res.getString(12);
                        textarea_mt.setVisible(true);
                        textarea_mt.setText(tx2);
                        L_mt.setVisible(true);
                    }
                } else {
                    textarea_mt.setVisible(false);
                    radio_nulo.setSelected(true);
                    L_mt.setVisible(false);
                    textarea_mt.setPreferredSize(new java.awt.Dimension(0, 0));

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void atualizar(String Cod) {

        bus(admin);
        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps;
        String status = "";
        if ((admin == null) || (admin == "")) {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Admin não logado";
            String tit = "Detalhes Serviço não atualizados";
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
                String tit = "Nenhum Serviço selecionado";
                al.alertinput(tit, "info", msg, msg2, "", "info");

            }
            if (radio_apv.isSelected()) {

                try {
                    ps = connection.prepareStatement("update servico set aprovacaoserv=? ,admin_codadmn=? where servcod=?");
                    ps.setBoolean(1, true);
                    ps.setString(2, codadm);
                    ps.setString(3, Cod);
                    int i = ps.executeUpdate();
                    if (i != 0) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes Serviço";
                        String msg2 = "atualizados com sucesso";
                        String tit = "Atualização de Serviço";
                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                        buscarTodospet();
                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes Serviço não atualizados";
                        String tit = "Atualização de Serviço";
                        al.alertinput(tit, "erro", "", msg, "", "erro");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (radio_rep.isSelected()) {
                audios("tci");
                ServicoReprovar pr = new ServicoReprovar(admin, audio, Cod);
                pr.setVisible(true);
            } else if (radio_nulo.isSelected()) {

                try {
                    ps = connection.prepareStatement("update servico set aprovacaoserv=? ,admin_codadmn=? where servcod=?");
                    ps.setNull(1, java.sql.Types.BOOLEAN);
                    ps.setNull(2, java.sql.Types.BOOLEAN);
                    ps.setString(3, Cod);
                    int i = ps.executeUpdate();

                    if (i != 0) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes do Serviço";
                        String msg2 = "atualizados com sucesso";
                        String tit = "Atualização de Serviço";
                        al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                        buscarTodospet();
                    } else {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Detalhes Serviço não atualizados";
                        String tit = "Atualização de Serviço";
                        al.alertinput(tit, "erro", "", msg, "", "erro");
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
        apv = null;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupatt = new javax.swing.ButtonGroup();
        BGaudio = new javax.swing.ButtonGroup();
        PFundo = new javax.swing.JPanel();
        label_selecionado = new javax.swing.JLabel();
        textf_copiado = new javax.swing.JTextField();
        label_copy = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        label_foto = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        radio_apv = new javax.swing.JRadioButton();
        radio_rep = new javax.swing.JRadioButton();
        label_ajuda9 = new javax.swing.JLabel();
        radio_nulo = new javax.swing.JRadioButton();
        jbuttonArr1 = new SwingPerson.JbuttonArr();
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
        jLabel2 = new javax.swing.JLabel();
        btn_deletar = new SwingPerson.JbuttonArr();
        cb_ordem = new javax.swing.JComboBox<>();
        cb_anunc = new javax.swing.JComboBox<>();
        cb_dst = new javax.swing.JComboBox<>();
        btn_lmpfil = new SwingPerson.JbuttonArr();
        btn_filpet = new SwingPerson.JbuttonArr();
        slider_preco = new javax.swing.JSlider();
        L_max = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        L_mt = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textarea_mt = new javax.swing.JTextArea();
        btn_dst = new SwingPerson.JbuttonArr();
        btn_att = new javax.swing.JButton();
        btn_cad = new javax.swing.JButton();
        textf_result = new javax.swing.JLabel();
        textf_preco = new javax.swing.JFormattedTextField();
        check_preco = new javax.swing.JCheckBox();

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
        textf_copiado.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
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

        jPanel12.setBackground(new java.awt.Color(255, 253, 243));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Análise", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        radio_apv.setBackground(new java.awt.Color(255, 253, 243));
        groupatt.add(radio_apv);
        radio_apv.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_apv.setText("Aprovar");
        radio_apv.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_apv.setEnabled(false);

        radio_rep.setBackground(new java.awt.Color(255, 253, 243));
        groupatt.add(radio_rep);
        radio_rep.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_rep.setText("Reprovar");
        radio_rep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_rep.setEnabled(false);

        label_ajuda9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajuda9.setToolTipText("<html>\n<p><b>Autorizar anuncio de pet</b><br>\nPara permitir que o anuncio seja visivel<br>\npara todos os usuarios<br>\nmodificar.\n\n</p>\n</html>");
        label_ajuda9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        radio_nulo.setBackground(new java.awt.Color(255, 253, 243));
        groupatt.add(radio_nulo);
        radio_nulo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        radio_nulo.setText("Nulo");
        radio_nulo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        radio_nulo.setEnabled(false);
        radio_nulo.setFocusable(false);

        jbuttonArr1.setText("Enviar");
        jbuttonArr1.setToolTipText("Enviar Análise de Aprovação de Serviço");
        jbuttonArr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonArr1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radio_apv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_rep)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radio_nulo)
                .addGap(7, 7, 7)
                .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_ajuda9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label_ajuda9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radio_apv)
                        .addComponent(radio_nulo)
                        .addComponent(radio_rep)
                        .addComponent(jbuttonArr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 253, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(397, 200));
        jPanel1.setRequestFocusEnabled(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Buscar Serviço");

        Latt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_att.png"))); // NOI18N
        Latt.setToolTipText("Busque Todos e Atualize o Banco");
        Latt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Latt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LattMouseClicked(evt);
            }
        });

        Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        Lbuscar.setToolTipText("Buscar Serviço");
        Lbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Lbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LbuscarMouseClicked(evt);
            }
        });

        Cb_pet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código de Serviço", "Código de Pessoa", "Código de Admin Que Aprovou" }));
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
        check_buperso.setToolTipText("Busca Personalizada");
        check_buperso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Buscar Serviço");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addGap(588, 588, 588))
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
                            .addComponent(btn_tirarsel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Latt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Cb_pet, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
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

        btn_deletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/deletarm.png"))); // NOI18N
        btn_deletar.setToolTipText("Deletar o Serviço Selecionado");
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
        cb_ordem.setToolTipText("Serviços em Ordem de data Crescente/Decrescente");
        cb_ordem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cb_anunc.setBackground(new java.awt.Color(255, 253, 243));
        cb_anunc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos Tipos de Aprovações", "Anuncio Aprovado", "Anuncio Reprovado", "Anuncio em Análise" }));
        cb_anunc.setToolTipText("Serviços Aprovados/Reprovados/Não Analisados");
        cb_anunc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb_anunc.setMinimumSize(new java.awt.Dimension(202, 22));
        cb_anunc.setPreferredSize(new java.awt.Dimension(202, 22));

        cb_dst.setBackground(new java.awt.Color(255, 253, 243));
        cb_dst.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos os Tipos de Serviços At/Dst", "Serviços Ativos", "Serviços Desativados" }));
        cb_dst.setToolTipText("Serviços Aivos(At)/Desativados(Dst)");
        cb_dst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cb_dst.setMinimumSize(new java.awt.Dimension(202, 22));
        cb_dst.setPreferredSize(new java.awt.Dimension(202, 22));
        cb_dst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_dstActionPerformed(evt);
            }
        });

        btn_lmpfil.setText("Limpar Filtros");
        btn_lmpfil.setToolTipText("Limpar todos os filtros");
        btn_lmpfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lmpfilActionPerformed(evt);
            }
        });

        btn_filpet.setText("Filtrar Serviço");
        btn_filpet.setToolTipText("Procurar Serviços com os filtros");
        btn_filpet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filpetActionPerformed(evt);
            }
        });

        slider_preco.setMaximum(2000);
        slider_preco.setToolTipText("Valor do Serviço");
        slider_preco.setValue(2000);
        slider_preco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slider_preco.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_precoStateChanged(evt);
            }
        });
        slider_preco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                slider_precoKeyReleased(evt);
            }
        });

        L_max.setForeground(new java.awt.Color(255, 253, 243));
        L_max.setText("R$2000,00");

        jLabel3.setForeground(new java.awt.Color(255, 253, 243));
        jLabel3.setText("R$0,00");

        jTable1.setBackground(new java.awt.Color(204, 204, 204));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Descrição", "Preço", "Código", "Código de Adm", "Código de User", "Aprovação do Serviço", "Data de Criação", "Hora de Criação", "Estado do Serviço", "Cidade do Serviço"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 253, 243));
        jScrollPane1.setViewportView(jTable1);

        L_mt.setBackground(new java.awt.Color(255, 253, 243));
        L_mt.setForeground(new java.awt.Color(255, 253, 243));
        L_mt.setText("Motivo da Reprovação");

        textarea_mt.setEditable(false);
        textarea_mt.setColumns(20);
        textarea_mt.setRows(5);
        jScrollPane2.setViewportView(textarea_mt);

        btn_dst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_dest.png"))); // NOI18N
        btn_dst.setToolTipText("Desativar/Ativar o Serviço Selecionado");
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
        btn_att.setToolTipText("Atualizar Serviço Selecionado");
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
        btn_cad.setToolTipText("Cadastrar Serviço");
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

        textf_preco.setEditable(false);
        try {
            textf_preco.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("R$######.##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        textf_preco.setText("R$000000.00");

        check_preco.setToolTipText("Coloque um Limite Específico");
        check_preco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_precoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PFundoLayout = new javax.swing.GroupLayout(PFundo);
        PFundo.setLayout(PFundoLayout);
        PFundoLayout.setHorizontalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addComponent(L_mt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textf_result))
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_cad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_deletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(14, 14, 14))
                    .addComponent(jScrollPane1)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slider_preco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L_max)
                                .addGap(3, 3, 3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                                .addComponent(cb_ordem, 0, 195, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cb_anunc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cb_dst, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_selecionado)
                                    .addGroup(PFundoLayout.createSequentialGroup()
                                        .addGap(256, 256, 256)
                                        .addComponent(check_preco)
                                        .addGap(5, 5, 5)
                                        .addComponent(textf_preco, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btn_lmpfil, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_filpet, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                                .addComponent(textf_copiado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_copy, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        PFundoLayout.setVerticalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addComponent(label_selecionado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textf_copiado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_copy, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_ordem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_anunc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_dst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(slider_preco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(L_max)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(check_preco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textf_preco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_lmpfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_filpet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PFundoLayout.createSequentialGroup()
                            .addComponent(L_mt)
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_cad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addComponent(textf_result)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_deletar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_dst, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_att, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14))
        );

        getContentPane().add(PFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void bloqbus() {
        Cb_pet.setSelectedItem(null);
        textf_buscar.setEditable(false);
        textf_buscar.setToolTipText("Selecione a A forma que deseja Pesquisar o Serviço.");
    }

    public void inm() {
        String cod = textf_buscar.getText();
        alert al = new alert(admin, audio);
        al.setVisible(true);
        String msg = "Indisponível no momento";
        String tit = "Página em manutenção";
        al.alertinput(tit, "info", "", msg, "", "info");
    }

    public void lmp() {
        audios("cl");
        textf_copiado.setText("");
        limpar();
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

                if (selcod == null) {
                    String cod = textf_buscar.getText();
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Selecione na Tabela";
                    String msg2 = "quem deseja deletar";
                    String tit = "Nenhum Serviço selecionado";
                    al.alertinput(tit, "info", msg, msg2, "", "info");

                } else {
                    audios("aviso");
                    int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Deletar o Serviço " + selcod + "?\nUma vez deletado, essas informações sumirão do banco de dados");

                    if (escolha == 0) {
                        tipoveri = 3;
                        verificar(selcod);
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

    public void vlt() {
        audios("tc");
        dispose();
    }

    public void bus() {
        //   String cod = textf_codBuscar.getText();

        if (!check_buperso.isSelected()) {
            String selectedItem = Cb_pet.getSelectedItem().toString();

            if (selectedItem.equals("Código de Serviço")) {
                tipo2 = 2;
                tipoveri = 2;
                String cod = textf_buscar.getText();
                verificar(cod);
            } else if (selectedItem.equals("Código de Pessoa")) {
                tipo2 = 1;
                tipoveri = 2;
                String cp = textf_buscar.getText();
                verificar(cp);
            } else if (selectedItem.equals("Código de Admin Que Aprovou")) {
                tipo2 = 4;
                tipoveri = 2;
                String cadm = textf_buscar.getText();
                verificar(cadm);
            } else {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "";
                String msg2 = "Nenhuma Opção Selecionada";
                String tit = "";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
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
        String cod = textf_buscar.getText();

        if (check_buperso.isSelected()) {
            switch (a) {
                case 1:
                    tipo2 = 2;

                    if (!cod.isEmpty()) {
                        BPersPet(cod);
                    }
                    break;
                case 2:
                    tipo2 = 1;

                    if (!cod.isEmpty()) {
                        BPersPet(cod);
                    }
                    break;
                case 3:
                    tipo2 = 3;

                    if (!cod.isEmpty()) {
                        BPersPet(cod);
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
        if (tipoveri == 0) {
            jTable1.clearSelection();
            textf_copiado.setText("");
            label_foto.setIcon(null);
            selcod = "";
            radio_apv.setEnabled(false);
            radio_rep.setEnabled(false);
            radio_nulo.setEnabled(false);
            groupatt.clearSelection();
        } else {
            jTable1.clearSelection();
            textf_copiado.setText("");
            label_foto.setIcon(null);
            selcod = "";
            radio_apv.setEnabled(false);
            radio_rep.setEnabled(false);
            radio_nulo.setEnabled(false);
            groupatt.clearSelection();
        }
        dst = null;
        L_mt.setVisible(false);
        textarea_mt.setVisible(false);
        textarea_mt.setPreferredSize(new java.awt.Dimension(0, 0));
    }

    public void att_banco() {
        limpar();
        tmslider();
        buscarTodospet();

        String cod = textf_buscar.getText();
        alert al = new alert(admin, audio);
        al.setVisible(true);
        String msg = "Banco Atualizado";
        String tit = "Atualização";
        al.alertinput(tit, "ok", "", msg, "", "sucesso");
    }

    public void enviar_att() {
        if (selcod == null) {
            String cod = textf_buscar.getText();
            atualizar(cod);
        } else {
            atualizar(selcod);
        }
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

            if (selcod == null) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Selecione na Tabela";
                String msg2 = "quem deseja Desativar";
                String tit = "Nenhum Serviço selecionado";
                al.alertinput(tit, "info", msg, msg2, "", "info");

            } else {
                String selectedOption = (String) cb_anunc.getSelectedItem();
                if (dst == false) {
                    audios("aviso");
                    int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Ativar o Serviço " + selcod + "?");

                    if (escolha == 0) {
                        tipo2 = 4;
                        tipoveri = 4;
                        verificar(selcod);
                    }
                } else {
                    audios("aviso");
                    int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Desativar o Serviço " + selcod + "?");
                    btn_dst.setIcon(new ImageIcon(ServicoBuscar.class.getResource("/img/icon_atv.png")));
                    if (escolha == 0) {
                        tipo2 = 4;
                        tipoveri = 4;
                        verificar(selcod);
                    }
                }
            }
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
            ServicoCadastrar pj = new ServicoCadastrar(admin, audio);
            pj.setVisible(true);

        }
    }

    public void att() {
        if (permchefe == true || selcod.equals(admin)) {
            if ("nulo".equals(admin) || !"nulo".equals(selcod)) {
                if ("null".equals(admin) || !"null".equals(selcod)) {
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

                        if (selcod == null) {
                            String cod = textf_buscar.getText();
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Selecione na Tabela";
                            String msg2 = "quem deseja Atualizar";
                            String tit = "Nenhum Serviço selecionado";
                            al.alertinput(tit, "info", msg, msg2, "", "info");

                        } else {
                            audios("tc");
                            ServicoAtualizar adm = new ServicoAtualizar(admin, audio, selcod);
                            adm.setVisible(true);
                        }
                    }
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
            textf_buscar.setToolTipText("Selecione a A forma que deseja Pesquisar o Serviço.");
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
        if (textf_buscar.isEditable()) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                bus();
            }
        }
    }//GEN-LAST:event_textf_buscarKeyPressed

    private void textf_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_buscarKeyReleased
        if (textf_buscar.isEditable()) {
            String selectedItem = Cb_pet.getSelectedItem().toString();

            switch (selectedItem) {
                case "Código de Serviço":
                    tipo2 = 1;
                    bper(1);
                    break;
                case "Código de Pessoa":
                    tipo2 = 2;
                    bper(2);
                    break;
                case "Código de Admin Que Aprovou":
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

    private void jbuttonArr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonArr1ActionPerformed
        enviar_att();
    }//GEN-LAST:event_jbuttonArr1ActionPerformed

    private void btn_dstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dstActionPerformed
        dst();
        tsl();
    }//GEN-LAST:event_btn_dstActionPerformed

    private void btn_deletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deletarActionPerformed
        del();
        tsl();
    }//GEN-LAST:event_btn_deletarActionPerformed

    private void btn_filpetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filpetActionPerformed
        L_mt.setVisible(false);
        textarea_mt.setVisible(false);
        textarea_mt.setPreferredSize(new java.awt.Dimension(0, 0));
        radio_apv.setEnabled(false);
        radio_rep.setEnabled(false);
        radio_nulo.setEnabled(false);
        dst = null;
        buscarTodospet();
        tsl();
    }//GEN-LAST:event_btn_filpetActionPerformed

    private void btn_lmpfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lmpfilActionPerformed
        audios("cl");
        limfiltro();
        tsl();
    }//GEN-LAST:event_btn_lmpfilActionPerformed

    private void cb_dstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_dstActionPerformed
        String selectedOption = (String) cb_anunc.getSelectedItem();

    }//GEN-LAST:event_cb_dstActionPerformed

    private void slider_precoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_slider_precoKeyReleased
        int sliderValue = slider_preco.getValue()+1;
        L_max.setText("R$" + sliderValue + ",00");
    }//GEN-LAST:event_slider_precoKeyReleased

    private void slider_precoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_precoStateChanged
        int sliderValue = slider_preco.getValue()+1;
        L_max.setText("R$" + sliderValue + ",00");
    }//GEN-LAST:event_slider_precoStateChanged

    private void check_precoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_precoActionPerformed
        if (check_preco.isSelected()) {
            textf_preco.setEditable(true);
            slider_preco.setEnabled(false);
        } else {
            textf_preco.setEditable(false);
            slider_preco.setEnabled(true);
        }

    }//GEN-LAST:event_check_precoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGaudio;
    private javax.swing.JComboBox<String> Cb_pet;
    private javax.swing.JLabel L_max;
    private javax.swing.JLabel L_mt;
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
    private javax.swing.JComboBox<String> cb_anunc;
    private javax.swing.JComboBox<String> cb_dst;
    private javax.swing.JComboBox<String> cb_ordem;
    private javax.swing.JCheckBox check_buperso;
    private javax.swing.JCheckBox check_preco;
    private javax.swing.ButtonGroup groupatt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private SwingPerson.JbuttonArr jbuttonArr1;
    private javax.swing.JLabel label_ajuda9;
    private javax.swing.JLabel label_copy;
    private javax.swing.JLabel label_foto;
    private javax.swing.JLabel label_selecionado;
    private javax.swing.JRadioButton radio_apv;
    private javax.swing.JRadioButton radio_nulo;
    private javax.swing.JRadioButton radio_rep;
    private javax.swing.JSlider slider_preco;
    private javax.swing.JTextArea textarea_mt;
    private javax.swing.JTextField textf_buscar;
    private javax.swing.JTextField textf_copiado;
    private javax.swing.JFormattedTextField textf_preco;
    private javax.swing.JLabel textf_result;
    // End of variables declaration//GEN-END:variables
}
