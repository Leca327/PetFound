package subGUI;

import Logar.versao;
import alert.alert;
import factory.ConnectionFactory;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import starter.Icone;

public class ServicoAtualizar extends javax.swing.JFrame {

    private Connection connection;
    private int tamanho;
    public int tipo, tipo2;
    public String admin, ts, sexo, ft, codadm, audio, selcod, admapv;

    //instanciar objeto para fluxo de bytes.
    private FileInputStream fis;
    byte[] imageBytes;
    Boolean maxc;
    Boolean permchefe = false;

    // variável global para armazenar tamanho da imagem em bytes.
    public ServicoAtualizar(String adm, String au, String cod) {
        initComponents();
        placeholder();
        textf_loc1.setText("UF");
        textf_loc1.setForeground(Color.GRAY);

        textf_loc5.setText("Cidade");
        textf_loc5.setForeground(Color.GRAY);
        setIcon();
        admin = adm;
        audio = au;
        buspessoa();
        busadm();
        Status();
        bus(adm);
        selcod = cod;
        if (selcod != "" || selcod != null) {
            textf_codBuscar.setText(selcod);
            buscarServico(selcod);
            bupf();
            buadm();
            tamanho();
        }

        if (permchefe == true) {
            textf_adm.setEditable(true);
            textf_pf.setEditable(true);
            jLabel4.setVisible(true);
            jLabel3.setVisible(true);
            comb_adm.setVisible(true);
            comb_p.setVisible(true);
        } else {
            textf_adm.setEditable(false);
            textf_pf.setEditable(false);
            jLabel4.setVisible(false);
            jLabel3.setVisible(false);
            comb_adm.setVisible(false);
            comb_p.setVisible(false);
        }

    }

    public void placeholder() {

        textf_loc1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textf_loc1.getText().equals("UF")) {
                    textf_loc1.setText("");
                    textf_loc1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textf_loc1.getText().isEmpty()) {
                    textf_loc1.setText("UF");
                    textf_loc1.setForeground(Color.GRAY);
                }
            }
        });

        textf_loc5.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textf_loc5.getText().equals("Cidade")) {
                    textf_loc5.setText("");
                    textf_loc5.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textf_loc5.getText().isEmpty()) {
                    textf_loc5.setText("Cidade");
                    textf_loc5.setForeground(Color.GRAY);
                }
            }
        });
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

    private void Status() {

        switch (audio) {
            case "off":

                Rdsta.setSelected(true);
                break;
            case "on":

                Ratva.setSelected(true);
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
                codadm = tx4;
                permchefe = tx5;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void limpar() {

        textf_codBuscar.setText("");
        textf_nome.setText("");
        textf_desc.setText("");
        textf_loc1.setText("UF");
        textf_loc5.setText("Cidade");
        textf_loc5.setForeground(new Color(102, 102, 102));
        textf_loc1.setForeground(new Color(102, 102, 102));
        textf_preco.setText("");
        textf_adm.setText("");
        label_foto.setIcon(null);
        textf_pf.setText("");
        textf_adm.setText("");
        bupf();
        buadm();
        tamanho();
    }

    private void CarregarFoto() {

        // vai ser responsável por carregar foto do computador local para a interface java.
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Selecionar Arquivo Desejado");
        jfc.setFileFilter(new FileNameExtensionFilter("Arquivos de Imagens(*.PNG,"
                + "*.JPG, *.JPEG)", "png", "jpg", "jpeg"));

        int resultado = jfc.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                fis = new FileInputStream(jfc.getSelectedFile());
                tamanho = (int) jfc.getSelectedFile().length();
                Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(label_foto.getWidth(),
                        label_foto.getHeight(), Image.SCALE_SMOOTH);
                label_foto.setIcon(new ImageIcon(foto));
                label_foto.updateUI();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void TirarFoto() {
        audios("cl");
        ts = "sim";
        ft = "tr";
        label_foto.setIcon(null);
        label_foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/semimg.png")));
    }

    public static String formatPreco(String input) {
        // Remove os caracteres não numéricos do valor
        String numericValue = input.replaceAll("[^\\d.]", "");

        // Verifica se há caracteres suficientes para criar um número válido
        if (numericValue.length() < 1) {
            return ""; // Retorna uma string vazia se não houver dígitos numéricos
        }

        // Converte o valor para um número decimal
        double value = Double.parseDouble(numericValue);

        // Define o locale para usar o ponto como separador decimal
        Locale locale = new Locale("en", "US");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setDecimalSeparator('.');

        // Formata o número como um valor monetário com duas casas decimais
        DecimalFormat decimalFormat = new DecimalFormat("0.00", symbols);
        
        
        return decimalFormat.format(value);
    }

    public void tamanho() {
        //Nome
        String inputText = textf_nome.getText(); // Obtém o texto do campo de texto
        int numCaracteres = inputText.length();
        int maxperm = 25;
        L_caracternm.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracternm.setForeground(new Color(255, 51, 51));
            L_caracternm.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracternm.setForeground(new Color(0, 0, 0));
            L_caracternm.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        //senha confirma
        inputText = textf_desc.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 255;
        L_caracterdesc.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracterdesc.setForeground(new Color(255, 51, 51));
            L_caracterdesc.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracterdesc.setForeground(new Color(0, 0, 0));
            L_caracterdesc.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_loc1.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 2;
        if ("UF".equals(inputText)) {
            L_caracterest.setText("0");
        } else {
            L_caracterest.setText(Integer.toString(numCaracteres));
        }
        if (numCaracteres > maxperm) {
            L_caracterest.setForeground(new Color(255, 51, 51));
            L_caracterest.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracterest.setForeground(new Color(0, 0, 0));
            L_caracterest.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        //Código
        inputText = textf_loc5.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 50;
        if ("Cidade".equals(inputText)) {
            L_caractercid.setText("0");
        } else {
            L_caractercid.setText(Integer.toString(numCaracteres));
        }
        if (numCaracteres > maxperm) {
            L_caractercid.setForeground(new Color(255, 51, 51));
            L_caractercid.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caractercid.setForeground(new Color(0, 0, 0));
            L_caractercid.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

    }

    public Boolean maxperm() {
        maxc = !(textf_nome.getText().length() <= 50 && textf_desc.getText().length() <= 255 && textf_loc1.getText().length() <= 2 && textf_loc5.getText().length() <= 25 && textf_pf.getText().length() <= 25 && textf_adm.getText().length() <= 25);
        return maxc;
    }

    public String verificar(String cod) {
        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps;
        String status = "";

        String adm = textf_adm.getText();

        try {
            if ((admin == null) || (admin == "")) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Administrador não";
                String msg2 = "está logado";
                String tit = "Admin nulo";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");

            } else {
                ps = connection.prepareStatement("select * from servico where servcod=?;");
                ps.setString(1, cod);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (tipo == 1) {
                        // fazendo a validação dos dados
                        if ((textf_codBuscar.getText().isEmpty()) || (textf_nome.getText().isEmpty()) || (textf_desc.getText().isEmpty()) || (textf_loc1.getText().equals("UF")) || (textf_loc5.getText().equals("Cidade"))) {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Os campos não podem";
                            String msg2 = "retornar vazios";
                            String tit = "Campo(s) de Texto Vazio(s)";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        } else {
                            if (textf_pf.getText().length() < 6) {
                                alert al = new alert(admin, audio);
                                al.setVisible(true);
                                String msg = "Código de pessoa errada,";
                                String msg2 = "é preciso alterar";
                                String tit = "Erro no código";
                                al.alertinput(tit, "erro", msg, msg2, "", "erro");

                            } else {
                                String uf = textf_loc1.getText().replaceAll("[^a-zA-Z]", "");
                                uf = uf.toUpperCase();
                                if (uf.length() != 2) {
                                    alert al = new alert(admin, audio);
                                    al.setVisible(true);
                                    String msg = "Valor deve seguir";
                                    String msg2 = "o seguinte formato:";
                                    String msg3 = "RJ,SP,SC,MT,GO,DF ...";
                                    String tit = "Valor da UF Errada";
                                    al.alertinput(tit, "erro", msg, msg2, msg3, "erro");

                                } else {
                                    Pattern pattern = Pattern.compile(".*\\d.*");
                                    Matcher matcher1 = pattern.matcher(textf_loc1.getText());
                                    Matcher matcher2 = pattern.matcher(textf_loc5.getText());
                                    if (matcher1.matches()) {
                                        alert al = new alert(admin, audio);
                                        al.setVisible(true);
                                        String msg = "Unidade Federativa contém";
                                        String msg2 = "Números.";
                                        String tit = "UF incorreta";
                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                    } else {
                                        if (matcher2.matches()) {
                                            alert al = new alert(admin, audio);
                                            al.setVisible(true);
                                            String msg = "Cidade contém Números.";
                                            String tit = "Cidade incorreta";
                                            al.alertinput(tit, "erro", "", msg, "", "erro");
                                        } else {
                                            bus(admin);
                                            // Código válido
                                            String CodBuscar = textf_codBuscar.getText();
                                            String nome = textf_nome.getText();
                                            String desc = textf_desc.getText();
                                            String est = textf_loc1.getText();
                                            String cid = textf_loc5.getText();
                                            String preco = formatPreco(textf_preco.getText());
                                            String cd = textf_adm.getText();
                                            atualizarServico(CodBuscar, nome, desc, est, preco, codadm, cid, cd);
                                            limpar();
                                            fis = null;
                                            ts = null;
                                            ft = null;
                                        }
                                    }
                                }
                            }
                        }

                    } else {

                        buscarServico(cod);
                    }
                } else {
                    alert al = new alert(admin, audio);
                    al.setVisible(true);
                    String msg = "Código do Serviço inexistente";
                    String tit = "Serviço inexistente";
                    al.alertinput(tit, "erro", "", msg, "", "erro");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;

    }

    public static String reformatpreco(String value) {
        try {
            // Substituir a vírgula por ponto para garantir que seja um valor numérico correto
            value = value.replace(",", ".");
            // Converter a string para um valor numérico (double)
            double numericValue = Double.parseDouble(value);

            // Formatar o valor numérico para obter a string desejada "R$000000.00"
            DecimalFormat decimalFormat = new DecimalFormat("R$000000.00");
            String formattedValue = decimalFormat.format(numericValue);

            // Se o valor resultante contiver vírgula, substituir por ponto
            formattedValue = formattedValue.replace(",", ".");

            return formattedValue;
        } catch (NumberFormatException e) {
            // Caso a string não seja um valor numérico válido, retornar a string original
            return value;
        }
    }

    public void buscarServico(String cod) {

        this.connection = new ConnectionFactory().getConnection();
        String sql = "";

        sql = "select * from servico where servcod= '" + cod + "'";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            while (res.next()) {

                String tx1 = (res.getString(8));
                String tx2 = (res.getString(1));
                String tx3 = (res.getString(2));
                String tx4 = (res.getString(3));
                String tx5 = (res.getString(5));
                String tx6 = (res.getString(4));
                String tx13 = (res.getString(7));

                textf_nome.setText(tx2);
                textf_desc.setText(tx3);
                textf_loc1.setText(tx4);
                textf_loc5.setText(tx6);
                textf_preco.setText(reformatpreco(tx5));
                textf_adm.setText(tx1);
                admapv = tx1;
                textf_pf.setText(tx13);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.connection = new ConnectionFactory().getConnection();
        sql = "select * from imagem where servicocodserv= ?";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, cod);
            ResultSet res = stm.executeQuery();

            if (res.next()) {
                imageBytes = res.getBytes(2);

                if (imageBytes != null && imageBytes.length > 0) {
                    ImageIcon imageIcon = new ImageIcon(imageBytes);
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(label_foto.getWidth(), label_foto.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);

                    if (label_foto != null) {
                        label_foto.setIcon(scaledIcon);
                    } else {
                        label_foto.setText("Sem foto");
                    }
                } else {
                    label_foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/semimg.png")));
                }
                ft = "ok";
            } else {
                // Caso não haja resultados no ResultSet, definimos a imagem padrão "semimg.png"
                ft = null;
                label_foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/semimg.png")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        textf_loc1.setForeground(Color.black);
        textf_loc5.setForeground(Color.black);
    }

    public static String codimg(String nome, String p) {
        LocalDateTime now = LocalDateTime.now();

        // Remover os espaços das strings nome e adm
        nome = nome.replaceAll("\\s", "");
        p = p.replaceAll("\\s", "");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        String dayOfMonth = String.format("%02d", now.getDayOfMonth());
        String monthValue = String.format("%02d", now.getMonthValue());

        String svcCode = "IMGsvc"
                + nome.charAt(0)
                + nome.charAt(nome.length() / 2)
                + nome.charAt(nome.length() - 1)
                + now.getHour()
                + now.getMinute()
                + p.substring(0, 6) // Modificação para pegar os 6 primeiros caracteres
                + dayOfMonth
                + monthValue
                + now.getYear();

        return svcCode;
    }

    public String atualizarServico(String CodBuscar, String nome, String desc, String est, String preco, String codadm, String cid, String codad) {

        if (admapv == null ? codad == null : admapv.equals(codad)) {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "";
            PreparedStatement ps;
            String status = "";

            sql = "select * from servico where servcod= '" + CodBuscar + "'";
            try {
                PreparedStatement stm = connection.prepareStatement(sql);
                ResultSet res = stm.executeQuery();

                while (res.next()) {
                    ps = connection.prepareStatement("update servico set nomeserv=?, descserv=?, estados=?,preco=?,admin_codadmn=? ,aprovacaoserv=?,cidades=?,pessoa_codp=? where servcod=?");

                    ps.setString(1, nome);
                    ps.setString(2, desc);
                    ps.setString(3, est);
                    ps.setString(4, preco);
                    ps.setString(5, codadm);
                    ps.setBoolean(6, true);
                    ps.setString(7, cid);
                    ps.setString(8, textf_pf.getText());
                    ps.setString(9, CodBuscar);
                    int i = ps.executeUpdate();

                    if (ft == null && fis!=null) {
                        sql = "INSERT INTO imagem(codimg ,img,servicocodserv ) VALUES(?,?,?)";
                        PreparedStatement stmt;
                        try {
                            stmt = connection.prepareStatement(sql);

                            stmt.setString(1, codimg(textf_nome.getText(), textf_pf.getText()));
                            stmt.setBlob(2, fis, tamanho);
                            stmt.setString(3, CodBuscar);
                            stmt.execute();
                            stmt.close();
                            if (i != 0) {
                                alert al = new alert(admin, audio);
                                al.setVisible(true);
                                String msg = "Detalhes do Serviço";
                                String msg2 = "atualizados com sucesso";
                                String tit = "Atualização de Serviço";
                                al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

                            } else {
                                alert al = new alert(admin, audio);
                                al.setVisible(true);
                                String msg = "Detalhes do Serviço";
                                String msg2 = "não atualizados";
                                String tit = "Atualização de Serviço";
                                al.alertinput(tit, "erro", msg, msg2, "", "erro");

                            }
                        } catch (SQLException u) {
                            throw new RuntimeException(u);
                        }
                    } else if (ft == "tr") {
                        ps = connection.prepareStatement("DELETE FROM imagem WHERE servicocodserv=?");
                        ps.setString(1, CodBuscar);
                        i = ps.executeUpdate();
                        if (i != 0) {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Serviço";
                            String msg2 = "atualizados com sucesso";
                            String tit = "Atualização de Serviço";
                            al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

                        } else {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Serviço";
                            String msg2 = "não atualizados";
                            String tit = "Atualização de Serviço";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        }
                    } else if(fis!=null){
                        ps = connection.prepareStatement("update imagem set img=? where servicocodserv=?");

                        if (fis == null && ts == null && imageBytes != null) {
                            ps.setBlob(1, new ByteArrayInputStream(imageBytes), imageBytes.length);

                        } else {
                            ps.setBlob(1, fis, tamanho);

                        }
                        ps.setString(2, CodBuscar);
                        i = ps.executeUpdate();

                        if (i != 0) {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Serviço";
                            String msg2 = "atualizados com sucesso";
                            String tit = "Atualização de Serviço";
                            al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

                        } else {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Serviço";
                            String msg2 = "não atualizados";
                            String tit = "Atualização de Serviço";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        }
                    }else{
                        if (i != 0) {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Serviço";
                            String msg2 = "atualizados com sucesso";
                            String tit = "Atualização de Serviço";
                            al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

                        } else {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Serviço";
                            String msg2 = "não atualizados";
                            String tit = "Atualização de Serviço";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "";
            PreparedStatement ps;
            String status = "";

            sql = "select * from servico where servcod= '" + CodBuscar + "'";
            try {
                PreparedStatement stm = connection.prepareStatement(sql);
                ResultSet res = stm.executeQuery();

                while (res.next()) {
                    ps = connection.prepareStatement("update servico set nomeserv=?, descserv=?, estados=?,preco=?,admin_codadmn=? ,aprovacaoserv=?,cidades=? ,pessoa_codp=? where servcod=?");

                    ps.setString(1, nome);
                    ps.setString(2, desc);
                    ps.setString(3, est);
                    ps.setString(4, preco);
                    ps.setString(5, codad);
                    ps.setBoolean(6, true);
                    ps.setString(7, cid);
                    ps.setString(8, textf_pf.getText());
                    ps.setString(9, CodBuscar);
                    int i = ps.executeUpdate();

                    if (ft == null) {
                        sql = "INSERT INTO imagem(codimg ,img,servicocodserv ) VALUES(?,?,?)";
                        PreparedStatement stmt;
                        try {
                            stmt = connection.prepareStatement(sql);

                            stmt.setString(1, codimg(textf_nome.getText(), textf_pf.getText()));
                            stmt.setBlob(2, fis, tamanho);
                            stmt.setString(3, CodBuscar);
                            stmt.execute();
                            stmt.close();
                            if (i != 0) {
                                alert al = new alert(admin, audio);
                                al.setVisible(true);
                                String msg = "Detalhes do Serviço";
                                String msg2 = "atualizados com sucesso";
                                String tit = "Atualização de Serviço";
                                al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

                            } else {
                                alert al = new alert(admin, audio);
                                al.setVisible(true);
                                String msg = "Detalhes do Serviço";
                                String msg2 = "não atualizados";
                                String tit = "Atualização de Serviço";
                                al.alertinput(tit, "erro", msg, msg2, "", "erro");

                            }
                        } catch (SQLException u) {
                            throw new RuntimeException(u);
                        }
                    } else if (ft == "tr") {
                        ps = connection.prepareStatement("DELETE FROM imagem WHERE servicocodserv=?");
                        ps.setString(1, CodBuscar);
                        i = ps.executeUpdate();
                        if (i != 0) {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Serviço";
                            String msg2 = "atualizados com sucesso";
                            String tit = "Atualização de Serviço";
                            al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

                        } else {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Serviço";
                            String msg2 = "não atualizados";
                            String tit = "Atualização de Serviço";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        }
                    } else {
                        ps = connection.prepareStatement("update imagem set img=? where servicocodserv=?");

                        if (fis == null && ts == null && imageBytes != null) {
                            ps.setBlob(1, new ByteArrayInputStream(imageBytes), imageBytes.length);

                        } else {
                            ps.setBlob(1, fis, tamanho);

                        }
                        ps.setString(2, CodBuscar);
                        i = ps.executeUpdate();

                        if (i != 0) {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Serviço";
                            String msg2 = "atualizados com sucesso";
                            String tit = "Atualização de Serviço";
                            al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

                        } else {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Detalhes do Serviço";
                            String msg2 = "não atualizados";
                            String tit = "Atualização de Serviço";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public void bspadm() {
        String userInput = textf_adm.getText();
        if (!userInput.isEmpty()) {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "select * from admin where admcod like ?";

            try {
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setString(1, userInput + "%");
                ResultSet res = stm.executeQuery();

                if (res.next()) {
                    String tx1 = res.getString(1);
                    textf_ad.setText(tx1);

                } else {
                    textf_ad.setText("Admin Não Encontrada");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            textf_ad.setText(""); // Define o campo como vazio se o texto estiver vazio
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

    private boolean valorExisteNoComboBox(String valor) {
        for (int i = 0; i < comb_p.getItemCount(); i++) {
            Object item = comb_p.getItemAt(i);
            if (valor.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public void busadm() {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "select * from admin";
        //cb_v.removeAllItems();
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            // Limpar itens existentes do ComboBox
            while (res.next()) {
                comb_adm.removeItem("Sem admin encontrado");
                String tx1 = res.getString(4);

                // Verificar se o valor já existe no ComboBox
                if (!valorExisteNoComboBox2(tx1)) {
                    comb_adm.addItem(tx1); // Adicionar valor ao ComboBox
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean valorExisteNoComboBox2(String valor) {
        for (int i = 0; i < comb_adm.getItemCount(); i++) {
            Object item = comb_adm.getItemAt(i);
            if (valor.equals(item)) {
                return true;
            }
        }
        return false;
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BGaudio = new javax.swing.ButtonGroup();
        PFundo = new javax.swing.JPanel();
        Plogin = new javax.swing.JPanel();
        btn_limpar = new SwingPerson.JbuttonArr();
        btn_atualizar = new SwingPerson.JbuttonArr();
        jLabel8 = new javax.swing.JLabel();
        textf_preco = new javax.swing.JFormattedTextField();
        label_preco = new javax.swing.JLabel();
        L_caracternm = new javax.swing.JLabel();
        textf_nome = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        label_nomeS = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comb_adm = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        textf_adm = new javax.swing.JTextField();
        textf_ad = new javax.swing.JTextField();
        label_adm = new javax.swing.JLabel();
        textf_pf1 = new javax.swing.JTextField();
        label_pf = new javax.swing.JLabel();
        textf_pf = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        comb_p = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        label_desc = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textf_desc = new javax.swing.JTextArea();
        L_caracterdesc = new javax.swing.JLabel();
        textf_loc1 = new javax.swing.JTextField();
        textf_loc5 = new javax.swing.JTextField();
        label_ajuda = new javax.swing.JLabel();
        label_loc = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        L_caracterest = new javax.swing.JLabel();
        L_caractercid = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        label_codBuscar = new javax.swing.JLabel();
        btn_att = new javax.swing.JButton();
        textf_codBuscar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Lbuscar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        label_foto = new javax.swing.JLabel();
        btnCARREGAR1 = new SwingPerson.JbuttonArr();
        BtnSemimg1 = new SwingPerson.JbuttonArr();
        L_vlt = new javax.swing.JLabel();
        jMenuBar3 = new javax.swing.JMenuBar();
        Mopc = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        menu_voz = new javax.swing.JMenuItem();
        menu_texto = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menu_bus = new javax.swing.JMenuItem();
        menu_atu = new javax.swing.JMenuItem();
        menu_cf = new javax.swing.JMenuItem();
        menu_tf = new javax.swing.JMenuItem();
        menu_cad2 = new javax.swing.JMenuItem();
        menu_sobre = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        Ratva = new javax.swing.JRadioButtonMenuItem();
        Rdsta = new javax.swing.JRadioButtonMenuItem();
        menu_voltar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pet Found - Atualizar Serviço");
        setPreferredSize(new java.awt.Dimension(804, 534));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PFundo.setBackground(new java.awt.Color(64, 33, 7));
        PFundo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PFundo.setPreferredSize(new java.awt.Dimension(788, 460));

        Plogin.setBackground(new java.awt.Color(255, 253, 243));
        Plogin.setPreferredSize(new java.awt.Dimension(600, 330));

        btn_limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/apagar.png"))); // NOI18N
        btn_limpar.setToolTipText("Limpar Todos os Campos");
        btn_limpar.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparActionPerformed(evt);
            }
        });

        btn_atualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/atualizar.png"))); // NOI18N
        btn_atualizar.setToolTipText("Atualizar Informações de Serviço\n");
        btn_atualizar.setMaximumSize(new java.awt.Dimension(0, 0));
        btn_atualizar.setMinimumSize(new java.awt.Dimension(60, 60));
        btn_atualizar.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizarActionPerformed(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(363, 3));

        textf_preco.setBackground(new java.awt.Color(255, 253, 243));
        textf_preco.setBorder(null);
        try {
            textf_preco.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("R$######.##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        textf_preco.setText("R$000000.00");
        textf_preco.setPreferredSize(new java.awt.Dimension(65, 20));
        textf_preco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_precoKeyPressed(evt);
            }
        });

        label_preco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_preco.setText("PREÇO");

        L_caracternm.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracternm.setText("0");

        textf_nome.setBackground(new java.awt.Color(255, 253, 243));
        textf_nome.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_nome.setToolTipText("");
        textf_nome.setBorder(null);
        textf_nome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_nomeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_nomeKeyReleased(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(363, 3));

        label_nomeS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_nomeS.setText("NOME DO SERVIÇO");

        jLabel4.setText("Selecionar Código");
        jLabel4.setToolTipText("<html> Selecionar Código Na Comb Box <br> E colocar No Campo de Texto</html>");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        comb_adm.setBackground(new java.awt.Color(204, 204, 204));
        comb_adm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        comb_adm.setSelectedItem(null);
        comb_adm.setToolTipText("");
        comb_adm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(363, 3));

        textf_adm.setBackground(new java.awt.Color(255, 253, 243));
        textf_adm.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_adm.setBorder(null);
        textf_adm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_admKeyReleased(evt);
            }
        });

        textf_ad.setEditable(false);
        textf_ad.setBackground(new java.awt.Color(204, 204, 204));
        textf_ad.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        textf_ad.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label_adm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_adm.setText("COD ADM");
        label_adm.setToolTipText("Anuncio Aprovado Pelo Admin");

        textf_pf1.setEditable(false);
        textf_pf1.setBackground(new java.awt.Color(204, 204, 204));
        textf_pf1.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        textf_pf1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label_pf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_pf.setText("COD PESSOA");

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

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(363, 3));

        comb_p.setBackground(new java.awt.Color(204, 204, 204));
        comb_p.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        comb_p.setSelectedItem(null);
        comb_p.setToolTipText("");
        comb_p.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel3.setText("Selecionar Código");
        jLabel3.setToolTipText("<html> Selecionar Código Na Comb Box <br> E colocar No Campo de Texto</html>");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        label_desc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_desc.setText("DESCRIÇÃO");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 148, 44)));

        textf_desc.setBackground(new java.awt.Color(255, 253, 243));
        textf_desc.setColumns(20);
        textf_desc.setLineWrap(true);
        textf_desc.setRows(5);
        textf_desc.setWrapStyleWord(true);
        textf_desc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 148, 44)));
        jScrollPane1.setViewportView(textf_desc);

        L_caracterdesc.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterdesc.setText("0");

        textf_loc1.setBackground(new java.awt.Color(255, 253, 243));
        textf_loc1.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_loc1.setForeground(new java.awt.Color(102, 102, 102));
        textf_loc1.setText("UF");
        textf_loc1.setBorder(null);
        textf_loc1.setPreferredSize(new java.awt.Dimension(64, 20));
        textf_loc1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textf_loc1MouseClicked(evt);
            }
        });
        textf_loc1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_loc1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_loc1KeyReleased(evt);
            }
        });

        textf_loc5.setBackground(new java.awt.Color(255, 253, 243));
        textf_loc5.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_loc5.setForeground(new java.awt.Color(102, 102, 102));
        textf_loc5.setText("Cidade");
        textf_loc5.setBorder(null);
        textf_loc5.setPreferredSize(new java.awt.Dimension(64, 20));
        textf_loc5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textf_loc5MouseClicked(evt);
            }
        });
        textf_loc5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_loc5KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_loc5KeyReleased(evt);
            }
        });

        label_ajuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajuda.setToolTipText("<html>\nRegião que a pessoa irá trabalhar <br>\nEx: Rio de Janeiro, Rj,Centro\n</html>");
        label_ajuda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        label_loc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_loc.setText("LOCAL DO SERVIÇO");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caracterest.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterest.setText("0");

        L_caractercid.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercid.setText("0");

        javax.swing.GroupLayout PloginLayout = new javax.swing.GroupLayout(Plogin);
        Plogin.setLayout(PloginLayout);
        PloginLayout.setHorizontalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PloginLayout.createSequentialGroup()
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                        .addContainerGap(426, Short.MAX_VALUE)
                        .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(textf_pf, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(textf_adm, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(comb_p, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(comb_adm, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jLabel3)
                                .addGap(204, 204, 204)
                                .addComponent(jLabel4))
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_nomeS)
                                    .addComponent(textf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(L_caracternm)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(L_caracterest)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(5, 5, 5)
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(L_caractercid))))
                                .addGap(91, 91, 91)
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_preco)
                                    .addComponent(textf_preco, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_desc)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(L_caracterdesc)
                                .addGroup(PloginLayout.createSequentialGroup()
                                    .addComponent(label_pf)
                                    .addGap(7, 7, 7)
                                    .addComponent(textf_pf1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(89, 89, 89)
                                    .addComponent(label_adm)
                                    .addGap(29, 29, 29)
                                    .addComponent(textf_ad, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(label_loc)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(textf_loc1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(textf_loc5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(label_ajuda)))
                        .addGap(16, 16, 16)))
                .addContainerGap())
        );
        PloginLayout.setVerticalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(PloginLayout.createSequentialGroup()
                            .addComponent(label_nomeS)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PloginLayout.createSequentialGroup()
                            .addComponent(label_preco)
                            .addGap(28, 28, 28)))
                    .addComponent(textf_preco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(L_caracternm)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_desc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(L_caracterdesc))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(label_loc)
                        .addGap(3, 3, 3)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textf_loc1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textf_loc5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_ajuda, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(L_caracterest)
                            .addComponent(L_caractercid))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_pf)
                    .addComponent(textf_pf1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_adm)
                    .addComponent(textf_ad, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textf_pf, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textf_adm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comb_p, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comb_adm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Atualizar Serviço");

        jPanel1.setBackground(new java.awt.Color(255, 253, 243));

        label_codBuscar.setBackground(new java.awt.Color(51, 51, 51));
        label_codBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_codBuscar.setForeground(new java.awt.Color(51, 51, 51));
        label_codBuscar.setText("CÓDIGO");

        btn_att.setBackground(new java.awt.Color(255, 253, 243));
        btn_att.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_att.setForeground(new java.awt.Color(51, 51, 51));
        btn_att.setText("Não sei o Código");
        btn_att.setToolTipText("<html>Sair da Tela E buscar<br> O Código do Pet</html>");
        btn_att.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_att.setPreferredSize(new java.awt.Dimension(148, 22));
        btn_att.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_attActionPerformed(evt);
            }
        });

        textf_codBuscar.setBackground(new java.awt.Color(255, 253, 243));
        textf_codBuscar.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_codBuscar.setBorder(null);
        textf_codBuscar.setPreferredSize(new java.awt.Dimension(64, 20));
        textf_codBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textf_codBuscarMouseClicked(evt);
            }
        });
        textf_codBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_codBuscarKeyPressed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(363, 3));

        Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        Lbuscar.setToolTipText("Buscar Serviço");
        Lbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Lbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LbuscarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(label_codBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textf_codBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Lbuscar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textf_codBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_codBuscar))
                    .addComponent(Lbuscar))
                .addGap(0, 0, 0)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        label_foto.setForeground(new java.awt.Color(255, 255, 255));
        label_foto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(label_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnCARREGAR1.setText("Carregar");
        btnCARREGAR1.setToolTipText("Carregar Uma Foto");
        btnCARREGAR1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCARREGAR1ActionPerformed(evt);
            }
        });

        BtnSemimg1.setText("Remover");
        BtnSemimg1.setToolTipText("Remover imagem do Serviço\n");
        BtnSemimg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSemimg1ActionPerformed(evt);
            }
        });

        L_vlt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_voltarbr.png"))); // NOI18N
        L_vlt.setToolTipText("Sair de Cadastrar");
        L_vlt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        L_vlt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                L_vltMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PFundoLayout = new javax.swing.GroupLayout(PFundo);
        PFundo.setLayout(PFundoLayout);
        PFundoLayout.setHorizontalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(PFundoLayout.createSequentialGroup()
                            .addComponent(btnCARREGAR1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BtnSemimg1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(L_vlt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Plogin, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        PFundoLayout.setVerticalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(65, 65, 65)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnSemimg1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCARREGAR1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(L_vlt)
                .addContainerGap())
            .addGroup(PFundoLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Plogin, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
        );

        getContentPane().add(PFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jMenuBar3.setBackground(new java.awt.Color(255, 253, 243));
        jMenuBar3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuBar3.setMinimumSize(new java.awt.Dimension(210, 20));
        jMenuBar3.setPreferredSize(new java.awt.Dimension(210, 35));

        Mopc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/option.png"))); // NOI18N
        Mopc.setToolTipText("Aba de Opções");

        jMenu5.setText("Acessibilidade");

        menu_voz.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_voz.setText("Ativar comando por voz");
        menu_voz.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_voz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_vozActionPerformed(evt);
            }
        });
        jMenu5.add(menu_voz);

        menu_texto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_texto.setText("Ativar leitura de texto");
        menu_texto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_texto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_textoActionPerformed(evt);
            }
        });
        jMenu5.add(menu_texto);

        Mopc.add(jMenu5);

        jMenu4.setText("Comandos Rápidos");
        jMenu4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menu_bus.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_bus.setText("Atualizar");
        menu_bus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_bus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_busActionPerformed(evt);
            }
        });
        jMenu4.add(menu_bus);

        menu_atu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_atu.setText("Buscar");
        menu_atu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_atu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_atuActionPerformed(evt);
            }
        });
        jMenu4.add(menu_atu);

        menu_cf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_cf.setText("Carregar Foto");
        menu_cf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_cf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_cfActionPerformed(evt);
            }
        });
        jMenu4.add(menu_cf);

        menu_tf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_tf.setText("Tirar Foto");
        menu_tf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_tfActionPerformed(evt);
            }
        });
        jMenu4.add(menu_tf);

        menu_cad2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_cad2.setText("Limpar");
        menu_cad2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_cad2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_cad2ActionPerformed(evt);
            }
        });
        jMenu4.add(menu_cad2);

        Mopc.add(jMenu4);

        menu_sobre.setText("Sobre");
        menu_sobre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem1.setText("Versão");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menu_sobre.add(jMenuItem1);

        Mopc.add(menu_sobre);

        jMenu3.setText("Opções");
        jMenu3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenu6.setText("Audio");
        jMenu6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Ratva.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        BGaudio.add(Ratva);
        Ratva.setSelected(true);
        Ratva.setText("Ativar Audio");
        Ratva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Ratva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RatvaActionPerformed(evt);
            }
        });
        jMenu6.add(Ratva);

        Rdsta.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        BGaudio.add(Rdsta);
        Rdsta.setText("Desativar Audio");
        Rdsta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Rdsta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RdstaActionPerformed(evt);
            }
        });
        jMenu6.add(Rdsta);

        jMenu3.add(jMenu6);

        menu_voltar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        menu_voltar.setText("Voltar");
        menu_voltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_voltarActionPerformed(evt);
            }
        });
        jMenu3.add(menu_voltar);

        Mopc.add(jMenu3);

        jMenuBar3.add(Mopc);

        setJMenuBar(jMenuBar3);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    public void att() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo atulizar o Serviço " + textf_codBuscar.getText() + "?\nUma vez atulizado, a informação irá mudar para sempre.");
        if (escolha == 0) {
            String cod = textf_codBuscar.getText();
            tipo = 1;
            verificar(cod);
        }
    }

    public void bus() {
        String user = textf_codBuscar.getText();
        tipo = 2;
        verificar(user);
    }

    public void vlt() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Sair?");
        if (escolha == 0) {
            audios("tc");
//            Servicos serv = new Servicos(admin, audio);
            // serv.setVisible(true);
            dispose();
        }
    }

    public void lmp() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Limpar todos os campos?");
        if (escolha == 0) {
            limpar();

        }
    }

    public void buadm() {
        bspadm();
        String userInput = textf_adm.getText().toLowerCase();
        for (int i = 0; i < comb_adm.getItemCount(); i++) {
            String item = comb_adm.getItemAt(i).toLowerCase();
            if (item.startsWith(userInput)) {
                comb_adm.setSelectedIndex(i);
                return;
            }
        }
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

    public void inm() {
        alert al = new alert(admin, audio);
        al.setVisible(true);
        String msg = "Indisponível no momento";
        String tit = "Página em manutenção";
        al.alertinput(tit, "info", "", msg, "", "info");
    }
    private void menu_cad2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cad2ActionPerformed
        lmp();
    }//GEN-LAST:event_menu_cad2ActionPerformed

    private void menu_atuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_atuActionPerformed
        bus();
    }//GEN-LAST:event_menu_atuActionPerformed

    private void menu_busActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_busActionPerformed
        att();
    }//GEN-LAST:event_menu_busActionPerformed

    private void menu_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_voltarActionPerformed
        vlt();
    }//GEN-LAST:event_menu_voltarActionPerformed

    private void menu_vozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_vozActionPerformed
        inm();
    }//GEN-LAST:event_menu_vozActionPerformed

    private void menu_textoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_textoActionPerformed
        inm();
    }//GEN-LAST:event_menu_textoActionPerformed

    private void menu_cfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cfActionPerformed
        CarregarFoto();
    }//GEN-LAST:event_menu_cfActionPerformed

    private void menu_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tfActionPerformed
        TirarFoto();
    }//GEN-LAST:event_menu_tfActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        audios("tci");
        versao v = new versao();
        v.buscarVersao(); // Buscar versões antes de exibir a janela
        v.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void RatvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RatvaActionPerformed
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        if (mixers.length == 0) {
            audio = "off";
            String user = textf_codBuscar.getText();
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Nenhum dispositivo de";
            String msg2 = "saída de áudio encontrado";
            String tit = "Áudio indisponível";
            al.alertinput(tit, "erro", msg, msg2, "", "erro");
        } else {

            audio = "on";
            for (Mixer.Info mixerInfo : mixers) {
                System.out.println(mixerInfo);
            }
        }
        Status();
        Rdsta.setSelected(true);
    }//GEN-LAST:event_RatvaActionPerformed

    private void RdstaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RdstaActionPerformed
        audio = "off";
        Status();
        Rdsta.setSelected(true);
    }//GEN-LAST:event_RdstaActionPerformed

    private void btn_attActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_attActionPerformed
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja ir para a tela de Buscar Serviço?");
        if (escolha == 0) {
            audios("tc");
//            ServicoBuscar pf = new ServicoBuscar(admin, audio);
            //          pf.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_btn_attActionPerformed

    private void textf_codBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_codBuscarMouseClicked
        limpar();
    }//GEN-LAST:event_textf_codBuscarMouseClicked

    private void textf_codBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_codBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            bus();
        }
    }//GEN-LAST:event_textf_codBuscarKeyPressed

    private void LbuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LbuscarMouseClicked
        bus();
    }//GEN-LAST:event_LbuscarMouseClicked

    private void L_vltMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_vltMouseClicked
        vlt();
    }//GEN-LAST:event_L_vltMouseClicked

    private void btnCARREGAR1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCARREGAR1ActionPerformed
        audios("cl");
        CarregarFoto();
    }//GEN-LAST:event_btnCARREGAR1ActionPerformed

    private void BtnSemimg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSemimg1ActionPerformed
        TirarFoto();
    }//GEN-LAST:event_BtnSemimg1ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        textf_pf.setText(comb_p.getSelectedItem().toString());
        bsp();
    }//GEN-LAST:event_jLabel3MouseClicked

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

    private void textf_pfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_pfKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_pfKeyPressed

    private void textf_admKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_admKeyReleased
        buadm();
    }//GEN-LAST:event_textf_admKeyReleased

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if (comb_adm.getSelectedItem() == null || comb_adm.getSelectedItem() == "") {

        } else {
            textf_adm.setText(comb_adm.getSelectedItem().toString());
            bspadm();
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void textf_loc5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_loc5KeyReleased
        tamanho();
    }//GEN-LAST:event_textf_loc5KeyReleased

    private void textf_loc5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_loc5KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_loc5KeyPressed

    private void textf_loc5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_loc5MouseClicked

        textf_loc5.setForeground(Color.black);
    }//GEN-LAST:event_textf_loc5MouseClicked

    private void textf_loc1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_loc1KeyReleased
        tamanho();
    }//GEN-LAST:event_textf_loc1KeyReleased

    private void textf_loc1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_loc1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_loc1KeyPressed

    private void textf_loc1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_loc1MouseClicked
        textf_loc1.setForeground(Color.black);
    }//GEN-LAST:event_textf_loc1MouseClicked

    private void textf_nomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nomeKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_nomeKeyReleased

    private void textf_nomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_nomeKeyPressed

    private void textf_precoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_precoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_precoKeyPressed

    private void btn_atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizarActionPerformed
        att();
    }//GEN-LAST:event_btn_atualizarActionPerformed

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        audios("cl");
        lmp();
    }//GEN-LAST:event_btn_limparActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String ad = "";
                String au = "off";
                String c = null;
                new ServicoAtualizar(ad, au, c).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGaudio;
    private SwingPerson.JbuttonArr BtnSemimg1;
    private javax.swing.JLabel L_caractercid;
    private javax.swing.JLabel L_caracterdesc;
    private javax.swing.JLabel L_caracterest;
    private javax.swing.JLabel L_caracternm;
    private javax.swing.JLabel L_vlt;
    private javax.swing.JLabel Lbuscar;
    private javax.swing.JMenu Mopc;
    private javax.swing.JPanel PFundo;
    private javax.swing.JPanel Plogin;
    private javax.swing.JRadioButtonMenuItem Ratva;
    private javax.swing.JRadioButtonMenuItem Rdsta;
    private SwingPerson.JbuttonArr btnCARREGAR1;
    private javax.swing.JButton btn_att;
    private SwingPerson.JbuttonArr btn_atualizar;
    private SwingPerson.JbuttonArr btn_limpar;
    private javax.swing.JComboBox<String> comb_adm;
    private javax.swing.JComboBox<String> comb_p;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_adm;
    private javax.swing.JLabel label_ajuda;
    private javax.swing.JLabel label_codBuscar;
    private javax.swing.JLabel label_desc;
    private javax.swing.JLabel label_foto;
    private javax.swing.JLabel label_loc;
    private javax.swing.JLabel label_nomeS;
    private javax.swing.JLabel label_pf;
    private javax.swing.JLabel label_preco;
    private javax.swing.JMenuItem menu_atu;
    private javax.swing.JMenuItem menu_bus;
    private javax.swing.JMenuItem menu_cad2;
    private javax.swing.JMenuItem menu_cf;
    private javax.swing.JMenu menu_sobre;
    private javax.swing.JMenuItem menu_texto;
    private javax.swing.JMenuItem menu_tf;
    private javax.swing.JMenuItem menu_voltar;
    private javax.swing.JMenuItem menu_voz;
    private javax.swing.JTextField textf_ad;
    private javax.swing.JTextField textf_adm;
    private javax.swing.JTextField textf_codBuscar;
    private javax.swing.JTextArea textf_desc;
    private javax.swing.JTextField textf_loc1;
    private javax.swing.JTextField textf_loc5;
    private javax.swing.JTextField textf_nome;
    private javax.swing.JTextField textf_pf;
    private javax.swing.JTextField textf_pf1;
    private javax.swing.JFormattedTextField textf_preco;
    // End of variables declaration//GEN-END:variables
}
