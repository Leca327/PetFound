package subGUI;

import Logar.versao;
import alert.alert;
import dao.PetDAO;
import factory.ConnectionFactory;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import modelo.PetMOD;
import starter.Icone;

public class PetCadastrar extends javax.swing.JFrame {

    private Connection connection;
    //instanciar objeto para fluxo de bytes.
    private FileInputStream fis;
    public String admin, audio;
    // variável global para armazenar tamanho da imagem em bytes.
    private int tamanho;
    String sexo, dt, hr, codimg, codadm;
    Boolean maxc;

    public PetCadastrar(String adm, String au) {
        initComponents();
        setIcon();
        admin = adm;
        audio = au;
        bus(adm);
        Status();
        buspessoa();
        tamanho();

        placeholder();
        textf_loc1.setText("UF");
        textf_loc1.setForeground(Color.GRAY);

        textf_loc2.setText("Cidade");
        textf_loc2.setForeground(Color.GRAY);
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

        textf_loc2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textf_loc2.getText().equals("Cidade")) {
                    textf_loc2.setText("");
                    textf_loc2.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textf_loc2.getText().isEmpty()) {
                    textf_loc2.setText("Cidade");
                    textf_loc2.setForeground(Color.GRAY);
                }
            }
        });
    }

    public void setIcon() {
        Icone ic = new Icone();
        String cm = ic.getIcon();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(cm)));
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
        textf_pf.setText("");
        textf_nomeP.setText("");
        comb_fai.setSelectedItem(null);
        textf_hist.setText("");
        textf_raca.setText("");
        textf_cor.setText("");
        comb_sexo.setSelectedItem(null);
        comb_porte.setSelectedItem(null);
        comb_tipop.setSelectedItem(null);
        textf_desc.setText("");
        textf_loc1.setText("UF");
        comb_tp.setSelectedItem(null);
        label_foto.setIcon(null);
        textf_loc2.setText("Cidade");
        textf_loc2.setForeground(new Color(102, 102, 102));
        textf_loc1.setForeground(new Color(102, 102, 102));
        comb_p.setSelectedItem(null);
        textf_pf1.setText("");
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
        label_foto.setIcon(null);
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

    public void tamanho() {
        //Nome
        String inputText = textf_nomeP.getText(); // Obtém o texto do campo de texto
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

        //User
        inputText = textf_cor.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 25;
        L_caractercor.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caractercor.setForeground(new Color(255, 51, 51));
            L_caractercor.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caractercor.setForeground(new Color(0, 0, 0));
            L_caractercor.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

        }

        //Senha
        inputText = textf_raca.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 25;
        L_caracterrc.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracterrc.setForeground(new Color(255, 51, 51));
            L_caracterrc.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracterrc.setForeground(new Color(0, 0, 0));
            L_caracterrc.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

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

        //Código
        inputText = textf_hist.getText(); // Obtém o texto do campo de texto
        numCaracteres = inputText.length();
        maxperm = 255;
        L_caracterhist.setText(Integer.toString(numCaracteres));
        if (numCaracteres > maxperm) {
            L_caracterhist.setForeground(new Color(255, 51, 51));
            L_caracterhist.setToolTipText("<html> A quantidade de Caracter é muito Grande <BR> A quantidade máxima Permitidade é <html>" + maxperm);

        } else {
            L_caracterhist.setForeground(new Color(0, 0, 0));
            L_caracterhist.setToolTipText("<html>A quantidade máxima Permitidade de Caracter é <html>" + maxperm);

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
        inputText = textf_loc2.getText(); // Obtém o texto do campo de texto
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
        maxc = !(textf_cor.getText().length() <= 25 && textf_nomeP.getText().length() <= 25 && textf_raca.getText().length() <= 25 && textf_desc.getText().length() <= 255 && textf_hist.getText().length() <= 255 && textf_loc1.getText().length() <= 2 && textf_loc2.getText().length() <= 25 && textf_pf.getText().length() <= 25);
        return maxc;
    }

    public void cadastro() {
        Boolean perm = maxperm();
        if (perm == false) {
            if ((admin == null) || (admin == "")) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Administrador não está logado";
                String tit = "Admin nulo";
                al.alertinput(tit, "erro", "", msg, "", "erro");

            } else {
                this.connection = new ConnectionFactory().getConnection();
                PreparedStatement ps;
                String status;
                String nome = textf_nomeP.getText();

                try {
                    if ((textf_nomeP.getText().isEmpty()) || (comb_fai.getSelectedItem() == null) || (comb_tipop.getSelectedItem() == null) || (textf_hist.getText().isEmpty()) || (textf_cor.getText().isEmpty()) || (comb_porte.getSelectedItem() == null) || (textf_raca.getText().isEmpty()) || (textf_desc.getText().isEmpty()) || (comb_sexo.getSelectedItem() == null) || (comb_tp.getSelectedItem() == null) || (textf_loc1.getText().equals("UF")) || (textf_loc2.getText().equals("Cidade")) || (textf_pf.getText().isEmpty())) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Os campos não podem";
                        String msg2 = "retornar vazios";
                        String tit = "Campo(s) de Texto Vazio(s)";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");

                    } else {
                        ps = connection.prepareStatement("select * from pessoa where pcod=?;");
                        ps.setString(1, textf_pf.getText());
                        ResultSet rs = ps.executeQuery();
                        if (!rs.next()) {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Código de Pessoa não existe";
                            String tit = "Pessoa inexistente";
                            al.alertinput(tit, "erro", "", msg, "", "erro");

                        } else {
                            String p = textf_pf.getText();
                            String tipop = p.substring(0, 2);
                            String anc = (String) comb_tp.getSelectedItem();
                            if ((tipop.equals("PJ")) && (!anc.equals("Adocao"))) {
                                String sql = "SELECT * FROM juridica WHERE cod_p = ?";

                                ps = connection.prepareStatement(sql);
                                ps.setString(1, textf_pf.getText());
                                rs = ps.executeQuery();
                                if (rs.next()) {
                                    String tipoj = rs.getString("tipoj");
                                    if (tipoj.equals("Empresa")) {
                                        alert al = new alert(admin, audio);
                                        al.setVisible(true);
                                        String msg = "Indisponível para Pessoa";
                                        String msg2 = "Jurídica do Tipo Empresa.";
                                        String msg3 = "Somente anúncio de adoção";
                                        String tit = "Anuncio indisponível";
                                        al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                                        return;
                                    }
                                }
                            }

                            if ((tipop.equals("PF")) && (!anc.equals("Adocao"))) {
                                alert al = new alert(admin, audio);
                                al.setVisible(true);
                                String msg = "Indisponível para Pessoa";
                                String msg2 = "Física só é possível.";
                                String msg3 = "Anúncio de adoção";
                                String tit = "Anuncio indisponível";
                                al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
                            } else {
                                Pattern pattern = Pattern.compile(".*\\d.*");
                                Matcher matcher1 = pattern.matcher(textf_loc1.getText());
                                Matcher matcher2 = pattern.matcher(textf_loc2.getText());
                                if (matcher1.matches()) {
                                    alert al = new alert(admin, audio);
                                    al.setVisible(true);
                                    String msg = "Unidade Federativa contém";
                                    String msg2 = "Números.";
                                    String tit = "UF incorreta";
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
                                        if (matcher2.matches()) {
                                            alert al = new alert(admin, audio);
                                            al.setVisible(true);
                                            String msg = "Cidade contém Números.";
                                            String tit = "Cidade incorreta";
                                            al.alertinput(tit, "erro", "", msg, "", "erro");
                                        } else {
                                            // Se a entrada for válida, você pode extrair apenas o número da idade e armazená-lo em uma variável.
                                            // fazendo a validação dos dados
                                            String petCode = generatePETCode(nome, textf_pf.getText());
                                            PetMOD pet = new PetMOD();

                                            String tip = (String) comb_sexo.getSelectedItem();

                                            if ("Macho".equals(tip)) {
                                                sexo = "M";

                                            } else if ("Femea".equals(tip)) {
                                                sexo = "F";
                                            }
                                            dt = DtAtual();
                                            hr = HrAtual();
                                            codimg = codimg(nome, textf_pf.getText());
                                            pet.setCodpet(petCode);
                                            pet.setNomepet(textf_nomeP.getText());
                                            pet.setFai_ida((String) comb_fai.getSelectedItem()/*+ "-" + textf_ida*/);
                                            pet.setHistoria(textf_hist.getText());
                                            pet.setCor_pel(textf_cor.getText());
                                            pet.setRaca(textf_raca.getText());
                                            pet.setPorte((String) comb_porte.getSelectedItem());
                                            pet.setSexo(sexo);
                                            pet.setDescpet(textf_desc.getText());
                                            pet.setTipoa((String) comb_tp.getSelectedItem());
                                            pet.setAdmcad(codadm);
                                            if (fis != null) {
                                                pet.setImg(fis);
                                                pet.setTamanho(tamanho);
                                                pet.setCodimg(codimg);
                                            }
                                            pet.setDtp(dt);
                                            pet.setHrp(hr);
                                            pet.setPf(textf_pf.getText());
                                            pet.setTipo((String) comb_tipop.getSelectedItem());
                                            pet.setEst(textf_loc1.getText());
                                            pet.setCid(textf_loc2.getText());
                                            // instanciando a classe UsuarioDAO do pacote dao e criando seu objeto dao
                                            PetDAO dao = new PetDAO();
                                            dao.adiciona(pet);

                                            alert al = new alert(admin, audio);
                                            al.setVisible(true);
                                            String msg = "Pet " + textf_nomeP.getText();
                                            String msg2 = "inserido(a) com sucesso";
                                            String tit = "Cadastro de Pet";
                                            al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                                            limpar();
                                            dispose();
                                            fis = null;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            alert al = new alert(admin, audio);
            al.setVisible(true);
            String msg = "Diminua a Quantidade ";
            String msg2 = "de Caracter Para";
            String msg3 = "o Cadastro.";
            String tit = "Excedeu o Limite de Caracter";
            al.alertinput(tit, "erro", msg, msg2, msg3, "erro");
        }
    }

    public static String generatePETCode(String nome, String p) {
        LocalDateTime now = LocalDateTime.now();

        // Remover os espaços das strings nome e adm
        nome = nome.replaceAll("\\s", "");
        p = p.replaceAll("\\s", "");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        String dayOfMonth = String.format("%02d", now.getDayOfMonth());
        String monthValue = String.format("%02d", now.getMonthValue());

        String petCode = "PET"
                + nome.charAt(0)
                + nome.charAt(nome.length() / 2)
                + nome.charAt(nome.length() - 1)
                + now.getHour()
                + now.getMinute()
                + p.substring(0, 6) // Modificação para pegar os 6 primeiros caracteres
                + dayOfMonth
                + monthValue
                + now.getYear();

        return petCode;
    }

    public static String codimg(String nome, String p) {
        LocalDateTime now = LocalDateTime.now();

        // Remover os espaços das strings nome e adm
        nome = nome.replaceAll("\\s", "");
        p = p.replaceAll("\\s", "");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        String dayOfMonth = String.format("%02d", now.getDayOfMonth());
        String monthValue = String.format("%02d", now.getMonthValue());

        String petCode = "IMGpet"
                + nome.charAt(0)
                + nome.charAt(nome.length() / 2)
                + nome.charAt(nome.length() - 1)
                + now.getHour()
                + now.getMinute()
                + p.substring(0, 6) // Modificação para pegar os 6 primeiros caracteres
                + dayOfMonth
                + monthValue
                + now.getYear();

        return petCode;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BGaudio = new javax.swing.ButtonGroup();
        PFundo = new javax.swing.JPanel();
        Plogin = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        L_caracternm = new javax.swing.JLabel();
        textf_nomeP = new javax.swing.JTextField();
        label_nomeP = new javax.swing.JLabel();
        textf_cor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        label_cor = new javax.swing.JLabel();
        L_caractercor = new javax.swing.JLabel();
        comb_tipop = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        label_tipo = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        L_caracterrc = new javax.swing.JLabel();
        textf_raca = new javax.swing.JTextField();
        label_raca = new javax.swing.JLabel();
        label_porte = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        comb_porte = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        label_sexo = new javax.swing.JLabel();
        comb_sexo = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        label_idade = new javax.swing.JLabel();
        comb_fai = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        label_ajuda1 = new javax.swing.JLabel();
        label_anun = new javax.swing.JLabel();
        comb_tp = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        label_loc = new javax.swing.JLabel();
        textf_loc2 = new javax.swing.JTextField();
        textf_loc1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        label_ajuda3 = new javax.swing.JLabel();
        label_ajuda = new javax.swing.JLabel();
        label_pf = new javax.swing.JLabel();
        textf_pf1 = new javax.swing.JTextField();
        label_desc = new javax.swing.JLabel();
        label_hist = new javax.swing.JLabel();
        textf_pf = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        label_ajudapf = new javax.swing.JLabel();
        label_ajuda2 = new javax.swing.JLabel();
        comb_p = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btn_cadastrar = new SwingPerson.JbuttonArr();
        btn_limpar = new SwingPerson.JbuttonArr();
        L_caracterdesc = new javax.swing.JLabel();
        L_caracterhist = new javax.swing.JLabel();
        L_caracterest = new javax.swing.JLabel();
        L_caractercid = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textf_desc = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        textf_hist = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        label_foto = new javax.swing.JLabel();
        L_vlt = new javax.swing.JLabel();
        btnCARREGAR1 = new SwingPerson.JbuttonArr();
        BtnSemimg1 = new SwingPerson.JbuttonArr();
        jMenuBar3 = new javax.swing.JMenuBar();
        Mopc = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        menu_cad = new javax.swing.JMenuItem();
        menu_tf = new javax.swing.JMenuItem();
        menu_cf = new javax.swing.JMenuItem();
        menu_lmp = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menu_voz = new javax.swing.JMenuItem();
        menu_texto = new javax.swing.JMenuItem();
        menu_sobre = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        Ratva = new javax.swing.JRadioButtonMenuItem();
        Rdsta = new javax.swing.JRadioButtonMenuItem();
        menu_voltar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pet Found - Cadastrar Pet");
        setPreferredSize(new java.awt.Dimension(1025, 496));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PFundo.setBackground(new java.awt.Color(64, 33, 7));
        PFundo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PFundo.setPreferredSize(new java.awt.Dimension(970, 450));

        Plogin.setBackground(new java.awt.Color(255, 253, 243));
        Plogin.setPreferredSize(new java.awt.Dimension(710, 408));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caracternm.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracternm.setText("0");

        textf_nomeP.setBackground(new java.awt.Color(255, 253, 243));
        textf_nomeP.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_nomeP.setToolTipText("");
        textf_nomeP.setBorder(null);
        textf_nomeP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_nomePKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_nomePKeyReleased(evt);
            }
        });

        label_nomeP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_nomeP.setText("NOME DO PET");

        textf_cor.setBackground(new java.awt.Color(255, 253, 243));
        textf_cor.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_cor.setToolTipText("");
        textf_cor.setBorder(null);
        textf_cor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_corKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_corKeyReleased(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(363, 3));

        label_cor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_cor.setText("COR DO PELO");

        L_caractercor.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercor.setText("0");

        comb_tipop.setBackground(new java.awt.Color(255, 253, 243));
        comb_tipop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cachorro", "Gato", "Passaro", "Roedor", "Reptil" }));
        comb_tipop.setSelectedIndex(-1);
        comb_tipop.setSelectedItem(null);
        comb_tipop.setBorder(null);
        comb_tipop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(363, 3));

        label_tipo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_tipo.setText("TIPO DE PET");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caracterrc.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterrc.setText("0");

        textf_raca.setBackground(new java.awt.Color(255, 253, 243));
        textf_raca.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_raca.setToolTipText("");
        textf_raca.setBorder(null);
        textf_raca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_racaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_racaKeyReleased(evt);
            }
        });

        label_raca.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_raca.setText("RAÇA");

        label_porte.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_porte.setText("PORTE");

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(363, 3));

        comb_porte.setBackground(new java.awt.Color(255, 253, 243));
        comb_porte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Grande", "Medio", "Pequeno" }));
        comb_porte.setSelectedIndex(-1);
        comb_porte.setSelectedItem(null);
        comb_porte.setToolTipText("");
        comb_porte.setBorder(null);
        comb_porte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(363, 3));

        label_sexo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_sexo.setText("SEXO");

        comb_sexo.setBackground(new java.awt.Color(255, 253, 243));
        comb_sexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Macho", "Femea" }));
        comb_sexo.setSelectedIndex(-1);
        comb_sexo.setSelectedItem(null);
        comb_sexo.setToolTipText("");
        comb_sexo.setBorder(null);
        comb_sexo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(363, 3));

        label_idade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_idade.setText("FAIXA-ETÁRIA");

        comb_fai.setBackground(new java.awt.Color(255, 253, 243));
        comb_fai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Filhote", "Jovem", "Adulto", "Senior", "Idoso" }));
        comb_fai.setSelectedIndex(-1);
        comb_fai.setSelectedItem(null);
        comb_fai.setToolTipText("");
        comb_fai.setBorder(null);
        comb_fai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comb_fai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                comb_faiKeyReleased(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(363, 3));

        label_ajuda1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajuda1.setToolTipText("<html>\n<b>Para Cachorro/Gato</b>\nFilhote até 6 meses de idade, jovem entre 6 meses a 2 anos, <br>\nadulto de 2 a 8 anos, Sênior de 8 a 12 anos <br>\ne idoso acima de 12 anos\n</html>");
        label_ajuda1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_ajuda1.setPreferredSize(new java.awt.Dimension(15, 20));

        label_anun.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_anun.setText("TIPO DE ANUNCIO");

        comb_tp.setBackground(new java.awt.Color(255, 253, 243));
        comb_tp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Padrinho", "Adocao", "Pad_Ado" }));
        comb_tp.setSelectedItem(null);
        comb_tp.setBorder(null);
        comb_tp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(363, 3));

        label_loc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_loc.setText("LOCAL DO PET");

        textf_loc2.setBackground(new java.awt.Color(255, 253, 243));
        textf_loc2.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_loc2.setForeground(new java.awt.Color(102, 102, 102));
        textf_loc2.setText("Cidade");
        textf_loc2.setBorder(null);
        textf_loc2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textf_loc2MouseClicked(evt);
            }
        });
        textf_loc2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textf_loc2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_loc2KeyReleased(evt);
            }
        });

        textf_loc1.setBackground(new java.awt.Color(255, 253, 243));
        textf_loc1.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        textf_loc1.setForeground(new java.awt.Color(102, 102, 102));
        textf_loc1.setText("UF");
        textf_loc1.setBorder(null);
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

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(363, 3));

        label_ajuda3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajuda3.setToolTipText("<html>\nO tipo de anuncio para o pet.<br>\npad_ado = Quando o anuncio é de adoção e para encontrar padrinhos <br>\n</html>");
        label_ajuda3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_ajuda3.setPreferredSize(new java.awt.Dimension(15, 20));

        label_ajuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajuda.setToolTipText("<html>\nLocal que o pet se encontra. <br>\nEx: Rio de Janeiro, Rj\n</html>");
        label_ajuda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_ajuda.setPreferredSize(new java.awt.Dimension(15, 20));

        label_pf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_pf.setText("COD PESSOA");

        textf_pf1.setEditable(false);
        textf_pf1.setBackground(new java.awt.Color(204, 204, 204));
        textf_pf1.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        textf_pf1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label_desc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_desc.setText("DESCRIÇÃO");

        label_hist.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_hist.setText("HISTÓRIA");

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

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(363, 3));

        label_ajudapf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajudapf.setToolTipText("<html> Código da Pessoa   <br> Responsável pelo Pet. </html>");
        label_ajudapf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        label_ajuda2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajuda2.setToolTipText("<html>\nConte a história do Pet. \n<br>Como foi encontrado, em que estado de saúde,<br> como ele se encontra atualmente...\n</html>");
        label_ajuda2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        comb_p.setBackground(new java.awt.Color(204, 204, 204));
        comb_p.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        comb_p.setSelectedItem(null);
        comb_p.setToolTipText("");
        comb_p.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel2.setText("Selecionar Código");
        jLabel2.setToolTipText("<html> Selecionar Código Na Comb Box <br> E colocar No Campo de Texto</html>");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        btn_cadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cadadc.png"))); // NOI18N
        btn_cadastrar.setToolTipText("Cadastre Um Pet\n");
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

        L_caracterdesc.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterdesc.setText("0");

        L_caracterhist.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterhist.setText("0");

        L_caracterest.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterest.setText("0");

        L_caractercid.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercid.setText("0");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 148, 44)));

        textf_desc.setBackground(new java.awt.Color(255, 253, 243));
        textf_desc.setColumns(20);
        textf_desc.setLineWrap(true);
        textf_desc.setRows(5);
        textf_desc.setWrapStyleWord(true);
        textf_desc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 148, 44)));
        textf_desc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_descKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(textf_desc);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 148, 44)));

        textf_hist.setBackground(new java.awt.Color(255, 253, 243));
        textf_hist.setColumns(20);
        textf_hist.setLineWrap(true);
        textf_hist.setRows(5);
        textf_hist.setWrapStyleWord(true);
        textf_hist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 148, 44)));
        textf_hist.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textf_histKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(textf_hist);

        javax.swing.GroupLayout PloginLayout = new javax.swing.GroupLayout(Plogin);
        Plogin.setLayout(PloginLayout);
        PloginLayout.setHorizontalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PloginLayout.createSequentialGroup()
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textf_nomeP, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(label_nomeP)
                                .addGap(141, 141, 141)
                                .addComponent(label_cor))
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(label_pf)
                                .addGap(7, 7, 7)
                                .addComponent(textf_pf1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(L_caracternm)
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(textf_raca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label_raca)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(L_caracterrc))
                                    .addComponent(label_idade)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(comb_fai, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label_ajuda1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(comb_p, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addComponent(jLabel2))
                                    .addComponent(textf_pf, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addComponent(comb_tp, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label_ajuda3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label_anun)
                                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(L_caractercor)
                                        .addComponent(label_porte, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(comb_porte, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textf_cor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label_desc)
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(L_caracterdesc)
                                            .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label_ajudapf)))))
                        .addGap(9, 9, 9)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_tipo)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comb_tipop, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_sexo)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comb_sexo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_loc)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(textf_loc1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(textf_loc2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_ajuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label_hist)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(L_caracterhist)
                                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PloginLayout.createSequentialGroup()
                                            .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(L_caracterest)
                                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(10, 10, 10)
                                            .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(L_caractercid)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_ajuda2))))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGap(301, 301, 301)
                        .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        PloginLayout.setVerticalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PloginLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addComponent(label_tipo)
                        .addGap(0, 0, 0)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comb_tipop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_nomeP)
                            .addComponent(label_cor, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(6, 6, 6)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textf_nomeP, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textf_cor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(L_caracternm))
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(L_caractercor)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_raca)
                    .addComponent(label_porte)
                    .addComponent(label_sexo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comb_porte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textf_raca, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(comb_sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(L_caracterrc))
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_idade)
                    .addComponent(label_anun)
                    .addComponent(label_loc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_ajuda1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_ajuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textf_loc1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textf_loc2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(label_ajuda3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comb_fai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comb_tp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(L_caracterest)
                    .addComponent(L_caractercid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_pf)
                    .addComponent(textf_pf1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_desc)
                    .addComponent(label_hist))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addComponent(textf_pf, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comb_p, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2))
                            .addComponent(label_ajudapf, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_ajuda2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(L_caracterdesc)
                            .addComponent(L_caracterhist))))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cadastrar Pet");

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        label_foto.setBackground(new java.awt.Color(255, 253, 243));
        label_foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        L_vlt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_voltarbr.png"))); // NOI18N
        L_vlt.setToolTipText("Sair de Cadastrar");
        L_vlt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        L_vlt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                L_vltMouseClicked(evt);
            }
        });

        btnCARREGAR1.setText("Carregar");
        btnCARREGAR1.setToolTipText("Carregar Uma Foto");
        btnCARREGAR1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCARREGAR1ActionPerformed(evt);
            }
        });

        BtnSemimg1.setText("Remover");
        BtnSemimg1.setToolTipText("Remover imagem do Pet\n");
        BtnSemimg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSemimg1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PFundoLayout = new javax.swing.GroupLayout(PFundo);
        PFundo.setLayout(PFundoLayout);
        PFundoLayout.setHorizontalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PFundoLayout.createSequentialGroup()
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addComponent(btnCARREGAR1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BtnSemimg1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(L_vlt)))
                .addGap(15, 15, 15)
                .addComponent(Plogin, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
                .addContainerGap())
        );
        PFundoLayout.setVerticalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(PFundoLayout.createSequentialGroup()
                .addComponent(Plogin, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(PFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(53, 53, 53)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnSemimg1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCARREGAR1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(L_vlt)
                .addGap(22, 22, 22))
        );

        getContentPane().add(PFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 430));

        jMenuBar3.setBackground(new java.awt.Color(255, 253, 243));
        jMenuBar3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuBar3.setMinimumSize(new java.awt.Dimension(210, 20));
        jMenuBar3.setPreferredSize(new java.awt.Dimension(210, 35));

        Mopc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/option.png"))); // NOI18N
        Mopc.setToolTipText("Aba de Opções");

        jMenu4.setText("Comandos Rápidos");
        jMenu4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menu_cad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_cad.setText("Cadastrar");
        menu_cad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_cad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_cadActionPerformed(evt);
            }
        });
        jMenu4.add(menu_cad);

        menu_tf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_tf.setText("Tirar Foto");
        menu_tf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_tfActionPerformed(evt);
            }
        });
        jMenu4.add(menu_tf);

        menu_cf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_cf.setText("Carregar Foto");
        menu_cf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_cf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_cfActionPerformed(evt);
            }
        });
        jMenu4.add(menu_cf);

        menu_lmp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_lmp.setText("Limpar");
        menu_lmp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_lmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_lmpActionPerformed(evt);
            }
        });
        jMenu4.add(menu_lmp);

        Mopc.add(jMenu4);

        jMenu5.setText("Acessibilidade");
        jMenu5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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
    public void lmp() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Mesmo Limpar todos os campos?");

        if (escolha == 0) {
            limpar();
        }
    }

    public void vlt() {
        audios("aviso");
        int escolha = JOptionPane.showConfirmDialog(null, "Deseja Sair?");
        if (escolha == 0) {
            audios("tc");
            //Pets pet = new Pets(admin, audio);
            //pet.setVisible(true);
            dispose();
        }
    }

    public void inm() {
        alert al = new alert(admin, audio);
        al.setVisible(true);
        String msg = "Indisponível no momento";
        String tit = "Página em manutenção";
        al.alertinput(tit, "info", "", msg, "", "info");
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

    private void menu_cadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cadActionPerformed
        cadastro();
    }//GEN-LAST:event_menu_cadActionPerformed

    private void menu_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tfActionPerformed
        TirarFoto();
    }//GEN-LAST:event_menu_tfActionPerformed

    private void menu_cfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cfActionPerformed
        CarregarFoto();
    }//GEN-LAST:event_menu_cfActionPerformed

    private void menu_lmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_lmpActionPerformed
        lmp();
    }//GEN-LAST:event_menu_lmpActionPerformed

    private void menu_vozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_vozActionPerformed
        inm();
    }//GEN-LAST:event_menu_vozActionPerformed

    private void menu_textoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_textoActionPerformed
        inm();
    }//GEN-LAST:event_menu_textoActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        audios("tci");
        versao v = new versao();
        v.buscarVersao(); // Buscar versões antes de exibir a janela
        v.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void RatvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RatvaActionPerformed
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        if (mixers.length == 0) {
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

    private void menu_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_voltarActionPerformed
        vlt();
    }//GEN-LAST:event_menu_voltarActionPerformed

    private void btnCARREGAR1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCARREGAR1ActionPerformed
        audios("cl");
        CarregarFoto();
    }//GEN-LAST:event_btnCARREGAR1ActionPerformed

    private void BtnSemimg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSemimg1ActionPerformed
        TirarFoto();
    }//GEN-LAST:event_BtnSemimg1ActionPerformed

    private void L_vltMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_vltMouseClicked
        vlt();
    }//GEN-LAST:event_L_vltMouseClicked

    private void textf_loc1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_loc1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_loc1KeyPressed

    private void textf_loc1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_loc1MouseClicked
        textf_loc1.setForeground(Color.black);
    }//GEN-LAST:event_textf_loc1MouseClicked

    private void textf_loc2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_loc2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_loc2KeyPressed

    private void textf_loc2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_loc2MouseClicked
        textf_loc2.setForeground(Color.black);
    }//GEN-LAST:event_textf_loc2MouseClicked

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
            cadastro();
        }
    }//GEN-LAST:event_textf_pfKeyPressed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        if (comb_p.getSelectedItem() == null || comb_p.getSelectedItem() == "") {

        } else {
            textf_pf.setText(comb_p.getSelectedItem().toString());
            bsp();
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void textf_racaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_racaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_racaKeyPressed

    private void textf_corKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_corKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_corKeyPressed

    private void textf_nomePKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nomePKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_nomePKeyPressed

    private void btn_cadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadastrarActionPerformed
        cadastro();
    }//GEN-LAST:event_btn_cadastrarActionPerformed

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        audios("cl");
        lmp();
    }//GEN-LAST:event_btn_limparActionPerformed

    private void comb_faiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comb_faiKeyReleased
        tamanho();
    }//GEN-LAST:event_comb_faiKeyReleased

    private void textf_racaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_racaKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_racaKeyReleased

    private void textf_nomePKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nomePKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_nomePKeyReleased

    private void textf_corKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_corKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_corKeyReleased

    private void textf_loc1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_loc1KeyReleased
        tamanho();
    }//GEN-LAST:event_textf_loc1KeyReleased

    private void textf_loc2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_loc2KeyReleased
        tamanho();
    }//GEN-LAST:event_textf_loc2KeyReleased

    private void textf_descKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_descKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_descKeyReleased

    private void textf_histKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_histKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_histKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String ad = "";
                String au = "off";
                new PetCadastrar(ad, au).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGaudio;
    private SwingPerson.JbuttonArr BtnSemimg1;
    private javax.swing.JLabel L_caractercid;
    private javax.swing.JLabel L_caractercor;
    private javax.swing.JLabel L_caracterdesc;
    private javax.swing.JLabel L_caracterest;
    private javax.swing.JLabel L_caracterhist;
    private javax.swing.JLabel L_caracternm;
    private javax.swing.JLabel L_caracterrc;
    private javax.swing.JLabel L_vlt;
    private javax.swing.JMenu Mopc;
    private javax.swing.JPanel PFundo;
    private javax.swing.JPanel Plogin;
    private javax.swing.JRadioButtonMenuItem Ratva;
    private javax.swing.JRadioButtonMenuItem Rdsta;
    private SwingPerson.JbuttonArr btnCARREGAR1;
    private SwingPerson.JbuttonArr btn_cadastrar;
    private SwingPerson.JbuttonArr btn_limpar;
    private javax.swing.JComboBox<String> comb_fai;
    private javax.swing.JComboBox<String> comb_p;
    private javax.swing.JComboBox<String> comb_porte;
    private javax.swing.JComboBox<String> comb_sexo;
    private javax.swing.JComboBox<String> comb_tipop;
    private javax.swing.JComboBox<String> comb_tp;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label_ajuda;
    private javax.swing.JLabel label_ajuda1;
    private javax.swing.JLabel label_ajuda2;
    private javax.swing.JLabel label_ajuda3;
    private javax.swing.JLabel label_ajudapf;
    private javax.swing.JLabel label_anun;
    private javax.swing.JLabel label_cor;
    private javax.swing.JLabel label_desc;
    private javax.swing.JLabel label_foto;
    private javax.swing.JLabel label_hist;
    private javax.swing.JLabel label_idade;
    private javax.swing.JLabel label_loc;
    private javax.swing.JLabel label_nomeP;
    private javax.swing.JLabel label_pf;
    private javax.swing.JLabel label_porte;
    private javax.swing.JLabel label_raca;
    private javax.swing.JLabel label_sexo;
    private javax.swing.JLabel label_tipo;
    private javax.swing.JMenuItem menu_cad;
    private javax.swing.JMenuItem menu_cf;
    private javax.swing.JMenuItem menu_lmp;
    private javax.swing.JMenu menu_sobre;
    private javax.swing.JMenuItem menu_texto;
    private javax.swing.JMenuItem menu_tf;
    private javax.swing.JMenuItem menu_voltar;
    private javax.swing.JMenuItem menu_voz;
    private javax.swing.JTextField textf_cor;
    private javax.swing.JTextArea textf_desc;
    private javax.swing.JTextArea textf_hist;
    private javax.swing.JTextField textf_loc1;
    private javax.swing.JTextField textf_loc2;
    private javax.swing.JTextField textf_nomeP;
    private javax.swing.JTextField textf_pf;
    private javax.swing.JTextField textf_pf1;
    private javax.swing.JTextField textf_raca;
    // End of variables declaration//GEN-END:variables
}
