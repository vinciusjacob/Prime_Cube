package br.com.prime.control;

import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

/**
 *
 * @author u090
 */
@ManagedBean
public class Serasa {

    private Part sfile;
    private String status;
    private String cnpj;
    private String period;
    private String periodode;
    private String periodoate;
    private String inconsist;
    private String inconslimt;
    private String dtarquivo;
    private String estilocss;

    public String getEstilocss() {
        return estilocss;
    }

    public void setEstilocss(String estilocss) {
        this.estilocss = estilocss;
    }

    public String getDtarquivo() {
        return dtarquivo;
    }

    public void setDtarquivo(String dtarquivo) {
        this.dtarquivo = dtarquivo;
    }

    public String getInconslimt() {
        return inconslimt;
    }

    public void setInconslimt(String inconslimt) {
        this.inconslimt = inconslimt;
    }

    public String getInconsist() {
        return inconsist;
    }

    public void setInconsist(String inconsist) {
        this.inconsist = inconsist;
    }

    public String getPeriodoate() {
        return periodoate;
    }

    public void setPeriodoate(String periodoate) {
        this.periodoate = periodoate;
    }

    public String getPeriodode() {
        return periodode;
    }

    public void setPeriodode(String periodode) {
        this.periodode = periodode;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @PostConstruct
    public void init() {
        this.status = "NÃO INFORMADO!";
        this.cnpj = "NÃO INFORMADO!";
        this.period = "NÃO INFORMADO!";
        this.periodode = "NÃO INFORMADO!";
        this.inconsist = "NÃO INFORMADO!";
        this.inconslimt = "NÃO INFORMADO!";
        this.dtarquivo = "NÃO INFORMADO!";
    }

    public void importatxt() {
        UploadFile up = new UploadFile();
        sfile = up.getFile();
        try {

            Scanner scan = new Scanner(sfile.getInputStream()).useDelimiter("\\n");
            while (scan.hasNext()) {
                String linha = scan.next();
                linha = linha.replaceAll(" ", "+");
                if (linha.length() >= 42) {
                    if (linha.substring(7, 42).equals("REMESSA+++PARCIALMENTE+++DESPREZADA")) {
                        this.setStatus("REMESSA PARCIALMENTE DESPREZADA");
                        this.setDtarquivo(linha.substring(60, 70).replace(".", "/"));
                    }
                    if (linha.substring(10, 37).equals("TODA+++REMESSA+++DESPREZADA")) {
                        this.setStatus("TODA REMESSA DESPREZADA");
                        this.setDtarquivo(linha.substring(60, 70).replace(".", "/"));
                    }
                    if (linha.substring(7, 42).equals("REMESSA++++TOTALMENTE++++PROCESSADA")) {
                        this.setStatus("REMESSA TOTALMENTE PROCESSADA");
                        this.setDtarquivo(linha.substring(60, 70).replace(".", "/"));
                        this.setEstilocss("display:none !IMPORTANT;");
                    }
                    if (linha.substring(2, 22).equals("+CNPJ+DA+CONVENIADA+")) {
                        this.setCnpj(linha.substring(26, 42));
                    }
                }
                if (linha.length() >= 56) {
                    if (linha.substring(36, 56).equals("PERIODICIDADE++FONTE")) {
                        this.setPeriod(linha.substring(59, 66));
                    }
                }
                if (linha.length() >= 23) {
                    if (linha.substring(3, 23).equals("DATA++INFORMACAO++DE")) {
                        periodode = linha.substring(26, 34);
                        periodode = periodode.substring(0, 2) + "/" + periodode.substring(2, 4) + "/" + periodode.substring(4, 8);
                        this.setPeriodode("DE " + periodode + " ATE ");
                    }
                    if (linha.substring(3, 23).equals("DATA++INFORMACAO+ATE")) {
                        periodoate = linha.substring(26, 34);
                        periodoate = periodoate.substring(0, 2) + "/" + periodoate.substring(2, 4) + "/" + periodoate.substring(4, 8);
                        this.setPeriodode(periodode + periodoate);
                    }
                }
                if (linha.length() >= 30) {
                    if (linha.substring(5, 30).equals("INCONSISTENCIA+DA+REMESSA")) {
                        this.setInconsist(linha.substring(42, 47) + " DA REMESSA");
                    }
                    if (linha.substring(5, 26).equals("INCONSISTENCIA+LIMITE")) {
                        this.setInconslimt(linha.substring(41, 47) + " DE INCONSISTÊNCIA");
                    }
                }
            }

            /**
             * String linha = scan.next(); linha = scan.next(); linha =
             * linha.replaceAll(" ", "+");
             *
             * this.setCnpj(linha.substring(42, 60));*
             */
        } catch (Exception e) {
            status = "Erro!" + e.toString();
        }
    }

    public Part getSfile() {
        return sfile;
    }

    public void setSfile(Part sfile) {
        this.sfile = sfile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = Status;
    }
}
