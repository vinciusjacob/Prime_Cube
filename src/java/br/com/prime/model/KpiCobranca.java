/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prime.model;

/**
 *
 * @author Vinicius
 */
public class KpiCobranca {
    private String parcela;
    private String razao;
    private String valor;
    
    public void setParcela(String parcela){
        this.parcela = parcela;
    }
    public void setRazao(String razao){
        this.razao = razao;
    }
    public void setValor(String valor){
        this.valor = valor;
    }
    public String getParcela(){
        return this.parcela;
    }
    public String getRazao(){
        return this.razao;
    }
    public String getValor(){
        return this.valor;
    }
    
    public KpiCobranca(String parcela,String razao,String valor){
        this.setParcela(parcela);
        this.setRazao(razao);
        this.setValor(valor);
    }
}
