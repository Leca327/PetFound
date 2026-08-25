package modelo;

import java.io.FileInputStream;

public class ServicoMOD {

    String codserv;
    String nomeserv;
    String descserv;
    String preco;
    String admcad;
    FileInputStream img;
    int tamanho;
    String pf;
    String dtp;
    String hrp;
    String codimg;
    String est;
    String cid;

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getDtp() {
        return dtp;
    }

    public void setDtp(String dtp) {
        this.dtp = dtp;
    }

    public String getHrp() {
        return hrp;
    }

    public void setHrp(String hrp) {
        this.hrp = hrp;
    }

    public String getCodimg() {
        return codimg;
    }

    public void setCodimg(String codimg) {
        this.codimg = codimg;
    }

    public FileInputStream getImg() {
        return img;
    }

    public void setImg(FileInputStream img) {
        this.img = img;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public String getAdmcad() {
        return admcad;
    }

    public void setAdmcad(String admcad) {
        this.admcad = admcad;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getCodserv() {
        return codserv;
    }

    public void setCodserv(String codserv) {
        this.codserv = codserv;
    }

    public String getNomeserv() {
        return nomeserv;
    }

    public void setNomeserv(String nomeserv) {
        this.nomeserv = nomeserv;
    }

    public String getDescserv() {
        return descserv;
    }

    public void setDescserv(String descserv) {
        this.descserv = descserv;
    }

    public String getEst() {
        return est;
    }

    public void setEst(String est) {
        this.est = est;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }


}
