package subGUI;

import Logar.versao;
import alert.alert;
import dao.ServicoDAO;
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
import modelo.ServicoMOD;
import starter.Icone;

public class ServicoCadastrar extends javax.swing.JFrame {

    private Connection connection;
    //instanciar objeto para fluxo de bytes.
    private FileInputStream fis;
    public String admin;
    // variável global para armazenar tamanho da imagem em bytes.
    private int tamanho;
    String sexo, dt, hr, codimg, codadm, audio;
    Boolean maxc;

    public ServicoCadastrar(String adm, String au) {
        initComponents();
        setIcon();
        admin = adm;
        audio = au;
        bus(adm);
        Status();
        tamanho();
        buspessoa();

        placeholder();
        textf_loc1.setText("UF");
        textf_loc1.setForeground(Color.GRAY);

        textf_loc5.setText("Cidade");
        textf_loc5.setForeground(Color.GRAY);
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
        textf_nome.setText("");
        textf_desc.setText("");
        textf_loc5.setText("Cidade");
        textf_loc1.setText("UF");
        textf_loc5.setForeground(new Color(102, 102, 102));
        textf_loc1.setForeground(new Color(102, 102, 102));
        textf_preco.setText("00000000");
        label_foto.setIcon(null);
        textf_pf.setText("");
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
        if (input.equals("R$000000.00")) {
            input = "0.00";
        }
        // Retorne o valor formatado.
        return input;
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
        maxc = !(textf_nome.getText().length() <= 50 && textf_desc.getText().length() <= 255 && textf_loc1.getText().length() <= 2 && textf_loc5.getText().length() <= 25 && textf_pf.getText().length() <= 25);
        return maxc;
    }

    public void cadastro() {
        Boolean perm = maxperm();
        if (perm == false) {
            if ((admin == null) || (admin == "")) {
                alert al = new alert(admin, audio);
                al.setVisible(true);
                String msg = "Administrador não";
                String msg2 = "está logado";
                String tit = "Admin nulo";
                al.alertinput(tit, "erro", msg, msg2, "", "erro");

            } else {
                this.connection = new ConnectionFactory().getConnection();
                PreparedStatement ps;
                String status;
                String nome = textf_nome.getText();
                ResultSet rs;

                try {
                    // fazendo a validação dos dados
                    if ((textf_nome.getText().isEmpty()) || (textf_desc.getText().isEmpty()) || (textf_loc1.getText().equals("UF")) || (textf_loc5.getText().equals("Cidade")) || (textf_pf.getText().isEmpty())) {
                        alert al = new alert(admin, audio);
                        al.setVisible(true);
                        String msg = "Os campos não podem";
                        String msg2 = "retornar vazios";
                        String tit = "Campo(s) de Texto Vazio(s)";
                        al.alertinput(tit, "erro", msg, msg2, "", "erro");

                    } else {

                        ps = connection.prepareStatement("select * from admin where admcod=?;");
                        ps.setString(1, codadm);
                        rs = ps.executeQuery();
                        if (!rs.next()) {
                            alert al = new alert(admin, audio);
                            al.setVisible(true);
                            String msg = "Código de Admin inexistente";
                            String msg2 = "não atualizados";
                            String tit = "Admin inexistente";
                            al.alertinput(tit, "erro", msg, msg2, "", "erro");

                        } else {
                            ps = connection.prepareStatement("select * from pessoa where pcod=?;");
                            ps.setString(1, textf_pf.getText());
                            rs = ps.executeQuery();
                            if (!rs.next()) {
                                alert al = new alert(admin, audio);
                                al.setVisible(true);
                                String msg = "Código de Pessoa inexistente";
                                String msg2 = "não atualizados";
                                String tit = "Pessoa inexistente";
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
                                            String svcCode = generateSVCCode(nome, textf_pf.getText());
                                            codimg = codimg(nome, textf_pf.getText());
                                            dt = DtAtual();
                                            hr = HrAtual();
                                            String preco = textf_preco.getText();
                                            ServicoMOD servico = new ServicoMOD();

                                            servico.setCodserv(svcCode);
                                            servico.setNomeserv(textf_nome.getText());
                                            servico.setDescserv(textf_desc.getText());
                                            servico.setEst(textf_loc1.getText());
                                            servico.setCid(textf_loc5.getText());
                                            servico.setPreco(formatpreco(preco));
                                            servico.setAdmcad(codadm);
                                            if (fis != null) {
                                                servico.setImg(fis);
                                                servico.setTamanho(tamanho);
                                                servico.setCodimg(codimg);
                                            }
                                            servico.setDtp(dt);
                                            servico.setHrp(hr);
                                            servico.setPf(textf_pf.getText());

                                            // instanciando a classe UsuarioDAO do pacote dao e criando seu objeto dao
                                            ServicoDAO dao = new ServicoDAO();
                                            dao.adiciona(servico);
                                            alert al = new alert(admin, audio);
                                            al.setVisible(true);
                                            String msg = "Serviço " + textf_nome.getText();
                                            String msg2 = " inserido com sucesso";
                                            String tit = "Cadastro de Serviço";
                                            al.alertinput(tit, "ok", msg, msg2, "", "sucesso");
                                            dispose();
                                            limpar();
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

    public static String generateSVCCode(String nome, String p) {
        LocalDateTime now = LocalDateTime.now();

        // Remover os espaços das strings nome e adm
        nome = nome.replaceAll("\\s", "");
        p = p.replaceAll("\\s", "");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        String dayOfMonth = String.format("%02d", now.getDayOfMonth());
        String monthValue = String.format("%02d", now.getMonthValue());

        String svcCode = "SVC"
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

    public static String codimg(String nome, String p) {
        LocalDateTime now = LocalDateTime.now();

        // Remover os espaços das strings nome e adm
        nome = nome.replaceAll("\\s", "");
        p = p.replaceAll("\\s", "");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        String dayOfMonth = String.format("%02d", now.getDayOfMonth());
        String monthValue = String.format("%02d", now.getMonthValue());

        String petCode = "IMGserv"
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
        textf_nome = new javax.swing.JTextField();
        label_nomeS = new javax.swing.JLabel();
        label_desc = new javax.swing.JLabel();
        textf_pf = new javax.swing.JTextField();
        label_pf = new javax.swing.JLabel();
        label_ajudapf = new javax.swing.JLabel();
        label_loc = new javax.swing.JLabel();
        label_ajuda = new javax.swing.JLabel();
        textf_preco = new javax.swing.JFormattedTextField();
        label_preco = new javax.swing.JLabel();
        textf_loc5 = new javax.swing.JTextField();
        textf_loc1 = new javax.swing.JTextField();
        textf_pf1 = new javax.swing.JTextField();
        comb_p = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        L_caracternm = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        L_caracterest = new javax.swing.JLabel();
        L_caractercid = new javax.swing.JLabel();
        L_caracterdesc = new javax.swing.JLabel();
        btn_cadastrar = new SwingPerson.JbuttonArr();
        btn_limpar = new SwingPerson.JbuttonArr();
        jScrollPane1 = new javax.swing.JScrollPane();
        textf_desc = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
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
        menu_cad = new javax.swing.JMenuItem();
        menu_tf = new javax.swing.JMenuItem();
        menu_cf = new javax.swing.JMenuItem();
        menu_lmp = new javax.swing.JMenuItem();
        menu_sobre = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        Ratva = new javax.swing.JRadioButtonMenuItem();
        Rdsta = new javax.swing.JRadioButtonMenuItem();
        menu_voltar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pet Found - Cadastrar Serviço");
        setPreferredSize(new java.awt.Dimension(880, 444));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PFundo.setBackground(new java.awt.Color(64, 33, 7));
        PFundo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PFundo.setPreferredSize(new java.awt.Dimension(970, 450));

        Plogin.setBackground(new java.awt.Color(255, 253, 243));
        Plogin.setPreferredSize(new java.awt.Dimension(604, 365));

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

        label_nomeS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_nomeS.setText("NOME DO SERVIÇO");

        label_desc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_desc.setText("DESCRIÇÃO");

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

        label_pf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_pf.setText("COD PESSOA");

        label_ajudapf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajudapf.setToolTipText("<html>\nCódigo da Pessoa   <br>\nResponsável pelo Pet.\n</html>");
        label_ajudapf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        label_loc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label_loc.setText("LOCAL DO SERVIÇO");

        label_ajuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ajuda.png"))); // NOI18N
        label_ajuda.setToolTipText("<html>\nRegião que a pessoa irá trabalhar <br>\nEx: Rio de Janeiro, Rj,Centro\n</html>");
        label_ajuda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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

        textf_pf1.setEditable(false);
        textf_pf1.setBackground(new java.awt.Color(204, 204, 204));
        textf_pf1.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        textf_pf1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caracternm.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracternm.setText("0");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(363, 3));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campotexto2.png"))); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(363, 3));

        L_caracterest.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterest.setText("0");

        L_caractercid.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caractercid.setText("0");

        L_caracterdesc.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        L_caracterdesc.setText("0");

        btn_cadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cadadc.png"))); // NOI18N
        btn_cadastrar.setToolTipText("Cadastre Um Serviço\n");
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

        javax.swing.GroupLayout PloginLayout = new javax.swing.GroupLayout(Plogin);
        Plogin.setLayout(PloginLayout);
        PloginLayout.setHorizontalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PloginLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(L_caracterdesc))
                            .addComponent(jScrollPane1))
                        .addGap(44, 44, 44))
                    .addGroup(PloginLayout.createSequentialGroup()
                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_nomeS)
                                    .addComponent(textf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(L_caracternm)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PloginLayout.createSequentialGroup()
                                                    .addComponent(label_pf)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(textf_pf1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(textf_pf, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(comb_p, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label_ajudapf))
                                    .addComponent(label_desc))
                                .addGap(71, 71, 71)
                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_loc)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_preco)
                                    .addComponent(textf_preco, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PloginLayout.createSequentialGroup()
                                        .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(PloginLayout.createSequentialGroup()
                                                .addComponent(textf_loc1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(textf_loc5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(PloginLayout.createSequentialGroup()
                                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(L_caracterest)
                                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(10, 10, 10)
                                                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(L_caractercid, javax.swing.GroupLayout.Alignment.TRAILING))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label_ajuda))))
                            .addGroup(PloginLayout.createSequentialGroup()
                                .addGap(194, 194, 194)
                                .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(41, Short.MAX_VALUE))))
        );
        PloginLayout.setVerticalGroup(
            PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PloginLayout.createSequentialGroup()
                .addGap(40, 40, 40)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_pf)
                    .addComponent(textf_pf1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_loc))
                .addGap(7, 7, 7)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textf_pf, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textf_loc1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textf_loc5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_ajudapf, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label_ajuda, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(L_caractercid)
                    .addComponent(L_caracterest)
                    .addComponent(comb_p, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_desc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(L_caracterdesc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cadastrar Serviço");

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
            .addGroup(PFundoLayout.createSequentialGroup()
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PFundoLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PFundoLayout.createSequentialGroup()
                                        .addComponent(btnCARREGAR1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(BtnSemimg1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(L_vlt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Plogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        PFundoLayout.setVerticalGroup(
            PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PFundoLayout.createSequentialGroup()
                .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PFundoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(28, 28, 28)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnSemimg1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCARREGAR1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addComponent(L_vlt)
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addComponent(Plogin, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        getContentPane().add(PFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 864, 370));

        jMenuBar3.setBackground(new java.awt.Color(255, 253, 243));
        jMenuBar3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuBar3.setMinimumSize(new java.awt.Dimension(210, 20));
        jMenuBar3.setPreferredSize(new java.awt.Dimension(210, 35));

        Mopc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/option.png"))); // NOI18N
        Mopc.setToolTipText("Aba de Opções");

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
//            Servicos scv = new Servicos(admin, audio);
            // scv.setVisible(true);
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


    private void menu_lmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_lmpActionPerformed
        lmp();
    }//GEN-LAST:event_menu_lmpActionPerformed

    private void menu_cadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cadActionPerformed
        cadastro();
    }//GEN-LAST:event_menu_cadActionPerformed

    private void menu_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_voltarActionPerformed
        vlt();
    }//GEN-LAST:event_menu_voltarActionPerformed

    private void menu_vozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_vozActionPerformed
        inm();
    }//GEN-LAST:event_menu_vozActionPerformed

    private void menu_textoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_textoActionPerformed
        inm();
    }//GEN-LAST:event_menu_textoActionPerformed

    private void menu_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tfActionPerformed
        TirarFoto();
    }//GEN-LAST:event_menu_tfActionPerformed

    private void menu_cfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cfActionPerformed
        CarregarFoto();
    }//GEN-LAST:event_menu_cfActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        audios("tci");
        versao v = new versao();
        v.buscarVersao(); // Buscar versões antes de exibir a janela
        v.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void textf_nomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_nomeKeyPressed

    private void textf_pfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_pfKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_pfKeyPressed

    private void textf_precoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_precoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_precoKeyPressed

    private void RatvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RatvaActionPerformed
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        if (mixers.length == 0) {
            audio = "off";
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

    private void textf_loc5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_loc5KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_loc5KeyPressed

    private void textf_loc1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_loc1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cadastro();
        }
    }//GEN-LAST:event_textf_loc1KeyPressed

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

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        textf_pf.setText(comb_p.getSelectedItem().toString());
        bsp();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void BtnSemimg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSemimg1ActionPerformed
        TirarFoto();
    }//GEN-LAST:event_BtnSemimg1ActionPerformed

    private void btnCARREGAR1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCARREGAR1ActionPerformed
        audios("cl");
        CarregarFoto();
    }//GEN-LAST:event_btnCARREGAR1ActionPerformed

    private void L_vltMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_vltMouseClicked
        vlt();
    }//GEN-LAST:event_L_vltMouseClicked

    private void btn_cadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadastrarActionPerformed
        cadastro();
    }//GEN-LAST:event_btn_cadastrarActionPerformed

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        audios("cl");
        lmp();
    }//GEN-LAST:event_btn_limparActionPerformed

    private void textf_nomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_nomeKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_nomeKeyReleased

    private void textf_loc1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_loc1KeyReleased
        tamanho();
    }//GEN-LAST:event_textf_loc1KeyReleased

    private void textf_loc5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_loc5KeyReleased
        tamanho();
    }//GEN-LAST:event_textf_loc5KeyReleased

    private void textf_descKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textf_descKeyReleased
        tamanho();
    }//GEN-LAST:event_textf_descKeyReleased

    private void textf_loc5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_loc5MouseClicked

        textf_loc5.setForeground(Color.black);
    }//GEN-LAST:event_textf_loc5MouseClicked

    private void textf_loc1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textf_loc1MouseClicked
        textf_loc1.setForeground(Color.black);
    }//GEN-LAST:event_textf_loc1MouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String ad = "";
                String au = "off";
                new ServicoCadastrar(ad, au).setVisible(true);
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
    private javax.swing.JMenu Mopc;
    private javax.swing.JPanel PFundo;
    private javax.swing.JPanel Plogin;
    private javax.swing.JRadioButtonMenuItem Ratva;
    private javax.swing.JRadioButtonMenuItem Rdsta;
    private SwingPerson.JbuttonArr btnCARREGAR1;
    private SwingPerson.JbuttonArr btn_cadastrar;
    private SwingPerson.JbuttonArr btn_limpar;
    private javax.swing.JComboBox<String> comb_p;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel label_ajuda;
    private javax.swing.JLabel label_ajudapf;
    private javax.swing.JLabel label_desc;
    private javax.swing.JLabel label_foto;
    private javax.swing.JLabel label_loc;
    private javax.swing.JLabel label_nomeS;
    private javax.swing.JLabel label_pf;
    private javax.swing.JLabel label_preco;
    private javax.swing.JMenuItem menu_cad;
    private javax.swing.JMenuItem menu_cf;
    private javax.swing.JMenuItem menu_lmp;
    private javax.swing.JMenu menu_sobre;
    private javax.swing.JMenuItem menu_texto;
    private javax.swing.JMenuItem menu_tf;
    private javax.swing.JMenuItem menu_voltar;
    private javax.swing.JMenuItem menu_voz;
    private javax.swing.JTextArea textf_desc;
    private javax.swing.JTextField textf_loc1;
    private javax.swing.JTextField textf_loc5;
    private javax.swing.JTextField textf_nome;
    private javax.swing.JTextField textf_pf;
    private javax.swing.JTextField textf_pf1;
    private javax.swing.JFormattedTextField textf_preco;
    // End of variables declaration//GEN-END:variables
}
