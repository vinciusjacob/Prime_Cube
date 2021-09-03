/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prime.control;

import br.com.prime.conect.sqlServer;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

/**
 *
 * @author Vinicius
 */
@ManagedBean
public class Remessa {

    private Part file;
    private String msg;
    private sqlServer sql;
    private String id;

    @PostConstruct
    public void init() {

    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String error) {
        this.msg = error;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public void teste() {
        this.msg = "Passei aqui!";
    }
    
    private String getDesconto(String numbanco,String cnpj) throws Exception{
        sql = new sqlServer();
        String empresa;
        String desconto = "";
        if(cnpj.equals("15240783000310")){
            empresa  = "CRT";
        }else if(cnpj.equals("15240783000239")){
            empresa = "MOC";
        }else{
            return "0000000000000";
        }
        String query = "SELECT * FROM TBL_DESCBANCO WHERE EMPRESA='"+empresa+"' AND RIGHT('0000000000'+NUMBANCO,12) ='"+numbanco+"'";
        sql.SqlQuery(query);
        while(sql.rs.next()){
            DecimalFormat df = new DecimalFormat("0.00");
            desconto = df.format(sql.rs.getDouble("DESCONTO")).replaceAll(",", "");
            desconto = String.format("%013d", Integer.parseInt(desconto));
            id = sql.rs.getString("ID");
        }
        if(desconto == null || desconto.equals(""))
            desconto = "0000000000000";
        return desconto;
    }
    
    public void setRegistro(String id) throws Exception{
        sql = new sqlServer();
        sql.SqlUpdate("UPDATE TBL_DESCBANCO SET PROCESSADO = 1, DTPROCESSAMENTO = GETDATE() WHERE ID="+id);
    }
    
    //Processa titulos que já estam registrado no banco
    public void newUpload() {
        try {
            UploadFile up = new UploadFile();
            setFile(up.getFile());
            
            if (file != null) {
                String caminho = "C:\\Users\\Vinicius\\Dropbox\\Projetos WEB\\Prime_Cube\\RemessaBanco\\ALT"+up.getHeader(file);
                FileWriter arq = new FileWriter(caminho);
                PrintWriter gravarArq = new PrintWriter(arq);
                Scanner scan = new Scanner(file.getInputStream()).useDelimiter("\\n");
                while (scan.hasNext()){
                    String linha = scan.next();
                    if (linha.substring(2, 9).equals("REMESSA") && linha.substring(11, 19).equals("COBRANÇA")) {
                        gravarArq.println(linha.substring(0, 400));
                    } else if (linha.substring(0, 1).equals("9")) {
                        gravarArq.print(linha.substring(0, 400));
                    } else {
                        String data = linha.substring(120, 126);
                        String desc = getDesconto(linha.substring(62, 74),linha.substring(3, 17));
                        String tipo = "04"; //04 = Concessão de abatimento
                        String nlinha = linha.substring(0, 108)+tipo+linha.substring(110, 205) + desc + linha.substring(218, 400);
                        gravarArq.println(nlinha);
                        this.setRegistro(id);
                    }
                }
                arq.close();
                this.setMsg("Arquivo gerado com sucesso!");
                this.setFile(null);
                up.setFile(null);
            } else {
                this.setMsg("Selecione um arquivo.");
            }
        } catch (Exception ex) {
            this.setMsg("error: " + ex.toString());
        }
    }

    //Função usada em upload
    private String getDesc(String numbanco,String cnpj) throws Exception{
        sql = new sqlServer();
        String empresa;
        String desconto = "";
        if(cnpj.equals("15240783000310")){
            empresa  = "3";
        }else if(cnpj.equals("15240783000239")){
            empresa = "2";
        }else{
            return "0000000000000";
        }
        String query = "SELECT * FROM VW_DESCSINT WHERE EMPPRESA='"+empresa+"' AND RIGHT('0000000000'+NUMNOBANCO,12) ='"+numbanco+"'";
        sql.SqlQuery(query);
        while(sql.rs.next()){
            DecimalFormat df = new DecimalFormat("0.00");
            desconto = df.format(sql.rs.getDouble("DESCONTO")).replaceAll(",", "");
            desconto = String.format("%013d", Integer.parseInt(desconto));
        }
        if(desconto == null || desconto.equals("")){
            desconto = "0000000000000";
        }
        return desconto;
    }
    public void upload() {
        try {
            UploadFile up = new UploadFile();
            setFile(up.getFile());
            if (file != null) {
                String caminho = "C:\\Users\\Vinicius\\Dropbox\\Projetos WEB\\Prime_Cube\\RemessaBanco\\NEW"+up.getHeader(file);
                FileWriter arq = new FileWriter(caminho);
                PrintWriter gravarArq = new PrintWriter(arq);
                Scanner scan = new Scanner(file.getInputStream()).useDelimiter("\\n");
                while (scan.hasNext()) {
                    String linha = scan.next();
                    if (linha.substring(2, 9).equals("REMESSA") && linha.substring(11, 19).equals("COBRANÇA")) {
                        gravarArq.println(linha.substring(0, 400));
                    } else if (linha.substring(0, 1).equals("9")) {
                        gravarArq.print(linha.substring(0, 400));
                    } else {
                        String desc = getDesc(linha.substring(62, 74),linha.substring(3, 17));
                        String data = linha.substring(120, 126);
                        if(desc.equals("0000000000000"))data = "000000";
                        String tipo = "01"; //04 = Concessão de abatimento
                        String nlinha = linha.substring(0, 108)+tipo+linha.substring(110, 205) + desc + linha.substring(218, 400);
                        //String nlinha = linha.substring(0, 173) + data + desc + linha.substring(192, 400);
                        gravarArq.println(nlinha);
                    }
                }
                arq.close();
                this.setMsg("Arquivo gerado com sucesso!");
                this.setFile(null);
                up.setFile(null);
            } else {
                this.setMsg("Selecione um arquivo.");
            }
        } catch (Exception ex) {
            this.setMsg("error: " + ex.toString());
        }
    }
}
