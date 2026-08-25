package subGUI;

import Logar.versao;
import alert.alert;
import factory.ConnectionFactory;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

public class PFisicaAtualizar extends javax.swing.JFrame {

    private Connection connection;
    private int tamanho;
    public int tipo, tipo2;
    String adm, ts, audio, selnick, codpf;
    //instanciar objeto para fluxo de bytes.
    private FileInputStream fis;
    byte[] imageBytes;
    Boolean maxc, permchefe;

    // variável global para armazenar tamanho da imagem em bytes.
    public PFisicaAtualizar(String admin, String au, String user) {
        initComponents();
        setIcon();
        adm = admin;
        audio = au;
        Status();
        selnick = user;
        if (selnick != "" || selnick != null) {
            textf_nickBuscar.setText(selnick);
            buscarPF(selnick);
            bus(adm);
            tamanho();
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

    public void limpar() {
        textf_nickBuscar.setText("");
        textf_nome.setText("");
        textf_contato.setText("");
        textf_email.setText("");
        comb_sexo.setSelectedItem("");
        textf_cep.setText("");
        textf_snome.setText("");
        textf_uf.setText("");
        textf_num.setText("");
        textf_cmp.setText("");
        textf_bai.setText("");
        textf_ende.setText("");
        textf_cid.setText("");
        textf_nick.setText("");
        textf_dtnasc.setText("");
        pass_senha.setText("");
        pass_senhaconfirm.setText("");
        comb_sexo.setSelectedItem(null);
        label_foto.setIcon(null);
        tamanho();
    }

    private void CarregarFoto() {

        // vai ser responsável por carregar foto do computador local para a interface java.
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Selecionar Arquivo Desejado");
        jfc.setFileFilter(new FileNameExtensionFilter("Arquivos de Imagens(*.PNG,"
                + "*.JPG, *.JPEG)", "png", "jpg", "jpeg"));
        //jfc.showOpenDialog(this);
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
        label_foto.setIcon(null);
        label_foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/semimg.png")));
    }

    public String getCod(String user) {

        this.connection = new ConnectionFactory().getConnection();
        String sql = "";
        String cod = "";
        sql = "select * from admin where usera= '" + user + "'";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet res = stm.executeQuery();

            if (res.next()) {

                String tx1 = (res.getString(4));

                cod = tx1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cod;
    }

    public void tamanho() {
        //Nome
        String inputText = textf_nome.getText(); // Obtém o texto do campo de texto
        int numCaracteres = inputText.length();
        int maxperm = 50;
        L_caracternm.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracternm.setForeground(new Color(255, 51, 51));
            L_caracternm.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracternm.setForeground(new Color(0, 0, 0));
            L_caracternm.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_snome.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 50;
        L_caractersb.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caractersb.setForeground(new Color(255, 51, 51));
            L_caractersb.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caractersb.setForeground(new Color(0, 0, 0));
            L_caractersb.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_nick.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 50;
        L_caracternk.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracternk.setForeground(new Color(255, 51, 51));
            L_caracternk.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracternk.setForeground(new Color(0, 0, 0));
            L_caracternk.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_email.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 100;
        L_caracterem.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracterem.setForeground(new Color(255, 51, 51));
            L_caracterem.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracterem.setForeground(new Color(0, 0, 0));
            L_caracterem.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = pass_senha.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 25;
        L_caracters.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracters.setForeground(new Color(255, 51, 51));
            L_caracters.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracters.setForeground(new Color(0, 0, 0));
            L_caracters.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = pass_senhaconfirm.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 25;
        L_caractercs.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caractercs.setForeground(new Color(255, 51, 51));
            L_caractercs.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caractercs.setForeground(new Color(0, 0, 0));
            L_caractercs.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_uf.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 2;
        L_caracteruf.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracteruf.setForeground(new Color(255, 51, 51));
            L_caracteruf.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracteruf.setForeground(new Color(0, 0, 0));
            L_caracteruf.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_cid.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 25;
        L_caractercid.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caractercid.setForeground(new Color(255, 51, 51));
            L_caractercid.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caractercid.setForeground(new Color(0, 0, 0));
            L_caractercid.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_bai.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 25;
        L_caracterbai.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracterbai.setForeground(new Color(255, 51, 51));
            L_caracterbai.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracterbai.setForeground(new Color(0, 0, 0));
            L_caracterbai.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_ende.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 50;
        L_caracterende.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracterende.setForeground(new Color(255, 51, 51));
            L_caracterende.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracterende.setForeground(new Color(0, 0, 0));
            L_caracterende.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_num.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 5;
        L_caracternum.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracternum.setForeground(new Color(255, 51, 51));
            L_caracternum.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracternum.setForeground(new Color(0, 0, 0));
            L_caracternum.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        inputText = textf_cmp.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 25;
        L_caractercmp.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caractercmp.setForeground(new Color(255, 51, 51));
            L_caractercmp.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caractercmp.setForeground(new Color(0, 0, 0));
            L_caractercmp.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

    }

    public Boolean maxperm() {
        maxc = !(textf_nick.getText().length() <= 50 && textf_nome.getText().length() <= 50 && pass_senha.getPassword().length <= 25 && pass_senhaconfirm.getPassword().length <= 25 && textf_snome.getText().length() <= 50 && textf_email.getText().length() <= 100 && textf_uf.getText().length() <= 2 && textf_cid.getText().length() <= 25 && textf_bai.getText().length() <= 25 && textf_ende.getText().length() <= 50 && textf_num.getText().length() <= 5 && textf_cmp.getText().length() <= 25);
        return maxc;
    }

    public String verificar(String cod) {
        String status = "";
        Boolean perm = maxperm();
        if (perm == false) {
            this.connection = new ConnectionFactory().getConnection();
            PreparedStatement ps;
            String nick = textf_nick.getText();
            String cont = textf_contato.getText();
            String email = textf_email.getText();

            try {
                ps = connection.prepareStatement("select * from pessoa join fisica where nickname=? and fisica.codp=pessoa.pcod;");
                ps.setString(1, cod);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    if (tipo == 1) {

                        if ((textf_nickBuscar.getText().isEmpty()) || (textf_nome.getText().isEmpty()) || (textf_snome.getText().isEmpty()) || (textf_contato.getText().equals("(  )     -    ")) || (textf_email.getText().isEmpty()) || (pass_senha.getText().isEmpty()) || (pass_senhaconfirm.getText().isEmpty()) || (textf_cep.getText().equals("     -   ")) || (textf_bai.getText().isEmpty()) || (textf_uf.getText().isEmpty()) || (textf_ende.getText().isEmpty()) || (textf_num.getText().isEmpty()) || (textf_cid.getText().isEmpty()) || (textf_nick.getText().isEmpty()) || (textf_dtnasc.getText().equals("    -  -  ")) || (comb_sexo.getSelectedItem() == null)) {
                            alert al = new alert(adm, audio);
                            al.setVisible(true);
                            String msg = "Os campos não podem";
                            String msg2 = "retornar vazios";
                            String tit = "Campo(s) de Texto Vazio(s)";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        } else {
                            ps = connection.prepareStatement("SELECT * FROM pessoa where nickname=? AND nickname <> ?");
                            ps.setString(1, nick);
                            ps.setString(2, cod);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                alert al = new alert(adm, audio);
                                al.setVisible(true);
                                String msg = "Nickname já existe";
                                String tit = "Pessoa existente";
                                al.alertinput(tit, "erro", "", msg, "", "erro");

                            } else {
                                ps = connection.prepareStatement("SELECT * FROM admin where usera=? ");
                                ps.setString(1, nick);
                                rs = ps.executeQuery();
                                if (rs.next()) {
                                    alert al = new alert(adm, audio);
                                    al.setVisible(true);
                                    String msg = "User já existe";
                                    String tit = "Pessoa existente";
                                    al.alertinput(tit, "erro", "", msg, "", "erro");

                                } else {
                                    ps = connection.prepareStatement("SELECT * FROM pessoa where emailp=? AND nickname <> ?");
                                    ps.setString(2, cod);
                                    ps.setString(1, email);
                                    rs = ps.executeQuery();
                                    if (rs.next()) {
                                        alert al = new alert(adm, audio);
                                        al.setVisible(true);
                                        String msg = "E-mail já existe";
                                        String tit = "Pessoa existente";
                                        al.alertinput(tit, "erro", "", msg, "", "erro");

                                    } else {
                                        ps = connection.prepareStatement("SELECT * FROM pessoa where contatop=? AND nickname <> ?");
                                        ps.setString(1, cont);
                                        ps.setString(2, cod);
                                        rs = ps.executeQuery();
                                        if (rs.next()) {
                                            alert al = new alert(adm, audio);
                                            al.setVisible(true);
                                            String msg = "Contato já existe";
                                            String tit = "Pessoa existente";
                                            al.alertinput(tit, "erro", "", msg, "", "erro");

                                        } else {
                                            String dt = textf_dtnasc.getText();

                                            int year = Integer.parseInt(dt.substring(0, 4));
                                            int month = Integer.parseInt(dt.substring(5, 7));
                                            int day = Integer.parseInt(dt.substring(8, 10));
                                            //verifica se o mes é valido
                                            if (month < 1 || month > 12) {
                                                alert al = new alert(adm, audio);
                                                al.setVisible(true);
                                                String msg = "Mês inválido";
                                                String tit = "Data de nascimento inválido";
                                                al.alertinput(tit, "erro", "", msg, "", "erro");

                                            } else {
                                                //verifica se os dias existem no mes
                                                int[] daysInMonth = {31, (isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                                                if (day < 1 || day > daysInMonth[month - 1]) {
                                                    alert al = new alert(adm, audio);
                                                    al.setVisible(true);
                                                    String msg = "Dia inválido";
                                                    String tit = "Data de nascimento inválido";
                                                    al.alertinput(tit, "erro", "", msg, "", "erro");

                                                } else {
                                                    //caso sejam verifica se tem idade para ser cadastrado
                                                    LocalDate dataNasc = LocalDate.parse(dt);
                                                    LocalDate dataMinima = LocalDate.now().minusYears(18);

                                                    if (dataNasc.isAfter(dataMinima)) {
                                                        alert al = new alert(adm, audio);
                                                        al.setVisible(true);
                                                        String msg = "A idade mínima permitida";
                                                        String msg2 = "é de 18 anos";
                                                        String tit = "Idade mínima inválida";
                                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                                    } else {

                                                        email = textf_email.getText();
                                                        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                                                            alert al = new alert(adm, audio);
                                                            al.setVisible(true);
                                                            String msg = "Endereço de email inválido";
                                                            String tit = "Email inválido";
                                                            al.alertinput(tit, "erro", "", msg, "", "erro");

                                                        } else {
                                                            if (pass_senha.getText().equals(pass_senhaconfirm.getText())) {

                                                                String uf = textf_uf.getText().replaceAll("[^a-zA-Z]", "");
                                                                uf = uf.toUpperCase();
                                                                if (uf.length() != 2) {
                                                                    alert al = new alert(adm, audio);
                                                                    al.setVisible(true);
                                                                    String msg = "Valor deve seguir";
                                                                    String msg2 = "o seguinte formato:";
                                                                    String msg3 = "RJ,SP,SC,MT,GO,DF ...";
                                                                    String tit = "Valor da UF Errada";
                                                                    al.alertinput(tit, "erro", msg, msg2, msg3, "erro");

                                                                } else {
                                                                    String num = textf_num.getText().replaceAll("[^0-9]", "");
                                                                    if (num.isEmpty()) {
                                                                        alert al = new alert(adm, audio);
                                                                        al.setVisible(true);
                                                                        String msg = "Valor deve conter somente";
                                                                        String msg2 = "números ou não pode estar vazio";
                                                                        String tit = "Número não pode conter letras";
                                                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");

                                                                    } else {
                                                                        Pattern pattern = Pattern.compile(".*\\d.*");
                                                                        Matcher matcher1 = pattern.matcher(textf_uf.getText());
                                                                        Matcher matcher2 = pattern.matcher(textf_cid.getText());
                                                                        Matcher matcher3 = pattern.matcher(textf_bai.getText());
                                                                        Matcher matcher4 = pattern.matcher(textf_ende.getText());

                                                                        if (matcher1.matches()) {
                                                                            alert al = new alert(adm, audio);
                                                                            al.setVisible(true);
                                                                            String msg = "Unidade Federativa contém";
                                                                            String msg2 = "Números.";
                                                                            String tit = "UF incorreta";
                                                                            al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                                                        } else {
                                                                            if (matcher2.matches()) {
                                                                                alert al = new alert(adm, audio);
                                                                                al.setVisible(true);
                                                                                String msg = "Cidade contém Números.";
                                                                                String tit = "Cidade incorreta";
                                                                                al.alertinput(tit, "erro", "", msg, "", "erro");
                                                                            } else {
                                                                                if (matcher3.matches()) {
                                                                                    alert al = new alert(adm, audio);
                                                                                    al.setVisible(true);
                                                                                    String msg = "Bairro contém Números.";
                                                                                    String tit = "Bairro incorreta";
                                                                                    al.alertinput(tit, "erro", "", msg, "", "erro");
                                                                                } else {
                                                                                    if (matcher4.matches()) {
                                                                                        alert al = new alert(adm, audio);
                                                                                        al.setVisible(true);
                                                                                        String msg = "Endereço contém";
                                                                                        String msg2 = "Números.";
                                                                                        String tit = "Endereço incorreta";
                                                                                        al.alertinput(tit, "erro", msg, msg2, "", "erro");
                                                                                    } else {
                                                                                        // instanciando a classe UsuarioDAO do pacote dao e criando seu objeto dao
                                                                                        String tip = (String) comb_sexo.getSelectedItem();
                                                                                        String cep = textf_cep.getText().replaceAll("[^0-9]", "");

                                                                                        String sexo = "";
                                                                                        if (null != tip) {
                                                                                            switch (tip) {
                                                                                                case "Masculino":
                                                                                                    sexo = "M";
                                                                                                    break;
                                                                                                case "Feminino":
                                                                                                    sexo = "F";
                                                                                                    break;
                                                                                                case "Prefiro não informar":
                                                                                                    sexo = "O";
                                                                                                    break;
                                                                                                default:
                                                                                                    break;
                                                                                            }
                                                                                        }
                                                                                        String nickbusca = textf_nickBuscar.getText();
                                                                                        String endereco = textf_ende.getText();
                                                                                        String senha = pass_senha.getText();
                                                                                        nick = textf_nick.getText();
                                                                                        String sex = sexo;
                                                                                        dt = textf_dtnasc.getText();
                                                                                        String nome = textf_nome.getText();
                                                                                        cont = formatcont(textf_contato.getText());
                                                                                        email = textf_email.getText();
                                                                                        String bai = textf_bai.getText();
                                                                                        String end = textf_ende.getText();
                                                                                        String cmp = textf_cmp.getText();
                                                                                        String cid = textf_cid.getText();
                                                                                        String snome = textf_snome.getText();
                                                                                        atualizarpf(nickbusca, endereco, senha, nick, sex, dt, nome, cont, email, cep, uf, bai, end, num, cmp, cid, snome);
                                                                                        limpar();

                                                                                        fis = null;
                                                                                        ts = null;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                alert al = new alert(adm, audio);
                                                                al.setVisible(true);
                                                                String msg = "As senhas não estão iguais";
                                                                String tit = "Senhas desiquais";
                                                                al.alertinput(tit, "erro", "", msg, "", "erro");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    } else {

                        buscarPF(cod);
                    }
                } else {
                    alert al = new alert(adm, audio);
                    al.setVisible(true);
                    String msg = "Nickname não existe";
                    String tit = "Pessoa Física inexistente";
                    al.alertinput(tit, "erro", "", msg, "", "erro");
                }

            } catch (Exception e) {
                e.printStackTrace();
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

    public static String formatcont(String cont) {
        String dd = cont.substring(1, 3);
        String num = cont.substring(4);
        cont = dd + num.substring(0, 5) + num.substring(6);

        return cont;
    }

    public void buscarPF(String nick) {

        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps;

        try {
            ps = connection.prepareStatement("SELECT * FROM pessoa JOIN fisica ON fisica.codp = pessoa.pcod JOIN endereco ON endereco.endcod = pessoa.endcodend WHERE pessoa.nickname=?;");
            ps.setString(1, nick);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                alert al = new alert(adm, audio);
                al.audios("ok");
                String tx1 = (res.getString(1)); //nome
                String tx2 = (res.getString(2)); //contato
                String tx3 = (res.getString(3)); //email
                String tx4 = (res.getString(5)); //nick
                String tx5 = (res.getString(6)); //senha
                String tx6 = (res.getString(6)); //confirm senha
                String tx7 = (res.getString(7)); // codigo pf
                String tx8 = (res.getString(11)); //sobrenome
                
                String tx9 = (res.getString("cep"));
                String tx10 = (res.getString("uf"));
                String tx11 = (res.getString("endereco"));
                String tx12 = (res.getString("cidade"));
                String tx13 = (res.getString("bairro"));
                String tx14 = (res.getString("numero"));
                String tx15 = (res.getString("cmpt"));
                String tx16 = (res.getString("dt_nascimento")); //nome
                String tx17 = (res.getString("sexo")); //contato

                textf_nome.setText(tx1);
                textf_contato.setText(tx2);
                textf_email.setText(tx3);
                textf_nick.setText(tx4);
                pass_senha.setText(tx5);
                pass_senhaconfirm.setText(tx6);
                codpf = tx7;
                textf_snome.setText(tx8);
                
                textf_cep.setText(tx9);
                textf_uf.setText(tx10);
                textf_ende.setText(tx11);
                textf_cid.setText(tx12);
                textf_bai.setText(tx13);
                textf_num.setText(tx14);
                textf_cmp.setText(tx15);
                
               // String tx1 = (res.getString(13));
                String tip = tx17;
                String sx="";
                if (null != tip) {
                    switch (tip) {
                        case "M":
                            sx = "Masculino";
                            break;
                        case "F":
                            sx = "Feminino";
                            break;
                        case "O":
                            sx = "Prefiro não informar";
                            break;
                        default:
                            break;
                    }
                }

                textf_dtnasc.setText(tx16);
                comb_sexo.setSelectedItem(sx);

                imageBytes = res.getBytes(8);

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
                textf_bai.setForeground(Color.black);
                textf_cep.setForeground(Color.black);
                textf_cid.setForeground(Color.black);
                textf_uf.setForeground(Color.black);
                textf_ende.setForeground(Color.black);
                textf_cmp.setForeground(Color.black);
                textf_num.setForeground(Color.black);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String atualizarpf(String nickbusca, String endereco, String senha, String nick, String sexo, String dt, String nome, String cont, String email, String cep, String uf, String bai, String end, String num, String cmp, String cid, String snome) {
        String status = "";

        this.connection = new ConnectionFactory().getConnection();
        PreparedStatement ps;

        try {
            ps = connection.prepareStatement("UPDATE pessoa SET nomep=?, contatop=?, emailp=?, senha=?, imgperfil=?,nickname=?,snomep=? WHERE nickname=?");

            ps.setString(1, nome);
            ps.setString(2, cont);
            ps.setString(3, email);
            ps.setString(4, senha);
            if (fis == null && ts == null && imageBytes != null) {
                ps.setBlob(5, new ByteArrayInputStream(imageBytes), imageBytes.length);

            } else {
                ps.setBlob(5, fis, tamanho);
            }
            ps.setString(6, nick);
            ps.setString(7, snome);
            ps.setString(8, nickbusca);
            int i = ps.executeUpdate();

            ps = connection.prepareStatement("UPDATE fisica SET dt_nascimento=?, sexo=? WHERE codp=(SELECT pcod FROM pessoa WHERE nickname=?)");
            ps.setString(1, dt);
            ps.setString(2, sexo);
            ps.setString(3, nick);
            i = ps.executeUpdate();

            ps = connection.prepareStatement("UPDATE endereco SET cep=?, uf=?, bairro=?, endereco=?, numero=?, cmpt=?, cidade=? WHERE endcod=(SELECT endcodend FROM pessoa WHERE nickname=?)");
            ps.setString(1, cep);
            ps.setString(2, uf);
            ps.setString(3, bai);
            ps.setString(4, end);
            ps.setString(5, num);
            ps.setString(6, cmp);
            ps.setString(7, cid);
            ps.setString(8, nick);
            i = ps.executeUpdate();

            if (i != 0) {
                alert al = new alert(adm, audio);
                al.setVisible(true);
                String msg = "Detalhes de Pessoa Física";
                String msg2 = "atualizados com sucesso";
                String tit = "Atualização de Pessoa Física";
                al.alertinput(tit, "ok", msg, msg2, "", "sucesso");

            } else {
                alert al = new alert(adm, audio);
                al.setVisible(true);
                String msg = "etalhes de Pessoa";
                String msg2 = "Física não atualizados";
                String tit = "Atualização de Pessoa Física";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BGaudio = new javax.swing.ButtonGroup();
        PFundo = new javax.swing.JPanel();
        Plogin = new javax.swing.JPanel();
        label_dtnasc = new javax.swing.JLabel();
        textf_dtnasc = new javax.swing.JFormattedTextField();
        label_contato = new javax.swing.JLabel();
        textf_contato = new javax.swing.JFormattedTextField();
        label_nick = new javax.swing.JLabel();
        textf_nick = new javax.swing.JTextField();
        label_ende = new javax.swing.JLabel();
        label_email = new javax.swing.JLabel();
        textf_email = new javax.swing.JTextField();
        label_senha = new javax.swing.JLabel();
        pass_senha = new javax.swing.JPasswordField();
        label_senhaconfirm = new javax.swing.JLabel();
        pass_senhaconfirm = new javax.swing.JPasswordField();
        comb_sexo = new javax.swing.JComboBox<>();
        label_sexo = new javax.swing.JLabel();
        textf_snome = new javax.swing.JTextField();
        label_nome1 = new javax.swing.JLabel();
        label_cep = new javax.swing.JLabel();
        textf_num = new javax.swing.JTextField();
        textf_bai = new javax.swing.JTextField();
        textf_cep = new javax.swing.JFormattedTextField();
        textf_uf = new javax.swing.JTextField();
        textf_cid = new javax.swing.JTextField();
        textf_ende = new javax.swing.JTextField();
        textf_cmp = new javax.swing.JTextField();
        label_uf = new javax.swing.JLabel();
        label_end = new javax.swing.JLabel();
        label_cmp = new javax.swing.JLabel();
        label_uf1 = new javax.swing.JLabel();
        label_uf2 = new javax.swing.JLabel();
        label_uf3 = new javax.swing.JLabel();
        textf_nome = new javax.swing.JTextField();
        label_nome = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        L_caractersb = new javax.swing.JLabel();
        L_caracternm = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        L_caracternk = new javax.swing.JLabel();
        L_caracterem = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        L_caractercs = new javax.swing.JLabel();
        L_caracters = new javax.swing.JLabel();
        btn_atualizar = new SwingPerson.JbuttonArr();
        btn_limpar = new SwingPerson.JbuttonArr();
        L_caractercmp = new javax.swing.JLabel();
        L_caracternum = new javax.swing.JLabel();
        L_caracterende = new javax.swing.JLabel();
        L_caracterbai = new javax.swing.JLabel();
        L_caractercid = new javax.swing.JLabel();
        L_caracteruf = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        textf_nobgd1 = new javax.swing.JLabel();
        textf_nobgd2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        label_userBuscar = new javax.swing.JLabel();
        textf_nickBuscar = new javax.swing.JTextField();
        btn_att = new javax.swing.JButton();
        Lbuscar = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        label_foto = new javax.swing.JLabel();
        btnCARREGAR = new SwingPerson.JbuttonArr();
        BtnSemimg = new SwingPerson.JbuttonArr();
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
        setTitle("Pet Found - Atualizar Pessoa Física");
        setPreferredSize(new java.awt.Dimension(1026, 574));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PFundo.setBackground(new java.awt.Color(64, 33, 7));
        PFundo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PFundo.setPreferredSize(new java.awt.Dimension(970, 450));

        Plogin.setBackground(new java.awt.Color(255, 253, 243));

        label_dtnasc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_dtnasc.setText("DATA DE NASCIMENTO");

        textf_dtnasc.setBackground(new java.awt.Color(255, 253, 243));
        textf_dtnasc.setBorder(null);
        try {
            textf_dtnasc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("AAAA-AA-AA")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        textf_dtnasc.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_dtnasc.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_dtnasc.setPreferredSize(new java.awt.Dimension(200, 20));
        textf_dtnasc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_dtnascKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_dtnascKeyReleased(evt);
            }
        });

        label_contato.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_contato.setText("CONTATO (CELULAR)");

        textf_contato.setBackground(new java.awt.Color(255, 253, 243));
        textf_contato.setBorder(null);
        try {
            textf_contato.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        textf_contato.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_contato.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_contato.setPreferredSize(new java.awt.Dimension(200, 20));
        textf_contato.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_contatoKeyPressed(evt);
            }
        });

        label_nick.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_nick.setText("NICKNAME");

        textf_nick.setBackground(new java.awt.Color(255, 253, 243));
        textf_nick.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_nick.setBorder(null);
        textf_nick.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_nick.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_nick.setPreferredSize(new java.awt.Dimension(200, 20));
        textf_nick.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_nickKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_nickKeyReleased(evt);
            }
        });

        label_ende.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_ende.setText("ENDEREÇO");

        label_email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_email.setText("EMAIL");

        textf_email.setBackground(new java.awt.Color(255, 253, 243));
        textf_email.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_email.setBorder(null);
        textf_email.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_email.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_email.setPreferredSize(new java.awt.Dimension(200, 20));
        textf_email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_emailKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_emailKeyReleased(evt);
            }
        });

        label_senha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_senha.setText("SENHA");

        pass_senha.setBackground(new java.awt.Color(255, 253, 243));
        pass_senha.setBorder(null);
        pass_senha.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pass_senha.setMinimumSize(new java.awt.Dimension(0, 0));
        pass_senha.setPreferredSize(new java.awt.Dimension(200, 20));
        pass_senha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pass_senhaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pass_senhaKeyReleased(evt);
            }
        });

        label_senhaconfirm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_senhaconfirm.setText("CONFIRMAR SENHA");

        pass_senhaconfirm.setBackground(new java.awt.Color(255, 253, 243));
        pass_senhaconfirm.setBorder(null);
        pass_senhaconfirm.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pass_senhaconfirm.setMinimumSize(new java.awt.Dimension(0, 0));
        pass_senhaconfirm.setPreferredSize(new java.awt.Dimension(200, 20));
        pass_senhaconfirm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pass_senhaconfirmKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pass_senhaconfirmKeyReleased(evt);
            }
        });

        comb_sexo.setBackground(new java.awt.Color(255, 253, 243));
        comb_sexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Feminino", "Masculino", "Prefiro não informar" }));
        comb_sexo.setSelectedItem(null);
        comb_sexo.setBorder(null);
        comb_sexo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comb_sexo.setMinimumSize(new java.awt.Dimension(0, 0));
        comb_sexo.setPreferredSize(new java.awt.Dimension(200, 20));

        label_sexo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_sexo.setText("SEXO");

        textf_snome.setBackground(new java.awt.Color(255, 253, 243));
        textf_snome.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_snome.setBorder(null);
        textf_snome.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_snome.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_snome.setPreferredSize(new java.awt.Dimension(95, 20));
        textf_snome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_snomeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_snomeKeyReleased(evt);
            }
        });

        label_nome1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_nome1.setText("SOBRE NOME");

        label_cep.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label_cep.setText("CEP");

        textf_num.setBackground(new java.awt.Color(255, 253, 243));
        textf_num.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_num.setBorder(null);
        textf_num.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_num.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_num.setPreferredSize(new java.awt.Dimension(64, 20));
        textf_num.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_numKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_numKeyReleased(evt);
            }
        });

        textf_bai.setBackground(new java.awt.Color(255, 253, 243));
        textf_bai.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_bai.setBorder(null);
        textf_bai.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_bai.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_bai.setPreferredSize(new java.awt.Dimension(90, 20));
        textf_bai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_baiKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_baiKeyReleased(evt);
            }
        });

        textf_cep.setBackground(new java.awt.Color(255, 253, 243));
        textf_cep.setBorder(null);
        try {
            textf_cep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        textf_cep.setText("");
        textf_cep.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_cep.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_cep.setPreferredSize(new java.awt.Dimension(70, 20));
        textf_cep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_cepKeyPressed(evt);
            }
        });

        textf_uf.setBackground(new java.awt.Color(255, 253, 243));
        textf_uf.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_uf.setBorder(null);
        textf_uf.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_uf.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_uf.setPreferredSize(new java.awt.Dimension(30, 20));
        textf_uf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_ufKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_ufKeyReleased(evt);
            }
        });

        textf_cid.setBackground(new java.awt.Color(255, 253, 243));
        textf_cid.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_cid.setBorder(null);
        textf_cid.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_cid.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_cid.setPreferredSize(new java.awt.Dimension(90, 20));
        textf_cid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_cidKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_cidKeyReleased(evt);
            }
        });

        textf_ende.setBackground(new java.awt.Color(255, 253, 243));
        textf_ende.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_ende.setBorder(null);
        textf_ende.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_ende.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_ende.setPreferredSize(new java.awt.Dimension(175, 20));
        textf_ende.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_endeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_endeKeyReleased(evt);
            }
        });

        textf_cmp.setBackground(new java.awt.Color(255, 253, 243));
        textf_cmp.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_cmp.setBorder(null);
        textf_cmp.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_cmp.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_cmp.setPreferredSize(new java.awt.Dimension(105, 21));
        textf_cmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_cmpKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_cmpKeyReleased(evt);
            }
        });

        label_uf.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label_uf.setText("UF");

        label_end.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label_end.setText("Endereço");

        label_cmp.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label_cmp.setText("Complemento");

        label_uf1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label_uf1.setText("Cidade");

        label_uf2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label_uf2.setText("Bairro");

        label_uf3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label_uf3.setText("Número");

        textf_nome.setBackground(new java.awt.Color(255, 253, 243));
        textf_nome.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_nome.setBorder(null);
        textf_nome.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textf_nome.setMinimumSize(new java.awt.Dimension(0, 0));
        textf_nome.setPreferredSize(new java.awt.Dimension(95, 20));
        textf_nome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_nomeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_nomeKeyReleased(evt);
            }
        });

        label_nome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_nome.setText("NOME");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caractersb.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractersb.setText("0");

        L_caracternm.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracternm.setText("0");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caracternk.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracternk.setText("0");

        L_caracterem.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterem.setText("0");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caractercs.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercs.setText("0");

        L_caracters.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracters.setText("0");

        btn_atualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/atualizar.png"))); // NOI18N
        btn_atualizar.setToolTipText("Atualizar Informações de Pessoa Física");
        btn_atualizar.setMaximumSize(new java.awt.Dimension(0, 0));
        btn_atualizar.setMinimumSize(new java.awt.Dimension(60, 60));
        btn_atualizar.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizarActionPerformed(evt);
            }
        });

        btn_limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/apagar.png"))); // NOI18N
        btn_limpar.setToolTipText("Limpar Todos os Campos");
        btn_limpar.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparActionPerformed(evt);
            }
        });

        L_caractercmp.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercmp.setText("0");

        L_caracternum.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracternum.setText("0");

        L_caracterende.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterende.setText("0");

        L_caracterbai.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterbai.setText("0");

        L_caractercid.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercid.setText("0");

        L_caracteruf.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracteruf.setText("0");

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(70, 3));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(30, 3));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(90, 3));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(90, 3));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(175, 3));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(64, 3));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(105, 3));

        textf_nobgd1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        textf_nobgd1.setForeground(new java.awt.Color(204, 51, 0));
        textf_nobgd1.setText("*");
        textf_nobgd1.setToolTipText("Campo não Obrigatório");
        textf_nobgd1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        textf_nobgd1.setPreferredSize(new java.awt.Dimension(15, 20));

        textf_nobgd2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        textf_nobgd2.setForeground(new java.awt.Color(204, 51, 0));
        textf_nobgd2.setText("*");
        textf_nobgd2.setToolTipText("Campo não Obrigatório");
        textf_nobgd2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        textf_nobgd2.setPreferredSize(new java.awt.Dimension(15, 13));

        javax.swing.GroupLayout PloginLayout = new javax.swing.GroupLayout(Plogin);
        Plogin.setLayout(PloginLayout);
        PloginLayout.setHorizontalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PloginLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textf_nome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_nome))
                                .addGap(10, 10, 10)
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textf_snome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addComponent(label_nome1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textf_nobgd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(label_nick)
                                    .addComponent(textf_nick, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(PloginLayout.createSequentialGroup()
                                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(L_caracternm)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(L_caractersb, javax.swing.GroupLayout.Alignment.TRAILING)))
                                .addComponent(L_caracternk)
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(label_senha)
                                    .addComponent(pass_senha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                                .addComponent(L_caracters)
                                .addGap(8, 8, 8)))
                        .addGap(20, 20, 20)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(L_caracterem)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label_dtnasc)
                                        .addComponent(textf_dtnasc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textf_contato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_contato)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label_senhaconfirm)
                                        .addComponent(pass_senhaconfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(L_caractercs))
                                .addGap(30, 30, 30)
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textf_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_email)
                                    .addComponent(label_sexo)
                                    .addComponent(comb_sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_ende)
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(textf_cep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label_cep)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label_uf)
                                            .addComponent(textf_uf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(L_caracteruf, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textf_cid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_uf1)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(L_caractercid))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(textf_bai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_uf2))
                                    .addComponent(L_caracterbai, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(textf_ende, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_end))
                                    .addComponent(L_caracterende, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label_uf3)
                                        .addComponent(textf_num, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(L_caracternum, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(PloginLayout.createSequentialGroup()
                                                .addComponent(label_cmp)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(textf_nobgd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(textf_cmp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                                        .addGap(106, 106, 106)
                                        .addComponent(L_caractercmp))))
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(52, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(305, 305, 305))
        );
        PloginLayout.setVerticalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PloginLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(label_nome1)
                                            .addComponent(textf_nobgd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textf_snome, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addComponent(label_nome)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(PloginLayout.createSequentialGroup()
                                                .addComponent(label_sexo)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(PloginLayout.createSequentialGroup()
                                                .addComponent(label_dtnasc)
                                                .addGap(6, 6, 6)))
                                        .addGap(0, 0, 0)
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(textf_dtnasc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(comb_sexo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(L_caractersb)
                            .addComponent(L_caracternm))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(label_nick)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textf_nick, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(label_contato)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textf_contato, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addComponent(label_email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_email, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(L_caracternk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_senha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pass_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(L_caracterem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label_senhaconfirm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pass_senhaconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(L_caractercs)
                            .addComponent(L_caracters))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_ende)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_cep, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_uf)
                            .addComponent(label_uf1)
                            .addComponent(label_uf2)
                            .addComponent(label_end)
                            .addComponent(label_uf3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textf_cep, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textf_uf, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textf_cid, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textf_bai, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textf_ende, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textf_num, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_cmp)
                            .addComponent(textf_nobgd1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textf_cmp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(L_caractercmp)
                    .addComponent(L_caracternum)
                    .addComponent(L_caracterende)
                    .addComponent(L_caracterbai)
                    .addComponent(L_caractercid)
                    .addComponent(L_caracteruf))
                .addGap(19, 19, 19)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Atualizar Pessoa Física");

        jPanel1.setBackground(new java.awt.Color(255, 253, 243));

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

        btn_att.setBackground(new java.awt.Color(255, 253, 243));
        btn_att.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_att.setForeground(new java.awt.Color(51, 51, 51));
        btn_att.setText("Não sei o Nickname");
        btn_att.setToolTipText("<html>Sair da Tela E buscar<br> O Nickname da Pessoa Física</html>");
        btn_att.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_att.setPreferredSize(new java.awt.Dimension(147, 22));
        btn_att.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_attActionPerformed(evt);
            }
        });

        Lbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        Lbuscar.setToolTipText("Buscar Pessoa Física");
        Lbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Lbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LbuscarMouseClicked(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(363, 3));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label_userBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textf_nickBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Lbuscar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textf_nickBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_userBuscar))
                        .addGap(0, 0, 0)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Lbuscar))
                .addGap(8, 8, 8)
                .addComponent(btn_att, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
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

        btnCARREGAR.setText("Carregar");
        btnCARREGAR.setToolTipText("Carregar Uma Imagem");
        btnCARREGAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCARREGARActionPerformed(evt);
            }
        });

        BtnSemimg.setText("Remover");
        BtnSemimg.setToolTipText("Remover imagem da Pessoa Física");
        BtnSemimg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSemimgActionPerformed(evt);
            }
        });

        L_vlt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_voltarbr.png"))); // NOI18N
        L_vlt.setToolTipText("Sair de Atualizar");
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
                    .addComponent(L_vlt)
                    .addComponent(jLabel1)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addComponent(btnCARREGAR, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnSemimg, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Plogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        PFundoLayout.setVerticalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(83, 83, 83)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCARREGAR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnSemimg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(L_vlt)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PFundoLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Plogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(PFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 500));

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
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo atulizar Pessoa Física " + textf_nickBuscar.getText() + "?\nUma vez atulizado, a informação irá mudar para sempre.");
        if (escolha == 0) {
            String nick = textf_nickBuscar.getText();
            tipo = 1;
            verificar(nick);
        }
    }

    public void bus() {
        String nick = textf_nickBuscar.getText();
        tipo = 2;
        verificar(nick);
    }

    public void vlt() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Sair?");
        if (escolha == 0) {
            audios("tc");
//            PFisica pf = new PFisica(adm, audio);
            // pf.setVisible(true);
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

    public void inm() {
        alert al = new alert(adm, audio);
        al.setVisible(true);
        String msg = "Indisponível no momento";
        String tit = "Página em manutenção";
        al.alertinput(tit, "info", "", msg, "", "info");
    }
    private void textf_nickBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_nickBuscarMouseClicked
        limpar();

    }//GEN-LAST:event_textf_nickBuscarMouseClicked

    private void textf_nickBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nickBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            bus();
        }
    }//GEN-LAST:event_textf_nickBuscarKeyPressed

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

    private void textf_dtnascKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_dtnascKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_dtnascKeyPressed

    private void textf_contatoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_contatoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_contatoKeyPressed

    private void textf_nickKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nickKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_nickKeyPressed

    private void textf_nomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_nomeKeyPressed

    private void textf_emailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_emailKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_emailKeyPressed

    private void pass_senhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_pass_senhaKeyPressed

    private void pass_senhaconfirmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaconfirmKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_pass_senhaconfirmKeyPressed

    private void RatvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RatvaActionPerformed
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        if (mixers.length == 0) {
            audio = "off";
            alert al = new alert(adm, audio);
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

    private void textf_snomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_snomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_snomeKeyPressed

    private void textf_numKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_numKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_numKeyPressed

    private void textf_baiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_baiKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_baiKeyPressed

    private void textf_cepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_cepKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_cepKeyPressed

    private void textf_ufKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_ufKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_ufKeyPressed

    private void textf_cidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_cidKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_cidKeyPressed

    private void textf_endeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_endeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_endeKeyPressed

    private void textf_cmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_cmpKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            att();
        }
    }//GEN-LAST:event_textf_cmpKeyPressed

    private void btn_attActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_attActionPerformed
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja ir para a tela de Buscar Pessoa Física?");
        if (escolha == 0) {
            audios("tc");
            dispose();
        }


    }//GEN-LAST:event_btn_attActionPerformed

    private void LbuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LbuscarMouseClicked
        bus();
    }//GEN-LAST:event_LbuscarMouseClicked

    private void BtnSemimgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSemimgActionPerformed
        TirarFoto();
    }//GEN-LAST:event_BtnSemimgActionPerformed

    private void btnCARREGARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCARREGARActionPerformed
        audios("cl");
        CarregarFoto();
    }//GEN-LAST:event_btnCARREGARActionPerformed

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        audios("cl");
        lmp();
    }//GEN-LAST:event_btn_limparActionPerformed

    private void btn_atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizarActionPerformed
        att();
    }//GEN-LAST:event_btn_atualizarActionPerformed

    private void L_vltMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_vltMouseClicked
        vlt();
    }//GEN-LAST:event_L_vltMouseClicked

    private void textf_nomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nomeKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_nomeKeyReleased

    private void textf_snomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_snomeKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_snomeKeyReleased

    private void textf_dtnascKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_dtnascKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_dtnascKeyReleased

    private void textf_nickKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nickKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_nickKeyReleased

    private void textf_emailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_emailKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_emailKeyReleased

    private void pass_senhaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaKeyReleased
        tamanho();
    }//GEN-LAST:event_pass_senhaKeyReleased

    private void pass_senhaconfirmKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass_senhaconfirmKeyReleased
        tamanho();
    }//GEN-LAST:event_pass_senhaconfirmKeyReleased

    private void textf_ufKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_ufKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_ufKeyReleased

    private void textf_cidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_cidKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_cidKeyReleased

    private void textf_baiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_baiKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_baiKeyReleased

    private void textf_endeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_endeKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_endeKeyReleased

    private void textf_numKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_numKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_numKeyReleased

    private void textf_cmpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_cmpKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_cmpKeyReleased

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String ad = "";
                String au = "off";
                String user = "";
                new PFisicaAtualizar(ad, au, user).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGaudio;
    private SwingPerson.JbuttonArr BtnSemimg;
    private javax.swing.JLabel L_caracterbai;
    private javax.swing.JLabel L_caractercid;
    private javax.swing.JLabel L_caractercmp;
    private javax.swing.JLabel L_caractercs;
    private javax.swing.JLabel L_caracterem;
    private javax.swing.JLabel L_caracterende;
    private javax.swing.JLabel L_caracternk;
    private javax.swing.JLabel L_caracternm;
    private javax.swing.JLabel L_caracternum;
    private javax.swing.JLabel L_caracters;
    private javax.swing.JLabel L_caractersb;
    private javax.swing.JLabel L_caracteruf;
    private javax.swing.JLabel L_vlt;
    private javax.swing.JLabel Lbuscar;
    private javax.swing.JMenu Mopc;
    private javax.swing.JPanel PFundo;
    private javax.swing.JPanel Plogin;
    private javax.swing.JRadioButtonMenuItem Ratva;
    private javax.swing.JRadioButtonMenuItem Rdsta;
    private SwingPerson.JbuttonArr btnCARREGAR;
    private javax.swing.JButton btn_att;
    private SwingPerson.JbuttonArr btn_atualizar;
    private SwingPerson.JbuttonArr btn_limpar;
    private javax.swing.JComboBox<String> comb_sexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JLabel label_cep;
    private javax.swing.JLabel label_cmp;
    private javax.swing.JLabel label_contato;
    private javax.swing.JLabel label_dtnasc;
    private javax.swing.JLabel label_email;
    private javax.swing.JLabel label_end;
    private javax.swing.JLabel label_ende;
    private javax.swing.JLabel label_foto;
    private javax.swing.JLabel label_nick;
    private javax.swing.JLabel label_nome;
    private javax.swing.JLabel label_nome1;
    private javax.swing.JLabel label_senha;
    private javax.swing.JLabel label_senhaconfirm;
    private javax.swing.JLabel label_sexo;
    private javax.swing.JLabel label_uf;
    private javax.swing.JLabel label_uf1;
    private javax.swing.JLabel label_uf2;
    private javax.swing.JLabel label_uf3;
    private javax.swing.JLabel label_userBuscar;
    private javax.swing.JMenuItem menu_atu;
    private javax.swing.JMenuItem menu_bus;
    private javax.swing.JMenuItem menu_cad2;
    private javax.swing.JMenuItem menu_cf;
    private javax.swing.JMenu menu_sobre;
    private javax.swing.JMenuItem menu_texto;
    private javax.swing.JMenuItem menu_tf;
    private javax.swing.JMenuItem menu_voltar;
    private javax.swing.JMenuItem menu_voz;
    private javax.swing.JPasswordField pass_senha;
    private javax.swing.JPasswordField pass_senhaconfirm;
    private javax.swing.JTextField textf_bai;
    private javax.swing.JFormattedTextField textf_cep;
    private javax.swing.JTextField textf_cid;
    private javax.swing.JTextField textf_cmp;
    private javax.swing.JFormattedTextField textf_contato;
    private javax.swing.JFormattedTextField textf_dtnasc;
    private javax.swing.JTextField textf_email;
    private javax.swing.JTextField textf_ende;
    private javax.swing.JTextField textf_nick;
    private javax.swing.JTextField textf_nickBuscar;
    private javax.swing.JLabel textf_nobgd1;
    private javax.swing.JLabel textf_nobgd2;
    private javax.swing.JTextField textf_nome;
    private javax.swing.JTextField textf_num;
    private javax.swing.JTextField textf_snome;
    private javax.swing.JTextField textf_uf;
    // End of variables declaration//GEN-END:variables
}
