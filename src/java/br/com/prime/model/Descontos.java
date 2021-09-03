/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prime.model;

import br.com.prime.conect.sqlServer;

/**
 *
 * @author Vinicius
 */
public class Descontos {

    private String empresa;
    private String supervisor;
    private String vendedor;
    private String pedido;
    private String dtped;
    private String cliente;
    private String codProduto;
    private String descPro;
    private String und;
    private String preco;
    private String vlrun;
    private String obs;
    private String status;
    private Biblioteca b = new Biblioteca();

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
    
    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }
    
    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public String getDtped() {
        return dtped;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    public void setDtped(String dtped) {
        this.dtped = dtped;
    }
    
    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

    public String getDescPro() {
        return descPro;
    }

    public void setDescPro(String descPro) {
        this.descPro = descPro;
    }

    public String getUnd() {
        return und;
    }

    public void setUnd(String und) {
        this.und = und;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
    
    public String getVlrun() {
        return vlrun;
    }

    public void setVlrun(String vlrun) {
        if(b.isNumeric(vlrun)){
            if (!vlrun.equals(this.vlrun)) {
                sqlServer sql = new sqlServer();
                try {
                    int qtd = sql.SqlNumRows("SELECT * FROM TBL_DESCBANCOUN WHERE NUMPED='"+this.pedido+"' AND CODPROD='"+this.codProduto+"' ");
                    if(qtd > 0){
                        sql.SqlUpdate("UPDATE TBL_DESCBANCOUN SET PRUNIT = "+vlrun+" WHERE NUMPED='"+this.pedido+"' AND CODPROD='"+this.codProduto+"' ");
                    }else{
                        sql.SqlUpdate("INSERT INTO TBL_DESCBANCOUN (NUMPED,CODPROD,PRUNIT,EMPRESA) "
                            + "VALUES ("+getPedido()+","+getCodProduto()+","+vlrun+","+getEmpresa()+" )");
                    }
                } catch (Exception ex) {
                    
                }
            }
            System.out.println("É número!");
        }else{
            System.out.println("Não é número!");
        }
        
        this.vlrun = vlrun;
    }
    public void setVlrun(String vlrun,String x) {
        this.vlrun = vlrun;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
