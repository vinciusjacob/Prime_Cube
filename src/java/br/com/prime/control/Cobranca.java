/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prime.control;

import br.com.prime.conect.sqlServer;
import br.com.prime.model.KpiCobranca;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Vinicius
 */
@ManagedBean
public class Cobranca {
    
    private List<KpiCobranca> cobrancas;
    private String msgError;
    private List<KpiCobranca> filteredCobrancas;
    
    public Set<String> getParcelas(){
        List<String> lista = new ArrayList<String>();
        for(KpiCobranca kpi : cobrancas){
            lista.add(kpi.getParcela());
        }
        Set<String> outra = new HashSet<String>(lista);
        return outra;
    }
    public Set<String> getRazoes(){
        List<String> lista = new ArrayList<String>();
        for(KpiCobranca kpi : cobrancas){
            lista.add(kpi.getRazao());
        }
        Set<String> outra = new HashSet<String>(lista);
        return outra;
    }
    public Set<String> getValores(){
        List<String> lista = new ArrayList<String>();
        for(KpiCobranca kpi : cobrancas){
            lista.add(kpi.getValor());
        }
        Set<String> outra = new HashSet<String>(lista);
        return outra;
    }
    private String isNull(String var){
        if(var == null){
            var = "";
        }
        return var;
    }
    
    @PostConstruct
    public void init(){
        sqlServer sql = new sqlServer();
        KpiCobranca kpi;
        this.cobrancas = new ArrayList<KpiCobranca>();
        String query = "SELECT top 1000 NUMPARCELA,CODCLIENTE,SUM(VALOR)AS VALOR FROM TBL_PARCELAS GROUP BY NUMPARCELA,CODCLIENTE";
        try{
            sql.SqlQuery(query);
            while(sql.rs.next()){
                kpi = new KpiCobranca(isNull(sql.rs.getString("NUMPARCELA")),isNull(sql.rs.getString("CODCLIENTE")),isNull(sql.rs.getString("VALOR")));
                this.cobrancas.add(kpi);
            }
        }catch(Exception ex){
            
        }
    }
    
    public List<KpiCobranca> getCobrancas(){
        return this.cobrancas;
    }
    public List<KpiCobranca> getFilteredCobrancas(){
        return this.filteredCobrancas;
    }
    public void setFilteredCobrancas(List<KpiCobranca> filteredCobrancas){
        this.filteredCobrancas = filteredCobrancas;
    }
    
}
